package drunvisual.render.icons;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import ru.drunvisual.DrunVisual;

public final class IconTextureRegistry {
    public static final String WATER = "water";
    public static final String ARROW_V = "arrow_v";
    public static final String KEYBOARD = "keyboard";
    public static final String ICON = "icon";
    public static final String BUBBLE_1 = "bubble_1";
    public static final String BUBBLE_2 = "bubble_2";
    public static final String LIGHTNING = "lightning";
    public static final String DRUNVISUAL_ICON = "drunvisual_icon";
    public static final String SEARCH = "search";
    public static final String ARROW = "arrow";
    public static final String UP_ARROW = "up_arrow";
    public static final String LOGO = "logo";
    public static final String CALENDAR = "calendar";
    public static final String DEATH = "death";
    public static final String DIAMOND = "diamond";
    public static final String FAST = "fast";
    public static final String HOME = "home";
    public static final String LOCKED = "locked";
    public static final String MOUNTAIN = "mountain";
    public static final String REPAIR = "repair";
    public static final String SHIELD = "shield";
    public static final String TRASH = "trash";
    public static final String PLAY = "play";
    public static final String SHARE = "share";
    public static final String SAVE = "save";
    public static final String PLUS = "plus";
    public static final String KEY = "key";
    public static final String EDIT = "edit";
    public static final String LOAD = "load";
    public static final String DRUNVISUAL_ICO = "drunvisual_ico";
    private static final String DEFAULT_KEY = "icon";
    private static final Map<String, TextureInfo> TEXTURES = new HashMap();

    public static final class TextureInfo {
        private final Identifier identifier;
        private final int width;
        private final int height;

        public TextureInfo(Identifier IdentifierVar, int i, int i2) {
            this.identifier = IdentifierVar;
            this.width = i;
            this.height = i2;
        }

        public Identifier a() {
            return this.identifier;
        }

        public int b() {
            return this.width;
        }

        public int c() {
            return this.height;
        }
    }

    private IconTextureRegistry() {
    }

    public static void load() {
        if (TEXTURES.isEmpty()) {
            register("shadow", "textures/shadow.png");
            register(LOGO, "textures/logo.png");
            register("icon", "textures/icon.png");
            register(BUBBLE_1, "textures/bubble_1.png");
            register(BUBBLE_2, "textures/bubble_2.png");
            register(UP_ARROW, "textures/up_arrow.png");
            register(ARROW, "textures/arrow.png");
            register(DrunVisual.CLIENT_NAME, "textures/drunvisual.png");
            register("drunvisual_logo", "textures/drunvisual_logo.png");
            register("target", "textures/target.png");
            register(WATER, "textures/clickgui/drunvisual_water.png");
            register("drunvisual_water", "textures/clickgui/drunvisual_water.png");
            register(SEARCH, "textures/clickgui/search.png");
            register(KEYBOARD, "textures/clickgui/keyboard.png");
            register(CALENDAR, "textures/clickgui/calendar.png");
            register(DEATH, "textures/clickgui/death.png");
            register(DIAMOND, "textures/clickgui/diamond.png");
            register(FAST, "textures/clickgui/fast.png");
            register(HOME, "textures/clickgui/home.png");
            register(LOCKED, "textures/clickgui/locked.png");
            register(MOUNTAIN, "textures/clickgui/mountain.png");
            register(REPAIR, "textures/clickgui/repair.png");
            register(SHIELD, "textures/clickgui/shield.png");
            register(TRASH, "textures/clickgui/trash.png");
            register(PLAY, "textures/clickgui/play.png");
            register(SHARE, "textures/clickgui/share.png");
            register(SAVE, "textures/clickgui/save.png");
            register(PLUS, "textures/clickgui/plus.png");
            register(KEY, "textures/clickgui/key.png");
            register(EDIT, "textures/clickgui/edit.png");
            register(LOAD, "textures/clickgui/load.png");
            register(DRUNVISUAL_ICO, "textures/clickgui/drunvisual_ico.png");
            register("bell", "textures/clickgui/bell.png");
            register("warning", "textures/clickgui/warning.png");
            register("music", "textures/clickgui/music.png");
            register("arrows_left", "textures/clickgui/arrows_left.png");
            register("arrows_right", "textures/clickgui/arrows_right.png");
            register("pause", "textures/clickgui/pause.png");
            register("play_button", "textures/clickgui/play_button.png");
            register("potions", "textures/clickgui/potions.png");
            register("hotkeys", "textures/clickgui/hotkeys.png");
            register("cooldowns", "textures/clickgui/cooldowns.png");
            register("configs", "textures/clickgui/configs.png");
            register("events", "textures/clickgui/events.png");
            register("friends", "textures/clickgui/friends.png");
            register("hud", "textures/clickgui/hud.png");
            register("markers", "textures/clickgui/markers.png");
            register("modules", "textures/clickgui/modules.png");
            register("utilities", "textures/clickgui/utilities.png");
            register("visuals", "textures/clickgui/visuals.png");
            register("example_group", "textures/clickgui/example_group.png");
            registerPotionAliases();
        }
    }

    public static Identifier get(String str) {
        TextureInfo info = getInfo(str);
        return info == null ? Identifier.of(DrunVisual.CLIENT_NAME, "textures/icon.png") : info.a();
    }

    @Nullable
    public static TextureInfo getInfo(String str) {
        load();
        TextureInfo textureInfo = TEXTURES.get(normalizeKey(str));
        return textureInfo != null ? textureInfo : TEXTURES.get("icon");
    }

    private static void register(String str, String str2) {
        Identifier IdentifierVarOf = Identifier.of(DrunVisual.CLIENT_NAME, str2);
        int iGetWidth = 1;
        int iGetHeight = 1;
        try {
            InputStream resourceAsStream = IconTextureRegistry.class.getResourceAsStream("/assets/drunvisual/" + str2);
            try {
                if (resourceAsStream == null) {
                    DrunVisual.getLOGGER().warn("[DrunVisualIcons] Texture file NOT FOUND on classpath: /assets/drunvisual/{} (key={}, id={})", str2, str, IdentifierVarOf);
                } else {
                    NativeImage NativeImageVarRead = NativeImage.read(resourceAsStream);
                    iGetWidth = NativeImageVarRead.getWidth();
                    iGetHeight = NativeImageVarRead.getHeight();
                    NativeImageVarRead.close();
                    if (str.equals(LOGO)) {
                        DrunVisual.getLOGGER().info("[DrunVisualIcons] Loaded LOGO texture: path=/assets/drunvisual/{} id={} size={}x{}", str2, IdentifierVarOf, Integer.valueOf(iGetWidth), Integer.valueOf(iGetHeight));
                    }
                }
                if (resourceAsStream != null) {
                    resourceAsStream.close();
                }
            } finally {
            }
        } catch (IOException e) {
            DrunVisual.getLOGGER().warn("[DrunVisualIcons] Failed to read texture: /assets/drunvisual/{}", str2, e);
        }
        TEXTURES.put(str, new TextureInfo(IdentifierVarOf, iGetWidth, iGetHeight));
    }

    private static void registerPotionAliases() {
        TextureInfo textureInfo = TEXTURES.get("potions");
        for (String str : new String[]{"absorption", "bad_omen", "blindness", "conduit_power", "dolphins_grace", "fire_resistance", "glowing", "haste", "health_boost", "hero_of_the_village", "hunger", "instant_damage", "instant_health", "invisibility", "jump_boost", "levitation", "luck", "mining_fatigue", "nausea", "night_vision", "poison", "regeneration", "resistance", "saturation", "slow_falling", "slowness", "speed", "strength", "unluck", "water_breathing", "weakness", "wither"}) {
            TEXTURES.put(str, textureInfo);
        }
    }

    private static String normalizeKey(String str) {
        return (str == null || str.isBlank() || !str.matches("[a-z0-9_./-]+")) ? "icon" : str;
    }
}
