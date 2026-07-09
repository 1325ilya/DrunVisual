package drunvisual.gui.modules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.client.MinecraftContext;
import drunvisual.gui.modules.ModuleCard;
import drunvisual.gui.modules.ModulesTab;
import drunvisual.module.ClientModule;
import drunvisual.core.Bool;
import drunvisual.gui.core.GuiEntry;
import drunvisual.gui.core.GuiInput;
import drunvisual.gui.core.GuiInteractionState;
import drunvisual.gui.core.DrunVisualClickGuiScreen;
import drunvisual.render.Renderer2D;
import drunvisual.render.icons.IconTextureRegistry;

public class ModuleCardList {
    private final List<ModuleCard> c = new ArrayList();
    private float d;
    private float e;
    public static int a;
    public static boolean b;

    public List<ModuleCard> a() {
        return this.c;
    }

    public boolean hasOpenPanelAt(int i, int i2) {
        for (ModuleCard moduleCard : this.c) {
            if (!moduleCard.f() && (moduleCard.d(i, i2) || moduleCard.f(i, i2))) {
                return true;
            }
        }
        return false;
    }

    public void a(float f, float f2, float f3, int i, float f4, float f5) {
        this.d = f;
        this.e = f2;
        float f6 = f - f3;
        Iterator<ModuleCard> it = this.c.iterator();
        while (it.hasNext()) {
            ModuleCard next = it.next();
            if (next.e()) {
                it.remove();
            } else {
                int nextOrd = next.m();
                if (nextOrd >= 0) {
                    next.a(f6 + ((nextOrd / i) * (f4 + f5)) + 1.0f);
                    if (!next.f() && next.a(f, f2)) {
                        next.b();
                    }
                }
            }
        }
    }

    public void b() {
        int i = 0;
        int i2 = 0;
        Iterator<ModuleCard> it = this.c.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ModuleCard next = it.next();
            if (!next.f() && next.g()) {
                i = 1;
                GuiInput.a(next.j(), next.i(), 150.0f, next.k());
                break;
            }
        }
        if (i == 0) {
            Iterator<ModuleCard> it2 = this.c.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                ModuleCard next2 = it2.next();
                if (!next2.f() && !next2.g() && next2.u()) {
                    i2 = 1;
                    float[] fArrW = next2.w();
                    if (fArrW != null) {
                        GuiInput.a(fArrW[0], fArrW[1], fArrW[2], fArrW[3]);
                    }
                    break;
                }
            }
        }
        if (i == 0 && i2 == 0) {
            GuiInput.a();
        }
        GuiInteractionState.a().a(Bool.from(i));
        GuiInteractionState.a().c(Bool.from(i2));
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, int i, int i2, float f3, float f4) {
        ModuleCard moduleCardA = a(i, i2);
        boolean z = moduleCardA != null;
        Iterator<ModuleCard> it = this.c.iterator();
        while (it.hasNext()) {
            ModuleCard next = it.next();
            if (!next.g()) {
                next.a(MatrixStackVar, renderer2D, f, f2, i, i2, f3, f4, Bool.from((!z || moduleCardA == next) ? 0 : 1));
            }
        }
        for (ModuleCard moduleCard : this.c) {
            if (!moduleCard.g()) {
                moduleCard.b(MatrixStackVar, renderer2D, i, i2);
            }
        }
    }

    public void b(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, int i, int i2, float f3, float f4) {
        for (ModuleCard moduleCard : this.c) {
            if (moduleCard.g()) {
                moduleCard.a(MatrixStackVar, renderer2D, f, f2, i, i2, f3, f4);
            }
        }
    }

    public boolean c() {
        for (ModuleCard moduleCard : this.c) {
            if (!moduleCard.f() && moduleCard.g()) {
                return true;
            }
        }
        return false;
    }

    public boolean d() {
        for (ModuleCard moduleCard : this.c) {
            if (!moduleCard.f() && !moduleCard.g() && moduleCard.u()) {
                return true;
            }
        }
        return false;
    }

    public GuiEntry e() {
        for (ModuleCard moduleCard : this.c) {
            if (!moduleCard.f() && moduleCard.g()) {
                return moduleCard.l();
            }
        }
        if (!b) {
            return null;
        }
        return null;
    }

    public void f() {
        for (ModuleCard moduleCard : this.c) {
            if (!moduleCard.f() && moduleCard.g()) {
                moduleCard.b();
            }
        }
    }

    public void g() {
        Iterator<ModuleCard> it = this.c.iterator();
        while (it.hasNext()) {
            ModuleCard next = it.next();
            if (next.g()) {
                next.c();
                it.remove();
            }
        }
    }

    public void h() {
        for (ModuleCard moduleCard : this.c) {
            if (!moduleCard.f() && !moduleCard.g()) {
                moduleCard.v();
            }
        }
    }

    public void i() {
        Iterator<ModuleCard> it = this.c.iterator();
        while (it.hasNext()) {
            it.next().b();
        }
    }

    public void j() {
        Iterator<ModuleCard> it = this.c.iterator();
        while (it.hasNext()) {
            it.next().c();
        }
        this.c.clear();
    }

    public void k() {
        Iterator<ModuleCard> it = this.c.iterator();
        while (it.hasNext()) {
            ModuleCard next = it.next();
            if (next.g()) {
                next.c();
                it.remove();
            } else {
                next.d();
            }
        }
    }

    public ModuleCard a(int i, int i2) {
        for (ModuleCard moduleCard : this.c) {
            if (!moduleCard.f() && !moduleCard.g() && moduleCard.f(i, i2)) {
                return moduleCard;
            }
        }
        return null;
    }

    public boolean b(int i, int i2) {
        return Bool.from(a(i, i2) != null ? 1 : 0);
    }

    public boolean c(int i, int i2) {
        ModuleCard moduleCardA = a(i, i2);
        if (moduleCardA != null) {
            moduleCardA.a(i, i2);
            return true;
        }
        for (ModuleCard moduleCard : this.c) {
            if (!moduleCard.f() && moduleCard.d(i, i2)) {
                moduleCard.a(i, i2);
                return true;
            }
        }
        if (c()) {
            f();
            return true;
        }
        if (!d()) {
            return false;
        }
        h();
        return true;
    }

    public boolean d(int i, int i2) {
        return false;
    }

    public void e(int i, int i2) {
        Iterator<ModuleCard> it = this.c.iterator();
        while (it.hasNext()) {
            it.next().c(i, i2);
        }
    }

    public void a(int i, int i2, double d, double d2) {
        for (ModuleCard moduleCard : this.c) {
            if (!moduleCard.f()) {
                moduleCard.a(i, i2, d, d2);
            } else if (b) {
            }
        }
    }

    public boolean a(float f, int i, int i2) {
        if (c()) {
            f();
            return true;
        }
        if (d()) {
            h();
            return true;
        }
        for (ModuleCard moduleCard : this.c) {
            if (!moduleCard.f() && moduleCard.e(i, i2)) {
                moduleCard.a(f, i, i2);
                return true;
            }
        }
        return false;
    }

    public boolean a(int i, int i2, int i3) {
        for (ModuleCard moduleCard : this.c) {
            if (!moduleCard.f() && moduleCard.a(i, i2, i3)) {
                return true;
            }
        }
        return false;
    }

    public boolean a(char c, int i) {
        for (ModuleCard moduleCard : this.c) {
            if (!moduleCard.f() && moduleCard.a(c, i)) {
                return true;
            }
        }
        return false;
    }

    public boolean l() {
        for (ModuleCard moduleCard : this.c) {
            if (!moduleCard.f() && moduleCard.h()) {
                return true;
            }
        }
        return false;
    }

    public void a(GuiEntry guiEntry, int i, boolean z, float f, float f2, float f3, float f4, float f5, float f6) {
        Iterator<ModuleCard> itCleanup = this.c.iterator();
        while (itCleanup.hasNext()) {
            if (itCleanup.next().e()) {
                itCleanup.remove();
            }
        }
        for (ModuleCard moduleCard : this.c) {
            if (moduleCard.l() == guiEntry && !moduleCard.f() && moduleCard.g()) {
                moduleCard.b();
                return;
            }
        }
        Iterator<ModuleCard> itClose = this.c.iterator();
        while (itClose.hasNext()) {
            ModuleCard mc = itClose.next();
            if (!mc.f()) {
                mc.c();
                itClose.remove();
            }
        }
        h();
        ModuleCard moduleCardA = ModuleCard.a(guiEntry, guiEntry.e(), i, z, f2, f4);
        moduleCardA.a((f5 + (12.0f / 2.0f)) - (150.0f / 2.0f), (f6 - 30.0f) - 4.0f, false);
        this.c.add(moduleCardA);
    }

    public void a(GuiEntry guiEntry, int i, boolean z, float f, float f2, float f3, float f4, int i2, int i3) {
        Iterator<ModuleCard> itCleanup = this.c.iterator();
        while (itCleanup.hasNext()) {
            if (itCleanup.next().e()) {
                itCleanup.remove();
            }
        }
        for (ModuleCard moduleCard : this.c) {
            if (moduleCard.l() == guiEntry && !moduleCard.f() && moduleCard.g()) {
                moduleCard.b();
                return;
            }
        }
        Iterator<ModuleCard> itClose = this.c.iterator();
        while (itClose.hasNext()) {
            ModuleCard mc = itClose.next();
            if (!mc.f()) {
                mc.c();
                itClose.remove();
            }
        }
        h();
        ModuleCard moduleCardA = ModuleCard.a(guiEntry, guiEntry.e(), i, z, f2, f4);
        moduleCardA.a(i2 - (150.0f / 2.0f), (i3 - 30.0f) - 8.0f, true);
        this.c.add(moduleCardA);
    }

    public void a(GuiEntry guiEntry, int i, boolean z, float f, float f2) {
        if (guiEntry.e() == null || guiEntry.e().m().isEmpty()) {
            return;
        }
        Iterator<ModuleCard> itCleanup = this.c.iterator();
        while (itCleanup.hasNext()) {
            if (itCleanup.next().e()) {
                itCleanup.remove();
            }
        }
        ModuleCard moduleCard = null;
        Iterator<ModuleCard> it = this.c.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ModuleCard next = it.next();
            if (next.l() == guiEntry && !next.g()) {
                moduleCard = next;
                break;
            }
        }
        if (moduleCard != null) {
            if (moduleCard.f()) {
                moduleCard.d();
            } else {
                moduleCard.b();
            }
            return;
        }
        Iterator<ModuleCard> itCloseAll = this.c.iterator();
        while (itCloseAll.hasNext()) {
            ModuleCard mc2 = itCloseAll.next();
            mc2.c();
            itCloseAll.remove();
        }
        h();
        IconTextureRegistry.TextureInfo arrowInfo = IconTextureRegistry.getInfo(IconTextureRegistry.ARROW_V);
        float fC = arrowInfo != null ? arrowInfo.c() / 2.0f : 0.0f;
        ArrayList<ModuleCard> arrayList = new ArrayList();
        for (ModuleCard moduleCard3 : this.c) {
            if (!moduleCard3.f() && moduleCard3.n() == z) {
                arrayList.add(moduleCard3);
            }
        }
        ModuleCard.VerticalAlign verticalAlign = ModuleCard.VerticalAlign.CENTER;
        float fK = new ModuleCard(guiEntry, guiEntry.e(), i, z, f, f2, verticalAlign).k();
        if (!arrayList.isEmpty()) {
            float fA = ModuleCard.a(f, f2, ModuleCard.VerticalAlign.CENTER, fC, fK);
            ArrayList arrayList2 = new ArrayList();
            for (ModuleCard moduleCard4 : arrayList) {
                if (ModuleCard.a(fA, fK, moduleCard4.i(), moduleCard4.k())) {
                    arrayList2.add(moduleCard4);
                } else {
                }
            }
            if (!arrayList2.isEmpty()) {
                ModuleCard moduleCard5 = (ModuleCard) arrayList2.get(0);
                ModuleCard.VerticalAlign verticalAlign2 = f + (f2 / 2.0f) >= moduleCard5.i() + (moduleCard5.k() / 2.0f) ? ModuleCard.VerticalAlign.TOP : ModuleCard.VerticalAlign.BOTTOM;
                float fA2 = ModuleCard.a(f, f2, verticalAlign2, fC, fK);
                ArrayList arrayList3 = new ArrayList();
                for (ModuleCard moduleCard6 : arrayList) {
                    if (ModuleCard.a(fA2, fK, moduleCard6.i(), moduleCard6.k())) {
                        arrayList3.add(moduleCard6);
                    }
                }
                if (arrayList3.isEmpty()) {
                    verticalAlign = verticalAlign2;
                } else {
                    ModuleCard.VerticalAlign verticalAlign3 = verticalAlign2 != ModuleCard.VerticalAlign.TOP ? ModuleCard.VerticalAlign.TOP : ModuleCard.VerticalAlign.BOTTOM;
                    float fA3 = ModuleCard.a(f, f2, verticalAlign3, fC, fK);
                    ArrayList arrayList4 = new ArrayList();
                    for (ModuleCard moduleCard7 : arrayList) {
                        if (ModuleCard.a(fA3, fK, moduleCard7.i(), moduleCard7.k())) {
                            arrayList4.add(moduleCard7);
                        }
                    }
                    if (arrayList4.isEmpty()) {
                        verticalAlign = verticalAlign3;
                    } else {
                        verticalAlign = ModuleCard.VerticalAlign.CENTER;
                        Iterator it2 = arrayList2.iterator();
                        while (it2.hasNext()) {
                            ((ModuleCard) it2.next()).b();
                        }
                    }
                }
            }
        }
        ModuleCard moduleCard8 = new ModuleCard(guiEntry, guiEntry.e(), i, z, f, f2, verticalAlign);
        float leftGui = (MinecraftContext.d.getWidth() / 4.0f) - 205.5f;
        float rightGui = leftGui + DrunVisualClickGuiScreen.d();
        float panelX = z ? rightGui + 20.0f : (leftGui - 20.0f) - 150.0f;
        moduleCard8.a(panelX, f2, false);
        this.c.add(moduleCard8);
    }

    public void a(ClientModule clientModule) {
        if (clientModule == null || clientModule.m().isEmpty()) {
            return;
        }
        for (ModuleCard moduleCard : this.c) {
            if (moduleCard.l().e() == clientModule && !moduleCard.f()) {
                moduleCard.b();
                return;
            }
        }
        i();
        h();
        this.c.add(new ModuleCard(new ModulesTab.ModuleEntry(clientModule), clientModule, -1, false, this.d - 32.0f, 15.0f, ModuleCard.VerticalAlign.CENTER));
    }

    public static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }

    public ModuleCard findCardByBounds(int i, int i2) {
        for (ModuleCard moduleCard : this.c) {
            if (!moduleCard.f() && moduleCard.e(i, i2)) {
                return moduleCard;
            }
        }
        return null;
    }
}
