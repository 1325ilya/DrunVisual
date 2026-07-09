package drunvisual.modules.visuals;

import java.awt.Color;
import java.util.Objects;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.module.ModuleRegistry;
import drunvisual.settings.BooleanSetting;
import drunvisual.settings.ColorSetting;
import drunvisual.settings.ModeSetting;
import drunvisual.settings.SettingGroup;
import drunvisual.settings.SliderSetting;
import drunvisual.modules.hud.ClientColor;

@ModuleInfo(a = "Custom Hand", b = "Customizes first-person hands", c = ModuleCategory.VISUALS)
public class CustomHand extends ClientModule {
    private static final String MODE_NORMAL = "Normal";
    private static final String MODE_NO_ANIMATION = "No Animation";
    private static final String MODE_TILT = "Tilt";
    private static final String MODE_SWING = "Swing";
    private static final String MODE_ROTATE = "Rotate";
    private static final String MODE_GROW = "Grow";
    private static final String MODE_SHRINK = "Shrink";
    private static final String MODE_STRETCH = "Stretch";
    private static final String SHADER_NEBULA = "Nebula";
    private static final String SHADER_STARS = "Stars";
    private static final String SHADER_WEB = "Web";
    private static final String SHADER_PLASMA = "Plasma";
    public final ModeSetting animationMode = new ModeSetting("Animation", new String[]{MODE_NORMAL, MODE_NO_ANIMATION, MODE_TILT, MODE_SWING, MODE_ROTATE, MODE_GROW, MODE_SHRINK, MODE_STRETCH}, MODE_NORMAL);
    public final SliderSetting animationSpeed = new SliderSetting("Animation Speed", 6.0f, 1.0f, 20.0f, 1.0f);
    public final SliderSetting swingStrength = new SliderSetting("Swing Strength", 5.0f, 1.0f, 10.0f, 0.5f);
    private final SettingGroup shaderSection = new SettingGroup("Shader");
    public final BooleanSetting shaderEnabled = new BooleanSetting("Shader", false);
    public final ModeSetting shaderMode;
    public final SliderSetting shaderSpeed;
    public final SliderSetting shaderOpacity;
    public final BooleanSetting shaderOnly;
    public final BooleanSetting useClientColor;
    public final ColorSetting shaderColor;
    private final SettingGroup mainHandSection;
    public final SliderSetting mainHandScale;
    public final SliderSetting mainHandOffsetX;
    public final SliderSetting mainHandOffsetY;
    public final SliderSetting mainHandOffsetZ;
    private final SettingGroup offHandSection;
    public final SliderSetting offHandScale;
    public final SliderSetting offHandOffsetX;
    public final SliderSetting offHandOffsetY;
    public final SliderSetting offHandOffsetZ;

    public CustomHand() {
        ModeSetting modeSetting = new ModeSetting("Shader Type", new String[]{SHADER_NEBULA, SHADER_STARS, SHADER_WEB, SHADER_PLASMA}, SHADER_NEBULA);
        BooleanSetting booleanSetting = this.shaderEnabled;
        Objects.requireNonNull(booleanSetting);
        this.shaderMode = modeSetting.a(booleanSetting::a);
        SliderSetting sliderSetting = new SliderSetting("Shader Speed", 1.0f, 0.1f, 3.0f, 0.1f);
        BooleanSetting booleanSetting2 = this.shaderEnabled;
        Objects.requireNonNull(booleanSetting2);
        this.shaderSpeed = sliderSetting.a(booleanSetting2::a);
        SliderSetting sliderSetting2 = new SliderSetting("Shader Opacity", 0.5f, 0.1f, 1.0f, 0.05f);
        BooleanSetting booleanSetting3 = this.shaderEnabled;
        Objects.requireNonNull(booleanSetting3);
        this.shaderOpacity = sliderSetting2.a(booleanSetting3::a);
        BooleanSetting booleanSetting4 = new BooleanSetting("Shader Only", false);
        BooleanSetting booleanSetting5 = this.shaderEnabled;
        Objects.requireNonNull(booleanSetting5);
        this.shaderOnly = booleanSetting4.a(booleanSetting5::a);
        BooleanSetting booleanSetting6 = new BooleanSetting("Client Color", true);
        BooleanSetting booleanSetting7 = this.shaderEnabled;
        Objects.requireNonNull(booleanSetting7);
        this.useClientColor = booleanSetting6.a(booleanSetting7::a);
        this.shaderColor = new ColorSetting("Shader Color", Color.WHITE).a(() -> {
            return Boolean.valueOf(this.shaderEnabled.a() && !this.useClientColor.a());
        });
        this.mainHandSection = new SettingGroup("Main Hand");
        this.mainHandScale = new SliderSetting("Main Hand Scale", 1.0f, 0.5f, 1.5f, 0.05f);
        this.mainHandOffsetX = new SliderSetting("Main Hand X", 0.0f, -2.0f, 2.0f, 0.05f);
        this.mainHandOffsetY = new SliderSetting("Main Hand Y", 0.0f, -2.0f, 2.0f, 0.05f);
        this.mainHandOffsetZ = new SliderSetting("Main Hand Z", 0.0f, -2.0f, 2.0f, 0.05f);
        this.offHandSection = new SettingGroup("Off Hand");
        this.offHandScale = new SliderSetting("Off Hand Scale", 1.0f, 0.5f, 1.5f, 0.05f);
        this.offHandOffsetX = new SliderSetting("Off Hand X", 0.0f, -2.0f, 2.0f, 0.05f);
        this.offHandOffsetY = new SliderSetting("Off Hand Y", 0.0f, -2.0f, 2.0f, 0.05f);
        this.offHandOffsetZ = new SliderSetting("Off Hand Z", 0.0f, -2.0f, 2.0f, 0.05f);
    }

    public void applyCustomSwing(MatrixStack MatrixStackVar, float f, Runnable runnable, boolean z) {
        float fSin;
        float fMethod_153742;
        float fMax;
        int i;
        if (runnable == null || isNoAnimationMode()) {
            return;
        }
        fSin = MathHelper.sin(MathHelper.sqrt(f) * 3.1415927f);
        fMethod_153742 = MathHelper.sin(f * f * 3.1415927f);
        fMax = Math.max(0.1f, this.swingStrength.a());
        i = z ? -1 : 1;
        switch (this.animationMode.d()) {
            case "Tilt":
                MatrixStackVar.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(i * fSin * fMax * 3.5f));
                MatrixStackVar.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i * fMethod_153742 * (-(fMax * 2.5f))));
                runnable.run();
                break;
            case "Swing":
                MatrixStackVar.translate(i * fSin * 0.08f, (-fMethod_153742) * 0.08f, (-fSin) * 0.12f);
                MatrixStackVar.multiply(RotationAxis.POSITIVE_X.rotationDegrees((-fSin) * fMax * 5.0f));
                MatrixStackVar.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(i * fMethod_153742 * fMax * 3.0f));
                runnable.run();
                break;
            case "Rotate":
                MatrixStackVar.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i * fSin * fMax * 16.0f));
                MatrixStackVar.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(i * fMethod_153742 * fMax * 10.0f));
                runnable.run();
                break;
            case "Grow":
                scaleUniform(MatrixStackVar, 1.0f + (fSin * 0.18f));
                runnable.run();
                break;
            case "Shrink":
                scaleUniform(MatrixStackVar, 1.0f - (fSin * 0.16f));
                runnable.run();
                break;
            case "Stretch":
                MatrixStackVar.scale(1.0f + (fSin * 0.12f), 1.0f - (fSin * 0.08f), 1.0f + (fMethod_153742 * 0.18f));
                runnable.run();
                break;
            case "Normal":
            default:
                runnable.run();
                break;
        }
    }

    public boolean hasCustomSwingAnimation() {
        return !isNoAnimationMode();
    }

    public int shaderModeIndex() {
        return this.shaderMode.k().intValue();
    }

    public Color handShaderColor() {
        ClientColor clientColor;
        return (!this.useClientColor.a() || (clientColor = ModuleRegistry.CLIENT_COLOR) == null) ? this.shaderColor.a() : clientColor.n();
    }

    public boolean isNoAnimationMode() {
        return this.animationMode.b(MODE_NO_ANIMATION);
    }

    public void a(MatrixStack MatrixStackVar, float f, Runnable runnable, boolean z) {
        applyCustomSwing(MatrixStackVar, f, runnable, z);
    }

    public boolean n() {
        return hasCustomSwingAnimation();
    }

    public int o() {
        return shaderModeIndex();
    }

    public Color p() {
        return handShaderColor();
    }

    private static void scaleUniform(MatrixStack MatrixStackVar, float f) {
        MatrixStackVar.scale(f, f, f);
    }
}
