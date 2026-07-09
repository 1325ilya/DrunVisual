package drunvisual.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.Optional;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.util.Window;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import drunvisual.render.shader.DrunVisualShaderProgram;
import drunvisual.render.shader.ShaderLibrary;

public class Renderer2DImpl implements Renderer2D {
    private final ScissorStack a = new ScissorStack(this);

    @Override
    public void a(float f, float f2, float f3, float f4, float f5, Color color, MatrixStack MatrixStackVar) {
        Window WindowVarGetWindow = MinecraftClient.getInstance().getWindow();
        Optional<DrunVisualShaderProgram> optionalFind = ShaderLibrary.getRegistry().find("round_rect");
        if (optionalFind.isPresent()) {
            DrunVisualShaderProgram pulseShaderProgram = optionalFind.get();
            pulseShaderProgram.d();
            pulseShaderProgram.a("location", f * ScreenScale.d(), (WindowVarGetWindow.getHeight() - (f4 * ScreenScale.d())) - (f2 * ScreenScale.d()));
            pulseShaderProgram.a("rectSize", f3 * ScreenScale.d(), f4 * ScreenScale.d());
            pulseShaderProgram.a("radius", f5);
            pulseShaderProgram.a("color", color);
            BlendRenderScope.runBlended(() -> {
                a(f, f2, f3, f4);
            });
            pulseShaderProgram.e();
        }
    }

    @Override
    public void a(float f, float f2, float f3, float f4, MatrixStack MatrixStackVar) {
        BufferBuilder BufferBuilderVarBegin = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        BufferBuilderVarBegin.vertex(f, f2, 0.0f).texture(0.0f, 0.0f);
        BufferBuilderVarBegin.vertex(f, f2 + f4, 0.0f).texture(0.0f, 1.0f);
        BufferBuilderVarBegin.vertex(f + f3, f2 + f4, 0.0f).texture(1.0f, 1.0f);
        BufferBuilderVarBegin.vertex(f + f3, f2, 0.0f).texture(1.0f, 0.0f);
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
    }

    @Override
    public Window a() {
        return MinecraftClient.getInstance().getWindow();
    }

    @Override
    public void b(float f, float f2, float f3, float f4, float f5, float f6, Color color, MatrixStack MatrixStackVar) {
        Window WindowVarGetWindow = MinecraftClient.getInstance().getWindow();
        Optional<DrunVisualShaderProgram> optionalFind = ShaderLibrary.getRegistry().find("round_rect_outline");
        if (optionalFind.isPresent()) {
            DrunVisualShaderProgram pulseShaderProgram = optionalFind.get();
            pulseShaderProgram.d();
            pulseShaderProgram.a("location", f * ScreenScale.d(), (WindowVarGetWindow.getHeight() - (f4 * ScreenScale.d())) - (f2 * ScreenScale.d()));
            pulseShaderProgram.a("rectSize", f3 * ScreenScale.d(), f4 * ScreenScale.d());
            pulseShaderProgram.a("radius", f5);
            pulseShaderProgram.a("thickness", f6);
            pulseShaderProgram.a("color", new Color(0, 0, 0, 0));
            pulseShaderProgram.a("outlineColor", color);
            BlendRenderScope.runBlended(() -> {
                a(f - f6, f2 - (1.0f + f6), f3 + (f6 * 2.0f), f4 + 2.0f + (f6 * 2.0f));
            });
            pulseShaderProgram.e();
        }
    }

    private void a(double d, double d2, double d3, double d4) {
        DrunVisualShaderProgram.a((float) d, (float) d2, (float) d3, (float) d4);
    }

    @Override
    public void a(int i, float f, float f2, float f3, float f4, Color color, MatrixStack MatrixStackVar) {
        GlStateManager._bindTexture(i);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        RenderSystem.setShaderTexture(0, i);
        RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
        BlendRenderScope.runBlended(() -> {
            Matrix4f matrix4fGetPositionMatrix = MatrixStackVar.peek().getPositionMatrix();
            BufferBuilder BufferBuilderVarBegin = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
            BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f, f2, 0.0f).texture(0.0f, 1.0f).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
            BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f, f2 + f4, 0.0f).texture(0.0f, 0.0f).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
            BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f + f3, f2 + f4, 0.0f).texture(1.0f, 0.0f).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
            BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f + f3, f2, 0.0f).texture(1.0f, 1.0f).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
            BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
        });
        GlStateManager._bindTexture(0);
    }

    @Override
    public void a(Identifier IdentifierVar, float f, float f2, float f3, float f4, Color color, MatrixStack MatrixStackVar) {
        a(MinecraftClient.getInstance().getTextureManager().getTexture(IdentifierVar).getGlId(), f, f2, f3, f4, color, MatrixStackVar);
    }

    @Override
    public void a(float f, float f2, float f3, Color color, MatrixStack MatrixStackVar) {
        Window WindowVarGetWindow = MinecraftClient.getInstance().getWindow();
        Optional<DrunVisualShaderProgram> optionalFind = ShaderLibrary.getRegistry().find("drop_shadow");
        if (optionalFind.isPresent()) {
            DrunVisualShaderProgram pulseShaderProgram = optionalFind.get();
            pulseShaderProgram.d();
            float fD = f3 * 2.0f * ScreenScale.d();
            float f4 = f - f3;
            float f5 = f2 - f3;
            pulseShaderProgram.a("location", f4 * ScreenScale.d(), (WindowVarGetWindow.getHeight() - (fD * ScreenScale.d())) - (f5 * ScreenScale.d()));
            pulseShaderProgram.a("rectSize", fD * ScreenScale.d(), fD * ScreenScale.d());
            pulseShaderProgram.a("radius", fD);
            pulseShaderProgram.a("shadowColor", color);
            BlendRenderScope.runBlended(() -> {
                a(f4, f5, fD, fD);
            });
            pulseShaderProgram.e();
        }
    }

    @Override
    public void a(float f, float f2, float f3, float f4, float f5, float f6, Color color, MatrixStack MatrixStackVar) {
        Window WindowVarGetWindow = MinecraftClient.getInstance().getWindow();
        Optional<DrunVisualShaderProgram> optionalFind = ShaderLibrary.getRegistry().find("rect_glow");
        if (optionalFind.isPresent()) {
            DrunVisualShaderProgram pulseShaderProgram = optionalFind.get();
            pulseShaderProgram.d();
            float fD = ScreenScale.d();
            float f7 = f - f5;
            float f8 = f2 - f5;
            float f9 = f3 + (f5 * 2.0f);
            float f10 = f4 + (f5 * 2.0f);
            pulseShaderProgram.a("location", f7 * fD, (WindowVarGetWindow.getHeight() - (f10 * fD)) - (f8 * fD));
            pulseShaderProgram.a("rectSize", f9 * fD, f10 * fD);
            pulseShaderProgram.a("glowRadius", f5 * fD);
            pulseShaderProgram.a("cornerRadius", f6 * fD);
            pulseShaderProgram.a("glowColor", color);
            BlendRenderScope.runBlended(() -> {
                a(f7, f8, f9, f10);
            });
            pulseShaderProgram.e();
        }
    }

    @Override
    public void a(Identifier IdentifierVar, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, Color color, MatrixStack MatrixStackVar) {
        Window WindowVarGetWindow = MinecraftClient.getInstance().getWindow();
        Optional<DrunVisualShaderProgram> optionalFind = ShaderLibrary.getRegistry().find("round_texture");
        if (optionalFind.isPresent()) {
            DrunVisualShaderProgram pulseShaderProgram = optionalFind.get();
            int iGetGlId = MinecraftClient.getInstance().getTextureManager().getTexture(IdentifierVar).getGlId();
            GlStateManager._activeTexture(33984);
            GlStateManager._bindTexture(iGetGlId);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
            pulseShaderProgram.d();
            pulseShaderProgram.a("location", f * ScreenScale.d(), (WindowVarGetWindow.getHeight() - (f4 * ScreenScale.d())) - (f2 * ScreenScale.d()));
            pulseShaderProgram.a("rectSize", f3 * ScreenScale.d(), f4 * ScreenScale.d());
            pulseShaderProgram.a("radius", f5);
            pulseShaderProgram.b("tex", 0);
            pulseShaderProgram.a("u", f6);
            pulseShaderProgram.a("v", f7);
            pulseShaderProgram.a("w", f8);
            pulseShaderProgram.a("h", f9);
            pulseShaderProgram.a("tintColor", color);
            BlendRenderScope.runBlended(() -> {
                a(f, f2, f3, f4);
            });
            pulseShaderProgram.e();
        }
    }

    @Override
    public void b(float f, float f2, float f3, float f4, MatrixStack MatrixStackVar) {
        Window WindowVarGetWindow = MinecraftClient.getInstance().getWindow();
        float fD = ScreenScale.d();
        GL11.glEnable(3089);
        GL11.glScissor((int) (f * fD), (int) (WindowVarGetWindow.getFramebufferHeight() - ((f2 + f4) * fD)), (int) (f3 * fD), (int) (f4 * fD));
    }

    @Override
    public void a(float f, float f2, float f3, float f4, float f5, MatrixStack MatrixStackVar) {
        b(f, f2, f3, f4, MatrixStackVar);
    }

    @Override
    public void a(MatrixStack MatrixStackVar) {
        GL11.glDisable(3089);
    }

    @Override
    public ScissorStack b() {
        return this.a;
    }

    @Override
    public void a(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, Color color, Color color2, Color color3, Color color4, MatrixStack MatrixStackVar) {
        Window WindowVarGetWindow = MinecraftClient.getInstance().getWindow();
        Optional<DrunVisualShaderProgram> optionalFind = ShaderLibrary.getRegistry().find("gradient");
        if (optionalFind.isPresent()) {
            DrunVisualShaderProgram pulseShaderProgram = optionalFind.get();
            pulseShaderProgram.d();
            float fD = ScreenScale.d();
            pulseShaderProgram.a("location", f * fD, (WindowVarGetWindow.getHeight() - (f4 * fD)) - (f2 * fD));
            pulseShaderProgram.a("rectSize", f3 * fD, f4 * fD);
            pulseShaderProgram.a("radii", f5 * fD, f6 * fD, f7 * fD, f8 * fD);
            pulseShaderProgram.a("topLeftColor", color);
            pulseShaderProgram.a("topRightColor", color2);
            pulseShaderProgram.a("bottomLeftColor", color3);
            pulseShaderProgram.a("bottomRightColor", color4);
            BlendRenderScope.runBlended(() -> {
                a(f, f2, f3, f4);
            });
            pulseShaderProgram.e();
        }
    }

    @Override
    public void a(float f, float f2, float f3, float f4, float f5, float f6, MatrixStack MatrixStackVar) {
        Window WindowVarGetWindow = MinecraftClient.getInstance().getWindow();
        Optional<DrunVisualShaderProgram> optionalFind = ShaderLibrary.getRegistry().find("hue_bar");
        if (optionalFind.isPresent()) {
            DrunVisualShaderProgram pulseShaderProgram = optionalFind.get();
            pulseShaderProgram.d();
            pulseShaderProgram.a("location", f * ScreenScale.d(), (WindowVarGetWindow.getHeight() - (f4 * ScreenScale.d())) - (f2 * ScreenScale.d()));
            pulseShaderProgram.a("rectSize", f3 * ScreenScale.d(), f4 * ScreenScale.d());
            pulseShaderProgram.a("radius", f5 * ScreenScale.d());
            BlendRenderScope.runBlended(() -> {
                a(f, f2, f3, f4);
            });
            pulseShaderProgram.e();
        }
    }

    @Override
    public void c(float f, float f2, float f3, float f4, MatrixStack MatrixStackVar) {
        FramebufferCapture.begin(f, f2, f3, f4);
    }

    @Override
    public void a(float f, MatrixStack MatrixStackVar) {
        FramebufferCapture.end(this, f, MatrixStackVar);
    }
}
