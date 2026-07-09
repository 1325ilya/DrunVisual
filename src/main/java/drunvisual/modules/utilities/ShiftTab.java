package drunvisual.modules.utilities;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.util.InputUtil;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.BooleanSetting;
import drunvisual.events.AttackEntityEvent;
import drunvisual.events.ClientTickEvent;
import drunvisual.events.CriticalHitEvent;

@ModuleInfo(a = "Shift Tab", b = "Briefly sneaks while attacking.", c = ModuleCategory.UTILITIES)
public class ShiftTab extends ClientModule {
    private static final int SNEAK_TICKS = 2;
    private final BooleanSetting triggerBeforeAttack = new BooleanSetting("Before Attack", "If disabled, sneaking starts after the attack event.", true);
    private int sneakTicksLeft;
    private boolean restorePending;
    private boolean previousSneakPressed;

    @EventHandler
    public void a(CriticalHitEvent criticalHitEvent) {
        if (this.triggerBeforeAttack.k().booleanValue()) {
            startSneakBurst();
        }
    }

    @EventHandler
    public void a(AttackEntityEvent attackEntityEvent) {
        if (this.triggerBeforeAttack.k().booleanValue()) {
            return;
        }
        startSneakBurst();
    }

    @EventHandler
    public void a(ClientTickEvent clientTickEvent) {
        if (c.player == null) {
            return;
        }
        if (this.sneakTicksLeft > 0) {
            c.options.sneakKey.setPressed(true);
            this.sneakTicksLeft--;
        } else if (this.restorePending) {
            c.options.sneakKey.setPressed(this.previousSneakPressed);
            this.restorePending = false;
        }
    }

    @Override
    public void f() {
        super.f();
        this.sneakTicksLeft = 0;
        this.restorePending = false;
        if (c == null || c.options == null) {
            return;
        }
        c.options.sneakKey.setPressed(isSneakKeyPhysicallyPressed());
    }

    private void startSneakBurst() {
        if (c.player == null) {
            return;
        }
        this.previousSneakPressed = isSneakKeyPhysicallyPressed();
        this.sneakTicksLeft = SNEAK_TICKS;
        this.restorePending = true;
        c.options.sneakKey.setPressed(true);
    }

    private boolean isSneakKeyPhysicallyPressed() {
        return (c == null || c.getWindow() == null || !InputUtil.isKeyPressed(c.getWindow().getHandle(), c.options.sneakKey.getDefaultKey().getCode())) ? false : true;
    }
}
