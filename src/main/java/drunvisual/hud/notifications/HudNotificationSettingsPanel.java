package drunvisual.hud.notifications;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import net.minecraft.util.Identifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import drunvisual.render.icons.IconTextureRegistry;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.config.ConfigManager;
import drunvisual.core.Bool;
import drunvisual.gui.core.GuiInput;
import drunvisual.gui.core.PanelFadeOverlay;
import drunvisual.gui.settings.BooleanSettingWidget;
import drunvisual.gui.settings.SettingWidgetList;
import drunvisual.render.Renderer2D;
import drunvisual.theme.Theme;

public class HudNotificationSettingsPanel {
    public static final float a = 150.0f;
    public static final float b = 30.0f;
    public static final float c = 120.0f;
    public static final float d = 9.5f;
    public static final float e = 7.0f;
    private static final int h = 25;
    private static final float i = 5.0f;
    private float n;
    private float o;
    private float p;
    private float q;
    private float r;
    private float s;
    private final PanelFadeOverlay u;
    private final BooleanSettingWidget x;
    private final BooleanSettingWidget y;
    private final BooleanSettingWidget z;
    private final BooleanSettingWidget A;
    private final BooleanSettingWidget B;
    public static int f;
    public static boolean g;
    private final AnimationState j = new AnimationState();
    private boolean k = false;
    private boolean l = false;
    private float m = 150.0f;
    private final AnimationState v = new AnimationState();
    private boolean w = true;
    private final SettingWidgetList t = new SettingWidgetList();

    public HudNotificationSettingsPanel() {
        this.t.b(7.0f);
        this.t.a(120.0f);
        this.x = new BooleanSettingWidget("Уведомления", true);
        this.y = new BooleanSettingWidget("Музыка", true);
        this.z = new BooleanSettingWidget("FPS & Ping", true);
        this.A = new BooleanSettingWidget("Ивенты", true);
        this.B = new BooleanSettingWidget("Функции", true);
        this.t.a(this.x);
        this.t.a(this.y);
        this.t.a(this.z);
        this.t.a(this.A);
        this.t.a(this.B);
        this.u = new PanelFadeOverlay(h, i, 9.5f);
        this.j.d(0.0d);
        this.v.d(1.0d);
    }

    public void a(float f2, float f3, float f4, float f5) {
        a(f2, f3, f4, f5, 150.0f);
    }

    public void a(float f2, float f3, float f4, float f5, float f6) {
        if (this.k && !this.l) {
            b();
            return;
        }
        this.p = f2;
        this.q = f3;
        this.r = f4;
        this.s = f5;
        this.m = f6;
        this.n = (f2 + (f4 / 2.0f)) - (this.m / 2.0f);
        this.o = f3 + f5 + 11.0f;
        this.k = true;
        this.l = false;
        this.j.a(1.0d, 0.25d, Easing.F);
    }

    public void a(float f2) {
        this.m = f2;
    }

    public float a() {
        return this.m;
    }

    public void b() {
        if (this.k) {
            this.l = true;
            this.t.g();
            this.j.a(0.0d, 0.15d, Easing.g);
        }
    }

    public void c() {
        this.k = false;
        this.l = false;
        this.j.d(0.0d);
        this.t.g();
    }

    public boolean d() {
        return this.k;
    }

    public boolean e() {
        return (this.l && this.j.d() && this.j.j() < 0.01d) ? true : false;
    }

    public void f() {
        this.j.a();
        this.v.a();
        if (e()) {
            this.k = false;
            this.l = false;
        }
    }

    public float g() {
        return Math.max(30.0f, Math.min(this.t.m(), 120.0f));
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, int i2, int i3) {
        int i4 = 0;
        if (this.k) {
            f();
            float fJ = (float) this.j.j();
            if (fJ >= 0.01f) {
                float fG = g();
                float fMax = Math.max(0.0f, Math.min(1.0f, fJ));
                int i5 = (int) (255.0f * fMax);
                float f2 = this.n + (this.m / 2.0f);
                float f3 = this.o + (fG / 2.0f);
                float f4 = this.m * fMax;
                float f5 = fG * fMax;
                float f6 = f2 - (f4 / 2.0f);
                float f7 = f3 - (f5 / 2.0f);
                Color colorA = Theme.a(Theme.q, i5);
                Color colorA2 = Theme.a(Theme.r, i5);
                renderer2D.a(f6 - (0.5f * fMax), f7 - (0.5f * fMax), f4 + fMax, f5 + fMax, 9.5f * fMax, colorA, colorA, colorA2, colorA2, MatrixStackVar);
                Color colorA3 = Theme.a(Theme.e, i5);
                Color colorA4 = Theme.a(Theme.f, i5);
                renderer2D.a(f6, f7, f4, f5, 9.5f * fMax, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
                IconTextureRegistry.TextureInfo info = IconTextureRegistry.getInfo(IconTextureRegistry.ARROW_V);
                if (info != null) {
                    Identifier IdentifierVarA = info.a();
                    RenderSystem.setShaderTexture(0, IdentifierVarA);
                    float fB = (info.b() / 2.0f) * fMax;
                    float fC = (info.c() / 2.0f) * fMax;
                    float f8 = f6 + (f4 / 2.0f);
                    float f9 = (f7 - (fB / 2.0f)) - 1.0f;
                    Color colorA5 = Theme.a(Theme.aa, i5);
                    MatrixStackVar.push();
                    MatrixStackVar.translate(f8, f9, 0.0f);
                    MatrixStackVar.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-90.0f));
                    MatrixStackVar.translate(-f8, -f9, 0.0f);
                    renderer2D.a(IdentifierVarA, (f8 - (fB / 2.0f)) - 0.5f, f9 - (fC / 2.0f), fB, fC, colorA5, MatrixStackVar);
                    MatrixStackVar.pop();
                }
                if (fMax > 0.1f) {
                    renderer2D.b().a(f6, f7 + (7.0f * fMax), f4, ((f5 - 7.0f) - 6.0f) * fMax, 9.5f * fMax, MatrixStackVar);
                    if (this.l) {
                        i4 = -1;
                    } else {
                        i4 = i2;
                    }
                    this.t.a(MatrixStackVar, renderer2D, f6, f7, f4, f5, i4, !this.l ? i3 : -1, fMax, fMax);
                    int i7 = (!this.t.d(f5) || this.t.e(f7 + f5)) ? 0 : 1;
                    if (Bool.from(i7) != this.w) {
                        this.v.a(i7 == 0 ? 0.0d : 1.0d, 0.15d, Easing.h);
                        this.w = Bool.from(i7);
                    }
                    float fJ2 = (float) this.v.j();
                    if (fJ2 > 0.01f) {
                        this.u.a(MatrixStackVar, renderer2D, f6, f7, f4, f5, fMax * fJ2);
                    }
                    renderer2D.b().a(MatrixStackVar);
                }
            }
        }
    }

    public boolean a(int i2, int i3) {
        if (!this.k || this.l || this.j.j() < 0.5d) {
            return false;
        }
        if (d(i2, i3)) {
            boolean zA = this.t.a(this.n, this.o, this.m, g(), i2, i3);
            if (zA) {
                ConfigManager.a().h();
            }
            return zA;
        }
        if (!this.t.d()) {
            return false;
        }
        this.t.g();
        return true;
    }

    public boolean b(int i2, int i3) {
        if (!this.k || this.l || this.j.j() < 0.5d) {
            return false;
        }
        if (d(i2, i3)) {
            if (!this.t.d()) {
                return this.t.b(this.n, this.o, this.m, g(), i2, i3);
            }
            this.t.g();
            return true;
        }
        if (!this.t.d()) {
            return false;
        }
        this.t.g();
        return true;
    }

    public void c(int i2, int i3) {
        if (this.k) {
            this.t.c(i2, i3);
        }
    }

    public void a(int i2, int i3, double d2, double d3) {
        if (!this.k || this.l) {
            return;
        }
        this.t.a(g(), i2, i3, d2, d3);
    }

    public boolean a(float f2, int i2, int i3) {
        if (!this.k || this.l) {
            return false;
        }
        if (!d(i2, i3)) {
            return false;
        }
        this.t.a(f2, g());
        return true;
    }

    public boolean d(int i2, int i3) {
        return (!this.k || this.j.j() < 0.5d) ? false : GuiInput.a(this.n, this.o, this.m, g(), i2, i3);
    }

    public void b(float f2, float f3, float f4, float f5) {
        this.p = f2;
        this.q = f3;
        this.r = f4;
        this.s = f5;
    }

    public boolean h() {
        return this.x.c();
    }

    public boolean i() {
        return this.y.c();
    }

    public boolean j() {
        return this.z.c();
    }

    public boolean k() {
        return this.A.c();
    }

    public boolean l() {
        return this.B.c();
    }

    public void a(boolean z) {
        this.x.a(z);
    }

    public void b(boolean z) {
        this.y.a(z);
    }

    public void c(boolean z) {
        this.z.a(z);
    }

    public void d(boolean z) {
        this.A.a(z);
    }

    public void e(boolean z) {
        this.B.a(z);
    }

    public static String a(String str, String str2, int i2, int i3, int i4, int i5) {
        return null;
    }
}
