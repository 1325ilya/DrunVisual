package drunvisual.hud.notifications;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Queue;
import lombok.Generated;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.module.ModuleRegistry;
import ru.drunvisual.mixin.accessor.BossBarHudAccessor;
import ru.drunvisual.mixin.accessor.PlayerListHudAccessor;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.core.Bool;
import drunvisual.hud.core.HudElementManager;
import drunvisual.hud.core.HudServiceRegistry;
import drunvisual.media.MediaPlaybackState;
import drunvisual.media.MediaSessionService;
import drunvisual.render.Renderer2D;

public class HudNotificationCenter {
    private static HudNotificationCenter a;
    private static final MinecraftClient b = MinecraftClient.getInstance();
    private static final Color c = new Color(19, 19, 25);
    private static final Color d = new Color(19, 19, 26);
    private static final Color e = new Color(13, 13, 17);
    private static final Color f = new Color(17, 17, 23);
    private static final float g = 12.0f;
    private static final float h = 7.0f;
    private static final float i = 19.0f;
    private float s;
    private float t;
    private float u;
    private float v;
    private static final long z = 3000;
    private static final long D = 300;
    private final WatermarkOverlay j = new WatermarkOverlay();
    private final Queue<HudNotification> k = new LinkedList();
    private HudNotification l = null;
    private ActionNotification m = null;
    private final AnimationState n = new AnimationState();
    private final AnimationState o = new AnimationState();
    private final AnimationState p = new AnimationState();
    private final AnimationState q = new AnimationState();
    private final AnimationState r = new AnimationState();
    private final HudNotificationSettingsPanel w = new HudNotificationSettingsPanel();
    private long x = 0;
    private boolean y = false;
    private int A = 0;
    private int B = 0;
    private long C = 0;
    private final AnimationState E = new AnimationState();
    private boolean F = false;

    public HudNotificationCenter() {
        a = this;
        float fC = this.j.c();
        float fD = this.j.d();
        this.n.d(fC);
        this.o.d(fD);
        this.p.d(7.0d);
        this.q.d(1.0d);
        this.r.d(0.0d);
        this.E.d(1.0d);
        this.s = fC;
        this.t = fD;
        this.u = 7.0f;
    }

    public static HudNotificationCenter a() {
        if (a != null) {
            return a;
        }
        HudNotificationCenter hudNotificationCenter = new HudNotificationCenter();
        a = hudNotificationCenter;
        return hudNotificationCenter;
    }

    public static void a(String str, boolean z2) {
    }

    public static void a(String str, String str2, String str3) {
    }

    public static void a(ActionNotification actionNotification) {
    }

    public static void a(String str) {
    }

    public static void a(String str, String str2) {
    }

    public static void b() {
    }

    public static void c() {
    }

    @Deprecated
    public static void b(String str, String str2) {
        b();
    }

    private void b(HudNotification hudNotification) {
    }

    public boolean a(double d2, double d3, int i2) {
        return false;
    }

    private boolean b(double d2, double d3) {
        return Bool.from((d2 < ((double) this.v) || d2 > ((double) (this.v + this.s)) || d3 < ((double) this.u) || d3 > ((double) (this.u + this.t))) ? 0 : 1);
    }

    public void b(double d2, double d3, int i2) {
        if (this.w.d()) {
            this.w.c((int) d2, (int) d3);
        }
        if (this.l instanceof MediaPlaybackCardNotification) {
            ((MediaPlaybackCardNotification) this.l).b(d2, d3, i2);
        }
    }

    public boolean a(double d2, double d3, double d4) {
        return this.w.d() ? this.w.a((float) d4, (int) d2, (int) d3) : false;
    }

    public void a(double d2, double d3, double d4, double d5) {
        if (this.w.d()) {
            this.w.a((int) d2, (int) d3, d4, d5);
        }
        if (this.l instanceof MediaPlaybackCardNotification) {
            ((MediaPlaybackCardNotification) this.l).b(d2, d3);
        }
    }

    public void d() {
    }

    private void b(String str, boolean z2) {
    }

    public void a(HudNotification hudNotification) {
        b(hudNotification);
    }

    private boolean j() {
        MediaSessionService mediaSessionService;
        MediaPlaybackState mediaPlaybackStateI;
        if (this.w.i() && !this.y && (mediaSessionService = HudServiceRegistry.MEDIA) != null && (mediaPlaybackStateI = mediaSessionService.i()) != null) {
            return mediaPlaybackStateI.c();
        }
        return false;
    }

    private void k() {
        MediaSessionService mediaSessionService;
        MediaPlaybackState mediaPlaybackStateI;
        if (!this.w.i() || (mediaSessionService = HudServiceRegistry.MEDIA) == null || (mediaPlaybackStateI = mediaSessionService.i()) == null) {
            return;
        }
        if (mediaPlaybackStateI.k()) {
            this.y = false;
            b();
        }
        if (mediaPlaybackStateI.l()) {
            this.y = false;
            c();
        }
        if (!this.y || !mediaPlaybackStateI.c() || this.w.d() || System.currentTimeMillis() - this.x < z) {
            return;
        }
        this.y = false;
        b();
    }

    private void l() {
        k();
        if (this.m != null) {
            this.m.e();
            if (this.m.g() || this.m.h()) {
                this.m = null;
            }
        }
        if (this.l != null) {
            this.l.e();
            if (this.l instanceof MediaPlaybackCardNotification) {
                float fC = this.l.c();
                float fD = this.l.d();
                if (this.r.j() >= 0.99d) {
                    this.n.d(fC);
                    this.o.d(fD);
                }
            } else {
                float fC2 = this.l.c();
                float fD2 = this.l.d();
                if (Math.abs(this.n.i() - ((double) fC2)) > 0.10000000149011612d) {
                    this.n.a(fC2, 0.25d, Easing.k);
                }
                if (Math.abs(this.o.i() - ((double) fD2)) > 0.10000000149011612d) {
                    this.o.a(fD2, 0.25d, Easing.k);
                }
            }
            if (this.l.g()) {
                this.l = null;
                if (this.m != null && !this.m.h()) {
                    this.l = this.m;
                    this.m = null;
                    float fC3 = this.l.c();
                    float fD3 = this.l.d();
                    this.n.a(fC3, 0.3d, Easing.k);
                    this.o.a(fD3, 0.3d, Easing.k);
                    this.r.a(1.0d, 0.2d, Easing.k);
                    this.q.a(0.0d, 0.15d, Easing.k);
                } else if (j()) {
                    this.l = new MediaPlaybackCardNotification();
                    float fC4 = this.l.c();
                    float fD4 = this.l.d();
                    this.n.a(fC4, 0.3d, Easing.k);
                    this.o.a(fD4, 0.3d, Easing.k);
                    this.r.d(0.0d);
                    this.r.a(1.0d, 0.2d, Easing.k);
                    this.q.a(0.0d, 0.15d, Easing.k);
                } else {
                    this.r.a(0.0d, 0.2d, Easing.k);
                    this.q.a(1.0d, 0.2d, Easing.k);
                    float fC5 = this.j.c();
                    float fD5 = this.j.d();
                    this.n.a(fC5, 0.3d, Easing.k);
                    this.o.a(fD5, 0.3d, Easing.k);
                }
            }
        }
        if (this.l == null && !this.k.isEmpty()) {
            this.l = this.k.poll();
            float fC6 = this.l.c();
            float fD6 = this.l.d();
            this.n.a(fC6, 0.3d, Easing.k);
            this.o.a(fD6, 0.3d, Easing.k);
            this.r.a(1.0d, 0.2d, Easing.k);
            this.q.a(0.0d, 0.15d, Easing.k);
        }
        this.n.a();
        this.o.a();
        this.q.a();
        this.r.a();
        float fN = n();
        if (Math.abs(this.u - fN) > 0.5f) {
            this.p.a(fN, 0.3d, Easing.k, true);
        }
        this.p.a();
        this.s = (float) this.n.j();
        this.t = (float) this.o.j();
        this.u = (float) this.p.j();
        if (this.l == null) {
            float fC7 = this.j.c();
            if (Math.abs(this.s - fC7) > 0.5f) {
                this.n.a(fC7, 0.3d, Easing.k, true);
            }
        }
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D) {
        if (b.world == null || b.player == null) {
            return;
        }
        l();
        boolean zO = o();
        if (zO != this.F) {
            if (zO) {
                this.E.a(0.0d, 0.15d, Easing.k);
                if (this.w.d()) {
                    this.w.b();
                }
            } else {
                this.E.a(1.0d, 0.2d, Easing.k);
            }
            this.F = zO;
        }
        this.E.a();
        float fJ = (float) this.E.j();
        if (fJ >= 0.01f) {
            float fRound = (float) (Math.round(((double) (((b.getWindow().getFramebufferWidth() / 2.0f) / 2.0f) - (this.s / 2.0f))) * 2.0d) / 2.0d);
            float f2 = this.u;
            this.v = fRound;
            float fJ2 = ((float) this.q.j()) * fJ;
            float fJ3 = ((float) this.r.j()) * fJ;
            WatermarkOverlay watermarkOverlay = this.j;
            if (watermarkOverlay instanceof WatermarkOverlay) {
                watermarkOverlay.renderStandalone(MatrixStackVar, renderer2D, fRound, f2, fJ);
            } else {
                Color colorA = a(c, fJ);
                Color colorA2 = a(d, fJ);
                Color colorA3 = a(e, fJ);
                Color colorA4 = a(f, fJ);
                renderer2D.a(fRound - (2 / 2.0f), f2 - (2 / 2.0f), this.s + 2, this.t + 2, g, colorA, colorA, colorA2, colorA2, MatrixStackVar);
                renderer2D.a(fRound, f2, this.s, this.t, g, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
                renderer2D.b().a(fRound, f2, this.s, this.t, g, MatrixStackVar);
                if (fJ2 > 0.01f) {
                    this.j.a(MatrixStackVar, renderer2D, fRound + this.j.j(), f2 + this.j.k(), this.s - (this.j.j() * 2.0f), this.t - (this.j.k() * 2.0f), fJ2);
                }
            }
            if (this.l != null && fJ3 > 0.01f) {
                this.l.a(MatrixStackVar, renderer2D, fRound + this.l.j(), f2 + this.l.k(), this.s - (this.l.j() * 2.0f), this.t - (this.l.k() * 2.0f), fJ3);
            }
            renderer2D.b().a(MatrixStackVar);
            if (this.w.d()) {
                this.w.b(fRound, f2, this.s, this.t);
                double dGetScaleFactor = b.getWindow().getScaleFactor();
                this.w.a(MatrixStackVar, renderer2D, (int) ((b.mouse.getX() * dGetScaleFactor) / 2.0d), (int) ((b.mouse.getY() * dGetScaleFactor) / 2.0d));
            }
        }
    }

    public float e() {
        return this.s;
    }

    public float f() {
        return this.t;
    }

    private int m() {
        BossBarHudAccessor bossBarHudAccessorGetBossBarHud;
        if (b.inGameHud == null) {
            return 0;
        }
        if ((ModuleRegistry.RENDER_TWEAKS == null || !ModuleRegistry.RENDER_TWEAKS.s()) && (bossBarHudAccessorGetBossBarHud = (BossBarHudAccessor) (Object) b.inGameHud.getBossBarHud()) != null) {
            return bossBarHudAccessorGetBossBarHud.getBossBars().size();
        }
        return 0;
    }

    private float n() {
        int iM = m();
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (iM > this.B) {
            this.B = iM;
            this.C = 0L;
        } else if (iM >= this.B) {
            this.C = 0L;
        } else if (this.C == 0) {
            this.C = jCurrentTimeMillis;
        } else if (jCurrentTimeMillis - this.C >= D) {
            this.B = iM;
            this.C = 0L;
        }
        this.A = iM;
        if (this.B == 0) {
            return 7.0f;
        }
        return 7.0f + ((float) ((((double) (this.B * i)) * b.getWindow().getScaleFactor()) / 2.0d));
    }

    public void a(double d2, double d3) {
        HudNotification hudNotification = this.l;
        if (hudNotification instanceof MediaPlaybackCardNotification) {
            MediaPlaybackCardNotification mediaPlaybackCardNotification = (MediaPlaybackCardNotification) hudNotification;
            if (HudElementManager.a().p()) {
                mediaPlaybackCardNotification.n();
            } else {
                mediaPlaybackCardNotification.a(d2, d3);
                mediaPlaybackCardNotification.m();
            }
        }
        HudNotification hudNotification2 = this.l;
        if (hudNotification2 instanceof ActionNotification) {
            ((ActionNotification) hudNotification2).n();
        }
    }

    public void g() {
        if (this.l instanceof MediaPlaybackCardNotification) {
            ((MediaPlaybackCardNotification) this.l).n();
        }
    }

    private Color a(Color color, float f2) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (color.getAlpha() * f2));
    }

    private boolean o() {
        PlayerListHudAccessor playerListHudAccessorGetPlayerListHud;
        if (b.inGameHud != null && (playerListHudAccessorGetPlayerListHud = (PlayerListHudAccessor) (Object) b.inGameHud.getPlayerListHud()) != null) {
            return playerListHudAccessorGetPlayerListHud.isVisible();
        }
        return false;
    }

    @Generated
    public HudNotification h() {
        return this.l;
    }

    @Generated
    public HudNotificationSettingsPanel i() {
        return this.w;
    }

    public static String a(String str, String str2, int i2, int i3, int i4, int i5) {
        return null;
    }
}
