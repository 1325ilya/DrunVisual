package ru.drunvisual.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.item.ItemRenderer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import drunvisual.modules.utilities.AutoReissue;
import drunvisual.inventory.CooldownInfo;
import drunvisual.modules.utilities.Cooldowns;
import drunvisual.events.EventBusService;
import drunvisual.modules.utilities.HealingHelper;
import drunvisual.modules.utilities.ItemHighlighter;
import drunvisual.events.ItemOverlayRenderEvent;
import drunvisual.module.ModuleRegistry;

@Mixin({ItemRenderer.class})
public abstract class ItemRendererMixin {
    public void renderGuiItemOverlay(DrawContext DrawContextVar, TextRenderer TextRendererVar, ItemStack ItemStackVar, int i, int i2) {
        renderGuiItemOverlay(DrawContextVar, TextRendererVar, ItemStackVar, i, i2, null);
    }

    public void renderGuiItemOverlay(DrawContext DrawContextVar, TextRenderer TextRendererVar, ItemStack ItemStackVar, int i, int i2, @Nullable String str) {
        AutoReissue autoReissue;
        if (ItemStackVar.isEmpty()) {
            return;
        }
        Item ItemVarGetItem = ItemStackVar.getItem();
        ItemHighlighter itemHighlighter = ModuleRegistry.ITEM_HIGHLIGHTER;
        if (itemHighlighter != null && itemHighlighter.k() && itemHighlighter.a(ItemStackVar)) {
            Color colorB = itemHighlighter.b(ItemStackVar);
            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            renderGuiQuad(Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR), i, i2, 16, 16, colorB.getRed(), colorB.getGreen(), colorB.getBlue(), itemHighlighter.n());
            RenderSystem.enableDepthTest();
        }
        if (ItemStackVar.getCount() != 1 || str != null) {
            String strValueOf = str == null ? String.valueOf(ItemStackVar.getCount()) : str;
            DrawContextVar.drawText(TextRendererVar, strValueOf, ((i + 19) - 2) - TextRendererVar.getWidth(strValueOf), i2 + 6 + 3, 16777215, true);
        }
        if (ItemStackVar.isDamaged()) {
            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            BufferBuilder BufferBuilderVarBegin = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
            float fGetDamage = ItemStackVar.getDamage();
            float fGetMaxDamage = ItemStackVar.getMaxDamage();
            int iRound = Math.round(13.0f - ((fGetDamage * 13.0f) / fGetMaxDamage));
            int iHsvToRgb = MathHelper.hsvToRgb(Math.max(0.0f, (fGetMaxDamage - fGetDamage) / fGetMaxDamage) / 3.0f, 1.0f, 1.0f);
            renderGuiQuad(BufferBuilderVarBegin, i + 2, i2 + 13, 13, 2, 0, 0, 0, 255);
            renderGuiQuad(BufferBuilderVarBegin, i + 2, i2 + 13, iRound, 1, (iHsvToRgb >> 16) & 255, (iHsvToRgb >> 8) & 255, iHsvToRgb & 255, 255);
            RenderSystem.enableDepthTest();
        }
        ClientPlayerEntity ClientPlayerEntityVar = MinecraftClient.getInstance().player;
        float fC = 0.0f;
        if (ClientPlayerEntityVar != null) {
            fC = CooldownInfo.a(ClientPlayerEntityVar.getItemCooldownManager(), ItemVarGetItem).c();
        }
        Cooldowns cooldowns = ModuleRegistry.COOLDOWNS;
        if (cooldowns != null && cooldowns.k()) {
            float fA = cooldowns.a(ItemVarGetItem);
            if (fA > fC) {
                fC = fA;
            }
        }
        if (fC > 0.0f) {
            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            renderGuiQuad(Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR), i, i2 + MathHelper.floor(16.0f * (1.0f - fC)), 16, MathHelper.ceil(16.0f * fC), 255, 255, 255, 127);
            RenderSystem.enableDepthTest();
        }
        HealingHelper healingHelper = ModuleRegistry.HEALING_HELPER;
        if (healingHelper != null && healingHelper.k() && healingHelper.a(ItemStackVar)) {
            Stream<Item> stream = healingHelper.n().stream();
            Objects.requireNonNull(healingHelper);
            int[] colorByPriority = getColorByPriority(healingHelper.a(ItemVarGetItem), stream.mapToInt(healingHelper::a).max().orElse(1));
            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            renderGuiQuad(Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR), i, i2, 16, 16, colorByPriority[0], colorByPriority[1], colorByPriority[2], ((int) (((Math.sin(System.currentTimeMillis() / 100.0d) * 0.5d) + 0.5d) * 100.0d)) + 50);
            RenderSystem.enableDepthTest();
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
                    int i3 = 1;
                    while (true) {
                        if (i3 > (autoReissue.durationMs / 1000) + 1) {
                            break;
                        }
                        if (!autoReissue.timer.a(i3 * 1000)) {
                            j = (i3 - 1) * 1000;
                            break;
                        }
                        i3++;
                    }
                    int iMax = Math.max(0, (autoReissue.durationMs - ((int) j)) / 1000);
                    float fMax = Math.max(0.0f, 1.0f - (j / autoReissue.durationMs));
                    if (iMax > 0) {
                        RenderSystem.disableDepthTest();
                        RenderSystem.enableBlend();
                        RenderSystem.defaultBlendFunc();
                        BufferBuilder BufferBuilderVarMethod_608272 = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
                        int iCeil = MathHelper.ceil(16 * fMax);
                        renderGuiQuad(BufferBuilderVarMethod_608272, i, i2 + (16 - iCeil), 16, iCeil, 255, 255, 255, 127);
                        RenderSystem.enableDepthTest();
                        String iMaxStr = String.valueOf(iMax);
                        DrawContextVar.drawText(TextRendererVar, iMaxStr, (int) ((i + 8) - (TextRendererVar.getWidth(iMaxStr) / 2.0f)), (int) ((i2 + 8) - 4.5f), 16777215, true);
                    }
                }
            }
        }
        EventBusService.EVENT_BUS.post(new ItemOverlayRenderEvent(ItemStackVar, i, i2));
    }

    private void renderGuiQuad(BufferBuilder BufferBuilderVar, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        BufferBuilderVar.vertex(i + 0, i2 + 0, 0.0f).color(i5, i6, i7, i8);
        BufferBuilderVar.vertex(i + 0, i2 + i4, 0.0f).color(i5, i6, i7, i8);
        BufferBuilderVar.vertex(i + i3, i2 + i4, 0.0f).color(i5, i6, i7, i8);
        BufferBuilderVar.vertex(i + i3, i2 + 0, 0.0f).color(i5, i6, i7, i8);
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVar.end());
    }

    private int[] getColorByPriority(int i, int i2) {
        float f = i / i2;
        return new int[]{(int) (255.0f * (1.0f - f)), (int) (255.0f * f), 0};
    }
}
