package drunvisual.cosmetic;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import ru.drunvisual.DrunVisual;
import drunvisual.auth.UserInfo;
import drunvisual.player.PlayerListCache;

public final class CapeResolver {
    private CapeResolver() {
    }

    public static Identifier a(AbstractClientPlayerEntity AbstractClientPlayerEntityVar) {
        Integer numResolveCapeId = resolveCapeId(AbstractClientPlayerEntityVar);
        if (numResolveCapeId == null) {
            return null;
        }
        return CapeTextureCache.getInstance().getCapeTexture(numResolveCapeId.intValue());
    }

    public static boolean b(AbstractClientPlayerEntity AbstractClientPlayerEntityVar) {
        return (AbstractClientPlayerEntityVar == null || a(AbstractClientPlayerEntityVar) == null || AbstractClientPlayerEntityVar.isInvisible() || !AbstractClientPlayerEntityVar.isPartVisible(PlayerModelPart.CAPE) || AbstractClientPlayerEntityVar.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA) ? false : true;
    }

    private static Integer resolveCapeId(AbstractClientPlayerEntity AbstractClientPlayerEntityVar) {
        if (AbstractClientPlayerEntityVar == null) {
            return null;
        }
        MinecraftClient MinecraftClientVarGetInstance = MinecraftClient.getInstance();
        if (MinecraftClientVarGetInstance.player == null || MinecraftClientVarGetInstance.player.getId() != AbstractClientPlayerEntityVar.getId()) {
            return PlayerListCache.c().b(AbstractClientPlayerEntityVar.getGameProfile() == null ? null : AbstractClientPlayerEntityVar.getGameProfile().getName());
        }
        UserInfo userInfo = DrunVisual.getUserInfo();
        if (userInfo == null) {
            return null;
        }
        return userInfo.b();
    }
}
