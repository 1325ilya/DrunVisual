package ru.drunvisual;

import java.awt.Desktop;
import java.net.URI;
import java.util.concurrent.ThreadFactory;
import lombok.Generated;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.gui.screen.Screen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import drunvisual.gui.core.ClickGuiKeyBinding;
import drunvisual.hud.core.HudServiceRegistry;
import drunvisual.render.icons.IconTextureRegistry;
import drunvisual.gui.core.DrunVisualClickGuiScreen;
import drunvisual.render.Renderer2D;
import drunvisual.render.Renderer2DImpl;
import drunvisual.render.shader.ShaderLibrary;
import drunvisual.auth.UserInfo;
import drunvisual.config.LocalConfigManager;
import drunvisual.module.ModuleRegistry;
import drunvisual.player.DrunVisualPlayerTracker;

public class DrunVisual implements Runnable, ThreadFactory, ModInitializer {
    public static final String CLIENT_VERSION = "2.0.1";
    private String backendUrl;
    private Renderer2D render;
    private Screen clickGui;
    private static final DrunVisual instance = new DrunVisual();
    private static UserInfo userInfo = null;
    private static String cachedBackendUrl = null;
    private static final String[] BACKEND_URLS = {"https://backend4.drunvisuals.pro", "https://backend.drunvisuals.pro", "https://backend1.drunvisuals.pro", "https://backend2.drunvisuals.pro", "https://backend3.drunvisuals.pro"};
    private static final String[] API_URLS = {"https://api.drunvisuals.pro/api", "https://euapi.drunvisuals.pro/api"};
    private static final String[] MAIN_URLS = {"https://drunvisuals.pro", "https://eu.drunvisuals.pro"};
    public static final String CLIENT_NAME = "drunvisual";
    private static final Logger LOGGER = LogManager.getLogger(CLIENT_NAME);
    private static int currentBackendIndex = 0;
    private static boolean updateScreenShown = false;
    private String pendingTokenToSave = null;
    private boolean init = false;

    public void onInitialize() {
        DrunVisualPlayerTracker.get().init();
        ClickGuiKeyBinding.register();
        ClientPlayConnectionEvents.DISCONNECT.register((ClientPlayNetworkHandlerVar, MinecraftClientVar) -> {
            LOGGER.info("[DrunVisual] Disconnect — saving config");
            LocalConfigManager.get().flushSaveNow("disconnect");
        });
        ClientLifecycleEvents.CLIENT_STOPPING.register(MinecraftClientVar2 -> {
            LOGGER.info("[DrunVisual] Client stop — saving config");
            LocalConfigManager.get().flushSaveNow("client-stop");
        });
        LOGGER.info("DrunVisual initialized without native protection");
    }

    @Override
    public void run() {
    }

    @Override
    public Thread newThread(Runnable runnable) {
        return null;
    }

    private static void unlockCryptoPolicy() {
    }

    public void init() {
        IconTextureRegistry.load();
        ShaderLibrary.loadDefaultShaders();
        this.render = new Renderer2DImpl();
        HudServiceRegistry.a();
        ModuleRegistry.init();
        LOGGER.info("[DrunVisual] Loading config (dir: {})", LocalConfigManager.get().configDirectory());
        try {
            LocalConfigManager.get().init();
            LOGGER.info("[DrunVisual] Config load finished");
        } catch (Exception e) {
            LOGGER.warn("[DrunVisual] Config load failed", e);
        }
        this.clickGui = new DrunVisualClickGuiScreen();
        this.init = true;
        LOGGER.info("DrunVisual Java GUI initialized");
    }

    private String loadTokenFromAnySource() {
        return "";
    }

    private String loadTokenFromJar() {
        return "";
    }

    public static void showMessage(String str) {
    }

    public static void showAntivirusWarning() {
    }

    public static void showSystemMessage() {
    }

    public static void openYouTube() {
    }

    public static void checkAndShowUpdate(Screen ScreenVar) {
    }

    public static String getMainUrl() {
        for (String str : MAIN_URLS) {
            if (isUrlAccessible(str)) {
                return str;
            }
        }
        return MAIN_URLS[0];
    }

    public static String getBackendUrl() {
        return cachedBackendUrl != null ? cachedBackendUrl : BACKEND_URLS[currentBackendIndex];
    }

    public static String getDirectApiUrl() {
        for (String str : API_URLS) {
            if (isUrlAccessible(str + "/auth/device/poll?state=test")) {
                return str;
            }
        }
        return API_URLS[0];
    }

    private static boolean isUrlAccessible(String str) {
        return false;
    }

    public Renderer2D getRender() {
        return this.render;
    }

    public Screen getClickGui() {
        return this.clickGui;
    }

    @Generated
    public static DrunVisual getInstance() {
        return instance;
    }

    @Generated
    public static Logger getLOGGER() {
        return LOGGER;
    }

    @Generated
    public static UserInfo getUserInfo() {
        return userInfo;
    }

    @Generated
    public static void setUserInfo(UserInfo userInfo2) {
        userInfo = userInfo2;
    }

    public static String decrypt(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }

}
