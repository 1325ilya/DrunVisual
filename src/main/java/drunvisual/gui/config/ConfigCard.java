package drunvisual.gui.config;

import java.awt.Color;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.client.MinecraftContext;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.config.ConfigEntry;
import drunvisual.core.Bool;
import drunvisual.cosmetic.CosmeticModelLoader;
import drunvisual.cosmetic.CosmeticModelRenderer;
import drunvisual.gui.core.GuiInput;
import drunvisual.gui.core.GuiInteractionState;
import drunvisual.gui.friends.FriendCard;
import drunvisual.model.ModelCube;
import drunvisual.model.ModelPart;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.render.icons.IconGlyphs;
import drunvisual.theme.Theme;
import drunvisual.util.ColorUtils;

public class ConfigCard {
    public static final float a = 36.5f;
    private static final float d = 9.5f;
    private static final float e = 11.0f;
    private static final float f = 9.0f;
    private static final float g = 19.5f;
    private static final float h = 6.5f;
    private static final float i = 6.0f;
    private static final float j = 3.0f;
    private static final float k = 5.0f;
    private static final Color l = new Color(13, 13, 17);
    private static final Color m = new Color(17, 17, 23);
    private static final Color n = new Color(18, 18, 23);
    private static final Color o = new Color(19, 19, 25);
    private static final Color p = new Color(26, 26, 46);
    private static final Color q = new Color(18, 18, 33);
    private static final Color r = new Color(36, 36, 66);
    private static final Color s = new Color(18, 18, 33);
    private static final Color t = new Color(20, 20, 27);
    private static final Color u = new Color(24, 24, 32);
    private static final Color v = new Color(36, 36, 60);
    private static final Color w = new Color(26, 26, 45);
    private static final Color x = new Color(30, 25, 15);
    private static final Color y = new Color(25, 20, 12);
    private static final Color z = new Color(80, 65, 30);
    private static final Color A = new Color(55, 45, 20);
    private static final Color B = new Color(40, 33, 18);
    private static final Color C = new Color(35, 28, 15);
    private static final Color D = new Color(255, EventPriority.HIGHEST, 80);
    private static final long E = 400;
    private final ConfigEntry F;
    private Consumer<ConfigEntry> O;
    private Consumer<ConfigEntry> P;
    private Consumer<ConfigEntry> Q;
    private BiConsumer<ConfigEntry, Float[]> R;
    private BiConsumer<String, String> S;
    private float U;
    private float V;
    private float W;
    private static final String ModelCube = "\\/:*?\"<>| ";
    public static int b;
    public static boolean c;
    private final AnimationState G = new AnimationState();
    private final AnimationState H = new AnimationState();
    private final AnimationState I = new AnimationState();
    private final AnimationState J = new AnimationState();
    private boolean K = false;
    private boolean L = false;
    private boolean M = false;
    private boolean N = false;
    private long T = 0;
    private boolean X = false;
    private String Y = "";
    private String Z = "";
    private int aa = 0;
    private int FriendCard = -1;
    private int CosmeticModelRenderer = -1;
    private final AnimationState CosmeticModelLoader = new AnimationState();
    private boolean ModelPart = true;

    public ConfigCard(ConfigEntry configEntry) {
        this.F = configEntry;
    }

    public ConfigEntry a() {
        return this.F;
    }

    public void a(Consumer<ConfigEntry> consumer) {
        this.O = consumer;
    }

    public void b(Consumer<ConfigEntry> consumer) {
        this.P = consumer;
    }

    public void c(Consumer<ConfigEntry> consumer) {
        this.Q = consumer;
    }

    public void a(BiConsumer<ConfigEntry, Float[]> biConsumer) {
        this.R = biConsumer;
    }

    public void b(BiConsumer<String, String> biConsumer) {
        this.S = biConsumer;
    }

    public void b() {
        this.X = true;
        this.Z = this.F.a();
        this.Y = this.F.a();
        this.aa = this.Y.length();
        this.FriendCard = -1;
        this.CosmeticModelRenderer = -1;
        this.CosmeticModelLoader.d(1.0d);
        this.ModelPart = false;
        this.CosmeticModelLoader.a(0.3d, 0.5d, Easing.i);
    }

    public String c() {
        return this.Z;
    }

    public void a(boolean z2) {
        if (this.X) {
            this.X = false;
            if (z2) {
                String strTrim = this.Y.trim();
                if (strTrim.isEmpty() || strTrim.equals(this.Z)) {
                    return;
                }
                String str = this.Z;
                this.F.a(strTrim);
                if (this.S != null) {
                    this.S.accept(str, strTrim);
                }
            }
        }
    }

    public boolean d() {
        return this.X;
    }

    private boolean a(char c2) {
        return Bool.from((ModelCube.indexOf(c2) != -1 || Character.isISOControl(c2)) ? 0 : 1);
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, int i2, int i3, float f5) {
        int i4;
        int i5;
        int i6;
        this.U = f2;
        this.V = f3;
        this.W = f4;
        this.G.a();
        this.H.a();
        this.I.a();
        this.J.a();
        int i7 = (int) (255.0f * f5);
        boolean zB = GuiInteractionState.a().b();
        boolean zE = this.F.e();
        boolean zF = this.F.f();
        if (!zF) {
            if (zE != this.L) {
                this.H.a(!zE ? 0.0d : 1.0d, 0.2d, Easing.h);
                this.L = zE;
            } else if (c) {
            }
        }
        float fJ = (float) this.G.j();
        float fJ2 = !zF ? (float) this.H.j() : 0.0f;
        if (zF) {
            float f6 = ((f2 + f4) - e) - g;
            float f7 = f3 + 8.5f;
            if (zB) {
                i4 = 0;
            } else if (GuiInput.a(f6, f7, g, g, i2, i3)) {
                i4 = 1;
            } else {
                i4 = 0;
            }
            int i8 = i4;
            int i9 = ((!zB && GuiInput.a(f2, f3, f4, 36.5f, (double) i2, (double) i3)) && i8 == 0) ? 1 : 0;
            a(this.G, Bool.from(i9), this.K, zB);
            this.K = Bool.from((zB || i9 == 0) ? 0 : 1);
            a(this.J, Bool.from(i8), this.N, zB);
            this.N = Bool.from((zB || i8 == 0) ? 0 : 1);
            a(MatrixStackVar, renderer2D, f2, f3, f4, i7, fJ, fJ2);
            a(MatrixStackVar, renderer2D, f2, f3, i2, i3, i7, f5);
            a(MatrixStackVar, renderer2D, f6, f7, i7);
            if (i8 == 0 || zB) {
                return;
            }
            GuiInput.g();
            return;
        }
        float f8 = ((f2 + f4) - e) - g;
        float f9 = (f8 - i) - g;
        float f10 = f3 + 8.5f;
        if (zB || !GuiInput.a(f9, f10, g, g, i2, i3)) {
            i5 = 0;
        } else {
            i5 = 1;
        }
        int i10 = i5;
        int i11 = (zB || !GuiInput.a(f8, f10, g, g, (double) i2, (double) i3)) ? 0 : 1;
        int i12 = ((!zB && GuiInput.a(f2, f3, f4, 36.5f, (double) i2, (double) i3)) && i10 == 0 && i11 == 0) ? 1 : 0;
        a(this.G, Bool.from(i12), this.K, zB);
        if (zB) {
            i6 = 0;
        } else if (i12 != 0) {
            i6 = 1;
        } else {
            i6 = 0;
        }
        this.K = Bool.from(i6);
        a(this.I, Bool.from(i10), this.M, zB);
        this.M = Bool.from((zB || i10 == 0) ? 0 : 1);
        a(this.J, Bool.from(i11), this.N, zB);
        this.N = Bool.from((zB || i11 == 0) ? 0 : 1);
        a(MatrixStackVar, renderer2D, f2, f3, f4, i7, fJ, fJ2);
        a(MatrixStackVar, renderer2D, f2, f3, i2, i3, i7, f5);
        a(MatrixStackVar, renderer2D, f9, f8, f10, i7, fJ2);
        if ((i10 == 0 && i11 == 0) || zB) {
            return;
        }
        GuiInput.g();
    }

    private void a(AnimationState animationState, boolean z2, boolean z3, boolean z4) {
        if (z4 && z3) {
            animationState.a(0.0d, 0.15d, Easing.h);
        } else {
            if (z4 || z2 == z3) {
                return;
            }
            animationState.a(!z2 ? 0.0d : 1.0d, 0.15d, Easing.h);
        }
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, int i2, float f5, float f6) {
        Color colorA;
        Color colorA2;
        Color colorA3;
        Color colorA4;
        if (this.F.f()) {
            colorA = Theme.a(z, i2);
            colorA2 = Theme.a(A, i2);
            colorA3 = Theme.a(ColorUtils.a(x, B, f5), i2);
            colorA4 = Theme.a(ColorUtils.a(y, C, f5), i2);
        } else {
            Color color = n;
            Color color2 = o;
            Color color3 = r;
            Color color4 = s;
            colorA = Theme.a(ColorUtils.a(color, color3, f6), i2);
            colorA2 = Theme.a(ColorUtils.a(color2, color4, f6), i2);
            Color colorA5 = ColorUtils.a(l, t, f5);
            Color colorA6 = ColorUtils.a(m, u, f5);
            Color colorA7 = ColorUtils.a(p, v, f5);
            Color colorA8 = ColorUtils.a(q, w, f5);
            colorA3 = Theme.a(ColorUtils.a(colorA5, colorA7, f6), i2);
            colorA4 = Theme.a(ColorUtils.a(colorA6, colorA8, f6), i2);
        }
        renderer2D.a(f2 - 0.5f, f3 - 0.5f, f4 + 1.0f, 37.5f, 9.5f, colorA, colorA, colorA2, colorA2, MatrixStackVar);
        renderer2D.a(f2, f3, f4, 36.5f, 9.5f, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, int i2, int i3, int i4, float f4) {
        String strB;
        double d2;
        FontRenderer fontRenderer = FontManager.a[12];
        FontRenderer fontRenderer2 = FontManager.b[15];
        FontRenderer fontRenderer3 = FontManager.b[15];
        float f5 = f2 + e;
        float f6 = f3 + f;
        float fB = (f6 + fontRenderer.b("A")) - i;
        Color colorA = Theme.a(Theme.b, i4);
        Color colorA2 = Theme.a(Theme.a, i4);
        Color colorA3 = Theme.a(Theme.b, i4);
        fontRenderer.a(this.F.d(), f5, f6, colorA, MatrixStackVar);
        if (!this.X) {
            float fA = fontRenderer2.a(this.F.a());
            fontRenderer2.a(this.F.a(), f5, fB, colorA2, MatrixStackVar);
            float iconX = f5 + fA + k;
            renderer2D.a(iconX, (fB + (fontRenderer2.b("A") / 2.0f)) - k, j, j, 1.5f, colorA3, MatrixStackVar);
            fontRenderer3.a(this.F.b() != null ? this.F.b() : "", iconX + j + k, fB, colorA2, MatrixStackVar);
            return;
        }
        this.CosmeticModelLoader.a();
        if (this.CosmeticModelLoader.d()) {
            this.ModelPart = Bool.from(this.ModelPart ? 0 : 1);
            AnimationState animationState = this.CosmeticModelLoader;
            if (this.ModelPart) {
                d2 = 1.0d;
            } else {
                d2 = 0.3d;
            }
            animationState.a(d2, 0.5d, Easing.i);
        }
        float fA2 = fontRenderer2.a(this.Y);
        float fB2 = fontRenderer2.b("A");
        if (i()) {
            int iMin = Math.min(this.FriendCard, this.CosmeticModelRenderer);
            renderer2D.a(f5 + fontRenderer2.a(this.Y.substring(0, iMin)), fB, fontRenderer2.a(this.Y.substring(iMin, Math.max(this.FriendCard, this.CosmeticModelRenderer))), fB2 - 8.0f, 2.0f, Theme.a(new Color(Theme.y.getRed(), Theme.y.getGreen(), Theme.y.getBlue(), 80), i4), MatrixStackVar);
        }
        fontRenderer2.a(this.Y, f5, fB, colorA2, MatrixStackVar);
        if (!i()) {
            float fJ = (float) this.CosmeticModelLoader.j();
            if (fJ > 0.1f) {
                renderer2D.a(f5 + fontRenderer2.a(this.Y.substring(0, this.aa)), fB + 1.0f, 1.5f, fB2 - f, 0.5f, Theme.a(Theme.y, (int) (i4 * fJ)), MatrixStackVar);
            }
        }
        float f7 = f5 + fA2 + k;
        renderer2D.a(f7, (fB + (fontRenderer2.b("A") / 2.0f)) - k, j, j, 1.5f, colorA3, MatrixStackVar);
        float f8 = f7 + j + k;
        if (this.F.b() != null) {
            strB = this.F.b();
        } else {
            strB = "";
        }
        fontRenderer3.a(strB, f8, fB, colorA2, MatrixStackVar);
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, int i2, float f5) {
        float fJ = (float) this.I.j();
        float fJ2 = (float) this.J.j();
        a(MatrixStackVar, renderer2D, (int) f2, (int) f4, i2, fJ, f5, true);
        a(MatrixStackVar, renderer2D, (int) f3, (int) f4, i2, fJ2, f5, false);
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, int i2, float f4, float f5, boolean z2) {
        Color colorA = ColorUtils.a(l, t, f4);
        Color colorA2 = ColorUtils.a(m, u, f4);
        Color colorA3 = ColorUtils.a(n, colorA, f4);
        Color colorA4 = ColorUtils.a(o, colorA2, f4);
        Color colorA5 = ColorUtils.a(p, v, f4);
        Color colorA6 = ColorUtils.a(q, w, f4);
        Color colorA7 = ColorUtils.a(r, colorA5, f4);
        Color colorA8 = ColorUtils.a(s, colorA6, f4);
        Color colorA9 = ColorUtils.a(colorA, colorA5, f5);
        Color colorA10 = ColorUtils.a(colorA2, colorA6, f5);
        Color colorA11 = ColorUtils.a(colorA3, colorA7, f5);
        Color colorA12 = ColorUtils.a(colorA4, colorA8, f5);
        Color colorA13 = Theme.a(colorA11, i2);
        Color colorA14 = Theme.a(colorA12, i2);
        renderer2D.a(f2 - 0.5f, f3 - 0.5f, 20.5f, 20.5f, h, colorA13, colorA13, colorA14, colorA14, MatrixStackVar);
        Color colorA15 = Theme.a(colorA9, i2);
        Color colorA16 = Theme.a(colorA10, i2);
        renderer2D.a(f2, f3, g, g, h, colorA15, colorA15, colorA16, colorA16, MatrixStackVar);
        Color colorA17 = Theme.a(ColorUtils.a(Theme.a, Theme.aa, f4), i2);
        if (z2) {
            a(MatrixStackVar, renderer2D, f2, f3, colorA17);
        } else {
            b(MatrixStackVar, renderer2D, f2, f3, colorA17);
        }
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, Color color) {
        float f4 = (1.5f * j) + (1.0f * 2.0f);
        float f5 = f2 + ((g - 1.5f) / 2.0f);
        float f6 = f3 + ((g - f4) / 2.0f);
        for (int i2 = 0; i2 < 3; i2++) {
            renderer2D.a(f5, f6 + (i2 * (1.5f + 1.0f)), 1.5f, 1.5f, 1.5f / 2.0f, color, MatrixStackVar);
        }
    }

    private void b(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, Color color) {
        FontRenderer fontRenderer = FontManager.e[11];
        float fA = fontRenderer.a(IconGlyphs.j);
        float fB = fontRenderer.b(IconGlyphs.j);
        fontRenderer.a(IconGlyphs.j, f2 + ((g - fA) / 2.0f), (f3 + ((g - (fB / 2.0f)) / 2.0f)) - 0.5f, color, MatrixStackVar);
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, int i2) {
        float fJ = (float) this.J.j();
        Color color = x;
        Color color2 = B;
        Color color3 = z;
        Color color4 = new Color(100, 80, 35);
        Color colorA = Theme.a(ColorUtils.a(color, color2, fJ), i2);
        Color colorA2 = Theme.a(ColorUtils.a(color3, color4, fJ), i2);
        renderer2D.a(f2 - 0.5f, f3 - 0.5f, 20.5f, 20.5f, h, colorA2, colorA2, colorA2, colorA2, MatrixStackVar);
        renderer2D.a(f2, f3, g, g, h, colorA, colorA, colorA, colorA, MatrixStackVar);
        b(MatrixStackVar, renderer2D, f2, f3, Theme.a(ColorUtils.a(Theme.a, Theme.aa, fJ), i2));
    }

    public boolean a(float f2, float f3, float f4, int i2, int i3) {
        if (this.X && !GuiInput.a(f2, f3, f4, 36.5f, i2, i3)) {
            a(true);
            return true;
        }
        if (this.F.f()) {
            if (!GuiInput.a(((f2 + f4) - e) - g, f3 + 8.5f, g, g, i2, i3)) {
                return false;
            }
            if (this.Q != null) {
                this.Q.accept(this.F);
            }
            return true;
        }
        float f5 = ((f2 + f4) - e) - g;
        float f6 = (f5 - i) - g;
        float f7 = f3 + 8.5f;
        if (GuiInput.a(f6, f7, g, g, i2, i3)) {
            if (this.X) {
                a(true);
            }
            if (this.O != null) {
                this.O.accept(this.F);
            }
            if (this.R != null) {
                this.R.accept(this.F, new Float[]{Float.valueOf(f6), Float.valueOf(f7 + g), Float.valueOf(g)});
            }
            return true;
        }
        if (GuiInput.a(f5, f7, g, g, i2, i3)) {
            if (this.X) {
                a(true);
            }
            if (this.P != null) {
                this.P.accept(this.F);
            }
            return true;
        }
        if (GuiInput.a(f2, f3, f4, 36.5f, i2, i3)) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis - this.T < E) {
                if (!this.X && this.P != null) {
                    this.P.accept(this.F);
                }
                this.T = 0L;
                return true;
            }
            this.T = jCurrentTimeMillis;
        }
        return false;
    }

    public float e() {
        return ((((this.U + this.W) - e) - g) - i) - g;
    }

    public float f() {
        return this.V + 8.5f + g;
    }

    public boolean a(int i2, int i3, int i4) {
        if (!this.X) {
            return false;
        }
        boolean z2 = ((i4 ^ (-1)) | 2) - (i4 ^ (-1)) != 0;
        boolean z3 = ((i4 ^ (-1)) | 1) - (i4 ^ (-1)) != 0;
        if (i2 == 257 || i2 == 335) {
            a(true);
            return true;
        }
        if (i2 == 256) {
            a(false);
            return true;
        }
        if (z2 && i2 == 65) {
            this.FriendCard = 0;
            this.CosmeticModelRenderer = this.Y.length();
            this.aa = this.Y.length();
            return true;
        }
        if (z2 && i2 == 67) {
            if (i()) {
                MinecraftContext.c.keyboard.setClipboard(k());
            }
            return true;
        }
        if (z2 && i2 == 88) {
            if (i()) {
                MinecraftContext.c.keyboard.setClipboard(k());
                l();
                h();
            }
            return true;
        }
        if (z2 && i2 == 86) {
            String strGetClipboard = MinecraftContext.c.keyboard.getClipboard();
            if (strGetClipboard != null && !strGetClipboard.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (char c2 : strGetClipboard.toCharArray()) {
                    if (a(c2)) {
                        sb.append(c2);
                    }
                }
                String string = sb.toString();
                if (!string.isEmpty()) {
                    if (i()) {
                        l();
                    }
                    int length = 32 - this.Y.length();
                    if (length > 0) {
                        String strSubstring = string.substring(0, Math.min(string.length(), length));
                        this.Y = this.Y.substring(0, this.aa) + strSubstring + this.Y.substring(this.aa);
                        int i5 = this.aa;
                        int length2 = strSubstring.length();
                        this.aa = (i5 & (length2 ^ (-1))) + (length2 & (i5 ^ (-1))) + (2 * (i5 & length2));
                        h();
                    }
                }
            }
            return true;
        }
        if (i2 == 259) {
            if (i()) {
                l();
            } else if (this.aa > 0) {
                this.Y = this.Y.substring(0, (this.aa - 2) + 1) + this.Y.substring(this.aa);
                int i6 = this.aa;
                this.aa = (i6 ^ 1) - (2 * ((i6 ^ (-1)) & 1));
            }
            h();
            return true;
        }
        if (i2 == 261) {
            if (i()) {
                l();
            } else if (this.aa < this.Y.length()) {
                String strSubstring2 = this.Y.substring(0, this.aa);
                int i7 = this.aa;
                this.Y = strSubstring2 + this.Y.substring((i7 | 1) + (i7 & 1));
            } else if (c) {
            }
            h();
            return true;
        }
        if (i2 == 263) {
            if (z3) {
                if (!i()) {
                    this.FriendCard = this.aa;
                }
                if (this.aa > 0) {
                    this.aa--;
                    this.CosmeticModelRenderer = this.aa;
                }
            } else if (i()) {
                this.aa = Math.min(this.FriendCard, this.CosmeticModelRenderer);
                j();
            } else if (this.aa > 0) {
                int i9 = this.aa;
                this.aa = (i9 & (-2)) - ((i9 ^ (-1)) & 1);
            }
            h();
            return true;
        }
        if (i2 == 262) {
            if (z3) {
                if (!i()) {
                    this.FriendCard = this.aa;
                }
                if (this.aa < this.Y.length()) {
                    int i10 = this.aa;
                    this.aa = (i10 | 1) + (i10 & 1);
                    this.CosmeticModelRenderer = this.aa;
                } else if (c) {
                }
            } else if (i()) {
                this.aa = Math.max(this.FriendCard, this.CosmeticModelRenderer);
                j();
            } else if (this.aa < this.Y.length()) {
                int i12 = this.aa;
                this.aa = (i12 | 1) + (i12 & 1);
            }
            h();
            return true;
        }
        if (i2 == 268) {
            if (z3) {
                if (!i()) {
                    this.FriendCard = this.aa;
                }
                this.aa = 0;
                this.CosmeticModelRenderer = 0;
            } else {
                this.aa = 0;
                j();
            }
            h();
            return true;
        }
        if (i2 != 269) {
            return true;
        }
        if (z3) {
            if (!i()) {
                this.FriendCard = this.aa;
            }
            this.aa = this.Y.length();
            this.CosmeticModelRenderer = this.Y.length();
        } else {
            this.aa = this.Y.length();
            j();
        }
        h();
        return true;
    }

    public boolean a(char c2, int i2) {
        if (!this.X) {
            return false;
        }
        if (a(c2)) {
            if (i()) {
                l();
            }
            if (this.Y.length() < 32) {
                this.Y = this.Y.substring(0, this.aa) + c2 + this.Y.substring(this.aa);
                int i3 = this.aa;
                this.aa = (i3 & (-2)) + (1 & (i3 ^ (-1))) + (2 * (i3 & 1));
                h();
            }
        }
        return true;
    }

    private void h() {
        this.CosmeticModelLoader.d(1.0d);
        this.ModelPart = false;
        this.CosmeticModelLoader.a(0.3d, 0.5d, Easing.i);
    }

    private boolean i() {
        if (this.FriendCard != -1) {
            if (this.CosmeticModelRenderer == -1) {
            } else {
                if (this.FriendCard != this.CosmeticModelRenderer) {
                    return true;
                }
            }
        }
        return false;
    }

    private void j() {
        this.FriendCard = -1;
        this.CosmeticModelRenderer = -1;
    }

    private String k() {
        if (!i()) {
            return "";
        }
        return this.Y.substring(Math.min(this.FriendCard, this.CosmeticModelRenderer), Math.max(this.FriendCard, this.CosmeticModelRenderer));
    }

    private void l() {
        if (i()) {
            int iMin = Math.min(this.FriendCard, this.CosmeticModelRenderer);
            this.Y = this.Y.substring(0, iMin) + this.Y.substring(Math.max(this.FriendCard, this.CosmeticModelRenderer));
            this.aa = iMin;
            j();
        }
    }

    public boolean g() {
        return this.X;
    }

    public static String a(String str, String str2, int i2, int i3, int i4, int i5) {
        return null;
    }
}
