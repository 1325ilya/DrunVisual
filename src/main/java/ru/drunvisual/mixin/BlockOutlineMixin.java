package ru.drunvisual.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import drunvisual.events.BlockOutlineEvent;
import drunvisual.events.EventBusService;
import drunvisual.module.ModuleRegistry;

@Mixin({WorldRenderer.class})
public class BlockOutlineMixin {
    @Inject(method = {"drawBlockOutline"}, at = {@At("HEAD")}, cancellable = true)
    private void onDrawBlockOutline(MatrixStack MatrixStackVar, VertexConsumer VertexConsumerVar, Entity EntityVar, double d, double d2, double d3, BlockPos BlockPosVar, BlockState BlockStateVar, int i, CallbackInfo callbackInfo) {
        if (ModuleRegistry.BLOCK_OVERLAY.k()) {
            BlockOutlineEvent blockOutlineEvent = new BlockOutlineEvent(MatrixStackVar, VertexConsumerVar, EntityVar, d, d2, d3, BlockPosVar, BlockStateVar);
            EventBusService.EVENT_BUS.post(blockOutlineEvent);
            if (blockOutlineEvent.c()) {
                callbackInfo.cancel();
            }
        }
    }
}
