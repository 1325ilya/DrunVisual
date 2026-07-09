package drunvisual.hud.notifications;

import java.awt.Color;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.core.Bool;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;

public class MediaPlaybackNotification extends HudNotification {
    private static final Color m = new Color(255, 255, 255);
    private static final Color n = new Color(223, 223, 243);
    private static final Color o = new Color(255, 255, 255, 77);
    private static final Color p = new Color(76, 46, 212, 100);
    private static final Color q = new Color(115, 83, 255);
    private static final Color r = new Color(70, 46, 174);
    private static final float s = 30.0f;
    private static final float t = 5.0f;
    private static final float u = 5.0f;
    private final String v = "crypt";
    private final AnimationState w = new AnimationState();
    private boolean x = true;
    public static int a;
    public static boolean b;

    public MediaPlaybackNotification() {
        this.g = 7.0f;
        this.h = 3.0f;
        this.j = Long.MAX_VALUE;
        this.w.d(1.0d);
    }

    private boolean m() {
        HudNotificationCenter hudNotificationCenterA = HudNotificationCenter.a();
        return Bool.from((hudNotificationCenterA == null || hudNotificationCenterA.i().j()) ? 1 : 0);
    }

    @Override
    public float a() {
        double d;
        FontRenderer fontRenderer = FontManager.b[15];
        FontRenderer fontRenderer2 = FontManager.a[15];
        float fA = 15.0f + 5.0f + fontRenderer.a("crypt") + 1.5f;
        boolean zM = m();
        if (zM != this.x) {
            this.x = zM;
            if (zM) {
                d = 1.0d;
            } else {
                d = 0.0d;
            }
            this.w.a(d, 0.15d, Easing.k);
        }
        this.w.a();
        float fJ = (float) this.w.j();
        if (fJ < 0.01f) {
            return fA;
        }
        float fA2 = fontRenderer2.a("crypt");
        int iO = o();
        int iN = n();
        String str = iO + "crypt";
        String str2 = iN + "crypt";
        return fA + ((5.0f + fA2 + 5.0f + fontRenderer2.a(str) + 5.0f + fA2 + 5.0f + fontRenderer2.a(str2)) * fJ);
    }

    @Override
    public float b() {
        return 16.0f;
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, float f3, float f4, float f5) {
        FontRenderer fontRenderer = FontManager.b[15];
        FontRenderer fontRenderer2 = FontManager.a[15];
        float f6 = f2 + (f4 / 2.0f);
        int i = (int) (f5 * 255.0f);
        FontRenderer fontRenderer3 = FontManager.e[30];
        float fA = fontRenderer3.a("crypt");
        float fB = f6 - (fontRenderer3.b("crypt") / 4.0f);
        renderer2D.a(f + 1.0f, fB + 2.0f, 6.5f, a(p, f5), MatrixStackVar);
        fontRenderer3.a("crypt", f + 1.0f, fB, a(q, f5), a(r, f5), MatrixStackVar);
        float f7 = f + fA + 5.0f;
        Color color = new Color(m.getRed(), m.getGreen(), m.getBlue(), i);
        new Color(n.getRed(), n.getGreen(), n.getBlue(), i);
        new Color(o.getRed(), o.getGreen(), o.getBlue(), (int) (o.getAlpha() * f5));
        float fB2 = (f6 - (fontRenderer.b("crypt") / 4.0f)) - 0.5f;
        fontRenderer.a("crypt", f7, fB2, color, MatrixStackVar);
        float fJ = (float) this.w.j();
        if (fJ >= 0.01f) {
            float f8 = f5 * fJ;
            Color color2 = new Color(n.getRed(), n.getGreen(), n.getBlue(), (int) (f8 * 255.0f));
            Color color3 = new Color(o.getRed(), o.getGreen(), o.getBlue(), (int) (o.getAlpha() * f8));
            float fA2 = f7 + fontRenderer.a("crypt") + 5.0f;
            fontRenderer2.a("crypt", fA2, fB2, color3, MatrixStackVar);
            float fA3 = fA2 + fontRenderer2.a("crypt") + 5.0f;
            String str = o() + "crypt";
            fontRenderer2.a(str, fA3, fB2, color2, MatrixStackVar);
            float fA4 = fA3 + fontRenderer2.a(str) + 5.0f;
            fontRenderer2.a("ﴋ⢺ﵮ", fA4, fB2, color3, MatrixStackVar);
            fontRenderer2.a(n() + "crypt", fA4 + fontRenderer2.a("crypt") + 5.0f, fB2, color2, MatrixStackVar);
        }
    }

    private int n() {
        if (c.fpsDebugString == null) {
            return 0;
        }
        try {
            return Integer.parseInt(c.fpsDebugString.split("crypt")[0]);
        } catch (Exception e) {
            return 0;
        }
    }

    private int o() {
        ClientPlayNetworkHandler ClientPlayNetworkHandlerVarGetNetworkHandler;
        PlayerListEntry PlayerListEntryVarGetPlayerListEntry;
        if (c.player == null || (ClientPlayNetworkHandlerVarGetNetworkHandler = c.getNetworkHandler()) == null || (PlayerListEntryVarGetPlayerListEntry = ClientPlayNetworkHandlerVarGetNetworkHandler.getPlayerListEntry(c.player.getUuid())) == null) {
            return 0;
        }
        return PlayerListEntryVarGetPlayerListEntry.getLatency();
    }

    public static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
