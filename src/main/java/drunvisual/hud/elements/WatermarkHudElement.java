package drunvisual.hud.elements;

import java.awt.Color;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.module.ModuleRegistry;
import drunvisual.hud.core.HudElement;
import drunvisual.hud.core.HudIcons;
import drunvisual.hud.core.RemoteWatermarkText;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.render.icons.IconTextureRegistry;

public class WatermarkHudElement extends HudElement {
    private static final float PAD_X = 8.5f;
    private static final float PAD_Y = 6.0f;
    private static final float ICON_BASE = 12.0f;
    private static final float ICON_GAP = 5.0f;
    private static final float RADIUS = 6.5f;
    private static final Color CARD_TOP = new Color(15, 15, 26, 232);
    private static final Color CARD_BOTTOM = new Color(9, 9, 18, 236);
    private boolean settingsBound;

    public WatermarkHudElement(float f, float f2) {
        super(f, f2);
    }

    @Override
    protected void a() {
        bindSettings();
        float fG = g();
        String text = RemoteWatermarkText.getText();
        FontRenderer fontRenderer = FontManager.b[Math.max(6, Math.min(16, Math.round(14.0f * fG)))];
        float f = ICON_BASE * fG;
        float fA = (PAD_X * fG) + f + (ICON_GAP * fG) + fontRenderer.a(text) + (PAD_X * fG);
        float fMax = (ICON_BASE * fG) + Math.max(f, fontRenderer.b(text));
        this.d = fA;
        this.e = fMax;
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2) {
        if (this.a.player == null) {
            return;
        }
        a();
        float fG = g();
        float f3 = this.b;
        float f4 = this.c;
        float f5 = this.d;
        float f6 = this.e;
        float f7 = RADIUS * fG;
        float f8 = PAD_X * fG;
        float f9 = ICON_BASE * fG;
        float f10 = ICON_GAP * fG;
        Color colorWithAlpha = withAlpha(CARD_TOP, 1.0f);
        Color colorWithAlpha2 = withAlpha(CARD_BOTTOM, 1.0f);
        renderer2D.a(f3, f4, f5, f6, f7, colorWithAlpha, colorWithAlpha, colorWithAlpha2, colorWithAlpha2, MatrixStackVar);
        float iconX = f3 + f8;
        float iconY = f4 + ((f6 - f9) / 2.0f);
        float iconCx = iconX + (f9 / 2.0f);
        float iconCy = iconY + (f9 / 2.0f);
        float gs1 = f9 * 1.1f;
        float gs2 = f9 * 1.45f;
        float gs3 = f9 * 1.85f;
        Color glowOuter = new Color(140, 95, 255, 22);
        Color glowMid = new Color(150, 100, 255, 45);
        Color glowInner = new Color(165, 115, 255, 80);
        renderer2D.a(iconCx - (gs3 / 2.0f), iconCy - (gs3 / 2.0f), gs3, gs3, gs3 / 2.0f, glowOuter, MatrixStackVar);
        renderer2D.a(iconCx - (gs2 / 2.0f), iconCy - (gs2 / 2.0f), gs2, gs2, gs2 / 2.0f, glowMid, MatrixStackVar);
        renderer2D.a(iconCx - (gs1 / 2.0f), iconCy - (gs1 / 2.0f), gs1, gs1, gs1 / 2.0f, glowInner, MatrixStackVar);
        String text = RemoteWatermarkText.getText();
        HudIcons.drawFlippedVertical(renderer2D, MatrixStackVar, IconTextureRegistry.get(IconTextureRegistry.LOGO), iconX, iconY, f9, withAlpha(Color.WHITE, 1.0f));
        FontRenderer pulseFont = FontManager.b[Math.max(6, Math.min(16, Math.round(14.0f * fG)))];
        pulseFont.a(text, iconX + f9 + f10, (f4 + (f6 / 2.0f)) - (pulseFont.b(text) / 4.0f), withAlpha(Color.WHITE, 1.0f), MatrixStackVar);
    }

    private void bindSettings() {
        if (this.settingsBound || ModuleRegistry.WATERMARK == null) {
            return;
        }
        f().a(ModuleRegistry.WATERMARK);
        this.settingsBound = true;
    }

    private static Color withAlpha(Color color, float f) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(0, Math.min(255, Math.round(color.getAlpha() * f))));
    }
}
