package drunvisual.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.util.Identifier;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Quaternionf;
import drunvisual.render.icons.IconTextureRegistry;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.config.ConfigState;
import drunvisual.gui.core.GuiInput;
import drunvisual.gui.friends.FriendCard;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.theme.Theme;

public class ColorPickerPopup {
    private static final ColorPickerPopup c = new ColorPickerPopup();
    private static final float d = 84.5f;
    private static final float e = 10.5f;
    private static final float f = 7.0f;
    private static final float g = 9.5f;
    private static final float h = 63.5f;
    private static final float i = 41.5f;
    private static final float j = 7.0f;
    private static final float k = 4.0f;
    private static final float l = 2.0f;
    private static final float m = 9.0f;
    private static final float n = 1.5f;
    private static final float o = 3.0f;
    private static final float p = 7.0f;
    private static final float q = 1.0f;
    private float t;
    private float u;
    private float y;
    private float z;
    private float A;
    private Consumer<Color> B;
    private float E;
    private float F;
    private float G;
    private float H;
    private float I;
    private float J;
    private float K;
    private float L;
    private float M;
    private float N;
    private float O;
    private float P;
    private float Q;
    private float R;
    private float S;
    public static int a;
    public static boolean b;
    private boolean r = false;
    private final AnimationState s = new AnimationState();
    private Edge v = Edge.BOTTOM;
    private String w = "";
    private float x = q;
    private boolean C = false;
    private boolean D = false;
    private String[] modes;
    private int modeIdx = -1;
    private IntConsumer modeCB;
    private float modesY;
    private float modesH;
    private static final float MODE_ROW_H = 13.0f;
    private static final float MODE_GAP = 4.0f;

    public enum Edge {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT;

        public static int e;
        public static boolean f;

        public static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return str;
        }
    }

    private ColorPickerPopup() {
    }

    public static ColorPickerPopup a() {
        return c;
    }

    public void a(float f2, float f3, Edge edge, String str, Color color, float f4, Consumer<Color> consumer) {
        a(f2, f3, edge, str, color, f4, consumer, null, -1, null);
    }

    public void a(float f2, float f3, Edge edge, String str, Color color, float f4, Consumer<Color> consumer, String[] strArr, int i2, IntConsumer intConsumer) {
        if (this.r) {
            b();
        }
        this.t = f2;
        this.u = f3;
        this.v = edge != null ? edge : Edge.BOTTOM;
        this.w = str != null ? str : "";
        this.x = f4;
        this.B = consumer;
        this.modes = strArr;
        this.modeIdx = i2;
        this.modeCB = intConsumer;
        float[] fArrRGBtoHSB = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), (float[]) null);
        this.y = fArrRGBtoHSB[0];
        this.z = fArrRGBtoHSB[1];
        this.A = fArrRGBtoHSB[2];
        this.r = true;
        this.s.d(0.0d);
        this.s.a(1.0d, 0.2d, Easing.h);
    }

    public void a(float f2, float f3, String str, Color color, float f4, Consumer<Color> consumer) {
        a(f2, f3, Edge.BOTTOM, str, color, f4, consumer);
    }

    public void b() {
        if (this.r) {
            this.r = false;
            this.s.a(0.0d, 0.15d, Easing.h);
            this.C = false;
            this.D = false;
            this.modes = null;
            this.modeIdx = -1;
            this.modeCB = null;
        }
    }

    public boolean c() {
        return (this.r || this.s.j() > 0.01d) ? true : false;
    }

    public boolean d() {
        return (!this.r || this.s.j() <= 0.5d) ? false : true;
    }

    public Color e() {
        return Color.getHSBColor(this.y, this.z, this.A);
    }

    public float[] f() {
        if (!c()) {
            return null;
        }
        float fJ = (float) this.s.j();
        float f2 = this.G * fJ;
        float f3 = this.H * fJ;
        return new float[]{this.I - (f2 / l), this.J - (f3 / l), f2, f3};
    }

    private float a(float f2, float f3, float f4) {
        return f3 + ((f2 - f3) * f4);
    }

    private float a(float f2, float f3) {
        return f2 * f3;
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, int i2, int i3, float f2) {
        this.s.a();
        float fJ = (float) this.s.j();
        this.S = fJ;
        if (fJ >= 0.01f) {
            FontRenderer fontRenderer = FontManager.a[14];
            float f3 = d * this.x;
            float f4 = e * this.x;
            float f5 = 7.0f * this.x;
            float f6 = 9.5f * this.x;
            float f7 = h * this.x;
            float f8 = i * this.x;
            float f9 = k * this.x;
            float fB = (fontRenderer.b(this.w) * this.x) + (l * this.x);
            this.modesH = (this.modes == null || this.modes.length == 0) ? 0.0f : ((this.modes.length * MODE_ROW_H * this.x) + (MODE_GAP * this.x));
            float f10 = fB + this.modesH + f8 + f5 + f9 + (f4 * l);
            this.G = f3;
            this.H = f10;
            b(f3, f10);
            this.I = this.E + (f3 / l);
            this.J = this.F + (f10 / l);
            float fA = a(this.E, this.I, fJ);
            float fA2 = a(this.F, this.J, fJ);
            float fA3 = a(f3, fJ);
            float fA4 = a(f10, fJ);
            float fA5 = a(f6, fJ);
            a(f4, fJ);
            a(f5, fJ);
            a(MatrixStackVar, renderer2D, fJ, f2);
            Color colorA = a(Theme.x, f2 * fJ);
            Color colorA2 = a(Theme.x, f2 * fJ);
            renderer2D.a(fA - ((0.5f * this.x) * fJ), fA2 - ((0.5f * this.x) * fJ), fA3 + (this.x * fJ), fA4 + (this.x * fJ), fA5, colorA, colorA, colorA2, colorA2, MatrixStackVar);
            Color colorA3 = a(Theme.e, f2 * fJ);
            Color colorA4 = a(Theme.f, f2 * fJ);
            renderer2D.a(fA, fA2, fA3, fA4, fA5, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
            if (fJ > 0.3f) {
                float fMin = Math.min(q, (fJ - 0.3f) / 0.7f);
                int i4 = (int) (255.0f * f2 * fMin);
                renderer2D.b().a(fA, fA2, fA3, fA4, fA5, MatrixStackVar);
                float f11 = this.E + f4;
                float f12 = this.F + f4;
                float fA6 = a(f11, this.I, fJ);
                float fA7 = a(f12, this.J, fJ);
                a(fB, fJ);
                float f13 = this.x * fJ;
                Color colorA5 = Theme.a(Theme.a, i4);
                MatrixStackVar.push();
                MatrixStackVar.translate(fA6, fA7, 0.0f);
                MatrixStackVar.scale(f13, f13, q);
                MatrixStackVar.translate(-fA6, -fA7, 0.0f);
                fontRenderer.a(this.w, fA6, fA7, colorA5, MatrixStackVar);
                MatrixStackVar.pop();
                float f14 = f12 + fB + this.modesH;
                this.modesY = f12 + fB;
                if (this.modes != null && this.modes.length > 0 && fMin > 0.01f) {
                    drawModes(MatrixStackVar, renderer2D, fontRenderer, f11, this.I, this.modesY, this.J, fJ, fMin * f2, f4, i4);
                }
                this.K = a(f11, this.I, fJ);
                this.L = a(f14, this.J, fJ);
                this.M = a(f7, fJ);
                this.N = a(f8, fJ);
                a(MatrixStackVar, renderer2D, this.K, this.L, this.M, this.N, a(7.0f * this.x, fJ), fMin * f2);
                a(MatrixStackVar, renderer2D, a(f11 + (this.z * f7), this.I, fJ), a(f14 + ((q - this.A) * f8), this.J, fJ), fJ, fMin * f2);
                float f15 = f14 + f8 + f5;
                this.O = a(f11, this.I, fJ);
                this.P = a(f15, this.J, fJ);
                this.Q = a(f7, fJ);
                this.R = a(f9, fJ);
                b(MatrixStackVar, renderer2D, this.O, this.P, this.Q, this.R, a(l * this.x, fJ), fMin * f2);
                b(MatrixStackVar, renderer2D, a(f11 + (this.y * f7), this.I, fJ), a(f15 + (f9 / l), this.J, fJ), fJ, fMin * f2);
                renderer2D.b().a(MatrixStackVar);
            }
            if (!this.r) {
                if (1703664633 < a) {
                    return;
                }
                return;
            }
            if (fJ > 0.8f) {
                boolean zA = GuiInput.a(this.K, this.L, this.M, this.N, i2, i3);
                boolean zA2 = GuiInput.a(this.O, this.P - ((k * this.x) * fJ), this.Q, this.R + (8.0f * this.x * fJ), i2, i3);
                if (zA || zA2 || this.C || this.D) {
                    GuiInput.g();
                }
            }
        }
    }

    private void b(float f2, float f3) {
        IconTextureRegistry.TextureInfo arrowInfo = IconTextureRegistry.getInfo(IconTextureRegistry.ARROW_V);
        float fB = arrowInfo != null ? (arrowInfo.b() / l) * this.x : 0.0f;
        switch (this.v.ordinal()) {
            case EventPriority.MEDIUM /* 0 */:
                this.E = this.t - (f2 / l);
                this.F = ((this.u - f3) - fB) - (q * this.x);
                break;
            case ConfigState.a /* 1 */:
                this.E = this.t - (f2 / l);
                this.F = this.u + fB + (q * this.x);
                break;
            case 2:
                this.E = ((this.t - f2) - fB) - (q * this.x);
                this.F = this.u - (f3 / l);
                break;
            case 3:
                this.E = this.t + fB + (q * this.x);
                this.F = this.u - (f3 / l);
                break;
        }
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3) {
        float f4;
        float f5;
        float f6;
        IconTextureRegistry.TextureInfo info = IconTextureRegistry.getInfo(IconTextureRegistry.ARROW_V);
        if (info != null) {
            Identifier IdentifierVarA = info.a();
            RenderSystem.setShaderTexture(0, IdentifierVarA);
            float fB = (info.b() / l) * this.x * f2;
            float fC = (info.c() / l) * this.x * f2;
            Color colorA = Theme.a(Theme.aa, f3 * f2);
            float fA = a(this.E, this.I, f2);
            float fA2 = a(this.F, this.J, f2) + 2.5f;
            float fA3 = a(this.G, f2);
            float fA4 = a(this.H, f2);
            float f7 = q * this.x * f2;
            switch (this.v.ordinal()) {
                case EventPriority.MEDIUM /* 0 */:
                    f4 = fA + (fA3 / l);
                    f5 = fA2 + fA4 + (fC / l) + f7;
                    f6 = 90.0f;
                    break;
                case ConfigState.a /* 1 */:
                    f4 = fA + (fA3 / l);
                    f5 = (fA2 - (fC / l)) - f7;
                    f6 = -90.0f;
                    break;
                case 2:
                    f4 = fA + fA3 + (fB / l) + f7;
                    f5 = fA2 + (fA4 / l);
                    f6 = 0.0f;
                    break;
                default:
                    f4 = (fA - (fB / l)) - f7;
                    f5 = fA2 + (fA4 / l);
                    f6 = 180.0f;
                    break;
            }
            MatrixStackVar.push();
            MatrixStackVar.translate(f4, f5, 0.0f);
            MatrixStackVar.multiply(new Quaternionf().rotationZ((float) Math.toRadians(f6)));
            MatrixStackVar.translate(-f4, -f5, 0.0f);
            renderer2D.a(IdentifierVarA, f4 - (fB / l), f5 - (fC / l), fB, fC, colorA, MatrixStackVar);
            MatrixStackVar.pop();
        }
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5, float f6, float f7) {
        Color hSBColor = Color.getHSBColor(this.y, q, q);
        int i2 = (int) (255.0f * f7);
        Color colorA = Theme.a(Theme.aa, i2);
        Color colorA2 = Theme.a(hSBColor, i2);
        Color colorA3 = Theme.a(Theme.FriendCard, i2);
        renderer2D.a(f2, f3, f4, f5, f6, colorA, colorA2, colorA3, colorA3, MatrixStackVar);
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5) {
        float f6 = m * this.x * f4;
        float f7 = f6 - (((n * this.x) * f4) * l);
        int i2 = (int) (255.0f * f5);
        renderer2D.a((f2 - (f7 / l)) - l, (f3 - (f7 / l)) - l, 5.0f, Theme.ad, MatrixStackVar);
        renderer2D.a(f2 - (f6 / l), f3 - (f6 / l), f6, f6, f6, Theme.a(Theme.aa, i2), MatrixStackVar);
        renderer2D.a(f2 - (f7 / l), f3 - (f7 / l), f7, f7, f7, Theme.a(e(), i2), MatrixStackVar);
    }

    private void b(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5, float f6, float f7) {
        renderer2D.a(f2, f3, f4, f5, f6, f7, MatrixStackVar);
    }

    private void b(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5) {
        float f6 = o * this.x * f4;
        float f7 = 7.0f * this.x * f4;
        renderer2D.a(f2 - (f6 / l), f3 - (f7 / l), f6, f7, f6, Theme.a(Theme.aa, f5), MatrixStackVar);
    }

    private void drawModes(MatrixStack ms, Renderer2D r2d, FontRenderer fr, float startX, float centerX, float startY, float centerY, float anim, float alpha, float pad, int alphaInt255) {
        float rowHOrig = MODE_ROW_H * this.x;
        float rowWOrig = (d * this.x) - (pad * l);
        float radiusOrig = 3.5f * this.x;
        for (int mi = 0; mi < this.modes.length; mi++) {
            float rowYOrig = startY + (mi * rowHOrig);
            float fAx = a(startX, centerX, anim);
            float fAy = a(rowYOrig, centerY, anim);
            float wScaled = a(rowWOrig, anim);
            float hScaled = a(rowHOrig - 1.5f, anim);
            float radiusScaled = a(radiusOrig, anim);
            boolean sel = mi == this.modeIdx;
            Color bg1 = Theme.a(sel ? Theme.C : Theme.q, alphaInt255);
            Color bg2 = Theme.a(sel ? Theme.D : Theme.r, alphaInt255);
            r2d.a(fAx, fAy, wScaled, hScaled, radiusScaled, bg1, bg1, bg2, bg2, ms);
            Color txc = Theme.a(sel ? Theme.aa : Theme.b, alphaInt255);
            String label = this.modes[mi];
            float labelHalfH = (fr.b(label) * this.x * anim) / l;
            float tx = Math.round(fAx + (5.0f * anim));
            float ty = Math.round(fAy + ((hScaled / l) - labelHalfH));
            float scale = this.x * anim;
            ms.push();
            ms.translate(tx, ty, 0.0f);
            ms.scale(scale, scale, q);
            ms.translate(-tx, -ty, 0.0f);
            fr.a(label, tx, ty + 4.0f, txc, ms);
            ms.pop();
        }
    }

    public boolean a(int i2, int i3) {
        if (this.r) {
            if (this.modes != null && this.modes.length > 0) {
                    float rowH = MODE_ROW_H * this.x;
                    float rowW = this.G - (k * this.x * l);
                    for (int mi = 0; mi < this.modes.length; mi++) {
                        float rowY = this.modesY + (mi * rowH);
                        if (GuiInput.a(this.E + (k * this.x), rowY, rowW, rowH - 1.5f, i2, i3)) {
                            this.modeIdx = mi;
                            if (this.modeCB != null) {
                                this.modeCB.accept(mi);
                            }
                            return true;
                        }
                    }
                }
                if (GuiInput.a(this.K, this.L, this.M, this.N, i2, i3)) {
                    this.C = true;
                    d(i2, i3);
                    return true;
                }
                float f2 = k * this.x * this.S;
                if (!GuiInput.a(this.O, this.P - f2, this.Q, this.R + (f2 * l), i2, i3)) {
                    return c(i2, i3);
                }
                this.D = true;
                a(i2);
                return true;
        }
        return false;
    }

    public void b(int i2, int i3) {
        this.C = false;
        this.D = false;
    }

    public void a(int i2, int i3, double d2, double d3) {
        if (this.r) {
            if (this.C) {
                d(i2, i3);
            }
            if (this.D) {
                a(i2);
            }
        }
    }

    private void d(int i2, int i3) {
        float f2 = i2 - this.K;
        float f3 = i3 - this.L;
        this.z = Math.max(0.0f, Math.min(q, f2 / this.M));
        this.A = Math.max(0.0f, Math.min(q, q - (f3 / this.N)));
        g();
    }

    private void a(int i2) {
        this.y = Math.max(0.0f, Math.min(q, (i2 - this.O) / this.Q));
        g();
    }

    private void g() {
        if (this.B != null) {
            this.B.accept(e());
        }
    }

    public boolean c(int i2, int i3) {
        if (!c()) {
            return false;
        }
        float fJ = (float) this.s.j();
        float f2 = this.G * fJ;
        float f3 = this.H * fJ;
        return GuiInput.a(this.I - (f2 / l), this.J - (f3 / l), f2, f3, i2, i3);
    }

    private Color a(Color color, float f2) {
        return Theme.b(color, f2);
    }

    public static String a(String str, String str2, int i2, int i3, int i4, int i5) {
        return null;
    }
}
