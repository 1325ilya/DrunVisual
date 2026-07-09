package drunvisual.gui.modules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleRegistry;
import drunvisual.core.Bool;
import drunvisual.gui.core.ClickGuiTab;
import drunvisual.gui.core.ClickGuiTabType;
import drunvisual.gui.core.GuiEntry;
import drunvisual.gui.core.GuiEntrySource;
import drunvisual.gui.core.PanelFadeOverlay;
import drunvisual.gui.core.DrunVisualClickGuiScreen;
import drunvisual.gui.core.TabHost;
import drunvisual.gui.core.TabSelector;
import drunvisual.gui.widgets.SearchBox;
import drunvisual.gui.widgets.SettingsIconButton;
import drunvisual.render.Renderer2D;

public class ModulesTab implements TabHost, GuiEntrySource, ClickGuiTab {
private static final String[] a = {"Visuals", "HUD", "Utilities"};
private static final ModuleCategory[] b = {ModuleCategory.VISUALS, ModuleCategory.HUD, ModuleCategory.UTILITIES};
private static final float c = 117.5f;
private static final float d = 15.0f;
private static final float e = 19.0f;
private static final float f = 19.0f;
private static final float g = 15.0f;
private static final float h = 4.0f;
private final TabSelector l;
private final ModuleGrid m;
private final PanelFadeOverlay n;
private final SearchBox o;
private final SettingsIconButton p;
private List<ModuleEntry> r;
private int i = 0;
private String q = "";
private boolean s = false;
private final Map<ModuleCategory, List<ModuleEntry>> j = new HashMap();
private final List<ModuleEntry> k = new ArrayList();

public static class ModuleEntry implements GuiEntry {
private final ClientModule a;

public ModuleEntry(ClientModule clientModule) {
this.a = clientModule;
}

@Override
public String a() {
return this.a.g();
}

@Override
public boolean b() {
return this.a.a();
}

@Override
public void a(boolean z) {
this.a.b(z);
}

public ModuleCategory i() {
return this.a.i();
}

@Override
public ClientModule e() {
return this.a;
}

@Override
public boolean d() {
return g();
}

public static String b(String str, String str2, int i, int i2, int i3, int i4) {
return null;
}
}

public ModulesTab() {
for (ModuleCategory moduleCategory : ModuleCategory.values()) {
ArrayList arrayList = new ArrayList();
Iterator<ClientModule> it = ModuleRegistry.byCategory(moduleCategory).iterator();
while (it.hasNext()) {
arrayList.add(new ModuleEntry(it.next()));
}
arrayList.sort(Comparator.comparing((ModuleEntry moduleEntry) -> moduleEntry.a().toLowerCase()));
this.j.put(moduleCategory, arrayList);
this.k.addAll(arrayList);
}
this.k.sort(Comparator.comparing(moduleEntry2 -> {
return moduleEntry2.a().toLowerCase();
}));
this.r = new ArrayList();
this.l = new TabSelector(this);
this.m = new ModuleGrid(this);
this.n = new PanelFadeOverlay();
this.o = new SearchBox(c, 15.0f);
this.o.a(this::a);
this.p = new SettingsIconButton();
this.p.a(r3 -> {
s();
});
}

private void s() {
}

private void a(String str) {
this.q = str == null ? "" : str.toLowerCase().trim();
if (this.q.isEmpty()) {
this.s = false;
if (this.r != null) {
this.r.clear();
return;
}
return;
}
this.s = true;
this.r = new ArrayList();
for (int i = 0; i < this.k.size(); i++) {
ModuleEntry moduleEntry = this.k.get(i);
if (moduleEntry.a().toLowerCase().contains(this.q)) {
this.r.add(moduleEntry);
}
}
this.m.d();
}

private boolean a(String str, String str2) {
String lowerCase = str.toLowerCase();
if (!lowerCase.contains(str2) && !lowerCase.replace(" ", "").contains(str2.replace(" ", "")) && !b(lowerCase, str2)) {
return false;
}
return true;
}

private boolean b(String str, String str2) {
String[] strArrSplit = str.split(" ");
StringBuilder sb = new StringBuilder();
for (String str3 : strArrSplit) {
if (!str3.isEmpty()) {
sb.append(str3.charAt(0));
}
}
return sb.toString().contains(str2.replace(" ", ""));
}

public ModuleCategory g() {
return b[this.i];
}

public void a(ModuleCategory moduleCategory) {
for (int i = 0; i < b.length; i++) {
if (b[i] == moduleCategory) {
if (this.i != i) {
this.i = i;
this.m.d();
return;
}
return;
}
}
}

@Override
public String[] c() {
return a;
}

@Override
public int d() {
return this.i;
}

@Override
public void a(int i) {
if (i < 0 || i >= b.length || this.i == i) {
return;
}
this.i = i;
this.m.d();
}

@Override
public List<? extends GuiEntry> e() {
if (this.s) {
return (this.r == null || this.r.isEmpty()) ? Collections.emptyList() : this.r;
}
List<ModuleEntry> list = this.j.get(g());
return list == null ? Collections.emptyList() : list;
}

@Override
public int f() {
return 2;
}

public List<ModuleCard> h() {
return this.m.a();
}

public boolean i() {
Iterator<ModuleCard> it = this.m.a().iterator();
while (it.hasNext()) {
if (!it.next().f()) {
return true;
}
}
return false;
}

public boolean j() {
return this.m.j();
}

public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D) {
this.m.a(MatrixStackVar, renderer2D);
}

public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, int i, int i2) {
this.m.a(MatrixStackVar, renderer2D, i, i2);
}

public void k() {
this.m.e();
}

public void l() {
this.m.f();
}

public void m() {
this.m.g();
}

public void n() {
this.m.h();
}

public void o() {
this.m.i();
}

@Override
public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, int i, int i2) {
this.m.b();
this.l.a(MatrixStackVar, renderer2D, f2, f3, i, i2);
float fD = ((f2 + DrunVisualClickGuiScreen.d()) - 19.0f) - 15.0f;
float f4 = (fD - h) - c;
float f5 = (f3 + 19.0f) - 3.0f;
this.o.a(MatrixStackVar, renderer2D, f4, f5, c, 15.0f, i, i2);
this.p.a(MatrixStackVar, renderer2D, fD, f5, 15.0f, i, i2);
this.m.a(MatrixStackVar, renderer2D, f2, f3, i, i2);
this.n.a(MatrixStackVar, renderer2D, f2, f3, i, i2);
}

@Override
public void a(float f2, float f3, int i, int i2) {
this.m.b();
if (this.o.a(i, i2) || this.p.a(i, i2)) {
return;
}
if (!this.m.j() && !this.o.c()) {
this.l.a(f2, f3, i, i2);
}
this.m.a(f2, f3, i, i2);
}

@Override
public void b(float f2, float f3, int i, int i2) {
this.m.b();
this.m.b(f2, f3, i, i2);
}

@Override
public void c(float f2, float f3, int i, int i2) {
this.m.c(f2, f3, i, i2);
}

@Override
public void a(float f2, float f3, int i, int i2, double d2, double d3) {
this.m.a(f2, f3, i, i2, d2, d3);
}

@Override
public void a(float f2) {
this.m.a(f2);
}

public void a(float f2, int i, int i2) {
this.m.a(f2, i, i2);
}

@Override
public boolean a(int i, int i2, int i3) {
if (this.m.p()) {
this.m.a(i, i2, i3);
return true;
}
if (this.o.c() && this.o.a(i, i2, i3)) {
return true;
}
return this.m.a(i, i2, i3);
}

@Override
public boolean b() {
return Bool.from((this.o.c() || this.m.c()) ? 1 : 0);
}

public boolean a(char c2, int i) {
return this.o.c() ? this.o.a(c2, i) : this.m.a(c2, i);
}

public boolean p() {
return this.o.c();
}

public boolean hasOpenSettingsAt(int i, int i2) {
return this.m.hasOpenSettingsAt(i, i2);
}

public ModuleCard getCardAt(int i, int i2) {
return this.m.findCardByBounds(i, i2);
}

public void openSettings(ClientModule clientModule) {
this.m.a(clientModule);
}

public ModuleCard findCardAtBounds(int i, int i2) {
for (ModuleCard moduleCard : this.m.a()) {
if (!moduleCard.f() && moduleCard.e(i, i2)) {
return moduleCard;
}
}
return null;
}

public void openSettingsForCard(ModuleCard moduleCard) {
if (moduleCard == null || moduleCard.f()) {
return;
}
moduleCard.b();
}

public String q() {
return this.q;
}

public boolean r() {
return this.s;
}

@Override
public ClickGuiTabType a() {
return ClickGuiTabType.MODULES;
}

public static String b(String str, String str2, int i, int i2, int i3, int i4) {
return null;
}
}
