package drunvisual.gui.core;

import net.minecraft.client.util.math.MatrixStack;
import drunvisual.render.Renderer2D;

public interface ClickGuiTab {
    void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, int i, int i2);

    void a(float f, float f2, int i, int i2);

    void b(float f, float f2, int i, int i2);

    void c(float f, float f2, int i, int i2);

    void a(float f, float f2, int i, int i2, double d, double d2);

    void a(float f);

    ClickGuiTabType a();

    default boolean a(int i, int i2, int i3) {
        return false;
    }

    default boolean b() {
        return false;
    }
}
