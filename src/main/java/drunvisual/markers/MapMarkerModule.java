package drunvisual.markers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Generated;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.Formatting;
import net.minecraft.text.Text;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import drunvisual.client.MinecraftContext;
import drunvisual.events.ClientTickEvent;
import drunvisual.events.PacketEvent;
import drunvisual.events.WorldChangeEvent;
import drunvisual.hud.core.HudService;
import drunvisual.hud.core.HudServiceInfo;
import drunvisual.hud.notifications.HudNotificationCenter;
import drunvisual.markers.providers.ActiveEventMarkerProvider;
import drunvisual.markers.providers.EventDelayMarkerProvider;
import drunvisual.markers.providers.EventsCommandMarkerProvider;

@HudServiceInfo(enabledByDefault = true)
public class MapMarkerModule extends HudService implements MinecraftContext {
    private static final long DEFAULT_MARKER_LIFETIME_MS = 600000;
    private static final int JOIN_REFRESH_DELAY_TICKS = 20;
    private static final Pattern SERVER_NUMBER_PATTERN = Pattern.compile("(?:анархия|сервер|гриф)\\D*(\\d+)", 2);
    private static final Pattern HEADER_SERVER_PATTERN = Pattern.compile("(?:анархия|сервер|гриф)\\D*(\\d+)(?:\\D+([a-z0-9_.:-]+))?", 2);
    private MarkerProvider activeProvider;
    private boolean needsProviderRefresh;
    private boolean waitingForCommandResponse;
    private String pendingMarkerName;
    public static int a;
    public static boolean b;
    private final List<MarkerProvider> providers = new ArrayList();
    private int pendingServerId = -1;

    public MapMarkerModule() {
        this.providers.add(new EventDelayMarkerProvider());
        this.providers.add(new EventsCommandMarkerProvider());
        this.providers.add(new ActiveEventMarkerProvider());
    }

    @EventHandler
    private void onGameMessage(PacketEvent packetEvent) {
        if (!MarkerSettings.d() || c.player == null) {
            return;
        }
        if (!(packetEvent.d() instanceof GameMessageS2CPacket)) {
            return;
        }
        GameMessageS2CPacket GameMessageS2CPacketVarD = (GameMessageS2CPacket) packetEvent.d();
        {
            String string = GameMessageS2CPacketVarD.content().getString();
            String lowerCase = Formatting.strip(string).toLowerCase(Locale.ROOT);
            updateActiveProvider();
            boolean z = this.waitingForCommandResponse;
            String strCurrentServerAddress = currentServerAddress();
            for (MarkerProvider markerProvider : this.providers) {
                if (markerProvider.supportsServer(strCurrentServerAddress)) {
                    boolean zHandleMessage = markerProvider.handleMessage(lowerCase, string, this);
                    if (zHandleMessage && z) {
                        packetEvent.b();
                    }
                    if (zHandleMessage) {
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    private void onClientTick(ClientTickEvent clientTickEvent) {
        if (MarkerSettings.d() && c.player != null && isConnectedToMarkedServer()) {
            updateActiveProvider();
            refreshProviderAfterJoin();
            MarkerManager.e();
            if (this.activeProvider != null) {
                this.activeProvider.tick(this);
            }
        }
    }

    @EventHandler
    private void onWorldChanged(WorldChangeEvent worldChangeEvent) {
        if (MarkerSettings.d()) {
            this.needsProviderRefresh = true;
        }
    }

    private void updateActiveProvider() {
        String strCurrentServerAddress = currentServerAddress();
        if (strCurrentServerAddress == null) {
            this.activeProvider = null;
            return;
        }
        if (this.activeProvider == null || !this.activeProvider.supportsServer(strCurrentServerAddress)) {
            for (MarkerProvider markerProvider : this.providers) {
                if (markerProvider.supportsServer(strCurrentServerAddress)) {
                    this.activeProvider = markerProvider;
                    return;
                }
            }
            this.activeProvider = null;
        }
    }

    private void refreshProviderAfterJoin() {
        if (!this.needsProviderRefresh || c.player.age <= JOIN_REFRESH_DELAY_TICKS || this.activeProvider == null) {
            return;
        }
        requestProviderCommand();
        this.needsProviderRefresh = false;
    }

    public void d() {
        clearMarkers();
    }

    public void clearMarkers() {
        MarkerManager.c();
        this.pendingMarkerName = null;
    }

    public void a(String str, String str2) {
        sendCommand(str, str2);
    }

    public void sendCommand(String str, String str2) {
        if (str == null || !this.waitingForCommandResponse) {
            return;
        }
        HudNotificationCenter.a("%s %s", str, str2 != null ? str2 : "");
        this.waitingForCommandResponse = false;
    }

    public void a(String str, int i) {
        sendTimedLookup(str, i);
    }

    public void sendTimedLookup(String str, int i) {
        if (this.waitingForCommandResponse) {
            HudNotificationCenter.a("%s %s", formatSeconds(i), capitalizeEventName(str));
            this.waitingForCommandResponse = false;
        }
    }

    public void a(int i) {
        sendDelayLookup(i);
    }

    public void sendDelayLookup(int i) {
        if (!this.waitingForCommandResponse || this.activeProvider == null) {
            return;
        }
        HudNotificationCenter.a("%s %s", this.activeProvider.command(), formatSeconds(i));
        this.waitingForCommandResponse = false;
    }

    public void e() {
        requestProviderCommand();
    }

    public void requestProviderCommand() {
        if (this.activeProvider == null) {
            return;
        }
        HudNotificationCenter.a(this.activeProvider.command());
        this.waitingForCommandResponse = true;
    }

    private String formatSeconds(int i) {
        return i + " сек.";
    }

    public boolean f() {
        return this.waitingForCommandResponse;
    }

    public void g() {
        this.waitingForCommandResponse = true;
    }

    public void a(String str, int[] iArr, int i) {
        addMarker(str, iArr, i);
    }

    public void addMarker(String str, int[] iArr, int i) {
        if (str == null || iArr == null || iArr.length < 3) {
            return;
        }
        int iH = h();
        String strI = i();
        MapMarker mapMarkerA = MarkerManager.a(iArr[0], iArr[1], iArr[2]);
        if (mapMarkerA == null) {
            MapMarkerStyle mapMarkerStyleA = a(str.toLowerCase(Locale.ROOT));
            MarkerManager.a(MapMarker.a(capitalizeEventName(str), iArr[0], iArr[1], iArr[2], mapMarkerStyleA.a, mapMarkerStyleA.b, iH, strI, i > 0 ? ((long) i) * 1000 : DEFAULT_MARKER_LIFETIME_MS, i > 0));
        } else {
            mapMarkerA.a(capitalizeEventName(str));
            if (i > 0) {
                mapMarkerA.a(System.currentTimeMillis() + (((long) i) * 1000));
                mapMarkerA.a(true);
            }
        }
    }

    public void b(String str, int i) {
        setPendingMarker(str, i);
    }

    public void setPendingMarker(String str, int i) {
        this.pendingMarkerName = str;
        this.pendingServerId = i;
    }

    public void a(int[] iArr) {
        addPendingMarker(iArr);
    }

    public void addPendingMarker(int[] iArr) {
        if (this.pendingMarkerName == null || iArr == null) {
            return;
        }
        addMarker(this.pendingMarkerName, iArr, -1);
    }

    public void b(int i) {
        updatePendingMarkerTimer(i);
    }

    public void updatePendingMarkerTimer(int i) {
        if (this.pendingMarkerName == null) {
            return;
        }
        String strCapitalizeEventName = capitalizeEventName(this.pendingMarkerName);
        for (MapMarker mapMarker : MarkerManager.f()) {
            if (mapMarker.a().equalsIgnoreCase(strCapitalizeEventName)) {
                mapMarker.a(System.currentTimeMillis() + (((long) i) * 1000));
                mapMarker.a(true);
                return;
            }
        }
    }

    public int h() {
        Text TextVarPlayerListHeader = playerListHeader();
        if (TextVarPlayerListHeader == null) {
            return -1;
        }
        Matcher matcher = SERVER_NUMBER_PATTERN.matcher(Formatting.strip(TextVarPlayerListHeader.getString()));
        if (matcher.find()) {
            return parseInt(matcher.group(1), -1);
        }
        return -1;
    }

    public String i() {
        Text TextVarPlayerListHeader = playerListHeader();
        if (TextVarPlayerListHeader == null) {
            return null;
        }
        Matcher matcher = HEADER_SERVER_PATTERN.matcher(Formatting.strip(TextVarPlayerListHeader.getString()));
        if (matcher.find()) {
            return matcher.group(2);
        }
        return null;
    }

    public boolean j() {
        return isConnectedToMarkedServer();
    }

    public boolean isConnectedToMarkedServer() {
        if (c.player == null) {
            return false;
        }
        String string = c.player.getDisplayName().getString();
        return !string.equals(Formatting.strip(string));
    }

    public boolean k() {
        return canRenderServerBoundMarkers();
    }

    public boolean canRenderServerBoundMarkers() {
        return (c.world == null || !"minecraft:overworld".equals(c.world.getRegistryKey().getValue().toString()) || c.isInSingleplayer()) ? false : true;
    }

    public MapMarkerStyle a(String str) {
        return findStyle(str);
    }

    public MapMarkerStyle findStyle(String str) {
        if (str == null) {
            return defaultStyle();
        }
        String lowerCase = str.toLowerCase(Locale.ROOT);
        if (this.activeProvider != null) {
            Map<String, MapMarkerStyle> mapStyles = this.activeProvider.styles();
            if (mapStyles.containsKey(lowerCase)) {
                return mapStyles.get(lowerCase);
            }
        }
        Iterator<MarkerProvider> it = this.providers.iterator();
        while (it.hasNext()) {
            Map<String, MapMarkerStyle> mapStyles2 = it.next().styles();
            if (mapStyles2.containsKey(lowerCase)) {
                return mapStyles2.get(lowerCase);
            }
        }
        return defaultStyle();
    }

    private MapMarkerStyle defaultStyle() {
        return new MapMarkerStyle(new Color(255, 165, 0));
    }

    private String capitalizeEventName(String str) {
        return (str == null || str.isEmpty()) ? str : str.substring(0, 1).toUpperCase(Locale.ROOT) + str.substring(1);
    }

    private Text playerListHeader() {
        if (c.inGameHud == null || c.inGameHud.getPlayerListHud() == null) {
            return null;
        }
        return ((ru.drunvisual.mixin.accessor.PlayerListHudAccessor) (Object) c.inGameHud.getPlayerListHud()).getHeader();
    }

    private String currentServerAddress() {
        if (c.getCurrentServerEntry() != null) {
            return c.getCurrentServerEntry().address;
        }
        return null;
    }

    private static int parseInt(String str, int i) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return i;
        }
    }

    @Generated
    public List<MarkerProvider> l() {
        return this.providers;
    }

    @Generated
    public MarkerProvider m() {
        return this.activeProvider;
    }

    @Generated
    public boolean n() {
        return this.needsProviderRefresh;
    }

    @Generated
    public String o() {
        return this.pendingMarkerName;
    }

    @Generated
    public int p() {
        return this.pendingServerId;
    }
}
