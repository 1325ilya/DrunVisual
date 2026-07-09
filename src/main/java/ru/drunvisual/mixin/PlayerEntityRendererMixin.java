package ru.drunvisual.mixin;

import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import drunvisual.module.ModuleRegistry;

@Mixin({PlayerEntityRenderer.class})
public abstract class PlayerEntityRendererMixin extends EntityRenderer<AbstractClientPlayerEntity, PlayerEntityRenderState> {
    protected PlayerEntityRendererMixin() {
        super((EntityRendererFactory.Context) null);
    }

    @Inject(method = {"renderLabelIfPresent(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at = {@At("HEAD")})
    private void onRenderLabel(PlayerEntityRenderState PlayerEntityRenderStateVar, Text TextVar, MatrixStack MatrixStackVar, VertexConsumerProvider VertexConsumerProviderVar, int i, CallbackInfo callbackInfo) {
        PlayerListEntry PlayerListEntryVarGetPlayerListEntry;
        MinecraftClient MinecraftClientVarGetInstance = MinecraftClient.getInstance();
        if (MinecraftClientVarGetInstance.player == null || PlayerEntityRenderStateVar.id != MinecraftClientVarGetInstance.player.getId() || !ModuleRegistry.SELF_NAMETAG.k() || MinecraftClientVarGetInstance.options.getPerspective().isFirstPerson() || PlayerEntityRenderStateVar.squaredDistanceToCamera >= 4096.0d) {
            return;
        }
        MatrixStackVar.push();
        if (PlayerEntityRenderStateVar.playerName != null) {
            super.renderLabelIfPresent(PlayerEntityRenderStateVar, PlayerEntityRenderStateVar.playerName, MatrixStackVar, VertexConsumerProviderVar, i);
            MatrixStackVar.translate(0.0d, 0.25874999165534973d, 0.0d);
        }
        Text TextVarLiteral = (MinecraftClientVarGetInstance.getNetworkHandler() == null || (PlayerListEntryVarGetPlayerListEntry = MinecraftClientVarGetInstance.getNetworkHandler().getPlayerListEntry(MinecraftClientVarGetInstance.player.getUuid())) == null || PlayerListEntryVarGetPlayerListEntry.getDisplayName() == null) ? Text.literal(PlayerEntityRenderStateVar.name) : PlayerListEntryVarGetPlayerListEntry.getDisplayName();
        super.renderLabelIfPresent(PlayerEntityRenderStateVar, TextVarLiteral, MatrixStackVar, VertexConsumerProviderVar, i);
        MatrixStackVar.pop();
    }
}
