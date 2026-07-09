package drunvisual.gui.config;

import java.awt.Color;
import java.util.function.Consumer;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.config.ConfigEntry;
import drunvisual.config.ConfigState;
import drunvisual.core.Bool;
import drunvisual.gui.core.GuiInput;
import drunvisual.gui.core.GuiInteractionState;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.render.icons.IconGlyphs;
import drunvisual.theme.Theme;
import drunvisual.util.ColorUtils;

public class ConfigActionMenu {
    private static final float c = 79.0f;
    private static final float d = 12.0f;
    private static final float e = 11.0f;
    private static final float f = 9.5f;
    private static final float g = 7.0f;
    private static final float h = 6.5f;
    private static final float i = 8.0f;
    private static final float j = 4.0f;
    private static final float k = 3.0f;
    private float m;
    private float n;
    private float o;
    private ConfigEntry p;
    private Consumer<Action> t;
    private Runnable u;
    public static int a;
    public static boolean b;
    private boolean l = false;
    private final AnimationState q = new AnimationState();
    private final AnimationState[] r = new AnimationState[4];
    private final boolean[] s = new boolean[4];

    public enum Action {
        SAVE_TO("Сохранить в"),
        SHARE("Поделиться"),
        RENAME("Переименовать"),
        DELETE("Удалить");

        private final String label;
        public static int e;
        public static boolean f;

        Action(String str) {
            this.label = str;
        }

        public String a() {
            return this.label;
        }

        public static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return str;
        }
    }

    public ConfigActionMenu() {
        for (int i2 = 0; i2 < 4; i2++) {
            this.r[i2] = new AnimationState();
            this.s[i2] = false;
        }
    }

    public void a(float f2, float f3, float f4, ConfigEntry configEntry) {
        this.o = f2 + (f4 / 2.0f);
        this.m = this.o - 39.5f;
        this.n = f3 + k;
        this.p = configEntry;
        this.l = true;
        this.q.d(0.0d);
        this.q.a(1.0d, 0.25d, Easing.F);
        for (int i2 = 0; i2 < 4; i2++) {
            this.r[i2].d(0.0d);
            this.s[i2] = false;
        }
        GuiInteractionState.a().d(true);
        GuiInput.a(this.m, this.n, c, 69.5f);
    }

    private boolean a(Action action) {
        if (this.p == null) {
            return false;
        }
        if (this.p.f()) {
            return Bool.from(action == Action.SAVE_TO ? 0 : 1);
        }
        return (this.p.e() && (action == Action.DELETE || action == Action.RENAME)) ? true : false;
    }

    private String b(Action action) {
        return (this.p != null && this.p.f() && action == Action.SAVE_TO) ? "Применить" : action.a();
    }

    public void a(float f2, float f3, ConfigEntry configEntry) {
        a(f2, f3, 0.0f, configEntry);
    }

    public void a() {
        if (this.l) {
            this.q.a(0.0d, 0.15d, Easing.g);
            GuiInteractionState.a().d(false);
            GuiInput.a();
        }
    }

    public boolean b() {
        return this.l;
    }

    public boolean c() {
        return (!this.l || this.q.j() < 0.01d) ? true : false;
    }

    public ConfigEntry d() {
        return this.p;
    }

    public void a(Consumer<Action> consumer) {
        this.t = consumer;
    }

    public void a(Runnable runnable) {
        this.u = runnable;
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, int i2, int i3) {
        this.q.a();
        float fJ = (float) this.q.j();
        if (fJ < 0.01f) {
            if (this.l && this.q.d()) {
                this.l = false;
                if (this.u != null) {
                    this.u.run();
                    return;
                }
                return;
            }
            return;
        }
        for (int i4 = 0; i4 < 4; i4++) {
            this.r[i4].a();
        }
        float fMax = Math.max(0.0f, Math.min(1.0f, fJ));
        int i5 = (int) (255.0f * fMax);
        float f2 = c * fMax;
        float f3 = 69.5f * fMax;
        float f4 = this.m + 39.5f;
        float f5 = this.n + (69.5f / 2.0f);
        float f6 = f4 - (f2 / 2.0f);
        float f7 = f5 - (f3 / 2.0f);
        Color colorA = Theme.a(Theme.q, i5);
        Color colorA2 = Theme.a(Theme.r, i5);
        renderer2D.a(f6 - (0.5f * fMax), f7 - (0.5f * fMax), f2 + fMax, f3 + fMax, 9.5f * fMax, colorA, colorA, colorA2, colorA2, MatrixStackVar);
        Color colorA3 = Theme.a(Theme.e, i5);
        Color colorA4 = Theme.a(Theme.f, i5);
        renderer2D.a(f6, f7, f2, f3, 9.5f * fMax, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
        if (fMax > 0.1f) {
            MatrixStackVar.push();
            MatrixStackVar.translate(f4, f5, 0.0f);
            MatrixStackVar.scale(fMax, fMax, 1.0f);
            MatrixStackVar.translate(-f4, -f5, 0.0f);
            int i6 = (int) (this.m + d);
            int i7 = (int) (this.n + e);
            FontRenderer fontRenderer = FontManager.a[12];
            Action[] actionArrValues = Action.values();
            for (int i8 = 0; i8 < actionArrValues.length; i8++) {
                float f8 = i7 + (i8 * 13.5f);
                boolean zA = a(actionArrValues[i8]);
                int i9 = (zA || fMax <= 0.5f || !GuiInput.a((f6 + (d * fMax)) - 5.0f, ((f7 + (e * fMax)) + ((((float) i8) * 13.5f) * fMax)) - 2.0f, (55.0f * fMax) + 10.0f, (7.0f * fMax) + j, (double) i2, (double) i3)) ? 0 : 1;
                if (Bool.from(i9) != this.s[i8]) {
                    this.r[i8].a(i9 == 0 ? 0.0d : 1.0d, 0.15d, Easing.h);
                    this.s[i8] = Bool.from(i9);
                }
                float fJ2 = (float) this.r[i8].j();
                a(MatrixStackVar, renderer2D, fontRenderer, i6, f8, 1.0f, actionArrValues[i8], b(actionArrValues[i8]), Theme.a(!zA ? actionArrValues[i8] != Action.DELETE ? ColorUtils.a(Theme.a, Theme.aa, fJ2) : ColorUtils.a(Theme.Z, Theme.b(Theme.Z, 30), fJ2) : Theme.b, i5), i5);
                if (i9 != 0) {
                    GuiInput.g();
                }
            }
            MatrixStackVar.pop();
        }
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, FontRenderer fontRenderer, float f2, float f3, float f4, Action action, String str, Color color, int i2) {
        float f5 = 8.0f * f4;
        float f6 = f2 + f5 + (j * f4);
        a(MatrixStackVar, renderer2D, f2, f3, f5, action, color);
        fontRenderer.a(str, f6, f3, color, MatrixStackVar);
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, Action action, Color color) {
        String str;
        switch (action.ordinal()) {
            case EventPriority.MEDIUM /* 0 */:
                str = IconGlyphs.G;
                break;
            case ConfigState.a /* 1 */:
                str = IconGlyphs.I;
                break;
            case 2:
                str = IconGlyphs.j;
                break;
            case 3:
                str = IconGlyphs.K;
                break;
            default:
                return;
        }
        FontManager.e[14].a(str, f2, f3, color, MatrixStackVar);
    }

    public boolean a(int i2, int i3) {
        if (!this.l || this.q.j() < 0.5d) {
            return false;
        }
        if (!GuiInput.a(this.m, this.n, c, 69.5f, i2, i3)) {
            a();
            return true;
        }
        float f2 = this.m + d;
        float f3 = this.n + e;
        Action[] actionArrValues = Action.values();
        for (int i4 = 0; i4 < actionArrValues.length; i4++) {
            if (GuiInput.a(f2 - 5.0f, (f3 + (i4 * 13.5f)) - 2.0f, 55.0f + 10.0f, 7.0f + j, i2, i3)) {
                if (a(actionArrValues[i4])) {
                    return true;
                }
                if (this.t != null) {
                    this.t.accept(actionArrValues[i4]);
                }
                a();
                return true;
            }
        }
        return true;
    }

    public boolean b(int i2, int i3) {
        return this.l ? GuiInput.a(this.m, this.n, c, 69.5f, i2, i3) : false;
    }

    public static String a(String str, String str2, int i2, int i3, int i4, int i5) {
        return null;
    }
}
