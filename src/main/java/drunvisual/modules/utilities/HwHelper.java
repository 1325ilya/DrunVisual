package drunvisual.modules.utilities;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.BooleanSetting;
import drunvisual.settings.SettingGroup;
import drunvisual.events.ClientTickEvent;
import drunvisual.events.WorldRenderEvent;

@ModuleInfo(a = "HW Helper", b = "Shows helper timers for HolyWorld utility items.", c = ModuleCategory.UTILITIES)
public class HwHelper extends ClientModule {
    private static final long MESSAGE_DURATION_MS = 4000;
    private boolean stunTrapCooldownActive;
    private long stunTrapStartMs;
    private boolean explosiveTrapCooldownActive;
    private long explosiveTrapStartMs;
    private final BooleanSetting trackItems = new BooleanSetting("Track Items", true);
    private final BooleanSetting showStunTrap = new BooleanSetting("Stun Trap", true);
    private final BooleanSetting showExplosiveTrap = new BooleanSetting("Explosive Trap", true);
    private final SettingGroup renderGroup = new SettingGroup("Render");
    private final BooleanSetting renderNearbyTrapBox = new BooleanSetting("Render Nearby Trap Box", true);
    private int lastStunTrapCount = 0;
    private int lastExplosiveTrapCount = 0;

    @EventHandler
    public void a(WorldRenderEvent worldRenderEvent) {
    }

    @EventHandler
    public void a(ClientTickEvent clientTickEvent) {
        if (c.world == null || c.player == null || !this.trackItems.k().booleanValue()) {
            return;
        }
        updateTrackedItem(Items.PRISMARINE_SHARD, this.showStunTrap.k().booleanValue(), true);
        updateTrackedItem(Items.POPPED_CHORUS_FRUIT, this.showExplosiveTrap.k().booleanValue(), false);
        showOverlayMessage();
    }

    private void updateTrackedItem(Item ItemVar, boolean z, boolean z2) {
        int iCountItem = countItem(ItemVar);
        boolean zIsCoolingDown = c.player.getItemCooldownManager().isCoolingDown(ItemVar.getDefaultStack());
        int i = z2 ? this.lastStunTrapCount : this.lastExplosiveTrapCount;
        if (z && zIsCoolingDown && iCountItem < i) {
            if (z2) {
                this.stunTrapCooldownActive = true;
                this.stunTrapStartMs = System.currentTimeMillis();
            } else {
                this.explosiveTrapCooldownActive = true;
                this.explosiveTrapStartMs = System.currentTimeMillis();
            }
        }
        if (z2) {
            this.lastStunTrapCount = iCountItem;
        } else {
            this.lastExplosiveTrapCount = iCountItem;
        }
    }

    private int countItem(Item ItemVar) {
        int iGetCount = 0;
        for (int i = 0; i < c.player.getInventory().size(); i++) {
            ItemStack ItemStackVarGetStack = c.player.getInventory().getStack(i);
            if (ItemStackVarGetStack.getItem() == ItemVar) {
                iGetCount += ItemStackVarGetStack.getCount();
            }
        }
        return iGetCount;
    }

    private void showOverlayMessage() {
        String str;
        long jCurrentTimeMillis = System.currentTimeMillis();
        String cooldownMessage = getCooldownMessage("Stun Trap", jCurrentTimeMillis, this.stunTrapStartMs, this.stunTrapCooldownActive);
        if (cooldownMessage == null) {
            this.stunTrapCooldownActive = false;
        }
        String cooldownMessage2 = getCooldownMessage("Explosive Trap", jCurrentTimeMillis, this.explosiveTrapStartMs, this.explosiveTrapCooldownActive);
        if (cooldownMessage2 == null) {
            this.explosiveTrapCooldownActive = false;
        }
        if (cooldownMessage == null || cooldownMessage2 == null) {
            str = cooldownMessage != null ? cooldownMessage : cooldownMessage2;
        } else {
            str = cooldownMessage + " | " + cooldownMessage2;
        }
        if (str != null) {
            c.inGameHud.setOverlayMessage(Text.of(str), false);
        }
    }

    private String getCooldownMessage(String str, long j, long j2, boolean z) {
        if (!z) {
            return null;
        }
        long elapsed = j - j2;
        if (elapsed >= MESSAGE_DURATION_MS) {
            return null;
        }
        return String.format("%ModuleCard cooldown: %.1fs", str, Double.valueOf((MESSAGE_DURATION_MS - elapsed) / 1000.0d));
    }
}
