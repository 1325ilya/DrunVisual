package drunvisual.hud.core;

import lombok.Generated;
import drunvisual.events.EventBusService;

public abstract class HudService {
    private boolean enabled;

    public void a() {
        HudServiceInfo hudServiceInfo = (HudServiceInfo) getClass().getAnnotation(HudServiceInfo.class);
        a(hudServiceInfo == null || hudServiceInfo.enabledByDefault());
    }

    public void a(boolean z) {
        if (this.enabled == z) {
            return;
        }
        this.enabled = z;
        if (z) {
            EventBusService.EVENT_BUS.subscribe(this);
        } else {
            EventBusService.EVENT_BUS.unsubscribe(this);
        }
    }

    public void b() {
        a(!this.enabled);
    }

    @Generated
    public boolean c() {
        return this.enabled;
    }
}
