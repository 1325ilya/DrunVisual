package drunvisual.gui.widgets;

import java.awt.Color;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.core.Bool;
import drunvisual.gui.core.GuiInput;
import drunvisual.gui.core.GuiInteractionState;
import drunvisual.render.Renderer2D;
import drunvisual.theme.Theme;
import drunvisual.util.ColorUtils;

public class ScrollBar {
    private static final float c = 2.0f;
    private static final float d = 10.0f;
    private static final float e = 15.0f;
    private static final float f = 25.0f;
    private float g;
    private float h;
    private final AnimationState i;
    private boolean j;
    private float k;
    private float l;
    private final AnimationState m;
    private boolean n;
    private float o;
    private float p;
    private float q;
    private float r;
    private Color s;
    private Color t;
    private float u;
    private float v;
    private float w;
    private float x;
    private float y;
    public static int a;
    public static boolean b;

    public ScrollBar() {
        this.g = 0.0f;
        this.h = 0.0f;
        this.i = new AnimationState();
        this.j = false;
        this.k = 0.0f;
        this.l = 0.0f;
        this.m = new AnimationState();
        this.n = false;
        this.o = c / 2.5f;
        this.p = d;
        this.q = e;
        this.r = 25.0f;
        this.s = Theme.b;
        this.t = Theme.d;
    }

    public ScrollBar(float f2, float f3) {
        this.g = 0.0f;
        this.h = 0.0f;
        this.i = new AnimationState();
        this.j = false;
        this.k = 0.0f;
        this.l = 0.0f;
        this.m = new AnimationState();
        this.n = false;
        this.o = c / 2.5f;
        this.p = d;
        this.q = e;
        this.r = 25.0f;
        this.s = Theme.b;
        this.t = Theme.d;
        this.o = f2 / 2.5f;
        this.r = f3;
    }

    public void a(float f2) {
        this.o = f2;
    }

    public void b(float f2) {
        this.p = f2;
    }

    public void c(float f2) {
        this.q = f2;
    }

    public void d(float f2) {
        this.r = f2;
    }

    public void a(Color color) {
        this.s = color;
    }

    public void b(Color color) {
        this.t = color;
    }

    public void a() {
        if (this.j) {
            return;
        }
        this.i.a();
        this.g = (float) this.i.j();
    }

    public boolean a(float f2, float f3) {
        return Bool.from(f2 <= f3 ? 0 : 1);
    }

    public float b() {
        return this.g;
    }

    public void e(float f2) {
        this.g = f2;
        this.h = f2;
        this.i.d(f2);
    }

    public boolean c() {
        return this.j;
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5, float f6, int i, int i2, boolean z) {
        int i3;
        if (f5 > f6) {
            this.u = f2;
            this.v = f3;
            this.w = f4;
            this.x = f5;
            this.y = f6;
            float f7 = this.g / (f5 - f6);
            float fMax = Math.max(this.q, f4 * (f6 / f5));
            float f8 = f3 + ((f4 - fMax) * f7);
            float renderX = f2 + (c - this.o);
            boolean zB = GuiInteractionState.a().b();
            boolean z2 = (z || zB || !GuiInput.a(renderX - (this.p / c), f3, this.o + this.p, f4, (double) i, (double) i2)) ? false : true;
            if (this.j || z2) {
                i3 = 1;
            } else {
                i3 = 0;
            }
            int i5 = i3;
            if (zB && this.n) {
                this.m.a(0.0d, 0.15d, Easing.h);
                this.n = false;
            } else if (!zB && Bool.from(i5) != this.n) {
                this.m.a(i5 == 0 ? 0.0d : 1.0d, 0.15d, Easing.h);
                this.n = Bool.from(i5);
            }
            this.m.a();
            renderer2D.a(renderX, f8, this.o, fMax, this.o / c, Color.WHITE, MatrixStackVar);
        }
    }

    public boolean a(float f2, float f3, float f4, float f5, float f6, int i, int i2) {
        float renderX = f2 + (c - this.o);
        if (f5 > f6 && GuiInput.a(renderX - (this.p / c), f3, this.o + this.p, f4, i, i2)) {
            this.j = true;
            this.k = i2;
            this.l = this.g;
            return true;
        }
        return false;
    }

    public void d() {
        if (this.j) {
            this.j = false;
            this.h = this.g;
            this.i.d(this.g);
        }
    }

    public void a(int i, float f2, float f3) {
        if (!this.j || f2 <= f3) {
            return;
        }
        float f4 = f2 - f3;
        float f5 = this.w;
        this.g = this.l + (((i - this.k) / (f5 - Math.max(this.q, f5 * (f3 / f2)))) * f4);
        float fMax = Math.max(0.0f, Math.min(this.g, f4));
        this.g = fMax;
        this.h = fMax;
        this.i.d(this.g);
    }

    public void a(float f2, float f3, float f4) {
        if (this.j || f3 <= f4) {
            return;
        }
        float fMax = Math.max(0.0f, f3 - f4);
        this.h -= f2 * this.r;
        this.h = Math.max(0.0f, Math.min(this.h, fMax));
        this.i.a(this.h, 0.15d, Easing.h);
    }

    public void e() {
        this.g = 0.0f;
        this.h = 0.0f;
        this.i.d(0.0d);
        this.j = false;
    }

    public void b(float f2, float f3) {
        if (f2 <= f3) {
            if (this.g > 0.0f || this.h > 0.0f) {
                this.h = 0.0f;
                this.i.a(0.0d, 0.15d, Easing.h);
                return;
            }
            return;
        }
        float f4 = f2 - f3;
        if (this.g > f4 || this.h > f4) {
            this.h = f4;
            this.i.a(f4, 0.15d, Easing.h);
        }
    }

    public static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
