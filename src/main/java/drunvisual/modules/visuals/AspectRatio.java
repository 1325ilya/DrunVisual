package drunvisual.modules.visuals;

import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.ModeSetting;
import drunvisual.settings.SliderSetting;

@ModuleInfo(a = "Aspect Ratio", b = "Changes the screen aspect ratio", c = ModuleCategory.VISUALS)
public class AspectRatio extends ClientModule {
    private static final String MODE_16_9 = "16:9";
    private static final String MODE_4_3 = "4:3";
    private static final String MODE_21_9 = "21:9";
    private static final String MODE_1_1 = "1:1";
    private static final String MODE_CUSTOM = "Custom";
    public final ModeSetting a = new ModeSetting("Mode", new String[]{MODE_16_9, MODE_4_3, MODE_21_9, MODE_1_1, MODE_CUSTOM}, MODE_16_9);
    public final SliderSetting b = new SliderSetting("Custom Ratio", 1.78f, 0.5f, 3.0f, 0.01f).a(() -> {
        return Boolean.valueOf(this.a.b(MODE_CUSTOM));
    });

    public float n() {
        switch (this.a.d()) {
            case "4:3":
                return 1.3333334f;
            case "21:9":
                return 2.3333333f;
            case "1:1":
                return 1.0f;
            case "Custom":
                return this.b.a();
            default:
                return 1.7777778f;
        }
    }
}
