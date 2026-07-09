package drunvisual.gui.events;

public class EventInfo {
    private final String c;
    private final int d;
    private final EventState e;
    private final EventKind f;
    private final Rarity g;
    private final String h;
    private int i;
    public static int a;
    public static boolean b;

    public enum EventKind {
        METEOR("Метеор"),
        BEACON("Маяк"),
        MINE("Шахта");

        private final String label;
        public static int d;
        public static boolean e;

        EventKind(String str) {
            this.label = str;
        }

        public String a() {
            return this.label;
        }

        public static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return str;
        }
    }

    public enum EventState {
        CURRENT,
        UPCOMING,
        MINE;

        public static int d;
        public static boolean e;

        public static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return str;
        }
    }

    public enum Rarity {
        LEGENDARY("Легендарная"),
        ELITE("Элитный"),
        RICH("Богатый"),
        MYTHIC("Мифическая");

        private final String label;
        public static int e;
        public static boolean f;

        Rarity(String str) {
            this.label = str;
        }

        public String a() {
            return this.label;
        }

        public static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return str;
        }
    }

    public EventInfo(String str, int i, EventState eventState, EventKind eventKind, Rarity rarity, String str2, int i2) {
        this.c = str;
        this.d = i;
        this.e = eventState;
        this.f = eventKind;
        this.g = rarity;
        this.h = str2;
        this.i = i2;
    }

    public static EventInfo a(String str, int i, int i2) {
        return new EventInfo(str, i, EventState.UPCOMING, null, null, null, i2);
    }

    public static EventInfo a(String str, int i, EventKind eventKind, Rarity rarity, String str2, int i2) {
        return new EventInfo(str, i, EventState.CURRENT, eventKind, rarity, str2, i2);
    }

    public static EventInfo a(String str, int i, Rarity rarity, String str2, int i2) {
        return new EventInfo(str, i, EventState.MINE, EventKind.MINE, rarity, str2, i2);
    }

    public String a() {
        return this.c;
    }

    public int b() {
        return this.d;
    }

    public EventState c() {
        return this.e;
    }

    public EventKind d() {
        return this.f;
    }

    public Rarity e() {
        return this.g;
    }

    public int f() {
        return this.i;
    }

    public void a(int i) {
        this.i = i;
    }

    public String g() {
        return String.format("%d:%02d", Integer.valueOf(this.i / 60), Integer.valueOf(this.i % 60));
    }

    public String h() {
        return this.c + "  /  " + this.d;
    }

    public String i() {
        return this.h;
    }

    public String j() {
        if (this.h != null && !this.h.isEmpty()) {
            return this.h;
        }
        if (this.f == null) {
            return this.c;
        }
        if (this.g == null) {
            return this.f.a();
        }
        if (this.e == EventState.MINE) {
            return this.g.a();
        }
        return this.f.a() + " • " + this.g.a();
    }

    public static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
