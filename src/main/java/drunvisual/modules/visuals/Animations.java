package drunvisual.modules.visuals;

import java.util.Objects;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.BooleanSetting;
import drunvisual.settings.SettingGroup;
import drunvisual.settings.SliderSetting;
import drunvisual.animation.ChatInputSlideAnimation;
import drunvisual.animation.HotbarSelectionAnimation;
import drunvisual.animation.PerspectiveDistanceAnimation;
import drunvisual.animation.PlayerListScaleAnimation;

@ModuleInfo(a = "Animations", b = "Interface animations", c = ModuleCategory.VISUALS)
public class Animations extends ClientModule {
    private final SettingGroup playerListGroup = new SettingGroup("Player List");
    public final BooleanSetting a = new BooleanSetting("Tab List", true);
    public final SliderSetting b;
    private final SettingGroup chatGroup;
    public final BooleanSetting e;
    public final SliderSetting f;
    private final SettingGroup hotbarGroup;
    public final BooleanSetting g;
    public final SliderSetting h;
    private final SettingGroup cameraGroup;
    public final BooleanSetting i;
    public final SliderSetting j;
    private static PlayerListScaleAnimation playerListAnimation;
    private static ChatInputSlideAnimation chatInputAnimation;
    private static HotbarSelectionAnimation hotbarSelectionAnimation;
    private static PerspectiveDistanceAnimation perspectiveDistanceAnimation;

    public Animations() {
        SliderSetting sliderSetting = new SliderSetting("Tab Duration", 200.0f, 50.0f, 500.0f, 10.0f);
        BooleanSetting booleanSetting = this.a;
        Objects.requireNonNull(booleanSetting);
        this.b = sliderSetting.a(booleanSetting::a);
        this.chatGroup = new SettingGroup("Chat");
        this.e = new BooleanSetting("Chat Messages", true);
        SliderSetting sliderSetting2 = new SliderSetting("Chat Duration", 200.0f, 50.0f, 500.0f, 10.0f);
        BooleanSetting booleanSetting2 = this.e;
        Objects.requireNonNull(booleanSetting2);
        this.f = sliderSetting2.a(booleanSetting2::a);
        this.hotbarGroup = new SettingGroup("Hotbar");
        this.g = new BooleanSetting("Hotbar Selector", true);
        SliderSetting sliderSetting3 = new SliderSetting("Hotbar Duration", 100.0f, 50.0f, 500.0f, 10.0f);
        BooleanSetting booleanSetting3 = this.g;
        Objects.requireNonNull(booleanSetting3);
        this.h = sliderSetting3.a(booleanSetting3::a);
        this.cameraGroup = new SettingGroup("Camera");
        this.i = new BooleanSetting("Perspective", true);
        SliderSetting sliderSetting4 = new SliderSetting("Perspective Duration", 300.0f, 50.0f, 1000.0f, 10.0f);
        BooleanSetting booleanSetting4 = this.i;
        Objects.requireNonNull(booleanSetting4);
        this.j = sliderSetting4.a(booleanSetting4::a);
    }

    public static PlayerListScaleAnimation n() {
        if (playerListAnimation == null) {
            playerListAnimation = new PlayerListScaleAnimation();
        }
        return playerListAnimation;
    }

    public static ChatInputSlideAnimation o() {
        if (chatInputAnimation == null) {
            chatInputAnimation = new ChatInputSlideAnimation();
        }
        return chatInputAnimation;
    }

    public static HotbarSelectionAnimation p() {
        if (hotbarSelectionAnimation == null) {
            hotbarSelectionAnimation = new HotbarSelectionAnimation();
        }
        return hotbarSelectionAnimation;
    }

    public static PerspectiveDistanceAnimation q() {
        if (perspectiveDistanceAnimation == null) {
            perspectiveDistanceAnimation = new PerspectiveDistanceAnimation();
        }
        return perspectiveDistanceAnimation;
    }
}
