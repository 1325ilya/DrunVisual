package drunvisual.render.font;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.util.Identifier;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import ru.drunvisual.DrunVisual;
import drunvisual.render.FramebufferCapture;

public class FontRenderer {
    private static final FontRenderContext a = new FontRenderContext(new AffineTransform(), true, true);
    private final Object2ObjectMap<Identifier, ObjectList<GlyphDrawEntry>> b = new Object2ObjectOpenHashMap();
    private final Object2ObjectMap<Identifier, ObjectList<GradientGlyphDrawEntry>> c = new Object2ObjectOpenHashMap();
    private final ObjectList<FontGlyphAtlas> d = new ObjectArrayList();
    private Font e;
    private float f;

    @FunctionalInterface
    public interface GlyphPredicate {
        boolean a(int i, char c);
    }

    public void a(Font font) {
        this.e = font;
    }

    public void a(float f) {
        this.f = f;
    }

    public float a() {
        return this.f;
    }

    public Font b() {
        return this.e;
    }

    public FontRenderer(Font font, float f) {
        a(font, f);
    }

    private static void a(String str, GlyphPredicate glyphPredicate) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!glyphPredicate.a(i, str.charAt(i))) {
                return;
            }
        }
    }

    @Contract(value = "-> new", pure = true)
    @NotNull
    public static Identifier c() {
        return Identifier.of(DrunVisual.CLIENT_NAME, "temp/" + d());
    }

    private void a(Font font, float f) {
        this.f = f;
        this.e = font.deriveFont(f * 2.0f);
    }

    private static String d() {
        Random random = new Random();
        return (String) IntStream.range(0, 32).mapToObj(i -> {
            return String.valueOf((char) (97 + random.nextInt(26)));
        }).collect(Collectors.joining());
    }

    private FontGlyphAtlas a(char c, char c2) {
        FontGlyphAtlas fontGlyphAtlas = new FontGlyphAtlas(c, c2, this.e, c(), 5);
        this.d.add(fontGlyphAtlas);
        return fontGlyphAtlas;
    }

    private FontGlyph b(char c) {
        ObjectListIterator it = this.d.iterator();
        while (it.hasNext()) {
            FontGlyphAtlas fontGlyphAtlas = (FontGlyphAtlas) it.next();
            if (fontGlyphAtlas.b(c)) {
                return fontGlyphAtlas.a(c);
            }
        }
        char cA = (char) a((int) c);
        return a(cA, (char) (cA + 256)).a(c);
    }

    private static int a(int i) {
        return 256 * ((int) Math.floor(((double) i) / 256.0d));
    }

    public void a(String str, double d, double d2, MatrixStack MatrixStackVar) {
        a(str, d, d2, Color.WHITE, MatrixStackVar);
    }

    public void a(String str, double d, double d2, Color color, MatrixStack MatrixStackVar) {
        a(MatrixStackVar, str, d, d2, color.getRGB());
    }

    public void a(String str, double d, double d2, Color color, Color color2, MatrixStack MatrixStackVar) {
        a(MatrixStackVar, str, d, d2, color.getRGB(), color2.getRGB(), b(str));
    }

    public void b(String str, double d, double d2, Color color, MatrixStack MatrixStackVar) {
        a(str, d - (((double) a(str)) / 2.0d), d2, color, MatrixStackVar);
    }

    public void c(String str, double d, double d2, Color color, MatrixStack MatrixStackVar) {
        a(str, d, d2 - (((double) b(str)) / 2.0d), color, MatrixStackVar);
    }

    public void a(MatrixStack MatrixStackVar, String str, double d, double d2, int i) {
        MatrixStackVar.push();
        MatrixStackVar.translate(Math.round(d * 2.0d) / 2.0d, Math.round(d2 * 2.0d) / 2.0d, 0.0d);
        MatrixStackVar.scale(0.5f, 0.5f, 0.5f);
        RenderSystem.enableBlend();
        FramebufferCapture.applyBlendState();
        Matrix4f matrix4fGetPositionMatrix = MatrixStackVar.peek().getPositionMatrix();
        char[] charArray = str.toCharArray();
        float[] fArrC = c(str);
        float fB = 0.0f;
        int i2 = 0;
        for (int i3 = 0; i3 < charArray.length; i3++) {
            char c = charArray[i3];
            if (c == '\n') {
                fB += b(str.substring(i2, i3)) - 2.0f;
                i2 = i3 + 1;
                if (i2 < charArray.length) {
                    fArrC = c(str.substring(i2));
                }
            } else {
                FontGlyph fontGlyphB = b(c);
                if (fontGlyphB != null && fontGlyphB.e() != ' ') {
                    ((ObjectList) this.b.computeIfAbsent(fontGlyphB.f().a, obj -> {
                        return new ObjectArrayList();
                    })).add(new GlyphDrawEntry(fArrC[i3 - i2], fB, i, fontGlyphB));
                }
            }
        }
        a(matrix4fGetPositionMatrix);
        RenderSystem.disableBlend();
        this.b.clear();
        MatrixStackVar.pop();
    }

    public void a(MatrixStack MatrixStackVar, String str, double d, double d2, int i, int i2, float f) {
        MatrixStackVar.push();
        MatrixStackVar.translate(Math.round(d * 2.0d) / 2.0d, Math.round(d2 * 2.0d) / 2.0d, 0.0d);
        MatrixStackVar.scale(0.5f, 0.5f, 0.5f);
        RenderSystem.enableBlend();
        FramebufferCapture.applyBlendState();
        Matrix4f matrix4fGetPositionMatrix = MatrixStackVar.peek().getPositionMatrix();
        char[] charArray = str.toCharArray();
        float[] fArrC = c(str);
        float fB = 0.0f;
        int i3 = 0;
        for (int i4 = 0; i4 < charArray.length; i4++) {
            char c = charArray[i4];
            if (c == '\n') {
                fB += b(str.substring(i3, i4)) - 2.0f;
                i3 = i4 + 1;
                if (i3 < charArray.length) {
                    fArrC = c(str.substring(i3));
                }
            } else {
                FontGlyph fontGlyphB = b(c);
                if (fontGlyphB != null && fontGlyphB.e() != ' ') {
                    ((ObjectList) this.c.computeIfAbsent(fontGlyphB.f().a, obj -> {
                        return new ObjectArrayList();
                    })).add(new GradientGlyphDrawEntry(fArrC[i4 - i3], fB, i, i2, fontGlyphB, f));
                }
            }
        }
        b(matrix4fGetPositionMatrix);
        RenderSystem.disableBlend();
        this.c.clear();
        MatrixStackVar.pop();
    }

    private float[] c(String str) {
        if (str.isEmpty()) {
            return new float[0];
        }
        String str2 = str.split("\n")[0];
        float[] fArr = new float[str2.length()];
        GlyphVector glyphVectorCreateGlyphVector = this.e.createGlyphVector(a, str2);
        fArr[0] = 0.0f;
        float fRound = 0.0f;
        for (int i = 1; i < str2.length(); i++) {
            fRound += Math.round(((float) glyphVectorCreateGlyphVector.getGlyphPosition(i).getX()) - ((float) glyphVectorCreateGlyphVector.getGlyphPosition(i - 1).getX()));
            fArr[i] = fRound;
        }
        return fArr;
    }

    private void a(Matrix4f matrix4f) {
        RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
        ObjectIterator it = this.b.keySet().iterator();
        while (it.hasNext()) {
            Identifier IdentifierVar = (Identifier) it.next();
            RenderSystem.setShaderTexture(0, IdentifierVar);
            GL11.glTexParameteri(3553, 10241, 9728);
            GL11.glTexParameteri(3553, 10240, 9728);
            BufferBuilder BufferBuilderVarBegin = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
            ObjectListIterator it2 = ((ObjectList) this.b.get(IdentifierVar)).iterator();
            while (it2.hasNext()) {
                GlyphDrawEntry glyphDrawEntry = (GlyphDrawEntry) it2.next();
                float fA = glyphDrawEntry.a();
                float fB = glyphDrawEntry.b();
                FontGlyph fontGlyphD = glyphDrawEntry.d();
                FontGlyphAtlas fontGlyphAtlasF = fontGlyphD.f();
                float fC = fontGlyphD.c();
                float fD = fontGlyphD.d();
                float fA2 = (float) fontGlyphD.a() / fontGlyphAtlasF.b;
                float fB2 = (float) fontGlyphD.b() / fontGlyphAtlasF.c;
                float fA3 = (float) (fontGlyphD.a() + fontGlyphD.c()) / fontGlyphAtlasF.b;
                float fB3 = (float) (fontGlyphD.b() + fontGlyphD.d()) / fontGlyphAtlasF.c;
                int iC = glyphDrawEntry.c();
                BufferBuilderVarBegin.vertex(matrix4f, fA, fB + fD, 0.0f).texture(fA2, fB3).color(iC);
                BufferBuilderVarBegin.vertex(matrix4f, fA + fC, fB + fD, 0.0f).texture(fA3, fB3).color(iC);
                BufferBuilderVarBegin.vertex(matrix4f, fA + fC, fB, 0.0f).texture(fA3, fB2).color(iC);
                BufferBuilderVarBegin.vertex(matrix4f, fA, fB, 0.0f).texture(fA2, fB2).color(iC);
            }
            BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
        }
    }

    private void b(Matrix4f matrix4f) {
        RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
        ObjectIterator it = this.c.keySet().iterator();
        while (it.hasNext()) {
            Identifier IdentifierVar = (Identifier) it.next();
            RenderSystem.setShaderTexture(0, IdentifierVar);
            GL11.glTexParameteri(3553, 10241, 9728);
            GL11.glTexParameteri(3553, 10240, 9728);
            BufferBuilder BufferBuilderVarBegin = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
            ObjectListIterator it2 = ((ObjectList) this.c.get(IdentifierVar)).iterator();
            while (it2.hasNext()) {
                GradientGlyphDrawEntry gradientGlyphDrawEntry = (GradientGlyphDrawEntry) it2.next();
                float fA = gradientGlyphDrawEntry.a();
                float fB = gradientGlyphDrawEntry.b();
                FontGlyph fontGlyphE = gradientGlyphDrawEntry.e();
                FontGlyphAtlas fontGlyphAtlasF = fontGlyphE.f();
                float fC = fontGlyphE.c();
                float fD = fontGlyphE.d();
                float fA2 = (float) fontGlyphE.a() / fontGlyphAtlasF.b;
                float fB2 = (float) fontGlyphE.b() / fontGlyphAtlasF.c;
                float fA3 = (float) (fontGlyphE.a() + fontGlyphE.c()) / fontGlyphAtlasF.b;
                float fB3 = (float) (fontGlyphE.b() + fontGlyphE.d()) / fontGlyphAtlasF.c;
                int iC = gradientGlyphDrawEntry.c();
                int iD = gradientGlyphDrawEntry.d();
                BufferBuilderVarBegin.vertex(matrix4f, fA, fB + fD, 0.0f).texture(fA2, fB3).color(iD);
                BufferBuilderVarBegin.vertex(matrix4f, fA + fC, fB + fD, 0.0f).texture(fA3, fB3).color(iD);
                BufferBuilderVarBegin.vertex(matrix4f, fA + fC, fB, 0.0f).texture(fA3, fB2).color(iC);
                BufferBuilderVarBegin.vertex(matrix4f, fA, fB, 0.0f).texture(fA2, fB2).color(iC);
            }
            BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
        }
    }

    private Color a(Color color, Color color2, float f) {
        return new Color((int) (color.getRed() + ((color2.getRed() - color.getRed()) * f)), (int) (color.getGreen() + ((color2.getGreen() - color.getGreen()) * f)), (int) (color.getBlue() + ((color2.getBlue() - color.getBlue()) * f)), (int) (color.getAlpha() + ((color2.getAlpha() - color.getAlpha()) * f)));
    }

    public void b(MatrixStack MatrixStackVar, String str, double d, double d2, int i) {
        a(MatrixStackVar, str, (float) (d - ((double) (a(str) / 2.0f))), (float) d2, i);
    }

    public float a(String str) {
        if (str.isEmpty()) {
            return 0.0f;
        }
        float fMax = 0.0f;
        for (String str2 : str.split("\n")) {
            if (!str2.isEmpty()) {
                GlyphVector glyphVectorCreateGlyphVector = this.e.createGlyphVector(a, str2);
                float fRound = 0.0f;
                for (int i = 1; i <= str2.length(); i++) {
                    float x = (float) glyphVectorCreateGlyphVector.getGlyphPosition(i - 1).getX();
                    fRound += Math.round(((float) glyphVectorCreateGlyphVector.getGlyphPosition(i).getX()) - x);
                }
                fMax = Math.max(fMax, fRound);
            }
        }
        return (float) (Math.round(((double) (fMax / 2.0f)) * 2.0d) / 2.0d);
    }

    public float b(String str) {
        float fMax;
        float f = 0.0f;
        float fD = 0.0f;
        for (char c : (str.isEmpty() ? " " : str).toCharArray()) {
            if (c == '\n') {
                fD += f == 0.0f ? b(' ').d() : f;
                fMax = 0.0f;
            } else {
                FontGlyph glyph = b(c);
                fMax = Math.max(glyph == null ? 0.0f : glyph.d(), f);
            }
            f = fMax;
        }
        return f + fD;
    }

    public float a(char c) {
        return (float) (Math.round(((double) (Math.round((float) this.e.createGlyphVector(a, String.valueOf(c)).getGlyphPosition(1).getX()) / 2.0f)) * 2.0d) / 2.0d);
    }

    public float a(String str, int i) {
        if (str.isEmpty() || i <= 0) {
            return 0.0f;
        }
        if (i > str.length()) {
            i = str.length();
        }
        GlyphVector glyphVectorCreateGlyphVector = this.e.createGlyphVector(a, str);
        float fRound = 0.0f;
        for (int i2 = 1; i2 <= i; i2++) {
            float x = (float) glyphVectorCreateGlyphVector.getGlyphPosition(i2 - 1).getX();
            fRound += Math.round(((float) glyphVectorCreateGlyphVector.getGlyphPosition(i2).getX()) - x);
        }
        return (float) (Math.round(((double) (fRound / 2.0f)) * 2.0d) / 2.0d);
    }

    public int a(String str, float f) {
        if (str.isEmpty() || f <= 0.0f) {
            return 0;
        }
        GlyphVector glyphVectorCreateGlyphVector = this.e.createGlyphVector(a, str);
        float f2 = f * 2.0f;
        float[] fArr = new float[str.length() + 1];
        fArr[0] = 0.0f;
        for (int i = 1; i <= str.length(); i++) {
            float x = (float) glyphVectorCreateGlyphVector.getGlyphPosition(i - 1).getX();
            fArr[i] = fArr[i - 1] + Math.round(((float) glyphVectorCreateGlyphVector.getGlyphPosition(i).getX()) - x);
        }
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (f2 < (fArr[i2] + fArr[i2 + 1]) / 2.0f) {
                return i2;
            }
        }
        return str.length();
    }

    public String a(String str, int i, boolean z) {
        return z ? c(str, i) : b(str, i);
    }

    public String b(String str, int i) {
        return str.substring(0, d(str, i));
    }

    private int d(String str, int i) {
        if (str.isEmpty()) {
            return 0;
        }
        GlyphVector glyphVectorCreateGlyphVector = this.e.createGlyphVector(a, str);
        float f = i * 2.0f;
        float fRound = 0.0f;
        for (int i2 = 1; i2 <= str.length(); i2++) {
            float x = (float) glyphVectorCreateGlyphVector.getGlyphPosition(i2 - 1).getX();
            fRound += Math.round(((float) glyphVectorCreateGlyphVector.getGlyphPosition(i2).getX()) - x);
            if (fRound > f) {
                return i2 - 1;
            }
        }
        return str.length();
    }

    public String c(String str, int i) {
        if (str.isEmpty()) {
            return str;
        }
        GlyphVector glyphVectorCreateGlyphVector = this.e.createGlyphVector(a, str);
        float f = i * 2.0f;
        float[] fArr = new float[str.length() + 1];
        fArr[0] = 0.0f;
        for (int i2 = 1; i2 <= str.length(); i2++) {
            float x = (float) glyphVectorCreateGlyphVector.getGlyphPosition(i2 - 1).getX();
            fArr[i2] = fArr[i2 - 1] + Math.round(((float) glyphVectorCreateGlyphVector.getGlyphPosition(i2).getX()) - x);
        }
        float f2 = fArr[str.length()];
        if (f2 <= f) {
            return str;
        }
        for (int i3 = 1; i3 < str.length(); i3++) {
            if (f2 - fArr[i3] <= f) {
                return str.substring(i3);
            }
        }
        return "";
    }
}
