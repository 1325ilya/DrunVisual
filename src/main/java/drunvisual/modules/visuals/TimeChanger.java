package drunvisual.modules.visuals;

import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import drunvisual.events.PacketEvent;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.ModeSetting;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.config.ConfigState;
import drunvisual.events.ClientTickEvent;

@ModuleInfo(a = "Time Changer", b = "Изменяет время суток в мире", c = ModuleCategory.VISUALS)
public class TimeChanger extends ClientModule {
    private final ModeSetting e = new ModeSetting("Время суток", new String[]{"День", "Закат", "Ночь", "Полночь", "Рассвет"}, "День");
    private final AnimationState f = new AnimationState();
    public static int a;
    public static boolean b;

    @EventHandler
    public void a(ClientTickEvent clientTickEvent) {
        if (c.world != null) {
            this.f.a();
            this.f.a(o(), 2.0d, Easing.C, false);
        }
    }

    @EventHandler
    public void a(PacketEvent packetEvent) {
        if (packetEvent.e() == PacketEvent.MessageDirection.RECIEVE) {
            if (packetEvent.d() instanceof WorldTimeUpdateS2CPacket) {
                packetEvent.b();
            } else if (b) {
            }
        }
    }

    public long n() {
        return (long) this.f.j();
    }

    private long o() {
        String strD = this.e.d();
        byte b2 = -1;
        switch (strD.hashCode()) {
            case -1729048529:
                break;
            case 32171536:
                if (!strD.equals("День")) {
                } else {
                }
                break;
            case 32448614:
                break;
            case 999936563:
                break;
            case 2073575010:
                break;
        }
        switch (b2) {
            case EventPriority.MEDIUM /* 0 */:
                return 1000L;
            case ConfigState.a /* 1 */:
                return 12500L;
            case 2:
                return 18000L;
            case 3:
                return 22000L;
            case 4:
                return 23000L;
            default:
                return 1000L;
        }
    }

    @Override
    public void f() {
        super.f();
    }

    public static String c(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
