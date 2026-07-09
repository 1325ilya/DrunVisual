package drunvisual.gui.settings;

import java.awt.Color;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.settings.BooleanSetting;
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

public class BooleanSettingWidget implements SettingWidget {
    public static final float a = 20.0f;
    private static final float d = 8.0f;
    private static final float e = 18.0f;
    private static final float f = 10.0f;
    private static final float g = 5.0f;
    private static final Color h = Theme.C;
    private static final Color i = Theme.D;
    private final String j;
    private boolean k;
    private final BooleanSetting l;
    private final AnimationState m;
    private final AnimationState n;
    private final MarqueeText o;
    private boolean p;
    private boolean q;
    public static int b;
    public static boolean c;

    public BooleanSettingWidget(String str, boolean z) {
        this(null, str, z);
    }

    public BooleanSettingWidget(BooleanSetting booleanSetting) {
        this(booleanSetting, booleanSetting.f(), booleanSetting.a());
    }

    private BooleanSettingWidget(BooleanSetting booleanSetting, String str, boolean z) {
        this.m = new AnimationState();
        this.n = new AnimationState();
        this.o = new MarqueeText();
        this.p = false;
        this.q = false;
        this.l = booleanSetting;
        this.j = str;
        this.k = z;
        this.q = z;
        this.m.d(!z ? 0.0d : 1.0d);
    }

    @Override
    public String a() {
        return this.j;
    }

    @Override
    public float b() {
        return 20.0f;
    }

    public boolean c() {
        if (this.l != null && this.k != this.l.a()) {
            this.k = this.l.a();
        }
        return this.k;
    }

    public void a(boolean z) {
        this.k = z;
        if (this.l != null) {
            this.l.a(z);
        }
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, int i2, int i3, float f5, float f6) {
        if (this.l != null && this.k != this.l.a()) {
            this.k = this.l.a();
        }
        FontRenderer fontRenderer = FontManager.a[14];
        float f7 = 20.0f * f6;
        float f8 = 8.0f * f6;
        boolean zA = GuiInput.a(f2, f3, f4, f7, i2, i3);
        this.o.a(zA);
        if (zA != this.p) {
            this.n.a(!zA ? 0.0d : 1.0d, 0.15d, Easing.h);
            this.p = zA;
        }
        if (this.k != this.q) {
            this.m.a(!this.k ? 0.0d : 1.0d, 0.2d, Easing.h);
            this.q = this.k;
        }
        this.n.a();
        this.m.a();
        float fJ = (float) this.m.j();
        float f9 = 18.0f * f6;
        float f10 = ((f2 + f4) - f8) - f9;
        this.o.a(MatrixStackVar, renderer2D, fontRenderer, this.j, f2 + f8, (f3 + (f7 / 2.0f)) - ((fontRenderer.b(this.j) * f6) / 4.0f), (f10 - (f2 + f8)) - (4.0f * f6), f6, Theme.a, f5);
        float f11 = f * f6;
        float f12 = (f3 + (f7 / 2.0f)) - (f11 / 2.0f);
        a(MatrixStackVar, renderer2D, f10, f12, fJ, f5, f6);
        if (GuiInput.a(f10, f12, f9, f11, i2, i3)) {
            GuiInput.g();
        }
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5, float f6) {
        int i2 = (int) (255.0f * f5);
        float f7 = 18.0f * f6;
        float f8 = f * f6;
        float f9 = g * f6;
        float f10 = 9.0f * f6;
        float f11 = f2 + (0.5f * f6) + (((f7 - f10) - f6) * f4);
        float f12 = f3 + (0.5f * f6);
        if (f4 > 0.01f) {
            renderer2D.a(f2 + f6, f3 - (3.0f * f6), 8.0f * f6, Theme.a(Theme.E, (int) (255.0f * f4 * 0.5f * f5)), MatrixStackVar);
        }
        Color colorA = ColorUtils.a(Theme.F, h, f4);
        Color colorA2 = ColorUtils.a(Theme.G, i, f4);
        Color colorA3 = Theme.a(colorA, i2);
        Color colorA4 = Theme.a(colorA2, i2);
        renderer2D.a(f2 - (0.5f * f6), f3 - (0.5f * f6), f7 + f6, f8 + f6, f9, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
        Color colorA5 = ColorUtils.a(Theme.f, Theme.y, f4);
        Color colorA6 = ColorUtils.a(Theme.H, Theme.z, f4);
        Color colorA7 = Theme.a(colorA5, i2);
        Color color = new Color(colorA6.getRed(), colorA6.getGreen(), colorA6.getBlue(), Math.min(255, (int) (colorA6.getAlpha() + ((255 - colorA6.getAlpha()) * f4 * f5))));
        renderer2D.a(f2, f3, f7, f8, f9, colorA7, colorA7, color, color, MatrixStackVar);
        renderer2D.a(f11, f12, f10, f10, f10, Theme.a(ColorUtils.a(Theme.b, Theme.a, f4), i2), MatrixStackVar);
    }

    @Override
    public boolean a(float f2, float f3, float f4, int i2, int i3) {
        int i4;
        if (!GuiInput.a(((f2 + f4) - 8.0f) - 18.0f, (f3 + f) - (f / 2.0f), 18.0f, f, i2, i3)) {
            return false;
        }
        if (this.k) {
            i4 = 0;
        } else {
            i4 = 1;
        }
        this.k = Bool.from(i4);
        if (this.l != null) {
            this.l.a(this.k);
        }
        return true;
    }

    @Override
    public boolean d() {
        int i2;
        if (this.l == null || this.l.m()) {
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
