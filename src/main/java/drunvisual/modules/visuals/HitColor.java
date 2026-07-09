package drunvisual.modules.visuals;

import java.awt.Color;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.module.ModuleRegistry;
import drunvisual.settings.BooleanSetting;
import drunvisual.settings.ColorSetting;
import drunvisual.settings.ModeSetting;
import drunvisual.core.Bool;

@ModuleInfo(a = "Hit Color", b = "Изменяет цвет сущностей при получении урона", c = ModuleCategory.VISUALS)
public class HitColor extends ClientModule {
    private final ModeSetting e = new ModeSetting("Режим", new String[]{"Полностью", "Скин"}, "Полностью");
    private final BooleanSetting f = new BooleanSetting("Цвет клиента", true);
    private final ColorSetting g = new ColorSetting("Кастомный цвет", Color.RED).a(() -> {
        return Boolean.valueOf(Bool.from(this.f.k().booleanValue() ? 0 : 1));
    });
    public static int a;
    public static boolean b;

    public Color n() {
        return !this.f.k().booleanValue() ? this.g.k() : ModuleRegistry.CLIENT_COLOR.n();
    }

    public boolean o() {
        return k();
    }

    public boolean p() {
        return this.e.c("Полностью");
    }

    public static String c(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
