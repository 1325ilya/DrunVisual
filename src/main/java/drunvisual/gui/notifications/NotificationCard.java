package drunvisual.gui.notifications;

import java.awt.Color;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.core.Bool;
import drunvisual.gui.core.GuiInput;
import drunvisual.gui.core.GuiInteractionState;
import drunvisual.hud.notifications.Notification;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.theme.Theme;
import drunvisual.util.MarqueeText;

public class NotificationCard {
    public static final float a = 27.5f;
    private static final float d = 7.5f;
    private static final float e = 4.0f;
    private static final float f = 20.0f;
    private static final float g = 6.5f;
    private static final float h = 12.0f;
    private static final float i = 5.0f;
    private static final float j = -6.0f;
    private final Notification k;
    private final AnimationState l = new AnimationState();
    private final AnimationState m = new AnimationState();
    private final MarqueeText n = new MarqueeText();
    private boolean o = false;
    private boolean p = false;
    public static int b;
    public static boolean c;

    public NotificationCard(Notification notification) {
        this.k = notification;
    }

    public Notification a() {
        return this.k;
    }

    public void a(boolean z) {
        if (this.p != z) {
            this.p = z;
            this.m.a(!z ? 0.0d : 1.0d, 0.2d, Easing.h);
        }
    }

    public boolean b() {
        return this.p;
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, int i2, int i3, float f5) {
        Color colorA;
        Color colorA2;
        Color colorA3;
        Color colorA4;
        this.l.a();
        this.m.a();
        boolean zB = GuiInteractionState.a().b();
        boolean zJ = this.k.j();
        int i4 = (zB || !GuiInput.a(f2, f3, f4, 27.5f, (double) i2, (double) i3)) ? 0 : 1;
        if (zB && this.o) {
            this.l.a(0.0d, 0.15d, Easing.h);
            this.o = false;
        } else if (!zB && Bool.from(i4) != this.o) {
            this.l.a(i4 == 0 ? 0.0d : 1.0d, 0.15d, Easing.h);
            this.o = Bool.from(i4);
        }
        float fJ = (float) this.l.j();
        float fJ2 = (float) this.m.j();
        int i5 = (int) (255.0f * f5);
        float fMax = Math.max(fJ * 0.6f, fJ2);
        if (zJ) {
            Color colorA5 = Theme.a(this.k.e(), (int) (Math.max(0.3f, (fMax * 0.4f) + 0.3f) * 255.0f * f5));
            renderer2D.a(f2 - 0.5f, f3 - 0.5f, f4 + 1.0f, 28.5f, d, colorA5, colorA5, colorA5, colorA5, MatrixStackVar);
            renderer2D.a(f2, f3, f4, 27.5f, d, Theme.a(Theme.e, i5), Theme.a(Theme.e, i5), Theme.a(Theme.f, i5), Theme.a(Theme.f, i5), MatrixStackVar);
        } else if (fMax > 0.01f) {
            if (fJ2 <= fJ * 0.6f) {
                float f6 = fJ * 0.6f;
                colorA = Theme.a(Theme.u, f6 * f5);
                colorA2 = Theme.a(Theme.v, f6 * f5);
                colorA3 = Theme.a(Theme.k, f6 * f5);
                colorA4 = Theme.a(Theme.l, f6 * f5);
            } else {
                colorA = Theme.a(Theme.s, fJ2 * f5);
                colorA2 = Theme.a(Theme.t, fJ2 * f5);
                colorA3 = Theme.a(Theme.i, fJ2 * f5);
                colorA4 = Theme.a(Theme.j, fJ2 * f5);
            }
            renderer2D.a(f2 - 0.5f, f3 - 0.5f, f4 + 1.0f, 28.5f, d, colorA, colorA, colorA2, colorA2, MatrixStackVar);
            renderer2D.a(f2, f3, f4, 27.5f, d, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
        }
        float f7 = f2 + e;
        a(MatrixStackVar, renderer2D, f7, f3 + e, i5);
        FontRenderer fontRenderer = FontManager.a[12];
        FontRenderer fontRenderer2 = FontManager.a[9];
        float f8 = f7 + 20.0f + i;
        String strA = this.k.a();
        String strG = this.k.g();
        if (this.k.j()) {
            long jP = this.k.p();
            if (jP > 0) {
                int i6 = (int) (jP / 1000);
                int i7 = i6 / 60;
                int i8 = i6 % 60;
                strA = i7 <= 0 ? strA + " (" + i8 + " сек)" : strA + " (" + i7 + " мин " + i8 + " сек)";
            }
        }
        float fB = fontRenderer.b(strA);
        float fB2 = f3 + ((27.5f - (((fB + j) + fontRenderer2.b(strG)) - e)) / 2.0f);
        float f9 = fB2 + fB + j;
        Theme.a(Theme.a, i5);
        Color colorA6 = Theme.a(Theme.b, i5);
        float f10 = ((f2 + f4) - f8) - e;
        this.n.a(Bool.from((i4 != 0 || this.p) ? 1 : 0));
        this.n.a(MatrixStackVar, renderer2D, fontRenderer, strA, f8, fB2, f10, 1.0f, Theme.a, f5);
        fontRenderer2.a(strG, f8, f9, colorA6, MatrixStackVar);
        if (i4 == 0 || this.p || zB) {
            return;
        }
        GuiInput.g();
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, int i2) {
        Color colorE = this.k.e();
        Color colorA = Theme.a(colorE, i2);
        Color colorA2 = Theme.a(Theme.c(colorE, 150), i2);
        Color colorB = Theme.b(colorE, 50);
        Color colorA3 = Theme.a(colorB, (int) ((50 * i2) / 255.0f));
        Color colorA4 = Theme.a(colorB, (int) ((10 * i2) / 255.0f));
        renderer2D.a(f2 - 0.5f, f3 - 0.5f, 21.0f, 21.0f, g, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
        renderer2D.a(f2, f3, 20.0f, 20.0f, g, colorA, colorA, colorA2, colorA2, MatrixStackVar);
        FontRenderer fontRenderer = FontManager.e[22];
        String strB = this.k.f().b();
        float f4 = f2 + e;
        fontRenderer.a(strB, f4 + 0.5f, f3 + e, Theme.a(Theme.aa, i2), MatrixStackVar);
    }

    public boolean a(float f2, float f3, float f4, int i2, int i3) {
        return GuiInput.a(f2, f3, f4, 27.5f, i2, i3);
    }

    public boolean c() {
        int i2;
        if (this.p) {
            i2 = 0;
        } else {
            i2 = 1;
        }
        return Bool.from(i2);
    }

    public static String a(String str, String str2, int i2, int i3, int i4, int i5) {
        return null;
    }
}
