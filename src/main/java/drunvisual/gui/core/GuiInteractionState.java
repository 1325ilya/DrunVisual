package drunvisual.gui.core;

import net.minecraft.client.util.math.MatrixStack;
import drunvisual.client.MinecraftContext;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.gui.friends.FriendCard;
import drunvisual.render.Renderer2D;
import drunvisual.theme.Theme;

public final class GuiInteractionState {
    private static final GuiInteractionState INSTANCE = new GuiInteractionState();
    private static final int DEFAULT_OVERLAY_ALPHA = 80;
    private static final int STRONG_OVERLAY_ALPHA = 150;
    private final AnimationState overlayAnimation = new AnimationState();
    private boolean overlayVisible;
    private boolean draggingMainPanel;
    private boolean draggingCategoryList;
    private boolean draggingConfigMenu;
    private boolean draggingPopup;
    private boolean strongOverlay;

    private GuiInteractionState() {
        this.overlayAnimation.d(0.0d);
    }

    public static GuiInteractionState a() {
        return INSTANCE;
    }

    public void a(boolean z) {
        setDraggingMainPanel(z);
    }

    public void b(boolean z) {
        this.draggingCategoryList = z;
        updateOverlayAnimation();
    }

    public void c(boolean z) {
        this.draggingConfigMenu = z;
        updateOverlayAnimation();
    }

    public void d(boolean z) {
        this.draggingPopup = z;
        updateOverlayAnimation();
    }

    public void e(boolean z) {
        this.strongOverlay = z;
        updateOverlayAnimation();
    }

    public boolean b() {
        return this.draggingMainPanel || this.draggingCategoryList || this.draggingConfigMenu || this.draggingPopup || this.strongOverlay;
    }

    public boolean c() {
        return this.draggingPopup;
    }

    public boolean d() {
        return this.strongOverlay;
    }

    public boolean e() {
        return this.draggingConfigMenu;
    }

    public void f() {
        this.draggingMainPanel = false;
        this.draggingCategoryList = false;
        this.draggingConfigMenu = false;
        this.draggingPopup = false;
        this.strongOverlay = false;
        this.overlayVisible = false;
        this.overlayAnimation.d(0.0d);
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D) {
        this.overlayAnimation.a();
        float fJ = (float) this.overlayAnimation.j();
        if (fJ <= 0.01f) {
            return;
        }
        renderer2D.a(0.0f, 0.0f, MinecraftContext.d.getWidth() / 2, MinecraftContext.d.getHeight() / 2, Theme.a(Theme.FriendCard, (int) ((this.strongOverlay ? STRONG_OVERLAY_ALPHA : DEFAULT_OVERLAY_ALPHA) * fJ)), MatrixStackVar);
    }

    public float g() {
        return (float) this.overlayAnimation.j();
    }

    private void setDraggingMainPanel(boolean z) {
        this.draggingMainPanel = z;
        updateOverlayAnimation();
    }

    private void updateOverlayAnimation() {
        boolean zB = b();
        if (zB == this.overlayVisible) {
            return;
        }
        this.overlayAnimation.a(zB ? 1.0d : 0.0d, zB ? 0.2d : 0.15d, Easing.h);
        this.overlayVisible = zB;
    }
}
