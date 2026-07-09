package drunvisual.gui.core;

import drunvisual.render.icons.IconGlyphs;
public enum ClickGuiTabType {
    MODULES("modules", IconGlyphs.w),
    MARKERS("markers", IconGlyphs.v),
    FRIENDS("friends", IconGlyphs.n),
    EVENTS("events", IconGlyphs.k),
    CONFIGS("configs", IconGlyphs.f);

    private final String id;
    private final String icon;

    ClickGuiTabType(String str, String str2) {
        this.id = str;
        this.icon = str2;
    }

    public String a() {
        return this.id;
    }

    public String b() {
        return this.icon;
    }
}
