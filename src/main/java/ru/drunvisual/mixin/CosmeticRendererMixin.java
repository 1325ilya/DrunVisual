package ru.drunvisual.mixin;

import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import drunvisual.cosmetic.CosmeticFeatureRenderer;

@Mixin({PlayerEntityRenderer.class})
public abstract class CosmeticRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityRenderState, PlayerEntityModel> {
    public CosmeticRendererMixin(EntityRendererFactory.Context class_5618Var, PlayerEntityModel PlayerEntityModelVar, float f) {
        super(class_5618Var, PlayerEntityModelVar, f);
    }

    @Inject(method = {"<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;Z)V"}, at = {@At("RETURN")})
    private void onInit(EntityRendererFactory.Context class_5618Var, boolean z, CallbackInfo callbackInfo) {
        addFeature(new CosmeticFeatureRenderer(this));
    }

}
