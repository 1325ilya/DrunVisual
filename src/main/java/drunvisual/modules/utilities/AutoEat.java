package drunvisual.modules.utilities;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.component.DataComponentTypes;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.ModeSetting;
import drunvisual.settings.SliderSetting;
import drunvisual.settings.TokenSetting;
import drunvisual.events.ClientTickEvent;

@ModuleInfo(a = "Auto Eat", b = "Automatically eats food at the selected hunger level.", c = ModuleCategory.UTILITIES)
public class AutoEat extends ClientModule {
    private final ModeSetting mode = new ModeSetting("Mode", new String[]{"Hand", "Command"}, "Hand");
    private final TokenSetting command = new TokenSetting("Command", TokenSetting.TokenType.COMMAND, "/feed", "").a(() -> {
        return Boolean.valueOf(this.mode.b("Command"));
    });
    private final SliderSetting hungerLevel = new SliderSetting("Hunger Level", 15.0f, 1.0f, 20.0f, 1.0f);

    @EventHandler
    public void a(ClientTickEvent clientTickEvent) {
    }

    private boolean a(ItemStack ItemStackVar) {
        return ItemStackVar != null && ItemStackVar.getComponents().contains(DataComponentTypes.FOOD);
    }

    @Override
    public void f() {
        super.f();
    }
}
