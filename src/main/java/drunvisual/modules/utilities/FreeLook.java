package drunvisual.modules.utilities;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.option.Perspective;
import org.lwjgl.glfw.GLFW;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.KeySetting;
import drunvisual.settings.ModeSetting;
import drunvisual.events.ClientTickEvent;

@ModuleInfo(a = "Free Look", b = "Lets the camera rotate independently from the player.", c = ModuleCategory.UTILITIES)
public class FreeLook extends ClientModule {
    public static boolean active;
    public static float cameraYaw;
    public static float cameraPitch;
    public final ModeSetting mode = new ModeSetting("Mode", new String[]{"Hold", "Toggle"}, 0);
    public final KeySetting key = new KeySetting("Key", 342);
    private Perspective previousPerspective = Perspective.FIRST_PERSON;
    private boolean activatedByThisModule = false;
    private boolean previousKeyPressed = false;

    @EventHandler
    public void a(ClientTickEvent clientTickEvent) {
        if (c == null || c.player == null || c.world == null || c.getWindow() == null || c.currentScreen != null) {
            resetIfNeeded();
            return;
        }
        int iA = this.key.a();
        if (iA <= -1) {
            resetIfNeeded();
            return;
        }
        boolean z = GLFW.glfwGetKey(c.getWindow().getHandle(), iA) == 1;
        if (this.mode.k().intValue() != 0) {
            if (z && !this.previousKeyPressed) {
                if (active) {
                    disableFreeLook();
                    this.activatedByThisModule = false;
                } else {
                    enableFreeLook();
                    this.activatedByThisModule = true;
                }
            }
        } else if (z && !active) {
            enableFreeLook();
            this.activatedByThisModule = true;
        } else if (!z && active && this.activatedByThisModule) {
            disableFreeLook();
            this.activatedByThisModule = false;
        }
        this.previousKeyPressed = z;
    }

    private void enableFreeLook() {
        if (c == null || c.player == null || c.options == null) {
            return;
        }
        this.previousPerspective = c.options.getPerspective();
        cameraYaw = c.player.getYaw(1.0f);
        cameraPitch = c.player.getPitch(1.0f);
        c.options.setPerspective(Perspective.THIRD_PERSON_BACK);
        active = true;
    }

    private void disableFreeLook() {
        if (c != null && c.options != null) {
            c.options.setPerspective(this.previousPerspective);
        }
        active = false;
    }

    private void resetIfNeeded() {
        if (active && this.activatedByThisModule) {
            disableFreeLook();
        }
        this.activatedByThisModule = false;
        this.previousKeyPressed = false;
    }

    @Override
    public void f() {
        if (active) {
            disableFreeLook();
        }
        this.activatedByThisModule = false;
        this.previousKeyPressed = false;
        super.f();
    }
}
