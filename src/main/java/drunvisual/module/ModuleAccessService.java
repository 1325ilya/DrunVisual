package drunvisual.module;

import drunvisual.client.MinecraftContext;
import drunvisual.hud.core.HudService;
import drunvisual.hud.core.HudServiceInfo;

@HudServiceInfo(enabledByDefault = true)
public class ModuleAccessService extends HudService implements MinecraftContext {
    @Override
    public void a() {
        initialize();
    }

    public void initialize() {
    }

    public boolean isModuleAllowed(String str) {
        return true;
    }

    public boolean a(String str) {
        return isModuleAllowed(str);
    }
}
