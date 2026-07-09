package drunvisual.modules.hud;

import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.SliderSetting;

@ModuleInfo(a = "Target HUD", b = "Shows target information.", c = ModuleCategory.HUD)
public class TargetHud extends ClientModule {
    private final SliderSetting scale = new SliderSetting("Scale", 1.0f, 1.0f, 2.0f, 0.1f);

    public TargetHud() {
        collectSettings();
    }

    public SliderSetting n() {
        return this.scale;
    }
}
