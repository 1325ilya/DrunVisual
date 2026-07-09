package drunvisual.gui.friends;

import net.minecraft.client.util.math.MatrixStack;
import drunvisual.gui.core.ClickGuiTab;
import drunvisual.gui.core.ClickGuiTabType;
import drunvisual.gui.core.PanelFadeOverlay;
import drunvisual.gui.core.DrunVisualClickGuiScreen;
import drunvisual.gui.core.TabHost;
import drunvisual.gui.core.TabSelector;
import drunvisual.render.Renderer2D;

public class FriendsTab implements TabHost, ClickGuiTab {
    private static final String[] a = {"Список друзей"};
    private static final float b = 20.0f;
    private static final float c = 19.0f;
    private final TabSelector d = new TabSelector(this);
    private final FriendsPanel e = new FriendsPanel();
    private final PanelFadeOverlay f = new PanelFadeOverlay(25, 10.0f, 7.5f);
    private int g;
    private int h;

    @Override
    public String[] c() {
        return a;
    }

    @Override
    public int d() {
        return 0;
    }

    @Override
    public void a(int i) {
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, int i, int i2) {
        this.g = i;
        this.h = i2;
        float fD = DrunVisualClickGuiScreen.d();
        float fE = DrunVisualClickGuiScreen.e();
        this.f.a(MatrixStackVar, renderer2D, f, f2, i, i2);
        this.e.a(MatrixStackVar, renderer2D, f + c, f2 + b, fD - (2.0f * c), (fE - b) - 5.0f, i, i2, 1.0f);
    }

    @Override
    public void a(float f, float f2, int i, int i2) {
        this.e.a(f + c, f2 + b, DrunVisualClickGuiScreen.d() - (2.0f * c), (DrunVisualClickGuiScreen.e() - b) - 5.0f, i, i2);
    }

    @Override
    public void b(float f, float f2, int i, int i2) {
    }

    @Override
    public void c(float f, float f2, int i, int i2) {
        this.e.a(i, i2);
    }

    @Override
    public void a(float f, float f2, int i, int i2, double d, double d2) {
        this.e.a(i, i2, d, d2);
    }

    @Override
    public void a(float f) {
        this.e.a(f, this.g, this.h);
    }

    @Override
    public boolean a(int i, int i2, int i3) {
        return this.e.a(i, i2, i3);
    }

    @Override
    public boolean b() {
        return this.e.c();
    }

    public boolean a(char c2, int i) {
        return this.e.a(c2, i);
    }

    @Override
    public ClickGuiTabType a() {
        return ClickGuiTabType.FRIENDS;
    }

    public FriendsPanel e() {
        return this.e;
    }

    public static String b(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
