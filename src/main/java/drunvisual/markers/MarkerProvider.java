package drunvisual.markers;

import java.awt.Color;
import java.util.Map;
import java.util.Set;

public interface MarkerProvider {
    boolean a(String str);

    boolean a(String str, String str2, MapMarkerModule mapMarkerModule);

    Set<String> a();

    Map<String, MapMarkerStyle> b();

    String c();

    default boolean supportsServer(String str) {
        return a(str);
    }

    default boolean handleMessage(String str, String str2, MapMarkerModule mapMarkerModule) {
        return a(str, str2, mapMarkerModule);
    }

    default Set<String> eventNames() {
        return a();
    }

    default Map<String, MapMarkerStyle> styles() {
        return b();
    }

    default String command() {
        return c();
    }

    default MapMarkerStyle d() {
        return new MapMarkerStyle(new Color(255, 165, 0));
    }

    default void a(MapMarkerModule mapMarkerModule) {
    }

    default void tick(MapMarkerModule mapMarkerModule) {
        a(mapMarkerModule);
    }
}
