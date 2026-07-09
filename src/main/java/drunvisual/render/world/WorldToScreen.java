package drunvisual.render.world;

import lombok.Generated;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import drunvisual.client.MinecraftContext;
import drunvisual.core.Bool;
import drunvisual.render.ScreenScale;

public final class WorldToScreen implements MinecraftContext {
    public static final Matrix4f a = new Matrix4f();
    public static final Matrix4f b = new Matrix4f();
    public static final Matrix4f e = new Matrix4f();

    private static double[] a(double d, double d2, double d3) {
        MinecraftClient MinecraftClientVarGetInstance = MinecraftClient.getInstance();
        Camera CameraVar = MinecraftClientVarGetInstance.getEntityRenderDispatcher().camera;
        int iGetHeight = MinecraftClientVarGetInstance.getWindow().getHeight();
        int[] iArr = new int[4];
        GL11.glGetIntegerv(2978, iArr);
        Vector3f vector3f = new Vector3f();
        Vector4f vector4fMul = new Vector4f((float) (d - CameraVar.getPos().x), (float) (d2 - CameraVar.getPos().y), (float) (d3 - CameraVar.getPos().z), 1.0f).mul(e);
        Vector3f vector3fProject = new Matrix4f(a).mul(new Matrix4f(b)).project(vector4fMul.x(), vector4fMul.y(), vector4fMul.z(), iArr, vector3f);
        double[] dArr = new double[3];
        dArr[0] = vector3fProject.x / ScreenScale.d();
        dArr[1] = (iGetHeight - vector3fProject.y) / ScreenScale.d();
        dArr[2] = vector3fProject.z;
        return dArr;
    }

    public static Vec3d a(Vec3d Vec3dVar) {
        if (c.gameRenderer == null || c.getWindow() == null || c.player == null || !c(Vec3dVar)) {
            return null;
        }
        double[] dArrA = a(Vec3dVar.x, Vec3dVar.y, Vec3dVar.z);
        return new Vec3d(dArrA[0], dArrA[1], dArrA[2]);
    }

    public static boolean b(Vec3d Vec3dVar) {
        if (Vec3dVar == null) {
            return false;
        }
        float fGetFramebufferWidth = c.getWindow().getFramebufferWidth() / ScreenScale.d();
        float fGetFramebufferHeight = c.getWindow().getFramebufferHeight() / ScreenScale.d();
        if (Vec3dVar.x >= -100.0d && Vec3dVar.x <= fGetFramebufferWidth + 100.0f && Vec3dVar.y >= -100.0d) {
            if (Vec3dVar.y <= fGetFramebufferHeight + 100.0f) {
                return true;
            }
        }
        return false;
    }

    public static boolean c(Vec3d Vec3dVar) {
        if (c.player == null) {
            return false;
        }
        return Bool.from(c.player.getRotationVector().dotProduct(Vec3dVar.subtract(c.player.getPos()).normalize()) <= 0.0d ? 0 : 1);
    }

    @Generated
    private WorldToScreen() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String b(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
