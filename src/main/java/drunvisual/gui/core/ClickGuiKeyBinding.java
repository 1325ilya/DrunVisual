package drunvisual.gui.core;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public final class ClickGuiKeyBinding {
    public static KeyBinding OPEN_KEY;

    private ClickGuiKeyBinding() {
    }

    public static void register() {
        OPEN_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding("Open Click GUI", InputUtil.Type.KEYSYM, 344, "DrunVisual"));
    }
}
