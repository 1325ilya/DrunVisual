package drunvisual.gui.settings;

import java.awt.Color;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.gui.widgets.ColorPickerPopup;
import drunvisual.settings.ColorSetting;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.core.Bool;
import drunvisual.gui.core.GuiInput;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.theme.Theme;
import drunvisual.util.MarqueeText;

public class ColorSettingWidget implements SettingWidget {
    public static final float a = 20.0f;
    private static final float d = 8.0f;
    private static final float e = 14.0f;
    private static final float f = 6.0f;
    private static final float g = 11.0f;
    private static final float h = 4.0f;
    private static final Color i = Theme.V;
    private final String j;
    private float k;
    private float l;
    private float m;
    private final ColorSetting n;
    private final AnimationState o;
    private final MarqueeText p;
    private boolean q;
    private float r;
    private float s;
    private float t;
    private boolean u;
    public static int b;
    public static boolean c;

    public ColorSettingWidget(ColorSetting colorSetting) {
        this.o = new AnimationState();
        this.p = new MarqueeText();
        this.q = false;
        this.t = 1.0f;
        this.u = false;
        this.n = colorSetting;
        this.j = colorSetting.f();
        this.k = colorSetting.c();
        this.l = colorSetting.d();
        this.m = colorSetting.e();
    }

    public ColorSettingWidget(String str, Color color) {
        this.o = new AnimationState();
        this.p = new MarqueeText();
        this.q = false;
        this.t = 1.0f;
        this.u = false;
        this.n = null;
        this.j = str;
        float[] fArrRGBtoHSB = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), (float[]) null);
        this.k = fArrRGBtoHSB[0];
        this.l = fArrRGBtoHSB[1];
        this.m = fArrRGBtoHSB[2];
    }

    public ColorSettingWidget(String str, float f2, float f3, float f4) {
        this.o = new AnimationState();
        this.p = new MarqueeText();
        this.q = false;
        this.t = 1.0f;
        this.u = false;
        this.n = null;
        this.j = str;
        this.k = f2;
        this.l = f3;
        this.m = f4;
    }

    @Override
    public String a() {
        return this.j;
    }

    @Override
    public float b() {
        return 20.0f;
    }

    public Color c() {
        return Color.getHSBColor(this.k, this.l, this.m);
    }

    public void a(Color color) {
        float[] fArrRGBtoHSB = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), (float[]) null);
        this.k = fArrRGBtoHSB[0];
        this.l = fArrRGBtoHSB[1];
        this.m = fArrRGBtoHSB[2];
        if (this.n != null) {
            this.n.a(color);
        }
    }

    public float e() {
        return this.k;
    }

    public float f() {
        return this.l;
    }

    public float g() {
        return this.m;
    }

    public boolean h() {
        if (this.u) {
            if (ColorPickerPopup.a().c()) {
                return true;
            }
        }
        return false;
    }

    public boolean i() {
        return Bool.from((this.u && ColorPickerPopup.a().c()) ? 1 : 0);
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, int i2, int i3, float f5, float f6) {
        FontRenderer fontRenderer = FontManager.a[14];
        float f7 = 20.0f * f6;
        float f8 = 8.0f * f6;
        this.t = f6;
        this.p.a(GuiInput.a(f2, f3, f4, f7, i2, i3));
        this.o.a();
        int i4 = (int) (255.0f * f5);
        float f9 = e * f6;
        float f10 = ((f2 + f4) - f8) - f9;
        this.p.a(MatrixStackVar, renderer2D, fontRenderer, this.j, f2 + f8, (f3 + (f7 / 2.0f)) - ((fontRenderer.b(this.j) * f6) / h), (f10 - (f2 + f8)) - (h * f6), f6, Theme.a, f5);
        float f11 = (f3 + (f7 / 2.0f)) - (f9 / 2.0f);
        this.r = f10;
        this.s = f11;
        boolean zA = GuiInput.a(f10, f11, f9, f9, i2, i3);
        if (zA != this.q) {
            this.o.a(!zA ? 0.0d : 1.0d, 0.15d, Easing.h);
            this.q = zA;
        }
        Color colorC = c();
        renderer2D.a(f10, f11, f9, f9, f * f6, Theme.a(i, i4), MatrixStackVar);
        float f12 = g * f6;
        renderer2D.a(f10 + ((f9 - f12) / 2.0f), f11 + ((f9 - f12) / 2.0f), f12, f12, h * f6, Theme.a(colorC, i4), MatrixStackVar);
        if (zA) {
            GuiInput.g();
        }
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5, int i2, int i3, float f6, float f7) {
    }

    @Override
    public boolean j() {
        return false;
    }

    @Override
    public boolean a(float f2, float f3, float f4, int i2, int i3) {
        float f5 = e * this.t;
        if (!GuiInput.a(this.r, this.s, f5, f5, i2, i3)) {
            return false;
        }
        if (this.u) {
            k();
        } else {
            o();
        }
        return true;
    }

    private void o() {
        float f2 = e * this.t;
        int i2 = (int) (this.r + (f2 / 2.0f));
        int i3 = (int) (this.s + f2 + (5.0f * this.t));
        this.u = true;
        ColorPickerPopup.a().a(i2, i3, ColorPickerPopup.Edge.BOTTOM, this.j, c(), this.t, this::b);
    }

    public void k() {
        this.u = false;
        ColorPickerPopup.a().b();
    }

    private void b(Color color) {
        a(color);
    }

    @Override
    public void a(int i2, int i3) {
    }

    @Override
    public void a(int i2, int i3, double d2, double d3) {
    }

    @Override
    public boolean l() {
        return Bool.from((this.u && ColorPickerPopup.a().c()) ? 1 : 0);
    }

    public void m() {
        if (this.u) {
            k();
        }
    }

    public boolean b(int i2, int i3) {
        return this.u ? ColorPickerPopup.a().c(i2, i3) : false;
    }

    public float[] n() {
        if (this.u) {
            return ColorPickerPopup.a().f();
        }
        return null;
    }

    @Override
    public boolean d() {
        return Bool.from((this.n == null || this.n.m()) ? 1 : 0);
    }

    public static String a(String str, String str2, int i2, int i3, int i4, int i5) {
        return null;
    }
}
