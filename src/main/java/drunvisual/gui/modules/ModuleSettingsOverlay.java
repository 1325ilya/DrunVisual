package drunvisual.gui.modules;

import java.util.Iterator;
import java.util.List;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.gui.core.ClickGuiOverlay;
import drunvisual.gui.core.ClickGuiTab;
import drunvisual.gui.core.ClickGuiTabType;
import drunvisual.gui.core.DrunVisualClickGuiScreen;
import drunvisual.render.Renderer2D;

public class ModuleSettingsOverlay implements ClickGuiOverlay {
    private static final float MODULE_LIST_TOP_OFFSET = 48.0f;
    private static final float MODULE_LIST_BOTTOM_PADDING = 5.0f;
    private final DrunVisualClickGuiScreen screen;
    private int mouseX;
    private int mouseY;

    public ModuleSettingsOverlay(DrunVisualClickGuiScreen pulseClickGuiScreen) {
        this.screen = pulseClickGuiScreen;
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, int i, int i2) {
        ClickGuiTab clickGuiTabA = this.screen.a();
        if (clickGuiTabA instanceof ModulesTab) {
            ModulesTab modulesTab = (ModulesTab) clickGuiTabA;
            if (clickGuiTabA.a() != ClickGuiTabType.MODULES) {
                return;
            }
            List<ModuleCard> listH = modulesTab.h();
            if (listH.isEmpty()) {
                return;
            }
            this.mouseX = i;
            this.mouseY = i2;
            float f3 = f2 + MODULE_LIST_TOP_OFFSET;
            float fE = (DrunVisualClickGuiScreen.e() - MODULE_LIST_TOP_OFFSET) - MODULE_LIST_BOTTOM_PADDING;
            ModuleCard moduleCardFindHoveredSettingsCard = findHoveredSettingsCard(listH, i, i2);
            Iterator<ModuleCard> it = listH.iterator();
            while (it.hasNext()) {
                ModuleCard next = it.next();
                next.a(MatrixStackVar, renderer2D, f, f2, i, i2, f3, fE, moduleCardFindHoveredSettingsCard == null || moduleCardFindHoveredSettingsCard == next);
            }
        }
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, int i, int i2) {
        ClickGuiTab clickGuiTabA = this.screen.a();
        if (clickGuiTabA instanceof ModulesTab) {
            ModulesTab modulesTab = (ModulesTab) clickGuiTabA;
            if (clickGuiTabA.a() != ClickGuiTabType.MODULES) {
                return;
            }
            Iterator<ModuleCard> it = modulesTab.h().iterator();
            while (it.hasNext()) {
                it.next().a(MatrixStackVar, renderer2D, i, i2);
            }
        }
    }

    @Override
    public void a(float f, float f2, int i, int i2) {
    }

    private ModuleCard findHoveredSettingsCard(List<ModuleCard> list, int i, int i2) {
        for (ModuleCard moduleCard : list) {
            if (!moduleCard.f() && !moduleCard.g() && moduleCard.f(i, i2)) {
                return moduleCard;
            }
        }
        return null;
    }
}
