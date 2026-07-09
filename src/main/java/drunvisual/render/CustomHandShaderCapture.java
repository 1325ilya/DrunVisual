package drunvisual.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.Optional;
import com.mojang.blaze3d.systems.ProjectionType;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.SimpleFramebuffer;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL30;
import drunvisual.module.ModuleRegistry;
import drunvisual.modules.visuals.CustomHand;
import drunvisual.render.shader.DrunVisualShaderProgram;
import drunvisual.render.shader.ShaderLibrary;

public final class CustomHandShaderCapture {
    private static final MinecraftClient a = MinecraftClient.getInstance();
    private static Framebuffer b = null;
    private static int c = 0;
    private static int d = 0;
    private static boolean e = false;

    private CustomHandShaderCapture() {
    }

    public static void beginCapture() {
        CustomHand customHand;
        if (guiOpen() || e || (customHand = ModuleRegistry.CUSTOM_HAND) == null || !customHand.k() || !customHand.shaderEnabled.a()) {
            return;
        }
        int iGetFramebufferWidth = a.getWindow().getFramebufferWidth();
        int iGetFramebufferHeight = a.getWindow().getFramebufferHeight();
        if (b == null || c != iGetFramebufferWidth || d != iGetFramebufferHeight) {
            if (b != null) {
                b.delete();
            }
            b = new SimpleFramebuffer(iGetFramebufferWidth, iGetFramebufferHeight, true);
            b.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            c = iGetFramebufferWidth;
            d = iGetFramebufferHeight;
        }
        GL30.glBindFramebuffer(36008, a.getFramebuffer().fbo);
        GL30.glBindFramebuffer(36009, b.fbo);
        GL30.glBlitFramebuffer(0, 0, iGetFramebufferWidth, iGetFramebufferHeight, 0, 0, iGetFramebufferWidth, iGetFramebufferHeight, 256, 9728);
        b.beginWrite(false);
        RenderSystem.clearColor(0.0f, 0.0f, 0.0f, 0.0f);
        RenderSystem.clear(16384);
        e = true;
    }

    public static void endCapture() {
        if (guiOpen()) {
            resetForGui();
            return;
        }
        if (!e || b == null) {
            return;
        }
        e = false;
        a.getFramebuffer().beginWrite(true);
        CustomHand customHand = ModuleRegistry.CUSTOM_HAND;
        if (customHand != null && customHand.k() && customHand.shaderEnabled.a()) {
            a(customHand);
        }
    }

    private static void a(CustomHand customHand) {
        Optional<DrunVisualShaderProgram> optionalFind = ShaderLibrary.getRegistry().find("hand_shader");
        if (optionalFind.isEmpty() || !optionalFind.get().b()) {
            return;
        }
        Color colorHandShaderColor = customHand.handShaderColor();
        float fCurrentTimeMillis = ((System.currentTimeMillis() % 100000) / 1000.0f) * customHand.shaderSpeed.a();
        float fA = customHand.shaderOpacity.a();
        int iGetFramebufferWidth = a.getWindow().getFramebufferWidth();
        int iGetFramebufferHeight = a.getWindow().getFramebufferHeight();
        Matrix4f matrix4f = new Matrix4f(RenderSystem.getProjectionMatrix());
        RenderSystem.setProjectionMatrix(new Matrix4f().ortho(0.0f, iGetFramebufferWidth, iGetFramebufferHeight, 0.0f, -1.0f, 1.0f), ProjectionType.ORTHOGRAPHIC);
        RenderSystem.getModelViewStack().pushMatrix();
        RenderSystem.getModelViewStack().identity();
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        GlStateManager._activeTexture(33984);
        GlStateManager._bindTexture(b.getColorAttachment());
        DrunVisualShaderProgram pulseShaderProgram = optionalFind.get();
        pulseShaderProgram.d();
        pulseShaderProgram.a("time", fCurrentTimeMillis);
        pulseShaderProgram.a("screenSize", iGetFramebufferWidth, iGetFramebufferHeight);
        pulseShaderProgram.a("baseColor", colorHandShaderColor.getRed() / 255.0f, colorHandShaderColor.getGreen() / 255.0f, colorHandShaderColor.getBlue() / 255.0f, 1.0f);
        pulseShaderProgram.a("alpha", fA);
        pulseShaderProgram.b("handTexture", 0);
        pulseShaderProgram.b("shaderMode", customHand.shaderModeIndex());
        pulseShaderProgram.b("shaderOnlyMode", customHand.shaderOnly.a() ? 1 : 0);
        DrunVisualShaderProgram.a(0.0f, 0.0f, iGetFramebufferWidth, iGetFramebufferHeight);
        pulseShaderProgram.e();
        RenderSystem.getModelViewStack().popMatrix();
        RenderSystem.setProjectionMatrix(matrix4f, ProjectionType.PERSPECTIVE);
        GlStateManager._bindTexture(0);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableDepthTest();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
    }

    public static boolean isActive() {
        if (guiOpen()) {
            return false;
        }
        return e;
    }

    public static Framebuffer getFramebuffer() {
        return b;
    }

    public static void dispose() {
        if (b != null) {
            b.delete();
            b = null;
        }
        e = false;
    }

    public static boolean guiOpen() {
        try {
            Object objInvoke = Class.forName("net.minecraft.client.MinecraftClient").getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
            if (objInvoke != null) {
                if (objInvoke.getClass().getField("currentScreen").get(objInvoke) != null) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            return false;
        }
    }

    public static void resetForGui() {
        try {
            if (e) {
                e = false;
                Object objInvoke = Class.forName("net.minecraft.client.MinecraftClient").getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
                if (objInvoke == null) {
                    return;
                }
                Object objInvoke2 = objInvoke.getClass().getMethod("getFramebuffer", new Class[0]).invoke(objInvoke, new Object[0]);
                if (objInvoke2 != null) {
                    objInvoke2.getClass().getMethod("beginWrite", Boolean.TYPE).invoke(objInvoke2, Boolean.TRUE);
                }
                Class<?> cls = Class.forName("com.mojang.blaze3d.systems.RenderSystem");
                cls.getMethod("setShaderColor", Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE).invoke(null, Float.valueOf(1.0f), Float.valueOf(1.0f), Float.valueOf(1.0f), Float.valueOf(1.0f));
                cls.getMethod("enableDepthTest", new Class[0]).invoke(null, new Object[0]);
                cls.getMethod("disableBlend", new Class[0]).invoke(null, new Object[0]);
            }
        } catch (Throwable th) {
        }
    }

    public static void beginGuiRender() {
        try {
            resetForGui();
            Class<?> cls = Class.forName("com.mojang.blaze3d.systems.RenderSystem");
            cls.getMethod("enableBlend", new Class[0]).invoke(null, new Object[0]);
            cls.getMethod("defaultBlendFunc", new Class[0]).invoke(null, new Object[0]);
            cls.getMethod("disableDepthTest", new Class[0]).invoke(null, new Object[0]);
            cls.getMethod("setShaderColor", Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE).invoke(null, Float.valueOf(1.0f), Float.valueOf(1.0f), Float.valueOf(1.0f), Float.valueOf(1.0f));
        } catch (Throwable th) {
        }
    }

    public static void endGuiRender() {
        try {
            Class<?> cls = Class.forName("com.mojang.blaze3d.systems.RenderSystem");
            cls.getMethod("enableDepthTest", new Class[0]).invoke(null, new Object[0]);
            cls.getMethod("setShaderColor", Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE).invoke(null, Float.valueOf(1.0f), Float.valueOf(1.0f), Float.valueOf(1.0f), Float.valueOf(1.0f));
        } catch (Throwable th) {
        }
    }
}
