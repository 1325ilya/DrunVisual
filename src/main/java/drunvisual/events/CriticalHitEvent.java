package drunvisual.events;

import net.minecraft.entity.Entity;

public class CriticalHitEvent extends DrunVisualEvent {
    private final Entity entity;

    public CriticalHitEvent(Entity EntityVar) {
        this.entity = EntityVar;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public Entity a() {
        return this.entity;
    }
}
