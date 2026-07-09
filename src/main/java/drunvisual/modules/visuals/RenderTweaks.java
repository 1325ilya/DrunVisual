package drunvisual.modules.visuals;

import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.ModeSetting;

@ModuleInfo(a = "Render Tweaks", b = "Отключает различные элементы рендеринга", c = ModuleCategory.VISUALS)
public class RenderTweaks extends ClientModule {
    private static final String[] e = {"Погода", "Тряска урона", "Оверлей огня", "Анимация тотема", "Скорборд", "Боссбар", "Черные сердца", "Пузырьки воздуха", "Свечение игроков"};
    private final ModeSetting f = new ModeSetting("Твики", e, new int[]{0, 1, 2, 3, 6, 7, 8});
    public static int a;
    public static boolean b;

    public boolean n() {
        return (k() && this.f.c("Погода")) ? true : false;
    }

    public boolean o() {
        return (k() && this.f.c("Тряска урона")) ? true : false;
    }

    public boolean p() {
        return (k() && this.f.c("Оверлей огня")) ? true : false;
    }

    public boolean q() {
        return (k() && this.f.c("Анимация тотема")) ? true : false;
    }

    public boolean r() {
        return (k() && this.f.c("Скорборд")) ? true : false;
    }

    public boolean s() {
        return (k() && this.f.c("Боссбар")) ? true : false;
    }

    public boolean t() {
        return (k() && this.f.c("Черные сердца")) ? true : false;
    }

    public boolean u() {
        return (k() && this.f.c("Пузырьки воздуха")) ? true : false;
    }

    public boolean v() {
        if (!k()) {
        } else if (this.f.c("Свечение игроков")) {
            return true;
        }
        return false;
    }

    public static String c(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
