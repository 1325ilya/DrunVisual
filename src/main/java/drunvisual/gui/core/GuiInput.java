package drunvisual.gui.core;

import lombok.Generated;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import drunvisual.gui.core.GuiLayerRegistry;
import drunvisual.core.Bool;

public class GuiInput {
    private static float j;
    private static float k;
    private static float l;
    private static float m;
    public static int a;
    public static boolean b;
    private static long e = 0;
    private static long f = 0;
    private static final double[] c = new double[1];
    private static final double[] d = new double[1];
    private static boolean g = false;
    private static boolean h = false;
    private static boolean i = false;

    public static void a(float f2, float f3, float f4, float f5) {
        i = true;
        j = f2;
        k = f3;
        l = f4;
        m = f5;
    }

    public static void a() {
        i = false;
    }

    public static void b() {
        h = true;
    }

    public static void c() {
        h = false;
    }

    public static boolean d() {
        return h;
    }

    public static boolean a(double d2, double d3) {
        if (i) {
            return Bool.from((d2 < ((double) j) || d3 < ((double) k) || d2 > ((double) (j + l)) || d3 > ((double) (k + m))) ? 0 : 1);
        }
        return true;
    }

    public static boolean a(double d2, double d3, double d4, double d5, double d6, double d7) {
        if (!(d6 >= d2 && d7 >= d3 && d6 <= d2 + d4 && d7 <= d3 + d5)) {
            return false;
        }
        if (i) {
            return Bool.from((d6 < ((double) j) || d7 < ((double) k) || d6 > ((double) (j + l)) || d7 > ((double) (k + m))) ? 0 : 1);
        }
        return true;
    }

    public static boolean a(float f2, float f3, float f4, float f5, double d2, double d3) {
        return a((double) f2, (double) f3, (double) f4, (double) f5, d2, d3);
    }

    public static boolean a(GuiLayerRegistry.Layer layer, double d2, double d3) {
        if (i) {
            if (!(d2 >= ((double) j) && d3 >= ((double) k) && d2 <= ((double) (j + l)) && d3 <= ((double) (k + m)))) {
                return false;
            }
        }
        return GuiLayerRegistry.a().a(layer, d2, d3);
    }

    public static boolean a(GuiLayerRegistry.Layer layer, float f2, float f3, float f4, float f5, double d2, double d3) {
        return a(f2, f3, f4, f5, d2, d3) ? a(layer, d2, d3) : false;
    }

    public static boolean b(GuiLayerRegistry.Layer layer, double d2, double d3) {
        return GuiLayerRegistry.a().d(layer, d2, d3);
    }

    public static double e() {
        MinecraftClient MinecraftClientVarGetInstance = MinecraftClient.getInstance();
        if (MinecraftClientVarGetInstance.getWindow() == null) {
            return 0.0d;
        }
        GLFW.glfwGetCursorPos(MinecraftClientVarGetInstance.getWindow().getHandle(), c, d);
        return (c[0] * ((double) MinecraftClientVarGetInstance.getWindow().getScaledWidth())) / ((double) MinecraftClientVarGetInstance.getWindow().getWidth());
    }

    public static double f() {
        MinecraftClient MinecraftClientVarGetInstance = MinecraftClient.getInstance();
        if (MinecraftClientVarGetInstance.getWindow() == null) {
            return 0.0d;
        }
        GLFW.glfwGetCursorPos(MinecraftClientVarGetInstance.getWindow().getHandle(), c, d);
        return (d[0] * ((double) MinecraftClientVarGetInstance.getWindow().getScaledHeight())) / ((double) MinecraftClientVarGetInstance.getWindow().getHeight());
    }

    public static void g() {
        if (h) {
            return;
        }
        g = true;
    }

    public static void h() {
        g = false;
    }

    public static void i() {
        MinecraftClient MinecraftClientVarGetInstance = MinecraftClient.getInstance();
        if (MinecraftClientVarGetInstance.getWindow() != null) {
            long jGetHandle = MinecraftClientVarGetInstance.getWindow().getHandle();
            if (g) {
                if (e == 0) {
                    e = GLFW.glfwCreateStandardCursor(221188);
                }
                GLFW.glfwSetCursor(jGetHandle, e);
            } else {
                if (f == 0) {
                    f = GLFW.glfwCreateStandardCursor(221185);
                }
                GLFW.glfwSetCursor(jGetHandle, f);
            }
        }
    }

    public static void j() {
        g = false;
        MinecraftClient MinecraftClientVarGetInstance = MinecraftClient.getInstance();
        if (MinecraftClientVarGetInstance.getWindow() != null) {
            if (f == 0) {
                f = GLFW.glfwCreateStandardCursor(221185);
            }
            GLFW.glfwSetCursor(MinecraftClientVarGetInstance.getWindow().getHandle(), f);
        }
    }

    @Generated
    public static boolean k() {
        return i;
    }

    public static String a(String str, String str2, int i2, int i3, int i4, int i5) {
        return null;
    }
}
