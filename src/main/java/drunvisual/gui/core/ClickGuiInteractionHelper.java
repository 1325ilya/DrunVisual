package drunvisual.gui.core;

import net.minecraft.client.gui.screen.Screen;
import drunvisual.client.MinecraftContext;
import drunvisual.module.ClientModule;
import drunvisual.gui.modules.ModuleCard;
import drunvisual.gui.modules.ModulesTab;

public final class ClickGuiInteractionHelper implements MinecraftContext {
    private ClickGuiInteractionHelper() {
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean handleRightClick(double d, double d2) {
        ModuleCard moduleCardFindCardAtBounds;
        ClientModule clientModuleE;
        Screen ScreenVar = c.currentScreen;
        if (!(ScreenVar instanceof DrunVisualClickGuiScreen)) {
            return false;
        }
        ClickGuiTab clickGuiTabA = ((DrunVisualClickGuiScreen) ScreenVar).a();
        if (!(clickGuiTabA instanceof ModulesTab)) {
            return true;
        }
        ModulesTab modulesTab = (ModulesTab) clickGuiTabA;
        if ((moduleCardFindCardAtBounds = modulesTab.findCardAtBounds((int) d, (int) d2)) == null || (clientModuleE = moduleCardFindCardAtBounds.l().e()) == null || clientModuleE.m().isEmpty()) {
            return true;
        }
        modulesTab.openSettingsForCard(moduleCardFindCardAtBounds);
        return true;
    }
}
