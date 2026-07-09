package drunvisual.modules.utilities;

import java.util.Objects;
import meteordevelopment.orbit.EventHandler;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.BooleanSetting;
import drunvisual.settings.ModeSetting;
import drunvisual.events.DropItemEvent;

@ModuleInfo(a = "Lock Slot", b = "Prevents dropping items from selected hotbar slots.", c = ModuleCategory.UTILITIES)
public class LockSlot extends ClientModule {
    private final BooleanSetting lockHotbarSlots = new BooleanSetting("Lock Hotbar Slots", true);
    private final ModeSetting slots;
    private final BooleanSetting lockOnlyValuableItems;

    public LockSlot() {
        ModeSetting modeSetting = new ModeSetting("Slots", new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"}, new int[0]);
        BooleanSetting booleanSetting = this.lockHotbarSlots;
        Objects.requireNonNull(booleanSetting);
        this.slots = modeSetting.a(booleanSetting::k);
        this.lockOnlyValuableItems = new BooleanSetting("Only Valuable Items", false);
    }

    @EventHandler
    public void a(DropItemEvent dropItemEvent) {
    }
}
