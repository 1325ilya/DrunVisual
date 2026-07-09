package drunvisual.modules.hud;

import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.SliderSetting;

@ModuleInfo(a = "Saturation HUD", b = "Удобное отображение уровня сытости.", c = ModuleCategory.HUD)
public class SaturationHud extends ClientModule {
    private final SliderSetting scale = new SliderSetting("Scale", 1.0f, 0.8f, 2.0f, 0.1f);

    public SaturationHud() {
        collectSettings();
    }

    public SliderSetting n() {
        return this.scale;
    }
}
