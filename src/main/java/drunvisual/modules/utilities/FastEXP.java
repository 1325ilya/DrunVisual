package drunvisual.modules.utilities;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.Hand;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.BooleanSetting;
import drunvisual.settings.SliderSetting;
import drunvisual.events.ClientTickEvent;

@ModuleInfo(a = "Fast EXP", b = "Мгновенно бросает бутылки опыта", c = ModuleCategory.UTILITIES)
public class FastEXP extends ClientModule {
    private final SliderSetting throwsPerTick = new SliderSetting("Бросков в тик", 5.0f, 1.0f, 20.0f, 1.0f);
    private final BooleanSetting onlyInHand = new BooleanSetting("Только в руке", "Бросать только из основной руки", true);
    public static int a;
    public static boolean b;

    @EventHandler
    public void onTick(ClientTickEvent clientTickEvent) {
        if (c.player == null || c.world == null || c.currentScreen != null) {
            return;
        }
        int iA = (int) this.throwsPerTick.a();
        for (int i = 0; i < iA; i++) {
            boolean z = false;
            if (c.player.getMainHandStack().getItem() == Items.EXPERIENCE_BOTTLE) {
                c.player.networkHandler.sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 0, c.player.getYaw(), c.player.getPitch()));
                z = true;
            }
            if (!z && !this.onlyInHand.k().booleanValue() && c.player.getOffHandStack().getItem() == Items.EXPERIENCE_BOTTLE) {
                c.player.networkHandler.sendPacket(new PlayerInteractItemC2SPacket(Hand.OFF_HAND, 0, c.player.getYaw(), c.player.getPitch()));
            }
        }
    }
}
