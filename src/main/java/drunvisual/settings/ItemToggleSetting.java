package drunvisual.settings;

import java.awt.Color;
import net.minecraft.item.Item;

public class ItemToggleSetting extends Setting<Boolean> {
    private final Item item;
    private Color color;

    public ItemToggleSetting(String str, String str2, Item ItemVar, boolean z, Color color) {
        super(str, str2, Boolean.valueOf(z));
        this.item = ItemVar;
        this.color = color;
    }

    public ItemToggleSetting(String str, Item ItemVar, boolean z, Color color) {
        this(str, "", ItemVar, z, color);
    }

    public ItemToggleSetting(String str, Item ItemVar, Color color) {
        this(str, "", ItemVar, false, color);
    }

    public boolean get() {
        return k().booleanValue();
    }

    public void set(boolean z) {
        super.a(Boolean.valueOf(z));
    }

    public void toggle() {
        set(!get());
    }

    public Item item() {
        return this.item;
    }

    public Color color() {
        return this.color;
    }

    public int rgb() {
        return this.color.getRGB();
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean a() {
        return get();
    }

    public void a(boolean z) {
        set(z);
    }

    public void b() {
        toggle();
    }

    public Item c() {
        return item();
    }

    public Color d() {
        return color();
    }

    public int e() {
        return rgb();
    }

    public void a(Color color) {
        setColor(color);
    }
}
