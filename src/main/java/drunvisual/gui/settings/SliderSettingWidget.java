package drunvisual.gui.settings;

import java.awt.Color;
import java.util.Locale;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.settings.SliderSetting;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.core.Bool;
import drunvisual.gui.core.GuiInput;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.theme.Theme;
import drunvisual.util.ColorUtils;
import drunvisual.util.MarqueeText;

public class SliderSettingWidget implements SettingWidget {
    public static final float a = 28.0f;
    private static final float d = 8.0f;
    private static final float e = 6.0f;
    private static final float f = 20.0f;
    private static final float g = 3.0f;
    private static final float h = 7.0f;
    private static final float i = 8.0f;
    private static final Color j = Theme.P;
    private static final Color k = Theme.Q;
    private static final Color l = Theme.R;
    private final String m;
    private float n;
    private final float o;
    private final float p;
    private final float q;
    private final SliderSetting r;
    private final AnimationState s;
    private final AnimationState t;
    private final AnimationState u;
    private final AnimationState v;
    private final AnimationState w;
    private final AnimationState x;
    private final MarqueeText y;
    private boolean z;
    private boolean A;
    private float B;
    private float C;
    private float D;
    private boolean E;
    private float F;
    private final AnimationState G;
    private final AnimationState H;
    public static int b;
    public static boolean c;

    public SliderSettingWidget(String str, float f2, float f3, float f4, float f5) {
        this(null, str, f2, f3, f4, f5);
    }

    public SliderSettingWidget(SliderSetting sliderSetting) {
        this(sliderSetting, sliderSetting.f(), sliderSetting.a(), sliderSetting.c(), sliderSetting.d(), sliderSetting.e());
    }

    private SliderSettingWidget(SliderSetting sliderSetting, String str, float f2, float f3, float f4, float f5) {
        this.s = new AnimationState();
        this.t = new AnimationState();
        this.u = new AnimationState();
        this.v = new AnimationState();
        this.w = new AnimationState();
        this.x = new AnimationState();
        this.y = new MarqueeText();
        this.z = false;
        this.A = false;
        this.C = 0.0f;
        this.D = 0.0f;
        this.E = false;
        this.G = new AnimationState();
        this.H = new AnimationState();
        this.r = sliderSetting;
        this.m = str;
        this.n = f2;
        this.o = f3;
        this.p = f4;
        this.q = f5;
        this.B = f2;
        this.F = f2;
        this.w.d(i());
        this.G.d(i());
        this.H.d(f2);
        this.u.d(0.0d);
        this.v.d(0.0d);
        this.x.d(0.0d);
    }

    public void a(boolean z) {
        this.E = z;
    }

    public boolean c() {
        return this.E;
    }

    @Override
    public String a() {
        return this.m;
    }

    @Override
    public float b() {
        return 28.0f;
    }

    public float e() {
        if (this.r != null && !this.A) {
            float fA = this.r.a();
            if (Math.abs(this.n - fA) > 0.001f) {
                this.n = fA;
                this.F = this.n;
                if (this.E) {
                    this.H.d(this.n);
                }
                this.w.d(i());
                this.G.d(i());
                this.B = this.n;
            }
        }
        return !this.E ? this.n : (float) this.H.j();
    }

    public void a(float f2) {
        this.n = Math.max(this.o, Math.min(this.p, f2));
        if (this.r != null) {
            this.r.a(this.n);
        }
    }

    public float f() {
        return this.o;
    }

    public float g() {
        return this.p;
    }

    public float h() {
        return this.q;
    }

    private float i() {
        if (this.p == this.o) {
            return 0.0f;
        }
        return (((this.E && this.A) ? this.F : this.n) - this.o) / (this.p - this.o);
    }

    private float m() {
        if (this.E) {
            if (this.A) {
                return this.F;
            }
        }
        return this.n;
    }

    private String b(float f2) {
        return this.q < 1.0f ? this.q < 0.1f ? String.format(Locale.US, "%.2f", Float.valueOf(f2)) : String.format(Locale.US, "%.1f", Float.valueOf(f2)) : String.valueOf((int) f2);
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, int i2, int i3, float f5, float f6) {
        float fJ;
        if (this.r != null && !this.A && Math.abs(this.n - this.r.a()) > 0.001f) {
            this.n = this.r.a();
            this.F = this.n;
            this.w.d(i());
            this.G.d(i());
            this.H.d(this.n);
            this.B = this.n;
        }
        FontRenderer fontRenderer = FontManager.a[14];
        float f7 = 8.0f * f6;
        boolean zA = GuiInput.a(f2, f3, f4, 28.0f * f6, i2, i3);
        this.y.a(zA);
        if (zA != this.z) {
            this.s.a(!zA ? 0.0d : 1.0d, 0.15d, Easing.h);
            this.z = zA;
        }
        if (this.n != this.B) {
            this.w.a(i(), 0.15d, Easing.C);
            this.x.d(1.0d);
            this.x.a(0.0d, 0.4d, Easing.h);
            this.B = this.n;
        }
        this.s.a();
        this.t.a();
        this.u.a();
        this.v.a();
        this.w.a();
        this.x.a();
        this.H.a();
        this.G.a();
        float fJ2 = (float) this.s.j();
        float fJ3 = (float) this.t.j();
        float fJ4 = (float) this.u.j();
        if (this.E && this.A) {
            this.G.a(i(), 0.1d, Easing.h);
            fJ = (float) this.G.j();
        } else {
            fJ = (float) this.w.j();
            this.G.d(fJ);
        }
        int i4 = (int) (255.0f * f5);
        String strB = b(m());
        float fA = fontRenderer.a(strB) * f6;
        float f8 = fA + (5.0f * f6 * 2.0f);
        float f9 = ((f2 + f4) - f7) - f8;
        float f10 = (f9 - (f2 + f7)) - (4.0f * f6);
        float f11 = f3 + (e * f6);
        this.y.a(MatrixStackVar, renderer2D, fontRenderer, this.m, (int) (f2 + f7), ((int) f11) - 1, f10, f6, Theme.a, f5);
        float fB = fontRenderer.b(strB) * f6;
        float f12 = 14.0f * f6;
        float fB2 = ((f11 + ((fontRenderer.b(this.m) * f6) / 2.0f)) - (f12 / 2.0f)) - (5.0f * f6);
        float f13 = 4.0f * f6;
        Color colorA = Theme.a(Theme.m, i4);
        Color colorA2 = Theme.a(Theme.r, i4);
        renderer2D.a(f9 - (0.5f * f6), fB2 - (0.5f * f6), f8 + f6, f12 + f6, f13, colorA, colorA, colorA2, colorA2, MatrixStackVar);
        Color colorA3 = Theme.a(Theme.e, i4);
        Color colorA4 = Theme.a(Theme.f, i4);
        renderer2D.a(f9, fB2, f8, f12, f13, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
        float f14 = f9 + (f8 / 2.0f);
        float f15 = fB2 + (f12 / 2.0f);
        float fRound = Math.round(f14 - (fA / 2.0f));
        float fRound2 = Math.round(f15 - (fB / 2.0f));
        Color colorA5 = Theme.a(Theme.a, i4);
        MatrixStackVar.push();
        MatrixStackVar.translate(fRound, fRound2, 0.0f);
        MatrixStackVar.scale(f6, f6, 1.0f);
        MatrixStackVar.translate(-fRound, -fRound2, 0.0f);
        fontRenderer.a(strB, fRound, fRound2 + 3.5f, colorA5, MatrixStackVar);
        MatrixStackVar.pop();
        float f16 = f3 + (20.0f * f6);
        float f17 = f4 - (f7 * 2.0f);
        float f18 = g * f6;
        float f19 = f18 / 2.0f;
        float f20 = f2 + f7;
        this.C = f20;
        this.D = f17;
        renderer2D.a(f20, f16, f17, f18, f19, Theme.a(ColorUtils.a(Theme.b, j, fJ2), i4), MatrixStackVar);
        float f21 = f17 * fJ;
        if (f21 > 0.0f) {
            Color colorA6 = ColorUtils.a(Theme.y, k, fJ3);
            Color colorA7 = ColorUtils.a(Theme.z, l, fJ3);
            Color colorA8 = Theme.a(colorA6, i4);
            Color colorA9 = Theme.a(colorA7, i4);
            if (f21 < f19 * 2.0f) {
                renderer2D.a(f20, f16, f21, f18, f21 / 2.0f, colorA8, colorA8, colorA9, colorA9, MatrixStackVar);
            } else {
                renderer2D.a(f20, f16, f21, f18, f19, colorA8, colorA8, colorA9, colorA9, MatrixStackVar);
            }
        }
        float f22 = (7.0f + (1.0f * fJ4)) * f6;
        renderer2D.a(Math.max(f20 - (f22 / 2.0f), Math.min((f20 + f21) - (f22 / 2.0f), (f20 + f17) - (f22 / 2.0f))), (f16 + (f18 / 2.0f)) - (f22 / 2.0f), f22, f22, f22, Theme.a(Theme.a, i4), MatrixStackVar);
        if (GuiInput.a(f20 - (f22 / 2.0f), f16 - (5.0f * f6), f17 + f22, f18 + (10.0f * f6), i2, i3) || this.A) {
            GuiInput.g();
        }
    }

    @Override
    public boolean a(float f2, float f3, float f4, int i2, int i3) {
        float f5 = f4 - (8.0f * 2.0f);
        float f6 = f2 + 8.0f;
        this.C = f6;
        this.D = f5;
        if (!GuiInput.a(f6 - 3.5f, (f3 + 20.0f) - 5.0f, f5 + 7.0f, 13.0f, i2, i3)) {
            return false;
        }
        this.A = true;
        if (this.E) {
            this.F = this.n;
        }
        this.t.a(1.0d, 0.1d, Easing.h);
        this.u.a(1.0d, 0.15d, Easing.F);
        this.v.a(1.0d, 0.2d, Easing.h);
        a(f6, f5, i2);
        return true;
    }

    @Override
    public void a(int i2, int i3) {
        if (this.A) {
            if (this.E) {
                this.n = this.F;
                if (this.r != null) {
                    this.r.a(this.n);
                }
                this.w.d(this.G.j());
                this.B = this.n;
                this.H.a(this.n, 0.2d, Easing.C);
            }
            this.A = false;
            this.t.a(0.0d, 0.2d, Easing.h);
            this.u.a(0.0d, 0.25d, Easing.F);
            this.v.a(0.0d, 0.3d, Easing.h);
        }
    }

    @Override
    public void a(int i2, int i3, double d2, double d3) {
        if (this.A) {
            if (this.D > 0.0f) {
                a(this.C, this.D, i2);
            } else if (c) {
            }
        }
    }

    private void a(float f2, float f3, int i2) {
        float fMax = Math.max(this.o, Math.min(this.p, Math.round((this.o + ((this.p - this.o) * Math.max(0.0f, Math.min(1.0f, (i2 - f2) / f3)))) / this.q) * this.q));
        if (this.E) {
            this.F = fMax;
            return;
        }
        this.n = fMax;
        if (this.r != null) {
            this.r.a(fMax);
        }
    }

    @Override
    public boolean d() {
        int i2;
        if (this.r == null || this.r.m()) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        return Bool.from(i2);
    }

    public static String a(String str, String str2, int i2, int i3, int i4, int i5) {
        return null;
    }
}
