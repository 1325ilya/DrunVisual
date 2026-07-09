package drunvisual.hud.notifications;

import java.awt.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.render.Renderer2D;

public abstract class HudNotification {
    protected static final MinecraftClient c = MinecraftClient.getInstance();
    protected final AnimationState d = new AnimationState();
    protected final AnimationState e = new AnimationState();
    protected final AnimationState f = new AnimationState();
    protected float g = 8.0f;
    protected float h = 4.0f;
    protected float i = 12.0f;
    protected long j = 1000;
    protected boolean l = false;
    protected long k = System.currentTimeMillis();

    public HudNotification() {
        this.f.d(0.0d);
        this.f.a(1.0d, 0.2d, Easing.k);
    }

    public abstract float a();

    public abstract float b();

    public abstract void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, float f3, float f4, float f5);

    public float c() {
        return this.g + a() + this.g;
    }

    public float d() {
        return this.h + b() + this.h;
    }

    public void e() {
        this.d.a();
        this.e.a();
        this.f.a();
        if (this.l || System.currentTimeMillis() - this.k <= this.j) {
            return;
        }
        f();
    }

    public void f() {
        if (this.l) {
            return;
        }
        this.l = true;
        this.f.a(0.0d, 0.2d, Easing.j);
    }

    public boolean g() {
        return (!this.l || this.f.j() > 0.01d) ? false : true;
    }

    public boolean h() {
        return this.l;
    }

    public float i() {
        return (float) this.f.j();
    }

    public float j() {
        return this.g;
    }

    public float k() {
        return this.h;
    }

    public float l() {
        return this.i;
    }

    protected Color a(Color color, float f) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (color.getAlpha() * f));
    }

    public static String b(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
