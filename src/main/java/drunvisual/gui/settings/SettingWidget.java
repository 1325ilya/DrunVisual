package drunvisual.gui.settings;

import net.minecraft.client.util.math.MatrixStack;
import drunvisual.render.Renderer2D;

public interface SettingWidget {
    String a();

    float b();

    void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, float f3, int i, int i2, float f4, float f5);

    boolean a(float f, float f2, float f3, int i, int i2);

    default boolean b(float f, float f2, float f3, int i, int i2) {
        return false;
    }

    default void a(int i, int i2) {
    }

    default void a(int i, int i2, double d, double d2) {
    }

    default boolean a(int i, int i2, int i3) {
        return false;
    }

    default boolean a(char c, int i) {
        return false;
    }

    default boolean a_() {
        return false;
    }

    default void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, float f3, float f4, int i, int i2, float f5, float f6) {
    }

    default boolean j() {
        return false;
    }

    default boolean l() {
        return false;
    }

    default boolean d() {
        return true;
    }
}
