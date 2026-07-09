package drunvisual.markers;

import java.awt.Color;
import drunvisual.render.icons.IconGlyphs;
import drunvisual.render.icons.IconTextureRegistry;

public class MapMarker {
    private String name;
    private int x;
    private int y;
    private int z;
    private Color color;
    private Icon icon;
    private boolean serverBound;
    private long createdAt;
    private int serverId;
    private String serverAddress;
    private long expiresAt;
    private boolean showTimer;

    public enum Icon {
        HOME(IconTextureRegistry.HOME, IconGlyphs.p),
        REPAIR(IconTextureRegistry.REPAIR, IconGlyphs.F),
        FAST(IconTextureRegistry.FAST, IconGlyphs.l),
        SHIELD(IconTextureRegistry.SHIELD, IconGlyphs.J),
        DEATH(IconTextureRegistry.DEATH, IconGlyphs.h),
        DIAMOND(IconTextureRegistry.DIAMOND, IconGlyphs.i),
        LOCKED(IconTextureRegistry.LOCKED, IconGlyphs.u),
        MOUNTAIN(IconTextureRegistry.MOUNTAIN, IconGlyphs.x),
        CALENDAR(IconTextureRegistry.CALENDAR, IconGlyphs.k),
        EVENT(IconTextureRegistry.CALENDAR, IconGlyphs.k);

        private final String id;
        private final String glyph;

        Icon(String str, String str2) {
            this.id = str;
            this.glyph = str2;
        }

        public String a() {
            return this.id;
        }

        public String b() {
            return this.glyph;
        }
    }

    public MapMarker(String str, int i, int i2, int i3, Color color, Icon icon) {
        this.name = str;
        this.x = i;
        this.y = i2;
        this.z = i3;
        this.color = color;
        this.icon = icon != null ? icon : Icon.EVENT;
        this.createdAt = System.currentTimeMillis();
    }

    public static MapMarker a(String str, int i, int i2, int i3, Color color, Icon icon, int i4, String str2, long j, boolean z) {
        MapMarker mapMarker = new MapMarker(str, i, i2, i3, color, icon);
        mapMarker.serverBound = true;
        mapMarker.serverId = i4;
        mapMarker.serverAddress = str2;
        mapMarker.expiresAt = System.currentTimeMillis() + j;
        mapMarker.showTimer = z;
        return mapMarker;
    }

    public String a() {
        return this.name;
    }

    public void a(String str) {
        this.name = str;
    }

    public int b() {
        return this.x;
    }

    public void a(int i) {
        this.x = i;
    }

    public int c() {
        return this.y;
    }

    public void b(int i) {
        this.y = i;
    }

    public int d() {
        return this.z;
    }

    public void c(int i) {
        this.z = i;
    }

    public Color e() {
        return this.color;
    }

    public void a(Color color) {
        this.color = color;
    }

    public Icon f() {
        return this.icon;
    }

    public void a(Icon icon) {
        this.icon = icon != null ? icon : Icon.EVENT;
    }

    public String g() {
        return this.x + ", " + this.y + ", " + this.z;
    }

    public boolean h() {
        return this.icon == Icon.DEATH;
    }

    public boolean i() {
        return this.icon == Icon.FAST;
    }

    public boolean j() {
        return this.serverBound;
    }

    public long k() {
        return this.createdAt;
    }

    public int l() {
        return this.serverId;
    }

    public void d(int i) {
        this.serverId = i;
    }

    public String m() {
        return this.serverAddress;
    }

    public void b(String str) {
        this.serverAddress = str;
    }

    public long n() {
        return this.expiresAt;
    }

    public void a(long j) {
        this.expiresAt = j;
    }

    public boolean o() {
        return this.serverBound && this.expiresAt > 0 && System.currentTimeMillis() > this.expiresAt;
    }

    public long p() {
        if (!this.serverBound || this.expiresAt <= 0) {
            return -1L;
        }
        return Math.max(0L, this.expiresAt - System.currentTimeMillis());
    }

    public boolean q() {
        return this.icon == Icon.EVENT;
    }

    public boolean r() {
        return this.showTimer;
    }

    public void a(boolean z) {
        this.showTimer = z;
    }
}
