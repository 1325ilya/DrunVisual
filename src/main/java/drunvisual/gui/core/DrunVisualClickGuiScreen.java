package drunvisual.gui.core;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.gui.core.DockPanelController;
import drunvisual.client.MinecraftContext;
import ru.drunvisual.DrunVisual;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.gui.config.ConfigsTab;
import drunvisual.gui.events.EventsTab;
import drunvisual.gui.friends.FriendsTab;
import drunvisual.gui.markers.MarkersTab;
import drunvisual.gui.modules.ModuleSettingsOverlay;
import drunvisual.gui.modules.ModulesTab;
import drunvisual.gui.settings.TokenSettingWidget;
import drunvisual.hud.core.HudElementManager;
import drunvisual.render.CustomHandShaderCapture;
import drunvisual.render.Renderer2D;
import drunvisual.render.ScreenPoint;
import drunvisual.render.ScreenScale;
import drunvisual.render.icons.IconTextureRegistry;

public class DrunVisualClickGuiScreen extends Screen {
private static final float c = 411.0f;
private static final float d = 243.5f;
private static final float e = 9.5f;
private static final float f = 26.0f;
private static final double g = 0.35d;
private final ClickGuiFrameOverlay h;
private final DrunVisualLogoOverlay i;
private final DockPanelController j;
private final ModuleSettingsOverlay k;
private final Map<ClickGuiTabType, ClickGuiTab> l;
private ClickGuiTabType m;
private ClickGuiTabType n;
private final AnimationState o;
private boolean p;
private int q;
private int r;
private int s;
public static int a;
public static boolean b;
public int clickBtn;

public DrunVisualClickGuiScreen() {
super(Text.literal("DrunVisual"));
String.valueOf(Text.literal("crypt"));
this.clickBtn = 0;
this.m = ClickGuiTabType.MODULES;
this.n = null;
this.o = new AnimationState();
this.p = false;
this.q = 1;
this.r = 0;
this.s = 0;
this.h = new ClickGuiFrameOverlay();
this.i = new DrunVisualLogoOverlay();
this.l = new HashMap();
this.l.put(ClickGuiTabType.MODULES, new ModulesTab());
this.l.put(ClickGuiTabType.MARKERS, new MarkersTab());
this.l.put(ClickGuiTabType.FRIENDS, new FriendsTab());
this.l.put(ClickGuiTabType.EVENTS, new EventsTab());
this.l.put(ClickGuiTabType.CONFIGS, new ConfigsTab());
this.j = new DockPanelController(this);
this.k = new ModuleSettingsOverlay(this);
this.o.d(1.0d);
}

public void a(ClickGuiTabType clickGuiTabType) {
if (this.m != clickGuiTabType) {
ClickGuiTab clickGuiTabA = a();
if (clickGuiTabA instanceof ModulesTab) {
((ModulesTab) clickGuiTabA).k();
}
if (clickGuiTabA instanceof MarkersTab) {
((MarkersTab) clickGuiTabA).e();
}
this.n = this.m;
this.m = clickGuiTabType;
this.q = a(this.n, clickGuiTabType);
this.p = true;
this.o.d(0.0d);
this.o.a(1.0d, g, Easing.n);
}
}

public void a(ClickGuiTabType clickGuiTabType, int i) {
if (this.m != clickGuiTabType) {
ClickGuiTab clickGuiTabA = a();
if (clickGuiTabA instanceof ModulesTab) {
((ModulesTab) clickGuiTabA).k();
}
if (clickGuiTabA instanceof MarkersTab) {
((MarkersTab) clickGuiTabA).e();
}
this.n = this.m;
this.m = clickGuiTabType;
this.q = i;
this.p = true;
this.o.d(0.0d);
this.o.a(1.0d, g, Easing.n);
}
}

private int a(ClickGuiTabType clickGuiTabType, ClickGuiTabType clickGuiTabType2) {
return clickGuiTabType2.ordinal() <= clickGuiTabType.ordinal() ? -1 : 1;
}

public ClickGuiTab a() {
return this.l.get(this.m);
}

public ClickGuiTab b() {
if (this.n != null) {
return this.l.get(this.n);
}
return null;
}

private boolean isInsideClickGui(int i, int i2, float f2, float f3) {
return GuiInput.a(f2, f3, c, d, i, i2);
}

private boolean isOverOpenSettings(int i, int i2) {
ClickGuiTab clickGuiTabA = a();
return (clickGuiTabA instanceof ModulesTab) && ((ModulesTab) clickGuiTabA).hasOpenSettingsAt(i, i2);
}

protected void init() {
super.init();
HudElementManager.a().a(true);
}

public void removed() {
o();
HudElementManager.a().a(false);
super.removed();
}

public void render(DrawContext DrawContextVar, int i, int i2, float f2) {
ClickGuiTab clickGuiTabB;
CustomHandShaderCapture.beginGuiRender();
MatrixStack MatrixStackVarGetMatrices = DrawContextVar.getMatrices();
GuiInput.h();
ScreenScale.a(2.0d);
ScreenPoint screenPointA = ScreenScale.a(i, i2);
int iA = screenPointA.a();
int iB = screenPointA.b();
this.r = iA;
this.s = iB;
GuiLayerRegistry.a().b();
GuiLayerRegistry.a().a(iA, iB);
float fGetWidth = (MinecraftContext.d.getWidth() / 4.0f) - 205.5f;
float fGetHeight = (MinecraftContext.d.getHeight() / 4.0f) - 121.75f;
Renderer2D render = DrunVisual.getInstance().getRender();
IconTextureRegistry.get("crypt");
this.i.a(MatrixStackVarGetMatrices, render, fGetWidth, fGetHeight, iA, iB);
this.o.a();
if (this.p && this.o.d()) {
this.p = false;
this.n = null;
}
float fJ = (float) this.o.j();
boolean z = this.j.a() != DockPanelController.Dock.BOTTOM;
float fMethod_44802 = (MinecraftContext.d.getWidth() / 4.0f) + 205.5f + 50.0f;
float fGetRequiredSpaceCount = (MinecraftContext.d.getHeight() / 4.0f) + 121.75f + 50.0f;
if (this.p && this.n != null && (clickGuiTabB = b()) != null) {
float f3 = 0.0f;
float f4 = 0.0f;
if (z) {
f4 = fJ * fGetRequiredSpaceCount * ((this.q - 1) ^ (-1));
} else {
f3 = fJ * fMethod_44802 * (-this.q);
}
a(MatrixStackVarGetMatrices, render, fGetWidth + f3, fGetHeight + f4, iA, iB, clickGuiTabB);
}
ClickGuiTab clickGuiTabA = a();
if (clickGuiTabA != null) {
if (this.p) {
float f5 = 0.0f;
float f6 = 0.0f;
if (z) {
f6 = (1.0f - fJ) * fGetRequiredSpaceCount * this.q;
} else {
f5 = (1.0f - fJ) * fMethod_44802 * this.q;
}
a(MatrixStackVarGetMatrices, render, fGetWidth + f5, fGetHeight + f6, iA, iB, clickGuiTabA);
} else {
a(MatrixStackVarGetMatrices, render, fGetWidth, fGetHeight, iA, iB, clickGuiTabA);
}
}
if (this.j.d()) {
this.k.a(MatrixStackVarGetMatrices, render, fGetWidth, fGetHeight, iA, iB);
if (clickGuiTabA instanceof ModulesTab) {
((ModulesTab) clickGuiTabA).a(MatrixStackVarGetMatrices, render, iA, iB);
}
GuiInteractionState.a().a(MatrixStackVarGetMatrices, render);
this.j.b(MatrixStackVarGetMatrices, render, fGetWidth, fGetHeight, iA, iB);
this.j.a(MatrixStackVarGetMatrices, render, fGetWidth, fGetHeight, iA, iB);
this.k.a(MatrixStackVarGetMatrices, render, iA, iB);
} else {
this.j.a(MatrixStackVarGetMatrices, render, fGetWidth, fGetHeight, iA, iB);
this.k.a(MatrixStackVarGetMatrices, render, fGetWidth, fGetHeight, iA, iB);
GuiInteractionState.a().a(MatrixStackVarGetMatrices, render);
if (clickGuiTabA instanceof ModulesTab) {
((ModulesTab) clickGuiTabA).a(MatrixStackVarGetMatrices, render, iA, iB);
}
if (clickGuiTabA instanceof MarkersTab) {
((MarkersTab) clickGuiTabA).a(MatrixStackVarGetMatrices, render, iA, iB);
}
if (clickGuiTabA instanceof ConfigsTab) {
((ConfigsTab) clickGuiTabA).a(MatrixStackVarGetMatrices, render);
}
this.k.a(MatrixStackVarGetMatrices, render, iA, iB);
}
GuiInput.i();
super.render(DrawContextVar, i, i2, f2);
ScreenScale.a();
CustomHandShaderCapture.endGuiRender();
}

private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, int i, int i2, ClickGuiTab clickGuiTab) {
this.h.a(MatrixStackVar, renderer2D, f2, f3, i, i2);
this.h.b(MatrixStackVar, renderer2D, f2, f3, i, i2);
if (clickGuiTab != null) {
clickGuiTab.a(MatrixStackVar, renderer2D, f2, f3, i, i2);
}
}

public boolean mouseClicked(double d2, double d3, int i) {
if (i == 0 || i == 1) {
return dispatchPointerClick(d2, d3, i);
}
return false;
}

public boolean dispatchPointerClick(double d2, double d3, int i) {
this.clickBtn = i;
GuiInput.a();
ScreenPoint screenPointA = ScreenScale.a(d2, d3);
int iA = screenPointA.a();
int iB = screenPointA.b();
float fGetWidth = (MinecraftContext.d.getWidth() / 4.0f) - 205.5f;
float fGetHeight = (MinecraftContext.d.getHeight() / 4.0f) - 121.75f;
boolean zIsOverOpenSettings = isOverOpenSettings(iA, iB);
boolean zIsInsideClickGui = isInsideClickGui(iA, iB, fGetWidth, fGetHeight);
GuiLayerRegistry.a().a(iA, iB);
this.j.a(fGetWidth, fGetHeight, iA, iB);
if (!zIsOverOpenSettings && !zIsInsideClickGui) {
HudElementManager.a().a(d2, d3, i);
return true;
}
ClickGuiTab clickGuiTabA = a();
if (clickGuiTabA == null) {
return true;
}
if (!(clickGuiTabA instanceof ModulesTab)) {
clickGuiTabA.a(fGetWidth, fGetHeight, iA, iB);
return true;
}
if (i == 1) {
clickGuiTabA.b(fGetWidth, fGetHeight, iA, iB);
return true;
}
if (zIsOverOpenSettings || iB >= fGetHeight + 48.0f) {
clickGuiTabA.b(fGetWidth, fGetHeight, iA, iB);
return true;
}
clickGuiTabA.a(fGetWidth, fGetHeight, iA, iB);
return true;
}

public boolean mouseReleased(double d2, double d3, int i) {
if (i != 0) {
return false;
}
if (HudElementManager.a().b(d2, d3, i)) {
return true;
}
ScreenPoint screenPointA = ScreenScale.a(d2, d3);
int iA = screenPointA.a();
int iB = screenPointA.b();
float fGetWidth = (MinecraftContext.d.getWidth() / 4.0f) - 205.5f;
float fGetHeight = (MinecraftContext.d.getHeight() / 4.0f) - 121.75f;
GuiLayerRegistry.a().a(iA, iB);
this.j.c(fGetWidth, fGetHeight, iA, iB);
ClickGuiTab clickGuiTabA = a();
if (clickGuiTabA == null) {
return true;
}
clickGuiTabA.c(fGetWidth, fGetHeight, iA, iB);
return true;
}

public boolean mouseDragged(double d2, double d3, int i, double d4, double d5) {
if (i != 0) {
return false;
}
HudElementManager.a().a(d2, d3, d4, d5);
if (HudElementManager.a().b(d2, d3)) {
return true;
}
ScreenPoint screenPointA = ScreenScale.a(d2, d3);
int iA = screenPointA.a();
int iB = screenPointA.b();
float fGetWidth = (MinecraftContext.d.getWidth() / 4.0f) - 205.5f;
float fGetHeight = (MinecraftContext.d.getHeight() / 4.0f) - 121.75f;
try {
this.j.a(fGetWidth, fGetHeight, iA, iB, d4, d5);
} catch (Throwable th) {
}
try {
ClickGuiTab clickGuiTabA = a();
if (clickGuiTabA != null) {
clickGuiTabA.a(fGetWidth, fGetHeight, iA, iB, d4, d5);
}
return true;
} catch (Throwable th2) {
return true;
}
}

public boolean mouseScrolled(double d2, double d3, double d4, double d5) {
ScreenPoint screenPointA = ScreenScale.a(d2, d3);
int iA = screenPointA.a();
int iB = screenPointA.b();
GuiLayerRegistry.a().a(iA, iB);
boolean z = false;
float fGetWidth = (MinecraftContext.d.getWidth() / 4.0f) - 205.5f;
float fGetHeight = (MinecraftContext.d.getHeight() / 4.0f) - 121.75f;
double d6 = d5 != 0.0d ? d5 : d4;
if (d6 == 0.0d) {
return false;
}
if (!isOverOpenSettings(iA, iB) && !isInsideClickGui(iA, iB, fGetWidth, fGetHeight) && HudElementManager.a().a(d2, d3, d6)) {
return true;
}
try {
ClickGuiTab clickGuiTabA = a();
if (clickGuiTabA instanceof ModulesTab) {
((ModulesTab) clickGuiTabA).a((float) d6, iA, iB);
z = true;
} else if (clickGuiTabA != null) {
clickGuiTabA.a((float) d6);
z = true;
}
} catch (Throwable th) {
}
try {
if (this.j != null) {
this.j.a(fGetWidth, fGetHeight, iA, iB, 0.0d, (-d6) * 20.0d);
z = true;
}
} catch (Throwable th2) {
}
return z;
}

public boolean keyPressed(int i, int i2, int i3) {
if (i == 256) {
close();
return true;
}
ClickGuiTab clickGuiTabA = a();
if (clickGuiTabA == null) {
return false;
}
try {
Object objInvoke = clickGuiTabA.getClass().getMethod("a", Integer.TYPE, Integer.TYPE, Integer.TYPE).invoke(clickGuiTabA, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
if (objInvoke instanceof Boolean) {
return ((Boolean) objInvoke).booleanValue();
}
return false;
} catch (Throwable th) {
return false;
}
}

public boolean shouldCloseOnEsc() {
return true;
}

public boolean charTyped(char c2, int i) {
ClickGuiTab clickGuiTabA = a();
if ((clickGuiTabA instanceof ModulesTab) && ((ModulesTab) clickGuiTabA).a(c2, i)) {
return true;
}
if ((clickGuiTabA instanceof MarkersTab) && ((MarkersTab) clickGuiTabA).a(c2, i)) {
return true;
}
if ((clickGuiTabA instanceof FriendsTab) && ((FriendsTab) clickGuiTabA).a(c2, i)) {
return true;
}
if ((clickGuiTabA instanceof EventsTab) && ((EventsTab) clickGuiTabA).a(c2, i)) {
return true;
}
return (clickGuiTabA instanceof ConfigsTab) && ((ConfigsTab) clickGuiTabA).a(c2, i);
}

private void m() {
ClickGuiTabType[] clickGuiTabTypeArrValues = ClickGuiTabType.values();
int iOrdinal = this.m.ordinal();
a(clickGuiTabTypeArrValues[(((iOrdinal & (-2)) + (1 & (iOrdinal ^ (-1)))) + (2 * (iOrdinal & 1))) % clickGuiTabTypeArrValues.length], 1);
}

private void n() {
ClickGuiTabType[] clickGuiTabTypeArrValues = ClickGuiTabType.values();
int iOrdinal = (this.m.ordinal() - 2) + 1;
int length = clickGuiTabTypeArrValues.length;
a(clickGuiTabTypeArrValues[((iOrdinal | length) + (iOrdinal & length)) % clickGuiTabTypeArrValues.length], -1);
}

public void c() {
ClickGuiTab clickGuiTabA = a();
if (clickGuiTabA instanceof ModulesTab) {
ModulesTab modulesTab = (ModulesTab) clickGuiTabA;
modulesTab.m();
modulesTab.n();
} else {
}
if (clickGuiTabA instanceof MarkersTab) {
((MarkersTab) clickGuiTabA).e();
((MarkersTab) clickGuiTabA).f();
}
if (this.j.d()) {
this.j.e();
}
GuiInteractionState.a().a(false);
GuiInteractionState.a().c(false);
GuiInput.a();
}

private void o() {
ClickGuiTab clickGuiTabA = a();
if (clickGuiTabA instanceof ModulesTab) {
((ModulesTab) clickGuiTabA).o();
}
if (clickGuiTabA instanceof MarkersTab) {
((MarkersTab) clickGuiTabA).e();
((MarkersTab) clickGuiTabA).f();
}
if (this.j.d()) {
this.j.e();
}
}

public void close() {
o();
GuiInteractionState.a().f();
TokenSettingWidget.o();
GuiInput.j();
GuiInput.a();
super.close();
}

public void renderBackground(DrawContext DrawContextVar, int i, int i2, float f2) {
MatrixStack MatrixStackVarGetMatrices = DrawContextVar.getMatrices();
Renderer2D render = DrunVisual.getInstance().getRender();
MinecraftClient MinecraftClientVarGetInstance = MinecraftClient.getInstance();
render.a(0.0f, 0.0f, MinecraftClientVarGetInstance.getWindow().getScaledWidth(), MinecraftClientVarGetInstance.getWindow().getScaledHeight(), new Color(0, 0, 0, 80), MatrixStackVarGetMatrices);
}

public boolean shouldPause() {
return false;
}

public static float d() {
return c;
}

public static float e() {
return d;
}

public static float f() {
return 9.5f;
}

public static float g() {
return f;
}

public FriendsTab h() {
ClickGuiTab clickGuiTab = this.l.get(ClickGuiTabType.FRIENDS);
if (clickGuiTab instanceof FriendsTab) {
return (FriendsTab) clickGuiTab;
}
return null;
}

public ConfigsTab i() {
ClickGuiTab clickGuiTab = this.l.get(ClickGuiTabType.CONFIGS);
if (clickGuiTab instanceof ConfigsTab) {
return (ConfigsTab) clickGuiTab;
}
return null;
}

public DockPanelController j() {
return this.j;
}

@Generated
public ClickGuiTabType k() {
return this.m;
}

@Generated
public boolean l() {
return this.p;
}

public static String a(String str, String str2, int i, int i2, int i3, int i4) {
return null;
}
}
