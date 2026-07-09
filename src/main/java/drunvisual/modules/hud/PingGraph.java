package drunvisual.modules.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import org.joml.Matrix4f;
import drunvisual.events.ClientTickEvent;
import drunvisual.events.HudRenderPostEvent;
import drunvisual.events.PacketEvent;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.module.ModuleRegistry;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.settings.BooleanSetting;
import drunvisual.settings.SliderSetting;
import ru.drunvisual.DrunVisual;

@ModuleInfo(a = "PingGraph", b = "Показывает динамику пинга, TPS и FPS.", c = ModuleCategory.HUD)
public class PingGraph extends ClientModule {
    private static final int SAMPLE_STEP_TICKS = 2;
    private static final Color PANEL_TOP = new Color(10, 12, 16, 220);
    private static final Color PANEL_BOTTOM = new Color(6, 8, 12, 235);
    private static final Color GRID = new Color(255, 255, 255, 35);
    private static final Color LABEL = new Color(255, 255, 255, 255);
    private final BooleanSetting showPing = new BooleanSetting("Пинг", true);
    private final BooleanSetting showTps = new BooleanSetting("TPS", true);
    private final BooleanSetting showFps = new BooleanSetting("FPS", true);
    private final SliderSetting width = new SliderSetting("Ширина", 132.0f, 90.0f, 220.0f, 2.0f);
    private final SliderSetting height = new SliderSetting("Высота", 52.0f, 32.0f, 100.0f, 2.0f);
    private final SliderSetting samples = new SliderSetting("Сэмплы", 90.0f, 30.0f, 140.0f, 1.0f);
    private final SliderSetting margin = new SliderSetting("Отступ", 12.0f, 4.0f, 40.0f, 1.0f);
    private final Deque<Float> pingSamples = new ArrayDeque();
    private final Deque<Float> fpsSamples = new ArrayDeque();
    private final Deque<Float> tpsSamples = new ArrayDeque();
    private long lastWorldTimePacketMs = -1L;
    private float currentTps = 20.0f;
    private int sampleTickCounter = 0;

    @EventHandler
    public void onPacket(PacketEvent packetEvent) {
        if (packetEvent.e() != PacketEvent.MessageDirection.RECIEVE || !(packetEvent.d() instanceof WorldTimeUpdateS2CPacket)) {
            return;
        }
        long now = System.currentTimeMillis();
        if (this.lastWorldTimePacketMs > 0L) {
            long delta = Math.max(1L, now - this.lastWorldTimePacketMs);
            this.currentTps = Math.max(0.0f, Math.min(20.0f, 20000.0f / delta));
        }
        this.lastWorldTimePacketMs = now;
    }

    @EventHandler
    public void onTick(ClientTickEvent clientTickEvent) {
        if (c.player == null) {
            clearSamples();
            return;
        }
        if (++this.sampleTickCounter < SAMPLE_STEP_TICKS) {
            return;
        }
        this.sampleTickCounter = 0;
        push(this.pingSamples, (float) getPingMs());
        push(this.fpsSamples, (float) Math.max(0, c.getCurrentFps()));
        if (this.lastWorldTimePacketMs > 0L && System.currentTimeMillis() - this.lastWorldTimePacketMs > 3500L) {
            this.currentTps = 20.0f;
        }
        push(this.tpsSamples, this.currentTps);
    }

    @EventHandler
    public void onHudRender(HudRenderPostEvent hudRenderPostEvent) {
        if (c.player == null) {
            return;
        }
        Renderer2D renderer = DrunVisual.getInstance().getRender();
        if (renderer == null) {
            return;
        }
        MatrixStack matrices = hudRenderPostEvent.a();
        FontRenderer font = FontManager.MEDIUM[10];
        float panelWidth = this.width.get();
        float panelHeight = this.height.get();
        float screenWidth = c.getWindow().getWidth() / 4.0f;
        float x = (screenWidth - panelWidth) - this.margin.get();
        float y = this.margin.get();
        renderer.a(x, y, panelWidth, panelHeight, 8.0f, PANEL_TOP, PANEL_TOP, PANEL_BOTTOM, PANEL_BOTTOM, matrices);
        float headerY = y + 6.0f;
        font.a(buildHeader(), x + 8.0f, headerY, LABEL, matrices);
        float graphX = x + 8.0f;
        float graphY = headerY + font.b("A") + 6.0f;
        float graphWidth = panelWidth - 16.0f;
        float graphHeight = panelHeight - (graphY - y) - 8.0f;
        drawGrid(matrices, graphX, graphY, graphWidth, graphHeight);
        if (this.showPing.get()) {
            drawSeries(matrices, this.pingSamples, graphX, graphY, graphWidth, graphHeight, pingColor(), maxPingValue());
        }
        if (this.showFps.get()) {
            drawSeries(matrices, this.fpsSamples, graphX, graphY, graphWidth, graphHeight, new Color(130, 255, 140), maxFpsValue());
        }
        if (this.showTps.get()) {
            drawSeries(matrices, this.tpsSamples, graphX, graphY, graphWidth, graphHeight, new Color(255, 205, 95), 20.0f);
        }
    }

    private void drawGrid(MatrixStack matrices, float x, float y, float width, float height) {
        drawQuad(matrices, x, y, width, height, new Color(255, 255, 255, 10));
        float row = height / 3.0f;
        drawQuad(matrices, x, y + row, width, 1.0f, GRID);
        drawQuad(matrices, x, y + (row * 2.0f), width, 1.0f, GRID);
    }

    private void drawSeries(MatrixStack matrices, Deque<Float> values, float x, float y, float width, float height, Color color, float maxValue) {
        if (values.size() < 2 || maxValue <= 0.0f) {
            return;
        }
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        RenderSystem.lineWidth(1.5f);
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.LINE_STRIP, VertexFormats.POSITION_COLOR);
        List<Float> list = new ArrayList(values);
        float red = color.getRed() / 255.0f;
        float green = color.getGreen() / 255.0f;
        float blue = color.getBlue() / 255.0f;
        for (int i = 0; i < list.size(); i++) {
            float value = Math.max(0.0f, Math.min(maxValue, ((Float) list.get(i)).floatValue()));
            float progress = list.size() == 1 ? 0.0f : i / ((float) (list.size() - 1));
            float px = x + (progress * width);
            float py = (y + height) - ((value / maxValue) * height);
            buffer.vertex(matrix, px, py, 0.0f).color(red, green, blue, 0.95f);
        }
        BufferRenderer.drawWithGlobalProgram(buffer.end());
        RenderSystem.lineWidth(1.0f);
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    private void drawQuad(MatrixStack matrices, float x, float y, float width, float height, Color color) {
        float x2 = x + width;
        float y2 = y + height;
        float red = color.getRed() / 255.0f;
        float green = color.getGreen() / 255.0f;
        float blue = color.getBlue() / 255.0f;
        float alpha = color.getAlpha() / 255.0f;
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        buffer.vertex(matrix, x, y2, 0.0f).color(red, green, blue, alpha);
        buffer.vertex(matrix, x2, y2, 0.0f).color(red, green, blue, alpha);
        buffer.vertex(matrix, x2, y, 0.0f).color(red, green, blue, alpha);
        buffer.vertex(matrix, x, y, 0.0f).color(red, green, blue, alpha);
        BufferRenderer.drawWithGlobalProgram(buffer.end());
        RenderSystem.disableBlend();
    }

    private void push(Deque<Float> deque, float value) {
        deque.addLast(Float.valueOf(value));
        int maxSize = this.samples.roundedInt();
        while (deque.size() > maxSize) {
            deque.removeFirst();
        }
    }

    private float maxPingValue() {
        float max = 100.0f;
        for (Float value : this.pingSamples) {
            max = Math.max(max, value.floatValue());
        }
        return max;
    }

    private float maxFpsValue() {
        float max = 60.0f;
        for (Float value : this.fpsSamples) {
            max = Math.max(max, value.floatValue());
        }
        return max;
    }

    private Color pingColor() {
        return ModuleRegistry.CLIENT_COLOR.n();
    }

    private String buildHeader() {
        StringBuilder builder = new StringBuilder();
        if (this.showPing.get()) {
            builder.append(getPingMs()).append("ms");
        }
        if (this.showTps.get()) {
            if (builder.length() > 0) {
                builder.append(" • ");
            }
            builder.append(String.format("%.1f TPS", Float.valueOf(this.currentTps)));
        }
        if (this.showFps.get()) {
            if (builder.length() > 0) {
                builder.append(" • ");
            }
            builder.append(Math.max(0, c.getCurrentFps())).append(" FPS");
        }
        return builder.length() == 0 ? "PingGraph" : builder.toString();
    }

    private int getPingMs() {
        ClientPlayNetworkHandler handler;
        PlayerListEntry entry;
        if (c.player == null || (handler = c.getNetworkHandler()) == null || (entry = handler.getPlayerListEntry(c.player.getUuid())) == null) {
            return 0;
        }
        return entry.getLatency();
    }

    private void clearSamples() {
        this.pingSamples.clear();
        this.fpsSamples.clear();
        this.tpsSamples.clear();
        this.currentTps = 20.0f;
        this.sampleTickCounter = 0;
        this.lastWorldTimePacketMs = -1L;
    }

    @Override
    public void f() {
        super.f();
        clearSamples();
    }
}
