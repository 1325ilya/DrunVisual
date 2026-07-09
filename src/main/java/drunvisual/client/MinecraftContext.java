package drunvisual.client;

import net.minecraft.client.util.Window;
import net.minecraft.client.MinecraftClient;

public interface MinecraftContext {
    public static final MinecraftClient CLIENT = MinecraftClient.getInstance();
    public static final Window WINDOW = CLIENT.getWindow();
    public static final MinecraftClient c = CLIENT;
    public static final Window d = WINDOW;
}
