package drunvisual.markers.providers;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import drunvisual.markers.MapMarkerModule;
import drunvisual.markers.MapMarkerStyle;

public class EventsCommandMarkerProvider extends AbstractEventMarkerProvider {
    private static final Map<String, MapMarkerStyle> EVENT_STYLES = new LinkedHashMap();

    public EventsCommandMarkerProvider() {
        super("/events", EVENT_STYLES);
    }

    @Override
    public /* bridge */ /* synthetic */ String c() {
        return super.c();
    }

    @Override
    public /* bridge */ /* synthetic */ Map b() {
        return super.b();
    }

    @Override
    public /* bridge */ /* synthetic */ Set a() {
        return super.a();
    }

    @Override
    public /* bridge */ /* synthetic */ boolean a(String str, String str2, MapMarkerModule mapMarkerModule) {
        return super.a(str, str2, mapMarkerModule);
    }

    @Override
    public /* bridge */ /* synthetic */ boolean a(String str) {
        return super.a(str);
    }

    static {
        EVENT_STYLES.put("пороховой карьер", style(220, 20, 60));
        EVENT_STYLES.put("павший самурай", style(105, 105, 105));
        EVENT_STYLES.put("дерево мудрости", style(34, 139, 34));
        EVENT_STYLES.put("экскалибур", style(255, 215, 0));
        EVENT_STYLES.put("пиратский корабль", style(139, 69, 19));
    }
}
