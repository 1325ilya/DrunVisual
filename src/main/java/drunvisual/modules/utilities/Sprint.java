package drunvisual.modules.utilities;

import meteordevelopment.orbit.EventHandler;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.events.ClientTickEvent;

@ModuleInfo(a = "Sprint", b = "Automatically holds the sprint key.", c = ModuleCategory.UTILITIES)
public class Sprint extends ClientModule {
    @EventHandler
    public void a(ClientTickEvent clientTickEvent) {
        if (c.player != null) {
            c.options.sprintKey.setPressed(c.player.age > 3);
        }
    }
}
