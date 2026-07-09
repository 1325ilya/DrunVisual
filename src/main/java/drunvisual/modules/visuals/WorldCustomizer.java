package drunvisual.modules.visuals;

import java.awt.Color;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.module.ModuleRegistry;
import drunvisual.settings.BooleanSetting;
import drunvisual.settings.ColorSetting;
import drunvisual.settings.SettingGroup;
import drunvisual.settings.SliderSetting;
import drunvisual.core.Bool;

@ModuleInfo(a = "World Customizer", b = "Настройка мира", c = ModuleCategory.VISUALS)
public class WorldCustomizer extends ClientModule {
    private final SettingGroup e = new SettingGroup("Туман");
    private final BooleanSetting f = new BooleanSetting("Настроить туман", true);
    private final BooleanSetting g = new BooleanSetting("Кастомная дальность", false).a(() -> {
        return Boolean.valueOf(this.f.a());
    });
    private final SliderSetting h = new SliderSetting("Дальность тумана", 0.3f, 0.1f, 1.0f, 0.01f).a(() -> {
        return (this.f.a() && this.g.a()) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final SettingGroup i = new SettingGroup("Небо");
    private final BooleanSetting j = new BooleanSetting("Настроить небо", true);
    private final SettingGroup k = new SettingGroup("Цвет");
    private final BooleanSetting l = new BooleanSetting("Цвет клиента", true);
    private final ColorSetting m = new ColorSetting("Кастомный цвет", Color.WHITE).a(() -> {
        int i;
        if (this.l.a()) {
            i = 0;
        } else {
            i = 1;
        }
        return Boolean.valueOf(Bool.from(i));
    });
    public static int a;
    public static boolean b;

    public boolean n() {
        if (!k()) {
        } else if (this.f.a()) {
            return true;
        }
        return false;
    }

    public boolean o() {
        return Bool.from((k() && this.f.a() && this.g.a()) ? 1 : 0);
    }

    public float p() {
        return 0.0f;
    }

    public float q() {
        return this.h.a() * 250.0f;
    }

    public boolean r() {
        if (k()) {
            if (this.j.a()) {
                return true;
            }
        }
        return false;
    }

    public int s() {
        return u().getRGB();
    }

    public int t() {
        return u().getRGB();
    }

    private Color u() {
        return !this.l.a() ? this.m.a() : ModuleRegistry.CLIENT_COLOR.n();
    }

    @Override
    public void f() {
        super.f();
        if (c.worldRenderer != null) {
            c.worldRenderer.reload();
        }
    }

    public static String c(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
