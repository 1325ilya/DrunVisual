package drunvisual.modules.utilities;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.events.AttackEntityEvent;
import drunvisual.events.TotemPopEvent;
import drunvisual.media.chat.ChatMessages;
import drunvisual.util.ElapsedTimer;

@ModuleInfo(a = "Totem Tracker", b = "Tracks totem pops from recently attacked entities.", c = ModuleCategory.UTILITIES)
public class TotemTracker extends ClientModule {
    private static final long TARGET_MEMORY_MS = 5000;
    private final Map<UUID, ElapsedTimer> attackedTargets = new HashMap();
    private int lastNotificationAge = -1;

    @EventHandler
    public void a(AttackEntityEvent attackEntityEvent) {
        if (c.player != null) {
            net.minecraft.entity.Entity attacked = attackEntityEvent.a();
            if (!(attacked instanceof LivingEntity)) {
                return;
            }
            LivingEntity ClientPlayerEntityVar = (LivingEntity) attacked;
            if (ClientPlayerEntityVar == c.player) {
                return;
            }
            this.attackedTargets.put(ClientPlayerEntityVar.getUuid(), new ElapsedTimer());
        }
    }

    @EventHandler
    public void a(TotemPopEvent totemPopEvent) {
        if (c.player == null) {
            return;
        }
        LivingEntity ClientPlayerEntityVarA = totemPopEvent.a();
        if (ClientPlayerEntityVarA == c.player) {
            return;
        }
        ElapsedTimer elapsedTimer = this.attackedTargets.get(ClientPlayerEntityVarA.getUuid());
        if (elapsedTimer != null && !elapsedTimer.hasElapsed(TARGET_MEMORY_MS)) {
            notifyTotemPop(ClientPlayerEntityVarA, totemPopEvent.b());
        }
        removeExpiredTargets();
    }

    private void notifyTotemPop(LivingEntity LivingEntityVar, ItemStack ItemStackVar) {
        int i = c.player.age;
        if (i == this.lastNotificationAge) {
            return;
        }
        this.lastNotificationAge = i;
        ChatMessages.a((Object) ("Totem Tracker: " + LivingEntityVar.getName().getString() + " popped a " + (EnchantmentHelper.getEnchantments(ItemStackVar).isEmpty() ? "regular" : "enchanted") + " totem."));
    }

    private void removeExpiredTargets() {
        this.attackedTargets.entrySet().removeIf(entry -> {
            return ((ElapsedTimer) entry.getValue()).hasElapsed(TARGET_MEMORY_MS);
        });
    }

    @Override
    public void f() {
        super.f();
        this.attackedTargets.clear();
        this.lastNotificationAge = -1;
    }
}
