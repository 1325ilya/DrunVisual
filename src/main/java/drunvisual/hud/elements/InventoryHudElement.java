package drunvisual.hud.elements;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import net.minecraft.item.ItemStack;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.module.ModuleRegistry;
import drunvisual.animation.AnimationState;
import drunvisual.hud.core.HudElement;
import drunvisual.render.Renderer2D;

public class InventoryHudElement extends HudElement {
    private static final float SLOT = 18.0f;
    private static final float PADDING = 6.0f;
    private static final int COLS = 9;
    private static final int ROWS = 3;
    private final AnimationState visibilityAnimation;
    private boolean settingsBound;

    public InventoryHudElement(float f, float f2) {
        super(f, f2);
        this.visibilityAnimation = new AnimationState();
        this.visibilityAnimation.d(1.0d);
        updateBounds(1.0f);
    }

    @Override
    protected void a() {
        bindSettings();
        updateBounds(scale());
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2) {
        if (this.a.player == null) {
            return;
        }
        a();
        float fScale = scale();
        float fJ = (float) this.visibilityAnimation.j();
        this.visibilityAnimation.a();
        float f3 = this.b;
        float f4 = this.c;
        renderer2D.a(f3, f4, this.d, this.e, 7.0f * fScale, withAlpha(new Color(12, 12, 22, 0), fJ), MatrixStackVar);
        float f5 = f3 + (PADDING * fScale);
        float f6 = f4 + (16.0f * fScale);
        float f7 = 18.0f * fScale;
        DrawContext DrawContextVar = new DrawContext(this.a, this.a.getBufferBuilders().getEntityVertexConsumers());
        for (int i = 0; i < ROWS; i++) {
            for (int i2 = 0; i2 < COLS; i2++) {
                ItemStack ItemStackVarGetStack = this.a.player.getInventory().getStack(COLS + (i * COLS) + i2);
                float f8 = f5 + (i2 * f7);
                float f9 = f6 + (i * f7);
                renderer2D.a(f8, f9, f7 - 1.0f, f7 - 1.0f, 3.0f * fScale, withAlpha(new Color(24, 24, 36, 0), fJ), MatrixStackVar);
                drawItem(MatrixStackVar, DrawContextVar, ItemStackVarGetStack, f8 + (1.0f * fScale), f9 + (1.0f * fScale), f7 - (2.0f * fScale), fJ);
            }
        }
    }

    private void drawItem(MatrixStack MatrixStackVar, DrawContext DrawContextVar, ItemStack ItemStackVar, float f, float f2, float f3, float f4) {
        if (ItemStackVar.isEmpty() || f4 < 0.01f) {
            return;
        }
        MatrixStack MatrixStackVarGetMatrices = DrawContextVar.getMatrices();
        MatrixStackVarGetMatrices.push();
        MatrixStackVarGetMatrices.translate(f, f2, 0.0f);
        float f5 = f3 / 16.0f;
        MatrixStackVarGetMatrices.scale(f5, f5, 1.0f);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, f4);
        DrawContextVar.drawItem(ItemStackVar, 0, 0);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();
        MatrixStackVarGetMatrices.pop();
    }

    private void bindSettings() {
        if (this.settingsBound || ModuleRegistry.INVENTORY_HUD == null) {
            return;
        }
        f().a(ModuleRegistry.INVENTORY_HUD);
        this.settingsBound = true;
    }

    private float scale() {
        if (ModuleRegistry.INVENTORY_HUD != null) {
            return ModuleRegistry.INVENTORY_HUD.n().get();
        }
        return 1.0f;
    }

    private void updateBounds(float f) {
        this.d = 174.0f * f;
        this.e = 82.0f * f;
    }

    private static Color withAlpha(Color color, float f) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(0, Math.min(255, (int) (color.getAlpha() * f))));
    }
}
