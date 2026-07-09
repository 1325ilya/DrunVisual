package drunvisual.modules.utilities;

import meteordevelopment.orbit.EventHandler;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.BooleanSetting;
import drunvisual.events.ClientTickEvent;
import drunvisual.events.PacketEvent;
import drunvisual.util.ElapsedTimer;

@ModuleInfo(a = "Auto Reissue", b = "Tracks auction reissue cooldowns.", c = ModuleCategory.UTILITIES)
public class AutoReissue extends ClientModule {
    private final BooleanSetting autoReissue = new BooleanSetting("Auto Reissue", true);
    private final BooleanSetting showCooldownOverlay = new BooleanSetting("Show Cooldown Overlay", true);
    public boolean overlayActive = false;
    public ElapsedTimer timer = new ElapsedTimer();
    public int durationMs = 15000;

    @EventHandler
    public void a(PacketEvent packetEvent) {
    }

    @EventHandler
    public void a(ClientTickEvent clientTickEvent) {
    }

    @Override
    public void f() {
        super.f();
        this.overlayActive = false;
        this.timer.b();
    }
}
