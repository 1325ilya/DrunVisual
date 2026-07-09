package drunvisual.module;

import java.util.ArrayList;
import java.util.List;
import drunvisual.modules.visuals.Animations;
import drunvisual.modules.hud.ArmorHud;
import drunvisual.modules.visuals.AspectRatio;
import drunvisual.modules.utilities.AutoDuel;
import drunvisual.modules.utilities.AutoEat;
import drunvisual.modules.utilities.AutoInvest;
import drunvisual.modules.utilities.AutoLeave;
import drunvisual.modules.utilities.AutoPotion;
import drunvisual.modules.utilities.AutoReconnect;
import drunvisual.modules.utilities.AutoReissue;
import drunvisual.modules.utilities.AutoRespawn;
import drunvisual.modules.utilities.AutoTool;
import drunvisual.modules.visuals.BlockOverlay;
import drunvisual.modules.visuals.ChinaHat;
import drunvisual.modules.hud.ClientColor;
import drunvisual.modules.utilities.Cooldowns;
import drunvisual.modules.hud.CooldownsHud;
import drunvisual.modules.visuals.Crosshair;
import drunvisual.modules.visuals.CustomHand;
import drunvisual.modules.utilities.DamageLog;
import drunvisual.modules.utilities.DeathMarker;
import drunvisual.modules.hud.EffectNotify;
import drunvisual.modules.utilities.ElytraSwap;
import drunvisual.modules.utilities.FakePlayer;
import drunvisual.modules.utilities.FastEXP;
import drunvisual.modules.utilities.FastSwap;
import drunvisual.modules.utilities.FcHelper;
import drunvisual.modules.utilities.FreeLook;
import drunvisual.modules.utilities.FtHelper;
import drunvisual.modules.visuals.FullBright;
import drunvisual.modules.utilities.GpsCommand;
import drunvisual.modules.utilities.HealingHelper;
import drunvisual.modules.visuals.HitBubble;
import drunvisual.modules.visuals.HitColor;
import drunvisual.modules.visuals.HitSounds;
import drunvisual.modules.visuals.HitboxCustomizer;
import drunvisual.modules.visuals.KillEffect;
import drunvisual.modules.hud.HotkeysHud;
import drunvisual.modules.utilities.HwHelper;
import drunvisual.modules.hud.InventoryHud;
import drunvisual.modules.utilities.InventoryPresets;
import drunvisual.modules.utilities.ItemHighlighter;
import drunvisual.modules.utilities.ItemPickupLogger;
import drunvisual.modules.utilities.ItemScroller;
import drunvisual.modules.utilities.ItemSwap;
import drunvisual.effects.JumpCircles;
import drunvisual.modules.utilities.LockSlot;
import drunvisual.modules.visuals.Nimb;
import drunvisual.modules.visuals.NoFluid;
import drunvisual.modules.visuals.Particles;
import drunvisual.modules.hud.PingGraph;
import drunvisual.modules.utilities.PearlTracker;
import drunvisual.modules.hud.PotionsHud;
import drunvisual.modules.utilities.Predictions;
import drunvisual.modules.utilities.ProjectileLogger;
import drunvisual.modules.utilities.PvpSafe;
import drunvisual.modules.visuals.RenderTweaks;
import drunvisual.modules.utilities.RwHelper;
import drunvisual.modules.utilities.RwJoiner;
import drunvisual.modules.hud.SaturationHud;
import drunvisual.modules.visuals.SelfNametag;
import drunvisual.modules.utilities.ShiftTab;
import drunvisual.modules.visuals.ShulkerPreview;
import drunvisual.modules.utilities.SoundController;
import drunvisual.modules.utilities.Sprint;
import drunvisual.modules.utilities.StreamerMode;
import drunvisual.modules.utilities.TapeMouse;
import drunvisual.modules.visuals.TargetEsp;
import drunvisual.modules.hud.TargetHud;
import drunvisual.modules.visuals.TimeChanger;
import drunvisual.modules.utilities.TotemTracker;
import drunvisual.modules.visuals.Trails;
import drunvisual.modules.hud.Watermark;
import drunvisual.modules.visuals.WorldCustomizer;
import drunvisual.modules.visuals.WorldParticles;
import drunvisual.modules.utilities.Zoom;
import drunvisual.modules.utilities.DrunVisualIRC;
import drunvisual.gui.config.ConfigTextDialog;

public final class ModuleRegistry {
    private static final List<ClientModule> MODULES = new ArrayList();
    public static final FullBright FULL_BRIGHT = new FullBright();
    public static final Trails TRAILS = new Trails();
    public static final Particles PARTICLES = new Particles();
    public static final WorldParticles WORLD_PARTICLES = new WorldParticles();
    public static final JumpCircles JUMP_CIRCLES = new JumpCircles();
    public static final CustomHand CUSTOM_HAND = new CustomHand();
    public static final AspectRatio ASPECT_RATIO = new AspectRatio();
    public static final RenderTweaks RENDER_TWEAKS = new RenderTweaks();
    public static final TargetEsp TARGET_ESP = new TargetEsp();
    public static final HitboxCustomizer HITBOX_CUSTOMIZER = new HitboxCustomizer();
    public static final WorldCustomizer WORLD_CUSTOMIZER = new WorldCustomizer();
    public static final Crosshair CROSSHAIR = new Crosshair();
    public static final HitColor HIT_COLOR = new HitColor();
    public static final NoFluid NO_FLUID = new NoFluid();
    public static final BlockOverlay BLOCK_OVERLAY = new BlockOverlay();
    public static final HitBubble HIT_BUBBLE = new HitBubble();
    public static final SelfNametag SELF_NAMETAG = new SelfNametag();
    public static final TimeChanger TIME_CHANGER = new TimeChanger();
    public static final HitSounds HIT_SOUNDS = new HitSounds();
    public static final KillEffect KILL_EFFECT = new KillEffect();
    public static final ShulkerPreview SHULKER_PREVIEW = new ShulkerPreview();
    public static final Animations ANIMATIONS = new Animations();
    public static final Sprint SPRINT = new Sprint();
    public static final ElytraSwap ELYTRA_SWAP = new ElytraSwap();
    public static final FtHelper FT_HELPER = new FtHelper();
    public static final HealingHelper HEALING_HELPER = new HealingHelper();
    public static final Predictions PREDICTIONS = new Predictions();
    public static final PearlTracker PEARL_TRACKER = new PearlTracker();
    public static final ProjectileLogger PROJECTILE_LOGGER = new ProjectileLogger();
    public static final DamageLog DAMAGE_LOG = new DamageLog();
    public static final InventoryPresets INVENTORY_PRESETS = new InventoryPresets();
    public static final AutoTool AUTO_TOOL = new AutoTool();
    public static final ItemSwap ITEM_SWAP = new ItemSwap();
    public static final FastSwap FAST_SWAP = new FastSwap();
    public static final PvpSafe PVP_SAFE = new PvpSafe();
    public static final Cooldowns COOLDOWNS = new Cooldowns();
    public static final AutoEat AUTO_EAT = new AutoEat();
    public static final AutoInvest AUTO_INVEST = new AutoInvest();
    public static final AutoPotion AUTO_POTION = new AutoPotion();
    public static final AutoRespawn AUTO_RESPAWN = new AutoRespawn();
    public static final AutoReconnect AUTO_RECONNECT = new AutoReconnect();
    public static final AutoLeave AUTO_LEAVE = new AutoLeave();
    public static final FcHelper FC_HELPER = new FcHelper();
    public static final StreamerMode STREAMER_MODE = new StreamerMode();
    public static final HwHelper HW_HELPER = new HwHelper();
    public static final AutoReissue AUTO_REISSUE = new AutoReissue();
    public static final TapeMouse TAPE_MOUSE = new TapeMouse();
    public static final FreeLook FREE_LOOK = new FreeLook();
    public static final ItemScroller ITEM_SCROLLER = new ItemScroller();
    public static final LockSlot LOCK_SLOT = new LockSlot();
    public static final Zoom ZOOM = new Zoom();
    public static final TotemTracker TOTEM_TRACKER = new TotemTracker();
    public static final ItemHighlighter ITEM_HIGHLIGHTER = new ItemHighlighter();
    public static final AutoDuel AUTO_DUEL = new AutoDuel();
    public static final RwHelper RW_HELPER = new RwHelper();
    public static final ShiftTab SHIFT_TAB = new ShiftTab();
    public static final SoundController SOUND_CONTROLLER = new SoundController();
    public static final RwJoiner RW_JOINER = new RwJoiner();
    public static final FastEXP FAST_EXP = new FastEXP();
    public static final ItemPickupLogger ITEM_PICKUP_LOGGER = new ItemPickupLogger();
    public static final PotionsHud POTIONS_HUD = new PotionsHud();
    public static final TargetHud TARGET_HUD = new TargetHud();
    public static final EffectNotify EFFECT_NOTIFY = new EffectNotify();
    public static final ClientColor CLIENT_COLOR = new ClientColor();
    public static final ArmorHud ARMOR_HUD = new ArmorHud();
    public static final HotkeysHud HOTKEYS_HUD = new HotkeysHud();
    public static final PingGraph PING_GRAPH = new PingGraph();
    public static final Watermark WATERMARK = new Watermark();
    public static final ChinaHat CHINA_HAT = new ChinaHat();
    public static final Nimb NIMB = new Nimb();
    public static final CooldownsHud COOLDOWNS_HUD = new CooldownsHud();
    public static final SaturationHud SATURATION_HUD = new SaturationHud();
    public static final InventoryHud INVENTORY_HUD = new InventoryHud();
    public static final FakePlayer FAKE_PLAYER = new FakePlayer();
    public static final DeathMarker DEATH_MARKER = new DeathMarker();
    public static final GpsCommand GPS_COMMAND = new GpsCommand();
    public static final DrunVisualIRC DRUNVISUAL_IRC = new DrunVisualIRC();

    private ModuleRegistry() {
    }

    public static void init() {
        MODULES.clear();
        register(FULL_BRIGHT, TRAILS, PARTICLES, WORLD_PARTICLES, JUMP_CIRCLES, CUSTOM_HAND, ASPECT_RATIO, RENDER_TWEAKS, TARGET_ESP, HITBOX_CUSTOMIZER, WORLD_CUSTOMIZER, CROSSHAIR, HIT_COLOR, NO_FLUID, BLOCK_OVERLAY, HIT_BUBBLE, SELF_NAMETAG, TIME_CHANGER, HIT_SOUNDS, KILL_EFFECT, SHULKER_PREVIEW, ANIMATIONS, SPRINT, ELYTRA_SWAP, FT_HELPER, HEALING_HELPER, PREDICTIONS, PEARL_TRACKER, PROJECTILE_LOGGER, DAMAGE_LOG, INVENTORY_PRESETS, AUTO_TOOL, ITEM_SWAP, FAST_SWAP, PVP_SAFE, COOLDOWNS, AUTO_EAT, AUTO_INVEST, AUTO_POTION, AUTO_RESPAWN, AUTO_RECONNECT, AUTO_LEAVE, FC_HELPER, STREAMER_MODE, HW_HELPER, AUTO_REISSUE, TAPE_MOUSE, FREE_LOOK, ITEM_SCROLLER, LOCK_SLOT, ZOOM, TOTEM_TRACKER, ITEM_HIGHLIGHTER, AUTO_DUEL, RW_HELPER, SHIFT_TAB, SOUND_CONTROLLER, RW_JOINER, FAST_EXP, ITEM_PICKUP_LOGGER, POTIONS_HUD, TARGET_HUD, EFFECT_NOTIFY, CLIENT_COLOR, ARMOR_HUD, HOTKEYS_HUD, PING_GRAPH, WATERMARK, CHINA_HAT, NIMB, COOLDOWNS_HUD, SATURATION_HUD, INVENTORY_HUD, FAKE_PLAYER, DEATH_MARKER, GPS_COMMAND, DRUNVISUAL_IRC);
    }

    public static List<ClientModule> all() {
        return MODULES;
    }

    public static List<ClientModule> byCategory(ModuleCategory moduleCategory) {
        ArrayList arrayList = new ArrayList();
        for (ClientModule clientModule : MODULES) {
            if (clientModule.category() == moduleCategory) {
                arrayList.add(clientModule);
            }
        }
        return arrayList;
    }

    public static ClientModule findByName(String str) {
        for (ClientModule clientModule : MODULES) {
            if (clientModule.name().equalsIgnoreCase(str)) {
                return clientModule;
            }
        }
        return null;
    }

    public static <ConfigTextDialog extends ClientModule> ConfigTextDialog get(Class<ConfigTextDialog> cls) {
        for (ClientModule clientModule : MODULES) {
            if (cls.isInstance(clientModule)) {
                return cls.cast(clientModule);
            }
        }
        return null;
    }

    private static void register(ClientModule... clientModuleArr) {
        for (ClientModule clientModule : clientModuleArr) {
            MODULES.add(clientModule);
            clientModule.collectSettings();
        }
    }
}
