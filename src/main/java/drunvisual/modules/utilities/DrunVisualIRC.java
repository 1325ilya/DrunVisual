package drunvisual.modules.utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.net.ssl.SSLSocketFactory;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.text.Text;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;
import drunvisual.events.ChatSendEvent;
import drunvisual.events.EventBusService;
import drunvisual.client.MinecraftContext;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;

@ModuleInfo(a = "DrunVisual IRC", b = "Чат с другими пользователями клиента. Напиши .irc <текст>", c = ModuleCategory.UTILITIES)
public class DrunVisualIRC extends ClientModule implements MinecraftContext {
    private static final String SERVER = "irc.libera.chat";
    private static final int PORT = 6697;
    private static final String CHANNEL = "#drunvisual-client";
    private volatile Socket socket;
    private volatile PrintWriter out;
    private final AtomicBoolean joined = new AtomicBoolean(false);
    private final AtomicBoolean running = new AtomicBoolean(false);
    private String nick;

    public DrunVisualIRC() {
        collectSettings();
        EventBusService.EVENT_BUS.subscribe(this);
    }

    @Override
    public void onEnable() {
        this.nick = buildNick();
        this.running.set(true);
        this.joined.set(false);
        connect();
    }

    @Override
    public void onDisable() {
        this.running.set(false);
        this.joined.set(false);
        sendRaw("QUIT :DrunVisual Client");
        closeSocket();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(ChatSendEvent chatSendEvent) {
        String message = chatSendEvent.getMessage();
        if (message == null || !message.startsWith(".irc")) {
            return;
        }
        chatSendEvent.a();
        if (message.startsWith(".irc ")) {
            String strTrim = message.substring(5).trim();
            if (strTrim.isEmpty()) {
                return;
            }
            if (!isEnabled()) {
                printSystem("Модуль DrunVisual IRC выключен", 16733525);
            } else if (!this.joined.get()) {
                printSystem("Не подключён к IRC", 16733525);
            } else {
                sendRaw("PRIVMSG #drunvisual-client :" + strTrim);
                printChat(this.nick, strTrim, true);
            }
        }
    }

    private void connect() {
        Thread thread = new Thread(() -> {
            String line;
            try {
                try {
                    Socket socketCreateSocket = ((SSLSocketFactory) SSLSocketFactory.getDefault()).createSocket();
                    socketCreateSocket.connect(new InetSocketAddress(SERVER, PORT), 8000);
                    socketCreateSocket.setSoTimeout(300000);
                    this.socket = socketCreateSocket;
                    this.out = new PrintWriter((Writer) new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8), true);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8));
                    sendRaw("NICK " + this.nick);
                    sendRaw("USER drunvisual 0 * :DrunVisual Client");
                    while (this.running.get() && (line = bufferedReader.readLine()) != null) {
                        handleLine(line);
                    }
                    this.joined.set(false);
                    closeSocket();
                } catch (Exception e) {
                    if (this.running.get()) {
                        printSystem("IRC: " + e.getMessage(), 16733525);
                    }
                    this.joined.set(false);
                    closeSocket();
                }
            } catch (Throwable th) {
                this.joined.set(false);
                closeSocket();
                throw th;
            }
        }, "DrunVisual-IRC");
        thread.setDaemon(true);
        thread.start();
    }

    private void closeSocket() {
        try {
            if (this.socket != null) {
                this.socket.close();
            }
        } catch (Exception e) {
        }
        this.socket = null;
        this.out = null;
    }

    private void handleLine(String str) {
        String strSubstring;
        String str2;
        String strSubstring2;
        if (str.startsWith("PING ")) {
            sendRaw("PONG " + str.substring(5));
        }
        String strSubstring3 = "";
        strSubstring = "";
        String strTrim = str;
        if (str.startsWith(":")) {
            int iIndexOf = str.indexOf(32);
            if (iIndexOf < 0) {
                return;
            }
            strSubstring3 = str.substring(1, iIndexOf);
            strTrim = str.substring(iIndexOf + 1).trim();
        }
        int iIndexOf2 = strTrim.indexOf(" :");
        if (iIndexOf2 >= 0) {
            strSubstring = strTrim.substring(iIndexOf2 + 2);
            strTrim = strTrim.substring(0, iIndexOf2).trim();
        }
        String[] strArrSplit = strTrim.split(" ", 2);
        String str3 = strArrSplit[0];
        str2 = strArrSplit.length > 1 ? strArrSplit[1] : "";
        strSubstring2 = strSubstring3.contains("!") ? strSubstring3.substring(0, strSubstring3.indexOf(33)) : strSubstring3;
        switch (str3) {
            case "001":
                sendRaw("JOIN #drunvisual-client");
                break;
            case "433":
                this.nick += "_";
                sendRaw("NICK " + this.nick);
                break;
            case "366":
                this.joined.set(true);
                printSystem("Подключён · #drunvisual-client", 5635925);
                break;
            case "PRIVMSG":
                if (this.joined.get() && !strSubstring2.equalsIgnoreCase(this.nick) && str2.trim().equalsIgnoreCase(CHANNEL)) {
                    printChat(strSubstring2, strSubstring, false);
                    break;
                }
                break;
            case "KICK":
                if ((str2.contains(" ") ? str2.split(" ")[1] : str2).equalsIgnoreCase(this.nick)) {
                    this.joined.set(false);
                    printSystem("Кикнут из #drunvisual-client. Переподключение...", 16755200);
                    try {
                        Thread.sleep(3000L);
                        break;
                    } catch (InterruptedException e) {
                    }
                    if (this.running.get()) {
                        sendRaw("JOIN #drunvisual-client");
                    }
                    break;
                }
                break;
        }
    }

    private void printChat(String str, String str2, boolean z) {
        if (c.player == null) {
            return;
        }
        c.player.sendMessage(Text.literal("").append(lit("[IRC] ", 8146431)).append(lit("<" + str + "> ", z ? 10395294 : 14737632)).append(lit(str2, 16777215)), false);
    }

    private void printSystem(String str, int i) {
        if (c.player == null) {
            return;
        }
        c.player.sendMessage(Text.literal("").append(lit("[IRC] ", 8146431)).append(lit(str, i)), false);
    }

    private static Text lit(String str, int i) {
        return Text.literal(str).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(i)));
    }

    private void sendRaw(String str) {
        PrintWriter printWriter = this.out;
        if (printWriter != null) {
            printWriter.println(str);
        }
    }

    private String buildNick() {
        String strReplaceAll = (c.player != null ? c.player.getGameProfile().getName() : "DrunVisualUser").replaceAll("[^a-zA-Z0-9_]", "_");
        if (strReplaceAll.length() > 16) {
            strReplaceAll = strReplaceAll.substring(0, 16);
        }
        return strReplaceAll;
    }
}
