package drunvisual.events;

import net.minecraft.entity.LivingEntity;

public class EntityJumpEvent extends DrunVisualEvent {
    private final LivingEntity entity;

    public EntityJumpEvent(LivingEntity LivingEntityVar) {
        this.entity = LivingEntityVar;
    }

    public LivingEntity entity() {
        return this.entity;
    }

    public LivingEntity a() {
        return this.entity;
    }
}
