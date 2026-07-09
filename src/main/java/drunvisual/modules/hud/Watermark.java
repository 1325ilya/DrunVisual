package drunvisual.modules.hud;

import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;

@ModuleInfo(a = "Watermark", b = "Displays the client watermark", c = ModuleCategory.HUD)
public class Watermark extends ClientModule {
    public Watermark() {
        collectSettings();
    }
}
