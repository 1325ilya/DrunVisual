package drunvisual.modules.utilities;

import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.SliderSetting;

@ModuleInfo(a = "Item Scroller", b = "Quickly moves items while Shift and left mouse are held.", c = ModuleCategory.UTILITIES)
public class ItemScroller extends ClientModule {
    public final SliderSetting delay = new SliderSetting("Delay", 50.0f, 10.0f, 500.0f, 10.0f);
}
