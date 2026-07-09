package ru.drunvisual.mixin;

import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import drunvisual.inventory.CooldownInfo;
import drunvisual.modules.utilities.Cooldowns;
import drunvisual.util.ElapsedTimer;
import drunvisual.modules.utilities.FastSwap;
import drunvisual.util.KeyNameFormatter;
import drunvisual.modules.visuals.ShulkerPreview;
import drunvisual.modules.visuals.ShulkerPreviewRenderer;
import drunvisual.module.ModuleRegistry;

@Mixin({HandledScreen.class})
public class HandledScreenMixin {

    @Shadow
    protected int x;

    @Shadow
    protected int y;

    @Shadow
    protected int backgroundWidth;

    @Shadow
    @Nullable
    protected Slot focusedSlot;

    @Unique
    private ElapsedTimer scrollTime;

    @Unique
    private ElapsedTimer getScrollTime() {
        if (this.scrollTime == null) {
            this.scrollTime = new ElapsedTimer();
        }
        return this.scrollTime;
    }

    @Inject(method = {"render"}, at = {@At("TAIL")})
    private void renderItemBindsOverlay(DrawContext DrawContextVar, int i, int i2, float f, CallbackInfo callbackInfo) {
        int iCeil;
        int i3;
        HandledScreen HandledScreenVar = (HandledScreen)(Object)this;
        FastSwap fastSwap = ModuleRegistry.FAST_SWAP;
        boolean z = fastSwap != null && fastSwap.E().k().booleanValue() && fastSwap.k();
        Cooldowns cooldowns = ModuleRegistry.COOLDOWNS;
        TextRenderer TextRendererVar = MinecraftClient.getInstance().textRenderer;
        for (Slot SlotVar : HandledScreenVar.getScreenHandler().slots) {
            ItemStack ItemStackVarGetStack = SlotVar.getStack();
            if (!ItemStackVarGetStack.isEmpty()) {
                int key = getKey(ItemStackVarGetStack, fastSwap);
                boolean z2 = cooldowns != null && cooldowns.k() && cooldowns.n() && hasAnyCooldown(ItemStackVarGetStack, cooldowns);
                boolean z3 = (key == -1 || key == 0) ? false : true;
                if (z2 || z3) {
                    int i4 = SlotVar.x + this.x;
                    int i5 = SlotVar.y + this.y;
                    if (z3 && !z2 && z) {
                        MatrixStack MatrixStackVarGetMatrices = DrawContextVar.getMatrices();
                        MatrixStackVarGetMatrices.push();
                        MatrixStackVarGetMatrices.translate(0.0f, 0.0f, 300.0f);
                        String strA = KeyNameFormatter.a(key);
                        float fMin = Math.min(1.0f, 14.0f / TextRendererVar.getWidth(strA)) * fastSwap.F().k().floatValue();
                        MatrixStackVarGetMatrices.scale(fMin, fMin, 1.0f);
                        DrawContextVar.drawText(TextRendererVar, strA, (int) ((i4 + 0.5f) / fMin), (int) ((i5 + 0.5f) / fMin), 16777215, true);
                        MatrixStackVarGetMatrices.pop();
                    }
                    if (z2) {
                        CooldownInfo.ItemCooldownSnapshot itemCooldownSnapshotA = CooldownInfo.a(MinecraftClient.getInstance().player.getItemCooldownManager(), ItemStackVarGetStack.getItem());
                        if (Cooldowns.a(ItemStackVarGetStack) && cooldowns.b(ItemStackVarGetStack.getItem())) {
                            float fA = cooldowns.a(ItemStackVarGetStack.getItem());
                            iCeil = (int) Math.ceil(((double) fA) * 18.5d);
                            i3 = fA <= 0.33f ? 65280 : fA <= 0.66f ? 16776960 : 16711680;
                        } else {
                            iCeil = (int) Math.ceil(itemCooldownSnapshotA.b());
                            float fC = itemCooldownSnapshotA.c();
                            i3 = fC <= 0.33f ? 65280 : fC <= 0.66f ? 16776960 : 16711680;
                        }
                        int i6 = i3;
                        MatrixStack MatrixStackVarMethod_514482 = DrawContextVar.getMatrices();
                        MatrixStackVarMethod_514482.push();
                        MatrixStackVarMethod_514482.translate(0.0f, 0.0f, 300.0f);
                        String strValueOf = String.valueOf(iCeil);
                        float fMin2 = Math.min(1.0f, 14.0f / TextRendererVar.getWidth(strValueOf)) * cooldowns.q().k().floatValue();
                        MatrixStackVarMethod_514482.scale(fMin2, fMin2, 1.0f);
                        DrawContextVar.drawText(TextRendererVar, strValueOf, (int) ((i4 + 0.5f) / fMin2), (int) ((i5 + 0.5f) / fMin2), i6, true);
                        MatrixStackVarMethod_514482.pop();
                    }
                }
            }
        }
        handleItemScroller();
    }

    @Unique
    private void handleItemScroller() {
        if (ModuleRegistry.ITEM_SCROLLER.k()) {
            MinecraftClient MinecraftClientVarGetInstance = MinecraftClient.getInstance();
            if (MinecraftClientVarGetInstance.player == null || MinecraftClientVarGetInstance.currentScreen == null || this.focusedSlot == null || !this.focusedSlot.hasStack() || this.focusedSlot.getStack().getItem() == Items.AIR) {
                return;
            }
            long jGetHandle = MinecraftClientVarGetInstance.getWindow().getHandle();
            boolean z = GLFW.glfwGetMouseButton(jGetHandle, 0) == 1;
            boolean z2 = GLFW.glfwGetKey(jGetHandle, 340) == 1;
            if (z && z2) {
                if (getScrollTime().a(ModuleRegistry.ITEM_SCROLLER.delay.k().longValue())) {
                    MinecraftClientVarGetInstance.interactionManager.clickSlot(((HandledScreen)(Object)this).getScreenHandler().syncId, this.focusedSlot.id, 0, SlotActionType.QUICK_MOVE, MinecraftClientVarGetInstance.player);
                    getScrollTime().b();
                }
            }
        }
    }

    @Unique
    private boolean hasAnyCooldown(ItemStack ItemStackVar, Cooldowns cooldowns) {
        return CooldownInfo.a(MinecraftClient.getInstance().player.getItemCooldownManager(), ItemStackVar.getItem()).a() || (Cooldowns.a(ItemStackVar) && cooldowns.b(ItemStackVar.getItem()));
    }

    @Unique
    private int getKey(ItemStack ItemStackVar, FastSwap fastSwap) {
        if (fastSwap == null) {
            return -1;
        }
        Item ItemVarGetItem = ItemStackVar.getItem();
        if (ItemVarGetItem == Items.NETHERITE_SCRAP) {
            return getKeyOrDefault(fastSwap.q().a());
        }
        if (ItemVarGetItem == Items.DRIED_KELP) {
            return getKeyOrDefault(fastSwap.r().a());
        }
        if (ItemVarGetItem == Items.ENDER_EYE) {
            int iA = fastSwap.s().a();
            return iA > 0 ? iA : getKeyOrDefault(fastSwap.y().a());
        }
        if (ItemVarGetItem == Items.SUGAR) {
            return getKeyOrDefault(fastSwap.t().a());
        }
        if (ItemVarGetItem == Items.CHORUS_FRUIT) {
            return getKeyOrDefault(fastSwap.n().a());
        }
        if (ItemVarGetItem == Items.ENDER_PEARL) {
            return getKeyOrDefault(fastSwap.o().a());
        }
        if (ItemVarGetItem == Items.POTION && fastSwap.a(ItemStackVar)) {
            return getKeyOrDefault(fastSwap.p().a());
        }
        if (ItemVarGetItem == Items.NETHER_STAR) {
            return getKeyOrDefault(fastSwap.u().a());
        }
        if (ItemVarGetItem == Items.SLIME_BALL) {
            return getKeyOrDefault(fastSwap.v().a());
        }
        if (ItemVarGetItem == Items.TURTLE_SCUTE) {
            return getKeyOrDefault(fastSwap.w().a());
        }
        if (ItemVarGetItem == Items.COBWEB) {
            return getKeyOrDefault(fastSwap.x().a());
        }
        if (ItemVarGetItem == Items.FIREWORK_STAR) {
            return getKeyOrDefault(fastSwap.z().a());
        }
        return -1;
    }

    @Unique
    private int getKeyOrDefault(int i) {
        if (i <= 0) {
            return -1;
        }
        return i;
    }

    @Inject(method = {"drawMouseoverTooltip"}, at = {@At("HEAD")}, cancellable = true)
    private void drunvisual$cancelShulkerTooltip(DrawContext DrawContextVar, int i, int i2, CallbackInfo callbackInfo) {
        ShulkerPreview shulkerPreview = ModuleRegistry.SHULKER_PREVIEW;
        if (shulkerPreview != null && shulkerPreview.k() && shulkerPreview.inventoryPreview.a() && this.focusedSlot != null && this.focusedSlot.hasStack() && ShulkerPreviewRenderer.isShulkerBox(this.focusedSlot.getStack())) {
            callbackInfo.cancel();
            ShulkerPreviewRenderer.renderInventoryHover(this.focusedSlot, DrawContextVar, (int) ((this.x - 170.0f) - 6.0f), this.y, true);
        }
    }
}
