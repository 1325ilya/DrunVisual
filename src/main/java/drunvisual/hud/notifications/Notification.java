package drunvisual.hud.notifications;

import java.awt.Color;
import drunvisual.render.icons.IconGlyphs;
import drunvisual.render.icons.IconTextureRegistry;

public class Notification {
    private String title;
    private int x;
    private int y;
    private int z;
    private Color color;
    private Icon icon;
    private boolean temporary = false;
    private long createdAt = System.currentTimeMillis();
    private int priority = -1;
    private String source = null;
    private long expiresAt = 0;
    private boolean visible = true;

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
        private final String icon;

        Icon(String str, String str2) {
            this.id = str;
            this.icon = str2;
        }

        public String id() {
            return this.id;
        }

        public String glyph() {
            return this.icon;
        }

        public String a() {
            return this.id;
        }

        public String b() {
            return this.icon;
        }
    }

    public Notification(String str, int i, int i2, int i3, Color color, Icon icon) {
        this.title = str;
        this.x = i;
        this.y = i2;
        this.z = i3;
        this.color = color;
        this.icon = icon;
    }

    public static Notification a(String str, int i, int i2, int i3, Color color, Icon icon, int i4, String str2, long j, boolean z) {
        Notification notification = new Notification(str, i, i2, i3, color, icon != null ? icon : Icon.EVENT);
        notification.temporary = true;
        notification.priority = i4;
        notification.source = str2;
        notification.expiresAt = System.currentTimeMillis() + j;
        notification.visible = z;
        return notification;
    }

    public String title() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public int x() {
        return this.x;
    }

    public void setX(int i) {
        this.x = i;
    }

    public int y() {
        return this.y;
    }

    public void setY(int i) {
        this.y = i;
    }

    public int z() {
        return this.z;
    }

    public void setZ(int i) {
        this.z = i;
    }

    public Color color() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Icon icon() {
        return this.icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String coordinates() {
        return this.x + ", " + this.y + ", " + this.z;
    }

    public boolean isDeathIcon() {
        return this.icon == Icon.DEATH;
    }

    public boolean isFastIcon() {
        return this.icon == Icon.FAST;
    }

    public boolean isTemporary() {
        return this.temporary;
    }

    public long createdAt() {
        return this.createdAt;
    }

    public int priority() {
        return this.priority;
    }

    public void setPriority(int i) {
        this.priority = i;
    }

    public String source() {
        return this.source;
    }

    public void setSource(String str) {
        this.source = str;
    }

    public long expiresAt() {
        return this.expiresAt;
    }

    public void setExpiresAt(long j) {
        this.expiresAt = j;
    }

    public boolean isExpired() {
        return this.temporary && this.expiresAt > 0 && System.currentTimeMillis() > this.expiresAt;
    }

    public long remainingLifetimeMs() {
        if (!this.temporary || this.expiresAt <= 0) {
            return -1L;
        }
        return Math.max(0L, this.expiresAt - System.currentTimeMillis());
    }

    public boolean isEventIcon() {
        return this.icon == Icon.EVENT;
    }

    public boolean visible() {
        return this.visible;
    }

    public void setVisible(boolean z) {
        this.visible = z;
    }

    public String a() {
        return this.title;
    }

    public void a(String str) {
        this.title = str;
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
        this.icon = icon;
    }

    public String g() {
        return coordinates();
    }

    public boolean h() {
        return isDeathIcon();
    }

    public boolean i() {
        return isFastIcon();
    }

    public boolean j() {
        return this.temporary;
    }

    public long k() {
        return this.createdAt;
    }

    public int l() {
        return this.priority;
    }

    public void d(int i) {
        this.priority = i;
    }

    public String m() {
        return this.source;
    }

    public void b(String str) {
        this.source = str;
    }

    public long n() {
        return this.expiresAt;
    }

    public void a(long j) {
        this.expiresAt = j;
    }

    public boolean o() {
        return isExpired();
    }

    public long p() {
        return remainingLifetimeMs();
    }

    public boolean q() {
        return isEventIcon();
    }

    public boolean r() {
        return this.visible;
    }

    public void a(boolean z) {
        this.visible = z;
    }
}
