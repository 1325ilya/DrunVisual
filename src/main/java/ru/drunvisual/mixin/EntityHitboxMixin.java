package ru.drunvisual.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import drunvisual.modules.visuals.HitboxCustomizer;
import drunvisual.module.ModuleRegistry;

@Mixin({EntityRenderDispatcher.class})
public class EntityHitboxMixin {
    @Inject(method = {"renderHitbox"}, at = {@At("HEAD")}, cancellable = true)
    private static void renderCustomHitbox(MatrixStack MatrixStackVar, VertexConsumer VertexConsumerVar, Entity EntityVar, float f, float f2, float f3, float f4, CallbackInfo callbackInfo) {
        HitboxCustomizer hitboxCustomizer = ModuleRegistry.HITBOX_CUSTOMIZER;
        if (hitboxCustomizer.k()) {
            MinecraftClient MinecraftClientVarGetInstance = MinecraftClient.getInstance();
            if (MinecraftClientVarGetInstance.player == null || !MinecraftClientVarGetInstance.player.hasStatusEffect(StatusEffects.BLINDNESS)) {
                Box BoxVarOffset = EntityVar.getBoundingBox().offset(-EntityVar.getX(), -EntityVar.getY(), -EntityVar.getZ());
                if (hitboxCustomizer.o()) {
                    renderFilledBox(MatrixStackVar, BoxVarOffset, hitboxCustomizer);
                }
                renderHitboxOutline(MatrixStackVar, VertexConsumerVar, BoxVarOffset, hitboxCustomizer);
                if (hitboxCustomizer.n() && (EntityVar instanceof LivingEntity)) {
                    renderEyeLine(MatrixStackVar, VertexConsumerVar, EntityVar, f, hitboxCustomizer);
                }
                callbackInfo.cancel();
            }
        }
    }

    private static void renderFilledBox(MatrixStack MatrixStackVar, Box BoxVar, HitboxCustomizer hitboxCustomizer) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(515);
        RenderSystem.disableCull();
        BufferBuilder BufferBuilderVarBegin = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        Color colorP = hitboxCustomizer.p();
        int red = colorP.getRed();
        int green = colorP.getGreen();
        int blue = colorP.getBlue();
        Matrix4f matrix4fGetPositionMatrix = MatrixStackVar.peek().getPositionMatrix();
        float f = (float) BoxVar.minX;
        float f2 = (float) BoxVar.minY;
        float f3 = (float) BoxVar.minZ;
        float f4 = (float) BoxVar.maxX;
        float f5 = (float) BoxVar.maxY;
        float f6 = (float) BoxVar.maxZ;
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f, f2, f3).color(red, green, blue, 64);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f, f5, f3).color(red, green, blue, 64);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f4, f5, f3).color(red, green, blue, 64);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f4, f2, f3).color(red, green, blue, 64);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f4, f2, f6).color(red, green, blue, 64);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f4, f5, f6).color(red, green, blue, 64);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f, f5, f6).color(red, green, blue, 64);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f, f2, f6).color(red, green, blue, 64);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f, f2, f3).color(red, green, blue, 64);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f, f2, f6).color(red, green, blue, 64);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f, f5, f6).color(red, green, blue, 64);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f, f5, f3).color(red, green, blue, 64);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f4, f5, f3).color(red, green, blue, 64);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f4, f5, f6).color(red, green, blue, 64);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f4, f2, f6).color(red, green, blue, 64);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f4, f2, f3).color(red, green, blue, 64);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f, f2, f3).color(red, green, blue, 64);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f4, f2, f3).color(red, green, blue, 64);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f4, f2, f6).color(red, green, blue, 64);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f, f2, f6).color(red, green, blue, 64);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f, f5, f6).color(red, green, blue, 64);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f4, f5, f6).color(red, green, blue, 64);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f4, f5, f3).color(red, green, blue, 64);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f, f5, f3).color(red, green, blue, 64);
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    private static void renderHitboxOutline(MatrixStack MatrixStackVar, VertexConsumer VertexConsumerVar, Box BoxVar, HitboxCustomizer hitboxCustomizer) {
        if (hitboxCustomizer.q()) {
            renderCornerOutline(MatrixStackVar, VertexConsumerVar, BoxVar, hitboxCustomizer);
        } else {
            renderFullOutline(MatrixStackVar, VertexConsumerVar, BoxVar, hitboxCustomizer);
        }
    }

    private static void renderFullOutline(MatrixStack MatrixStackVar, VertexConsumer VertexConsumerVar, Box BoxVar, HitboxCustomizer hitboxCustomizer) {
        MatrixStack.Entry class_4665VarPeek = MatrixStackVar.peek();
        Color colorP = hitboxCustomizer.p();
        float red = colorP.getRed() / 255.0f;
        float green = colorP.getGreen() / 255.0f;
        float blue = colorP.getBlue() / 255.0f;
        VertexConsumerVar.vertex(class_4665VarPeek, (float) BoxVar.minX, (float) BoxVar.minY, (float) BoxVar.minZ).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 1.0f, 0.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, (float) BoxVar.maxX, (float) BoxVar.minY, (float) BoxVar.minZ).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 1.0f, 0.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, (float) BoxVar.maxX, (float) BoxVar.minY, (float) BoxVar.minZ).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 1.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, (float) BoxVar.maxX, (float) BoxVar.maxY, (float) BoxVar.minZ).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 1.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, (float) BoxVar.maxX, (float) BoxVar.maxY, (float) BoxVar.minZ).color(red, green, blue, 1.0f).normal(class_4665VarPeek, -1.0f, 0.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, (float) BoxVar.minX, (float) BoxVar.maxY, (float) BoxVar.minZ).color(red, green, blue, 1.0f).normal(class_4665VarPeek, -1.0f, 0.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, (float) BoxVar.minX, (float) BoxVar.maxY, (float) BoxVar.minZ).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, -1.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, (float) BoxVar.minX, (float) BoxVar.minY, (float) BoxVar.minZ).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, -1.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, (float) BoxVar.minX, (float) BoxVar.minY, (float) BoxVar.maxZ).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 1.0f, 0.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, (float) BoxVar.maxX, (float) BoxVar.minY, (float) BoxVar.maxZ).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 1.0f, 0.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, (float) BoxVar.maxX, (float) BoxVar.minY, (float) BoxVar.maxZ).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 1.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, (float) BoxVar.maxX, (float) BoxVar.maxY, (float) BoxVar.maxZ).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 1.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, (float) BoxVar.maxX, (float) BoxVar.maxY, (float) BoxVar.maxZ).color(red, green, blue, 1.0f).normal(class_4665VarPeek, -1.0f, 0.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, (float) BoxVar.minX, (float) BoxVar.maxY, (float) BoxVar.maxZ).color(red, green, blue, 1.0f).normal(class_4665VarPeek, -1.0f, 0.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, (float) BoxVar.minX, (float) BoxVar.maxY, (float) BoxVar.maxZ).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, -1.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, (float) BoxVar.minX, (float) BoxVar.minY, (float) BoxVar.maxZ).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, -1.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, (float) BoxVar.minX, (float) BoxVar.minY, (float) BoxVar.minZ).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 0.0f, 1.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, (float) BoxVar.minX, (float) BoxVar.minY, (float) BoxVar.maxZ).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 0.0f, 1.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, (float) BoxVar.maxX, (float) BoxVar.minY, (float) BoxVar.minZ).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 0.0f, 1.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, (float) BoxVar.maxX, (float) BoxVar.minY, (float) BoxVar.maxZ).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 0.0f, 1.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, (float) BoxVar.maxX, (float) BoxVar.maxY, (float) BoxVar.minZ).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 0.0f, 1.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, (float) BoxVar.maxX, (float) BoxVar.maxY, (float) BoxVar.maxZ).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 0.0f, 1.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, (float) BoxVar.minX, (float) BoxVar.maxY, (float) BoxVar.minZ).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 0.0f, 1.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, (float) BoxVar.minX, (float) BoxVar.maxY, (float) BoxVar.maxZ).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 0.0f, 1.0f);
    }

    private static void renderCornerOutline(MatrixStack MatrixStackVar, VertexConsumer VertexConsumerVar, Box BoxVar, HitboxCustomizer hitboxCustomizer) {
        MatrixStack.Entry class_4665VarPeek = MatrixStackVar.peek();
        Color colorP = hitboxCustomizer.p();
        float red = colorP.getRed() / 255.0f;
        float green = colorP.getGreen() / 255.0f;
        float blue = colorP.getBlue() / 255.0f;
        float fR = hitboxCustomizer.r();
        float f = (float) BoxVar.minX;
        float f2 = (float) BoxVar.minY;
        float f3 = (float) BoxVar.minZ;
        float f4 = (float) BoxVar.maxX;
        float f5 = (float) BoxVar.maxY;
        float f6 = (float) BoxVar.maxZ;
        VertexConsumerVar.vertex(class_4665VarPeek, f, f2, f3).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 1.0f, 0.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f + fR, f2, f3).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 1.0f, 0.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f, f2, f3).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 1.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f, f2 + fR, f3).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 1.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f, f2, f3).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 0.0f, 1.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f, f2, f3 + fR).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 0.0f, 1.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f4, f2, f3).color(red, green, blue, 1.0f).normal(class_4665VarPeek, -1.0f, 0.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f4 - fR, f2, f3).color(red, green, blue, 1.0f).normal(class_4665VarPeek, -1.0f, 0.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f4, f2, f3).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 1.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f4, f2 + fR, f3).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 1.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f4, f2, f3).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 0.0f, 1.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f4, f2, f3 + fR).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 0.0f, 1.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f, f5, f3).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 1.0f, 0.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f + fR, f5, f3).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 1.0f, 0.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f, f5, f3).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, -1.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f, f5 - fR, f3).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, -1.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f, f5, f3).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 0.0f, 1.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f, f5, f3 + fR).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 0.0f, 1.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f4, f5, f3).color(red, green, blue, 1.0f).normal(class_4665VarPeek, -1.0f, 0.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f4 - fR, f5, f3).color(red, green, blue, 1.0f).normal(class_4665VarPeek, -1.0f, 0.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f4, f5, f3).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, -1.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f4, f5 - fR, f3).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, -1.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f4, f5, f3).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 0.0f, 1.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f4, f5, f3 + fR).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 0.0f, 1.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f, f2, f6).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 1.0f, 0.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f + fR, f2, f6).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 1.0f, 0.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f, f2, f6).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 1.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f, f2 + fR, f6).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 1.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f, f2, f6).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 0.0f, -1.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f, f2, f6 - fR).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 0.0f, -1.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f4, f2, f6).color(red, green, blue, 1.0f).normal(class_4665VarPeek, -1.0f, 0.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f4 - fR, f2, f6).color(red, green, blue, 1.0f).normal(class_4665VarPeek, -1.0f, 0.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f4, f2, f6).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 1.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f4, f2 + fR, f6).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 1.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f4, f2, f6).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 0.0f, -1.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f4, f2, f6 - fR).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 0.0f, -1.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f, f5, f6).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 1.0f, 0.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f + fR, f5, f6).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 1.0f, 0.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f, f5, f6).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, -1.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f, f5 - fR, f6).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, -1.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f, f5, f6).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 0.0f, -1.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f, f5, f6 - fR).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 0.0f, -1.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f4, f5, f6).color(red, green, blue, 1.0f).normal(class_4665VarPeek, -1.0f, 0.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f4 - fR, f5, f6).color(red, green, blue, 1.0f).normal(class_4665VarPeek, -1.0f, 0.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f4, f5, f6).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, -1.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f4, f5 - fR, f6).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, -1.0f, 0.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f4, f5, f6).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 0.0f, -1.0f);
        VertexConsumerVar.vertex(class_4665VarPeek, f4, f5, f6 - fR).color(red, green, blue, 1.0f).normal(class_4665VarPeek, 0.0f, 0.0f, -1.0f);
    }

    private static void renderEyeLine(MatrixStack MatrixStackVar, VertexConsumer VertexConsumerVar, Entity EntityVar, float f, HitboxCustomizer hitboxCustomizer) {
        MatrixStack.Entry class_4665VarPeek = MatrixStackVar.peek();
        Vec3d Vec3dVar = new Vec3d(0.0d, EntityVar.getEyeHeight(EntityVar.getPose()), 0.0d);
        Vec3d Vec3dVarGetRotationVec = EntityVar.getRotationVec(f);
        Vec3d Vec3dVarAdd = Vec3dVar.add(Vec3dVarGetRotationVec.multiply(2.0d));
        Color colorP = hitboxCustomizer.p();
        float red = colorP.getRed() / 255.0f;
        float green = colorP.getGreen() / 255.0f;
        float blue = colorP.getBlue() / 255.0f;
        VertexConsumerVar.vertex(class_4665VarPeek, (float) Vec3dVar.x, (float) Vec3dVar.y, (float) Vec3dVar.z).color(red, green, blue, 1.0f).normal(class_4665VarPeek, (float) Vec3dVarGetRotationVec.x, (float) Vec3dVarGetRotationVec.y, (float) Vec3dVarGetRotationVec.z);
        VertexConsumerVar.vertex(class_4665VarPeek, (float) Vec3dVarAdd.x, (float) Vec3dVarAdd.y, (float) Vec3dVarAdd.z).color(red, green, blue, 1.0f).normal(class_4665VarPeek, (float) Vec3dVarGetRotationVec.x, (float) Vec3dVarGetRotationVec.y, (float) Vec3dVarGetRotationVec.z);
    }
}
