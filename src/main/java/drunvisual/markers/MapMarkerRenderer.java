package drunvisual.markers;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.Identifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import drunvisual.render.icons.IconTextureRegistry;
import drunvisual.client.MinecraftContext;
import ru.drunvisual.DrunVisual;
import drunvisual.events.HudRenderPreEvent;
import drunvisual.hud.core.HudService;
import drunvisual.hud.core.HudServiceInfo;
import drunvisual.hud.core.HudServiceRegistry;
import drunvisual.render.Renderer2D;
import drunvisual.render.ScreenScale;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.render.world.WorldToScreen;
import drunvisual.theme.Theme;

@HudServiceInfo
public class MapMarkerRenderer extends HudService implements MinecraftContext {
    private static final float e = 22.0f;
    private static final float f = 6.0f;
    private static final float g = 3.0f;
    private static final float h = 16.0f;
    private static final float i = 5.0f;
    private static final float j = 10.0f;
    private static final float k = 2.5f;
    private static final float l = -7.0f;
    private static final float m = 8.0f;
    private static final float n = 0.0f;
    private static final float o = 64.0f;
    private static final float p = 5.0f;
    public static int a;
    public static boolean b;

    private static float a(float f2) {
        return Math.round(f2 * 2.0f) / 2.0f;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void a(HudRenderPreEvent hudRenderPreEvent) {
        if (c.player == null || c.world == null || MarkerManager.d()) {
            return;
        }
        ScreenScale.a(2.0d);
        Renderer2D render = DrunVisual.getInstance().getRender();
        MatrixStack MatrixStackVarA = hudRenderPreEvent.a();
        ArrayList<MapMarker> arrayList = new ArrayList<>(MarkerManager.a());
        arrayList.sort(Comparator.comparingDouble((MapMarker mm) -> this.a(mm)).reversed());
        Iterator<MapMarker> it = arrayList.iterator();
        while (it.hasNext()) {
            a(MatrixStackVarA, render, it.next());
        }
        ScreenScale.a();
    }

    private double a(MapMarker mapMarker) {
        return c.player.getPos().distanceTo(new Vec3d(((double) mapMarker.b()) + 0.5d, ((double) mapMarker.c()) + 1.5d, ((double) mapMarker.d()) + 0.5d));
    }

    private boolean a(MapMarker mapMarker, MapMarkerModule mapMarkerModule) {
        int iH = mapMarkerModule.h();
        String strI = mapMarkerModule.i();
        String lowerCase = c.getCurrentServerEntry() != null ? c.getCurrentServerEntry().address.toLowerCase() : "";
        if (lowerCase.contains("crypt")) {
            if (mapMarker.l() != iH) {
                return false;
            }
            if (mapMarker.m() != null && strI != null && !mapMarker.m().equals(strI)) {
                return false;
            }
        } else if (!lowerCase.contains("crypt") && mapMarker.l() != iH) {
            return false;
        }
        return true;
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, MapMarker mapMarker) {
        if (mapMarker.j()) {
            MapMarkerModule mapMarkerModule = HudServiceRegistry.MAP_MARKER_MODULE;
            if (!mapMarkerModule.k() || !a(mapMarker, mapMarkerModule)) {
                return;
            }
        }
        Vec3d Vec3dVar = new Vec3d(((double) mapMarker.b()) + 0.5d, ((double) mapMarker.c()) + 1.5d, ((double) mapMarker.d()) + 0.5d);
        Vec3d Vec3dVarA = WorldToScreen.a(Vec3dVar);
        if (Vec3dVarA == null || !WorldToScreen.b(Vec3dVarA)) {
            return;
        }
        float fA = a((float) Vec3dVarA.x);
        float fA2 = a((float) Vec3dVarA.y);
        String strA = a(c.player.getPos().distanceTo(Vec3dVar));
        FontRenderer fontRenderer = FontManager.b[12];
        FontRenderer fontRenderer2 = FontManager.b[10];
        String strA2 = mapMarker.a();
        if (mapMarker.j() && mapMarker.r()) {
            long jP = mapMarker.p();
            if (jP > 0) {
                int i2 = (int) (jP / 1000);
                int i3 = i2 / 60;
                int i4 = i2 % 60;
                strA2 = i3 <= 0 ? strA2 + "crypt" + i4 + "crypt" : strA2 + "crypt" + i3 + "crypt" + i4 + "crypt";
            }
        }
        float fMax = Math.max(72.0f, 21.5f + Math.max(fontRenderer.a(strA2), fontRenderer2.a(strA)) + 10.0f);
        float fA3 = a(fA - (fMax / 2.0f));
        float fA4 = a(fA2 + 25.0f);
        a(MatrixStackVar, renderer2D, fA3, fA4, fMax, mapMarker);
        float fA5 = a(fA3 + g);
        a(MatrixStackVar, renderer2D, fA5, a(fA4 + g), mapMarker);
        float fA6 = a(fA5 + h + k);
        float fB = fontRenderer.b(strA2);
        float fB2 = fontRenderer2.b(strA);
        float fA7 = a(fA4 + ((e - (fB + fB2)) / 2.0f) + 8.0f);
        float fA8 = a(fA7 + fB);
        float textAreaRight = fA3 + fMax - 6.0f;
        float textAreaCenter = (fA6 + textAreaRight) / 2.0f;
        float nameX = a(textAreaCenter - (fontRenderer.a(strA2) / 2.0f));
        float coordX = a(textAreaCenter - (fontRenderer2.a(strA) / 2.0f));
        fontRenderer.a(strA2, nameX, fA7, Theme.a, MatrixStackVar);
        fontRenderer2.a(strA, coordX, fA8, Theme.a, MatrixStackVar);
        a(MatrixStackVar, renderer2D, fA, fA4 + e);
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, MapMarker mapMarker) {
        Color colorA = Theme.a(Theme.m, EventPriority.HIGHEST);
        Color colorA2 = Theme.a(Theme.n, EventPriority.HIGHEST);
        renderer2D.a(f2 - 0.5f, f3 - 0.5f, f4 + 1.0f, 23.0f, f, colorA, colorA, colorA2, colorA2, MatrixStackVar);
        Color colorA3 = Theme.a(Theme.e, 230);
        Color colorA4 = Theme.a(Theme.f, 230);
        renderer2D.a(f2, f3, f4, e, f, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, MapMarker mapMarker) {
        Color colorE = mapMarker.e();
        Color colorC = Theme.c(colorE, 150);
        Color colorB = Theme.b(colorE, 50);
        Color colorA = Theme.a(colorB, 50);
        Color colorA2 = Theme.a(colorB, 10);
        renderer2D.a(f2 - 0.5f, f3 - 0.5f, 17.0f, 17.0f, 5.0f, colorA, colorA, colorA2, colorA2, MatrixStackVar);
        renderer2D.a(f2, f3, h, h, 5.0f, colorE, colorE, colorC, colorC, MatrixStackVar);
        FontManager.e[18].a(mapMarker.f().b(), f2 + g + 0.5f, f3 + g + 0.5f, Theme.aa, MatrixStackVar);
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3) {
        IconTextureRegistry.TextureInfo info = IconTextureRegistry.getInfo("crypt");
        if (info != null) {
            Identifier IdentifierVarA = info.a();
            RenderSystem.setShaderTexture(0, IdentifierVarA);
            float fB = info.b() / 2.0f;
            float fC = info.c() / 2.0f;
            float f4 = f3 + (fB / 2.0f);
            MatrixStackVar.push();
            MatrixStackVar.translate(f2, f4, n);
            MatrixStackVar.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90.0f));
            MatrixStackVar.translate(-f2, -f4, n);
            renderer2D.a(IdentifierVarA, (f2 - (fB / 2.0f)) - 0.5f, f4 - (fC / 2.0f), fB, fC, Theme.aa, MatrixStackVar);
            MatrixStackVar.pop();
        }
    }

    private String a(double d) {
        if (d < 1.0d) {
            return "crypt";
        }
        return d >= 2.0d ? d >= 5.0d ? String.format("crypt", Double.valueOf(d)) : String.format("crypt", Double.valueOf(d)) : "crypt";
    }

    public static String b(String str, String str2, int i2, int i3, int i4, int i5) {
        return null;
    }
}
