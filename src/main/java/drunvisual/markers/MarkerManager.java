package drunvisual.markers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MarkerManager {
    private static final List<MapMarker> MARKERS = Collections.synchronizedList(new ArrayList());

    private MarkerManager() {
    }

    public static void a(MapMarker mapMarker) {
        if (mapMarker != null) {
            MARKERS.add(mapMarker);
        }
    }

    public static void b(MapMarker mapMarker) {
        MARKERS.remove(mapMarker);
    }

    public static List<MapMarker> a() {
        ArrayList arrayList;
        synchronized (MARKERS) {
            arrayList = new ArrayList(MARKERS);
        }
        return arrayList;
    }

    public static void b() {
        MARKERS.clear();
    }

    public static void c() {
        MARKERS.clear();
    }

    public static boolean d() {
        return MARKERS.isEmpty();
    }

    public static void e() {
        MARKERS.removeIf((v0) -> {
            return v0.o();
        });
    }

    public static MapMarker a(int i, int i2, int i3) {
        synchronized (MARKERS) {
            for (MapMarker mapMarker : MARKERS) {
                if (mapMarker.b() == i && mapMarker.c() == i2 && mapMarker.d() == i3) {
                    return mapMarker;
                }
            }
            return null;
        }
    }

    public static List<MapMarker> f() {
        return a();
    }

    public static List<MapMarker> g() {
        return a();
    }
}
