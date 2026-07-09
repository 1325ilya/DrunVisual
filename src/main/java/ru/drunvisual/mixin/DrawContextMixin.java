package ru.drunvisual.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.Objects;
import java.util.stream.Stream;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.world.World;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.ColorHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import drunvisual.modules.utilities.AutoReissue;
import drunvisual.inventory.CooldownInfo;
import drunvisual.modules.utilities.Cooldowns;
import drunvisual.events.EventBusService;
import drunvisual.modules.utilities.HealingHelper;
import drunvisual.modules.utilities.ItemHighlighter;
import drunvisual.events.ItemOverlayRenderEvent;
import drunvisual.module.ModuleRegistry;

@Mixin({DrawContext.class})
public abstract class DrawContextMixin {

    @Shadow
    @Final
    private MatrixStack matrices;

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    public abstract int drawText(TextRenderer TextRendererVar, @Nullable String str, int i, int i2, int i3, boolean z);

    @Shadow
    public abstract void fill(RenderLayer RenderLayerVar, int i, int i2, int i3, int i4, int i5);

    @Shadow
    public abstract void fill(RenderLayer RenderLayerVar, int i, int i2, int i3, int i4, int i5, int i6);

    @Inject(method = {"drawItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;IIII)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;push()V", shift = At.Shift.AFTER)})
    private void renderItemHighlight(LivingEntity LivingEntityVar, World WorldVar, ItemStack ItemStackVar, int i, int i2, int i3, int i4, CallbackInfo callbackInfo) {
        ItemHighlighter itemHighlighter = ModuleRegistry.ITEM_HIGHLIGHTER;
        if (itemHighlighter != null && itemHighlighter.k() && itemHighlighter.a(ItemStackVar)) {
            Color colorA = itemHighlighter.a(ItemStackVar.getItem());
            int rgb = new Color(colorA.getRed(), colorA.getGreen(), colorA.getBlue(), itemHighlighter.n()).getRGB();
            this.matrices.push();
            this.matrices.translate(0.0f, 0.0f, 100.0f);
            fill(RenderLayer.getGuiOverlay(), i, i2, i + 16, i2 + 16, rgb);
            this.matrices.pop();
        }
    }

    @Inject(method = {"drawStackOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V"}, at = {@At("HEAD")}, cancellable = true)
    private void onDrawStackOverlay(TextRenderer TextRendererVar, ItemStack ItemStackVar, int i, int i2, @Nullable String str, CallbackInfo callbackInfo) {
        AutoReissue autoReissue;
        if (ItemStackVar.isEmpty()) {
            return;
        }
        this.matrices.push();
        if (ItemStackVar.getCount() != 1 || str != null) {
            String strValueOf = str == null ? String.valueOf(ItemStackVar.getCount()) : str;
            this.matrices.translate(0.0f, 0.0f, 200.0f);
            drawText(TextRendererVar, strValueOf, ((i + 19) - 2) - TextRendererVar.getWidth(strValueOf), i2 + 6 + 3, -1, true);
        }
        if (ItemStackVar.isItemBarVisible()) {
            int iGetItemBarStep = ItemStackVar.getItemBarStep();
            int iGetItemBarColor = ItemStackVar.getItemBarColor();
            int i3 = i + 2;
            int i4 = i2 + 13;
            fill(RenderLayer.getGui(), i3, i4, i3 + 13, i4 + 2, EventPriority.HIGHEST, -16777216);
            fill(RenderLayer.getGui(), i3, i4, i3 + iGetItemBarStep, i4 + 1, EventPriority.HIGHEST, ColorHelper.fullAlpha(iGetItemBarColor));
        }
        ClientPlayerEntity ClientPlayerEntityVar = MinecraftClient.getInstance().player;
        Item ItemVarGetItem = ItemStackVar.getItem();
        float fC = 0.0f;
        if (ClientPlayerEntityVar != null) {
            fC = CooldownInfo.a(ClientPlayerEntityVar.getItemCooldownManager(), ItemVarGetItem).c();
        }
        Cooldowns cooldowns = ModuleRegistry.COOLDOWNS;
        boolean zA = Cooldowns.a(ItemStackVar);
        if (cooldowns != null && cooldowns.k() && Cooldowns.a(ItemStackVar)) {
            float fA = cooldowns.a(ItemStackVar.getItem());
            if (fA > fC) {
                fC = fA;
            }
        }
        if (fC > 0.0f) {
            boolean z = true;
            if (ItemStackVar.getItem() instanceof PotionItem) {
                z = zA;
            }
            if (z) {
                RenderSystem.disableDepthTest();
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                int iCeil = MathHelper.ceil(16.0f * fC);
                int iFloor = i2 + MathHelper.floor(16.0f * (1.0f - fC));
                fill(RenderLayer.getGui(), i, iFloor, i + 16, iFloor + iCeil, EventPriority.HIGHEST, new Color(255, 255, 255, 127).getRGB());
                RenderSystem.enableDepthTest();
            }
        }
        if (ItemStackVar.getItem() == Items.CLOCK && (autoReissue = ModuleRegistry.AUTO_REISSUE) != null && autoReissue.k()) {
            MinecraftClient MinecraftClientVarGetInstance = MinecraftClient.getInstance();
            if (MinecraftClientVarGetInstance.currentScreen != null && MinecraftClientVarGetInstance.player.currentScreenHandler != null) {
                try {
                    MinecraftClientVarGetInstance.currentScreen.getTitle().getString();
                } catch (Exception e) {
                }
                if (autoReissue.overlayActive) {
                    long j = autoReissue.timer.a(0L) ? autoReissue.durationMs : 0L;
                    int i5 = 1;
                    while (true) {
                        if (i5 > (autoReissue.durationMs / 1000) + 1) {
                            break;
                        }
                        if (!autoReissue.timer.a(i5 * 1000)) {
                            j = (i5 - 1) * 1000;
                            break;
                        }
                        i5++;
                    }
                    int iMax = Math.max(0, (autoReissue.durationMs - ((int) j)) / 1000);
                    float fMax = Math.max(0.0f, 1.0f - (j / autoReissue.durationMs));
                    if (iMax > 0) {
                        int iMethod_153862 = MathHelper.ceil(16 * fMax);
                        int i6 = i2 + (16 - iMethod_153862);
                        fill(RenderLayer.getGui(), i, i6, i + 16, i6 + iMethod_153862, EventPriority.HIGHEST, new Color(255, 255, 255, 127).getRGB());
                        String strValueOf2 = String.valueOf(iMax);
                        this.matrices.push();
                        this.matrices.translate(0.0f, 0.0f, 300.0f);
                        drawText(TextRendererVar, strValueOf2, (i + 8) - (TextRendererVar.getWidth(strValueOf2) / 2), (i2 + 8) - 4, -1, true);
                        this.matrices.pop();
                    }
                }
            }
        }
        HealingHelper healingHelper = ModuleRegistry.HEALING_HELPER;
        if (healingHelper != null && healingHelper.k() && healingHelper.a(ItemStackVar)) {
            Stream<Item> stream = healingHelper.n().stream();
            Objects.requireNonNull(healingHelper);
            float fClamp = MathHelper.clamp(healingHelper.a(ItemStackVar.getItem()) / stream.mapToInt(healingHelper::a).max().orElse(1), 0.0f, 1.0f);
            int rgb = new Color((int) (255.0f * (1.0f - fClamp)), (int) (255.0f * fClamp), 0, ((int) (((Math.sin(System.currentTimeMillis() / 100.0d) * 0.5d) + 0.5d) * 100.0d)) + 50).getRGB();
            this.matrices.push();
            this.matrices.translate(0.0f, 0.0f, 200.0f);
            fill(RenderLayer.getGuiOverlay(), i, i2, i + 16, i2 + 16, rgb);
            this.matrices.pop();
        }
        EventBusService.EVENT_BUS.post(new ItemOverlayRenderEvent(ItemStackVar, i, i2));
        this.matrices.pop();
        callbackInfo.cancel();
    }
}
