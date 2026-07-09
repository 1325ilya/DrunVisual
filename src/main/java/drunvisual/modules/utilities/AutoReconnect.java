package drunvisual.modules.utilities;

import meteordevelopment.orbit.EventHandler;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.events.ClientTickEvent;

@ModuleInfo(a = "Auto Reconnect", b = "Keeps reconnect state available for the reconnect screen.", c = ModuleCategory.UTILITIES)
public class AutoReconnect extends ClientModule {
    @EventHandler
    public void a(ClientTickEvent clientTickEvent) {
    }

    public boolean n() {
        return false;
    }

    public int o() {
        return 0;
    }
}
