package drunvisual.hud.elements;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.Identifier;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import drunvisual.module.ModuleRegistry;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.hud.core.HudElement;
import drunvisual.hud.core.HudServiceRegistry;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;

public class TargetHudElement extends HudElement {
    private static final float INFO_WIDTH = 134.0f;
    private static final float INFO_HEIGHT = 38.0f;
    private static final float GEAR_WIDTH = 134.0f;
    private static final float GEAR_HEIGHT = 22.0f;
    private static final float GEAR_GAP = 4.0f;
    private static final float AVATAR_SIZE = 23.0f;
    private static final float CARD_RADIUS = 7.0f;
    private static final float AVATAR_RADIUS = 4.0f;
    private static final float HP_BAR_HEIGHT = 2.4f;
    private static final float ITEM_SIZE = 14.0f;
    private static final float DURABILITY_HEIGHT = 1.4f;
    private static final Color SHADOW = new Color(0, 0, 0, 96);
    private static final Color CARD_TOP = new Color(15, 15, 26, 232);
    private static final Color CARD_BOTTOM = new Color(9, 9, 18, 236);
    private static final Color GEAR_CARD = new Color(12, 12, 22, 228);
    private static final Color AVATAR_BACK_TOP = new Color(30, 28, 42, 245);
    private static final Color AVATAR_BACK_BOTTOM = new Color(18, 17, 28, 245);
    private static final Color BAR_BACK = new Color(22, 20, 34, 230);
    private static final Color BAR_GLOW = new Color(125, 82, 255, 150);
    private static final Color TEXT_SECONDARY = new Color(230, 230, 238, 238);
    private static final Color BADGE_TEXT = new Color(185, 185, 198, 220);
    private final AnimationState visibilityAnimation;
    private final AnimationState healthAnimation;
    private final AnimationState switchAnimation;
    private LivingEntity target;
    private String targetName;
    private float targetHealth;
    private float targetMaxHealth;
    private Identifier skinTexture;
    private Identifier lastTexture;
    private long textureChangedAtMs;
    private boolean visible;
    private boolean settingsBound;

    public TargetHudElement(float f, float f2) {
        super(f, f2);
        this.visibilityAnimation = new AnimationState();
        this.healthAnimation = new AnimationState();
        this.switchAnimation = new AnimationState();
        this.targetName = "Target";
        this.targetMaxHealth = 20.0f;
        this.visibilityAnimation.d(0.0d);
        this.healthAnimation.d(1.0d);
        this.switchAnimation.d(1.0d);
        this.d = 134.0f;
        this.e = 64.0f;
    }

    @Override
    protected void a() {
        bindSettings();
        float fG = g();
        this.d = 134.0f * fG;
        this.e = 64.0f * fG;
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2) {
        if (this.a.player == null) {
            return;
        }
        a();
        updateTarget();
        float fJ = (float) this.visibilityAnimation.j();
        if (fJ < 0.01f) {
            return;
        }
        float fG = g();
        float f3 = this.b;
        float f4 = this.c;
        float f5 = 134.0f * fG;
        float f6 = 38.0f * fG;
        float f7 = 134.0f * fG;
        float f8 = GEAR_HEIGHT * fG;
        float f9 = 4.0f * fG;
        float f10 = 7.0f * fG;
        this.d = f5;
        this.e = f6 + f9 + f8;
        renderer2D.a(f3, f4 + (1.5f * fG), f5, f6, f10, withAlpha(SHADOW, fJ), MatrixStackVar);
        renderer2D.a(f3, f4, f5, f6, f10, withAlpha(CARD_TOP, fJ), withAlpha(CARD_TOP, fJ), withAlpha(CARD_BOTTOM, fJ), withAlpha(CARD_BOTTOM, fJ), MatrixStackVar);
        drawAvatar(MatrixStackVar, renderer2D, f3 + (7.0f * fG), f4 + (6.0f * fG), fJ, fG);
        drawText(MatrixStackVar, renderer2D, f3, f4, f5, fJ, fG);
        drawHealthBar(MatrixStackVar, renderer2D, f3, f4, f5, fJ, fG);
        drawEquipmentPanel(MatrixStackVar, renderer2D, f3, f4 + f6 + f9, f7, f8, fJ, fG);
    }

    private void bindSettings() {
        if (this.settingsBound || ModuleRegistry.TARGET_HUD == null) {
            return;
        }
        f().a(ModuleRegistry.TARGET_HUD);
        this.settingsBound = true;
    }

    private void updateTarget() {
        LivingEntity LivingEntityVarCurrentTarget = HudServiceRegistry.TARGETS.currentTarget();
        if (LivingEntityVarCurrentTarget == null) {
            LivingEntityVarCurrentTarget = targetFromCrosshair();
        }
        if (LivingEntityVarCurrentTarget == null && (this.a.currentScreen instanceof ChatScreen) && this.a.player != null) {
            LivingEntityVarCurrentTarget = this.a.player;
        }
        boolean z = this.visible;
        this.visible = LivingEntityVarCurrentTarget != null;
        if (LivingEntityVarCurrentTarget != null) {
            if (LivingEntityVarCurrentTarget != this.target) {
                this.target = LivingEntityVarCurrentTarget;
                this.switchAnimation.d(0.0d);
                this.switchAnimation.a(1.0d, 0.2d, Easing.h);
                this.targetName = LivingEntityVarCurrentTarget.getName().getString();
                this.skinTexture = LivingEntityVarCurrentTarget instanceof PlayerEntity ? skinFor((PlayerEntity) LivingEntityVarCurrentTarget) : DefaultSkinHelper.getTexture();
            }
            this.targetHealth = Math.max(0.0f, LivingEntityVarCurrentTarget.getHealth());
            this.targetMaxHealth = Math.max(1.0f, LivingEntityVarCurrentTarget.getMaxHealth());
            if (LivingEntityVarCurrentTarget instanceof PlayerEntity) {
                PlayerEntity PlayerEntityVar = (PlayerEntity) LivingEntityVarCurrentTarget;
                this.targetName = PlayerEntityVar.getGameProfile().getName();
                this.skinTexture = skinFor(PlayerEntityVar);
            }
        } else {
            this.target = null;
        }
        if (this.visible && !z) {
            this.visibilityAnimation.a(1.0d, 0.2d, Easing.h);
        } else if (!this.visible && z) {
            this.visibilityAnimation.a(0.0d, 0.2d, Easing.h);
        } else if (this.visible && this.visibilityAnimation.i() < 1.0d) {
            this.visibilityAnimation.a(1.0d, 0.2d, Easing.h);
        }
        float fMax = Math.max(0.0f, Math.min(1.0f, this.targetHealth / this.targetMaxHealth));
        if (Math.abs(this.healthAnimation.i() - ((double) fMax)) > 0.009999999776482582d) {
            this.healthAnimation.a(fMax, 0.15d, Easing.h);
        }
        this.visibilityAnimation.a();
        this.healthAnimation.a();
        this.switchAnimation.a();
    }

    private void drawAvatar(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, float f3, float f4) {
        float f5 = AVATAR_SIZE * f4;
        float f6 = 4.0f * f4;
        renderer2D.a(f - (0.5f * f4), f2 - (0.5f * f4), f5 + f4, f5 + f4, f6, withAlpha(AVATAR_BACK_TOP, f3), withAlpha(AVATAR_BACK_TOP, f3), withAlpha(AVATAR_BACK_BOTTOM, f3), withAlpha(AVATAR_BACK_BOTTOM, f3), MatrixStackVar);
        Identifier IdentifierVarGetTexture = this.skinTexture != null ? this.skinTexture : DefaultSkinHelper.getTexture();
        if (this.lastTexture == null || !this.lastTexture.equals(IdentifierVarGetTexture)) {
            this.lastTexture = IdentifierVarGetTexture;
            this.textureChangedAtMs = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - this.textureChangedAtMs < 100) {
            renderer2D.a(f, f2, f5, f5, f6, withAlpha(new Color(35, 31, 45), f3), MatrixStackVar);
            return;
        }
        Color colorWithAlpha = withAlpha(Color.WHITE, f3);
        renderer2D.a(IdentifierVarGetTexture, f, f2, f5, f5, f6, 0.125f, 0.125f, 0.125f, 0.125f, colorWithAlpha, MatrixStackVar);
        renderer2D.a(IdentifierVarGetTexture, f, f2, f5, f5, f6, 0.625f, 0.125f, 0.125f, 0.125f, colorWithAlpha, MatrixStackVar);
        if (this.target == null || this.target.hurtTime <= 0) {
            return;
        }
        renderer2D.a(f, f2, f5, f5, f6, new Color(255, 70, 70, (int) ((this.target.hurtTime / 10.0f) * 0.5f * f3 * 155.0f)), MatrixStackVar);
    }

    private void drawText(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, float f3, float f4, float f5) {
        FontRenderer fontRenderer = FontManager.b[Math.max(6, Math.min(16, Math.round(12.0f * f5)))];
        FontRenderer fontRenderer2 = FontManager.a[Math.max(6, Math.min(15, Math.round(10.8f * f5)))];
        FontRenderer fontRenderer3 = FontManager.a[Math.max(6, Math.min(14, Math.round(9.5f * f5)))];
        float f6 = f + (40.0f * f5);
        fontRenderer.a(fontRenderer.a((this.targetName == null || this.targetName.isBlank()) ? "Target" : this.targetName, Math.max(1, (int) (f3 - (78.0f * f5))), false), f6, f2 + (7.0f * f5), withAlpha(Color.WHITE, f4), MatrixStackVar);
        fontRenderer2.a(("HP / " + String.format(Locale.ROOT, "%.1f", Float.valueOf(Math.max(0.0f, this.targetHealth)))).replace('.', ','), f6, f2 + (19.5f * f5), withAlpha(TEXT_SECONDARY, f4), MatrixStackVar);
        String str = this.target instanceof PlayerEntity ? "Player" : "Mob";
        float fA = fontRenderer3.a(str) + (ITEM_SIZE * f5);
        float badgeX = ((f + f3) - fA) - (7.0f * f5);
        float badgeY = f2 + (6.5f * f5);
        renderer2D.a(badgeX, badgeY, fA, 11.0f * f5, 3.5f * f5, withAlpha(new Color(28, 28, 40, 170), f4), MatrixStackVar);
        fontRenderer3.a(str, badgeX + (11.0f * f5), badgeY + (1.5f * f5), withAlpha(BADGE_TEXT, f4), MatrixStackVar);
    }

    private void drawHealthBar(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, float f3, float f4, float f5) {
        float f6 = f + (7.0f * f5);
        float fInfoHeight = (f2 + infoHeight(f5)) - (8.0f * f5);
        float f7 = f3 - (ITEM_SIZE * f5);
        float f8 = HP_BAR_HEIGHT * f5;
        float f9 = 1.3f * f5;
        renderer2D.a(f6, fInfoHeight, f7, f8, f9, withAlpha(BAR_BACK, f4), MatrixStackVar);
        float fMax = f7 * Math.max(0.0f, Math.min(1.0f, (float) this.healthAnimation.j()));
        if (fMax <= 0.0f) {
            return;
        }
        Color colorN = ModuleRegistry.CLIENT_COLOR.n();
        Color color = new Color(Math.max(0, colorN.getRed() - 34), Math.max(0, colorN.getGreen() - 34), Math.max(0, colorN.getBlue() - 34), colorN.getAlpha());
        renderer2D.a(f6 - (0.55f * f5), fInfoHeight - (0.55f * f5), fMax + (1.1f * f5), f8 + (1.1f * f5), 2.0f * f5, withAlpha(BAR_GLOW, f4), MatrixStackVar);
        renderer2D.a(f6, fInfoHeight, fMax, f8, f9, withAlpha(colorN, f4), withAlpha(colorN, f4), withAlpha(color, f4), withAlpha(color, f4), MatrixStackVar);
    }

    private void drawEquipmentPanel(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, float f3, float f4, float f5, float f6) {
        if (this.target == null) {
            return;
        }
        float f7 = 5.5f * f6;
        renderer2D.a(f, f2 + (1.0f * f6), f3, f4, f7, withAlpha(SHADOW, f5 * 0.7f), MatrixStackVar);
        renderer2D.a(f, f2, f3, f4, f7, withAlpha(GEAR_CARD, f5), MatrixStackVar);
        List<ItemStack> listEquipmentItems = equipmentItems();
        float f8 = 4.0f * f6;
        float size = f + ((f3 - (((listEquipmentItems.size() * ITEM_SIZE) * f6) + (Math.max(0, listEquipmentItems.size() - 1) * f8))) / 2.0f);
        float f9 = f2 + (3.5f * f6);
        DrawContext DrawContextVar = new DrawContext(this.a, this.a.getBufferBuilders().getEntityVertexConsumers());
        for (int i = 0; i < listEquipmentItems.size(); i++) {
            float f10 = size + (i * ((ITEM_SIZE * f6) + f8));
            drawItem(MatrixStackVar, DrawContextVar, listEquipmentItems.get(i), f10, f9, ITEM_SIZE * f6, f5);
            drawDurabilityBar(renderer2D, MatrixStackVar, listEquipmentItems.get(i), f10, f9 + (ITEM_SIZE * f6) + (1.0f * f6), ITEM_SIZE * f6, f5, f6);
        }
    }

    private List<ItemStack> equipmentItems() {
        ArrayList arrayList = new ArrayList(5);
        arrayList.add(this.target.getMainHandStack());
        arrayList.add(this.target.getEquippedStack(EquipmentSlot.HEAD));
        arrayList.add(this.target.getEquippedStack(EquipmentSlot.CHEST));
        arrayList.add(this.target.getEquippedStack(EquipmentSlot.LEGS));
        arrayList.add(this.target.getEquippedStack(EquipmentSlot.FEET));
        return arrayList;
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

    private void drawDurabilityBar(Renderer2D renderer2D, MatrixStack MatrixStackVar, ItemStack ItemStackVar, float f, float f2, float f3, float f4, float f5) {
        if (ItemStackVar.isEmpty() || !ItemStackVar.isDamageable()) {
            return;
        }
        float fMax = Math.max(0.0f, Math.min(1.0f, 1.0f - (ItemStackVar.getDamage() / ItemStackVar.getMaxDamage())));
        float f6 = DURABILITY_HEIGHT * f5;
        Color colorWithAlpha = withAlpha(new Color(35, 35, 48, 180), f4);
        Color colorWithAlpha2 = withAlpha(durabilityColor(fMax), f4);
        renderer2D.a(f, f2, f3, f6, 0.8f * f5, colorWithAlpha, MatrixStackVar);
        renderer2D.a(f, f2, f3 * fMax, f6, 0.8f * f5, colorWithAlpha2, MatrixStackVar);
    }

    private Color durabilityColor(float f) {
        return f > 0.6f ? new Color(72, 210, 120) : f > 0.3f ? new Color(240, 176, 64) : new Color(235, 82, 82);
    }

    private float infoHeight(float f) {
        return 38.0f * f;
    }

    private Identifier skinFor(PlayerEntity PlayerEntityVar) {
        Identifier IdentifierVarTexture;
        return (!(PlayerEntityVar instanceof AbstractClientPlayerEntity) || (IdentifierVarTexture = ((AbstractClientPlayerEntity) PlayerEntityVar).getSkinTextures().texture()) == null) ? DefaultSkinHelper.getTexture() : IdentifierVarTexture;
    }

    private LivingEntity targetFromCrosshair() {
        HitResult hitResult;
        LivingEntity LivingEntityVar;
        if (this.a.player == null || this.a.world == null || (hitResult = this.a.crosshairTarget) == null || hitResult.getType() != HitResult.Type.ENTITY || !(hitResult instanceof EntityHitResult)) {
            return null;
        }
        Entity EntityVarGetEntity = ((EntityHitResult) hitResult).getEntity();
        if (!(EntityVarGetEntity instanceof LivingEntity) || (LivingEntityVar = (LivingEntity) EntityVarGetEntity) == this.a.player || !LivingEntityVar.isAlive() || LivingEntityVar.isRemoved()) {
            return null;
        }
        return LivingEntityVar;
    }

    private static Color withAlpha(Color color, float f) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(0, Math.min(255, (int) (color.getAlpha() * f))));
    }
}
