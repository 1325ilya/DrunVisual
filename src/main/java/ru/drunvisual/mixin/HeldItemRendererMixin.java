package ru.drunvisual.mixin;

import net.minecraft.util.Hand;
import net.minecraft.util.Arm;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.consume.UseAction;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.item.ModelTransformationMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import drunvisual.config.ConfigState;
import drunvisual.modules.visuals.CustomHand;
import drunvisual.module.ModuleRegistry;

@Mixin({HeldItemRenderer.class})
public abstract class HeldItemRendererMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    private ItemStack offHand;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$item$consume$UseAction = new int[UseAction.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$item$consume$UseAction[UseAction.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$item$consume$UseAction[UseAction.EAT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$item$consume$UseAction[UseAction.DRINK.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$item$consume$UseAction[UseAction.BLOCK.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$item$consume$UseAction[UseAction.BOW.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$minecraft$item$consume$UseAction[UseAction.SPEAR.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$net$minecraft$item$consume$UseAction[UseAction.BRUSH.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    @Shadow
    public abstract void renderItem(LivingEntity LivingEntityVar, ItemStack ItemStackVar, ModelTransformationMode ModelTransformationModeVar, boolean z, MatrixStack MatrixStackVar, VertexConsumerProvider VertexConsumerProviderVar, int i);

    @Shadow
    protected abstract void renderArmHoldingItem(MatrixStack MatrixStackVar, VertexConsumerProvider VertexConsumerProviderVar, int i, float f, float f2, Arm ArmVar);

    @Shadow
    protected abstract void renderMapInOneHand(MatrixStack MatrixStackVar, VertexConsumerProvider VertexConsumerProviderVar, int i, float f, Arm ArmVar, float f2, ItemStack ItemStackVar);

    @Shadow
    protected abstract void renderMapInBothHands(MatrixStack MatrixStackVar, VertexConsumerProvider VertexConsumerProviderVar, int i, float f, float f2, float f3);

    @Shadow
    protected abstract void applyEatOrDrinkTransformation(MatrixStack MatrixStackVar, float f, Arm ArmVar, ItemStack ItemStackVar, PlayerEntity PlayerEntityVar);

    @Shadow
    private void applySwingOffset(MatrixStack MatrixStackVar, Arm ArmVar, float f) {
    }

    @Shadow
    private void applyEquipOffset(MatrixStack MatrixStackVar, Arm ArmVar, float f) {
    }

    @Inject(method = {"applySwingOffset"}, at = {@At("HEAD")}, cancellable = true)
    private void applySwingOffset_tr(MatrixStack MatrixStackVar, Arm ArmVar, float f, CallbackInfo callbackInfo) {
        CustomHand customHand = ModuleRegistry.CUSTOM_HAND;
        if (customHand.k() && customHand.hasCustomSwingAnimation()) {
            int i = ArmVar == Arm.RIGHT ? 1 : -1;
            float fA = customHand.swingStrength.a() * 10.0f;
            MatrixStackVar.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i * (45.0f + (MathHelper.sin(f * f * 3.1415927f) * ((-fA) / 4.0f)))));
            float fSin = MathHelper.sin(MathHelper.sqrt(f) * 3.1415927f);
            MatrixStackVar.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(i * fSin * (-(fA / 4.0f))));
            MatrixStackVar.multiply(RotationAxis.POSITIVE_X.rotationDegrees(fSin * (-fA)));
            MatrixStackVar.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i * (-45.0f)));
        } else {
            int i2 = ArmVar == Arm.RIGHT ? 1 : -1;
            MatrixStackVar.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i2 * (45.0f + (MathHelper.sin(f * f * 3.1415927f) * (-20.0f)))));
            float fMethod_153742 = MathHelper.sin(MathHelper.sqrt(f) * 3.1415927f);
            MatrixStackVar.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(i2 * fMethod_153742 * (-20.0f)));
            MatrixStackVar.multiply(RotationAxis.POSITIVE_X.rotationDegrees(fMethod_153742 * (-80.0f)));
            MatrixStackVar.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i2 * (-45.0f)));
        }
        callbackInfo.cancel();
    }

    @Inject(method = {"applyEquipOffset"}, at = {@At("HEAD")}, cancellable = true)
    private void applyEquipOffset_tr(MatrixStack MatrixStackVar, Arm ArmVar, float f, CallbackInfo callbackInfo) {
        float fA;
        float fA2;
        float fA3;
        float fA4;
        CustomHand customHand = ModuleRegistry.CUSTOM_HAND;
        int i = ArmVar == Arm.RIGHT ? 1 : -1;
        if (customHand.k()) {
            if (ArmVar == this.client.options.getMainArm().getValue()) {
                fA = customHand.mainHandScale.a();
                fA2 = customHand.mainHandOffsetX.a();
                fA3 = customHand.mainHandOffsetY.a();
                fA4 = customHand.mainHandOffsetZ.a();
            } else {
                fA = customHand.offHandScale.a();
                fA2 = customHand.offHandOffsetX.a();
                fA3 = customHand.offHandOffsetY.a();
                fA4 = customHand.offHandOffsetZ.a();
            }
            float f2 = i * 0.56f;
            float f3 = (!customHand.isNoAnimationMode() && customHand.hasCustomSwingAnimation()) ? 0.0f : -0.6f;
            MatrixStackVar.translate(f2, (-0.52f) + (f * f3), -0.72f);
            MatrixStackVar.scale(fA, fA, fA);
            MatrixStackVar.translate(fA2, fA3, fA4);
        } else {
            MatrixStackVar.translate(i * 0.56f, (-0.52f) + (f * (-0.6f)), -0.72f);
        }
        callbackInfo.cancel();
    }

    @Inject(method = {"renderFirstPersonItem"}, at = {@At("HEAD")}, cancellable = true)
    private void renderFirstPersonItem(AbstractClientPlayerEntity AbstractClientPlayerEntityVar, float f, float f2, Hand HandVar, float f3, ItemStack ItemStackVar, float f4, MatrixStack MatrixStackVar, VertexConsumerProvider VertexConsumerProviderVar, int i, CallbackInfo callbackInfo) {
        if (AbstractClientPlayerEntityVar.isUsingSpyglass()) {
            callbackInfo.cancel();
            return;
        }
        boolean z = HandVar == Hand.MAIN_HAND;
        Arm ArmVarGetMainArm = z ? AbstractClientPlayerEntityVar.getMainArm() : AbstractClientPlayerEntityVar.getMainArm().getOpposite();
        CustomHand customHand = ModuleRegistry.CUSTOM_HAND;
        MatrixStackVar.push();
        if (ItemStackVar.isEmpty()) {
            if (z && !AbstractClientPlayerEntityVar.isInvisible()) {
                renderArmHoldingItem(MatrixStackVar, VertexConsumerProviderVar, i, f4, f3, ArmVarGetMainArm);
            }
        } else if (ItemStackVar.isOf(Items.FILLED_MAP)) {
            if (z && this.offHand.isEmpty()) {
                renderMapInBothHands(MatrixStackVar, VertexConsumerProviderVar, i, f2, f4, f3);
            } else {
                renderMapInOneHand(MatrixStackVar, VertexConsumerProviderVar, i, f4, ArmVarGetMainArm, f3, ItemStackVar);
            }
        } else if (ItemStackVar.isOf(Items.CROSSBOW)) {
            boolean zIsCharged = CrossbowItem.isCharged(ItemStackVar);
            boolean z2 = ArmVarGetMainArm == Arm.RIGHT;
            int i2 = z2 ? 1 : -1;
            if (AbstractClientPlayerEntityVar.isUsingItem() && AbstractClientPlayerEntityVar.getItemUseTimeLeft() > 0 && AbstractClientPlayerEntityVar.getActiveHand() == HandVar) {
                applyEquipOffset(MatrixStackVar, ArmVarGetMainArm, f4);
                MatrixStackVar.translate(i2 * (-0.4785682f), -0.094387f, 0.05731531f);
                MatrixStackVar.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-11.935f));
                MatrixStackVar.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i2 * 65.3f));
                MatrixStackVar.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(i2 * (-9.785f)));
                float fGetMaxUseTime = ItemStackVar.getMaxUseTime(AbstractClientPlayerEntityVar) - ((AbstractClientPlayerEntityVar.getItemUseTimeLeft() - f) + 1.0f);
                float fGetPullTime = fGetMaxUseTime / CrossbowItem.getPullTime(ItemStackVar, AbstractClientPlayerEntityVar);
                if (fGetPullTime > 1.0f) {
                    fGetPullTime = 1.0f;
                }
                if (fGetPullTime > 0.1f) {
                    float fSin = MathHelper.sin((fGetMaxUseTime - 0.1f) * 1.3f) * (fGetPullTime - 0.1f);
                    MatrixStackVar.translate(fSin * 0.0f, fSin * 0.004f, fSin * 0.0f);
                }
                MatrixStackVar.translate(fGetPullTime * 0.0f, fGetPullTime * 0.0f, fGetPullTime * 0.04f);
                MatrixStackVar.scale(1.0f, 1.0f, 1.0f + (fGetPullTime * 0.2f));
                MatrixStackVar.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(i2 * 45.0f));
            } else {
                MatrixStackVar.translate(i2 * (-0.4f) * MathHelper.sin(MathHelper.sqrt(f3) * 3.1415927f), 0.2f * MathHelper.sin(MathHelper.sqrt(f3) * 6.2831855f), (-0.2f) * MathHelper.sin(f3 * 3.1415927f));
                applyEquipOffset(MatrixStackVar, ArmVarGetMainArm, f4);
                applySwingOffset(MatrixStackVar, ArmVarGetMainArm, f3);
                if (zIsCharged && f3 < 0.001f && z) {
                    MatrixStackVar.translate(i2 * (-0.641864f), 0.0f, 0.0f);
                    MatrixStackVar.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i2 * 10.0f));
                }
            }
            renderItem(AbstractClientPlayerEntityVar, ItemStackVar, z2 ? ModelTransformationMode.FIRST_PERSON_RIGHT_HAND : ModelTransformationMode.FIRST_PERSON_LEFT_HAND, !z2, MatrixStackVar, VertexConsumerProviderVar, i);
        } else {
            boolean z3 = ArmVarGetMainArm == Arm.RIGHT;
            if (AbstractClientPlayerEntityVar.isUsingItem() && AbstractClientPlayerEntityVar.getItemUseTimeLeft() > 0 && AbstractClientPlayerEntityVar.getActiveHand() == HandVar) {
                int i3 = z3 ? 1 : -1;
                switch (AnonymousClass1.$SwitchMap$net$minecraft$item$consume$UseAction[ItemStackVar.getUseAction().ordinal()]) {
                    case ConfigState.a /* 1 */:
                        applyEquipOffset(MatrixStackVar, ArmVarGetMainArm, f4);
                        break;
                    case 2:
                    case 3:
                        applyEatOrDrinkTransformation(MatrixStackVar, f, ArmVarGetMainArm, ItemStackVar, AbstractClientPlayerEntityVar);
                        applyEquipOffset(MatrixStackVar, ArmVarGetMainArm, f4);
                        break;
                    case 4:
                        applyEquipOffset(MatrixStackVar, ArmVarGetMainArm, f4);
                        break;
                    case 5:
                        applyEquipOffset(MatrixStackVar, ArmVarGetMainArm, f4);
                        MatrixStackVar.translate(i3 * (-0.2785682f), 0.18344387f, 0.15731531f);
                        MatrixStackVar.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-13.935f));
                        MatrixStackVar.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i3 * 35.3f));
                        MatrixStackVar.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(i3 * (-9.785f)));
                        float fMethod_79352 = ItemStackVar.getMaxUseTime(AbstractClientPlayerEntityVar) - ((AbstractClientPlayerEntityVar.getItemUseTimeLeft() - f) + 1.0f);
                        float f5 = fMethod_79352 / 20.0f;
                        float f6 = ((f5 * f5) + (f5 * 2.0f)) / 3.0f;
                        if (f6 > 1.0f) {
                            f6 = 1.0f;
                        }
                        if (f6 > 0.1f) {
                            float fMethod_153742 = MathHelper.sin((fMethod_79352 - 0.1f) * 1.3f) * (f6 - 0.1f);
                            MatrixStackVar.translate(fMethod_153742 * 0.0f, fMethod_153742 * 0.004f, fMethod_153742 * 0.0f);
                        }
                        MatrixStackVar.translate(f6 * 0.0f, f6 * 0.0f, f6 * 0.04f);
                        MatrixStackVar.scale(1.0f, 1.0f, 1.0f + (f6 * 0.2f));
                        MatrixStackVar.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(i3 * 45.0f));
                        break;
                    case 6:
                        applyEquipOffset(MatrixStackVar, ArmVarGetMainArm, f4);
                        MatrixStackVar.translate(i3 * (-0.5f), 0.7f, 0.1f);
                        MatrixStackVar.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-55.0f));
                        MatrixStackVar.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i3 * 35.3f));
                        MatrixStackVar.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(i3 * (-9.785f)));
                        float fMethod_79353 = ItemStackVar.getMaxUseTime(AbstractClientPlayerEntityVar) - ((AbstractClientPlayerEntityVar.getItemUseTimeLeft() - f) + 1.0f);
                        float f7 = fMethod_79353 / 10.0f;
                        if (f7 > 1.0f) {
                            f7 = 1.0f;
                        }
                        if (f7 > 0.1f) {
                            float fMethod_153743 = MathHelper.sin((fMethod_79353 - 0.1f) * 1.3f) * (f7 - 0.1f);
                            MatrixStackVar.translate(fMethod_153743 * 0.0f, fMethod_153743 * 0.004f, fMethod_153743 * 0.0f);
                        }
                        MatrixStackVar.translate(0.0f, 0.0f, f7 * 0.2f);
                        MatrixStackVar.scale(1.0f, 1.0f, 1.0f + (f7 * 0.2f));
                        MatrixStackVar.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(i3 * 45.0f));
                        break;
                }
            } else if (AbstractClientPlayerEntityVar.isUsingRiptide()) {
                applyEquipOffset(MatrixStackVar, ArmVarGetMainArm, f4);
                int i4 = z3 ? 1 : -1;
                MatrixStackVar.translate(i4 * (-0.4f), 0.8f, 0.3f);
                MatrixStackVar.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i4 * 65.0f));
                MatrixStackVar.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(i4 * (-85.0f)));
            } else {
                float fMethod_153744 = (-0.4f) * MathHelper.sin(MathHelper.sqrt(f3) * 3.1415927f);
                float fMethod_153745 = 0.2f * MathHelper.sin(MathHelper.sqrt(f3) * 6.2831855f);
                float fMethod_153746 = (-0.2f) * MathHelper.sin(f3 * 3.1415927f);
                int i5 = z3 ? 1 : -1;
                if (customHand == null || !customHand.k() || customHand.isNoAnimationMode()) {
                    MatrixStackVar.translate(i5 * fMethod_153744, fMethod_153745, fMethod_153746);
                }
                applyEquipOffset(MatrixStackVar, ArmVarGetMainArm, f4);
                boolean z4 = HandVar == Hand.MAIN_HAND;
                if (customHand != null && customHand.k() && z4 && !customHand.isNoAnimationMode() && customHand.hasCustomSwingAnimation()) {
                    customHand.applyCustomSwing(MatrixStackVar, f3, () -> {
                        applySwingOffset(MatrixStackVar, ArmVarGetMainArm, f3);
                    }, ArmVarGetMainArm == Arm.LEFT);
                } else {
                    applySwingOffset(MatrixStackVar, ArmVarGetMainArm, f3);
                }
            }
            renderItem(AbstractClientPlayerEntityVar, ItemStackVar, z3 ? ModelTransformationMode.FIRST_PERSON_RIGHT_HAND : ModelTransformationMode.FIRST_PERSON_LEFT_HAND, !z3, MatrixStackVar, VertexConsumerProviderVar, i);
        }
        MatrixStackVar.pop();
        callbackInfo.cancel();
    }
}
