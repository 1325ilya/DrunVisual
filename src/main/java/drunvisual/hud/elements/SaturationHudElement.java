package drunvisual.hud.elements;

import java.awt.Color;
import java.util.Locale;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.module.ModuleRegistry;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.hud.core.HudElement;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;

public class SaturationHudElement extends HudElement {
    private static final float WIDTH = 118.0f;
    private static final float HEIGHT = 28.0f;
    private final AnimationState visibilityAnimation;
    private final AnimationState saturationAnimation;
    private boolean settingsBound;

    public SaturationHudElement(float f, float f2) {
        super(f, f2);
        this.visibilityAnimation = new AnimationState();
        this.saturationAnimation = new AnimationState();
        this.visibilityAnimation.d(1.0d);
        this.saturationAnimation.d(1.0d);
        this.d = WIDTH;
        this.e = 28.0f;
    }

    @Override
    protected void a() {
        bindSettings();
        float fScale = scale();
        this.d = WIDTH * fScale;
        this.e = 28.0f * fScale;
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2) {
        if (this.a.player == null) {
            return;
        }
        a();
        float fScale = scale();
        float fJ = (float) this.visibilityAnimation.j();
        float f3 = this.b;
        float f4 = this.c;
        float f5 = WIDTH * fScale;
        float f6 = 28.0f * fScale;
        float fGetFoodLevel = this.a.player.getHungerManager().getFoodLevel();
        float fGetSaturationLevel = this.a.player.getHungerManager().getSaturationLevel();
        float fMax = Math.max(0.0f, Math.min(1.0f, fGetSaturationLevel / Math.max(1.0f, fGetFoodLevel)));
        if (Math.abs(this.saturationAnimation.i() - ((double) fMax)) > 0.009999999776482582d) {
            this.saturationAnimation.a(fMax, 0.15d, Easing.h);
        }
        this.visibilityAnimation.a();
        this.saturationAnimation.a();
        Color color = new Color(12, 12, 22, 220);
        Color colorN = ModuleRegistry.CLIENT_COLOR.n();
        renderer2D.a(f3, f4, f5, f6, 6.0f * fScale, withAlpha(color, fJ), MatrixStackVar);
        FontRenderer fontRenderer = FontManager.a[Math.max(6, Math.min(14, Math.round(10.0f * fScale)))];
        FontRenderer fontRenderer2 = FontManager.b[Math.max(6, Math.min(14, Math.round(11.0f * fScale)))];
        fontRenderer.a("Saturation", f3 + (8.0f * fScale), f4 + (5.0f * fScale), withAlpha(Color.WHITE, fJ), MatrixStackVar);
        String satStr = String.format(Locale.ROOT, "%.1f", Float.valueOf(fGetSaturationLevel)).replace('.', ',');
        fontRenderer2.a(satStr, ((f3 + f5) - (8.0f * fScale)) - fontRenderer2.a(satStr), f4 + (4.5f * fScale), withAlpha(new Color(220, 220, 230), fJ), MatrixStackVar);
        float f7 = f3 + (8.0f * fScale);
        float f8 = (f4 + f6) - (9.0f * fScale);
        float f9 = f5 - (16.0f * fScale);
        float f10 = 3.0f * fScale;
        renderer2D.a(f7, f8, f9, f10, 1.2f * fScale, withAlpha(new Color(28, 28, 40), fJ), MatrixStackVar);
        float fJ2 = f9 * ((float) this.saturationAnimation.j());
        if (fJ2 > 0.0f) {
            renderer2D.a(f7, f8, fJ2, f10, 1.2f * fScale, withAlpha(colorN, fJ), MatrixStackVar);
        }
    }

    private void bindSettings() {
        if (this.settingsBound || ModuleRegistry.SATURATION_HUD == null) {
            return;
        }
        f().a(ModuleRegistry.SATURATION_HUD);
        this.settingsBound = true;
    }

    private float scale() {
        if (ModuleRegistry.SATURATION_HUD != null) {
            return ModuleRegistry.SATURATION_HUD.n().get();
        }
        return 1.0f;
    }

    private static Color withAlpha(Color color, float f) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(0, Math.min(255, (int) (color.getAlpha() * f))));
    }
}
