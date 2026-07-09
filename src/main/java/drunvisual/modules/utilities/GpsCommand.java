package drunvisual.modules.utilities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.text.Text;
import drunvisual.markers.MapMarker;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.events.ChatSendEvent;
import drunvisual.events.EventBusService;
import drunvisual.markers.MarkerManager;

@ModuleInfo(a = "GPS Command", b = "Local waypoints via .gps add / list / del", c = ModuleCategory.UTILITIES)
public class GpsCommand extends ClientModule {
    private static final Color GPS_COLOR = new Color(80, 220, 100);
    public static int a;
    public static boolean b;

    public GpsCommand() {
        EventBusService.EVENT_BUS.subscribe(this);
        GpsConfigManager.get().init();
    }

    @EventHandler
    public void onChatSend(ChatSendEvent chatSendEvent) {
        String[] strArrSplit;
        String strTrim = chatSendEvent.getMessage().trim();
        if (strTrim.startsWith(".gps")) {
            chatSendEvent.a();
            strArrSplit = strTrim.split("\\s+", 3);
            switch (strArrSplit.length >= 2 ? strArrSplit[1].toLowerCase(Locale.ROOT) : "") {
                case "add":
                    if (c.player != null) {
                        int iFloor = (int) Math.floor(c.player.getX());
                        int iFloor2 = (int) Math.floor(c.player.getY());
                        int iFloor3 = (int) Math.floor(c.player.getZ());
                        String strTrim2 = (strArrSplit.length < 3 || strArrSplit[2].trim().isEmpty()) ? "GPS " + iFloor + " " + iFloor3 : strArrSplit[2].trim();
                        MapMarker mapMarkerFindByName = findByName(strTrim2);
                        if (mapMarkerFindByName != null) {
                            mapMarkerFindByName.a(iFloor);
                            mapMarkerFindByName.b(iFloor2);
                            mapMarkerFindByName.c(iFloor3);
                            feedback("§aОбновлена§r метка §e" + strTrim2 + "§r → " + iFloor + ", " + iFloor2 + ", " + iFloor3);
                        } else {
                            MarkerManager.a(new MapMarker(strTrim2, iFloor, iFloor2, iFloor3, GPS_COLOR, MapMarker.Icon.HOME));
                            feedback("§aДобавлена§r метка §e" + strTrim2 + "§r (" + iFloor + ", " + iFloor2 + ", " + iFloor3 + ")");
                        }
                        GpsConfigManager.get().save();
                        break;
                    } else {
                        feedback("§cНет игрока.");
                        break;
                    }
                case "list":
                    List<MapMarker> listLocalMarkers = localMarkers();
                    if (!listLocalMarkers.isEmpty()) {
                        feedback("§aЛокальные метки (" + listLocalMarkers.size() + "):");
                        for (MapMarker mapMarker : listLocalMarkers) {
                            feedback("  §e" + mapMarker.a() + "§r — " + mapMarker.b() + ", " + mapMarker.c() + ", " + mapMarker.d());
                        }
                        break;
                    } else {
                        feedback("§7Нет локальных меток.");
                        break;
                    }
                case "del":
                case "delete":
                case "remove":
                    if (strArrSplit.length >= 3 && !strArrSplit[2].trim().isEmpty()) {
                        String strTrim3 = strArrSplit[2].trim();
                        MapMarker mapMarkerFindByName2 = findByName(strTrim3);
                        if (mapMarkerFindByName2 == null) {
                            feedback("§cМетка §e" + strTrim3 + "§c не найдена.");
                        } else {
                            MarkerManager.b(mapMarkerFindByName2);
                            feedback("§cУдалена§r метка §e" + strTrim3);
                            GpsConfigManager.get().flushNow();
                        }
                        break;
                    } else {
                        feedback("§cИспользование: .gps del <имя>");
                        break;
                    }
                default:
                    feedback("§7Использование:");
                    feedback("  §e.gps add [имя]§r — добавить метку на текущей позиции");
                    feedback("  §e.gps list§r — показать все метки");
                    feedback("  §e.gps del <имя>§r — удалить метку");
                    break;
            }
        }
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    private void feedback(String str) {
        if (c.player != null) {
            c.player.sendMessage(Text.literal(str), false);
        }
    }

    private List<MapMarker> localMarkers() {
        ArrayList arrayList = new ArrayList();
        for (MapMarker mapMarker : MarkerManager.a()) {
            if (!mapMarker.j()) {
                arrayList.add(mapMarker);
            }
        }
        return arrayList;
    }

    private MapMarker findByName(String str) {
        for (MapMarker mapMarker : localMarkers()) {
            if (mapMarker.a().equalsIgnoreCase(str)) {
                return mapMarker;
            }
        }
        return null;
    }
}
