package drunvisual.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.client.network.ClientPlayerEntity;
import drunvisual.client.MinecraftContext;
import drunvisual.entity.EntityUtils;
import drunvisual.events.ClientTickEvent;
import drunvisual.hud.core.HudService;
import drunvisual.hud.core.HudServiceInfo;
import drunvisual.util.ElapsedTimer;

@HudServiceInfo
public class TargetTracker extends HudService implements MinecraftContext {
    private static final long TARGET_MEMORY_MS = 1000;
    private LivingEntity currentTarget;
    private final Map<LivingEntity, ElapsedTimer> recentlySeenTargets = new HashMap();
    private final ElapsedTimer clearTimer = new ElapsedTimer();
    private boolean waitingToClearTarget;

    @EventHandler
    public void onClientTick(ClientTickEvent clientTickEvent) {
        if (c.player == null || c.world == null) {
            return;
        }
        HitResult hitResult = c.crosshairTarget;
        if (hitResult instanceof EntityHitResult && hitResult.getType() == HitResult.Type.ENTITY) {
            Entity EntityVarGetEntity = ((EntityHitResult) hitResult).getEntity();
            if (EntityVarGetEntity instanceof LivingEntity) {
                LivingEntity LivingEntityVar = (LivingEntity) EntityVarGetEntity;
                if (EntityVarGetEntity != c.player && !LivingEntityVar.isInvisible() && !EntityUtils.a(LivingEntityVar)) {
                    this.currentTarget = LivingEntityVar;
                    this.recentlySeenTargets.put(LivingEntityVar, new ElapsedTimer());
                    this.clearTimer.reset();
                    this.waitingToClearTarget = true;
                }
            }
        } else if (this.waitingToClearTarget && this.clearTimer.hasElapsed(TARGET_MEMORY_MS)) {
            this.currentTarget = null;
            this.waitingToClearTarget = false;
        }
        this.recentlySeenTargets.entrySet().removeIf(entry -> {
            LivingEntity LivingEntityVar2 = (LivingEntity) entry.getKey();
            if (!((ElapsedTimer) entry.getValue()).hasElapsed(TARGET_MEMORY_MS)) {
                return false;
            }
            if (!(LivingEntityVar2 instanceof PlayerEntity) || !LivingEntityVar2.isInvisible() || this.currentTarget != LivingEntityVar2) {
                return true;
            }
            this.currentTarget = null;
            this.waitingToClearTarget = false;
            return true;
        });
    }

    public boolean hasTarget() {
        return this.currentTarget != null;
    }

    public List<LivingEntity> recentTargets() {
        return new ArrayList(this.recentlySeenTargets.keySet());
    }

    public void clearTarget() {
        this.currentTarget = null;
        this.waitingToClearTarget = false;
        this.clearTimer.reset();
    }

    public void clearAll() {
        clearTarget();
        this.recentlySeenTargets.clear();
    }

    public LivingEntity currentTarget() {
        return this.currentTarget;
    }

    public void a(ClientTickEvent clientTickEvent) {
        onClientTick(clientTickEvent);
    }

    public boolean d() {
        return hasTarget();
    }

    public List<LivingEntity> e() {
        return recentTargets();
    }

    public void f() {
        clearTarget();
    }

    public void g() {
        clearAll();
    }

    public LivingEntity h() {
        return currentTarget();
    }
}
