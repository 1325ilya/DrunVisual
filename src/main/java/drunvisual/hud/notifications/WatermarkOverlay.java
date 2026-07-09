package drunvisual.hud.notifications;

import java.awt.Color;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import drunvisual.hud.core.HudIcons;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;

public class WatermarkOverlay extends HudNotification {
    private static final String WATERMARK = "DrunVisuals";
    private static final float ICON_SIZE = 14.0f;
    private static final float GAP = 7.0f;
    private static final int FONT_SIZE = 14;
    private static final float RADIUS = 12.0f;
    private static final Color CARD_TOP = new Color(8, 8, 11, 235);
    private static final Color CARD_BOTTOM = new Color(5, 5, 8, 240);
    private static final Color ICON_TINT = new Color(255, 255, 255, 255);
    private static final Color TEXT = new Color(255, 255, 255, 255);
    private static final Color MUTED = new Color(255, 255, 255, 255);

    public WatermarkOverlay() {
        this.g = 11.0f;
        this.h = 4.0f;
    }

    @Override
    public float a() {
        return measureTextWidth();
    }

    @Override
    public float b() {
        return ICON_SIZE;
    }

    public void renderStandalone(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, float f3) {
        if (f3 < 0.01f) {
            return;
        }
        float fC = c();
        float fD = d();
        Color colorA = a(CARD_TOP, f3);
        Color colorA2 = a(CARD_BOTTOM, f3);
        renderer2D.a(f, f2, fC, fD, RADIUS, colorA, colorA, colorA2, colorA2, MatrixStackVar);
        a(MatrixStackVar, renderer2D, f + this.g, f2 + this.h, a(), b(), f3);
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, float f3, float f4, float f5) {
        if (f5 < 0.01f) {
            return;
        }
        float f6 = f2 + (f4 / 2.0f);
        HudIcons.drawWatermarkIcon(renderer2D, MatrixStackVar, f, f6 - (ICON_SIZE / 2.0f), ICON_SIZE, a(ICON_TINT, f5));
        drawSegments(MatrixStackVar, f + ICON_SIZE + 7.0f, f6, f5);
    }

    private void drawSegments(MatrixStack MatrixStackVar, float f, float f2, float f3) {
        FontRenderer fontRenderer = FontManager.MEDIUM[FONT_SIZE];
        float fB = f2 - (fontRenderer.b(WATERMARK) / 4.0f);
        drawPart(fontRenderer, getFps() + " FPS", drawPart(fontRenderer, " / ", drawPart(fontRenderer, getPingMs() + " ms", drawPart(fontRenderer, " / ", drawPart(fontRenderer, WATERMARK, f, fB, TEXT, f3, MatrixStackVar), fB, MUTED, f3, MatrixStackVar), fB, TEXT, f3, MatrixStackVar), fB, MUTED, f3, MatrixStackVar), fB, TEXT, f3, MatrixStackVar);
    }

    private float drawPart(FontRenderer fontRenderer, String str, float f, float f2, Color color, float f3, MatrixStack MatrixStackVar) {
        fontRenderer.a(str, f, f2, a(color, f3), MatrixStackVar);
        return f + fontRenderer.a(str);
    }

    private float measureTextWidth() {
        FontRenderer fontRenderer = FontManager.MEDIUM[FONT_SIZE];
        return 21.0f + fontRenderer.a(WATERMARK) + fontRenderer.a(" / ") + fontRenderer.a(getPingMs() + " ms") + fontRenderer.a(" / ") + fontRenderer.a(getFps() + " FPS");
    }

    private int getFps() {
        return Math.max(0, c.getCurrentFps());
    }

    private int getPingMs() {
        ClientPlayNetworkHandler ClientPlayNetworkHandlerVarGetNetworkHandler;
        PlayerListEntry PlayerListEntryVarGetPlayerListEntry;
        if (c.player == null || (ClientPlayNetworkHandlerVarGetNetworkHandler = c.getNetworkHandler()) == null || (PlayerListEntryVarGetPlayerListEntry = ClientPlayNetworkHandlerVarGetNetworkHandler.getPlayerListEntry(c.player.getUuid())) == null) {
            return 0;
        }
        return PlayerListEntryVarGetPlayerListEntry.getLatency();
    }
}
