package drunvisual.render.shader;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.nio.FloatBuffer;
import java.util.Map;
import net.minecraft.client.MinecraftClient;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;
import ru.drunvisual.DrunVisual;

public class DrunVisualShaderProgram {
    private static final int a = 0;
    private static final int b = 1;
    private static int e = -1;
    private static int f = -1;
    private static final FloatBuffer g = BufferUtils.createFloatBuffer(16);
    private boolean d = true;
    private final Map<String, Integer> h = Maps.newLinkedHashMap();
    private final int c = GL30.glCreateProgram();

    public int a() {
        return this.c;
    }

    public boolean b() {
        return this.d;
    }

    public void a(String str, int i) {
        int iGlCreateShader = GL30.glCreateShader(i);
        GL30.glShaderSource(iGlCreateShader, str);
        GL30.glCompileShader(iGlCreateShader);
        if (GL30.glGetShaderi(iGlCreateShader, 35713) != 0) {
            GL30.glAttachShader(this.c, iGlCreateShader);
        } else {
            DrunVisual.getLOGGER().error("Shader {} compile error: \n{}", Integer.valueOf(iGlCreateShader), GL30.glGetShaderInfoLog(iGlCreateShader));
            this.d = false;
        }
    }

    public void c() {
        GL30.glBindAttribLocation(this.c, 0, "Position");
        GL30.glBindAttribLocation(this.c, 1, "UV0");
        GL30.glLinkProgram(this.c);
        if (GL30.glGetProgrami(this.c, 35714) == 0) {
            DrunVisual.getLOGGER().error("Program {} link error: \n{}", Integer.valueOf(this.c), GL30.glGetProgramInfoLog(this.c));
            this.d = false;
        }
    }

    public void d() {
        if (this.d) {
            GL30.glUseProgram(this.c);
            Matrix4f projectionMatrix = RenderSystem.getProjectionMatrix();
            Matrix4f modelViewMatrix = RenderSystem.getModelViewMatrix();
            g.clear();
            projectionMatrix.get(g);
            g.rewind();
            int iA = a("ProjMat");
            if (iA != -1) {
                GL30.glUniformMatrix4fv(iA, false, g);
            }
            g.clear();
            modelViewMatrix.get(g);
            g.rewind();
            int iA2 = a("ModelViewMat");
            if (iA2 != -1) {
                GL30.glUniformMatrix4fv(iA2, false, g);
            }
            MinecraftClient MinecraftClientVarGetInstance = MinecraftClient.getInstance();
            a("resolution", MinecraftClientVarGetInstance.getWindow().getScaledWidth(), MinecraftClientVarGetInstance.getWindow().getScaledHeight());
        }
    }

    public void e() {
        GL30.glUseProgram(0);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void a(float f2, float f3, float f4, float f5) {
        if (e == -1) {
            f();
        }
        int iGlGetInteger = GL30.glGetInteger(34229);
        GL30.glBindVertexArray(e);
        GL30.glBindBuffer(34962, f);
        GL30.glBufferData(34962, new float[]{f2, f3 + f5, 0.0f, 0.0f, 1.0f, f2 + f4, f3 + f5, 0.0f, 1.0f, 1.0f, f2 + f4, f3, 0.0f, 1.0f, 0.0f, f2, f3 + f5, 0.0f, 0.0f, 1.0f, f2 + f4, f3, 0.0f, 1.0f, 0.0f, f2, f3, 0.0f, 0.0f, 0.0f}, 35048);
        GL30.glDrawArrays(4, 0, 6);
        GL30.glBindVertexArray(iGlGetInteger);
    }

    public static void a(float[] fArr, int i) {
        if (e == -1) {
            f();
        }
        int iGlGetInteger = GL30.glGetInteger(34229);
        GL30.glBindVertexArray(e);
        GL30.glBindBuffer(34962, f);
        GL30.glBufferData(34962, fArr, 35048);
        GL30.glDrawArrays(4, 0, i);
        GL30.glBindVertexArray(iGlGetInteger);
    }

    private static void f() {
        int iGlGetInteger = GL30.glGetInteger(34229);
        e = GL30.glGenVertexArrays();
        f = GL30.glGenBuffers();
        GL30.glBindVertexArray(e);
        GL30.glBindBuffer(34962, f);
        GL30.glEnableVertexAttribArray(0);
        GL30.glVertexAttribPointer(0, 3, 5126, false, 20, 0L);
        GL30.glEnableVertexAttribArray(1);
        GL30.glVertexAttribPointer(1, 2, 5126, false, 20, 12L);
        GL30.glBindVertexArray(iGlGetInteger);
    }

    public int a(String str) {
        if (!this.h.containsKey(str)) {
            this.h.put(str, Integer.valueOf(GL30.glGetUniformLocation(this.c, str)));
        }
        return this.h.get(str).intValue();
    }

    public void a(String str, float f2) {
        GL30.glUniform1f(a(str), f2);
    }

    public void a(String str, float f2, float f3) {
        GL30.glUniform2f(a(str), f2, f3);
    }

    public void a(String str, float f2, float f3, float f4) {
        GL30.glUniform3f(a(str), f2, f3, f4);
    }

    public void a(String str, float f2, float f3, float f4, float f5) {
        GL30.glUniform4f(a(str), f2, f3, f4, f5);
    }

    public void a(String str, Color color) {
        GL30.glUniform4f(a(str), color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }

    public void a(String str, FloatBuffer floatBuffer) {
        GL30.glUniform1fv(a(str), floatBuffer);
    }

    public void b(String str, FloatBuffer floatBuffer) {
        RenderSystem.glUniform1(a(str), floatBuffer);
    }

    public void b(String str, int i) {
        GL30.glUniform1i(a(str), i);
    }

    public void a(String str, int i, int i2) {
        GL30.glUniform2i(a(str), i, i2);
    }

    public void a(String str, int i, int i2, int i3) {
        GL30.glUniform3i(a(str), i, i2, i3);
    }

    public void a(String str, int i, int i2, int i3, int i4) {
        GL30.glUniform4i(a(str), i, i2, i3, i4);
    }

    public void c(String str, FloatBuffer floatBuffer) {
        GL30.glUniformMatrix4fv(a(str), false, floatBuffer);
    }

    public void a(String str, float[] fArr) {
        GL30.glUniformMatrix4fv(a(str), false, fArr);
    }
}
