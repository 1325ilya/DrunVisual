package drunvisual.hud.core;

import java.awt.Color;
import net.minecraft.util.Identifier;
import net.minecraft.client.util.math.MatrixStack;
import ru.drunvisual.DrunVisual;
import drunvisual.render.Renderer2D;
import drunvisual.render.icons.IconTextureRegistry;

public final class HudIcons {
    private static boolean loggedWatermarkIcon = false;

    private HudIcons() {
    }

    public static Identifier clickGui(String str) {
        Identifier IdentifierVar = IconTextureRegistry.get(str);
        return IdentifierVar != null ? IdentifierVar : Identifier.of(DrunVisual.CLIENT_NAME, "textures/clickgui/" + str);
    }

    public static void draw(Renderer2D renderer2D, MatrixStack MatrixStackVar, Identifier IdentifierVar, float f, float f2, float f3, Color color) {
        if (renderer2D == null || IdentifierVar == null || color.getAlpha() < 1) {
            return;
        }
        renderer2D.a(IdentifierVar, f, f2, f3, f3, color, MatrixStackVar);
    }

    public static void drawFlippedVertical(Renderer2D renderer2D, MatrixStack MatrixStackVar, Identifier IdentifierVar, float f, float f2, float f3, Color color) {
        if (renderer2D == null || IdentifierVar == null || color.getAlpha() < 1) {
            return;
        }
        float cy = f2 + f3 / 2.0f;
        MatrixStackVar.push();
        MatrixStackVar.translate(0.0f, cy, 0.0f);
        MatrixStackVar.scale(1.0f, -1.0f, 1.0f);
        MatrixStackVar.translate(0.0f, -cy, 0.0f);
        renderer2D.a(IdentifierVar, f, f2, f3, f3, color, MatrixStackVar);
        MatrixStackVar.pop();
    }

    public static void drawRotated180(Renderer2D renderer2D, MatrixStack MatrixStackVar, Identifier IdentifierVar, float f, float f2, float f3, Color color) {
        if (renderer2D == null || IdentifierVar == null || color.getAlpha() < 1) {
            return;
        }
        float cx = f + f3 / 2.0f;
        float cy = f2 + f3 / 2.0f;
        MatrixStackVar.push();
        MatrixStackVar.translate(cx, cy, 0.0f);
        MatrixStackVar.scale(-1.0f, -1.0f, 1.0f);
        MatrixStackVar.translate(-cx, -cy, 0.0f);
        renderer2D.a(IdentifierVar, f, f2, f3, f3, color, MatrixStackVar);
        MatrixStackVar.pop();
    }

    public static void drawPulseMark(Renderer2D renderer2D, MatrixStack MatrixStackVar, float f, float f2, float f3, Color color) {
        Identifier IdentifierVar = IconTextureRegistry.get(IconTextureRegistry.DRUNVISUAL_ICO);
        if (IdentifierVar == null) {
            IdentifierVar = IconTextureRegistry.get(IconTextureRegistry.LOGO);
        }
        if (IdentifierVar == null) {
            return;
        }
        if (color.getAlpha() < 1) {
            return;
        }
        renderer2D.a(IdentifierVar, f, f2, f3, f3, 0.0f, 1.0f, 1.0f, -1.0f, -1.0f, color, MatrixStackVar);
    }

    public static void drawWatermarkIcon(Renderer2D renderer2D, MatrixStack MatrixStackVar, float f, float f2, float f3, Color color) {
        Identifier IdentifierVar = IconTextureRegistry.get(IconTextureRegistry.LOGO);
        if (IdentifierVar == null) {
            return;
        }
        if (!loggedWatermarkIcon) {
            loggedWatermarkIcon = true;
            DrunVisual.getLOGGER().info("[DrunVisualIcons] drawWatermarkIcon -> identifier={} size={} tint=rgba({},{},{},{})", IdentifierVar, Float.valueOf(f3), Integer.valueOf(color.getRed()), Integer.valueOf(color.getGreen()), Integer.valueOf(color.getBlue()), Integer.valueOf(color.getAlpha()));
        }
        draw(renderer2D, MatrixStackVar, IdentifierVar, f, f2, f3, color);
    }
}
