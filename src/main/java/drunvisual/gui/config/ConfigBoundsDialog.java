package drunvisual.gui.config;

import java.awt.Color;
import java.util.function.BiConsumer;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.gui.widgets.TextInput;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.config.ConfigEntry;
import drunvisual.core.Bool;
import drunvisual.gui.core.GuiInput;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.theme.Theme;
import drunvisual.util.ColorUtils;

public class ConfigBoundsDialog {
    private static final float a = 178.5f;
    private static final float b = 155.0f;
    private static final float c = 9.5f;
    private static final float d = 12.0f;
    private static final float e = 11.0f;
    private static final float f = 11.5f;
    private static final float g = 2.5f;
    private static final float h = 22.0f;
    private static final float i = 9.5f;
    private static final float j = 31.5f;
    private static final float k = 18.0f;
    private static final float l = 6.0f;
    private static final float m = 7.0f;
    private static final float n = 18.0f;
    private ConfigEntry p;
    private final TextInput z;
    private BiConsumer<Integer, Integer> A;
    private Runnable B;
    private boolean o = false;
    private final AnimationState q = new AnimationState();
    private final AnimationState r = new AnimationState();
    private final AnimationState s = new AnimationState();
    private final AnimationState t = new AnimationState();
    private boolean u = false;
    private boolean v = false;
    private boolean w = false;
    private boolean x = false;
    private final TextInput y = new TextInput(TextInput.InputType.INT, "", "1");

    public ConfigBoundsDialog() {
        this.y.a(6);
        this.y.a(9.5f, 0.0f, 0.0f, 9.5f);
        this.y.a(Theme.S, Theme.T);
        this.y.a(true);
        this.z = new TextInput(TextInput.InputType.INT, "", "1");
        this.z.a(6);
        this.z.a(9.5f);
        this.z.a(Theme.S, Theme.T);
        this.z.a(true);
    }

    public void a(ConfigEntry configEntry) {
    }

    public void a() {
    }

    public boolean b() {
        return this.o;
    }

    public boolean c() {
        return Bool.from((!this.o || this.q.j() < 0.01d) ? 1 : 0);
    }

    public ConfigEntry d() {
        return this.p;
    }

    public void a(BiConsumer<Integer, Integer> biConsumer) {
        this.A = biConsumer;
    }

    public void a(Runnable runnable) {
        this.B = runnable;
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, int i2, int i3) {
        this.q.a();
        this.r.a();
        this.s.a();
        this.t.a();
        float fJ = (float) this.q.j();
        if (fJ < 0.01f) {
            if (this.o && this.q.d()) {
                this.o = false;
                if (this.B != null) {
                    this.B.run();
                    return;
                }
                return;
            }
            return;
        }
        int i4 = (int) (255.0f * fJ);
        float f4 = (f2 - a) / 2.0f;
        float f5 = (f3 - b) / 2.0f;
        float f6 = 0.9f + (0.1f * fJ);
        float f7 = a * f6;
        float f8 = b * f6;
        float f9 = f4 + ((a - f7) / 2.0f);
        float f10 = f5 + ((b - f8) / 2.0f);
        Color colorA = Theme.a(new Color(17, 17, 23, 204), i4);
        Color colorA2 = Theme.a(new Color(13, 13, 17, 204), i4);
        renderer2D.a(f9, f10, f7, f8, 9.5f * f6, colorA, colorA, colorA2, colorA2, MatrixStackVar);
        FontRenderer fontRenderer = FontManager.b[15];
        FontRenderer fontRenderer2 = FontManager.a[12];
        FontRenderer fontRenderer3 = FontManager.a[12];
        float fA = f9 + ((f7 - fontRenderer.a("Создание ключа")) / 2.0f);
        float f11 = f10 + (e * f6);
        fontRenderer.a("Создание ключа", fA, f11, Theme.a(Theme.a, i4), MatrixStackVar);
        if (this.p != null) {
            String configText = "Конфиг: " + this.p.a();
            fontRenderer2.a(configText, f9 + ((f7 - fontRenderer2.a(configText)) / 2.0f), (f11 + fontRenderer.b("Создание ключа")) - 6.5f, Theme.a(Theme.b, i4), MatrixStackVar);
        }
        float f12 = f9 + (d * f6);
        float f13 = f7 - (24.0f * f6);
        float fB = f11 + fontRenderer.b("Создание ключа") + (f * f6) + 4.0f;
        fontRenderer3.a("Количество использований", f12, fB - 7.0f, Theme.a(Theme.a, i4), MatrixStackVar);
        float fB2 = fB + fontRenderer3.b("A") + (g * f6);
        float f14 = (f13 - (18.0f * f6)) - 3.0f;
        this.y.a(MatrixStackVar, renderer2D, f12, fB2 - 14, f14 + 3.0f, h * f6, i2, i3, fJ);
        float f15 = f12 + f14 + 3.0f;
        int i5 = (this.x || !GuiInput.a(f15, fB2 - ((float) 14), 18.0f * f6, h * f6, (double) i2, (double) i3)) ? 0 : 1;
        if (this.x && this.w) {
            this.t.a(0.0d, 0.15d, Easing.h);
            this.w = false;
        } else if (Bool.from(i5) != this.w) {
            this.t.a(i5 != 0 ? 1.0d : 0.0d, 0.15d, Easing.h);
            this.w = Bool.from(i5);
        }
        a(MatrixStackVar, renderer2D, f15, fB2 - 14, 18.0f * f6, h * f6, i4);
        float f16 = ((fB2 + (h * f6)) + (j * f6)) - 40.0f;
        fontRenderer3.a("Количество ключей", f12, f16 + 7.0f, Theme.a(Theme.a, i4), MatrixStackVar);
        float fB3 = f16 + fontRenderer3.b("A") + (g * f6);
        this.z.a(MatrixStackVar, renderer2D, f12, fB3, f13, h * f6, i2, i3, fJ);
        float f17 = fB3 + (h * f6) + (f * f6);
        float f18 = (f13 - (7.0f * f6)) / 2.0f;
        float f19 = f12 + f18 + (7.0f * f6);
        boolean zA = GuiInput.a(f12, f17, f18, 18.0f * f6, i2, i3);
        boolean zA2 = GuiInput.a(f19, f17, f18, 18.0f * f6, i2, i3);
        if (zA != this.u) {
            this.r.a(zA ? 1.0d : 0.0d, 0.15d, Easing.h);
            this.u = zA;
        }
        if (zA2 != this.v) {
            this.s.a(zA2 ? 1.0d : 0.0d, 0.15d, Easing.h);
            this.v = zA2;
        }
        b(MatrixStackVar, renderer2D, f12, f17, f18, 18.0f * f6, i4);
        c(MatrixStackVar, renderer2D, f19, f17, f18, 18.0f * f6, i4);
        if (zA || zA2 || !(this.x || i5 == 0)) {
            GuiInput.g();
        }
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5, int i2) {
        float fJ = (float) this.t.j();
        Color colorB = Theme.b(Theme.q, i2 / 255.0f);
        Color colorB2 = Theme.b(Theme.n, i2 / 255.0f);
        renderer2D.a(f2 - 0.5f, f3 - 0.5f, f4 + 1.0f, f5 + 1.0f, 9.5f, colorB, colorB, colorB2, colorB2, MatrixStackVar);
        Color colorA = ColorUtils.a(new Color(12, 12, 20, 153), Theme.I, fJ);
        Color colorA2 = ColorUtils.a(new Color(13, 13, 16, 153), Theme.J, fJ);
        Color colorA3 = Theme.a(colorA, i2);
        Color colorA4 = Theme.a(colorA2, i2);
        renderer2D.a(f2, f3, f4, f5, 0.0f, 9.5f, 0.0f, 9.5f, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
        FontRenderer fr0 = FontManager.b[14];
        fr0.a("∞", f2 + ((f4 - fr0.a("∞")) / 2.0f), f3 + ((f5 - fr0.b("∞")) / 2.0f) + 3.5f, Theme.a(ColorUtils.a(Theme.a, Theme.aa, fJ), i2), MatrixStackVar);
    }

    private void b(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5, int i2) {
        float fJ = (float) this.r.j();
        Color colorA = ColorUtils.a(Theme.y, Theme.B, fJ);
        Color colorA2 = ColorUtils.a(Theme.z, Theme.y, fJ);
        Color colorA3 = Theme.a(colorA, i2);
        Color colorA4 = Theme.a(colorA2, i2);
        renderer2D.a(f2, f3, f4, f5, l, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
        FontRenderer fr0 = FontManager.a[12];
        fr0.a("Создать", f2 + ((f4 - fr0.a("Создать")) / 2.0f), f3 + ((f5 - fr0.b("Создать")) / 2.0f) + 3.5f, Theme.a(Theme.aa, i2), MatrixStackVar);
    }

    private void c(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5, int i2) {
        float fJ = (float) this.s.j();
        Color colorB = Theme.b(Theme.q, i2 / 255.0f);
        Color colorB2 = Theme.b(Theme.n, i2 / 255.0f);
        renderer2D.a(f2 - 0.5f, f3 - 0.5f, f4 + 1.0f, f5 + 1.0f, l, colorB, colorB, colorB2, colorB2, MatrixStackVar);
        Color colorA = ColorUtils.a(Theme.e, Theme.I, fJ);
        Color colorA2 = ColorUtils.a(Theme.f, Theme.J, fJ);
        Color colorA3 = Theme.a(colorA, i2);
        Color colorA4 = Theme.a(colorA2, i2);
        renderer2D.a(f2, f3, f4, f5, l, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
        FontRenderer fr0 = FontManager.a[12];
        fr0.a("Отмена", f2 + ((f4 - fr0.a("Отмена")) / 2.0f), f3 + ((f5 - fr0.b("Отмена")) / 2.0f) + 3.5f, Theme.a(ColorUtils.a(Theme.b, Theme.a, fJ), i2), MatrixStackVar);
    }

    public boolean a(float f2, float f3, int i2, int i3) {
        return false;
    }

    private int a(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e2) {
            return 1;
        }
    }

    public boolean a(int i2, int i3, int i4) {
        return !this.o ? false : this.y.d() ? this.y.b(i2, i3, i4) : this.z.d() ? this.z.b(i2, i3, i4) : false;
    }

    public boolean a(char c2, int i2) {
        return !this.o ? false : !Character.isDigit(c2) ? true : this.y.d() ? this.y.a(c2, i2) : this.z.d() ? this.z.a(c2, i2) : false;
    }

    public boolean e() {
        return Bool.from((this.o && (this.y.d() || this.z.d())) ? 1 : 0);
    }

    public static String a(String str, String str2, int i2, int i3, int i4, int i5) {
        return null;
    }
}
