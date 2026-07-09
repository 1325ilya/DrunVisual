package drunvisual.hud.notifications;

import java.awt.Color;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.util.ColorUtils;

public class ToggleTextNotification extends HudNotification {
    private static final Color m = new Color(87, 215, 106);
    private static final Color n = new Color(215, 87, 87);
    private static final Color o = new Color(255, 255, 255);
    private static final float p = 6.5f;
    private static final float q = 9.5f;
    private static final float r = 13.0f;
    private static final float s = 10.0f;
    private final String t;
    private boolean u;
    private final AnimationState v = new AnimationState();
    public static int a;
    public static boolean b;

    public ToggleTextNotification(String str, boolean z) {
        this.t = str;
        this.u = z;
        this.g = 8.0f;
        this.h = 4.5f;
        this.j = 1000L;
        this.v.d(!z ? 0.0d : 1.0d);
    }

    public String m() {
        return this.t;
    }

    public boolean n() {
        return this.u;
    }

    public void a(boolean z) {
        if (this.u != z) {
            this.u = z;
            this.v.a(!z ? 0.0d : 1.0d, 0.15d, Easing.h);
            this.k = System.currentTimeMillis();
            this.l = false;
        }
    }

    @Override
    public void e() {
        super.e();
        this.v.a();
    }

    @Override
    public float a() {
        FontRenderer fontRenderer = FontManager.b[14];
        FontRenderer fontRenderer2 = FontManager.a[12];
        float fA = fontRenderer.a(this.t);
        return fA + s + fontRenderer2.a("crypt") + r + 60.0f;
    }

    @Override
    public float b() {
        return r;
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, float f3, float f4, float f5) {
        FontRenderer fontRenderer = FontManager.b[14];
        FontRenderer fontRenderer2 = FontManager.a[12];
        int i = (int) (f5 * 255.0f);
        float f6 = f2 + (f4 / 2.0f);
        Color color = new Color(o.getRed(), o.getGreen(), o.getBlue(), i);
        fontRenderer.a(this.t, f, f6 - (fontRenderer.b(this.t) / 4.0f), color, MatrixStackVar);
        float fJ = (float) this.v.j();
        String strA = !this.u ? "crypt" : "crypt";
        float fA = fontRenderer2.a(strA) + r;
        Color colorA = ColorUtils.a(n, m, fJ);
        Color color2 = new Color(colorA.getRed(), colorA.getGreen(), colorA.getBlue(), i);
        float rectX = ((f + f3) - fA) + 2.0f;
        renderer2D.a(rectX, f6 - p, fA, r, 9.5f, color2, MatrixStackVar);
        fontRenderer2.a(strA, rectX + p, f6 - (fontRenderer2.b(strA) / 4.0f), color, MatrixStackVar);
    }

    public static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
