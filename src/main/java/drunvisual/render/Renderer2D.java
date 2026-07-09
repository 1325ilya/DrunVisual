package drunvisual.render;

import java.awt.Color;
import net.minecraft.client.util.Window;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.util.math.MatrixStack;

public interface Renderer2D {
    default void a(float f, float f2, float f3, float f4, Color color, MatrixStack MatrixStackVar) {
        a(f, f2, f3, f4, 0.0f, color, MatrixStackVar);
    }

    void a(float f, float f2, float f3, float f4, float f5, Color color, MatrixStack MatrixStackVar);

    default void a(float f, float f2, float f3, float f4, float f5, Color color, Color color2, Color color3, Color color4, MatrixStack MatrixStackVar) {
        a(f, f2, f3, f4, f5, f5, f5, f5, color, color2, color3, color4, MatrixStackVar);
    }

    void a(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, Color color, Color color2, Color color3, Color color4, MatrixStack MatrixStackVar);

    void a(float f, float f2, float f3, Color color, MatrixStack MatrixStackVar);

    void a(float f, float f2, float f3, float f4, float f5, float f6, Color color, MatrixStack MatrixStackVar);

    default void b(float f, float f2, float f3, float f4, float f5, Color color, MatrixStack MatrixStackVar) {
        b(f, f2, f3, f4, 0.0f, f5, color, MatrixStackVar);
    }

    void a(float f, float f2, float f3, float f4, MatrixStack MatrixStackVar);

    Window a();

    void b(float f, float f2, float f3, float f4, float f5, float f6, Color color, MatrixStack MatrixStackVar);

    void a(int i, float f, float f2, float f3, float f4, Color color, MatrixStack MatrixStackVar);

    void a(Identifier IdentifierVar, float f, float f2, float f3, float f4, Color color, MatrixStack MatrixStackVar);

    default void a(Identifier IdentifierVar, float f, float f2, float f3, float f4, float f5, Color color, MatrixStack MatrixStackVar) {
        a(IdentifierVar, f, f2, f3, f4, f5, 0.0f, 0.0f, 1.0f, 1.0f, color, MatrixStackVar);
    }

    void a(Identifier IdentifierVar, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, Color color, MatrixStack MatrixStackVar);

    default Color a(Color color, float f) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), MathHelper.clamp((int) (f * 255.0f), 0, 255));
    }

    default Color a(Color color, int i) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), MathHelper.clamp(i, 0, 255));
    }

    void b(float f, float f2, float f3, float f4, MatrixStack MatrixStackVar);

    default void a(float f, float f2, float f3, float f4, float f5, MatrixStack MatrixStackVar) {
        b(f, f2, f3, f4, MatrixStackVar);
    }

    void a(MatrixStack MatrixStackVar);

    ScissorStack b();

    void a(float f, float f2, float f3, float f4, float f5, float f6, MatrixStack MatrixStackVar);

    void c(float f, float f2, float f3, float f4, MatrixStack MatrixStackVar);

    void a(float f, MatrixStack MatrixStackVar);
}
