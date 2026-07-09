package drunvisual.gui.modules;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.core.Bool;
import drunvisual.gui.core.GuiEntry;
import drunvisual.gui.core.GuiInput;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.render.icons.IconGlyphs;
import drunvisual.theme.Theme;
import drunvisual.util.ColorUtils;

public class ModuleRowRenderer {
private static final float c = 9.5f;
private static final float d = 11.0f;
private static final float e = 18.0f;
private static final float f = 10.0f;
private static final float g = 5.0f;
private static final float h = 12.0f;
private static final float i = 4.0f;
private static final Color j = Theme.C;
private static final Color k = Theme.D;
private static final Color l = Theme.N;
private final Map<GuiEntry, RowAnimationState> m = new HashMap();
public static int a;
public static boolean b;

private static class RowAnimationState {
final AnimationState a = new AnimationState();
final AnimationState b = new AnimationState();
final AnimationState c = new AnimationState();
final AnimationState d = new AnimationState();
boolean e = false;
boolean f = false;
boolean g = false;
public static int h;
public static boolean i;

private RowAnimationState() {
}

public static String a(String str, String str2, int i2, int i3, int i4, int i5) {
return null;
}
}

public static class RowRenderResult {
public final boolean a;
public final boolean b;
public final float c;
public final float d;
public static int e;
public static boolean f;

public RowRenderResult(boolean z, boolean z2, float f2, float f3) {
this.a = z;
this.b = z2;
this.c = f2;
this.d = f3;
}

public static String a(String str, String str2, int i, int i2, int i3, int i4) {
return null;
}
}

public void a(GuiEntry guiEntry) {
if (this.m.containsKey(guiEntry)) {
return;
}
RowAnimationState rowAnimationState = new RowAnimationState();
rowAnimationState.a.d(!guiEntry.b() ? 0.0d : 1.0d);
rowAnimationState.g = guiEntry.b();
this.m.put(guiEntry, rowAnimationState);
}

public RowRenderResult a(MatrixStack MatrixStackVar, Renderer2D renderer2D, GuiEntry guiEntry, float f2, float f3, float f4, float f5, int i2, int i3, boolean z, boolean z2, boolean z3, boolean z4) {
int i4;
a(guiEntry);
RowAnimationState rowAnimationState = this.m.get(guiEntry);
FontRenderer fontRenderer = FontManager.b[14];
float f6 = ((f2 + f4) - d) - 18.0f;
float f7 = (f3 + (f5 / 2.0f)) - g;
float f8 = (f6 - i) - h;
float f9 = ((f3 + (f5 / 2.0f)) - 6.0f) - 0.5f;
boolean z5 = guiEntry.g() || guiEntry.h();
int i5 = (z || z2 || !GuiInput.a(f2, f3, f4, f5, (double) i2, (double) i3)) ? 0 : 1;
int i6 = (!z5 || z || !GuiInput.a(f8, f9, h, h, (double) i2, (double) i3) || i5 == 0) ? 0 : 1;
if (i5 == 0 || i6 != 0) {
i4 = 0;
} else {
i4 = 1;
}
int i7 = i4;
int i8 = (z5 && i5 != 0 && guiEntry.d()) ? 1 : 0;
boolean z6 = z4 && !z3;
boolean z7 = !z4 && z3;
if (z5) {
if (z4) {
if (z2) {
if (z6) {
rowAnimationState.b.a(0.0d, 0.15d, Easing.h);
rowAnimationState.d.a(1.0d, 0.15d, Easing.h);
}
rowAnimationState.c.d(1.0d);
} else if (z6 && rowAnimationState.e) {
rowAnimationState.b.a(0.0d, 0.15d, Easing.h);
rowAnimationState.c.a(0.0d, 0.2d, Easing.h);
rowAnimationState.e = false;
}
} else if (z7) {
if (i5 != 0) {
rowAnimationState.b.a(1.0d, 0.15d, Easing.h);
rowAnimationState.c.a(1.0d, 0.2d, Easing.h);
rowAnimationState.e = true;
}
if (i6 == 0) {
rowAnimationState.d.a(0.0d, 0.15d, Easing.h);
rowAnimationState.f = false;
}
} else if (Bool.from(i8) != rowAnimationState.e) {
rowAnimationState.b.a(i5 == 0 ? 0.0d : 1.0d, 0.15d, Easing.h);
rowAnimationState.c.a(i5 == 0 ? 0.0d : 1.0d, 0.2d, Easing.h);
rowAnimationState.e = Bool.from(i8);
}
} else if (Bool.from(i5) == rowAnimationState.e) {
} else {
rowAnimationState.b.a(i5 == 0 ? 0.0d : 1.0d, 0.15d, Easing.h);
rowAnimationState.e = Bool.from(i5);
}
if (z5 && !z2 && Bool.from(i6) != rowAnimationState.f) {
rowAnimationState.d.a(i6 == 0 ? 0.0d : 1.0d, 0.15d, Easing.h);
rowAnimationState.f = Bool.from(i6);
}
boolean zB = guiEntry.b();
if (zB != rowAnimationState.g) {
rowAnimationState.a.a(!zB ? 0.0d : 1.0d, 0.2d, Easing.h);
rowAnimationState.g = zB;
}
rowAnimationState.b.a();
rowAnimationState.a.a();
rowAnimationState.c.a();
rowAnimationState.d.a();
float fJ = (float) rowAnimationState.b.j();
float fJ2 = (float) rowAnimationState.a.j();
float fJ3 = (float) rowAnimationState.c.j();
float fJ4 = (float) rowAnimationState.d.j();
a(MatrixStackVar, renderer2D, f2, f3, f4, f5, fJ);
fontRenderer.a(guiEntry.a(), (int) (f2 + d), (int) ((f3 + (f5 / 2.0f)) - (fontRenderer.b(guiEntry.a()) / i)), ColorUtils.a(Theme.b, Theme.aa, fJ2), MatrixStackVar);
if (z5 && fJ3 > 0.01f) {
a(MatrixStackVar, renderer2D, f8, f9, fJ3, fJ4, guiEntry);
}
a(MatrixStackVar, renderer2D, f6, f7, fJ2);
return new RowRenderResult(Bool.from(i7), Bool.from(i6), f8, f9);
}

private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5, float f6) {
Color colorB = Theme.b(Theme.q, (int) (f6 * f));
Color colorB2 = Theme.b(Theme.r, (int) (f6 * f));
renderer2D.a(f2 - (1.0f / 2.0f), f3 - (1.0f / 2.0f), f4 + 1.0f, f5 + 1.0f, 9.5f, colorB, colorB, colorB2, colorB2, MatrixStackVar);
int i2 = (int) (f6 * g);
Color colorB3 = Theme.b(Theme.e, i2);
Color colorB4 = Theme.b(Theme.e, i2);
renderer2D.a(f2, f3, f4, f5, 9.5f, colorB3, colorB3, colorB4, colorB4, MatrixStackVar);
}

private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5, GuiEntry guiEntry) {
FontManager.e[24].a(IconGlyphs.a, f2, f3, Theme.a(ColorUtils.a(Theme.b, l, f5), (int) (255.0f * f4)), MatrixStackVar);
}

private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4) {
float f5 = f2 + 0.5f + (((18.0f - 9.0f) - 1.0f) * f4);
float f6 = f3 + 0.5f;
if (f4 > 0.01f) {
renderer2D.a(f2 + 1.0f, f3 - 3.0f, 8.0f, Theme.a(Theme.E, (int) (255.0f * f4 * 0.5f)), MatrixStackVar);
}
Color colorA = ColorUtils.a(Theme.q, j, f4);
Color colorA2 = ColorUtils.a(Theme.r, k, f4);
renderer2D.a(f2 - 0.5f, f3 - 0.5f, 19.0f, d, g, colorA, colorA, colorA2, colorA2, MatrixStackVar);
Color colorA3 = ColorUtils.a(Theme.f, Theme.y, f4);
Color colorA4 = ColorUtils.a(Theme.H, Theme.z, f4);
renderer2D.a(f2, f3, 18.0f, f, g, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
renderer2D.a(f5, f6, 9.0f, 9.0f, 9.0f, ColorUtils.a(Theme.b, Theme.a, f4), MatrixStackVar);
}

public static float a() {
return 18.0f;
}

public static float b() {
return d;
}

public static float c() {
return h;
}

public static float d() {
return i;
}

public static String a(String str, String str2, int i2, int i3, int i4, int i5) {
return null;
}
}
