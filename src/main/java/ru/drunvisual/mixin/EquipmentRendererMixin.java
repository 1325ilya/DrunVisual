package ru.drunvisual.mixin;

import java.awt.Color;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import drunvisual.render.ArmorRenderState;
import drunvisual.util.ColorUtils;
import drunvisual.modules.visuals.HitColor;
import drunvisual.module.ModuleRegistry;
import ru.drunvisual.DrunVisual;

@Mixin({EquipmentRenderer.class})
public class EquipmentRendererMixin {

    @Unique
    private static final Identifier HIT_COLOR_TEXTURE = Identifier.of(DrunVisual.CLIENT_NAME, "textures/misc/white.png");

    @ModifyArg(method = {"render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/Model;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V"), index = 4)
    private int modifyArmorColor(int i) {
        HitColor hitColor = ModuleRegistry.HIT_COLOR;
        if (hitColor == null || !hitColor.o() || !hitColor.p() || !ArmorRenderState.a()) {
            return i;
        }
        Color colorN = hitColor.n();
        return ColorUtils.a(colorN.getRed(), colorN.getGreen(), colorN.getBlue(), 255);
    }
}
