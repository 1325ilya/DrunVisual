package drunvisual.gui.config;

import java.awt.Color;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.config.ConfigEntry;
import drunvisual.core.Bool;
import drunvisual.gui.core.GuiInput;
import drunvisual.gui.widgets.ScrollBar;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.theme.Theme;
import drunvisual.util.ColorUtils;

public class ConfigTextDialog {
    private static final float a = 178.5f;
    private static final float b = 9.5f;
    private static final float c = 12.0f;
    private static final float d = 11.0f;
    private static final float e = 10.0f;
    private static final float f = 22.0f;
    private static final float g = 3.0f;
    private static final float h = 9.5f;
    private static final float i = 18.0f;
    private static final float j = 6.0f;
    private static final float k = 7.0f;
    private static final float l = 2.0f;
    private static final int m = 5;
    private ConfigEntry q;
    private Runnable z;
    private boolean n = false;
    private String o = "";
    private String[] p = new String[0];
    private boolean r = false;
    private final ScrollBar s = new ScrollBar(l, 20.0f);
    private final AnimationState t = new AnimationState();
    private final AnimationState u = new AnimationState();
    private final AnimationState v = new AnimationState();
    private final AnimationState w = new AnimationState();
    private boolean x = false;
    private boolean y = false;

    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void a(String str, ConfigEntry configEntry) {
        String strA;
        this.q = configEntry;
        if (configEntry != null) {
            strA = configEntry.a();
            if (strA == null) {
                strA = str;
            }
        } else {
            strA = str;
        }
        if (strA == null) {
            strA = "";
        }
        this.o = strA;
        String[] strArr = new String[1];
        strArr[0] = str == null ? "" : str;
        this.p = strArr;
        this.n = true;
        this.s.e();
        this.t.a(true, 1.0d);
        this.u.a(true, 1.0d);
        this.v.a(true, 1.0d);
        this.w.a(true, 1.0d);
    }

    private float e() {
        return d + 20.0f + e + ((Math.min(this.p.length, m) * 25.0f) - g) + e + 18.0f + d;
    }

    private float f() {
        return (Math.min(this.p.length, m) * 25.0f) - g;
    }

    private float g() {
        return (this.p.length * 25.0f) - g;
    }

    private boolean h() {
        return Bool.from(this.p.length > m ? 1 : 0);
    }

    public void a() {
    }

    public boolean b() {
        return this.n;
    }

    public boolean c() {
        return Bool.from((!this.n || this.t.j() < 0.01d) ? 1 : 0);
    }

    public void a(Runnable runnable) {
        this.z = runnable;
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, int i2, int i3) {
        this.t.a();
        this.u.a();
        this.v.a();
        this.w.a();
        this.s.a();
        float fJ = (float) this.t.j();
        if (fJ < 0.01f) {
            if (this.n && this.t.d()) {
                this.n = false;
                if (this.z != null) {
                    this.z.run();
                    return;
                }
                return;
            }
            return;
        }
        int i4 = (int) (255.0f * fJ);
        float fE = e();
        float f4 = (f2 - a) / l;
        float f5 = (f3 - fE) / l;
        float f6 = 0.9f + (0.1f * fJ);
        float f7 = a * f6;
        float f8 = fE * f6;
        float f9 = f4 + ((a - f7) / l);
        float f10 = f5 + ((fE - f8) / l);
        Color colorA = Theme.a(new Color(17, 17, 23, 204), i4);
        Color colorA2 = Theme.a(new Color(13, 13, 17, 204), i4);
        renderer2D.a(f9, f10, f7, f8, 9.5f * f6, colorA, colorA, colorA2, colorA2, MatrixStackVar);
        FontRenderer fontRenderer = FontManager.b[15];
        FontRenderer fontRenderer2 = FontManager.a[12];
        String str = this.p.length > 1 ? "Ключи успешно созданы" : "Ключ успешно создан";
        float fA = f9 + ((f7 - fontRenderer.a(str)) / l);
        float f11 = f10 + (d * f6);
        fontRenderer.a(str, fA, f11, Theme.a(Theme.a, i4), MatrixStackVar);
        float fB = (f11 + (fontRenderer.b(str) / l)) - j;
        if (this.q != null) {
            String strHeader = "Конфиг: " + this.q.a() + (this.p.length > 1 ? " (" + this.p.length + " шт.)" : "");
            fontRenderer2.a(strHeader, f9 + ((f7 - fontRenderer2.a(strHeader)) / l), fB + 8.0f, Theme.a(Theme.b, i4), MatrixStackVar);
        }
        float f12 = f9 + (c * f6);
        float f13 = f7 - (24.0f * f6);
        float fB2 = fB + fontRenderer2.b("A") + (e * f6);
        float f14 = f() * f6;
        float fG = g() * f6;
        if (h()) {
            renderer2D.b().a(f12, fB2, f13 + e, f14, MatrixStackVar);
        }
        float fB3 = this.s.b();
        for (int i5 = 0; i5 < this.p.length; i5++) {
            float f15 = (fB2 + ((i5 * 25.0f) * f6)) - fB3;
            if (f15 + (f * f6) >= fB2 && f15 <= fB2 + f14) {
                a(MatrixStackVar, renderer2D, f12, f15, f13, f * f6, i4, this.p[i5]);
            }
        }
        if (h()) {
            renderer2D.b().a(MatrixStackVar);
            this.s.a(MatrixStackVar, renderer2D, ((f9 + f7) - ((c * f6) / l)) - l, fB2, f14, fG, f14, i2, i3, false);
        }
        float f16 = fB2 + f14 + (e * f6);
        float f17 = (f13 - (7.0f * f6)) / l;
        float f18 = f12 + f17 + (7.0f * f6);
        boolean zA = GuiInput.a(f12, f16, f17, 18.0f * f6, i2, i3);
        boolean zA2 = GuiInput.a(f18, f16, f17, 18.0f * f6, i2, i3);
        if (zA != this.x) {
            this.u.a(zA ? 1.0d : 0.0d, 0.15d, Easing.h);
            this.x = zA;
        }
        if (zA2 != this.y) {
            this.v.a(zA2 ? 1.0d : 0.0d, 0.15d, Easing.h);
            this.y = zA2;
        }
        a(MatrixStackVar, renderer2D, f12, f16, f17, 18.0f * f6, i4);
        b(MatrixStackVar, renderer2D, f18, f16, f17, 18.0f * f6, i4);
        if (zA || zA2) {
            GuiInput.g();
        }
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5, int i2, String str) {
        Color colorB = Theme.b(Theme.q, i2 / 255.0f);
        Color colorB2 = Theme.b(Theme.n, i2 / 255.0f);
        renderer2D.a(f2 - 0.5f, f3 - 0.5f, f4 + 1.0f, f5 + 1.0f, 9.5f, colorB, colorB, colorB2, colorB2, MatrixStackVar);
        Color colorA = Theme.a(new Color(12, 12, 20, 153), i2);
        Color colorA2 = Theme.a(new Color(13, 13, 16, 153), i2);
        renderer2D.a(f2, f3, f4, f5, 9.5f, colorA, colorA, colorA2, colorA2, MatrixStackVar);
        FontRenderer fontRenderer = FontManager.a[12];
        float fB = f3 + ((f5 - fontRenderer.b(str)) / l) + 3.5f;
        String strSubstring = str;
        if (fontRenderer.a(strSubstring) > f4 - 16.0f) {
            while (fontRenderer.a(strSubstring + "...") > f4 - 16.0f && strSubstring.length() > 0) {
                int length = strSubstring.length();
                strSubstring = strSubstring.substring(0, (2 * (length & (-2))) - (length ^ 1));
            }
            strSubstring = strSubstring + "...";
        }
        fontRenderer.a(strSubstring, f2 + ((f4 - fontRenderer.a(strSubstring)) / l), fB, Theme.a(Theme.a, i2), MatrixStackVar);
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5, int i2) {
        float fJ = (float) this.u.j();
        float fJ2 = (float) this.w.j();
        Color colorA = ColorUtils.a(Theme.y, new Color(34, 197, 94), fJ2);
        Color colorA2 = ColorUtils.a(Theme.z, new Color(22, 163, 74), fJ2);
        Color colorA3 = ColorUtils.a(Theme.B, new Color(74, 222, 128), fJ2);
        Color colorA4 = ColorUtils.a(Theme.y, new Color(34, 197, 94), fJ2);
        Color colorA5 = ColorUtils.a(colorA, colorA3, fJ);
        Color colorA6 = ColorUtils.a(colorA2, colorA4, fJ);
        Color colorA7 = Theme.a(colorA5, i2);
        Color colorA8 = Theme.a(colorA6, i2);
        renderer2D.a(f2, f3, f4, f5, j, colorA7, colorA7, colorA8, colorA8, MatrixStackVar);
        FontRenderer fontRenderer = FontManager.a[12];
        String strCopy = this.r ? "Скопировано" : "Копировать";
        fontRenderer.a(strCopy, f2 + ((f4 - fontRenderer.a(strCopy)) / l), f3 + ((f5 - fontRenderer.b(strCopy)) / l) + 3.5f, Theme.a(Theme.aa, i2), MatrixStackVar);
    }

    private void b(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5, int i2) {
        float fJ = (float) this.v.j();
        Color colorB = Theme.b(Theme.q, i2 / 255.0f);
        Color colorB2 = Theme.b(Theme.n, i2 / 255.0f);
        renderer2D.a(f2 - 0.5f, f3 - 0.5f, f4 + 1.0f, f5 + 1.0f, j, colorB, colorB, colorB2, colorB2, MatrixStackVar);
        Color colorA = ColorUtils.a(Theme.e, Theme.I, fJ);
        Color colorA2 = ColorUtils.a(Theme.f, Theme.J, fJ);
        Color colorA3 = Theme.a(colorA, i2);
        Color colorA4 = Theme.a(colorA2, i2);
        renderer2D.a(f2, f3, f4, f5, j, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
        FontRenderer fontRendererClose = FontManager.a[12];
        fontRendererClose.a("Закрыть", f2 + ((f4 - fontRendererClose.a("Закрыть")) / l), f3 + ((f5 - fontRendererClose.b("Закрыть")) / l) + 3.5f, Theme.a(ColorUtils.a(Theme.b, Theme.a, fJ), i2), MatrixStackVar);
    }

    public boolean a(float f2, float f3, int i2, int i3) {
        return this.n;
    }

    public void a(float f2, int i2, int i3) {
        if (this.n && h()) {
            float f3 = f();
            this.s.a(f2, g(), f3);
        }
    }

    public void a(int i2, int i3) {
        this.s.d();
    }

    public void a(int i2, int i3, double d2, double d3) {
        if (this.s.c()) {
            float f2 = f();
            this.s.a(i3, g(), f2);
        }
    }

    public boolean a(int i2, int i3, int i4) {
        if (!this.n) {
            return false;
        }
        if (i2 == 259) {
            if (this.o == null) {
                this.o = "";
                return true;
            }
            if (this.o.isEmpty()) {
                return true;
            }
            this.o = this.o.substring(0, this.o.length() - 1);
            if (this.q == null) {
                return true;
            }
            this.q.a(this.o);
            return true;
        }
        if (i2 != 257 && i2 != 335) {
            if (i2 != 256) {
                return true;
            }
            this.n = false;
            return true;
        }
        if (this.q != null) {
            this.q.a(this.o);
        }
        this.n = false;
        if (this.z == null) {
            return true;
        }
        this.z.run();
        return true;
    }

    public boolean a(char c2, int i2) {
        if (!this.n) {
            return false;
        }
        if (c2 < ' ') {
            return true;
        }
        this.o = (this.o == null ? "" : this.o) + c2;
        if (this.q == null) {
            return true;
        }
        this.q.a(this.o);
        return true;
    }

    public boolean d() {
        return false;
    }

    public static String a(String str, String str2, int i2, int i3, int i4, int i5) {
        return null;
    }
}
