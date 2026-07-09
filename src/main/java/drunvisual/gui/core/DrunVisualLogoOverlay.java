package drunvisual.gui.core;

import java.awt.Color;
import net.minecraft.util.Identifier;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.hud.core.RemoteWatermarkText;
import drunvisual.render.icons.IconTextureRegistry;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;

public class DrunVisualLogoOverlay implements ClickGuiOverlay {
    private static final float LOGO_TOP_PADDING = 12.0f;
    private static final float LOGO_SCALE = 0.6f;

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, int i, int i2) {
        IconTextureRegistry.TextureInfo info = IconTextureRegistry.getInfo(IconTextureRegistry.DRUNVISUAL_ICO);
        if (info == null) {
            return;
        }
        Identifier IdentifierVarA = info.a();
        float fB = info.b() * LOGO_SCALE;
        float fC = info.c() * LOGO_SCALE;
        String text = RemoteWatermarkText.getText();
        float fA = FontManager.b[20].a(text);
        float fB2 = FontManager.b[20].b(text);
        float logoX = f + ((DrunVisualClickGuiScreen.d() - ((fB + 8.0f) + fA)) / 2.0f);
        float logoY = (f2 - Math.max(fC, fB2)) - LOGO_TOP_PADDING;
        renderer2D.a(IdentifierVarA, logoX, logoY + ((Math.max(fC, fB2) - fC) / 2.0f), fB, fC, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, Color.WHITE, MatrixStackVar);
        FontManager.b[20].a(text, logoX + fB + 8.0f, logoY + ((Math.max(fC, fB2) - fB2) / 2.0f), Color.WHITE, MatrixStackVar);
    }

    @Override
    public void a(float f, float f2, int i, int i2) {
    }
}
