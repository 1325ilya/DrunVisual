package drunvisual.gui.friends;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.gui.widgets.TextInput;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.core.Bool;
import drunvisual.gui.core.GuiInput;
import drunvisual.gui.core.PanelFadeOverlay;
import drunvisual.gui.widgets.ScrollBar;
import drunvisual.gui.widgets.SearchBox;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.theme.Theme;
import drunvisual.util.ColorUtils;

public class FriendsPanel {
    private static final float a = 19.5f;
    private static final float b = 98.5f;
    private static final float c = 5.0f;
    private static final float d = 19.5f;
    private static final float e = 6.5f;
    private static final float f = 5.0f;
    private static final float g = 9.5f;
    private static final float h = 6.5f;
    private static final float i = 6.5f;
    private static final float j = 6.5f;
    private static final float k = 1.0f;
    private static final float l = 9.5f;
    private static final float m = 2.0f;
    private static final int n = 2;
    private static final Color o = Theme.I;
    private static final Color p = Theme.K;
    private static final Color q = Theme.O;
    private final TextInput u;
    private final ScrollBar v;
    private final PanelFadeOverlay w;
    private Consumer<HistoryEntry> B;
    private float C;
    private float D;
    private float E;
    private float F;
    private float G;
    private float H;
    private float I;
    private final List<HistoryEntry> r = new ArrayList();
    private final List<FriendCard> s = new ArrayList();
    private final AnimationState x = new AnimationState();
    private boolean y = false;
    private String z = "";
    private List<FriendCard> A = new ArrayList();
    private final SearchBox t = new SearchBox();

    public FriendsPanel() {
        this.t.a(this::a);
        this.u = new TextInput(TextInput.InputType.PLAYER, "Добавить...");
        this.u.a(16);
        this.u.a(9.5f);
        this.u.a(Theme.S, Theme.T);
        this.v = new ScrollBar(m, 20.0f);
        this.v.b(10.0f);
        this.v.a(Theme.b);
        this.v.b(Theme.d);
        this.w = new PanelFadeOverlay(25, 5.0f, 9.0f);
    }

    public void a(HistoryEntry historyEntry) {
    }

    public void b(HistoryEntry historyEntry) {
    }

    public List<HistoryEntry> a() {
        return this.r;
    }

    public void b() {
    }

    public void a(Consumer<HistoryEntry> consumer) {
        this.B = consumer;
    }

    private void a(String str) {
        this.z = str.toLowerCase().trim();
        e();
        this.v.e();
    }

    private void e() {
        if (this.z.isEmpty()) {
            this.A = new ArrayList(this.s);
        } else {
            this.A = (List) this.s.stream().filter(friendCard -> {
                return friendCard.a().a().toLowerCase().contains(this.z);
            }).collect(Collectors.toList());
        }
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5, int i2, int i3, float f6) {
        this.C = f2;
        this.D = f3;
        this.E = f4;
        this.F = f5;
        this.v.a();
        this.x.a();
        float f7 = (((f4 - b) - 5.0f) - 19.5f) - 5.0f;
        this.t.a(MatrixStackVar, renderer2D, f2, f3, b, 19.5f, i2, i3);
        float f8 = f2 + b + 5.0f;
        this.u.a(MatrixStackVar, renderer2D, f8, f3, f7, 19.5f, i2, i3, f6);
        a(MatrixStackVar, renderer2D, f8 + f7 + 5.0f, f3, i2, i3, f6);
        float f9 = f3 + 24.5f;
        float f10 = f5 - (f9 - f3);
        this.G = f9;
        this.H = f10;
        this.I = f4;
        b(MatrixStackVar, renderer2D, f2, f9, f4, f10, i2, i3, f6);
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5, float f6) {
        Color colorB = Theme.b(Theme.m, f6);
        Color colorB2 = Theme.b(Theme.n, f6);
        renderer2D.a(f2 - 0.5f, f3 - 0.5f, f4 + k, f5 + k, 9.5f, colorB, colorB, colorB2, colorB2, MatrixStackVar);
        int i2 = (int) (255.0f * f6);
        Color colorA = Theme.a(Theme.e, i2);
        Color colorA2 = Theme.a(Theme.f, i2);
        renderer2D.a(f2, f3, f4, f5, 9.5f, colorA, colorA, colorA2, colorA2, MatrixStackVar);
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5) {
        FontRenderer fontRenderer = FontManager.b[24];
        FontRenderer fontRenderer2 = FontManager.a[15];
        String str = this.r.isEmpty() ? "У тебя пока-что нету друзей :(" : "Ничего не найдено";
        String str2 = this.r.isEmpty() ? "Введи никнейм друга в поле “Добавить” и нажми на плюсик" : "Попробуй изменить запрос";
        float fA = fontRenderer.a(str);
        float fA2 = fontRenderer2.a(str2);
        float f6 = f2 + ((f4 - fA) / m);
        float f7 = (f3 + (f5 / m)) - 20.0f;
        float f8 = f2 + ((f4 - fA2) / m);
        fontRenderer.a(str, f6, f7, Theme.a, MatrixStackVar);
        fontRenderer2.a(str2, f8, f7 + 15.0f, Theme.b, MatrixStackVar);
    }

    private void b(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5, int i2, int i3, float f6) {
        if (this.A.isEmpty()) {
            a(MatrixStackVar, renderer2D, f2, f3, f4, f5);
            return;
        }
        float f7 = f2 + k;
        float f8 = f3 + 6.5f;
        float f9 = f4 - m;
        float f10 = (f5 - 6.5f) - 6.5f;
        float f11 = f();
        boolean z = f11 > f10;
        if (!z) {
            this.v.e();
        }
        renderer2D.b().a(f2, f3 + (8 / m), f4, f5 - 8, MatrixStackVar);
        float f12 = (f9 - 9.5f) / m;
        float fB = f8 - this.v.b();
        int i4 = 0;
        for (FriendCard friendCard : this.A) {
            float f13 = f7 + ((i4 % n) * (f12 + 9.5f));
            float f14 = fB + ((i4 / n) * 43.5f);
            if (f14 + 37.0f >= f8 && f14 <= f8 + f10) {
                friendCard.a(MatrixStackVar, renderer2D, f13, f14, f12, i2, i3, f6);
            }
            i4++;
        }
        renderer2D.b().a(MatrixStackVar);
        if (z) {
            this.v.a(MatrixStackVar, renderer2D, f2 + f4 + 18.0f, f8, f10, f11, f10, i2, i3, false);
        }
        this.w.a(MatrixStackVar, renderer2D, f2, f3 + 1.5f, f4, f5, f6);
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, int i2, int i3, float f4) {
        int i4 = (int) (255.0f * f4);
        boolean zA = GuiInput.a(f2, f3, 19.5f, 19.5f, i2, i3);
        if (zA != this.y) {
            this.x.a(zA ? 1.0d : 0.0d, 0.15d, Easing.h);
            this.y = zA;
        }
        float fJ = (float) this.x.j();
        Color colorB = Theme.b(Theme.q, f4);
        Color colorB2 = Theme.b(Theme.n, f4);
        renderer2D.a(f2 - 0.5f, f3 - 0.5f, 20.5f, 20.5f, 6.5f, colorB, colorB, colorB2, colorB2, MatrixStackVar);
        Color colorA = ColorUtils.a(Theme.f, o, fJ);
        Color colorA2 = ColorUtils.a(Theme.e, p, fJ);
        Color colorA3 = Theme.a(colorA, i4);
        Color colorA4 = Theme.a(colorA2, i4);
        renderer2D.a(f2, f3, 19.5f, 19.5f, 6.5f, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
        Color colorA5 = Theme.a(ColorUtils.a(Theme.b, q, fJ), i4);
        float f5 = f2 + ((19.5f - 5.0f) / m);
        float f6 = f3 + ((19.5f - 5.0f) / m);
        renderer2D.a(f5, (f6 + (5.0f / m)) - (1.5f / m), 5.0f, 1.5f, colorA5, MatrixStackVar);
        renderer2D.a((f5 + (5.0f / m)) - (1.5f / m), f6, 1.5f, 5.0f, colorA5, MatrixStackVar);
        if (zA) {
            GuiInput.g();
        }
    }

    private float f() {
        if (this.A.isEmpty()) {
            return 0.0f;
        }
        return (((int) Math.ceil(((double) this.A.size()) / 2.0d)) * 43.5f) - 6.5f;
    }

    public boolean a(float f2, float f3, float f4, float f5, int i2, int i3) {
        if (this.t.a(i2, i3)) {
            this.u.a(false);
            return true;
        }
        float f6 = (((f4 - b) - 5.0f) - 19.5f) - 5.0f;
        float f7 = f2 + b + 5.0f;
        if (this.u.a(i2, i3, 0)) {
            this.t.b(false);
            return true;
        }
        if (GuiInput.a(f7 + f6 + 5.0f, f3, 19.5f, 19.5f, i2, i3)) {
            g();
            return true;
        }
        float f8 = f3 + 19.5f + 5.0f + 6.5f;
        float f9 = (((f5 - 19.5f) - 5.0f) - 6.5f) - 6.5f;
        float f10 = f4 - m;
        float f11 = f();
        if (f11 > f9) {
            if (this.v.a(f2 + f4 + 18.0f, f8, f9, f11, f9, i2, i3)) {
                return true;
            }
        }
        float f12 = f2 + k;
        float f13 = (f10 - 9.5f) / m;
        if (GuiInput.a(f12, f8, f10, f9, i2, i3)) {
            float fB = f8 - this.v.b();
            int i4 = 0;
            Iterator<FriendCard> it = this.A.iterator();
            while (it.hasNext()) {
                if (it.next().a(f12 + ((i4 % n) * (f13 + 9.5f)), fB + ((i4 / n) * 43.5f), f13, i2, i3)) {
                    return true;
                }
                i4++;
            }
        }
        return false;
    }

    private void g() {
    }

    public void a(int i2, int i3) {
        this.v.d();
    }

    public void a(int i2, int i3, double d2, double d3) {
        if (this.v.c()) {
            float f2 = (this.H - 6.5f) - 6.5f;
            this.v.a(i3, f(), f2);
        }
    }

    public void a(float f2, int i2, int i3) {
        float f3 = this.D + 19.5f + 5.0f;
        float f4 = (this.F - 19.5f) - 5.0f;
        if (GuiInput.a(this.C, f3, this.E, f4, i2, i3)) {
            this.v.a(f2, f(), (f4 - 6.5f) - 6.5f);
        }
    }

    public boolean a(int i2, int i3, int i4) {
        return this.t.c() ? this.t.a(i2, i3, i4) : this.u.d() ? this.u.b(i2, i3, i4) : false;
    }

    public boolean a(char c2, int i2) {
        return this.t.c() ? this.t.a(c2, i2) : this.u.d() ? this.u.a(c2, i2) : false;
    }

    public boolean c() {
        return Bool.from((this.t.c() || this.u.d()) ? 1 : 0);
    }

    public boolean d() {
        return this.r.isEmpty();
    }

    public static String a(String str, String str2, int i2, int i3, int i4, int i5) {
        return null;
    }
}
