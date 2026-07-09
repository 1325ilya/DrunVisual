package drunvisual.modules.utilities;

import java.util.List;
import java.util.Optional;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.network.packet.s2c.play.CloseScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.TokenSetting;
import drunvisual.events.ClientTickEvent;
import drunvisual.events.MouseButtonEvent;
import drunvisual.events.PacketEvent;
import drunvisual.events.WorldChangeEvent;
import drunvisual.media.chat.ChatMessages;
import drunvisual.util.ElapsedTimer;

@ModuleInfo(a = "RW Joiner", b = "Автоматически подключается к выбранному грифу ReallyWorld", c = ModuleCategory.UTILITIES)
public class RwJoiner extends ClientModule {
    private static final String f = "ГРИФЕРСКОЕ ВЫЖИВАНИЕ (1.16.5-1.20.4)";
    private static final int g = 5000;
    private List<ItemStack> n;
    private static final long q = 100;
    private static final long r = 1000;
    private static final long v = 3000;
    public static int a;
    public static boolean b;
    private final TokenSetting e = new TokenSetting("Номер грифа", TokenSetting.TokenType.NUMBER, "1", "Введите номер");
    private final ElapsedTimer h = new ElapsedTimer();
    private int i = -1;
    private String j = "";
    private boolean k = false;
    private boolean l = false;
    private boolean m = false;
    private int o = 0;
    private long p = 0;
    private boolean s = false;
    private long t = 0;
    private long u = 0;

    @EventHandler
    public void a(ClientTickEvent clientTickEvent) {
        if (c.player == null || c.world == null || !q()) {
            return;
        }
        if (this.l) {
            if (this.h.a() >= 5000) {
                this.l = false;
                p();
                this.t = 0L;
                n();
                return;
            }
            return;
        }
        if (this.k || this.s) {
            return;
        }
        if (this.t > 0 && System.currentTimeMillis() - this.t > r) {
            this.t = 0L;
        }
        n();
    }

    private void n() {
        if (System.currentTimeMillis() - this.p >= q) {
            if (this.t <= 0 || System.currentTimeMillis() - this.t >= r) {
                if (this.u <= 0) {
                } else if (System.currentTimeMillis() - this.u < v) {
                    return;
                }
                Optional<Integer> optionalA = a(Items.COMPASS);
                if (optionalA.isPresent()) {
                    int iIntValue = optionalA.get().intValue();
                    if (c.player.getInventory().selectedSlot != iIntValue) {
                        c.player.getInventory().selectedSlot = iIntValue;
                        return;
                    }
                    c.interactionManager.interactItem(c.player, Hand.MAIN_HAND);
                    this.p = System.currentTimeMillis();
                    this.t = System.currentTimeMillis();
                    this.s = true;
                }
            }
        }
    }

    private Optional<Integer> a(Item ItemVar) {
        for (int i = 0; i < 9; i++) {
            ItemStack ItemStackVarGetStack = c.player.getInventory().getStack(i);
            if (!ItemStackVarGetStack.isEmpty() && ItemStackVarGetStack.getItem() == ItemVar) {
                return Optional.of(Integer.valueOf(i));
            }
        }
        return Optional.empty();
    }

    private void o() {
        if (this.n == null || this.n.isEmpty() || System.currentTimeMillis() - this.p < q) {
            return;
        }
        if (this.j.contains("Выбор сервера")) {
            if (!this.m) {
                int iB = b(f);
                if (iB == -1) {
                    p();
                    d();
                    return;
                } else {
                    c.interactionManager.clickSlot(this.i, iB, 0, SlotActionType.PICKUP, c.player);
                    this.m = true;
                    this.p = System.currentTimeMillis();
                    return;
                }
            }
        }
        if (this.j.contains("Выбор мира грифа") && this.m && this.e.d()) {
            try {
                int iB2 = b("ГРИФ #" + Integer.parseInt(this.e.k()));
                if (iB2 == -1) {
                    p();
                    return;
                }
                c.interactionManager.clickSlot(this.i, iB2, 0, SlotActionType.PICKUP, c.player);
                int i = this.o;
                this.o = (i | 1) + (i & 1);
                this.p = System.currentTimeMillis();
                this.u = System.currentTimeMillis();
                p();
            } catch (NumberFormatException e) {
            }
        }
    }

    private void a(String str) {
        ChatMessages.a((Object) (g() + ": " + str));
    }

    private void p() {
        this.i = -1;
        this.j = "";
        this.k = false;
        this.n = null;
        this.m = false;
        this.s = false;
        this.t = 0L;
    }

    @EventHandler
    public void a(PacketEvent packetEvent) {
        String strStrip;
        if (packetEvent.d() instanceof OpenScreenS2CPacket) {
            OpenScreenS2CPacket OpenScreenS2CPacketVarD = (OpenScreenS2CPacket) packetEvent.d();
            String strMethod_5392 = Formatting.strip(OpenScreenS2CPacketVarD.getName().getString());
            if (strMethod_5392.contains("Выбор сервера") || strMethod_5392.contains("Выбор мира грифа")) {
                this.i = OpenScreenS2CPacketVarD.getSyncId();
                this.j = strMethod_5392;
                this.k = true;
                this.s = false;
                packetEvent.b();
                return;
            }
            return;
        }
        if (packetEvent.d() instanceof InventoryS2CPacket) {
            InventoryS2CPacket InventoryS2CPacketVarD = (InventoryS2CPacket) packetEvent.d();
            if (InventoryS2CPacketVarD.getSyncId() == this.i || (this.i != -1 && this.k)) {
                this.n = InventoryS2CPacketVarD.getContents();
                o();
                packetEvent.b();
                return;
            }
        }
        if ((packetEvent.d() instanceof CloseScreenS2CPacket) && ((CloseScreenS2CPacket) packetEvent.d()).getSyncId() == this.i) {
            p();
            packetEvent.b();
            return;
        }
        if (!(packetEvent.d() instanceof GameMessageS2CPacket) || (strStrip = Formatting.strip(((GameMessageS2CPacket) packetEvent.d()).content().getString())) == null) {
            return;
        }
        if (strStrip.contains("Не удалось подключиться к серверу") || strStrip.contains("Подождите несколько секунд перед повторым подключением") || strStrip.contains("сервер переполнен") || strStrip.contains("Unable to connect")) {
            if (!strStrip.contains("Unable to connect")) {
                packetEvent.b();
            } else if (b) {
            }
            this.l = true;
            this.h.b();
            p();
        }
    }

    @EventHandler
    public void a(WorldChangeEvent worldChangeEvent) {
        if (l() && !this.l && this.e.d()) {
            try {
                a("Успешно подключено к Гриф #" + Integer.parseInt(this.e.k()));
                d();
            } catch (NumberFormatException e) {
            }
        }
    }

    @EventHandler
    public void a(MouseButtonEvent mouseButtonEvent) {
    }

    private boolean q() {
        if (c.inGameHud == null || c.inGameHud.getPlayerListHud() == null) {
            return false;
        }
        try {
            Text header = ((ru.drunvisual.mixin.accessor.PlayerListHudAccessor) (Object) c.inGameHud.getPlayerListHud()).getHeader();
            if (header == null) {
                return false;
            }
            String strStrip = Formatting.strip(header.getString());
            if (strStrip != null) {

                if (strStrip.contains("Вы находитесь в: Lobby")) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private int b(String str) {
        String strStrip;
        if (this.n == null) {
            return -1;
        }
        for (int i = 0; i < this.n.size(); i++) {
            ItemStack ItemStackVar = this.n.get(i);
            if (!ItemStackVar.isEmpty() && (strStrip = Formatting.strip(ItemStackVar.getName().getString())) != null && strStrip.contains(str)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void e() {
        super.e();
        if (c.player == null || c.world == null) {
            d();
            return;
        }
        if (!q()) {
            d();
            return;
        }
        this.l = false;
        this.k = false;
        this.o = 0;
        this.s = false;
        this.p = 0L;
        this.t = 0L;
        this.u = 0L;
        this.h.b();
    }

    @Override
    public void f() {
        super.f();
        p();
        this.l = false;
        this.o = 0;
        this.s = false;
        this.u = 0L;
    }

    public static String c(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
