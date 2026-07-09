package drunvisual.modules.hud;

import java.awt.Color;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.ColorSetting;
import drunvisual.settings.ModeSetting;

@ModuleInfo(a = "Client Color", b = "Настройка основного цвета клиента", c = ModuleCategory.HUD)
public class ClientColor extends ClientModule {
    private static final int GRADIENT_DURATION_MS = 4000;
    private static final int ASTOLFO_DURATION_MS = 6000;
    private static final int RAINBOW_DURATION_MS = 6000;
    public final ModeSetting a = new ModeSetting("Режим", MODES, MODE_STATIC);
    public final ColorSetting b = new ColorSetting("Цвет", DEFAULT_COLOR).a(() -> {
        return Boolean.valueOf(this.a.b(MODE_STATIC));
    });
    public final ColorSetting e = new ColorSetting("Цвет #1", DEFAULT_GRADIENT_START).a(() -> {
        return Boolean.valueOf(this.a.b(MODE_GRADIENT));
    });
    public final ColorSetting f = new ColorSetting("Цвет #2", DEFAULT_GRADIENT_END).a(() -> {
        return Boolean.valueOf(this.a.b(MODE_GRADIENT));
    });
    private static final String MODE_STATIC = "Статичный";
    private static final String MODE_GRADIENT = "Градиент";
    private static final String MODE_ASTOLFO = "Астольфо";
    private static final String MODE_RAINBOW = "Радуга";
    private static final String[] MODES = {MODE_STATIC, MODE_GRADIENT, MODE_ASTOLFO, MODE_RAINBOW};
    private static final Color DEFAULT_COLOR = new Color(138, 43, 226);
    private static final Color DEFAULT_GRADIENT_START = Color.WHITE;
    private static final Color DEFAULT_GRADIENT_END = new Color(0, 125, 245);
    private static final Color[] ASTOLFO_COLORS = {new Color(255, 121, 198), new Color(170, 70, 255), new Color(80, 181, 255)};

    public Color n() {
        String strD = this.a.d();
        return MODE_STATIC.equals(strD) ? this.b.a() : MODE_GRADIENT.equals(strD) ? animatedGradient() : MODE_ASTOLFO.equals(strD) ? cycleColors(ASTOLFO_COLORS, 6000) : MODE_RAINBOW.equals(strD) ? Color.getHSBColor(linearProgress(6000), 1.0f, 1.0f) : this.b.a();
    }

    public int o() {
        return n().getRGB();
    }

    private Color animatedGradient() {
        return interpolate(this.e.a(), this.f.a(), sineProgress(GRADIENT_DURATION_MS));
    }

    private static Color cycleColors(Color[] colorArr, int i) {
        if (colorArr.length == 0) {
            return Color.WHITE;
        }
        float fLinearProgress = linearProgress(i) * colorArr.length;
        int length = ((int) fLinearProgress) % colorArr.length;
        return interpolate(colorArr[length], colorArr[(length + 1) % colorArr.length], fLinearProgress - ((int) fLinearProgress));
    }

    private static float sineProgress(int i) {
        return (float) (0.5d * (1.0d + Math.sin((System.currentTimeMillis() % ((long) i)) * (6.283185307179586d / ((double) i)))));
    }

    private static float linearProgress(int i) {
        return (System.currentTimeMillis() % ((long) i)) / i;
    }

    private static Color interpolate(Color color, Color color2, float f) {
        float fMax = Math.max(0.0f, Math.min(1.0f, f));
        return new Color(lerp(color.getRed(), color2.getRed(), fMax), lerp(color.getGreen(), color2.getGreen(), fMax), lerp(color.getBlue(), color2.getBlue(), fMax), lerp(color.getAlpha(), color2.getAlpha(), fMax));
    }

    private static int lerp(int i, int i2, float f) {
        return (int) (i + ((i2 - i) * f));
    }
}
