package drunvisual.gui.core;

import java.awt.Color;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.render.Renderer2D;
import drunvisual.theme.Theme;

public class PanelFadeOverlay implements ClickGuiOverlay {
    private static final int c = 50;
    private static final float d = 5.0f;
    private static final float e = 25.0f;
    private int f;
    private float g;
    private float h;
    private Color i;
    private boolean j;
    private float k;
    private float l;
    private float m;
    private float n;
    public static int a;
    public static boolean b;

    public PanelFadeOverlay() {
        this.f = c;
        this.g = d;
        this.h = 25.0f;
        this.i = Theme.f;
        this.j = false;
    }

    public PanelFadeOverlay(int i, float f, float f2) {
        this.f = c;
        this.g = d;
        this.h = 25.0f;
        this.i = Theme.f;
        this.j = false;
        this.f = i;
        this.g = f;
        this.h = f2;
    }

    public PanelFadeOverlay(int i, float f, float f2, Color color) {
        this.f = c;
        this.g = d;
        this.h = 25.0f;
        this.i = Theme.f;
        this.j = false;
        this.f = i;
        this.g = f;
        this.h = f2;
        this.i = color;
    }

    public void a(int i) {
        this.f = i;
    }

    public void a(float f) {
        this.g = f;
    }

    public void b(float f) {
        this.h = f;
    }

    public void a(Color color) {
        this.i = color;
    }

    public void a(float f, float f2, float f3, float f4) {
        this.j = true;
        this.k = f;
        this.l = f2;
        this.m = f3;
        this.n = f4;
    }

    public void a() {
        this.j = false;
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, int i, int i2) {
        float f3;
        float f4;
        float fD;
        float fE;
        if (this.j) {
            f3 = this.k;
            f4 = this.l;
            fD = this.m;
            fE = this.n;
        } else {
            f3 = f;
            f4 = f2;
            fD = DrunVisualClickGuiScreen.d();
            fE = DrunVisualClickGuiScreen.e();
        }
        Color colorA = Theme.a(this.i, 0);
        renderer2D.a(f3, ((f4 + fE) - this.f) - this.g, fD, this.f, this.h, colorA, colorA, this.i, this.i, MatrixStackVar);
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, float f3, float f4, float f5) {
        Color colorA = Theme.a(this.i, f5);
        Color colorA2 = Theme.a(this.i, 0);
        renderer2D.a(f, ((f2 + f4) - this.f) - this.g, f3, this.f, 0.0f, colorA2, colorA2, colorA, colorA, MatrixStackVar);
    }

    @Override
    public void a(float f, float f2, int i, int i2) {
    }

    public static String b(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
