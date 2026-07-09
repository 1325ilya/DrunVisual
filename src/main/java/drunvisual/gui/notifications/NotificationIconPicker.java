package drunvisual.gui.notifications;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.hud.notifications.Notification;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.core.Bool;
import drunvisual.gui.core.GuiInput;
import drunvisual.gui.core.GuiInteractionState;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.theme.Theme;
import drunvisual.util.ColorUtils;

public class NotificationIconPicker {
    private static final float c = 43.25f;
    private static final float d = 23.0f;
    private static final float e = 12.0f;
    private static final float f = 5.0f;
    private static final float g = 9.5f;
    private static final int h = 5;
    private static final Color i = Theme.I;
    private static final Color j = Theme.J;
    private static final Color k = Theme.N;
    private Consumer<Notification.Icon> q;
    public static int a;
    public static boolean b;
    private final Map<Notification.Icon, AnimationState> n = new HashMap();
    private final Map<Notification.Icon, AnimationState> o = new HashMap();
    private final Map<Notification.Icon, Boolean> p = new HashMap();
    private Color r = Theme.y;
    private final Notification.Icon[] l = Notification.Icon.values();
    private Notification.Icon m = Notification.Icon.DEATH;

    public NotificationIconPicker() {
        for (Notification.Icon icon : this.l) {
            this.n.put(icon, new AnimationState());
            this.o.put(icon, new AnimationState());
            this.p.put(icon, Boolean.valueOf(false));
            if (icon == this.m) {
                this.o.get(icon).d(1.0d);
            }
        }
    }

    public void a(Notification.Icon icon) {
        if (this.m != icon) {
            if (this.m != null) {
                this.o.get(this.m).a(0.0d, 0.2d, Easing.h);
            }
            this.m = icon;
            this.o.get(icon).a(1.0d, 0.2d, Easing.h);
        }
    }

    public Notification.Icon a() {
        return this.m;
    }

    public void a(Color color) {
        this.r = color;
    }

    public void a(Consumer<Notification.Icon> consumer) {
        this.q = consumer;
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, int i2, int i3, float f5) {
        int i4;
        Color colorA;
        Color colorA2;
        Color colorA3;
        Color colorA4;
        int i5 = (int) (255.0f * f5);
        boolean zB = GuiInteractionState.a().b();
        for (int i6 = 0; i6 < this.l.length; i6++) {
            Notification.Icon icon = this.l[i6];
            float f6 = f2 + ((i6 % h) * 48.25f);
            float f7 = f3 + ((i6 / h) * 28.0f);
            if (zB) {
                i4 = 0;
            } else if (GuiInput.a(f6, f7, c, d, i2, i3)) {
                i4 = 1;
            } else {
                i4 = 0;
            }
            int i7 = i4;
            if (zB && this.p.get(icon).booleanValue()) {
                this.n.get(icon).a(0.0d, 0.15d, Easing.h);
                this.p.put(icon, Boolean.valueOf(false));
            } else if (!zB && Bool.from(i7) != this.p.get(icon).booleanValue()) {
                this.n.get(icon).a(i7 == 0 ? 0.0d : 1.0d, 0.15d, Easing.h);
                this.p.put(icon, Boolean.valueOf(Bool.from(i7)));
            }
            this.n.get(icon).a();
            this.o.get(icon).a();
            float fJ = (float) this.n.get(icon).j();
            float fJ2 = (float) this.o.get(icon).j();
            Color color = Theme.q;
            Color color2 = Theme.n;
            Color color3 = Theme.f;
            Color color4 = Theme.e;
            if (fJ2 <= 0.01f) {
                colorA = ColorUtils.a(color3, i, fJ);
                colorA2 = ColorUtils.a(color4, j, fJ);
                colorA3 = color;
                colorA4 = color2;
            } else {
                Color color5 = this.r;
                Color colorC = Theme.c(color5, 150);
                colorA = ColorUtils.a(ColorUtils.a(color3, i, fJ), color5, fJ2);
                colorA2 = ColorUtils.a(ColorUtils.a(color4, j, fJ), colorC, fJ2);
                colorA3 = ColorUtils.a(color, color5, fJ2);
                colorA4 = ColorUtils.a(color2, colorC, fJ2);
            }
            Color colorB = Theme.b(colorA3, f5);
            Color colorB2 = Theme.b(colorA4, f5);
            Color colorA5 = Theme.a(colorA, i5);
            Color colorA6 = Theme.a(colorA2, i5);
            renderer2D.a(f6 - 0.5f, f7 - 0.5f, 44.25f, 24.0f, 9.5f, colorB, colorB, colorB2, colorB2, MatrixStackVar);
            renderer2D.a(f6, f7, c, d, 9.5f, colorA5, colorA5, colorA6, colorA6, MatrixStackVar);
            FontManager.e[22].a(icon.b(), f6 + 15.625f, f7 + 5.5f, Theme.a(fJ2 <= 0.01f ? ColorUtils.a(Theme.b, k, fJ) : ColorUtils.a(ColorUtils.a(Theme.b, k, fJ), Theme.aa, fJ2), i5), MatrixStackVar);
            if (i7 != 0 && !zB) {
                GuiInput.g();
            }
        }
    }

    public boolean a(float f2, float f3, int i2, int i3) {
        for (int i4 = 0; i4 < this.l.length; i4++) {
            Notification.Icon icon = this.l[i4];
            if (GuiInput.a(f2 + ((i4 % h) * 48.25f), f3 + ((i4 / h) * 28.0f), c, d, i2, i3)) {
                a(icon);
                if (this.q != null) {
                    this.q.accept(icon);
                }
                return true;
            }
        }
        return false;
    }

    public float b() {
        int rows = (int) Math.ceil(((double) this.l.length) / 5.0d);
        return (rows * d) + ((rows - 1) * f);
    }

    public static float c() {
        return c;
    }

    public static float d() {
        return d;
    }

    public static float e() {
        return f;
    }

    public static String a(String str, String str2, int i2, int i3, int i4, int i5) {
        return null;
    }
}
