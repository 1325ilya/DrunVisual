package drunvisual.render.world;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.HashSet;
import java.util.Iterator;
import lombok.Generated;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.util.Identifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import drunvisual.client.MinecraftContext;
import ru.drunvisual.DrunVisual;

public final class WorldRenderUtils implements MinecraftContext {
    private static final Tessellator e = Tessellator.getInstance();
    private static final Identifier f = Identifier.of(DrunVisual.CLIENT_NAME, "textures/bloom.png");
    public static int a;
    public static boolean b;

    public static void a(MatrixStack MatrixStackVar, Vec3d Vec3dVar, float f2, int i, Identifier IdentifierVar, float f3, boolean z) {
        RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
        RenderSystem.setShaderTexture(0, IdentifierVar);
        MatrixStackVar.push();
        RenderSystem.disableCull();
        if (z) {
            RenderSystem.disableDepthTest();
            GL11.glDepthMask(false);
        } else {
            GL11.glDepthMask(false);
        }
        RenderSystem.enableBlend();
        int i2 = i >> 16;
        int i3 = ((i2 ^ (-1)) | 255) - (i2 ^ (-1));
        int i4 = i >> 8;
        int i5 = ((i4 ^ (-1)) | 255) - (i4 ^ (-1));
        int i6 = i >> 24;
        int i7 = ((i6 ^ (-1)) | 255) - (i6 ^ (-1));
        RenderSystem.blendFunc(770, 771);
        Vec3d Vec3dVarGetPos = c.getEntityRenderDispatcher().camera.getPos();
        MatrixStackVar.translate((-Vec3dVarGetPos.x) + Vec3dVar.x, (-Vec3dVarGetPos.y) + Vec3dVar.y, (-Vec3dVarGetPos.z) + Vec3dVar.z);
        MatrixStackVar.multiply(c.getEntityRenderDispatcher().getRotation());
        MatrixStackVar.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(f3));
        MatrixStackVar.scale(f2, f2, f2);
        BufferBuilder BufferBuilderVarBegin = e.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        float f4 = i3 / 255.0f;
        float f5 = i5 / 255.0f;
        float f6 = (((i ^ (-1)) | 255) - (i ^ (-1))) / 255.0f;
        float f7 = i7 / 255.0f;
        BufferBuilderVarBegin.vertex(MatrixStackVar.peek().getPositionMatrix(), -0.5f, -0.5f, 0.0f).texture(0.0f, 0.0f).color(f4, f5, f6, f7);
        BufferBuilderVarBegin.vertex(MatrixStackVar.peek().getPositionMatrix(), 0.5f, -0.5f, 0.0f).texture(1.0f, 0.0f).color(f4, f5, f6, f7);
        BufferBuilderVarBegin.vertex(MatrixStackVar.peek().getPositionMatrix(), 0.5f, 0.5f, 0.0f).texture(1.0f, 1.0f).color(f4, f5, f6, f7);
        BufferBuilderVarBegin.vertex(MatrixStackVar.peek().getPositionMatrix(), -0.5f, 0.5f, 0.0f).texture(0.0f, 1.0f).color(f4, f5, f6, f7);
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
        if (z) {
            RenderSystem.enableDepthTest();
            GL11.glDepthMask(true);
        } else {
            GL11.glDepthMask(true);
        }
        RenderSystem.enableCull();
        MatrixStackVar.pop();
    }

    public static void a(MatrixStack MatrixStackVar, Vec3d Vec3dVar, float f2, int i) {
        RenderSystem.setShaderTexture(0, f);
        RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
        MatrixStackVar.push();
        RenderSystem.disableCull();
        GL11.glDepthMask(false);
        RenderSystem.enableBlend();
        int i2 = i >> 16;
        int i3 = ((i2 ^ (-1)) | 255) - (i2 ^ (-1));
        int i4 = i >> 8;
        int i5 = ((i4 ^ (-1)) | 255) - (i4 ^ (-1));
        int i6 = ((i ^ (-1)) | 255) - (i ^ (-1));
        int i7 = i >> 24;
        int i8 = ((i7 ^ (-1)) | 255) - (i7 ^ (-1));
        if (((((i3 ^ i5) + (2 * (i3 & i5))) - (i6 ^ (-1))) - 1) / 765.0f >= 0.2f) {
            RenderSystem.blendFunc(770, 1);
            Vec3d Vec3dVarGetPos = c.getEntityRenderDispatcher().camera.getPos();
            MatrixStackVar.translate((-Vec3dVarGetPos.x) + Vec3dVar.x, (-Vec3dVarGetPos.y) + Vec3dVar.y, (-Vec3dVarGetPos.z) + Vec3dVar.z);
            MatrixStackVar.multiply(c.getEntityRenderDispatcher().getRotation());
            MatrixStackVar.scale(f2, f2, f2);
            BufferBuilder BufferBuilderVarBegin = e.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
            float f3 = i3 / 255.0f;
            float f4 = i5 / 255.0f;
            float f5 = i6 / 255.0f;
            float f6 = i8 / 255.0f;
            BufferBuilderVarBegin.vertex(MatrixStackVar.peek().getPositionMatrix(), -0.5f, -0.5f, 0.0f).texture(0.0f, 0.0f).color(f3, f4, f5, f6);
            BufferBuilderVarBegin.vertex(MatrixStackVar.peek().getPositionMatrix(), 0.5f, -0.5f, 0.0f).texture(1.0f, 0.0f).color(f3, f4, f5, f6);
            BufferBuilderVarBegin.vertex(MatrixStackVar.peek().getPositionMatrix(), 0.5f, 0.5f, 0.0f).texture(1.0f, 1.0f).color(f3, f4, f5, f6);
            BufferBuilderVarBegin.vertex(MatrixStackVar.peek().getPositionMatrix(), -0.5f, 0.5f, 0.0f).texture(0.0f, 1.0f).color(f3, f4, f5, f6);
            BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
            RenderSystem.blendFunc(770, 771);
        } else {
            RenderSystem.blendFunc(770, 771);
            Vec3d Vec3dVarMethod_193262 = c.getEntityRenderDispatcher().camera.getPos();
            MatrixStackVar.translate((-Vec3dVarMethod_193262.x) + Vec3dVar.x, (-Vec3dVarMethod_193262.y) + Vec3dVar.y, (-Vec3dVarMethod_193262.z) + Vec3dVar.z);
            MatrixStackVar.multiply(c.getEntityRenderDispatcher().getRotation());
            MatrixStackVar.scale(f2 * 1.2f, f2 * 1.2f, f2 * 1.2f);
            BufferBuilder BufferBuilderVarMethod_608272 = e.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
            float f7 = (i8 / 255.0f) * 0.3f;
            BufferBuilderVarMethod_608272.vertex(MatrixStackVar.peek().getPositionMatrix(), -0.5f, -0.5f, 0.0f).texture(0.0f, 0.0f).color(1.0f, 1.0f, 1.0f, f7);
            BufferBuilderVarMethod_608272.vertex(MatrixStackVar.peek().getPositionMatrix(), 0.5f, -0.5f, 0.0f).texture(1.0f, 0.0f).color(1.0f, 1.0f, 1.0f, f7);
            BufferBuilderVarMethod_608272.vertex(MatrixStackVar.peek().getPositionMatrix(), 0.5f, 0.5f, 0.0f).texture(1.0f, 1.0f).color(1.0f, 1.0f, 1.0f, f7);
            BufferBuilderVarMethod_608272.vertex(MatrixStackVar.peek().getPositionMatrix(), -0.5f, 0.5f, 0.0f).texture(0.0f, 1.0f).color(1.0f, 1.0f, 1.0f, f7);
            BufferRenderer.drawWithGlobalProgram(BufferBuilderVarMethod_608272.end());
            MatrixStackVar.scale(0.8333333f, 0.8333333f, 0.8333333f);
            BufferBuilder BufferBuilderVarMethod_608273 = e.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
            float f8 = i3 / 255.0f;
            float f9 = i5 / 255.0f;
            float f10 = i6 / 255.0f;
            float f11 = i8 / 255.0f;
            BufferBuilderVarMethod_608273.vertex(MatrixStackVar.peek().getPositionMatrix(), -0.5f, -0.5f, 0.0f).texture(0.0f, 0.0f).color(f8, f9, f10, f11);
            BufferBuilderVarMethod_608273.vertex(MatrixStackVar.peek().getPositionMatrix(), 0.5f, -0.5f, 0.0f).texture(1.0f, 0.0f).color(f8, f9, f10, f11);
            BufferBuilderVarMethod_608273.vertex(MatrixStackVar.peek().getPositionMatrix(), 0.5f, 0.5f, 0.0f).texture(1.0f, 1.0f).color(f8, f9, f10, f11);
            BufferBuilderVarMethod_608273.vertex(MatrixStackVar.peek().getPositionMatrix(), -0.5f, 0.5f, 0.0f).texture(0.0f, 1.0f).color(f8, f9, f10, f11);
            BufferRenderer.drawWithGlobalProgram(BufferBuilderVarMethod_608273.end());
        }
        GL11.glDepthMask(true);
        RenderSystem.enableCull();
        MatrixStackVar.pop();
    }

    public static void a(MatrixStack MatrixStackVar, Box BoxVar, int i) {
        MatrixStackVar.push();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        GL11.glEnable(10754);
        GL11.glPolygonOffset(-1.0f, -1.0f);
        RenderSystem.lineWidth(1.5f);
        Tessellator TessellatorVarGetInstance = Tessellator.getInstance();
        Matrix4f matrix4fGetPositionMatrix = MatrixStackVar.peek().getPositionMatrix();
        Vec3d Vec3dVarGetPos = c.gameRenderer.getCamera().getPos();
        double d = BoxVar.minX - Vec3dVarGetPos.x;
        double d2 = BoxVar.minY - Vec3dVarGetPos.y;
        double d3 = BoxVar.minZ - Vec3dVarGetPos.z;
        double d4 = BoxVar.maxX - Vec3dVarGetPos.x;
        double d5 = BoxVar.maxY - Vec3dVarGetPos.y;
        double d6 = BoxVar.maxZ - Vec3dVarGetPos.z;
        int i2 = i >> 16;
        float f2 = (((i2 ^ (-1)) | 255) - (i2 ^ (-1))) / 255.0f;
        int i3 = i >> 8;
        float f3 = (((i3 ^ (-1)) | 255) - (i3 ^ (-1))) / 255.0f;
        float f4 = (((i ^ (-1)) | 255) - (i ^ (-1))) / 255.0f;
        int i4 = i >> 24;
        float f5 = (((i4 ^ (-1)) | 255) - (i4 ^ (-1))) / 255.0f;
        if (f5 == 0.0f) {
            f5 = 1.0f;
        }
        BufferBuilder BufferBuilderVarBegin = TessellatorVarGetInstance.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d2, (float) d3).color(f2, f3, f4, f5);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d2, (float) d3).color(f2, f3, f4, f5);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d2, (float) d6).color(f2, f3, f4, f5);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d2, (float) d6).color(f2, f3, f4, f5);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d2, (float) d3).color(f2, f3, f4, f5);
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
        BufferBuilder BufferBuilderVarMethod_608272 = TessellatorVarGetInstance.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);
        BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, (float) d, (float) d5, (float) d3).color(f2, f3, f4, f5);
        BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d5, (float) d3).color(f2, f3, f4, f5);
        BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d5, (float) d6).color(f2, f3, f4, f5);
        BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, (float) d, (float) d5, (float) d6).color(f2, f3, f4, f5);
        BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, (float) d, (float) d5, (float) d3).color(f2, f3, f4, f5);
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarMethod_608272.end());
        BufferBuilder BufferBuilderVarMethod_608273 = TessellatorVarGetInstance.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
        BufferBuilderVarMethod_608273.vertex(matrix4fGetPositionMatrix, (float) d, (float) d2, (float) d3).color(f2, f3, f4, f5);
        BufferBuilderVarMethod_608273.vertex(matrix4fGetPositionMatrix, (float) d, (float) d5, (float) d3).color(f2, f3, f4, f5);
        BufferBuilderVarMethod_608273.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d2, (float) d3).color(f2, f3, f4, f5);
        BufferBuilderVarMethod_608273.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d5, (float) d3).color(f2, f3, f4, f5);
        BufferBuilderVarMethod_608273.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d2, (float) d6).color(f2, f3, f4, f5);
        BufferBuilderVarMethod_608273.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d5, (float) d6).color(f2, f3, f4, f5);
        BufferBuilderVarMethod_608273.vertex(matrix4fGetPositionMatrix, (float) d, (float) d2, (float) d6).color(f2, f3, f4, f5);
        BufferBuilderVarMethod_608273.vertex(matrix4fGetPositionMatrix, (float) d, (float) d5, (float) d6).color(f2, f3, f4, f5);
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarMethod_608273.end());
        GL11.glPolygonOffset(0.0f, 0.0f);
        GL11.glDisable(10754);
        RenderSystem.lineWidth(1.0f);
        RenderSystem.disableBlend();
        MatrixStackVar.pop();
    }

    public static void a(MatrixStack MatrixStackVar, Vec3d Vec3dVar, float f2, int i, int i2, float f3) {
        MatrixStackVar.push();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        GL11.glEnable(10754);
        GL11.glPolygonOffset(-1.0f, -1.0f);
        RenderSystem.lineWidth(1.5f);
        Tessellator TessellatorVarGetInstance = Tessellator.getInstance();
        Matrix4f matrix4fGetPositionMatrix = MatrixStackVar.peek().getPositionMatrix();
        Vec3d Vec3dVarGetPos = c.gameRenderer.getCamera().getPos();
        int i3 = i2 >> 16;
        float f4 = (((i3 ^ (-1)) | 255) - (i3 ^ (-1))) / 255.0f;
        int i4 = i2 >> 8;
        float f5 = (((i4 ^ (-1)) | 255) - (i4 ^ (-1))) / 255.0f;
        float f6 = (((i2 ^ (-1)) | 255) - (i2 ^ (-1))) / 255.0f;
        int i5 = i2 >> 24;
        float f7 = (((i5 ^ (-1)) | 255) - (i5 ^ (-1))) / 255.0f;
        if (f7 == 0.0f) {
            f7 = 1.0f;
        }
        BufferBuilder BufferBuilderVarBegin = TessellatorVarGetInstance.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);
        for (int i6 = 0; i6 <= i; i6++) {
            double d = (6.283185307179586d * ((double) i6)) / ((double) i);
            BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) ((Vec3dVar.x + (((double) f2) * Math.cos(d))) - Vec3dVarGetPos.x), (float) (((Vec3dVar.y + ((double) f3)) + 1.0d) - Vec3dVarGetPos.y), (float) ((Vec3dVar.z + (((double) f2) * Math.sin(d))) - Vec3dVarGetPos.z)).color(f4, f5, f6, f7);
        }
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
        GL11.glPolygonOffset(0.0f, 0.0f);
        GL11.glDisable(10754);
        RenderSystem.lineWidth(1.0f);
        RenderSystem.disableBlend();
        MatrixStackVar.pop();
    }

    public static void a(MatrixStack MatrixStackVar, Vec3d Vec3dVar, float f2, int i, int i2) {
        MatrixStackVar.push();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        Tessellator TessellatorVarGetInstance = Tessellator.getInstance();
        Matrix4f matrix4fGetPositionMatrix = MatrixStackVar.peek().getPositionMatrix();
        Vec3d Vec3dVarGetPos = c.gameRenderer.getCamera().getPos();
        int i3 = i2 >> 16;
        float f3 = (((i3 ^ (-1)) | 255) - (i3 ^ (-1))) / 255.0f;
        int i4 = i2 >> 8;
        float f4 = (((i4 ^ (-1)) | 255) - (i4 ^ (-1))) / 255.0f;
        float f5 = (((i2 ^ (-1)) | 255) - (i2 ^ (-1))) / 255.0f;
        int i5 = i2 >> 24;
        float f6 = ((((i5 ^ (-1)) | 255) - (i5 ^ (-1))) / 255.0f) * 0.3f;
        BufferBuilder BufferBuilderVarBegin = TessellatorVarGetInstance.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        double d = Vec3dVar.x - Vec3dVarGetPos.x;
        double d2 = Vec3dVar.y - Vec3dVarGetPos.y;
        double d3 = Vec3dVar.z - Vec3dVarGetPos.z;
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d2, (float) d3).color(f3, f4, f5, f6);
        for (int i6 = 0; i6 <= i; i6++) {
            double d4 = (6.283185307179586d * ((double) i6)) / ((double) i);
            BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) (d + (((double) f2) * Math.cos(d4))), (float) d2, (float) (d3 + (((double) f2) * Math.sin(d4)))).color(f3, f4, f5, f6);
        }
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
        MatrixStackVar.pop();
    }

    public static void a(MatrixStack MatrixStackVar, Vec3d Vec3dVar, float f2, float f3, int i, int i2) {
        MatrixStackVar.push();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.lineWidth(1.5f);
        a(MatrixStackVar, Vec3dVar, f2, i, i2, 0.0f);
        a(MatrixStackVar, Vec3dVar, f2, i, i2, f3);
        Tessellator TessellatorVarGetInstance = Tessellator.getInstance();
        Matrix4f matrix4fGetPositionMatrix = MatrixStackVar.peek().getPositionMatrix();
        Vec3d Vec3dVarGetPos = c.gameRenderer.getCamera().getPos();
        int i3 = i2 >> 16;
        float f4 = (((i3 ^ (-1)) | 255) - (i3 ^ (-1))) / 255.0f;
        int i4 = i2 >> 8;
        float f5 = (((i4 ^ (-1)) | 255) - (i4 ^ (-1))) / 255.0f;
        float f6 = (((i2 ^ (-1)) | 255) - (i2 ^ (-1))) / 255.0f;
        int i5 = i2 >> 24;
        float f7 = (((i5 ^ (-1)) | 255) - (i5 ^ (-1))) / 255.0f;
        if (f7 == 0.0f) {
            f7 = 1.0f;
        }
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        BufferBuilder BufferBuilderVarBegin = TessellatorVarGetInstance.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
        for (int i6 = 0; i6 < 8; i6++) {
            double d = (6.283185307179586d * ((double) i6)) / 8.0d;
            double dCos = (Vec3dVar.x + (((double) f2) * Math.cos(d))) - Vec3dVarGetPos.x;
            double dSin = (Vec3dVar.z + (((double) f2) * Math.sin(d))) - Vec3dVarGetPos.z;
            double d2 = Vec3dVar.y - Vec3dVarGetPos.y;
            BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) dCos, (float) d2, (float) dSin).color(f4, f5, f6, f7);
            BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) dCos, (float) (d2 + ((double) f3)), (float) dSin).color(f4, f5, f6, f7);
        }
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
        RenderSystem.lineWidth(1.0f);
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
        MatrixStackVar.pop();
    }

    public static void b(MatrixStack MatrixStackVar, Box BoxVar, int i) {
        MatrixStackVar.push();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.disableCull();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        Tessellator TessellatorVarGetInstance = Tessellator.getInstance();
        Matrix4f matrix4fGetPositionMatrix = MatrixStackVar.peek().getPositionMatrix();
        Vec3d Vec3dVarGetPos = c.gameRenderer.getCamera().getPos();
        double d = BoxVar.minX - Vec3dVarGetPos.x;
        double d2 = BoxVar.minY - Vec3dVarGetPos.y;
        double d3 = BoxVar.minZ - Vec3dVarGetPos.z;
        double d4 = BoxVar.maxX - Vec3dVarGetPos.x;
        double d5 = BoxVar.maxY - Vec3dVarGetPos.y;
        double d6 = BoxVar.maxZ - Vec3dVarGetPos.z;
        int i2 = i >> 16;
        float f2 = (((i2 ^ (-1)) | 255) - (i2 ^ (-1))) / 255.0f;
        int i3 = i >> 8;
        float f3 = (((i3 ^ (-1)) | 255) - (i3 ^ (-1))) / 255.0f;
        float f4 = (((i ^ (-1)) | 255) - (i ^ (-1))) / 255.0f;
        int i4 = i >> 24;
        float f5 = (((i4 ^ (-1)) | 255) - (i4 ^ (-1))) / 255.0f;
        if (f5 == 0.0f) {
            f5 = 1.0f;
        }
        float f6 = f5 * 0.11f;
        BufferBuilder BufferBuilderVarBegin = TessellatorVarGetInstance.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d2, (float) d3).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d2, (float) d3).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d2, (float) d6).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d2, (float) d6).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d5, (float) d3).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d5, (float) d6).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d5, (float) d6).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d5, (float) d3).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d2, (float) d3).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d5, (float) d3).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d5, (float) d3).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d2, (float) d3).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d2, (float) d6).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d2, (float) d6).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d5, (float) d6).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d5, (float) d6).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d2, (float) d3).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d2, (float) d6).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d5, (float) d6).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d5, (float) d3).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d2, (float) d3).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d5, (float) d3).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d5, (float) d6).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d2, (float) d6).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d2, (float) d3).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d2, (float) d6).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d2, (float) d6).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d2, (float) d3).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d5, (float) d3).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d5, (float) d3).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d5, (float) d6).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d5, (float) d6).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d2, (float) d3).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d5, (float) d3).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d5, (float) d3).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d2, (float) d3).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d2, (float) d6).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d5, (float) d6).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d5, (float) d6).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d2, (float) d6).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d2, (float) d3).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d5, (float) d3).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d5, (float) d6).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d, (float) d2, (float) d6).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d2, (float) d3).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d2, (float) d6).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d5, (float) d6).color(f2, f3, f4, f6);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d5, (float) d3).color(f2, f3, f4, f6);
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
        GL11.glEnable(10754);
        GL11.glPolygonOffset(-1.0f, -1.0f);
        RenderSystem.lineWidth(1.5f);
        BufferBuilder BufferBuilderVarMethod_608272 = TessellatorVarGetInstance.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);
        BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, (float) d, (float) d2, (float) d3).color(f2, f3, f4, f5);
        BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d2, (float) d3).color(f2, f3, f4, f5);
        BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d2, (float) d6).color(f2, f3, f4, f5);
        BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, (float) d, (float) d2, (float) d6).color(f2, f3, f4, f5);
        BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, (float) d, (float) d2, (float) d3).color(f2, f3, f4, f5);
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarMethod_608272.end());
        BufferBuilder BufferBuilderVarMethod_608273 = TessellatorVarGetInstance.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);
        BufferBuilderVarMethod_608273.vertex(matrix4fGetPositionMatrix, (float) d, (float) d5, (float) d3).color(f2, f3, f4, f5);
        BufferBuilderVarMethod_608273.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d5, (float) d3).color(f2, f3, f4, f5);
        BufferBuilderVarMethod_608273.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d5, (float) d6).color(f2, f3, f4, f5);
        BufferBuilderVarMethod_608273.vertex(matrix4fGetPositionMatrix, (float) d, (float) d5, (float) d6).color(f2, f3, f4, f5);
        BufferBuilderVarMethod_608273.vertex(matrix4fGetPositionMatrix, (float) d, (float) d5, (float) d3).color(f2, f3, f4, f5);
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarMethod_608273.end());
        BufferBuilder BufferBuilderVarMethod_608274 = TessellatorVarGetInstance.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
        BufferBuilderVarMethod_608274.vertex(matrix4fGetPositionMatrix, (float) d, (float) d2, (float) d3).color(f2, f3, f4, f5);
        BufferBuilderVarMethod_608274.vertex(matrix4fGetPositionMatrix, (float) d, (float) d5, (float) d3).color(f2, f3, f4, f5);
        BufferBuilderVarMethod_608274.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d2, (float) d3).color(f2, f3, f4, f5);
        BufferBuilderVarMethod_608274.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d5, (float) d3).color(f2, f3, f4, f5);
        BufferBuilderVarMethod_608274.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d2, (float) d6).color(f2, f3, f4, f5);
        BufferBuilderVarMethod_608274.vertex(matrix4fGetPositionMatrix, (float) d4, (float) d5, (float) d6).color(f2, f3, f4, f5);
        BufferBuilderVarMethod_608274.vertex(matrix4fGetPositionMatrix, (float) d, (float) d2, (float) d6).color(f2, f3, f4, f5);
        BufferBuilderVarMethod_608274.vertex(matrix4fGetPositionMatrix, (float) d, (float) d5, (float) d6).color(f2, f3, f4, f5);
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarMethod_608274.end());
        GL11.glPolygonOffset(0.0f, 0.0f);
        GL11.glDisable(10754);
        RenderSystem.lineWidth(1.0f);
        RenderSystem.disableBlend();
        MatrixStackVar.pop();
    }

    public static void a(MatrixStack MatrixStackVar, Iterable<BlockPos> iterable, int i) {
        MatrixStackVar.push();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        GL11.glEnable(10754);
        GL11.glPolygonOffset(-1.0f, -1.0f);
        RenderSystem.lineWidth(1.5f);
        Tessellator TessellatorVarGetInstance = Tessellator.getInstance();
        Matrix4f matrix4fGetPositionMatrix = MatrixStackVar.peek().getPositionMatrix();
        Vec3d Vec3dVarGetPos = c.gameRenderer.getCamera().getPos();
        int i2 = i >> 16;
        float f2 = (((i2 ^ (-1)) | 255) - (i2 ^ (-1))) / 255.0f;
        int i3 = i >> 8;
        float f3 = (((i3 ^ (-1)) | 255) - (i3 ^ (-1))) / 255.0f;
        float f4 = (((i ^ (-1)) | 255) - (i ^ (-1))) / 255.0f;
        int i4 = i >> 24;
        float f5 = (((i4 ^ (-1)) | 255) - (i4 ^ (-1))) / 255.0f;
        if (f5 == 0.0f) {
            f5 = 1.0f;
        }
        HashSet<BlockPos> hashSet = new HashSet();
        Iterator<BlockPos> it = iterable.iterator();
        while (it.hasNext()) {
            hashSet.add(it.next());
        }
        BufferBuilder BufferBuilderVarBegin = TessellatorVarGetInstance.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
        for (BlockPos BlockPosVar : hashSet) {
            float fGetX = BlockPosVar.getX() - ((float) Vec3dVarGetPos.x);
            float fGetY = BlockPosVar.getY() - ((float) Vec3dVarGetPos.y);
            float fGetZ = BlockPosVar.getZ() - ((float) Vec3dVarGetPos.z);
            float f6 = fGetX + 1.0f;
            float f7 = fGetY + 1.0f;
            float f8 = fGetZ + 1.0f;
            if (!hashSet.contains(BlockPosVar.down())) {
                if (!hashSet.contains(BlockPosVar.west())) {
                    BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, fGetY, fGetZ).color(f2, f3, f4, f5);
                    BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, fGetY, f8).color(f2, f3, f4, f5);
                }
                if (!hashSet.contains(BlockPosVar.east())) {
                    BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f6, fGetY, fGetZ).color(f2, f3, f4, f5);
                    BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f6, fGetY, f8).color(f2, f3, f4, f5);
                }
                if (!hashSet.contains(BlockPosVar.north())) {
                    BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, fGetY, fGetZ).color(f2, f3, f4, f5);
                    BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f6, fGetY, fGetZ).color(f2, f3, f4, f5);
                }
                if (!hashSet.contains(BlockPosVar.south())) {
                    BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, fGetY, f8).color(f2, f3, f4, f5);
                    BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f6, fGetY, f8).color(f2, f3, f4, f5);
                }
            }
            if (!hashSet.contains(BlockPosVar.up())) {
                if (!hashSet.contains(BlockPosVar.west())) {
                    BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, f7, fGetZ).color(f2, f3, f4, f5);
                    BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, f7, f8).color(f2, f3, f4, f5);
                }
                if (!hashSet.contains(BlockPosVar.east())) {
                    BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f6, f7, fGetZ).color(f2, f3, f4, f5);
                    BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f6, f7, f8).color(f2, f3, f4, f5);
                }
                if (!hashSet.contains(BlockPosVar.north())) {
                    BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, f7, fGetZ).color(f2, f3, f4, f5);
                    BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f6, f7, fGetZ).color(f2, f3, f4, f5);
                } else if (b) {
                }
                if (!hashSet.contains(BlockPosVar.south())) {
                    BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, f7, f8).color(f2, f3, f4, f5);
                    BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f6, f7, f8).color(f2, f3, f4, f5);
                }
            }
            if (!hashSet.contains(BlockPosVar.west()) && !hashSet.contains(BlockPosVar.north())) {
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, fGetY, fGetZ).color(f2, f3, f4, f5);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, f7, fGetZ).color(f2, f3, f4, f5);
            }
            if (!hashSet.contains(BlockPosVar.east()) && !hashSet.contains(BlockPosVar.north())) {
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f6, fGetY, fGetZ).color(f2, f3, f4, f5);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f6, f7, fGetZ).color(f2, f3, f4, f5);
            }
            if (!hashSet.contains(BlockPosVar.east()) && !hashSet.contains(BlockPosVar.south())) {
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f6, fGetY, f8).color(f2, f3, f4, f5);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f6, f7, f8).color(f2, f3, f4, f5);
            }
            if (!hashSet.contains(BlockPosVar.west()) && !hashSet.contains(BlockPosVar.south())) {
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, fGetY, f8).color(f2, f3, f4, f5);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, f7, f8).color(f2, f3, f4, f5);
            }
            if (!hashSet.contains(BlockPosVar.west()) && hashSet.contains(BlockPosVar.north()) && !hashSet.contains(BlockPosVar.west().north())) {
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, fGetY, fGetZ).color(f2, f3, f4, f5);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, f7, fGetZ).color(f2, f3, f4, f5);
            }
            if (!hashSet.contains(BlockPosVar.north()) && hashSet.contains(BlockPosVar.west()) && !hashSet.contains(BlockPosVar.north().west())) {
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, fGetY, fGetZ).color(f2, f3, f4, f5);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, f7, fGetZ).color(f2, f3, f4, f5);
            }
            if (!hashSet.contains(BlockPosVar.east()) && hashSet.contains(BlockPosVar.north()) && !hashSet.contains(BlockPosVar.east().north())) {
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f6, fGetY, fGetZ).color(f2, f3, f4, f5);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f6, f7, fGetZ).color(f2, f3, f4, f5);
            }
            if (!hashSet.contains(BlockPosVar.north()) && hashSet.contains(BlockPosVar.east()) && !hashSet.contains(BlockPosVar.north().east())) {
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f6, fGetY, fGetZ).color(f2, f3, f4, f5);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f6, f7, fGetZ).color(f2, f3, f4, f5);
            }
            if (!hashSet.contains(BlockPosVar.east()) && hashSet.contains(BlockPosVar.south()) && !hashSet.contains(BlockPosVar.east().south())) {
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f6, fGetY, f8).color(f2, f3, f4, f5);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f6, f7, f8).color(f2, f3, f4, f5);
            }
            if (!hashSet.contains(BlockPosVar.south()) && hashSet.contains(BlockPosVar.east()) && !hashSet.contains(BlockPosVar.south().east())) {
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f6, fGetY, f8).color(f2, f3, f4, f5);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f6, f7, f8).color(f2, f3, f4, f5);
            }
            if (!hashSet.contains(BlockPosVar.west()) && hashSet.contains(BlockPosVar.south()) && !hashSet.contains(BlockPosVar.west().south())) {
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, fGetY, f8).color(f2, f3, f4, f5);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, f7, f8).color(f2, f3, f4, f5);
            }
            if (!hashSet.contains(BlockPosVar.south())) {
                if (hashSet.contains(BlockPosVar.west())) {
                    if (!hashSet.contains(BlockPosVar.south().west())) {
                        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, fGetY, f8).color(f2, f3, f4, f5);
                        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, f7, f8).color(f2, f3, f4, f5);
                    }
                } else if (b) {
                }
            }
        }
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
        GL11.glPolygonOffset(0.0f, 0.0f);
        GL11.glDisable(10754);
        RenderSystem.lineWidth(1.0f);
        RenderSystem.disableBlend();
        MatrixStackVar.pop();
    }

    public static void b(MatrixStack MatrixStackVar, Iterable<BlockPos> iterable, int i) {
        float f2;
        float f3;
        float f4;
        MatrixStackVar.push();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.disableCull();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        Tessellator TessellatorVarGetInstance = Tessellator.getInstance();
        Matrix4f matrix4fGetPositionMatrix = MatrixStackVar.peek().getPositionMatrix();
        Vec3d Vec3dVarGetPos = c.gameRenderer.getCamera().getPos();
        int i2 = i >> 16;
        float f5 = (((i2 ^ (-1)) | 255) - (i2 ^ (-1))) / 255.0f;
        int i3 = i >> 8;
        float f6 = (((i3 ^ (-1)) | 255) - (i3 ^ (-1))) / 255.0f;
        float f7 = (((i ^ (-1)) | 255) - (i ^ (-1))) / 255.0f;
        int i4 = i >> 24;
        float f8 = (((i4 ^ (-1)) | 255) - (i4 ^ (-1))) / 255.0f;
        if (f8 == 0.0f) {
            f8 = 1.0f;
        }
        float f9 = f8 * 0.2f;
        HashSet<BlockPos> hashSet = new HashSet();
        int iMin = Integer.MAX_VALUE;
        int iMin2 = Integer.MAX_VALUE;
        int iMin3 = Integer.MAX_VALUE;
        int iMax = Integer.MIN_VALUE;
        int iMax2 = Integer.MIN_VALUE;
        int iMax3 = Integer.MIN_VALUE;
        for (BlockPos BlockPosVar : iterable) {
            hashSet.add(BlockPosVar);
            iMin = Math.min(iMin, BlockPosVar.getX());
            iMin2 = Math.min(iMin2, BlockPosVar.getY());
            iMin3 = Math.min(iMin3, BlockPosVar.getZ());
            iMax = Math.max(iMax, BlockPosVar.getX());
            iMax2 = Math.max(iMax2, BlockPosVar.getY());
            iMax3 = Math.max(iMax3, BlockPosVar.getZ());
        }
        BufferBuilder BufferBuilderVarBegin = TessellatorVarGetInstance.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        for (BlockPos BlockPosVar2 : hashSet) {
            float f10 = BlockPosVar2.getX() != iMin ? 0.0f : 0.01f;
            float f11 = BlockPosVar2.getY() != iMin2 ? 0.0f : 0.01f;
            float f12 = BlockPosVar2.getZ() != iMin3 ? 0.0f : 0.01f;
            if (BlockPosVar2.getX() != iMax) {
                f3 = 0.0f;
            } else {
                f3 = 0.01f;
            }
            float f13 = f3;
            if (BlockPosVar2.getY() != iMax2) {
                f4 = 0.0f;
            } else {
                f4 = 0.01f;
            }
            float f14 = f4;
            float f15 = BlockPosVar2.getZ() != iMax3 ? 0.0f : 0.01f;
            float fGetX = (BlockPosVar2.getX() - ((float) Vec3dVarGetPos.x)) + f10;
            float fGetY = (BlockPosVar2.getY() - ((float) Vec3dVarGetPos.y)) + f11;
            float fGetZ = (BlockPosVar2.getZ() - ((float) Vec3dVarGetPos.z)) + f12;
            float f16 = ((fGetX + 1.0f) - f10) - f13;
            float f17 = ((fGetY + 1.0f) - f11) - f14;
            float f18 = ((fGetZ + 1.0f) - f12) - f15;
            if (!hashSet.contains(BlockPosVar2.down())) {
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, fGetY, fGetZ).color(f5, f6, f7, f9);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f16, fGetY, fGetZ).color(f5, f6, f7, f9);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f16, fGetY, f18).color(f5, f6, f7, f9);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, fGetY, f18).color(f5, f6, f7, f9);
            }
            if (!hashSet.contains(BlockPosVar2.up())) {
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, f17, fGetZ).color(f5, f6, f7, f9);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, f17, f18).color(f5, f6, f7, f9);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f16, f17, f18).color(f5, f6, f7, f9);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f16, f17, fGetZ).color(f5, f6, f7, f9);
            }
            if (!hashSet.contains(BlockPosVar2.north())) {
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, fGetY, fGetZ).color(f5, f6, f7, f9);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, f17, fGetZ).color(f5, f6, f7, f9);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f16, f17, fGetZ).color(f5, f6, f7, f9);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f16, fGetY, fGetZ).color(f5, f6, f7, f9);
            }
            if (!hashSet.contains(BlockPosVar2.south())) {
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, fGetY, f18).color(f5, f6, f7, f9);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f16, fGetY, f18).color(f5, f6, f7, f9);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f16, f17, f18).color(f5, f6, f7, f9);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, f17, f18).color(f5, f6, f7, f9);
            }
            if (!hashSet.contains(BlockPosVar2.west())) {
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, fGetY, fGetZ).color(f5, f6, f7, f9);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, fGetY, f18).color(f5, f6, f7, f9);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, f17, f18).color(f5, f6, f7, f9);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fGetX, f17, fGetZ).color(f5, f6, f7, f9);
            }
            if (!hashSet.contains(BlockPosVar2.east())) {
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f16, fGetY, fGetZ).color(f5, f6, f7, f9);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f16, f17, fGetZ).color(f5, f6, f7, f9);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f16, f17, f18).color(f5, f6, f7, f9);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f16, fGetY, f18).color(f5, f6, f7, f9);
            }
        }
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
        GL11.glEnable(10754);
        GL11.glPolygonOffset(-1.0f, -1.0f);
        RenderSystem.lineWidth(1.5f);
        BufferBuilder BufferBuilderVarMethod_608272 = TessellatorVarGetInstance.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
        for (BlockPos BlockPosVar3 : hashSet) {
            float f19 = BlockPosVar3.getX() != iMin ? 0.0f : 0.01f;
            float f20 = BlockPosVar3.getY() != iMin2 ? 0.0f : 0.01f;
            float f21 = BlockPosVar3.getZ() != iMin3 ? 0.0f : 0.01f;
            float f22 = BlockPosVar3.getX() != iMax ? 0.0f : 0.01f;
            float f23 = BlockPosVar3.getY() != iMax2 ? 0.0f : 0.01f;
            if (BlockPosVar3.getZ() != iMax3) {
                f2 = 0.0f;
            } else {
                f2 = 0.01f;
            }
            float fMethod_102632 = (BlockPosVar3.getX() - ((float) Vec3dVarGetPos.x)) + f19;
            float fMethod_102642 = (BlockPosVar3.getY() - ((float) Vec3dVarGetPos.y)) + f20;
            float fMethod_102602 = (BlockPosVar3.getZ() - ((float) Vec3dVarGetPos.z)) + f21;
            float f24 = ((fMethod_102632 + 1.0f) - f19) - f22;
            float f25 = ((fMethod_102642 + 1.0f) - f20) - f23;
            float f26 = ((fMethod_102602 + 1.0f) - f21) - f2;
            if (!hashSet.contains(BlockPosVar3.down())) {
                if (!hashSet.contains(BlockPosVar3.west())) {
                    BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, fMethod_102632, fMethod_102642, fMethod_102602).color(f5, f6, f7, f8);
                    BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, fMethod_102632, fMethod_102642, f26).color(f5, f6, f7, f8);
                }
                if (!hashSet.contains(BlockPosVar3.east())) {
                    BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, f24, fMethod_102642, fMethod_102602).color(f5, f6, f7, f8);
                    BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, f24, fMethod_102642, f26).color(f5, f6, f7, f8);
                }
                if (!hashSet.contains(BlockPosVar3.north())) {
                    BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, fMethod_102632, fMethod_102642, fMethod_102602).color(f5, f6, f7, f8);
                    BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, f24, fMethod_102642, fMethod_102602).color(f5, f6, f7, f8);
                }
                if (!hashSet.contains(BlockPosVar3.south())) {
                    BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, fMethod_102632, fMethod_102642, f26).color(f5, f6, f7, f8);
                    BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, f24, fMethod_102642, f26).color(f5, f6, f7, f8);
                }
            }
            if (!hashSet.contains(BlockPosVar3.up())) {
                if (!hashSet.contains(BlockPosVar3.west())) {
                    BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, fMethod_102632, f25, fMethod_102602).color(f5, f6, f7, f8);
                    BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, fMethod_102632, f25, f26).color(f5, f6, f7, f8);
                }
                if (!hashSet.contains(BlockPosVar3.east())) {
                    BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, f24, f25, fMethod_102602).color(f5, f6, f7, f8);
                    BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, f24, f25, f26).color(f5, f6, f7, f8);
                }
                if (!hashSet.contains(BlockPosVar3.north())) {
                    BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, fMethod_102632, f25, fMethod_102602).color(f5, f6, f7, f8);
                    BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, f24, f25, fMethod_102602).color(f5, f6, f7, f8);
                }
                if (!hashSet.contains(BlockPosVar3.south())) {
                    BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, fMethod_102632, f25, f26).color(f5, f6, f7, f8);
                    BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, f24, f25, f26).color(f5, f6, f7, f8);
                }
            }
            if (!hashSet.contains(BlockPosVar3.west()) && !hashSet.contains(BlockPosVar3.north())) {
                BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, fMethod_102632, fMethod_102642, fMethod_102602).color(f5, f6, f7, f8);
                BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, fMethod_102632, f25, fMethod_102602).color(f5, f6, f7, f8);
            }
            if (!hashSet.contains(BlockPosVar3.east()) && !hashSet.contains(BlockPosVar3.north())) {
                BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, f24, fMethod_102642, fMethod_102602).color(f5, f6, f7, f8);
                BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, f24, f25, fMethod_102602).color(f5, f6, f7, f8);
            }
            if (!hashSet.contains(BlockPosVar3.east()) && !hashSet.contains(BlockPosVar3.south())) {
                BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, f24, fMethod_102642, f26).color(f5, f6, f7, f8);
                BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, f24, f25, f26).color(f5, f6, f7, f8);
            }
            if (!hashSet.contains(BlockPosVar3.west()) && !hashSet.contains(BlockPosVar3.south())) {
                BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, fMethod_102632, fMethod_102642, f26).color(f5, f6, f7, f8);
                BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, fMethod_102632, f25, f26).color(f5, f6, f7, f8);
            }
            if (!hashSet.contains(BlockPosVar3.west()) && hashSet.contains(BlockPosVar3.north()) && !hashSet.contains(BlockPosVar3.west().north())) {
                BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, fMethod_102632, fMethod_102642, fMethod_102602).color(f5, f6, f7, f8);
                BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, fMethod_102632, f25, fMethod_102602).color(f5, f6, f7, f8);
            }
            if (!hashSet.contains(BlockPosVar3.north()) && hashSet.contains(BlockPosVar3.west()) && !hashSet.contains(BlockPosVar3.north().west())) {
                BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, fMethod_102632, fMethod_102642, fMethod_102602).color(f5, f6, f7, f8);
                BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, fMethod_102632, f25, fMethod_102602).color(f5, f6, f7, f8);
            }
            if (!hashSet.contains(BlockPosVar3.east())) {
                if (hashSet.contains(BlockPosVar3.north())) {
                    if (!hashSet.contains(BlockPosVar3.east().north())) {
                        BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, f24, fMethod_102642, fMethod_102602).color(f5, f6, f7, f8);
                        BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, f24, f25, fMethod_102602).color(f5, f6, f7, f8);
                    }
                } else if (b) {
                }
            }
            if (!hashSet.contains(BlockPosVar3.north())) {
                if (hashSet.contains(BlockPosVar3.east())) {
                    if (!hashSet.contains(BlockPosVar3.north().east())) {
                        BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, f24, fMethod_102642, fMethod_102602).color(f5, f6, f7, f8);
                        BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, f24, f25, fMethod_102602).color(f5, f6, f7, f8);
                    }
                } else if (b) {
                }
            }
            if (!hashSet.contains(BlockPosVar3.east()) && hashSet.contains(BlockPosVar3.south()) && !hashSet.contains(BlockPosVar3.east().south())) {
                BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, f24, fMethod_102642, f26).color(f5, f6, f7, f8);
                BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, f24, f25, f26).color(f5, f6, f7, f8);
            }
            if (!hashSet.contains(BlockPosVar3.south()) && hashSet.contains(BlockPosVar3.east()) && !hashSet.contains(BlockPosVar3.south().east())) {
                BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, f24, fMethod_102642, f26).color(f5, f6, f7, f8);
                BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, f24, f25, f26).color(f5, f6, f7, f8);
            }
            if (!hashSet.contains(BlockPosVar3.west())) {
                if (!hashSet.contains(BlockPosVar3.south())) {
                } else if (!hashSet.contains(BlockPosVar3.west().south())) {
                    BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, fMethod_102632, fMethod_102642, f26).color(f5, f6, f7, f8);
                    BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, fMethod_102632, f25, f26).color(f5, f6, f7, f8);
                }
            }
            if (!hashSet.contains(BlockPosVar3.south()) && hashSet.contains(BlockPosVar3.west()) && !hashSet.contains(BlockPosVar3.south().west())) {
                BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, fMethod_102632, fMethod_102642, f26).color(f5, f6, f7, f8);
                BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, fMethod_102632, f25, f26).color(f5, f6, f7, f8);
            }
        }
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarMethod_608272.end());
        GL11.glPolygonOffset(0.0f, 0.0f);
        GL11.glDisable(10754);
        RenderSystem.lineWidth(1.0f);
        RenderSystem.disableBlend();
        MatrixStackVar.pop();
    }

    @Generated
    private WorldRenderUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String b(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
