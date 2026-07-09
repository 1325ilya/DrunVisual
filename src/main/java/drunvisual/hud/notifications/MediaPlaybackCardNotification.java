package drunvisual.hud.notifications;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.config.CloudConfigRepository;
import drunvisual.config.ConfigJsonCodec;
import drunvisual.config.ConfigManager;
import drunvisual.config.ConfigProfile;
import drunvisual.config.ConfigState;
import drunvisual.core.Bool;
import drunvisual.gui.core.GuiInput;
import drunvisual.gui.friends.FriendCard;
import drunvisual.gui.friends.FriendsPanel;
import drunvisual.hud.core.HudServiceRegistry;
import drunvisual.media.MediaPlaybackState;
import drunvisual.media.MediaSessionService;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.render.icons.IconTextureRegistry;

public class MediaPlaybackCardNotification extends HudNotification {
    private static final Color m = new Color(255, 255, 255);
    private static final Color n = new Color(223, 223, 243);
    private static final Color o = new Color(49, 49, 69);
    private static final Color p = new Color(49, 49, 69);
    private static final Color q = new Color(255, 255, 255);
    private static final Color r = new Color(15, 15, 20);
    private static final float s = 12.0f;
    private static final float t = 8.0f;
    private static final float u = 24.0f;
    private static final float v = 6.0f;
    private static final float w = 8.0f;
    private static final float x = 5.0f;
    private static final float y = 25.0f;
    private static final float z = 103.0f;
    private static final float A = 147.5f;
    private static final float B = 65.0f;
    private static final float C = 9.5f;
    private static final float D = 7.0f;
    private static final float E = 31.0f;
    private static final float F = 10.0f;
    private static final float G = 7.5f;
    private static final float H = 70.0f;
    private static final float I = 12.0f;
    private static final float J = 20.0f;
    private static final long K = 2500;
    private static final long L = 1500;
    private static final long M = 8000;
    private static final int N = 6;
    private static final float O = 2.0f;
    private static final float P = 0.5f;
    private static final float Q = 12.0f;
    private static final float R = 16.0f;
    private static final float S = 2.0f;
    private static final float T = 1.0f;
    private static final float U = 4.0f;
    private static final float V = 3.0f;
    private static final float W = 32.0f;
    private static final float X = 32.0f;
    private static final float Y = 0.0f;
    private float ae;
    private float af;
    private float ag;
    private float ah;
    private float ai;
    private float aj;
    private float ak;
    private float al;
    private float am;
    private float an;
    private float ConfigState;
    private float ConfigProfile;
    private float ConfigJsonCodec;
    private float ConfigManager;
    private float at;
    private float au;
    private float CloudConfigRepository;
    private boolean aA;
    private boolean aB;
    private boolean aC;
    private static final double aT = 0.25d;
    public static int a;
    public static boolean b;
    private byte[] Z = null;
    private Identifier aa = null;
    private NativeImageBackedTexture FriendCard = null;
    private boolean FriendsPanel = false;
    private final AnimationState ad = new AnimationState();
    private boolean ar = false;
    private boolean aw = false;
    private final AnimationState ax = new AnimationState();
    private final AnimationState ay = new AnimationState();
    private final AnimationState az = new AnimationState();
    private final AnimationState aD = new AnimationState();
    private final AnimationState aE = new AnimationState();
    private final AnimationState aF = new AnimationState();
    private final AnimationState aG = new AnimationState();
    private boolean aH = false;
    private final AnimationState aI = new AnimationState();
    private final AnimationState aJ = new AnimationState();
    private String aK = "";
    private final AnimationState aL = new AnimationState();
    private boolean aM = false;
    private double aN = 0.0d;
    private double aO = 0.0d;
    private long aP = 0;
    private long aQ = 0;
    private boolean aR = false;
    private boolean aS = false;

    public MediaPlaybackCardNotification() {
        this.g = 9.5f;
        this.h = 7.0f;
        this.j = Long.MAX_VALUE;
        this.ad.d(0.0d);
        this.ax.d(0.0d);
        this.ay.d(0.0d);
        this.az.d(0.0d);
        this.aD.d(0.0d);
        this.aE.d(0.0d);
        this.aF.d(0.0d);
        this.aG.d(0.0d);
        this.aI.d(0.0d);
        this.aJ.d(1.0d);
        this.aL.d(0.0d);
    }

    private MediaSessionService q() {
        return HudServiceRegistry.MEDIA;
    }

    private MediaPlaybackState r() {
        MediaSessionService mediaSessionServiceQ = q();
        if (mediaSessionServiceQ != null) {
            return mediaSessionServiceQ.i();
        }
        return null;
    }

    @Override
    public void e() {
        boolean z2;
        super.e();
        this.ad.a();
        this.g = 9.5f;
        this.h = 7.0f;
        float fJ = (float) this.ad.j();
        MediaPlaybackState mediaPlaybackStateR = r();
        if (mediaPlaybackStateR != null) {
            byte[] bArrP = mediaPlaybackStateR.p();
            if (mediaPlaybackStateR.q()) {
                if (bArrP == null || bArrP.length <= 0) {
                    this.aa = null;
                    this.Z = null;
                } else {
                    a(bArrP);
                }
                mediaPlaybackStateR.f();
            } else if (this.aa == null && this.Z == null && bArrP != null && bArrP.length > 0) {
                a(bArrP);
            }
            this.ax.a();
            this.ay.a();
            this.az.a();
            this.aD.a();
            this.aE.a();
            this.aF.a();
            this.aG.a();
            this.aI.a();
            this.aJ.a();
            this.aL.a();
            boolean zC = mediaPlaybackStateR.c();
            if (zC != this.aH) {
                this.aH = zC;
                this.aG.a(!zC ? 0.0d : 1.0d, 0.2d, Easing.k);
            }
            boolean z3 = !zC;
            if (z3 && this.aI.j() < 0.5d) {
                this.aI.a(1.0d, 0.2d, Easing.k);
            } else if (!z3 && this.aI.j() > 0.5d) {
                this.aI.a(0.0d, 0.2d, Easing.k);
            }
            String strN = mediaPlaybackStateR.n();
            if (!strN.equals(this.aK)) {
                this.aK = strN;
                this.aJ.d(0.0d);
                this.aJ.a(1.0d, aT, Easing.k);
                this.aR = false;
                this.aS = false;
            }
            FontRenderer fontRenderer = FontManager.b[14];
            FontRenderer fontRenderer2 = FontManager.b[15];
            FontRenderer fontRenderer3 = FontManager.a[13];
            if ((fJ >= 0.2f ? fontRenderer2 : fontRenderer).a(strN) <= a(25.0f, H, fJ)) {
                z2 = false;
            } else {
                z2 = true;
            }
            boolean z4 = z2;
            if (z4 && !this.aR) {
                this.aR = true;
                this.aP = System.currentTimeMillis();
            } else if (!z4) {
                this.aR = false;
            }
            boolean z5 = fontRenderer3.a(mediaPlaybackStateR.o()) > H && fJ > 0.3f;
            if (z5 && !this.aS) {
                this.aS = true;
                this.aQ = System.currentTimeMillis();
            } else {
                if (z5) {
                    return;
                }
                this.aS = false;
            }
        }
    }

    private void s() {
        double d;
        MediaPlaybackState mediaPlaybackStateR = r();
        if (!this.ar || mediaPlaybackStateR == null) {
            this.aA = false;
            this.aB = false;
            this.aC = false;
            this.aM = false;
            return;
        }
        double d2 = this.aN;
        double d3 = this.aO;
        boolean z2 = this.aA;
        this.aA = a(d2, d3, this.ai, this.aj, this.ak, this.ak);
        if (this.aA != z2) {
            this.ax.a(!this.aA ? 0.0d : 1.0d, 0.15d, Easing.k);
        }
        boolean z3 = this.aB;
        this.aB = a(d2, d3, this.al, this.am, this.an, this.an);
        if (this.aB != z3) {
            if (this.aB) {
                d = 1.0d;
            } else {
                d = 0.0d;
            }
            this.ay.a(d, 0.15d, Easing.k);
        }
        boolean z4 = this.aC;
        this.aC = a(d2, d3, this.ConfigState, this.ConfigProfile, this.ConfigJsonCodec, this.ConfigJsonCodec);
        if (this.aC != z4) {
            this.az.a(!this.aC ? 0.0d : 1.0d, 0.15d, Easing.k);
        }
        if (this.aw) {
            boolean z5 = this.aM;
            this.aM = Bool.from((d2 < ((double) (this.ConfigManager - U)) || d2 > ((double) ((this.ConfigManager + this.au) + U)) || d3 < ((double) (this.at - U)) || d3 > ((double) ((this.at + this.CloudConfigRepository) + U))) ? 0 : 1);
            if (this.aM != z5 || mediaPlaybackStateR.y()) {
                this.aL.a((this.aM || mediaPlaybackStateR.y()) ? 1.0d : 0.0d, 0.15d, Easing.k);
            }
        }
    }

    public void m() {
        if (this.ar || this.aw) {
            double d = this.aN;
            double d2 = this.aO;
            boolean z2 = false;
            if (this.ar) {
                if (a(d, d2, this.ai, this.aj, this.ak, this.ak)) {
                    z2 = true;
                }
                if (a(d, d2, this.al, this.am, this.an, this.an)) {
                    z2 = true;
                }
                if (a(d, d2, this.ConfigState, this.ConfigProfile, this.ConfigJsonCodec, this.ConfigJsonCodec)) {
                    z2 = true;
                }
            }
            if (this.aw) {
                if (d < this.ConfigManager - U) {
                } else if (d <= this.ConfigManager + this.au + U && d2 >= this.at - U && d2 <= this.at + this.CloudConfigRepository + U) {
                    z2 = true;
                }
            }
            if (z2) {
                GuiInput.g();
            }
        }
    }

    private void a(byte[] bArr) {
        try {
            if (this.FriendCard != null) {
                this.FriendCard.close();
                this.FriendCard = null;
            }
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(bArr));
            if (bufferedImage == null) {
                return;
            }
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            NativeImage NativeImageVar = new NativeImage(width, height, false);
            for (int i = 0; i < height; i++) {
                for (int i2 = 0; i2 < width; i2++) {
                    NativeImageVar.setColorArgb(i2, i, bufferedImage.getRGB(i2, i));
                }
            }
            RenderSystem.recordRenderCall(() -> {
                this.FriendCard = new NativeImageBackedTexture(NativeImageVar);
                this.aa = Identifier.of("crypt", "crypt");
                c.getTextureManager().registerTexture(this.aa, this.FriendCard);
            });
            this.Z = bArr;
        } catch (Exception e) {
            this.aa = null;
        }
    }

    public void a(double d, double d2) {
        this.aN = d;
        this.aO = d2;
        t();
        s();
    }

    public void n() {
        if (this.FriendsPanel) {
            this.FriendsPanel = false;
            this.ad.a(0.0d, aT, Easing.k);
        }
        this.aA = false;
        this.aB = false;
        this.aC = false;
        this.aM = false;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void t() {
        int i;
        double d;
        boolean z2 = this.FriendsPanel;
        if (this.aN >= this.ae) {
            if (this.aN <= this.ae + this.ag && this.aO >= this.af && this.aO <= this.af + this.ah) {
                i = 1;
            } else {
                i = 0;
            }
            this.FriendsPanel = Bool.from(i);
            if (this.FriendsPanel == z2) {
                if (this.FriendsPanel) {
                    d = 1.0d;
                } else {
                    d = 0.0d;
                }
                this.ad.a(d, aT, Easing.k);
                return;
            }
            return;
        }
        i = 0;
        this.FriendsPanel = Bool.from(i);
    }

    @Override
    public float a() {
        return a(z, A, (float) this.ad.j());
    }

    @Override
    public float b() {
        return a(12.0f, B, (float) this.ad.j());
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, float f3, float f4, float f5) {
        boolean z2;
        this.ae = f - this.g;
        this.af = f2 - this.h;
        this.ag = f3 + (this.g * 2.0f);
        this.ah = f4 + (this.h * 2.0f);
        MediaPlaybackState mediaPlaybackStateR = r();
        if (mediaPlaybackStateR != null) {
            float fJ = (float) this.ad.j();
            int i = (int) (f5 * 255.0f);
            FontRenderer fontRenderer = FontManager.b[15];
            FontRenderer fontRenderer2 = FontManager.b[14];
            FontRenderer fontRenderer3 = FontManager.a[13];
            FontRenderer fontRenderer4 = FontManager.a[11];
            String strN = mediaPlaybackStateR.n();
            String strO = mediaPlaybackStateR.o();
            boolean zC = mediaPlaybackStateR.c();
            float fH = mediaPlaybackStateR.h();
            float fA = a(12.0f, E, fJ);
            float fA2 = a(8.0f, F, fJ);
            float fJ2 = (float) this.aI.j();
            float fMax = fJ2 * 0.08f * Math.max(Y, (fJ - 0.3f) / 0.7f);
            float f6 = T - (fJ2 * 0.3f);
            float f7 = fA * (T - fMax);
            float f8 = (fA - f7) / 2.0f;
            float fA3 = a((f2 + (f4 / 2.0f)) - v, f2, fJ) + f8;
            float f9 = f + f8;
            Color color = new Color(255, 255, 255, (int) (i * f6));
            if (this.aa != null) {
                renderer2D.a(this.aa, f9, fA3, f7, f7, fA2, color, MatrixStackVar);
            } else {
                renderer2D.a(IconTextureRegistry.get("crypt"), f9, fA3, f7, f7, fA2, color, MatrixStackVar);
            }
            float fA4 = a(f + 12.0f + v, f + E + 5.5f, fJ);
            float fA5 = a(((f2 + (f4 / 2.0f)) - (fontRenderer2.b(strN) / U)) - P, (f2 + 15.5f) - 9.0f, fJ);
            float fJ3 = (float) this.aJ.j();
            Color color2 = new Color(m.getRed(), m.getGreen(), m.getBlue(), (int) (i * fJ3));
            float fA6 = a(25.0f, H, fJ);
            float fA7 = a(20.0f, Y, fJ);
            FontRenderer fontRenderer5 = fJ >= 0.2f ? fontRenderer : fontRenderer2;
            float fA8 = fontRenderer5.a(strN);
            if (fA8 <= fA6) {
                z2 = false;
            } else {
                z2 = true;
            }
            boolean z3 = z2;
            float fA9 = a(fA8, fA6 + fA7, this.aP, this.aR);
            if (z3) {
                renderer2D.b().a(fA4, fA5 - V, fA6 + fA7, R, MatrixStackVar);
            }
            fontRenderer5.a(strN, fA4 + fA9, fA5, color2, MatrixStackVar);
            if (z3) {
                renderer2D.b().a(MatrixStackVar);
                Color color3 = new Color(r.getRed(), r.getGreen(), r.getBlue(), 0);
                Color color4 = new Color(r.getRed(), r.getGreen(), r.getBlue(), i);
                renderer2D.a((((fA4 + fA6) + fA7) - 12.0f) + T, fA5 - V, 12.0f, R, Y, color3, color4, color3, color4, MatrixStackVar);
            }
            if (fJ > 0.01f) {
                int iB = (int) (f5 * b(fJ) * fJ3 * 255.0f);
                Color color5 = new Color(n.getRed(), n.getGreen(), n.getBlue(), iB);
                float fA10 = fontRenderer3.a(strO);
                boolean z4 = fA10 > H;
                float fA11 = a(fA10, H, this.aQ, this.aS);
                if (z4) {
                    renderer2D.b().a(fA4, fA5 + 7.0f, H, R, MatrixStackVar);
                }
                fontRenderer3.a(strO, fA4 + fA11, fA5 + F, color5, MatrixStackVar);
                if (z4) {
                    renderer2D.b().a(MatrixStackVar);
                    Color color6 = new Color(r.getRed(), r.getGreen(), r.getBlue(), 0);
                    Color color7 = new Color(r.getRed(), r.getGreen(), r.getBlue(), iB);
                    renderer2D.a((fA4 + H) - 12.0f, fA5 + 7.0f, 12.0f, R, Y, color6, color7, color6, color7, MatrixStackVar);
                }
            }
            float fA12 = a(12.0f, R, fJ);
            float fA13 = a(((f + f3) - 14.5f) - U, ((f + f3) - 14.5f) - U, fJ) + U;
            float fA14 = a(f2 + (f4 / 2.0f), f2 + (fA12 / 2.0f), fJ);
            float[] fArrH = mediaPlaybackStateR.H();
            for (int i2 = 0; i2 < N; i2++) {
                float f10 = fArrH[i2];
                float fMax2 = Math.max(2.0f, f10 * fA12);
                float f11 = fA13 + (i2 * 2.5f);
                float f12 = fA14 - (fMax2 / 2.0f);
                int iA = (int) a(180.0f, 255.0f, f10);
                renderer2D.a(f11, f12, 2.0f, fMax2, T, a(new Color(iA, iA, iA, i), new Color((int) a(o.getRed(), iA, P), (int) a(o.getGreen(), iA, P), (int) a(o.getBlue(), iA, P), i), fJ), MatrixStackVar);
            }
            if (fJ <= 0.01f) {
                this.ar = false;
                this.aw = false;
                return;
            }
            int iB2 = (int) (f5 * b(fJ) * 255.0f);
            float f13 = f2 + E + G;
            float fB = mediaPlaybackStateR.b() * fH;
            String strA = a((int) fB);
            String str = "crypt" + a((int) (fH - fB));
            float fA15 = fontRenderer4.a(strA);
            float fA16 = fontRenderer4.a(str);
            Color color8 = new Color(n.getRed(), n.getGreen(), n.getBlue(), iB2);
            float f14 = (f13 + 2.0f) - U;
            fontRenderer4.a(strA, f, f14, color8, MatrixStackVar);
            fontRenderer4.a(str, (f + f3) - fA16, f14, color8, MatrixStackVar);
            float f15 = f + fA15 + 3.5f;
            float f16 = ((f3 - fA15) - fA16) - 7.0f;
            this.ConfigManager = f15;
            this.at = f13 - P;
            this.au = f16;
            this.CloudConfigRepository = U;
            this.aw = true;
            float fJ4 = (float) this.aL.j();
            float f17 = U + (fJ4 * 2.0f);
            float f18 = (f13 - P) - (fJ4 * T);
            float f19 = V + (fJ4 * T);
            renderer2D.a(f15, f18, f16, f17, f19, new Color(p.getRed(), p.getGreen(), p.getBlue(), iB2), MatrixStackVar);
            float fMax3 = f16 * Math.max(0.04f, mediaPlaybackStateR.b());
            Color color9 = new Color(q.getRed(), q.getGreen(), q.getBlue(), iB2);
            if (fMax3 > Y) {
                renderer2D.a(f15, f18, fMax3, f17, f19, Y, f19, Y, color9, color9, color9, color9, MatrixStackVar);
            }
            float f20 = ((f13 + U) + G) - F;
            float f21 = f + (f3 / 2.0f);
            float fJ5 = (0.9f + (((float) this.ax.j()) * 0.1f)) - (((float) this.aD.j()) * 0.2f);
            int iMin = (int) (255.0f * Math.min(T, fJ5));
            int iMin2 = (int) (iB2 * Math.min(T, fJ5));
            float f22 = ((f21 - R) - Y) - 32.0f;
            float f23 = f20 + Y;
            Color color10 = new Color(iMin, iMin, iMin, iMin2);
            FontRenderer fontRenderer5a = FontManager.e[35];
            fontRenderer5a.a("crypt", f22 + ((32.0f - fontRenderer5a.a("crypt")) / 2.0f), f23 + ((32.0f - (fontRenderer5a.b("crypt") / 2.0f)) / 2.0f), color10, MatrixStackVar);
            float fJ6 = (0.9f + (((float) this.ay.j()) * 0.1f)) - (((float) this.aE.j()) * 0.2f);
            int iMin3 = (int) (255.0f * Math.min(T, fJ6));
            int iMin4 = (int) (iB2 * Math.min(T, fJ6));
            float f24 = f21 - R;
            Color color11 = new Color(iMin3, iMin3, iMin3, iMin4);
            FontRenderer fontRenderer6 = FontManager.e[35];
            String strIcon6 = !zC ? "crypt" : "crypt";
            fontRenderer6.a(strIcon6, f24 + ((32.0f - fontRenderer6.a(strIcon6)) / 2.0f), f20 + ((32.0f - (fontRenderer6.b(strIcon6) / 2.0f)) / 2.0f), color11, MatrixStackVar);
            float fJ7 = (0.9f + (((float) this.az.j()) * 0.1f)) - (((float) this.aF.j()) * 0.2f);
            int iMin5 = (int) (255.0f * Math.min(T, fJ7));
            int iMin6 = (int) (iB2 * Math.min(T, fJ7));
            float f25 = f21 + R + Y;
            Color color12 = new Color(iMin5, iMin5, iMin5, iMin6);
            FontRenderer fontRenderer5c = FontManager.e[35];
            fontRenderer5c.a("crypt", f25 + ((32.0f - fontRenderer5c.a("crypt")) / 2.0f), f23 + ((32.0f - (fontRenderer5c.b("crypt") / 2.0f)) / 2.0f), color12, MatrixStackVar);
            this.ai = f22;
            this.aj = f23;
            this.ak = 32.0f;
            this.al = f24;
            this.am = f20;
            this.an = 32.0f;
            this.ConfigState = f25;
            this.ConfigProfile = f23;
            this.ConfigJsonCodec = 32.0f;
            this.ar = true;
        }
    }

    private String a(int i) {
        return (i / 60) + "crypt" + String.format("crypt", Integer.valueOf(i % 60));
    }

    private float a(float f, float f2, float f3) {
        return f + ((f2 - f) * f3);
    }

    private float a(float f, float f2, long j, boolean z2) {
        if (!z2 || f <= f2) {
            return Y;
        }
        float f3 = f - f2;
        long jCurrentTimeMillis = (System.currentTimeMillis() - j) % M;
        return (-f3) * (jCurrentTimeMillis >= L ? jCurrentTimeMillis >= 4000 ? jCurrentTimeMillis >= 5500 ? T - a((((jCurrentTimeMillis - L) - 2500) - L) / 2500.0f) : 1.0f : a((jCurrentTimeMillis - L) / 2500.0f) : 0.0f);
    }

    private float a(float f) {
        return f >= P ? T - (((float) Math.pow(((-2.0f) * f) + 2.0f, 3.0d)) / 2.0f) : U * f * f * f;
    }

    private float b(float f) {
        float fMax = Math.max(Y, Math.min(T, f));
        return fMax * fMax * (V - (2.0f * fMax));
    }

    private Color a(Color color, Color color2, float f) {
        return new Color((int) a(color.getRed(), color2.getRed(), f), (int) a(color.getGreen(), color2.getGreen(), f), (int) a(color.getBlue(), color2.getBlue(), f), (int) a(color.getAlpha(), color2.getAlpha(), f));
    }

    public boolean a(double d, double d2, int i) {
        if (i != 0) {
            return false;
        }
        MediaSessionService mediaSessionServiceQ = q();
        MediaPlaybackState mediaPlaybackStateR = r();
        if (mediaSessionServiceQ == null || mediaPlaybackStateR == null) {
            return false;
        }
        if (this.aw && mediaPlaybackStateR.h() > Y && d >= this.ConfigManager - U && d <= this.ConfigManager + this.au + U && d2 >= this.at - U && d2 <= this.at + this.CloudConfigRepository + U) {
            mediaSessionServiceQ.a(Math.max(Y, Math.min(T, ((float) (d - ((double) this.ConfigManager))) / this.au)));
            return true;
        }
        if (!this.ar) {
            return false;
        }
        if (a(d, d2, this.ai, this.aj, this.ak, this.ak)) {
            this.aD.d(1.0d);
            this.aD.a(0.0d, 0.2d, Easing.k);
            mediaSessionServiceQ.f();
            return true;
        }
        if (a(d, d2, this.al, this.am, this.an, this.an)) {
            this.aE.d(1.0d);
            this.aE.a(0.0d, 0.2d, Easing.k);
            mediaSessionServiceQ.d();
            return true;
        }
        if (!a(d, d2, this.ConfigState, this.ConfigProfile, this.ConfigJsonCodec, this.ConfigJsonCodec)) {
            return false;
        }
        this.aF.d(1.0d);
        this.aF.a(0.0d, 0.2d, Easing.k);
        mediaSessionServiceQ.e();
        return true;
    }

    public void b(double d, double d2) {
        MediaSessionService mediaSessionServiceQ = q();
        MediaPlaybackState mediaPlaybackStateR = r();
        if (mediaSessionServiceQ == null || mediaPlaybackStateR == null) {
            return;
        }
        if (!mediaPlaybackStateR.y()) {
        } else if (this.au > Y) {
            mediaSessionServiceQ.b(Math.max(Y, Math.min(T, ((float) (d - ((double) this.ConfigManager))) / this.au)));
        }
    }

    public boolean b(double d, double d2, int i) {
        MediaSessionService mediaSessionServiceQ = q();
        MediaPlaybackState mediaPlaybackStateR = r();
        if (mediaSessionServiceQ == null || mediaPlaybackStateR == null) {
            return false;
        }
        if (i != 0 || !mediaPlaybackStateR.y()) {
            return false;
        }
        mediaSessionServiceQ.c(Math.max(Y, Math.min(T, ((float) (d - ((double) this.ConfigManager))) / this.au)));
        return true;
    }

    private boolean a(double d, double d2, float f, float f2, float f3, float f4) {
        if (d < f) {
        } else if (d > f + f3) {
        } else if (d2 >= f2) {
            if (d2 <= f2 + f4) {
                return true;
            }
        }
        return false;
    }

    public void o() {
        if (this.FriendCard != null) {
            this.FriendCard.close();
            this.FriendCard = null;
        }
        this.aa = null;
        this.Z = null;
    }

    public static boolean p() {
        return MediaSessionService.g();
    }

    public static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
