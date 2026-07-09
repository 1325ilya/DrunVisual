package drunvisual.player;

import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import drunvisual.auth.AccountServiceClient;

public final class PlayerListCache {
    private static final PlayerListCache INSTANCE = new PlayerListCache();
    private final Map<String, Integer> capeIdsByName = new ConcurrentHashMap();
    private final AccountServiceClient accountService = AccountServiceClient.A();
    private volatile String currentServer = "";

    private PlayerListCache() {
    }

    public static PlayerListCache c() {
        return INSTANCE;
    }

    public void a() {
        refreshServer();
        this.capeIdsByName.clear();
    }

    public void b() {
        this.currentServer = "";
        this.capeIdsByName.clear();
    }

    public void a(PlayerListS2CPacket PlayerListS2CPacketVar) {
        refreshServer();
    }

    public boolean a(String str) {
        return b(str) != null;
    }

    public Integer b(String str) {
        if (str == null || str.isBlank()) {
            return null;
        }
        String strNormalize = normalize(str);
        Integer num = this.capeIdsByName.get(strNormalize);
        if (num != null) {
            return num;
        }
        Integer numA = a(str, e());
        if (numA != null) {
            this.capeIdsByName.put(strNormalize, numA);
        }
        return numA;
    }

    public Integer a(String str, String str2) {
        if (str == null || str.isBlank()) {
            return null;
        }
        try {
            return this.accountService.c(str, str2 == null ? "" : str2);
        } catch (RuntimeException e) {
            return null;
        }
    }

    private Collection<PlayerListEntry> d() {
        ClientPlayNetworkHandler ClientPlayNetworkHandlerVarGetNetworkHandler = MinecraftClient.getInstance().getNetworkHandler();
        return ClientPlayNetworkHandlerVarGetNetworkHandler == null ? Collections.emptyList() : ClientPlayNetworkHandlerVarGetNetworkHandler.getListedPlayerListEntries();
    }

    private String e() {
        refreshServer();
        return this.currentServer;
    }

    private void refreshServer() {
        MinecraftClient MinecraftClientVarGetInstance = MinecraftClient.getInstance();
        this.currentServer = MinecraftClientVarGetInstance.getCurrentServerEntry() == null ? "" : MinecraftClientVarGetInstance.getCurrentServerEntry().address;
    }

    private static String normalize(String str) {
        return str.toLowerCase(Locale.ROOT);
    }
}
