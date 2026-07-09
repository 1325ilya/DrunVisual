package drunvisual.modules.utilities;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import drunvisual.events.ClientTickEvent;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.BooleanSetting;

@ModuleInfo(a = "AutoTool", b = "Автоматически выбирает лучший инструмент под блок.", c = ModuleCategory.UTILITIES)
public class AutoTool extends ClientModule {
    private final BooleanSetting onlyHotbar = new BooleanSetting("Только хотбар", true);
    private final BooleanSetting ignoreCreative = new BooleanSetting("Игнорировать креатив", true);
    private int lastSlot = -1;

    @EventHandler
    public void onTick(ClientTickEvent clientTickEvent) {
        if (c.player == null || c.world == null || c.currentScreen != null) {
            this.lastSlot = -1;
            return;
        }
        ClientPlayerEntity player = c.player;
        if (this.ignoreCreative.get() && player.getAbilities().creativeMode) {
            this.lastSlot = -1;
            return;
        }
        if (!c.options.attackKey.isPressed()) {
            this.lastSlot = -1;
            return;
        }
        HitResult hitResult = c.crosshairTarget;
        if (!(hitResult instanceof BlockHitResult) || hitResult.getType() != HitResult.Type.BLOCK) {
            this.lastSlot = -1;
            return;
        }
        BlockState blockState = c.world.getBlockState(((BlockHitResult) hitResult).getBlockPos());
        if (blockState.isAir()) {
            this.lastSlot = -1;
            return;
        }
        int bestSlot = findBestSlot(blockState);
        if (bestSlot == -1 || bestSlot == player.getInventory().selectedSlot) {
            return;
        }
        this.lastSlot = player.getInventory().selectedSlot;
        player.getInventory().selectedSlot = bestSlot;
    }

    private int findBestSlot(BlockState blockState) {
        int start = this.onlyHotbar.get() ? 0 : 0;
        int end = this.onlyHotbar.get() ? 9 : 36;
        float bestSpeed = currentSpeed(blockState, c.player.getInventory().selectedSlot);
        int bestSlot = -1;
        for (int slot = start; slot < end; slot++) {
            ItemStack stack = c.player.getInventory().getStack(slot);
            if (stack.isEmpty()) {
                continue;
            }
            float speed = stack.getMiningSpeedMultiplier(blockState);
            if (speed > bestSpeed + 0.01f) {
                bestSpeed = speed;
                bestSlot = slot;
            }
        }
        return bestSlot;
    }

    private float currentSpeed(BlockState blockState, int slot) {
        ItemStack current = c.player.getInventory().getStack(slot);
        return current.isEmpty() ? 1.0f : current.getMiningSpeedMultiplier(blockState);
    }

    @Override
    public void f() {
        super.f();
        this.lastSlot = -1;
    }
}
