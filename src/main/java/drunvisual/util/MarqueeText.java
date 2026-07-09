package drunvisual.util;

import java.awt.Color;
import lombok.Generated;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontRenderer;

public class MarqueeText {
    public static final long a = 750;
    public static final long b = 500;
    public static final long c = 2500;
    public static final float d = 0.2f;
    private long g = 0;
    private boolean h = false;
    private boolean i = false;
    private float j = 0.0f;
    private final AnimationState k = new AnimationState();
    public static int e;

    public float a(String str, FontRenderer fontRenderer, float f2, float f3) {
        float fJ;
        float fA = fontRenderer.a(str) * f3;
        if (!this.h || fA <= f2) {
            return 0.0f;
        }
        float f4 = 0.0f;
        if (fA > f2) {
            long jCurrentTimeMillis = (System.currentTimeMillis() - this.g) % c;
            f4 = (-(fA - f2)) * (jCurrentTimeMillis >= 750 ? jCurrentTimeMillis >= 1250 ? jCurrentTimeMillis >= 2000 ? 0.0f : 1.0f - ((jCurrentTimeMillis - 1250) / 750.0f) : 1.0f : jCurrentTimeMillis / 750.0f);
        }
        if (this.i) {
            this.k.a();
            fJ = (float) this.k.j();
            if (this.k.d()) {
                this.h = false;
                this.i = false;
                fJ = 0.0f;
            }
        } else {
            this.j = f4;
            fJ = f4;
        }
        return fJ;
    }

    public void a(boolean z) {
        if (z && !this.h) {
            this.h = true;
            this.g = System.currentTimeMillis();
            this.i = false;
        }
        if (z || !this.h || this.i) {
            return;
        }
        this.i = true;
        this.k.d(this.j);
        this.k.a(0.0d, 0.20000000298023224d, Easing.h);
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, FontRenderer fontRenderer, String str, float f2, float f3, float f4, float f5, Color color, float f6) {
        float fA = fontRenderer.a(str) * f5;
        float fA2 = a(str, fontRenderer, f4, f5);
        Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (255.0f * f6));
        if (fA > f4) {
            renderer2D.b().a(f2, f3 - 5.0f, f4, 20.0f, MatrixStackVar);
        }
        MatrixStackVar.push();
        MatrixStackVar.translate(f2 + fA2, f3, 0.0f);
        MatrixStackVar.scale(f5, f5, 1.0f);
        MatrixStackVar.translate(-(f2 + fA2), -f3, 0.0f);
        fontRenderer.a(str, f2 + fA2, f3, color2, MatrixStackVar);
        MatrixStackVar.pop();
        if (fA > f4) {
            renderer2D.b().a(MatrixStackVar);
        }
    }

    @Generated
    public long a() {
        return this.g;
    }

    @Generated
    public boolean b() {
        return this.h;
    }

    @Generated
    public boolean c() {
        return this.i;
    }

    @Generated
    public float d() {
        return this.j;
    }

    @Generated
    public AnimationState e() {
        return this.k;
    }

    @Generated
    public void b(boolean z) {
        this.h = z;
    }

    @Generated
    public void c(boolean z) {
        this.i = z;
    }

    @Generated
    public void a(float f2) {
        this.j = f2;
    }

    public static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
