package drunvisual.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.gl.SimpleFramebuffer;
import org.lwjgl.opengl.GL14;

public class FramebufferCapture {
    private static final MinecraftClient a = MinecraftClient.getInstance();
    private static Framebuffer b = null;
    private static int c = 0;
    private static int d = 0;
    private static boolean e = false;
    private static float f = 0.0f;
    private static float g = 0.0f;
    private static float h = 0.0f;
    private static float i = 0.0f;

    public static void begin(float f2, float f3, float f4, float f5) {
        if (e) {
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
        f = f2;
        g = f3;
        h = f4;
        i = f5;
        b.clear();
        b.beginWrite(true);
        e = true;
    }

    public static void end(Renderer2D renderer2D, float f2, MatrixStack MatrixStackVar) {
        if (e) {
            e = false;
            a.getFramebuffer().beginWrite(true);
            if (f2 > 0.001f) {
                drawCaptured(f2);
            }
        }
    }

    public static boolean isCapturing() {
        return e;
    }

    public static void applyBlendState() {
        if (e) {
            GL14.glBlendFuncSeparate(770, 771, 1, 771);
        } else {
            RenderSystem.defaultBlendFunc();
        }
    }

    private static void drawCaptured(float f2) {
        if (b != null) {
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager._bindTexture(b.getColorAttachment());
            RenderSystem.setShaderTexture(0, b.getColorAttachment());
            RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            float fD = ScreenScale.d();
            float fGetFramebufferWidth = a.getWindow().getFramebufferWidth();
            float fGetFramebufferHeight = a.getWindow().getFramebufferHeight();
            float f3 = (f * fD) / fGetFramebufferWidth;
            float f4 = 1.0f - (((g + i) * fD) / fGetFramebufferHeight);
            float f5 = ((f + h) * fD) / fGetFramebufferWidth;
            float f6 = 1.0f - ((g * fD) / fGetFramebufferHeight);
            int i2 = (int) (f2 * 255.0f);
            BufferBuilder BufferBuilderVarBegin = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
            BufferBuilderVarBegin.vertex(f, g, 0.0f).texture(f3, f6).color(i2, i2, i2, i2);
            BufferBuilderVarBegin.vertex(f, g + i, 0.0f).texture(f3, f4).color(i2, i2, i2, i2);
            BufferBuilderVarBegin.vertex(f + h, g + i, 0.0f).texture(f5, f4).color(i2, i2, i2, i2);
            BufferBuilderVarBegin.vertex(f + h, g, 0.0f).texture(f5, f6).color(i2, i2, i2, i2);
            BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
            GlStateManager._bindTexture(0);
            RenderSystem.defaultBlendFunc();
        }
    }

    public static void dispose() {
        if (b != null) {
            b.delete();
            b = null;
        }
        e = false;
    }
}
