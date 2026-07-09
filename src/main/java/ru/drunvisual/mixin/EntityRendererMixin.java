package ru.drunvisual.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import drunvisual.render.icons.IconTextureRegistry;
import drunvisual.player.PlayerListCache;
import drunvisual.player.DrunVisualPlayerTracker;
import drunvisual.gui.config.ConfigBoundsDialog;
import drunvisual.gui.config.ConfigTextDialog;

@Mixin({EntityRenderer.class})
public abstract class EntityRendererMixin<ConfigTextDialog extends Entity, ConfigBoundsDialog extends EntityRenderState> {

    @Unique
    private static final int ICON_SIZE = 8;

    @Unique
    private static final int ICON_PADDING = 2;

    @Unique
    private static final int BACKGROUND_HEIGHT = 9;

    @Unique
    private static final float BG_Z = 0.0f;

    @Unique
    private static final float ICON_Z = 0.0f;

    @Unique
    private static final float SEE_THROUGH_ALPHA = 0.1254902f;

    @Unique
    private static final float SNEAK_DARKEN = 0.5f;

    @Unique
    private boolean shouldRenderIcon = false;

    @Unique
    private boolean isRenderingName = false;

    @Unique
    private int yOffset = 0;

    @Unique
    private float textWidth = 0.0f;

    @Unique
    private boolean isEntitySneaking = false;

    @Unique
    private boolean isSeeThroughMode = false;

    @Shadow
    public abstract TextRenderer getTextRenderer();

    @Inject(method = {"renderLabelIfPresent"}, at = {@At("HEAD")})
    private void onLabelStart(ConfigBoundsDialog configboundsdialog, Text TextVar, MatrixStack MatrixStackVar, VertexConsumerProvider VertexConsumerProviderVar, int i, CallbackInfo callbackInfo) {
        this.shouldRenderIcon = false;
        this.isRenderingName = false;
        this.isEntitySneaking = ((EntityRenderState) configboundsdialog).sneaking;
        this.isSeeThroughMode = !this.isEntitySneaking;
        this.yOffset = "deadmau5".equals(TextVar.getString()) ? -10 : 0;
        this.isRenderingName = true;
        if (configboundsdialog instanceof PlayerEntityRenderState) {
            PlayerEntityRenderState PlayerEntityRenderStateVar = (PlayerEntityRenderState) configboundsdialog;
            if (PlayerEntityRenderStateVar.name != null) {
                boolean zContains = TextVar.getString().contains(PlayerEntityRenderStateVar.name);
                boolean z = PlayerListCache.c().a(PlayerEntityRenderStateVar.name) || DrunVisualPlayerTracker.get().has(PlayerEntityRenderStateVar.name);
                if (zContains && z) {
                    this.shouldRenderIcon = true;
                }
            }
        }
        this.textWidth = getTextRenderer().getWidth(TextVar);
    }

    @Unique
    private float shiftedX(float f) {
        return (this.shouldRenderIcon && this.isRenderingName) ? f + 5.0f : f;
    }

    @ModifyArg(method = {"renderLabelIfPresent"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I", ordinal = 0), index = 1)
    private float shiftTextFirst(float f) {
        return shiftedX(f);
    }

    @ModifyArg(method = {"renderLabelIfPresent"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I", ordinal = 1), index = 1)
    private float shiftTextSecond(float f) {
        return shiftedX(f);
    }

    @Unique
    private int maybeKillBackground(int i) {
        if (this.shouldRenderIcon && this.isRenderingName) {
            return 0;
        }
        return i;
    }

    @ModifyArg(method = {"renderLabelIfPresent"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I", ordinal = 0), index = ICON_SIZE)
    private int killBgFirst(int i) {
        return maybeKillBackground(i);
    }

    @ModifyArg(method = {"renderLabelIfPresent"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I", ordinal = 1), index = ICON_SIZE)
    private int killBgSecond(int i) {
        return maybeKillBackground(i);
    }

    @Inject(method = {"renderLabelIfPresent"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I", ordinal = 0)})
    private void renderCustomBackgroundAndIcon(ConfigBoundsDialog configboundsdialog, Text TextVar, MatrixStack MatrixStackVar, VertexConsumerProvider VertexConsumerProviderVar, int i, CallbackInfo callbackInfo) {
        if (this.shouldRenderIcon && this.isRenderingName) {
            float f = 10.0f + this.textWidth;
            float f2 = (-f) / 2.0f;
            int iGetTextBackgroundOpacity = (((int) (MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25f) * 255.0f)) & 255) << 24;
            Matrix4f matrix4fGetPositionMatrix = MatrixStackVar.peek().getPositionMatrix();
            RenderSystem.depthMask(false);
            try {
                renderCustomBackground(matrix4fGetPositionMatrix, f2, f, iGetTextBackgroundOpacity, this.isSeeThroughMode);
                float f3 = this.yOffset + SNEAK_DARKEN;
                if (this.isSeeThroughMode) {
                    renderIconTwoPass(matrix4fGetPositionMatrix, f2, f3);
                } else {
                    renderIconSingleNormal(matrix4fGetPositionMatrix, f2, f3);
                }
            } finally {
                RenderSystem.depthMask(true);
                RenderSystem.depthFunc(515);
                RenderSystem.enableDepthTest();
                RenderSystem.disableBlend();
                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
    }

    @Unique
    private void renderCustomBackground(Matrix4f matrix4f, float f, float f2, int i, boolean z) {
        int i2 = (i >>> 24) & 255;
        int i3 = (i >>> 16) & 255;
        int i4 = (i >>> ICON_SIZE) & 255;
        int i5 = i & 255;
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(z ? 519 : 515);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        BufferBuilder BufferBuilderVarBegin = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        float f3 = f - 1.0f;
        float f4 = f + f2 + 1.0f;
        float f5 = this.yOffset - 1;
        float f6 = this.yOffset + BACKGROUND_HEIGHT + 1;
        BufferBuilderVarBegin.vertex(matrix4f, f3, f5, 0.0f).color(i3, i4, i5, i2);
        BufferBuilderVarBegin.vertex(matrix4f, f3, f6, 0.0f).color(i3, i4, i5, i2);
        BufferBuilderVarBegin.vertex(matrix4f, f4, f6, 0.0f).color(i3, i4, i5, i2);
        BufferBuilderVarBegin.vertex(matrix4f, f4, f5, 0.0f).color(i3, i4, i5, i2);
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
    }

    @Unique
    private void renderIconTwoPass(Matrix4f matrix4f, float f, float f2) {
        Identifier IdentifierVar = IconTextureRegistry.get(IconTextureRegistry.LOGO);
        RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
        RenderSystem.setShaderTexture(0, IdentifierVar);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(519);
        RenderSystem.defaultBlendFunc();
        drawIconQuad(matrix4f, f, f2, 1.0f, 1.0f, 1.0f, SEE_THROUGH_ALPHA);
        RenderSystem.depthFunc(515);
        drawIconQuad(matrix4f, f, f2, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Unique
    private void renderIconSingleNormal(Matrix4f matrix4f, float f, float f2) {
        Identifier IdentifierVar = IconTextureRegistry.get(IconTextureRegistry.LOGO);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(515);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
        RenderSystem.setShaderTexture(0, IdentifierVar);
        drawIconQuad(matrix4f, f, f2, SNEAK_DARKEN, SNEAK_DARKEN, SNEAK_DARKEN, 1.0f);
    }

    @Unique
    private void drawIconQuad(Matrix4f matrix4f, float f, float f2, float f3, float f4, float f5, float f6) {
        int i = (int) (f3 * 255.0f);
        int i2 = (int) (f4 * 255.0f);
        int i3 = (int) (f5 * 255.0f);
        int i4 = (int) (f6 * 255.0f);
        BufferBuilder BufferBuilderVarBegin = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        BufferBuilderVarBegin.vertex(matrix4f, f, f2, 0.0f).texture(0.0f, 0.0f).color(i, i2, i3, i4);
        BufferBuilderVarBegin.vertex(matrix4f, f, f2 + 8.0f, 0.0f).texture(0.0f, 1.0f).color(i, i2, i3, i4);
        BufferBuilderVarBegin.vertex(matrix4f, f + 8.0f, f2 + 8.0f, 0.0f).texture(1.0f, 1.0f).color(i, i2, i3, i4);
        BufferBuilderVarBegin.vertex(matrix4f, f + 8.0f, f2, 0.0f).texture(1.0f, 0.0f).color(i, i2, i3, i4);
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
    }
}
