package drunvisual.hud.elements;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.item.Item;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.inventory.CooldownInfo;
import drunvisual.module.ModuleRegistry;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.core.Bool;
import drunvisual.hud.core.HudElement;
import drunvisual.hud.core.HudIcons;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;

public class CooldownsHudElement extends HudElement {
    private static final Color m;
    private static final Color n;
    private static final Color o;
    private static final Color p;
    private static final Color q;
    private static final Color r;
    private static final Color s;
    private static final Color t;
    private static final Color u;
    private static final Color v;
    private static final Color w;
    private static final String HUD_TITLE = "Cooldowns";
    private static final float HUD_HEADER_ICON_BASE = 10.0f;
    private static final float HUD_HEADER_ICON_GAP = 4.0f;
    private static final float x = 8.5f;
    private static final float y = 8.0f;
    private static final float z = 24.0f;
    private static final float A = 16.0f;
    private static final float B = 5.0f;
    private static final float C = 10.0f;
    private static final float D = 2.5f;
    private static final float E = 12.0f;
    private static final float F = 2.0f;
    private static final float G = 12.0f;
    private static final float H = 6.0f;
    private static final float I = 6.5f;
    private static final float J = 0.5f;
    private static final float K = 100.0f;
    private static final double L = 0.2d;
    private static final long M = 1000;
    private final Map<Item, ActiveCooldownEntry> O;
    private final List<CooldownDefinition> P;
    private final AnimationState Q;
    private final AnimationState R;
    private final AnimationState S;
    private long T;
    private int U;
    private boolean V;
    private boolean W;
    private boolean X;
    private List<CooldownSnapshot> Y;
    private static final Color WHITE = new Color(255, 255, 255, 255);
    private static final Identifier HUD_HEADER_ICON = HudIcons.clickGui("cooldowns");
    private static final ItemRenderState N = new ItemRenderState();
    private static final Map<Item, String> ITEM_DISPLAY_NAMES = new HashMap();

    private static class ActiveCooldownEntry {
        final Item a;
        ItemStack b;
        String c;
        String d;
        int e;
        final AnimationState f = new AnimationState();
        final AnimationState g = new AnimationState();
        boolean h = false;

        ActiveCooldownEntry(Item ItemVar, ItemStack ItemStackVar, String str, String str2, int i) {
            this.a = ItemVar;
            this.b = ItemStackVar;
            this.c = str;
            this.d = str2;
            this.e = i;
        }

        public static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return null;
        }
    }

    private static class CooldownDefinition {
        final Item a;
        final String b;
        final String c;

        CooldownDefinition(Item ItemVar, String str, String str2) {
            this.a = ItemVar;
            this.b = str;
            this.c = str2;
        }

        public static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return null;
        }
    }

    private static class CooldownSnapshot {
        final Item a;
        final ItemStack b;
        final String c;
        final String d;
        final int e;

        CooldownSnapshot(Item ItemVar, ItemStack ItemStackVar, String str, String str2, int i) {
            this.a = ItemVar;
            this.b = ItemStackVar;
            this.c = str;
            this.d = str2;
            this.e = i;
        }

        public static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return null;
        }
    }

    public CooldownsHudElement(float f, float f2) {
        super(f, f2);
        this.O = new LinkedHashMap<>();
        this.P = new ArrayList();
        this.Q = new AnimationState();
        this.R = new AnimationState();
        this.S = new AnimationState();
        this.T = 0L;
        this.Y = List.of();
        this.U = 0;
        this.V = false;
        this.W = false;
        this.X = false;
        this.S.d(0.0d);
        u();
        a();
    }

    private void u() {
        this.P.add(new CooldownDefinition(Items.ENDER_PEARL, "Ender Pearl", "0:00"));
        this.P.add(new CooldownDefinition(Items.CHORUS_FRUIT, "Chorus Fruit", "0:00"));
        this.P.add(new CooldownDefinition(Items.ENDER_EYE, "Дезка", "0:00"));
    }

    private boolean v() {
        return Bool.from(((this.a.currentScreen instanceof ChatScreen) && x().isEmpty()) ? 1 : 0);
    }

    private void w() {
        if (this.X || ModuleRegistry.COOLDOWNS_HUD == null) {
            return;
        }
        f().a(ModuleRegistry.COOLDOWNS_HUD);
        this.X = true;
    }

    @Override
    protected void a() {
        w();
        boolean zV = v();
        List<CooldownSnapshot> listX = x();
        this.Y = listX;
        float fG = g();
        int iFontIndex = fontIndex(15.0f * fG);
        int iFontIndex2 = fontIndex(A * fG);
        int iFontIndex3 = fontIndex(14.0f * fG);
        FontRenderer fontRenderer = FontManager.MEDIUM[iFontIndex];
        FontRenderer fontRenderer2 = FontManager.REGULAR[iFontIndex2];
        FontRenderer fontRenderer3 = FontManager.REGULAR[iFontIndex3];
        if (zV) {
            this.O.clear();
        } else {
            HashSet hashSet = new HashSet();
            for (CooldownSnapshot cooldownSnapshot : listX) {
                hashSet.add(cooldownSnapshot.a);
                ActiveCooldownEntry activeCooldownEntry = this.O.get(cooldownSnapshot.a);
                if (activeCooldownEntry == null) {
                    ActiveCooldownEntry activeCooldownEntry2 = new ActiveCooldownEntry(cooldownSnapshot.a, cooldownSnapshot.b, cooldownSnapshot.c, cooldownSnapshot.d, cooldownSnapshot.e);
                    activeCooldownEntry2.f.d(0.0d);
                    activeCooldownEntry2.f.a(1.0d, L, Easing.h);
                    this.O.put(cooldownSnapshot.a, activeCooldownEntry2);
                } else {
                    activeCooldownEntry.b = cooldownSnapshot.b;
                    activeCooldownEntry.c = cooldownSnapshot.c;
                    activeCooldownEntry.d = cooldownSnapshot.d;
                    activeCooldownEntry.e = cooldownSnapshot.e;
                    activeCooldownEntry.h = false;
                    if (activeCooldownEntry.f.j() < 1.0d) {
                        activeCooldownEntry.f.a(1.0d, L, Easing.h);
                    }
                }
            }
            for (ActiveCooldownEntry activeCooldownEntry3 : new ArrayList<>(this.O.values())) {
                if (!hashSet.contains(activeCooldownEntry3.a) && !activeCooldownEntry3.h) {
                    activeCooldownEntry3.h = true;
                    activeCooldownEntry3.f.a(0.0d, L, Easing.h);
                }
            }
            this.O.entrySet().removeIf(entry -> {
                return ((ActiveCooldownEntry) entry.getValue()).h && ((ActiveCooldownEntry) entry.getValue()).f.j() <= 0.01d;
            });
        }
        Iterator<ActiveCooldownEntry> it = this.O.values().iterator();
        while (it.hasNext()) {
            it.next().f.a();
        }
        if (zV || !listX.isEmpty()) {
            if (!this.W) {
                this.S.a(1.0d, L, Easing.h);
                this.W = true;
            }
        } else if (this.W) {
            this.S.a(0.0d, L, Easing.h);
            this.W = false;
        }
        this.S.a();
        float f = x * fG;
        float f2 = z * fG;
        float f3 = 12.0f * fG;
        float f4 = H * fG;
        float fMax = 110.0f * fG;
        if (!zV || this.P.isEmpty()) {
            for (ActiveCooldownEntry activeCooldownEntry4 : this.O.values()) {
                if (activeCooldownEntry4.f.j() > 0.01d) {
                    fMax = Math.max(fMax, rowWidth(fontRenderer2, fontRenderer3, activeCooldownEntry4.c, activeCooldownEntry4.d, fG));
                }
            }
        } else {
            fMax = Math.max(fMax, rowWidth(fontRenderer2, fontRenderer3, this.P.get(this.U % this.P.size()).b, formatCooldownTime(24), fG));
        }
        float fMax2 = Math.max(fMax, f + (10.0f * fG) + (HUD_HEADER_ICON_GAP * fG) + fontRenderer.a(HUD_TITLE) + f);
        float f5 = 0.0f;
        int rowCount = zV ? 1 : listX.size();
        if (rowCount > 1) {
            f5 = ((rowCount - 1) * f3) + ((rowCount - 1) * 2.0f * fG);
        }
        float f6 = fMax2 + f;
        float f7 = f2 + f4 + f5 + (10.0f * fG) + (10.0f * fG);
        if (this.V) {
            if (Math.abs(this.Q.j() - ((double) f6)) > 0.5d) {
                this.Q.a(f6, L, Easing.h);
            }
            if (Math.abs(this.R.j() - ((double) f7)) > 0.5d) {
                this.R.a(f7, L, Easing.h);
            }
        } else {
            this.Q.d(f6);
            this.R.d(f7);
            this.V = true;
        }
        this.Q.a();
        this.R.a();
        this.d = (float) this.Q.j();
        this.e = (float) this.R.j();
        if (!zV || this.P.size() <= 1) {
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.T >= M) {
            this.T = jCurrentTimeMillis;
            this.U = (this.U + 1) % this.P.size();
        }
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2) {
        if (this.a.player == null) {
            return;
        }
        a();
        float fJ = (float) this.S.j();
        if (fJ < 0.01f) {
            return;
        }
        boolean zV = v();
        float fG = g();
        int iFontIndex = fontIndex(15.0f * fG);
        int iFontIndex2 = fontIndex(A * fG);
        int iFontIndex3 = fontIndex(14.0f * fG);
        FontRenderer fontRenderer = FontManager.MEDIUM[iFontIndex];
        FontRenderer fontRenderer2 = FontManager.REGULAR[iFontIndex2];
        FontRenderer fontRenderer3 = FontManager.REGULAR[iFontIndex3];
        float f3 = x * fG;
        float f4 = z * fG;
        float f5 = 12.0f * fG;
        float f6 = H * fG;
        float f7 = I * fG;
        float f8 = this.b;
        float f9 = this.c;
        Color colorA = a(new Color(13, 13, 17, 255), fJ);
        Color colorA2 = a(new Color(17, 17, 23, 255), fJ);
        renderer2D.a(f8, f9, this.d, this.e, f7, colorA, colorA, colorA2, colorA2, MatrixStackVar);
        DrawContext DrawContextVar = new DrawContext(this.a, this.a.getBufferBuilders().getEntityVertexConsumers());
        float f10 = 20.0f * fG;
        float f11 = HUD_HEADER_ICON_GAP * fG;
        drawClickGuiIcon(renderer2D, MatrixStackVar, HUD_HEADER_ICON, f8 + f3, (f9 + (f4 / F)) - (f10 / F), f10, fJ);
        fontRenderer.a(HUD_TITLE, f8 + f3 + f10 + f11, (f9 + (f4 / F)) - (fontRenderer.b(HUD_TITLE) / HUD_HEADER_ICON_GAP), a(WHITE, fJ), MatrixStackVar);
        float f12 = f9 + f4 + f6;
        float f13 = f8 + f3;
        if (!zV || this.P.isEmpty()) {
            boolean firstRow = true;
            for (CooldownSnapshot cooldownSnapshot : this.Y) {
                ActiveCooldownEntry activeCooldownEntry = this.O.get(cooldownSnapshot.a);
                float fMax = Math.max(0.5f, activeCooldownEntry != null ? (float) activeCooldownEntry.f.j() : 1.0f);
                if (!firstRow) {
                    f12 += 2.0f * fG;
                }
                a(MatrixStackVar, DrawContextVar, renderer2D, fontRenderer2, fontRenderer3, f13, f12, cooldownSnapshot.b, cooldownSnapshot.c, cooldownSnapshot.d, fMax * fJ, fG);
                f12 += 12.0f * fG;
                firstRow = false;
            }
        } else {
            a(MatrixStackVar, DrawContextVar, renderer2D, fontRenderer2, fontRenderer3, f13, f12, this.P.get(this.U % this.P.size()), fJ, fG);
        }
        renderer2D.a(f8, f9 + f4, this.d, 1.0f * fG, 0.0f, a(s, fJ), MatrixStackVar);
    }

    private static int fontIndex(float f) {
        return Math.max(6, Math.min(64, Math.round(f)));
    }

    private static float rowWidth(FontRenderer fontRenderer, FontRenderer fontRenderer2, String str, String str2, float f) {
        float f2 = D * f;
        return (10.0f * f) + f2 + fontRenderer.a(str) + (8.0f * f) + (((fontRenderer2.a(str2) + ((I * f) * F)) + ((J * f) * F)) - (F * f));
    }

    private void a(MatrixStack MatrixStackVar, DrawContext DrawContextVar, Renderer2D renderer2D, FontRenderer fontRenderer, FontRenderer fontRenderer2, float f, float f2, CooldownDefinition cooldownDefinition, float f3, float f4) {
        float f5 = 10.0f * f4;
        float f6 = D * f4;
        float f7 = x * f4;
        float f8 = I * f4;
        float f9 = J * f4;
        float f10 = 12.0f * f4;
        float f11 = H * f4;
        float f12 = f2 + ((12.0f * f4) / F);
        Color colorA = a(WHITE, f3);
        Color colorA2 = a(WHITE, f3);
        a(MatrixStackVar, DrawContextVar, new ItemStack(cooldownDefinition.a), f, f12 - (f5 / F), f5, f3);
        fontRenderer.a(cooldownDefinition.b, f + f5 + f6, f12 - (fontRenderer.b(cooldownDefinition.b) / HUD_HEADER_ICON_GAP), colorA, MatrixStackVar);
        float fA = ((fontRenderer2.a(formatCooldownTime(24)) + (f8 * F)) + (f9 * F)) - (F * f4);
        float f13 = ((this.b + this.d) - f7) - fA;
        float f14 = (f12 - (f10 / F)) - f9;
        float f15 = f10 + (f9 * F);
        Color colorA3 = a(v, f3);
        Color colorA4 = a(w, f3);
        Color colorA5 = a(t, f3);
        Color colorA6 = a(u, f3);
        renderer2D.a(f13, f14, fA, f15, f11, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
        renderer2D.a(f13 + f9, f14 + f9, fA - (f9 * F), f10, f11 - f9, colorA5, colorA5, colorA6, colorA6, MatrixStackVar);
        String strTime = formatCooldownTime(24);
        fontRenderer2.a(strTime, f13 + ((fA - fontRenderer2.a(strTime)) / F), f12 - (fontRenderer2.b(strTime) / HUD_HEADER_ICON_GAP), colorA2, MatrixStackVar);
    }

    private void a(MatrixStack MatrixStackVar, DrawContext DrawContextVar, Renderer2D renderer2D, FontRenderer fontRenderer, FontRenderer fontRenderer2, float f, float f2, ItemStack ItemStackVar, String str, String str2, float f3, float f4) {
        float f5 = 10.0f * f4;
        float f6 = D * f4;
        float f7 = x * f4;
        float f8 = I * f4;
        float f9 = J * f4;
        float f10 = 12.0f * f4;
        float f11 = H * f4;
        float f12 = f2 + ((12.0f * f4) / F);
        Color colorA = a(WHITE, f3);
        Color colorA2 = a(WHITE, f3);
        a(MatrixStackVar, DrawContextVar, ItemStackVar, f, f12 - (f5 / F), f5, f3);
        fontRenderer.a(str, f + f5 + f6, f12 - (fontRenderer.b(str) / HUD_HEADER_ICON_GAP), colorA, MatrixStackVar);
        float fA = ((fontRenderer2.a(str2) + (f8 * F)) + (f9 * F)) - (F * f4);
        float f13 = ((this.b + this.d) - f7) - fA;
        float f14 = (f12 - (f10 / F)) - f9;
        float f15 = f10 + (f9 * F);
        Color colorA3 = a(v, f3);
        Color colorA4 = a(w, f3);
        Color colorA5 = a(t, f3);
        Color colorA6 = a(u, f3);
        renderer2D.a(f13, f14, fA, f15, f11, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
        renderer2D.a(f13 + f9, f14 + f9, fA - (f9 * F), f10, f11 - f9, colorA5, colorA5, colorA6, colorA6, MatrixStackVar);
        fontRenderer2.a(str2, f13 + ((fA - fontRenderer2.a(str2)) / F), f12 - (fontRenderer2.b(str2) / HUD_HEADER_ICON_GAP), colorA2, MatrixStackVar);
    }

    private void drawClickGuiIcon(Renderer2D renderer2D, MatrixStack MatrixStackVar, Identifier IdentifierVar, float f, float f2, float f3, float f4) {
        HudIcons.drawRotated180(renderer2D, MatrixStackVar, IdentifierVar, f, f2, f3, a(WHITE, f4));
    }

    private void a(MatrixStack MatrixStackVar, DrawContext DrawContextVar, ItemStack ItemStackVar, float f, float f2, float f3, float f4) {
        if (this.a.player == null || ItemStackVar.isEmpty() || f4 < 0.01f) {
            return;
        }
        MatrixStack MatrixStackVarGetMatrices = DrawContextVar.getMatrices();
        MatrixStackVarGetMatrices.push();
        MatrixStackVarGetMatrices.translate(f, f2, 0.0f);
        float f5 = f3 / A;
        MatrixStackVarGetMatrices.scale(f5, f5, 1.0f);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, f4);
        DrawContextVar.drawItem(ItemStackVar, 0, 0);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();
        MatrixStackVarGetMatrices.pop();
    }

    private Color a(Color color, float f) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(0, Math.min(255, (int) (color.getAlpha() * f))));
    }

    private List<CooldownSnapshot> x() {
        ArrayList<CooldownSnapshot> arrayList = new ArrayList<>();
        if (this.a.player == null) {
            return arrayList;
        }
        ItemCooldownManager ItemCooldownManagerVarGetItemCooldownManager = this.a.player.getItemCooldownManager();
        HashSet<Identifier> hashSet = new HashSet<>();
        for (CooldownInfo.ActiveGroupCooldown activeGroupCooldown : CooldownInfo.collectActiveGroups(ItemCooldownManagerVarGetItemCooldownManager)) {
            if (hashSet.add(activeGroupCooldown.groupId)) {
                ItemStack ItemStackVarStackForGroup = CooldownInfo.stackForGroup(ItemCooldownManagerVarGetItemCooldownManager, activeGroupCooldown.groupId);
                int iMax = Math.max(0, activeGroupCooldown.remainingTicks);
                arrayList.add(new CooldownSnapshot(ItemStackVarStackForGroup.getItem(), ItemStackVarStackForGroup, ItemStackVarStackForGroup.getName().getString(), formatCooldownTime(iMax), iMax));
            }
        }
        for (int i = 0; i < this.a.player.getInventory().size(); i++) {
            tryAddCooldownStack(ItemCooldownManagerVarGetItemCooldownManager, this.a.player.getInventory().getStack(i), hashSet, arrayList);
        }
        for (Item ItemVar : new Item[]{Items.CHORUS_FRUIT, Items.POPPED_CHORUS_FRUIT, Items.ENDER_PEARL}) {
            tryAddCooldownStack(ItemCooldownManagerVarGetItemCooldownManager, ItemVar.getDefaultStack(), hashSet, arrayList);
        }
        arrayList.sort(Comparator.comparingInt(cooldownSnapshot -> {
            return cooldownSnapshot.e;
        }));
        return arrayList;
    }

    private void tryAddCooldownStack(ItemCooldownManager ItemCooldownManagerVar, ItemStack ItemStackVar, HashSet<Identifier> hashSet, List<CooldownSnapshot> list) {
        if (ItemStackVar.isEmpty()) {
            return;
        }
        Identifier IdentifierVarGetGroup = ItemCooldownManagerVar.getGroup(ItemStackVar);
        if (hashSet.contains(IdentifierVarGetGroup)) {
            return;
        }
        CooldownInfo.ItemCooldownSnapshot itemCooldownSnapshotA = CooldownInfo.a(ItemCooldownManagerVar, IdentifierVarGetGroup);
        int iMax = itemCooldownSnapshotA.a() ? Math.max(0, itemCooldownSnapshotA.c - itemCooldownSnapshotA.a) : 0;
        float fGetCooldownProgress = ItemCooldownManagerVar.getCooldownProgress(ItemStackVar, this.a.getRenderTickCounter().getTickDelta(true));
        if (iMax <= 0 && fGetCooldownProgress > 0.001f) {
            iMax = Math.max(1, Math.round(fGetCooldownProgress * 20.0f));
        }
        if (iMax <= 0) {
            return;
        }
        hashSet.add(IdentifierVarGetGroup);
        list.add(new CooldownSnapshot(ItemStackVar.getItem(), ItemStackVar, a(ItemStackVar), formatCooldownTime(iMax), iMax));
    }

    private static String formatCooldownTime(int i) {
        if (i <= 0) {
            return "0:00";
        }
        float f = i / 20.0f;
        int i2 = (int) f;
        int i3 = i2 / 60;
        return i3 > 0 ? String.format("%d:%02d", Integer.valueOf(i3), Integer.valueOf(i2 % 60)) : f < 10.0f ? String.format("%.1f", Float.valueOf(f)) : String.format("%d", Integer.valueOf(i2));
    }

    private String a(ItemStack ItemStackVar) {
        String str = ITEM_DISPLAY_NAMES.get(ItemStackVar.getItem());
        return str != null ? str : ItemStackVar.getName().getString();
    }

    private String b(int i) {
        return i <= 0 ? "0.0s" : String.format("%.1fs", Float.valueOf(i / 20.0f));
    }

    public static String b(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }

    static {
        ITEM_DISPLAY_NAMES.put(Items.DRIED_KELP, "Пласт");
        ITEM_DISPLAY_NAMES.put(Items.NETHERITE_SCRAP, "Трапка");
        ITEM_DISPLAY_NAMES.put(Items.CHORUS_FRUIT, "Хорус");
        ITEM_DISPLAY_NAMES.put(Items.POPPED_CHORUS_FRUIT, "Хорус");
        ITEM_DISPLAY_NAMES.put(Items.ENDER_PEARL, "Эндер-шар");
        ITEM_DISPLAY_NAMES.put(Items.ENDER_EYE, "Глаз эндера");
        m = new Color(13, 13, 17);
        n = new Color(17, 17, 23);
        o = new Color(255, 255, 255);
        p = new Color(223, 223, 243);
        q = new Color(20, 20, 28);
        r = new Color(255, 255, 255, 255);
        s = new Color(17, 17, 23);
        t = new Color(23, 23, 30);
        u = new Color(18, 18, 24);
        v = new Color(23, 23, 30);
        w = new Color(18, 18, 24);
    }
}
