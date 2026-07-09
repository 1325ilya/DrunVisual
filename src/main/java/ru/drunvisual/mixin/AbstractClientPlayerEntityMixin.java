package ru.drunvisual.mixin;

import net.minecraft.util.Identifier;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.util.SkinTextures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import drunvisual.cosmetic.CapeResolver;

@Mixin({AbstractClientPlayerEntity.class})
public abstract class AbstractClientPlayerEntityMixin {
    @Inject(method = {"getSkinTextures"}, at = {@At("RETURN")}, cancellable = true)
    private void drunvisual$useCustomCapeTexture(CallbackInfoReturnable<SkinTextures> callbackInfoReturnable) {
        Identifier IdentifierVarA;
        AbstractClientPlayerEntity AbstractClientPlayerEntityVar = (AbstractClientPlayerEntity)(Object)this;
        if (!CapeResolver.b(AbstractClientPlayerEntityVar) || (IdentifierVarA = CapeResolver.a(AbstractClientPlayerEntityVar)) == null) {
            return;
        }
        SkinTextures SkinTexturesVar = (SkinTextures) callbackInfoReturnable.getReturnValue();
        callbackInfoReturnable.setReturnValue(new SkinTextures(SkinTexturesVar.texture(), SkinTexturesVar.textureUrl(), IdentifierVarA, SkinTexturesVar.elytraTexture(), SkinTexturesVar.model(), SkinTexturesVar.secure()));
    }
}
