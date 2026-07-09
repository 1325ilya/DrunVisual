package drunvisual.gui.settings;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.stream.Collectors;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Quaternionf;
import drunvisual.gui.core.GuiLayerRegistry;
import drunvisual.settings.ModeSetting;
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

public class ModeSettingWidget implements SettingWidget {
    public static final float a = 25.0f;
    private static final float e = 8.0f;
    private static final float f = 17.0f;
    private static final float g = 6.0f;
    private static final float h = 20.0f;
    private static final float i = 7.5f;
    private static final float j = 6.0f;
    private static final float k = -2.0f;
    private static final float l = 7.5f;
    private static final float m = 6.0f;
    private static final float n = 1.5f;
    private static final float o = 15.0f;
    private final String q;
    private final String[] r;
    private final boolean s;
    private final ModeSetting t;
    private int u;
    private final Set<Integer> v;
    private boolean w;
    private final AnimationState x;
    private final AnimationState y;
    private final MarqueeText z;
    private final List<AnimationState> A;
    private final List<Boolean> B;
    private boolean C;
    private boolean D;
    private boolean E;
    private float F;
    private float G;
    private float H;
    private float I;
    private float J;
    private float K;
    private float L;
    private float M;
    public static int b;
    public static boolean c;
    private static final Set<ModeSettingWidget> d = Collections.newSetFromMap(new WeakHashMap());
    private static final Color p = Theme.U;

    public ModeSettingWidget(ModeSetting modeSetting) {
        this.w = false;
        this.x = new AnimationState();
        this.y = new AnimationState();
        this.z = new MarqueeText();
        this.A = new ArrayList();
        this.B = new ArrayList();
        this.C = false;
        this.D = false;
        this.E = false;
        this.I = 1.0f;
        this.t = modeSetting;
        this.q = modeSetting.f();
        this.r = modeSetting.a();
        this.s = modeSetting.c();
        this.u = modeSetting.k().intValue();
        this.v = new HashSet(modeSetting.e());
        q();
        d.add(this);
    }

    public ModeSettingWidget(String str, String[] strArr, int i2) {
        this(str, strArr, i2, false);
    }

    public ModeSettingWidget(String str, String[] strArr, int i2, boolean z) {
        this.w = false;
        this.x = new AnimationState();
        this.y = new AnimationState();
        this.z = new MarqueeText();
        this.A = new ArrayList();
        this.B = new ArrayList();
        this.C = false;
        this.D = false;
        this.E = false;
        this.I = 1.0f;
        this.t = null;
        this.q = str;
        this.r = strArr;
        this.s = z;
        int length = strArr.length;
        this.u = Math.max(0, Math.min(i2, (length & (-2)) - ((length ^ (-1)) & 1)));
        this.v = new HashSet();
        if (z && i2 >= 0 && i2 < strArr.length) {
            this.v.add(Integer.valueOf(i2));
        }
        q();
        d.add(this);
    }

    public ModeSettingWidget(String str, String[] strArr, String str2) {
        this(str, strArr, str2, false);
    }

    public ModeSettingWidget(String str, String[] strArr, String str2, boolean z) {
        this.w = false;
        this.x = new AnimationState();
        this.y = new AnimationState();
        this.z = new MarqueeText();
        this.A = new ArrayList();
        this.B = new ArrayList();
        this.C = false;
        this.D = false;
        this.E = false;
        this.I = 1.0f;
        this.t = null;
        this.q = str;
        this.r = strArr;
        this.s = z;
        this.u = 0;
        this.v = new HashSet();
        int i2 = 0;
        while (true) {
            if (i2 >= strArr.length) {
                break;
            }
            if (strArr[i2].equals(str2)) {
                this.u = i2;
                if (z) {
                    this.v.add(Integer.valueOf(i2));
                }
            } else {
                i2++;
            }
        }
        q();
        d.add(this);
    }

    public ModeSettingWidget(String str, String[] strArr, int[] iArr) {
        this.w = false;
        this.x = new AnimationState();
        this.y = new AnimationState();
        this.z = new MarqueeText();
        this.A = new ArrayList();
        this.B = new ArrayList();
        this.C = false;
        this.D = false;
        this.E = false;
        this.I = 1.0f;
        this.t = null;
        this.q = str;
        this.r = strArr;
        this.s = true;
        this.u = iArr.length <= 0 ? 0 : iArr[0];
        this.v = new HashSet();
        for (int i2 : iArr) {
            if (i2 >= 0 && i2 < strArr.length) {
                this.v.add(Integer.valueOf(i2));
            }
        }
        q();
        d.add(this);
    }

    public ModeSettingWidget(String str, String[] strArr, Set<String> set) {
        this.w = false;
        this.x = new AnimationState();
        this.y = new AnimationState();
        this.z = new MarqueeText();
        this.A = new ArrayList();
        this.B = new ArrayList();
        this.C = false;
        this.D = false;
        this.E = false;
        this.I = 1.0f;
        this.t = null;
        this.q = str;
        this.r = strArr;
        this.s = true;
        this.u = 0;
        this.v = new HashSet();
        for (int i2 = 0; i2 < strArr.length; i2++) {
            if (set.contains(strArr[i2])) {
                this.v.add(Integer.valueOf(i2));
                if (this.v.size() == 1) {
                    this.u = i2;
                }
            }
        }
        q();
        d.add(this);
    }

    private void q() {
        for (int i2 = 0; i2 < this.r.length; i2++) {
            this.A.add(new AnimationState());
            this.B.add(Boolean.valueOf(false));
        }
    }

    @Override
    public String a() {
        return this.q;
    }

    @Override
    public float b() {
        return 25.0f;
    }

    public boolean c() {
        return this.s;
    }

    public String e() {
        return this.r[this.u];
    }

    public int f() {
        return this.u;
    }

    public void a(int i2) {
        int length = this.r.length;
        this.u = Math.max(0, Math.min(i2, (length & (-2)) - ((length ^ (-1)) & 1)));
        if (this.s) {
            this.v.clear();
            this.v.add(Integer.valueOf(this.u));
        }
        if (this.t != null) {
            this.t.a(Integer.valueOf(this.u));
        }
    }

    public void a(String str) {
        for (int i2 = 0; i2 < this.r.length; i2++) {
            if (this.r[i2].equals(str)) {
                this.u = i2;
                if (this.s) {
                    this.v.clear();
                    this.v.add(Integer.valueOf(i2));
                }
                if (this.t != null) {
                    this.t.a(str);
                    return;
                }
                return;
            }
        }
    }

    public Set<Integer> g() {
        return !this.s ? Collections.singleton(Integer.valueOf(this.u)) : new HashSet(this.v);
    }

    public Set<String> h() {
        return !this.s ? Collections.singleton(this.r[this.u]) : (Set) this.v.stream().map(num -> {
            return this.r[num.intValue()];
        }).collect(Collectors.toSet());
    }

    public boolean b(int i2) {
        if (this.s) {
            return this.v.contains(Integer.valueOf(i2));
        }
        return Bool.from(i2 != this.u ? 0 : 1);
    }

    public void c(int i2) {
        if (!this.s || i2 < 0 || i2 >= this.r.length) {
            return;
        }
        if (this.v.contains(Integer.valueOf(i2))) {
            this.v.remove(Integer.valueOf(i2));
        } else {
            this.v.add(Integer.valueOf(i2));
        }
        if (!this.v.isEmpty()) {
            this.u = this.v.iterator().next().intValue();
        }
        if (this.t != null) {
            this.t.b(i2);
        }
    }

    public void a(Set<Integer> set) {
        if (this.s) {
            this.v.clear();
            Iterator<Integer> it = set.iterator();
            while (it.hasNext()) {
                int iIntValue = it.next().intValue();
                if (iIntValue >= 0 && iIntValue < this.r.length) {
                    this.v.add(Integer.valueOf(iIntValue));
                }
            }
            if (!this.v.isEmpty()) {
                this.u = this.v.iterator().next().intValue();
            }
            if (this.t != null) {
                this.t.a(set);
            }
        }
    }

    public void b(Set<String> set) {
        if (this.s) {
            this.v.clear();
            for (int i2 = 0; i2 < this.r.length; i2++) {
                if (set.contains(this.r[i2])) {
                    this.v.add(Integer.valueOf(i2));
                }
            }
            if (this.v.isEmpty()) {
                return;
            }
            this.u = this.v.iterator().next().intValue();
        }
    }

    public String[] i() {
        return this.r;
    }

    public boolean m() {
        return this.w;
    }

    public float n() {
        if (!this.w && this.x.j() < 0.01d) {
            return 0.0f;
        }
        return this.K + (this.M * ((float) this.x.j()));
    }

    public boolean o() {
        return (this.w || this.x.j() > 0.01d) ? true : false;
    }

    private String r() {
        if (!this.s) {
            return this.r[this.u];
        }
        if (this.v.isEmpty()) {
            return "-";
        }
        ArrayList arrayList = new ArrayList(this.v);
        Collections.sort(arrayList);
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            if (i2 > 0) {
                sb.append(", ");
            }
            sb.append(this.r[((Integer) arrayList.get(i2)).intValue()]);
        }
        return sb.toString();
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, int i2, int i3, float f5, float f6) {
        double d2;
        double d3;
        FontRenderer fontRenderer = FontManager.a[14];
        float f7 = 25.0f * f6;
        float f8 = 8.0f * f6;
        this.I = f6;
        this.z.a(GuiInput.a(f2, f3, f4, f7, i2, i3));
        if (this.w != this.D) {
            if (this.w) {
                d3 = 1.0d;
            } else {
                d3 = 0.0d;
            }
            this.x.a(d3, !this.w ? 0.15d : 0.2d, Easing.h);
            if (this.w) {
                this.E = true;
            }
            this.D = this.w;
        }
        this.x.a();
        this.y.a();
        int i4 = (int) (255.0f * f5);
        float fMax = 0.0f;
        for (String str : this.r) {
            fMax = Math.max(fMax, fontRenderer.a(str));
        }
        float f9 = 6.0f * f6;
        float f10 = 6.0f * f6;
        float f11 = f * f6;
        float f12 = 7.5f * f6;
        float f13 = (fMax * f6) + (20.0f * f6) + f10 + (f9 * 2.0f);
        float f14 = (((f2 + f4) - f8) - f13) + 1.0f;
        this.z.a(MatrixStackVar, renderer2D, fontRenderer, this.q, f2 + f8, ((f3 + (f7 / 2.0f)) - ((fontRenderer.b(this.q) * f6) / 4.0f)) - 1.0f, (f14 - (f2 + f8)) - (4.0f * f6), f6, Theme.a, f5);
        String strR = r();
        float f15 = ((f3 + (f7 / 2.0f)) - (f11 / 2.0f)) - 1.0f;
        this.F = f14;
        this.G = f15;
        this.H = f13;
        boolean zA = GuiInput.a(f14, f15, f13, f11, i2, i3);
        if (zA != this.C) {
            AnimationState animationState = this.y;
            if (zA) {
                d2 = 1.0d;
            } else {
                d2 = 0.0d;
            }
            animationState.a(d2, 0.15d, Easing.h);
            this.C = zA;
        }
        float fJ = (float) this.x.j();
        Color colorA = a(Theme.e, f5);
        Color colorA2 = a(Theme.f, f5);
        Color colorA3 = a(Theme.w, f5);
        float f16 = f12 * (1.0f - fJ);
        renderer2D.a(f14 - (0.5f * f6), f15 - (0.5f * f6), f13 + f6, f11 + f6, f16, f16, f12, f12, colorA3, colorA3, colorA3, colorA3, MatrixStackVar);
        renderer2D.a(f14, f15, f13, f11, f16, f16, f12, f12, colorA, colorA, colorA2, colorA2, MatrixStackVar);
        float f17 = f14 + f9;
        float fB = ((f15 + (f11 / 2.0f)) - ((fontRenderer.b(strR) * f6) / 4.0f)) - 1.0f;
        float f18 = ((f14 + f13) - f9) - f10;
        float f19 = (f18 - f17) - (4.0f * f6);
        float f20 = o * f6;
        renderer2D.b().a(f17, f15, f19, f11, MatrixStackVar);
        Color colorA4 = Theme.a(Theme.aa, i4);
        MatrixStackVar.push();
        MatrixStackVar.translate(f17, fB, 0.0f);
        MatrixStackVar.scale(f6, f6, 1.0f);
        MatrixStackVar.translate(-f17, -fB, 0.0f);
        fontRenderer.a(strR, f17, fB, colorA4, MatrixStackVar);
        MatrixStackVar.pop();
        if (fontRenderer.a(strR) * f6 > f19 - f20) {
            float f21 = (f17 + f19) - f20;
            Color colorA5 = Theme.a(Theme.e, 0);
            Color colorA6 = a(Theme.e, f5);
            renderer2D.a(f21, f15, f20, f11, 0.0f, colorA5, colorA6, colorA5, colorA6, MatrixStackVar);
        }
        renderer2D.b().a(MatrixStackVar);
        a(renderer2D, f18, f15 + (f11 / 2.0f), f10, n * f6, fJ, i4, MatrixStackVar);
        if (zA) {
            GuiInput.g();
        }
    }

    private void a(Renderer2D renderer2D, float f2, float f3, float f4, float f5, float f6, int i2, MatrixStack MatrixStackVar) {
        Color colorA = Theme.a(Theme.aa, i2);
        float f7 = 3.0f * this.I * (1.0f - f6);
        float f8 = f4 / 2.0f;
        renderer2D.a(f2, f3 - (f5 / 2.0f), f8 + (0.5f * this.I), f5, colorA, MatrixStackVar);
        renderer2D.a((f2 + f8) - (0.5f * this.I), f3 - (f5 / 2.0f), f8 + (0.5f * this.I), f5, colorA, MatrixStackVar);
        if (f7 <= 0.1f) {
            return;
        }
        float f9 = f3 - (f7 / 2.0f);
        float f10 = f2 + f8;
        float f11 = f3 + (f7 / 2.0f);
        float f12 = f3 - (f7 / 2.0f);
        float f2f4 = f2 + f4;
        float fAtan2 = (float) Math.atan2(f11 - f9, f10 - f2);
        float fAtan22 = (float) Math.atan2(f11 - f12, f10 - f2f4);
        float fSqrt = (float) Math.sqrt(Math.pow(f10 - f2, 2.0d) + Math.pow(f11 - f9, 2.0d));
        float fSqrt2 = (float) Math.sqrt(Math.pow(f2f4 - f10, 2.0d) + Math.pow(f12 - f11, 2.0d));
        MatrixStackVar.push();
        MatrixStackVar.translate(f2 + (fSqrt / 2.0f), (f9 + f11) / 2.0f, 0.0f);
        MatrixStackVar.multiply(new Quaternionf().rotationZ(fAtan2));
        renderer2D.a((-fSqrt) / 2.0f, (-f5) / 2.0f, fSqrt, f5, colorA, MatrixStackVar);
        MatrixStackVar.pop();
        MatrixStackVar.push();
        MatrixStackVar.translate(f10 + (((f2 + f4) - f10) / 2.0f), (f11 + f12) / 2.0f, 0.0f);
        MatrixStackVar.multiply(new Quaternionf().rotationZ(fAtan22));
        renderer2D.a((-fSqrt2) / 2.0f, (-f5) / 2.0f, fSqrt2, f5, colorA, MatrixStackVar);
        MatrixStackVar.pop();
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5, int i2, int i3, float f6, float f7) {
        Color colorA;
        if (this.w || this.x.j() >= 0.01d) {
            this.x.a();
            float fJ = (float) this.x.j();
            if (fJ >= 0.01f) {
                FontRenderer fontRenderer = FontManager.a[14];
                float f8 = 6.0f * this.I;
                float f9 = k * this.I;
                float f10 = 7.5f * this.I;
                float fB = fontRenderer.b(this.r[0]) * this.I;
                float length = this.r.length * fB;
                int length2 = this.r.length;
                float f11 = ((length + (((2 * (length2 & (-2))) - (length2 ^ 1)) * f9)) + (f8 * 2.0f)) - 8.0f;
                float f12 = this.H;
                float f13 = this.F;
                float f14 = this.G + (f * this.I);
                this.J = f13;
                this.K = f14;
                this.L = f12;
                this.M = f11;
                if (this.E) {
                    s();
                    this.E = false;
                }
                float f15 = f11 * fJ;
                if (this.w && fJ > 0.5f) {
                    GuiLayerRegistry.a().a(GuiLayerRegistry.Layer.DROPDOWN, f13, f14, f12, f15);
                }
                Color colorA2 = a(Theme.e, f6 * fJ);
                Color colorA3 = a(Theme.f, f6 * fJ);
                Color colorA4 = a(Theme.w, f6 * fJ);
                renderer2D.a(f13 - (0.5f * this.I), f14 - (0.5f * this.I), f12 + this.I, f15 + this.I, f10 * fJ, f10 * fJ, 0.0f, 0.0f, colorA4, colorA4, colorA4, colorA4, MatrixStackVar);
                renderer2D.a(f13, f14, f12, f15, f10 * fJ, f10 * fJ, 0.0f, 0.0f, colorA2, colorA2, colorA3, colorA3, MatrixStackVar);
                if (fJ > 0.3f) {
                    int iMin = (int) (255.0f * f6 * Math.min(1.0f, (fJ - 0.3f) / 0.7f));
                    float f16 = f14 + (f8 * fJ);
                    float f17 = fB + f9;
                    float f18 = f14 + (f8 * fJ);
                    int i4 = f17 <= 0.0f ? -1 : (int) ((i3 - f18) / f17);
                    boolean z = false;
                    if (i4 >= 0 && i4 < this.r.length) {
                        float f19 = i3 - (f18 + (i4 * f17));
                        z = f19 >= 0.0f && f19 < fB;
                    }
                    boolean z2 = ((float) i2) >= f13 && ((float) i2) <= f13 + f12;
                    int i5 = 0;
                    while (i5 < this.r.length && f16 <= f14 + f15) {
                        boolean zB = b(i5);
                        int i6 = (z2 && z && i5 == i4 && fJ > 0.8f && this.w) ? 1 : 0;
                        if (Bool.from(i6) != this.B.get(i5).booleanValue()) {
                            this.A.get(i5).a(i6 == 0 ? 0.0d : 1.0d, 0.12d, Easing.h);
                            this.B.set(i5, Boolean.valueOf(Bool.from(i6)));
                        }
                        this.A.get(i5).a();
                        float fJ2 = (float) this.A.get(i5).j();
                        if (zB) {
                            colorA = Theme.a(Theme.aa, iMin);
                        } else {
                            colorA = Theme.a(ColorUtils.a(Theme.b, p, fJ2), iMin);
                        }
                        float f20 = f13 + f8;
                        float f21 = f16;
                        MatrixStackVar.push();
                        MatrixStackVar.translate(f20, f21, 0.0f);
                        MatrixStackVar.scale(this.I, this.I, 1.0f);
                        MatrixStackVar.translate(-f20, -f21, 0.0f);
                        fontRenderer.a(this.r[i5], f20, f21, colorA, MatrixStackVar);
                        MatrixStackVar.pop();
                        if (i6 != 0 && this.w) {
                            GuiInput.g();
                        }
                        f16 += fB + f9;
                        i5++;
                    }
                }
            }
        }
    }

    @Override
    public boolean j() {
        return (this.w || this.x.j() > 0.01d) ? true : false;
    }

    @Override
    public boolean a(float f2, float f3, float f4, int i2, int i3) {
        return d(i2, i3);
    }

    @Override
    public boolean b(float f2, float f3, float f4, int i2, int i3) {
        return d(i2, i3);
    }

    private boolean d(int i2, int i3) {
        if (GuiInput.a(this.F, this.G, this.H, f * this.I, i2, i3)) {
            this.w = Bool.from(this.w ? 0 : 1);
            return true;
        }
        if (!this.w || this.x.j() <= 0.5d) {
            return false;
        }
        FontRenderer fontRenderer = FontManager.a[14];
        float fJ = (float) this.x.j();
        float f2 = 6.0f * this.I;
        float f3 = k * this.I;
        float fB = fontRenderer.b(this.r[0]) * this.I;
        float f4 = fB + f3;
        float f5 = this.G + (f * this.I) + (f2 * fJ);
        int i4 = f4 <= 0.0f ? -1 : (int) ((i3 - f5) / f4);
        boolean z = false;
        if (i4 < 0) {
        } else if (i4 < this.r.length) {
            float f6 = i3 - (f5 + (i4 * f4));
            z = f6 >= 0.0f && f6 < fB;
        }
        if (!(((float) i2) >= this.J && ((float) i2) <= this.J + this.L) || !z || i4 < 0 || i4 >= this.r.length) {
            if (!GuiInput.a(this.J, this.K, this.L, this.M, i2, i3)) {
                this.w = false;
            }
            return true;
        }
        if (this.s) {
            c(i4);
        } else {
            if (i4 != this.u) {
                this.u = i4;
                if (this.t != null) {
                    this.t.a(Integer.valueOf(i4));
                }
            }
            this.w = false;
        }
        return true;
    }

    @Override
    public boolean l() {
        if (!this.w && this.x.j() <= 0.1d) {
            return false;
        }
        return true;
    }

    public void p() {
        if (this.w) {
            this.w = false;
        }
    }

    public boolean b(int i2, int i3) {
        boolean zA = GuiInput.a(this.F, this.G, this.H, f * this.I, i2, i3);
        if (this.w || this.x.j() > 0.1d) {
            return Bool.from((zA || GuiInput.a(this.J, this.K, this.L, this.M, (double) i2, (double) i3)) ? 1 : 0);
        }
        return zA;
    }

    public boolean c(int i2, int i3) {
        if (!this.w && this.x.j() < 0.1d) {
            return false;
        }
        return GuiInput.a(this.J, this.K, this.L, this.M * ((float) this.x.j()), i2, i3);
    }

    private boolean a(ModeSettingWidget modeSettingWidget) {
        boolean z;
        if (modeSettingWidget == this || !modeSettingWidget.w || modeSettingWidget.x.j() < 0.1d) {
            return false;
        }
        float f2 = this.K;
        float f3 = this.K + this.M;
        float f4 = this.J;
        float f5 = this.J + this.L;
        float f6 = modeSettingWidget.K;
        float f7 = modeSettingWidget.K + modeSettingWidget.M;
        float f8 = modeSettingWidget.J;
        if (f4 >= modeSettingWidget.J + modeSettingWidget.L) {
            z = false;
        } else if (f5 > f8) {
            z = true;
        } else {
            z = false;
        }
        return Bool.from((z && ((f2 > f7 ? 1 : (f2 == f7 ? 0 : -1)) < 0 && (f3 > f6 ? 1 : (f3 == f6 ? 0 : -1)) > 0)) ? 1 : 0);
    }

    private void s() {
        for (ModeSettingWidget modeSettingWidget : d) {
            if (modeSettingWidget != this && modeSettingWidget.w && a(modeSettingWidget)) {
                modeSettingWidget.w = false;
            }
        }
    }

    private Color a(Color color, float f2) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(0, Math.min(255, (int) (color.getAlpha() * f2))));
    }

    @Override
    public boolean d() {
        int i2;
        if (this.t == null || this.t.m()) {
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
