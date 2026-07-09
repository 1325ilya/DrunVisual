package drunvisual.gui.config;

import java.awt.Color;
import java.util.function.Consumer;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.gui.widgets.TextInput;
import drunvisual.client.MinecraftContext;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.core.Bool;
import drunvisual.gui.core.GuiInput;
import drunvisual.gui.core.GuiInteractionState;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.theme.Theme;
import drunvisual.util.ColorUtils;

public class ConfigNameDialog {
    private static final float c = 178.5f;
    private static final float d = 94.0f;
    private static final float e = 9.5f;
    private static final float f = 12.0f;
    private static final float g = 3.0f;
    private static final float h = 11.5f;
    private static final float i = 22.0f;
    private static final float j = 9.5f;
    private static final float k = 18.0f;
    private static final float l = 6.0f;
    private static final float m = 7.0f;
    private boolean n = false;
    private final AnimationState o = new AnimationState();
    private final AnimationState p = new AnimationState();
    private final AnimationState q = new AnimationState();
    private boolean r = false;
    private boolean s = false;
    private final TextInput t = new TextInput(TextInput.InputType.KEY, "", "XXXX-XXXX-XXXX");
    private Consumer<String> u;
    private Runnable v;
    public static int a;
    public static boolean b;

    public ConfigNameDialog() {
        this.t.a(9.5f);
        this.t.a(Theme.S, Theme.T);
        this.t.a(true);
        this.t.b(true);
    }

    public void a() {
        this.n = true;
        this.o.d(0.0d);
        this.o.a(1.0d, 0.25d, Easing.F);
        this.t.b("");
        GuiInteractionState.a().e(true);
        GuiInput.a(0.0f, 0.0f, 10000.0f, 10000.0f);
    }

    public void b() {
        if (this.n) {
            this.o.a(0.0d, 0.15d, Easing.h);
            this.t.c(false);
            GuiInteractionState.a().e(false);
            GuiInput.a();
        }
    }

    public boolean c() {
        return this.n;
    }

    public boolean d() {
        return (!this.n || this.o.j() < 0.01d) ? true : false;
    }

    public void a(Consumer<String> consumer) {
        this.u = consumer;
    }

    public void a(Runnable runnable) {
        this.v = runnable;
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, int i2, int i3) {
        this.o.a();
        this.p.a();
        this.q.a();
        float fJ = (float) this.o.j();
        if (fJ < 0.01f) {
            if (this.n && this.o.d()) {
                this.n = false;
                if (this.v != null) {
                    this.v.run();
                    return;
                }
                return;
            }
            return;
        }
        int i4 = (int) (255.0f * fJ);
        float f4 = (f2 - c) / 2.0f;
        float f5 = (f3 - d) / 2.0f;
        float f6 = 0.9f + (0.1f * fJ);
        float f7 = c * f6;
        float f8 = d * f6;
        float f9 = f4 + ((c - f7) / 2.0f);
        float f10 = f5 + ((d - f8) / 2.0f);
        Color colorA = Theme.a(new Color(17, 17, 23, 204), i4);
        Color colorA2 = Theme.a(new Color(13, 13, 17, 204), i4);
        renderer2D.a(f9, f10, f7, f8, 9.5f * f6, colorA, colorA, colorA2, colorA2, MatrixStackVar);
        FontRenderer fontRenderer = FontManager.b[15];
        float fA = f9 + ((f7 - fontRenderer.a("Активация конфига")) / 2.0f);
        float f11 = f10 + (g * f6);
        fontRenderer.a("Активация конфига", fA, f11 + 5.0f, Theme.a(Theme.a, i4), MatrixStackVar);
        float f12 = f9 + (f * f6);
        float fB = f11 + fontRenderer.b("Активация конфига") + (h * f6);
        float f13 = f7 - (24.0f * f6);
        float f14 = i * f6;
        this.t.a(MatrixStackVar, renderer2D, f12, fB, f13, f14, i2, i3, fJ);
        float f15 = fB + f14 + (h * f6);
        float f16 = (f13 - (7.0f * f6)) / 2.0f;
        float f17 = f12 + f16 + (7.0f * f6);
        boolean zA = GuiInput.a(f12, f15, f16, 18.0f * f6, i2, i3);
        boolean zA2 = GuiInput.a(f17, f15, f16, 18.0f * f6, i2, i3);
        if (zA != this.r) {
            this.p.a(!zA ? 0.0d : 1.0d, 0.15d, Easing.h);
            this.r = zA;
        }
        if (zA2 != this.s) {
            this.q.a(!zA2 ? 0.0d : 1.0d, 0.15d, Easing.h);
            this.s = zA2;
        }
        a(MatrixStackVar, renderer2D, f12, f15, f16, 18.0f * f6, i4);
        b(MatrixStackVar, renderer2D, f17, f15, f16, 18.0f * f6, i4);
        if (zA || zA2) {
            GuiInput.g();
        }
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5, int i2) {
        float fJ = (float) this.p.j();
        Color colorA = ColorUtils.a(Theme.y, Theme.B, fJ);
        Color colorA2 = ColorUtils.a(Theme.z, Theme.y, fJ);
        Color colorA3 = Theme.a(colorA, i2);
        Color colorA4 = Theme.a(colorA2, i2);
        renderer2D.a(f2, f3, f4, f5, l, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
        FontRenderer fontRendererApply = FontManager.a[12];
        fontRendererApply.a("Активировать", f2 + ((f4 - fontRendererApply.a("Активировать")) / 2.0f), f3 + ((f5 - fontRendererApply.b("Активировать")) / 2.0f) + 3.5f, Theme.a(Theme.aa, i2), MatrixStackVar);
    }

    private void b(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5, int i2) {
        float fJ = (float) this.q.j();
        Color colorB = Theme.b(Theme.q, i2 / 255.0f);
        Color colorB2 = Theme.b(Theme.n, i2 / 255.0f);
        renderer2D.a(f2 - 0.5f, f3 - 0.5f, f4 + 1.0f, f5 + 1.0f, l, colorB, colorB, colorB2, colorB2, MatrixStackVar);
        Color colorA = ColorUtils.a(Theme.e, Theme.I, fJ);
        Color colorA2 = ColorUtils.a(Theme.f, Theme.J, fJ);
        Color colorA3 = Theme.a(colorA, i2);
        Color colorA4 = Theme.a(colorA2, i2);
        renderer2D.a(f2, f3, f4, f5, l, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
        FontRenderer fontRendererCancel = FontManager.a[12];
        fontRendererCancel.a("Отмена", f2 + ((f4 - fontRendererCancel.a("Отмена")) / 2.0f), f3 + ((f5 - fontRendererCancel.b("Отмена")) / 2.0f) + 3.5f, Theme.a(ColorUtils.a(Theme.b, Theme.a, fJ), i2), MatrixStackVar);
    }

    public boolean a(float f2, float f3, int i2, int i3) {
        if (!this.n || this.o.j() < 0.5d) {
            return false;
        }
        float fGetWidth = ((MinecraftContext.d.getWidth() / 2.0f) - c) / 2.0f;
        float fGetHeight = ((MinecraftContext.d.getHeight() / 2.0f) - d) / 2.0f;
        if (!GuiInput.a(fGetWidth, fGetHeight, c, d, i2, i3)) {
            b();
            return true;
        }
        if (this.t.a(i2, i3)) {
            return true;
        }
        FontRenderer fontRenderer = FontManager.b[15];
        float f4 = fGetWidth + f;
        float fB = fGetHeight + g + fontRenderer.b("Активация конфига") + h + i + h;
        float f5 = (154.5f - 7.0f) / 2.0f;
        float f6 = f4 + f5 + 7.0f;
        if (GuiInput.a(f4, fB, f5, 18.0f, i2, i3)) {
            if (this.u != null) {
                this.u.accept(this.t.a());
            }
            b();
            return true;
        }
        if (!GuiInput.a(f6, fB, f5, 18.0f, i2, i3)) {
            return true;
        }
        b();
        return true;
    }

    public boolean a(int i2, int i3, int i4) {
        return this.n ? this.t.b(i2, i3, i4) : false;
    }

    public boolean a(char c2, int i2) {
        return this.n ? this.t.a(c2, i2) : false;
    }

    public boolean e() {
        return Bool.from((this.n && this.t.d()) ? 1 : 0);
    }

    public static String a(String str, String str2, int i2, int i3, int i4, int i5) {
        return null;
    }
}
