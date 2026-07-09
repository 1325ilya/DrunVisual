package drunvisual.events;

import net.minecraft.entity.Entity;

public class AttackEntityEvent extends DrunVisualEvent {
    private final Entity entity;

    public AttackEntityEvent(Entity EntityVar) {
        this.entity = EntityVar;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public Entity a() {
        return this.entity;
    }
}
