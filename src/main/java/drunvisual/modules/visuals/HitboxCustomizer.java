package drunvisual.modules.visuals;

import java.awt.Color;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.module.ModuleRegistry;
import drunvisual.settings.BooleanSetting;
import drunvisual.settings.ColorSetting;
import drunvisual.settings.ModeSetting;
import drunvisual.settings.SettingGroup;
import drunvisual.settings.SliderSetting;
import drunvisual.core.Bool;

@ModuleInfo(a = "Hitbox Customizer", b = "Настраивает отображение хитбоксов сущностей", c = ModuleCategory.VISUALS)
public class HitboxCustomizer extends ClientModule {
    public final ModeSetting a = new ModeSetting("Режим обводки", new String[]{"Обычный", "Углы"}, "Обычный");
    public final SliderSetting b = new SliderSetting("Длина углов", 0.5f, 0.1f, 1.0f, 0.05f).a(() -> {
        return Boolean.valueOf(this.a.b("Углы"));
    });
    private final BooleanSetting g = new BooleanSetting("Линии взгляда", false);
    private final BooleanSetting h = new BooleanSetting("Заполнять", true);
    private final SettingGroup i = new SettingGroup("Цвет");
    private final BooleanSetting j = new BooleanSetting("Цвет клиента", true);
    private final ColorSetting k = new ColorSetting("Кастомный цвет", Color.WHITE).a(() -> {
        int i;
        if (this.j.a()) {
            i = 0;
        } else {
            i = 1;
        }
        return Boolean.valueOf(Bool.from(i));
    });

    public boolean n() {
        return Bool.from((k() && this.g.a()) ? 1 : 0);
    }

    public boolean o() {
        return Bool.from((k() && this.h.a()) ? 1 : 0);
    }

    public Color p() {
        return !this.j.a() ? this.k.a() : ModuleRegistry.CLIENT_COLOR.n();
    }

    public boolean q() {
        return this.a.b("Углы");
    }

    public float r() {
        return this.b.a() / 4.0f;
    }

    public static String c(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
