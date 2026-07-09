package drunvisual.gui.events;

import java.awt.Color;
import java.util.function.Consumer;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.gui.events.EventInfo;
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

public class EventCard {
    public static final float a = 36.5f;
    private static final float d = 6.5f;
    private static final float e = 1.5f;
    private static final float f = 19.0f;
    private static final float g = 2.0f;
    private static final float h = 6.5f;
    private static final float i = 9.0f;
    private static final float j = -6.0f;
    private static final float k = 19.0f;
    private static final float l = 5.0f;
    private final EventInfo m;
    private final AnimationState n = new AnimationState();
    private final AnimationState o = new AnimationState();
    private boolean p = false;
    private boolean q = false;
    private Consumer<EventInfo> r;
    public static int b;
    public static boolean c;

    public EventCard(EventInfo eventInfo) {
        this.m = eventInfo;
    }

    public EventInfo a() {
        return this.m;
    }

    public void a(Consumer<EventInfo> consumer) {
        this.r = consumer;
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, int i2, int i3, float f5) {
        this.n.a();
        this.o.a();
        int i6 = (int) (255.0f * f5);
        boolean zB = GuiInteractionState.a().b();
        int i4 = (!zB && GuiInput.a(f2, f3, f4, 36.5f, (double) i2, (double) i3)) ? 1 : 0;
        float rightEdge = ((f2 + f4) - i) - 19.0f;
        float topEdge = (f3 + 8.75f) + 0.7f;
        int i5 = (!zB && GuiInput.a(rightEdge, topEdge, 19.0f, 19.0f, (double) i2, (double) i3)) ? 1 : 0;
        if (zB && this.p) {
            this.n.a(0.0d, 0.15d, Easing.h);
            this.p = false;
        } else if (!zB && Bool.from(i4) != this.p) {
            this.n.a(i4 == 0 ? 0.0d : 1.0d, 0.15d, Easing.h);
            this.p = Bool.from(i4);
        }
        if (zB && this.q) {
            this.o.a(0.0d, 0.15d, Easing.h);
            this.q = false;
        } else if (!zB && Bool.from(i5) != this.q) {
            this.o.a(i5 == 0 ? 0.0d : 1.0d, 0.15d, Easing.h);
            this.q = Bool.from(i5);
        }
        float fJ = (float) this.n.j();
        Color color = new Color(17, 17, 22);
        Color color2 = new Color(14, 14, 19);
        Color color3 = new Color(22, 22, 29);
        Color colorA = ColorUtils.a(color, Theme.g, fJ);
        Color colorA2 = ColorUtils.a(color2, Theme.h, fJ);
        Color colorA3 = Theme.a(colorA, i6);
        Color colorA4 = Theme.a(colorA2, i6);
        renderer2D.a(f2 - 0.5f, f3 - 0.5f, f4 + 1.0f, 37.5f, 6.5f, color3, color3, color3, color3, MatrixStackVar);
        renderer2D.a(f2, f3, f4, 36.5f, 6.5f, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
        FontRenderer fontRenderer = FontManager.a[11];
        FontRenderer fontRenderer2 = FontManager.a[15];
        float f6 = f2 + 6.5f + 3.0f;
        String strValueOf;
        String strA;
        if (this.m.c() == EventInfo.EventState.UPCOMING) {
            strValueOf = this.m.h();
            strA = this.m.j();
        } else {
            strValueOf = String.valueOf(this.m.b());
            strA = this.m.a();
        }
        float fB = fontRenderer.b(strValueOf);
        float fB2 = f3 + ((36.5f - (((fB + j) + fontRenderer2.b(strA)) - 8.0f)) / g);
        float f7 = fB2 + fB + j;
        Color colorA5 = Theme.a(Theme.b, i6);
        Color colorA6 = Theme.a(Theme.a, i6);
        fontRenderer.a(strValueOf, f6, fB2, colorA5, MatrixStackVar);
        fontRenderer2.a(strA, f6, f7, colorA6, MatrixStackVar);
        String text = this.m.g();
        fontRenderer.a(text, (rightEdge - l) - fontRenderer.a(text), (f3 + 18.25f) - (fontRenderer.b(text) / 4.0f), colorA5, MatrixStackVar);
        a(MatrixStackVar, renderer2D, (int) rightEdge, (int) topEdge, f5);
        if (i5 != 0 && !zB) {
            GuiInput.g();
        }
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4) {
        int i2 = (int) (255.0f * f4);
        float fJ = (float) this.o.j();
        Color colorA = ColorUtils.a(Theme.f, Theme.I, fJ);
        Color colorA2 = ColorUtils.a(Theme.e, Theme.K, fJ);
        Color colorA3 = Theme.a(colorA, i2);
        Color colorA4 = Theme.a(colorA2, i2);
        Color color = new Color(19, 19, 25);
        renderer2D.a(f2 - 0.5f, f3 - 0.5f, 20.0f, 20.0f, 6.0f, color, color, color, color, MatrixStackVar);
        renderer2D.a(f2, f3, 19.0f, 19.0f, 6.0f, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
        Color colorA5 = Theme.a(ColorUtils.a(Theme.b, Theme.O, fJ), i2);
        FontRenderer fontRenderer = FontManager.e[12];
        float fA = fontRenderer.a(IconGlyphs.A);
        float fB = fontRenderer.b(IconGlyphs.A);
        fontRenderer.a(IconGlyphs.A, f2 + ((19.0f - fA) / g), f3 + ((19.0f - fB) / g) + 2.5f, colorA5, MatrixStackVar);
    }

    public boolean a(float f2, float f3, float f4, int i2, int i3) {
        if (!GuiInput.a(((f2 + f4) - i) - 19.0f, f3 + 8.75f, 19.0f, 19.0f, i2, i3)) {
            return false;
        }
        if (this.r != null) {
            this.r.accept(this.m);
        }
        return true;
    }

    public static String a(String str, String str2, int i2, int i3, int i4, int i5) {
        return null;
    }
}
