package ru.drunvisual.mixin;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import drunvisual.events.EventBusService;
import drunvisual.events.PlayerJoinEvent;
import drunvisual.events.PlayerLeaveEvent;

@Mixin({ClientPlayNetworkHandler.class})
public class PlayerListMixin {

    @Unique
    private static final ConcurrentHashMap<UUID, String> uuidToNameCache = new ConcurrentHashMap<>();

    @Inject(method = {"onPlayerList"}, at = {@At("HEAD")})
    private void onPlayerListUpdate(PlayerListS2CPacket PlayerListS2CPacketVar, CallbackInfo callbackInfo) {
        for (PlayerListS2CPacket.Entry class_2705Var : PlayerListS2CPacketVar.getEntries()) {
            UUID uuidProfileId = class_2705Var.profileId();
            if (PlayerListS2CPacketVar.getActions().contains(PlayerListS2CPacket.Action.ADD_PLAYER) && class_2705Var.profile() != null && class_2705Var.profile().getName() != null) {
                String name = class_2705Var.profile().getName();
                if (!uuidToNameCache.containsKey(uuidProfileId)) {
                    uuidToNameCache.put(uuidProfileId, name);
                    EventBusService.EVENT_BUS.post(new PlayerJoinEvent(name, uuidProfileId));
                }
            }
            if (PlayerListS2CPacketVar.getActions().contains(PlayerListS2CPacket.Action.UPDATE_LISTED) && !class_2705Var.listed()) {
                String str = uuidToNameCache.get(uuidProfileId);
                if (str != null) {
                    EventBusService.EVENT_BUS.post(new PlayerLeaveEvent(str, uuidProfileId));
                    uuidToNameCache.remove(uuidProfileId);
                } else {
                    EventBusService.EVENT_BUS.post(new PlayerLeaveEvent("", uuidProfileId));
                }
            }
        }
    }

    @Inject(method = {"onGameJoin"}, at = {@At("HEAD")})
    private void onGameJoin(GameJoinS2CPacket GameJoinS2CPacketVar, CallbackInfo callbackInfo) {
        uuidToNameCache.clear();
    }
}
