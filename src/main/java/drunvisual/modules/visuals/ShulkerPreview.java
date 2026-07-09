package drunvisual.modules.visuals;

import meteordevelopment.orbit.EventHandler;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.BooleanSetting;
import ru.drunvisual.DrunVisual;
import drunvisual.events.HudRenderPostEvent;

@ModuleInfo(a = "ShulkerPreview", b = "Просмотр содержимого шалкеров без открытия (в инвентаре при наводении и когда шалкер дропнут).", c = ModuleCategory.VISUALS)
public class ShulkerPreview extends ClientModule {
    public final BooleanSetting inventoryPreview = new BooleanSetting("Inventory Hover", true);
    public final BooleanSetting worldPreview = new BooleanSetting("Dropped Items", true);

    public ShulkerPreview() {
        collectSettings();
    }

    @EventHandler
    public void onHudRenderPost(HudRenderPostEvent hudRenderPostEvent) {
        ShulkerPreviewRenderer.renderWorldTarget(c, hudRenderPostEvent.a(), DrunVisual.getInstance().getRender());
    }
}
