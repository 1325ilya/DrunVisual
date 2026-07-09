package drunvisual.render;

import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Generated;
import com.mojang.blaze3d.systems.ProjectionType;
import net.minecraft.client.util.Window;
import org.joml.Matrix4f;
import drunvisual.client.MinecraftContext;

public final class ScreenScale implements MinecraftContext {
    private static float e = 1.0f;
    private static Matrix4f f;
    private static Matrix4f g;
    public static int a;
    public static boolean b;

    public static void a(double d) {
        e = (float) d;
        Window WindowVarGetWindow = c.getWindow();
        f = new Matrix4f(RenderSystem.getProjectionMatrix());
        g = new Matrix4f(RenderSystem.getModelViewMatrix());
        RenderSystem.setProjectionMatrix(new Matrix4f().setOrtho(0.0f, (float) (((double) WindowVarGetWindow.getFramebufferWidth()) / d), (float) (((double) WindowVarGetWindow.getFramebufferHeight()) / d), 0.0f, 1000.0f, 21000.0f), ProjectionType.ORTHOGRAPHIC);
        RenderSystem.getModelViewStack().pushMatrix();
        RenderSystem.getModelViewStack().identity();
        RenderSystem.getModelViewStack().translate(0.0f, 0.0f, -11000.0f);
    }

    public static void a() {
        RenderSystem.getModelViewStack().popMatrix();
        if (f != null) {
            RenderSystem.setProjectionMatrix(f, ProjectionType.ORTHOGRAPHIC);
        }
        e = (float) c.getWindow().getScaleFactor();
    }

    public static ScreenPoint a(int i, int i2) {
        double dGetScaleFactor = c.getWindow().getScaleFactor();
        return new ScreenPoint((int) ((((double) i) * dGetScaleFactor) / 2.0d), (int) ((((double) i2) * dGetScaleFactor) / 2.0d));
    }

    public static ScreenPoint a(double d, double d2) {
        double dGetScaleFactor = c.getWindow().getScaleFactor();
        return new ScreenPoint((int) ((d * dGetScaleFactor) / 2.0d), (int) ((d2 * dGetScaleFactor) / 2.0d));
    }

    public static void b() {
        RenderSystem.getModelViewStack().pushMatrix();
    }

    public static void c() {
        RenderSystem.getModelViewStack().popMatrix();
    }

    public static void a(float f2, float f3, float f4) {
        RenderSystem.getModelViewStack().scale(f2, f3, f4);
    }

    public static void b(float f2, float f3, float f4) {
        RenderSystem.getModelViewStack().translate(f2, f3, f4);
    }

    @Generated
    public static float d() {
        return e;
    }

    public static String b(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
