package drunvisual.media.chat;

import java.awt.Color;
import lombok.Generated;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.text.Text;
import net.minecraft.text.Style;
import net.minecraft.text.MutableText;
import net.minecraft.text.TextColor;
import drunvisual.client.MinecraftContext;
import drunvisual.module.ModuleRegistry;
import ru.drunvisual.DrunVisual;
import drunvisual.util.ColorUtils;

public final class ChatMessages implements MinecraftContext {
    private static final String e = "[DrunVisual] ";
    public static int a;
    public static boolean b;

    public static void a(Text TextVar) {
        if (c.player != null) {
            c.player.sendMessage(TextVar, false);
        }
    }

    public static void a(String str) {
        if (c.player != null) {
            c.player.networkHandler.sendChatCommand(str);
        }
    }

    public static void a(Object obj) {
        if (obj == null) {
            obj = "null";
        }
        if (c.player == null) {
            DrunVisual.getLOGGER().info("(CHAT) " + String.valueOf(obj));
        } else {
            c.inGameHud.getChatHud().addMessage(b("[DrunVisual] ").append(Text.literal("» ").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(16777215)))).append(Text.literal(("§7" + String.valueOf(obj)).replace("&", "§"))));
        }
    }

    public static void b(Text TextVar) {
        if (c.player != null) {
            c.inGameHud.getChatHud().addMessage(b("[DrunVisual] ").append(Text.literal("» ").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(16777215)))).append(TextVar));
        }
    }

    public static MutableText b(String str) {
        MutableText MutableTextVarLiteral = Text.literal("");
        Color color = new Color(a());
        Color colorA = a(color);
        int length = str.length();
        for (int i = 0; i < length; i++) {
            MutableTextVarLiteral.append(Text.literal(String.valueOf(str.charAt(i))).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(ColorUtils.a(color, colorA, length <= 1 ? 0.0f : i / ((length & (-2)) - ((length ^ (-1)) & 1))).getRGB()))));
        }
        return MutableTextVarLiteral;
    }

    private static int a() {
        return ModuleRegistry.CLIENT_COLOR.o();
    }

    private static Color a(Color color) {
        float[] fArrRGBtoHSB = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), (float[]) null);
        return new Color(Color.HSBtoRGB(fArrRGBtoHSB[0], Math.max(0.0f, fArrRGBtoHSB[1] - 0.3f), Math.min(1.0f, fArrRGBtoHSB[2] + 0.2f)));
    }

    @Generated
    private ChatMessages() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String b(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
