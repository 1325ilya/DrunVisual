package drunvisual.gui.core;

import net.minecraft.client.util.math.MatrixStack;
import drunvisual.render.Renderer2D;

public interface ClickGuiOverlay {
    void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, int i, int i2);

    void a(float f, float f2, int i, int i2);

    default void b(float f, float f2, int i, int i2) {
    }
}
