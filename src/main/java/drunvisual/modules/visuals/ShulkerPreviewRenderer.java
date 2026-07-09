package drunvisual.modules.visuals;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.ItemEntity;
import net.minecraft.screen.slot.Slot;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.component.DataComponentTypes;
import drunvisual.module.ModuleRegistry;
import ru.drunvisual.DrunVisual;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;

public final class ShulkerPreviewRenderer {
    private static final float SLOT = 18.0f;
    private static final float PADDING = 6.0f;
    private static final int COLS = 9;
    private static final int ROWS = 3;

    private ShulkerPreviewRenderer() {
    }

    public static void renderInventoryHover(Slot SlotVar, DrawContext DrawContextVar, int i, int i2) {
        renderInventoryHover(SlotVar, DrawContextVar, i, i2, false);
    }

    public static void renderInventoryHover(Slot SlotVar, DrawContext DrawContextVar, int i, int i2, boolean z) {
        ShulkerPreview shulkerPreview = ModuleRegistry.SHULKER_PREVIEW;
        if (shulkerPreview == null || !shulkerPreview.k() || !shulkerPreview.inventoryPreview.a() || SlotVar == null) {
            return;
        }
        ItemStack ItemStackVarGetStack = SlotVar.getStack();
        if (isShulkerBox(ItemStackVarGetStack)) {
            List<ItemStack> contents = readContents(ItemStackVarGetStack);
            float f = 174.0f * 1.0f;
            float f2 = 82.0f * 1.0f;
            float f3 = i;
            float f4 = i2;
            if (!z) {
                f3 = i + 14;
                f4 = i2 + 6;
                MinecraftClient MinecraftClientVarGetInstance = MinecraftClient.getInstance();
                if (f3 + f > MinecraftClientVarGetInstance.getWindow().getScaledWidth()) {
                    f3 = (i - f) - 4.0f;
                }
                if (f4 + f2 > MinecraftClientVarGetInstance.getWindow().getScaledHeight()) {
                    f4 = (i2 - f2) - 4.0f;
                }
            }
            renderPanel(DrawContextVar, f3, f4, f, f2, contents, ItemStackVarGetStack, 1.0f);
        }
    }

    public static void renderWorldTarget(MinecraftClient MinecraftClientVar, MatrixStack MatrixStackVar, Renderer2D renderer2D) {
        ShulkerPreview shulkerPreview = ModuleRegistry.SHULKER_PREVIEW;
        if (shulkerPreview == null || !shulkerPreview.k() || !shulkerPreview.worldPreview.a() || MinecraftClientVar.player == null) {
            return;
        }
        if (MinecraftClientVar.crosshairTarget instanceof EntityHitResult) {
            EntityHitResult EntityHitResultVar = (EntityHitResult) MinecraftClientVar.crosshairTarget;
            net.minecraft.entity.Entity ItemEntityVarGetEntity = EntityHitResultVar.getEntity();
            if (ItemEntityVarGetEntity instanceof ItemEntity) {
                ItemEntity ItemEntityVar = (ItemEntity) ItemEntityVarGetEntity;
                ItemStack ItemStackVarGetStack = ItemEntityVar.getStack();
                if (isShulkerBox(ItemStackVarGetStack) && MinecraftClientVar.player.squaredDistanceTo(ItemEntityVar) <= 36.0d) {
                    float f = 174.0f * 1.0f;
                    renderPanel(new DrawContext(MinecraftClientVar, MinecraftClientVar.getBufferBuilders().getEntityVertexConsumers()), (MinecraftClientVar.getWindow().getScaledWidth() / 2.0f) - (f / 2.0f), (MinecraftClientVar.getWindow().getScaledHeight() / 2.0f) + 20.0f, f, 82.0f * 1.0f, readContents(ItemStackVarGetStack), ItemStackVarGetStack, 1.0f);
                }
            }
        }
    }

    private static void renderPanel(DrawContext DrawContextVar, float f, float f2, float f3, float f4, List<ItemStack> list, ItemStack ItemStackVar, float f5) {
        MatrixStack MatrixStackVarGetMatrices = DrawContextVar.getMatrices();
        Renderer2D render = DrunVisual.getInstance().getRender();
        if (render == null) {
            return;
        }
        render.a(f, f2, f3, f4, 7.0f * f5, new Color(12, 12, 22, 220), MatrixStackVarGetMatrices);
        FontManager.a[Math.max(6, Math.min(14, Math.round(10.0f * f5)))].a(ItemStackVar.get(DataComponentTypes.CUSTOM_NAME) != null ? ItemStackVar.getName().getString() : "Shulker Box", f + (PADDING * f5), f2 + (4.0f * f5), Color.WHITE, MatrixStackVarGetMatrices);
        float f6 = f + (PADDING * f5);
        float f7 = f2 + (16.0f * f5);
        float f8 = 18.0f * f5;
        for (int i = 0; i < 27; i++) {
            float f9 = f6 + ((i % COLS) * f8);
            float f10 = f7 + ((i / COLS) * f8);
            render.a(f9, f10, f8 - 1.0f, f8 - 1.0f, 3.0f * f5, new Color(24, 24, 36, 180), MatrixStackVarGetMatrices);
            if (i < list.size() && !list.get(i).isEmpty()) {
                drawItem(MatrixStackVarGetMatrices, DrawContextVar, list.get(i), f9 + (1.0f * f5), f10 + (1.0f * f5), f8 - (2.0f * f5));
            }
        }
    }

    private static void drawItem(MatrixStack MatrixStackVar, DrawContext DrawContextVar, ItemStack ItemStackVar, float f, float f2, float f3) {
        if (ItemStackVar.isEmpty()) {
            return;
        }
        MatrixStack MatrixStackVarGetMatrices = DrawContextVar.getMatrices();
        float f4 = f3 / 16.0f;
        MatrixStackVarGetMatrices.push();
        MatrixStackVarGetMatrices.translate(f, f2, 0.0f);
        MatrixStackVarGetMatrices.scale(f4, f4, 1.0f);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        DrawContextVar.drawItem(ItemStackVar, 0, 0);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();
        if (ItemStackVar.getCount() > 1) {
            TextRenderer TextRendererVar = MinecraftClient.getInstance().textRenderer;
            String strValueOf = String.valueOf(ItemStackVar.getCount());
            MatrixStackVarGetMatrices.push();
            MatrixStackVarGetMatrices.translate(0.0f, 0.0f, 200.0f);
            MatrixStackVarGetMatrices.scale(0.5f, 0.5f, 1.0f);
            DrawContextVar.drawTextWithShadow(TextRendererVar, Text.literal(strValueOf), (int) ((16.0f - (TextRendererVar.getWidth(strValueOf) * 0.5f)) / 0.5f), (int) (11.0f / 0.5f), 16777215);
            MatrixStackVarGetMatrices.pop();
        }
        MatrixStackVarGetMatrices.pop();
    }

    public static boolean isShulkerBox(ItemStack ItemStackVar) {
        if (!ItemStackVar.isEmpty()) {
            net.minecraft.item.Item BlockItemVarGetItem = ItemStackVar.getItem();
            if ((BlockItemVarGetItem instanceof BlockItem) && (((BlockItem) BlockItemVarGetItem).getBlock() instanceof ShulkerBoxBlock)) {
                return true;
            }
        }
        return false;
    }

    private static List<ItemStack> readContents(ItemStack ItemStackVar) {
        ArrayList arrayList = new ArrayList();
        ContainerComponent ContainerComponentVar = (ContainerComponent) ItemStackVar.get(DataComponentTypes.CONTAINER);
        if (ContainerComponentVar == null) {
            return arrayList;
        }
        for (int i = 0; i < 27; i++) {
            arrayList.add(ItemStack.EMPTY);
        }
        int i2 = 0;
        for (ItemStack ItemStackVar2 : ContainerComponentVar.iterateNonEmpty()) {
            if (i2 < 27) {
                int i3 = i2;
                i2++;
                arrayList.set(i3, ItemStackVar2.copy());
            }
        }
        return arrayList;
    }
}
