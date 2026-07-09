package drunvisual.util;

import java.awt.Color;
import lombok.Generated;

public final class ColorUtils {
    public static int a;

    public static Color a(Color color, Color color2, float f) {
        float red = color.getRed();
        int red2 = color2.getRed();
        int red3 = color.getRed();
        int i = (int) (red + (((2 * (red2 & (red3 ^ (-1)))) - (red2 ^ red3)) * f));
        int green = (int) (color.getGreen() + ((color2.getGreen() + (color.getGreen() ^ (-1)) + 1) * f));
        float blue = color.getBlue();
        int blue2 = color2.getBlue();
        int blue3 = color.getBlue();
        return new Color(Math.max(0, Math.min(255, i)), Math.max(0, Math.min(255, green)), Math.max(0, Math.min(255, (int) (blue + (((blue2 ^ blue3) - (2 * ((blue2 ^ (-1)) & blue3))) * f)))), Math.max(0, Math.min(255, (int) (color.getAlpha() + ((color2.getAlpha() - color.getAlpha()) * f)))));
    }

    public static int a(int i) {
        int i2 = i >> 24;
        return ((i2 ^ (-1)) | 255) - (i2 ^ (-1)) != 0 ? i : (i & 16777215) - 16777216;
    }

    public static int a(int i, int i2) {
        int i3 = ((i ^ (-1)) | 16777215) - (i ^ (-1));
        int iMax = Math.max(0, Math.min(255, i2)) << 24;
        return (i3 & (iMax ^ (-1))) + iMax;
    }

    public static Color a(Color color, int i) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(0, Math.min(255, i)));
    }

    public static int a(int i, int i2, int i3, int i4) {
        int i5 = (((i ^ (-1)) | 255) - (i ^ (-1))) << 16;
        int i6 = (((((i4 ^ (-1)) | 255) - (i4 ^ (-1))) << 24) & (i5 ^ (-1))) + i5;
        int i7 = (((i2 ^ (-1)) | 255) - (i2 ^ (-1))) << 8;
        int i8 = (i6 & (i7 ^ (-1))) + i7;
        int i9 = ((i3 ^ (-1)) | 255) - (i3 ^ (-1));
        return (i8 & (i9 ^ (-1))) + i9;
    }

    public static int a(int i, int i2, int i3) {
        int i4 = (((i ^ (-1)) | 255) - (i ^ (-1))) << 16;
        int i5 = ((-16777216) & (i4 ^ (-1))) + i4;
        int i6 = (((i2 ^ (-1)) | 255) - (i2 ^ (-1))) << 8;
        int i7 = (i5 & (i6 ^ (-1))) + i6;
        int i8 = ((i3 ^ (-1)) | 255) - (i3 ^ (-1));
        return (i7 & (i8 ^ (-1))) + i8;
    }

    @Generated
    private ColorUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
