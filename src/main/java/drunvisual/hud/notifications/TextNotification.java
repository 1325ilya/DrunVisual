package drunvisual.hud.notifications;

import java.awt.Color;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;

public class TextNotification extends HudNotification {
    private static final Color m = new Color(87, 215, 106);
    private static final Color n = new Color(255, 255, 255);
    private static final Color o = new Color(223, 223, 243);
    private static final Color p = new Color(255, 255, 255, 77);
    private static final float q = 12.0f;
    private static final float r = 3.0f;
    private static final float s = 3.0f;
    private final String t;
    private final String u;
    private final String v;
    public static int a;
    public static boolean b;

    public TextNotification(String str, String str2, String str3) {
        this.t = str;
        this.u = str2;
        this.v = str3;
        this.g = 8.0f;
        this.h = 4.5f;
        this.j = 3000L;
    }

    @Override
    public float a() {
        FontRenderer fontRenderer = FontManager.b[15];
        float fA = 15.0f + FontManager.a[15].a(this.t);
        return fA + 15.0f + fontRenderer.a(this.u) + 3.0f + fontRenderer.a("ᗴ∪ᗎ") + 3.0f + fontRenderer.a(this.v) + 20.0f;
    }

    @Override
    public float b() {
        return 13.0f;
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, float f3, float f4, float f5) {
        FontRenderer fontRenderer = FontManager.b[15];
        FontRenderer fontRenderer2 = FontManager.a[15];
        float f6 = f2 + (f4 / 2.0f);
        int i = (int) (f5 * 255.0f);
        Color color = new Color(223, 223, 255, i);
        Color color2 = new Color(255, 255, 255, i);
        Color color3 = new Color(p.getRed(), p.getGreen(), p.getBlue(), (int) (p.getAlpha() * f5));
        float fB = f6 - (fontRenderer2.b(this.t) / 4.0f);
        float f7 = f6 - 6.0f;
        FontManager.e[22].a("crypt", f, f7 + 0.5f, new Color(m.getRed(), m.getGreen(), m.getBlue(), i), MatrixStackVar);
        fontRenderer2.a(this.t, f + 15.0f, fB, color, MatrixStackVar);
        float fA = fontRenderer.a("crypt");
        float fA2 = fontRenderer.a(this.u);
        float fA3 = (f + f3) - ((((fA2 + 3.0f) + fA) + 3.0f) + fontRenderer.a(this.v));
        fontRenderer.a(this.u, fA3, fB, color2, MatrixStackVar);
        float separatorX = fA3 + fA2 + 3.0f;
        fontRenderer.a("crypt", separatorX, fB, color3, MatrixStackVar);
        fontRenderer.a(this.v, separatorX + fA + 3.0f, fB, color2, MatrixStackVar);
    }

    public static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
