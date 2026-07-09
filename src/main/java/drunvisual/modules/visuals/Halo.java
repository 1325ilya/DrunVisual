package drunvisual.modules.visuals;

import java.awt.Color;
import meteordevelopment.orbit.EventHandler;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.module.ModuleRegistry;
import drunvisual.settings.BooleanSetting;
import drunvisual.settings.ColorSetting;
import drunvisual.settings.SettingGroup;
import drunvisual.settings.SliderSetting;
import drunvisual.events.LivingEntityRenderEvent;

@ModuleInfo(a = "Halo", b = "Draws a halo above the player.", c = ModuleCategory.VISUALS)
public class Halo extends ClientModule {
    private final SliderSetting lineWidth = new SliderSetting("Line Width", 0.05f, 0.01f, 0.2f, 0.01f);
    private final SliderSetting radius = new SliderSetting("Radius", 0.5f, 0.3f, 1.5f, 0.05f);
    private final SliderSetting yOffset = new SliderSetting("Y Offset", -0.1f, -1.0f, 0.5f, 0.05f);
    private final SettingGroup colorGroup = new SettingGroup("Color");
    private final BooleanSetting useClientColor = new BooleanSetting("Use Client Color", true);
    private final ColorSetting color = new ColorSetting("Custom Color", Color.WHITE).a(() -> {
        return Boolean.valueOf(!this.useClientColor.a());
    });

    @EventHandler
    public void a(LivingEntityRenderEvent livingEntityRenderEvent) {
    }

    private Color n() {
        return this.useClientColor.a() ? ModuleRegistry.CLIENT_COLOR.n() : this.color.a();
    }
}
