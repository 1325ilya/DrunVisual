package drunvisual.gui.widgets;

import java.awt.Color;
import java.util.function.Consumer;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.core.Bool;
import drunvisual.gui.core.GuiInput;
import drunvisual.gui.core.GuiInteractionState;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.render.icons.IconGlyphs;
import drunvisual.theme.Theme;
import drunvisual.util.ColorUtils;

public class SearchBox {
    private static final Color c = Theme.d;
    private static final float d = 8.5f;
    private static final float e = 12.0f;
    private static final float f = 4.0f;
    private static final float g = 4.0f;
    private static final int h = 16;
    private float i;
    private float j;
    private float k;
    private float l;
    private String m;
    private int n;
    private int o;
    private int p;
    private boolean q;
    private final AnimationState r;
    private final AnimationState s;
    private final AnimationState t;
    private boolean u;
    private boolean v;
    private boolean w;
    private Consumer<String> x;
    public static int a;
    public static boolean b;

    public SearchBox() {
        this.m = "";
        this.n = 0;
        this.o = -1;
        this.p = -1;
        this.q = false;
        this.r = new AnimationState();
        this.s = new AnimationState();
        this.t = new AnimationState();
        this.u = false;
        this.v = true;
        this.w = false;
        this.t.d(1.0d);
    }

    public SearchBox(float f2, float f3) {
        this();
        this.k = f2;
        this.l = f3;
    }

    public void a(float f2, float f3) {
        this.i = f2;
        this.j = f3;
    }

    public void b(float f2, float f3) {
        this.k = f2;
        this.l = f3;
    }

    public void a(Consumer<String> consumer) {
        this.x = consumer;
    }

    public void a(boolean z) {
        this.w = z;
    }

    public String a() {
        return this.m;
    }

    public void a(String str) {
        this.m = str;
        this.n = str.length();
        r();
        u();
    }

    public void b() {
        this.m = "";
        this.n = 0;
        r();
        u();
    }

    public boolean c() {
        return this.q;
    }

    public void b(boolean z) {
        if (this.q != z) {
            this.q = z;
            this.r.a(!z ? 0.0d : 1.0d, 0.2d, Easing.h);
            if (z) {
                this.t.d(1.0d);
                this.v = false;
                this.t.a(0.3d, 0.5d, Easing.i);
            }
        }
    }

    public boolean d() {
        return this.m.isEmpty();
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5, int i, int i2) {
        this.i = f2;
        this.j = f3;
        this.k = f4;
        this.l = f5;
        this.r.a();
        this.s.a();
        if (this.q) {
            this.t.a();
            if (this.t.d()) {
                this.v = Bool.from(this.v ? 0 : 1);
                this.t.a(!this.v ? 0.3d : 1.0d, 0.5d, Easing.i);
            }
        } else {
            this.t.d(1.0d);
            this.v = true;
        }
        boolean z = !this.w && GuiInteractionState.a().b();
        int i3 = (z || !GuiInput.a(f2, f3, f4, f5, (double) i, (double) i2)) ? 0 : 1;
        if (z && this.u) {
            this.s.a(0.0d, 0.15d, Easing.h);
            this.u = false;
        } else if (!z && Bool.from(i3) != this.u) {
            this.s.a(i3 == 0 ? 0.0d : 1.0d, 0.15d, Easing.h);
            this.u = Bool.from(i3);
        }
        float fJ = (float) this.r.j();
        float fJ2 = (float) this.s.j();
        a(MatrixStackVar, renderer2D, f2, f3, f4, f5, fJ, fJ2);
        a(MatrixStackVar, renderer2D, f2, f3, f5, fJ, fJ2);
        b(MatrixStackVar, renderer2D, f2, f3, f4, f5, fJ);
        if ((i3 != 0 || this.q) && !z) {
            GuiInput.g();
        }
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5, float f6, float f7) {
        int i = (int) (f7 * 3.0f);
        Color colorB = Theme.b(Theme.S, i);
        Color colorB2 = Theme.b(Theme.T, i);
        renderer2D.a(f2, f3, f4, f5, d, colorB, colorB, colorB2, colorB2, MatrixStackVar);
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5, float f6) {
        FontRenderer fontRenderer = FontManager.e[12];
        FontRenderer fontRenderer2 = FontManager.b[14];
        fontRenderer.a(IconGlyphs.H, (((f2 + 4.0f) + 6.0f) - (fontRenderer.a(IconGlyphs.H) / 2.0f)) + 2.0f, ((f3 + (f4 / 2.0f)) - (fontRenderer2.b("A") / 4.0f)) + 0.5f, ColorUtils.a(ColorUtils.a(Theme.b, c, f6), c, f5), MatrixStackVar);
    }

    private void b(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5, float f6) {
        FontRenderer fontRenderer = FontManager.b[14];
        float f7 = f2 + 4.0f + e + 4.0f;
        float fB = ((f3 + (f5 / 2.0f)) - (fontRenderer.b("A") / 4.0f)) - 0.5f;
        renderer2D.b().a(f7 - 2.0f, f3, ((((f4 - 4.0f) - e) - 4.0f) - 10.0f) + 4.0f, f5, MatrixStackVar);
        if (this.m.isEmpty() && !this.q) {
            fontRenderer.a("Поиск", f7, fB, Theme.b, MatrixStackVar);
        } else if (this.m.isEmpty() && this.q) {
            fontRenderer.a("|", f7, fB - 0.5f, Theme.a(Theme.a, (int) (255.0f * ((float) this.t.j()))), MatrixStackVar);
        } else {
            if (q()) {
                int iMin = Math.min(this.o, this.p);
                int iMax = Math.max(this.o, this.p);
                float fA = f7 + fontRenderer.a(this.m, iMin);
                renderer2D.a(fA, f3 + 4.0f, (f7 + fontRenderer.a(this.m, iMax)) - fA, f5 - 8.0f, 2.0f, Theme.A, MatrixStackVar);
            }
            fontRenderer.a(this.m, f7, fB, Theme.a, MatrixStackVar);
            if (this.q && !q()) {
                float fJ = (float) this.t.j();
                if (fJ <= 0.3f) {
                    if (867395491 < a) {
                        throw new ExceptionInInitializerError();
                    }
                } else {
                    renderer2D.a(f7 + fontRenderer.a(this.m, this.n), f3 + 5.0f, 1.0f, f5 - 10.0f, Theme.a(Theme.a, (int) (255.0f * fJ)), MatrixStackVar);
                }
            }
        }
        renderer2D.b().a(MatrixStackVar);
    }

    public boolean a(int i, int i2) {
        boolean zB = GuiInteractionState.a().b();
        if (!GuiInput.a(this.i, this.j, this.k, this.l, i, i2) || zB) {
            if (this.q) {
                b(false);
            } else if (67288528 >= 464816477) {
            }
            return false;
        }
        b(true);
        this.n = FontManager.b[14].a(this.m, i - (((this.i + 4.0f) + e) + 4.0f));
        r();
        return true;
    }

    public boolean a(int i, int i2, int i3) {
        if (!this.q) {
            return false;
        }
        int i4 = ((i3 ^ (-1)) | (i3 ^ (-1512818939))) - (i3 ^ (-1)) == 0 ? 0 : 1;
        int i5 = ((i3 ^ (-1)) | 1) - (i3 ^ (-1)) == 0 ? 0 : 1;
        if (i4 != 0) {
            switch (i) {
                case 65:
                    m();
                    break;
                case 67:
                    n();
                    break;
                case 86:
                    o();
                    break;
                case 88:
                    p();
                    break;
            }
            return true;
        }
        switch (i) {
            case 256:
                b(false);
                r();
                break;
            case 257:
                b(false);
                r();
                break;
            case 259:
                i();
                break;
            case 261:
                j();
                break;
            case 262:
                b(Bool.from(i5), Bool.from(i4));
                break;
            case 263:
                a(Bool.from(i5), Bool.from(i4));
                break;
            case 268:
                c(Bool.from(i5));
                break;
            case 269:
                d(Bool.from(i5));
                break;
        }
        return true;
    }

    public boolean a(char c2, int i) {
        if (this.q && !Character.isISOControl(c2)) {
            if (this.m.length() >= h && !q()) {
                return true;
            }
            if (q()) {
                s();
            }
            if (this.m.length() < h) {
                this.m = this.m.substring(0, this.n) + c2 + this.m.substring(this.n);
                int i2 = this.n;
                this.n = (i2 | 1) + (i2 & 1);
                t();
                u();
            }
            return true;
        }
        return false;
    }

    private void i() {
        if (q()) {
            s();
        } else if (this.n > 0) {
            this.m = this.m.substring(0, this.n - 1) + this.m.substring(this.n);
            int i = this.n;
            this.n = (i & (-2)) - ((i ^ (-1)) & 1);
            u();
        } else if (b) {
        }
        t();
    }

    private void j() {
        if (q()) {
            s();
        } else if (this.n >= this.m.length()) {
        } else {
            this.m = this.m.substring(0, this.n) + this.m.substring((this.n - (-2)) - 1);
            u();
        }
        t();
    }

    private void a(boolean z, boolean z2) {
        int iK;
        if (z2) {
            iK = k();
        } else {
            int i = this.n;
            iK = Math.max(0, (2 * (i & (-2))) - (i ^ 1));
        }
        if (z) {
            if (!q()) {
                this.o = this.n;
            }
            this.p = iK;
        } else {
            if (q()) {
                iK = Math.min(this.o, this.p);
            }
            r();
        }
        this.n = iK;
        t();
    }

    private void b(boolean z, boolean z2) {
        int iL;
        if (z2) {
            iL = l();
        } else {
            int length = this.m.length();
            int i = this.n;
            iL = Math.min(length, (2 * (i | 1)) - (i ^ 1));
        }
        if (z) {
            if (!q()) {
                this.o = this.n;
            }
            this.p = iL;
        } else {
            if (q()) {
                iL = Math.max(this.o, this.p);
            }
            r();
        }
        this.n = iL;
        t();
    }

    private void c(boolean z) {
        if (z) {
            if (!q()) {
                this.o = this.n;
            }
            this.p = 0;
        } else {
            r();
        }
        this.n = 0;
        t();
    }

    private void d(boolean z) {
        if (z) {
            if (!q()) {
                this.o = this.n;
            }
            this.p = this.m.length();
        } else {
            r();
        }
        this.n = this.m.length();
        t();
    }

    private int k() {
        if (this.n == 0) {
            return 0;
        }
        int i = this.n - 1;
        while (i > 0 && Character.isWhitespace(this.m.charAt(i))) {
            i--;
        }
        while (true) {
            if (i <= 0) {
                break;
            }
            int i2 = i;
            i--;
            if (Character.isWhitespace(this.m.charAt(i2 + (((i2 & (-1742277746)) | ((i2 ^ (-1)) & 1742277745)) ^ (-1)) + 1))) {
            }
        }
        return i;
    }

    private int l() {
        if (this.n >= this.m.length()) {
            return this.m.length();
        }
        int i = this.n;
        while (i < this.m.length() && !Character.isWhitespace(this.m.charAt(i))) {
            i++;
        }
        while (i < this.m.length() && Character.isWhitespace(this.m.charAt(i))) {
            i++;
        }
        return i;
    }

    private void m() {
        this.o = 0;
        this.p = this.m.length();
        this.n = this.m.length();
    }

    private void n() {
        if (q()) {
            MinecraftClient.getInstance().keyboard.setClipboard(this.m.substring(Math.min(this.o, this.p), Math.max(this.o, this.p)));
        }
    }

    private void o() {
        String strGetClipboard = MinecraftClient.getInstance().keyboard.getClipboard();
        if (strGetClipboard == null || strGetClipboard.isEmpty()) {
            return;
        }
        String strReplaceAll = strGetClipboard.replaceAll("[\\r\\n\\t]", "");
        if (q()) {
            s();
        } else {
        }
        int length = h - this.m.length();
        if (strReplaceAll.length() > length) {
            strReplaceAll = strReplaceAll.substring(0, length);
        }
        if (!strReplaceAll.isEmpty()) {
            this.m = this.m.substring(0, this.n) + strReplaceAll + this.m.substring(this.n);
            this.n = (this.n - (strReplaceAll.length() ^ (-1))) - 1;
            u();
        }
        t();
    }

    private void p() {
        if (q()) {
            n();
            s();
        }
    }

    private boolean q() {
        if (this.o != -1 && this.p != -1) {
            if (this.o != this.p) {
                return true;
            }
        }
        return false;
    }

    private void r() {
        this.o = -1;
        this.p = -1;
    }

    private void s() {
        if (q()) {
            int iMin = Math.min(this.o, this.p);
            this.m = this.m.substring(0, iMin) + this.m.substring(Math.max(this.o, this.p));
            this.n = iMin;
            r();
            u();
        }
    }

    private void t() {
        this.t.d(1.0d);
        this.v = false;
        this.t.a(0.3d, 0.5d, Easing.i);
    }

    private void u() {
        if (this.x != null) {
            this.x.accept(this.m);
        }
    }

    public float e() {
        return this.i;
    }

    public float f() {
        return this.j;
    }

    public float g() {
        return this.k;
    }

    public float h() {
        return this.l;
    }

    public static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
