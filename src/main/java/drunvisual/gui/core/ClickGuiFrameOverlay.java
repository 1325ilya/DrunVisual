package drunvisual.gui.core;

import net.minecraft.client.util.math.MatrixStack;
import drunvisual.render.Renderer2D;
import drunvisual.theme.Theme;

public class ClickGuiFrameOverlay implements ClickGuiOverlay {
    private static final float c = 90.0f;
    private static final float d = 0.5f;
    public static int a;
    public static boolean b;

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, int i, int i2) {
        float fD = DrunVisualClickGuiScreen.d();
        float fE = DrunVisualClickGuiScreen.e();
        float f3 = DrunVisualClickGuiScreen.f();
        renderer2D.a(f - d, f2 - d, fD + 1.0f, fE + 1.0f, f3, Theme.w, Theme.w, Theme.w, Theme.w, MatrixStackVar);
        renderer2D.a(f, f2, fD, fE, f3, Theme.e, Theme.e, Theme.f, Theme.f, MatrixStackVar);
    }

    public void b(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, int i, int i2) {
        float fD = DrunVisualClickGuiScreen.d();
        float fE = DrunVisualClickGuiScreen.e();
        renderer2D.b().a(f, f2, fD, fE, DrunVisualClickGuiScreen.f(), MatrixStackVar);
        renderer2D.a((f + (fD / 2.0f)) - c, f2 - (fE / 2.0f), c, Theme.a(Theme.y, 60), MatrixStackVar);
        renderer2D.b().a(MatrixStackVar);
    }

    @Override
    public void a(float f, float f2, int i, int i2) {
    }

    public static String b(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
