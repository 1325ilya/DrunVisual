package drunvisual.settings;

import java.awt.Color;
import java.util.function.Supplier;

public class ColorSetting extends Setting<Color> {
    private float hue;
    private float saturation;
    private float brightness;

    public ColorSetting(String str, String str2, Color color) {
        super(str, str2, color);
        updateHsb(color);
    }

    public ColorSetting(String str, Color color) {
        this(str, "", color);
    }

    public ColorSetting(String str, int i, int i2, int i3) {
        this(str, "", new Color(i, i2, i3));
    }

    public Color getColor() {
        return Color.getHSBColor(this.hue, this.saturation, this.brightness);
    }

    public void setColor(Color color) {
        updateHsb(color);
        super.a(color);
    }

    public int rgb() {
        return getColor().getRGB();
    }

    public float hue() {
        return this.hue;
    }

    public void setHue(float f) {
        setHsb(f, this.saturation, this.brightness);
    }

    public float saturation() {
        return this.saturation;
    }

    public void setSaturation(float f) {
        setHsb(this.hue, f, this.brightness);
    }

    public float brightness() {
        return this.brightness;
    }

    public void setBrightness(float f) {
        setHsb(this.hue, this.saturation, f);
    }

    public void setHsb(float f, float f2, float f3) {
        this.hue = f;
        this.saturation = f2;
        this.brightness = f3;
        super.a(getColor());
    }

    @Override
    public Setting<Color> visibleWhen(Supplier<Boolean> supplier) {
        super.visibleWhen(supplier);
        return this;
    }

    private void updateHsb(Color color) {
        float[] fArrRGBtoHSB = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), (float[]) null);
        this.hue = fArrRGBtoHSB[0];
        this.saturation = fArrRGBtoHSB[1];
        this.brightness = fArrRGBtoHSB[2];
    }

    public Color a() {
        return getColor();
    }

    @Override
    public void a(Color color) {
        setColor(color);
    }

    public int b() {
        return rgb();
    }

    public float c() {
        return hue();
    }

    public void a(float f) {
        setHue(f);
    }

    public float d() {
        return saturation();
    }

    public void b(float f) {
        setSaturation(f);
    }

    public float e() {
        return brightness();
    }

    public void c(float f) {
        setBrightness(f);
    }

    public void a(float f, float f2, float f3) {
        setHsb(f, f2, f3);
    }

    public ColorSetting a(Supplier<Boolean> supplier) {
        return (ColorSetting) visibleWhen(supplier);
    }

    public /* bridge */ /* synthetic */ Setting<Color> visibleWhen2(Supplier supplier) {
        return visibleWhen((Supplier<Boolean>) supplier);
    }
}
