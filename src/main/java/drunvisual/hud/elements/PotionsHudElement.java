package drunvisual.hud.elements;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.Registries;
import drunvisual.effects.PotionEffectService;
import drunvisual.module.ModuleRegistry;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.cosmetic.CosmeticModelLoader;
import drunvisual.cosmetic.CosmeticModelRenderer;
import drunvisual.gui.friends.FriendCard;
import drunvisual.hud.core.HudElement;
import drunvisual.hud.core.HudIcons;
import drunvisual.hud.core.HudServiceRegistry;
import drunvisual.model.ModelPart;
import drunvisual.modules.hud.PotionsHud;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.render.icons.IconTextureRegistry;

public class PotionsHudElement extends HudElement {
    private static final float A = 8.5f;
    private static final float B = 8.0f;
    private static final float C = 24.0f;
    private static final float D = 16.0f;
    private static final float E = 5.0f;
    private static final float F = 8.0f;
    private static final float G = 2.5f;
    private static final float H = 12.0f;
    private static final float I = 2.0f;
    private static final float J = 12.0f;
    private static final float K = 6.0f;
    private static final float L = 6.5f;
    private static final float M = 0.5f;
    private static final float N = 112.0f;
    private static final int O = 200;
    private static final double P = 0.2d;
    private static final long Q = 1000;
    private static final float R = 6.0f;
    private static final float S = 1.0f;
    private static final String HUD_TITLE = "Potions";
    private static final float HUD_HEADER_ICON_BASE = 10.0f;
    private static final float HUD_HEADER_ICON_GAP = 4.0f;
    private final Map<RegistryEntry<StatusEffect>, ActiveEffectEntry> T;
    private final List<PotionDefinition> U;
    private final AnimationState V;
    private final AnimationState W;
    private final AnimationState X;
    private final Map<String, ActivePotionEntry> Y;
    private final AnimationState Z;
    private long aa;
    private int FriendCard;
    private boolean CosmeticModelRenderer;
    private boolean CosmeticModelLoader;
    private boolean ModelPart;
    public static boolean n;
    private static final Identifier HUD_HEADER_ICON = HudIcons.clickGui("potions");
    private static final Color o = new Color(13, 13, 17);
    private static final Color p = new Color(17, 17, 23);
    private static final Color q = new Color(255, 255, 255);
    private static final Color r = new Color(223, 223, 243);
    private static final Color s = new Color(20, 20, 28);
    private static final Color t = new Color(16, 16, 21);
    private static final Color u = new Color(17, 17, 23);
    private static final Color v = new Color(23, 23, 30);
    private static final Color w = new Color(18, 18, 24);
    private static final Color x = new Color(255, 100, 100);
    private static final Color y = new Color(115, 83, 255);
    private static final Color z = new Color(70, 46, 174);

    private static class ActiveEffectEntry {
        final RegistryEntry<StatusEffect> a;
        String b;
        String c;
        int d;
        final AnimationState e = new AnimationState();
        final AnimationState f = new AnimationState();
        boolean g = false;
        public static int h;
        public static boolean i;

        ActiveEffectEntry(RegistryEntry<StatusEffect> RegistryEntryVar, String str, String str2, int i2) {
            this.a = RegistryEntryVar;
            this.b = str;
            this.c = str2;
            this.d = i2;
        }

        public static String a(String str, String str2, int i2, int i3, int i4, int i5) {
            return null;
        }
    }

    private static class ActivePotionEntry {
        final String a;
        String b;
        int c;
        RegistryEntry<StatusEffect> d;
        Item e;
        boolean f;
        boolean g;
        final AnimationState h = new AnimationState();
        final AnimationState i = new AnimationState();
        boolean j = false;
        public static int k;

        ActivePotionEntry(String str, String str2, int i, RegistryEntry<StatusEffect> RegistryEntryVar, Item ItemVar, boolean z, boolean z2) {
            this.a = str;
            this.b = str2;
            this.c = i;
            this.d = RegistryEntryVar;
            this.e = ItemVar;
            this.f = z;
            this.g = z2;
        }

        public static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return null;
        }
    }

    private static class EffectEntry {
        final RegistryEntry<StatusEffect> a;
        final String b;
        final String c;
        final int d;
        public static int e;
        public static boolean f;

        EffectEntry(RegistryEntry<StatusEffect> RegistryEntryVar, String str, String str2, int i) {
            this.a = RegistryEntryVar;
            this.b = str;
            this.c = str2;
            this.d = i;
        }

        public static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return null;
        }
    }

    private static class PotionDefinition {
        final RegistryEntry<StatusEffect> a;
        final String b;
        final String c;
        public static int d;
        public static boolean e;

        PotionDefinition(RegistryEntry<StatusEffect> RegistryEntryVar, String str, String str2) {
            this.a = RegistryEntryVar;
            this.b = str;
            this.c = str2;
        }

        public static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return null;
        }
    }

    private static class PotionSnapshot {
        final String a;
        final String b;
        final int c;
        final RegistryEntry<StatusEffect> d;
        final Item e;
        final boolean f;
        final boolean g;
        public static int h;
        public static boolean i;

        PotionSnapshot(String str, String str2, int i2, RegistryEntry<StatusEffect> RegistryEntryVar, Item ItemVar, boolean z, boolean z2) {
            this.a = str;
            this.b = str2;
            this.c = i2;
            this.d = RegistryEntryVar;
            this.e = ItemVar;
            this.f = z;
            this.g = z2;
        }

        public static String a(String str, String str2, int i2, int i3, int i4, int i5) {
            return null;
        }
    }

    public PotionsHudElement(float f, float f2) {
        super(f, f2);
        this.T = new LinkedHashMap();
        this.U = new ArrayList();
        this.V = new AnimationState();
        this.W = new AnimationState();
        this.X = new AnimationState();
        this.Y = new LinkedHashMap();
        this.Z = new AnimationState();
        this.aa = 0L;
        this.FriendCard = 0;
        this.CosmeticModelRenderer = false;
        this.CosmeticModelLoader = false;
        this.ModelPart = false;
        this.X.d(0.0d);
        v();
        a();
    }

    private void u() {
        PotionsHud potionsHud;
        if (this.ModelPart || (potionsHud = ModuleRegistry.POTIONS_HUD) == null) {
            return;
        }
        f().a(potionsHud);
        this.ModelPart = true;
    }

    private void v() {
        this.U.clear();
        addPlaceholderDefinition(StatusEffects.SPEED, "0:30");
        addPlaceholderDefinition(StatusEffects.STRENGTH, "0:45");
        addPlaceholderDefinition(StatusEffects.FIRE_RESISTANCE, "2:00");
    }

    private void addPlaceholderDefinition(RegistryEntry<StatusEffect> RegistryEntryVar, String str) {
        this.U.add(new PotionDefinition(RegistryEntryVar, effectDisplayName(RegistryEntryVar), str));
    }

    private String effectDisplayName(RegistryEntry<StatusEffect> RegistryEntryVar) {
        return Text.translatable(((StatusEffect) RegistryEntryVar.value()).getTranslationKey()).getString();
    }

    private String formatAmplifierSuffix(int i) {
        return i <= 0 ? "" : " " + Text.translatable("potion.potency." + i).getString();
    }

    private String formatEffectDuration(int i) {
        if (i <= 0) {
            return "--:--";
        }
        int iMax = Math.max(0, i / 20);
        int i2 = iMax / 60;
        return i2 > 0 ? String.format("%d:%02d", Integer.valueOf(i2), Integer.valueOf(iMax % 60)) : String.format("%d", Integer.valueOf(iMax));
    }

    private String effectIconKey(RegistryEntry<StatusEffect> RegistryEntryVar) {
        Identifier IdentifierVarGetId = Registries.STATUS_EFFECT.getId((StatusEffect) RegistryEntryVar.value());
        if (IdentifierVarGetId != null) {
            return IdentifierVarGetId.getPath();
        }
        return null;
    }

    private void drawClickGuiIcon(Renderer2D renderer2D, MatrixStack MatrixStackVar, Identifier IdentifierVar, float f, float f2, float f3, float f4) {
        HudIcons.draw(renderer2D, MatrixStackVar, IdentifierVar, f, f2, f3, a(Color.WHITE, f4));
    }

    private void drawEffectIcon(Renderer2D renderer2D, MatrixStack MatrixStackVar, Identifier IdentifierVar, float f, float f2, float f3, Color color) {
        if (color.getAlpha() < 1) {
            return;
        }
        renderer2D.a(IdentifierVar, f, f2, f3, f3, 0.0f, 0.0f, 1.0f, 1.0f, -1.0f, color, MatrixStackVar);
    }

    private boolean w() {
        return ((this.a.currentScreen instanceof ChatScreen) && y().isEmpty()) ? true : false;
    }

    @Override
    protected void a() {
        float f;
        u();
        boolean zW = w();
        List<EffectEntry> listY = y();
        float fG = g();
        float f2 = A * fG;
        float f3 = C * fG;
        float f4 = E * fG;
        float f5 = 8.0f * fG;
        float f6 = G * fG;
        float f7 = 12.0f * fG;
        float f8 = I * fG;
        float f9 = L * fG;
        float f10 = M * fG;
        float f11 = N * fG;
        int iRound = Math.round(15.0f * fG);
        int iRound2 = Math.round(14.0f * fG);
        FontRenderer fontRenderer = FontManager.a[Math.max(10, Math.min(48, iRound))];
        FontRenderer fontRenderer2 = FontManager.a[Math.max(10, Math.min(48, iRound2))];
        if (zW) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis - this.aa >= Q) {
                int i = this.FriendCard;
                this.FriendCard = ((2 * (i | 1)) - (i ^ 1)) % this.U.size();
                this.aa = jCurrentTimeMillis;
            }
        }
        HashSet hashSet = new HashSet();
        Iterator<EffectEntry> it = listY.iterator();
        while (it.hasNext()) {
            hashSet.add(it.next().a);
        }
        for (ActiveEffectEntry activeEffectEntry : this.T.values()) {
            if (!hashSet.contains(activeEffectEntry.a) && !activeEffectEntry.g) {
                activeEffectEntry.g = true;
                activeEffectEntry.e.a(0.0d, P, Easing.h);
            }
        }
        int i2 = 0;
        for (EffectEntry effectEntry : listY) {
            ActiveEffectEntry activeEffectEntry2 = this.T.get(effectEntry.a);
            if (activeEffectEntry2 == null) {
                ActiveEffectEntry activeEffectEntry3 = new ActiveEffectEntry(effectEntry.a, effectEntry.b, effectEntry.c, effectEntry.d);
                activeEffectEntry3.e.d(0.0d);
                activeEffectEntry3.e.a(1.0d, P, Easing.h);
                activeEffectEntry3.f.d(i2);
                this.T.put(effectEntry.a, activeEffectEntry3);
            } else {
                activeEffectEntry2.b = effectEntry.b;
                activeEffectEntry2.c = effectEntry.c;
                activeEffectEntry2.d = effectEntry.d;
                activeEffectEntry2.g = false;
                if (activeEffectEntry2.e.i() < 1.0d) {
                    activeEffectEntry2.e.a(1.0d, P, Easing.h);
                }
                if (Math.abs(activeEffectEntry2.f.i() - ((double) i2)) > 0.01d) {
                    activeEffectEntry2.f.a(i2, P, Easing.h);
                }
            }
            i2++;
        }
        this.T.entrySet().removeIf(entry -> {
            return (!((ActiveEffectEntry) entry.getValue()).g || ((ActiveEffectEntry) entry.getValue()).e.j() >= 0.01d) ? false : true;
        });
        for (ActiveEffectEntry activeEffectEntry4 : this.T.values()) {
            activeEffectEntry4.e.a();
            activeEffectEntry4.f.a();
        }
        List<PotionSnapshot> listEmptyList = !(ModuleRegistry.POTIONS_HUD != null && ModuleRegistry.POTIONS_HUD.n().k().booleanValue()) ? Collections.emptyList() : x();
        HashSet hashSet2 = new HashSet();
        Iterator<PotionSnapshot> it2 = listEmptyList.iterator();
        while (it2.hasNext()) {
            hashSet2.add(it2.next().a);
        }
        for (ActivePotionEntry activePotionEntry : this.Y.values()) {
            if (!hashSet2.contains(activePotionEntry.a) && !activePotionEntry.j) {
                activePotionEntry.j = true;
                activePotionEntry.h.a(0.0d, P, Easing.h);
            }
        }
        int i3 = 0;
        for (PotionSnapshot potionSnapshot : listEmptyList) {
            ActivePotionEntry activePotionEntry2 = this.Y.get(potionSnapshot.a);
            if (activePotionEntry2 == null) {
                ActivePotionEntry activePotionEntry3 = new ActivePotionEntry(potionSnapshot.a, potionSnapshot.b, potionSnapshot.c, potionSnapshot.d, potionSnapshot.e, potionSnapshot.f, potionSnapshot.g);
                activePotionEntry3.h.d(0.0d);
                activePotionEntry3.h.a(1.0d, P, Easing.h);
                activePotionEntry3.i.d(i3);
                this.Y.put(potionSnapshot.a, activePotionEntry3);
            } else {
                activePotionEntry2.b = potionSnapshot.b;
                activePotionEntry2.c = potionSnapshot.c;
                activePotionEntry2.d = potionSnapshot.d;
                activePotionEntry2.e = potionSnapshot.e;
                activePotionEntry2.f = potionSnapshot.f;
                activePotionEntry2.g = potionSnapshot.g;
                activePotionEntry2.j = false;
                if (activePotionEntry2.h.i() < 1.0d) {
                    activePotionEntry2.h.a(1.0d, P, Easing.h);
                }
                if (Math.abs(activePotionEntry2.i.i() - ((double) i3)) <= 0.01d) {
                } else {
                    activePotionEntry2.i.a(i3, P, Easing.h);
                }
            }
            i3++;
        }
        this.Y.entrySet().removeIf(entry2 -> {
            return (!((ActivePotionEntry) entry2.getValue()).j || ((ActivePotionEntry) entry2.getValue()).h.j() >= 0.01d) ? false : true;
        });
        for (ActivePotionEntry activePotionEntry4 : this.Y.values()) {
            activePotionEntry4.h.a();
            activePotionEntry4.i.a();
        }
        int i4 = 0;
        Iterator<ActivePotionEntry> it3 = this.Y.values().iterator();
        while (it3.hasNext()) {
            if (!it3.next().j) {
                i4++;
            }
        }
        boolean z2 = i4 > 0;
        if (z2 && this.Z.i() < 1.0d) {
            this.Z.a(1.0d, P, Easing.h);
        } else if (!z2 && this.Z.i() > 0.0d) {
            this.Z.a(0.0d, P, Easing.h);
        }
        this.Z.a();
        boolean z3 = this.CosmeticModelLoader;
        this.CosmeticModelLoader = false;
        int i5 = 0;
        if (zW) {
            this.CosmeticModelLoader = true;
        } else {
            Iterator<ActiveEffectEntry> it4 = this.T.values().iterator();
            while (it4.hasNext()) {
                if (!it4.next().g) {
                    this.CosmeticModelLoader = true;
                    i5++;
                }
            }
        }
        if (this.CosmeticModelLoader && !z3) {
            this.X.a(1.0d, P, Easing.h);
        } else if (!this.CosmeticModelLoader && z3) {
            this.X.a(0.0d, P, Easing.h);
        } else if (this.CosmeticModelLoader && this.X.i() < 1.0d) {
            this.X.a(1.0d, P, Easing.h);
        }
        this.X.a();
        float fA = 0.0f;
        if (zW) {
            PotionDefinition potionDefinition = this.U.get(this.FriendCard);
            fA = f5 + f6 + fontRenderer.a(potionDefinition.b) + (f6 * I) + fontRenderer2.a(potionDefinition.c) + (f9 * I) + (f10 * I);
        } else {
            for (ActiveEffectEntry activeEffectEntry5 : this.T.values()) {
                if (!activeEffectEntry5.g) {
                    fA = Math.max(fA, f5 + f6 + fontRenderer.a(activeEffectEntry5.b) + (f6 * I) + fontRenderer2.a(activeEffectEntry5.c) + (f9 * I) + (f10 * I));
                }
            }
        }
        for (ActivePotionEntry activePotionEntry5 : this.Y.values()) {
            if (!activePotionEntry5.j) {
                fA = Math.max(fA, f5 + f6 + fontRenderer.a(activePotionEntry5.a) + (f6 * I) + fontRenderer2.a(activePotionEntry5.b) + (f9 * I) + (f10 * I));
            }
        }
        float fMax = (f2 * I) + Math.max(f11, fA);
        float f12 = S * fG;
        if (i5 > 0) {
            f = (i5 - 1) * (f7 + f8);
        } else {
            f = 0.0f;
        }
        float f13 = f;
        float f14 = 0.0f;
        if (i4 > 0) {
            float f15 = 6.0f * fG;
            f14 = f15 + (S * fG) + f15 + (i4 * f7) + ((i4 - 1) * f8);
        }
        float f16 = f3 + f12 + f4 + f13 + f14 + f4 + (I * fG) + (13.0f * fG);
        if (this.CosmeticModelRenderer) {
            if (Math.abs(this.W.i() - ((double) fMax)) > 0.5d) {
                this.W.a(fMax, P, Easing.h);
            }
            if (Math.abs(this.V.i() - ((double) f16)) > 0.5d) {
                this.V.a(f16, P, Easing.h);
            }
        } else {
            this.W.d(fMax);
            this.V.d(f16);
            this.CosmeticModelRenderer = true;
        }
        this.W.a();
        this.V.a();
        this.d = (float) this.W.j();
        this.e = (float) this.V.j();
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2) {
        float f3;
        float f4;
        if (this.a.player != null) {
            a();
            float fJ = (float) this.X.j();
            if (fJ >= 0.01f) {
                boolean zW = w();
                float fG = g();
                float f5 = A * fG;
                float f6 = 8.0f * fG;
                float f7 = C * fG;
                float f8 = E * fG;
                float f9 = 12.0f * fG;
                float f10 = I * fG;
                int iRound = Math.round(D * fG);
                int iRound2 = Math.round(15.0f * fG);
                int iRound3 = Math.round(14.0f * fG);
                boolean z2 = fJ < 0.99f;
                if (z2) {
                    renderer2D.c(this.b, this.c, this.d, this.e, MatrixStackVar);
                }
                float f11 = !z2 ? fJ : S;
                FontRenderer fontRenderer = FontManager.b[Math.max(10, Math.min(48, iRound))];
                FontRenderer fontRenderer2 = FontManager.a[Math.max(10, Math.min(48, iRound2))];
                FontRenderer fontRenderer3 = FontManager.a[Math.max(10, Math.min(48, iRound3))];
                Color colorA = a(o, f11);
                Color colorA2 = a(p, f11);
                renderer2D.a(this.b, this.c, this.d, this.e, f6, colorA, colorA, colorA2, colorA2, MatrixStackVar);
                float f12 = this.b + f5;
                float f13 = this.c + (f7 / I);
                float f14 = HUD_HEADER_ICON_BASE * 2.0f * fG;
                float f15 = HUD_HEADER_ICON_GAP * fG;
                drawClickGuiIcon(renderer2D, MatrixStackVar, HUD_HEADER_ICON, f12, f13 - (f14 / I), f14, f11);
                FontRenderer fontRenderer4 = FontManager.e[Math.max(12, Math.min(48, Math.round(22.0f * fG)))];
                fontRenderer4.a("crypt");
                fontRenderer4.b("crypt");
                fontRenderer.a(HUD_TITLE, f12 + f14 + f15, (f13 - (fontRenderer.b(HUD_TITLE) / HUD_HEADER_ICON_GAP)) + (S * fG), a(q, f11), MatrixStackVar);
                float f16 = this.c + f7;
                renderer2D.a(this.b, f16, this.d, S * fG, 0.0f, a(s, f11), MatrixStackVar);
                renderer2D.b().a(this.b, f16 + (S * fG), this.d, (this.e - f7) - (S * fG), 0.0f, MatrixStackVar);
                float f17 = f16 + (S * fG) + f8;
                int i = 0;
                if (zW) {
                    i = 1;
                } else {
                    Iterator<ActiveEffectEntry> it = this.T.values().iterator();
                    while (it.hasNext()) {
                        if (!it.next().g) {
                            i++;
                        }
                    }
                }
                if (zW) {
                    a(MatrixStackVar, renderer2D, fontRenderer2, fontRenderer3, f12, f17, this.U.get(this.FriendCard), f11, fG);
                } else {
                    for (ActiveEffectEntry activeEffectEntry : this.T.values()) {
                        float fJ2 = (float) activeEffectEntry.e.j();
                        if (z2) {
                            f4 = fJ2;
                        } else {
                            f4 = fJ2 * fJ;
                        }
                        float f18 = f4;
                        if (f18 >= 0.01f) {
                            a(MatrixStackVar, renderer2D, fontRenderer2, fontRenderer3, f12, f17 + (((float) activeEffectEntry.f.j()) * (f9 + f10)), activeEffectEntry, f18, fG);
                        }
                    }
                }
                float fJ3 = (float) this.Z.j();
                if (fJ3 > 0.01f && !zW) {
                    float f19 = 6.0f * fG;
                    float f20 = S * fG;
                    float f21 = f17 + (i * (f9 + f10));
                    if (i > 0) {
                        f21 -= f10;
                    }
                    float f22 = f21 + f19;
                    if (z2) {
                        f3 = 1.0f;
                    } else {
                        f3 = fJ;
                    }
                    renderer2D.a(this.b, f22, this.d, f20, 0.0f, a(s, fJ3 * f3), MatrixStackVar);
                    float f23 = f22 + f20 + f19;
                    for (ActivePotionEntry activePotionEntry : this.Y.values()) {
                        float fJ4 = ((float) activePotionEntry.h.j()) * fJ3 * (!z2 ? fJ : S);
                        if (fJ4 >= 0.01f) {
                            a(MatrixStackVar, renderer2D, fontRenderer2, fontRenderer3, f12, f23 + (((float) activePotionEntry.i.j()) * (f9 + f10)), activePotionEntry, fJ4, fG);
                        }
                    }
                }
                renderer2D.b().a(MatrixStackVar);
                if (z2) {
                    renderer2D.a(fJ, MatrixStackVar);
                }
            }
        }
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, FontRenderer fontRenderer, FontRenderer fontRenderer2, float f, float f2, PotionDefinition potionDefinition, float f3, float f4) {
        float f5 = 8.0f * f4;
        float f6 = G * f4;
        float f7 = A * f4;
        float f8 = L * f4;
        float f9 = M * f4;
        float f10 = 12.0f * f4;
        float f11 = 6.0f * f4;
        float f12 = f2 + ((12.0f * f4) / I);
        Color colorA = a(r, f3);
        Color colorA2 = a(Color.WHITE, f3);
        Identifier IdentifierVarB = b(potionDefinition.a);
        float f13 = f12 - (f5 / I);
        if (IdentifierVarB != null) {
            drawEffectIcon(renderer2D, MatrixStackVar, IdentifierVarB, f, f13, f5, colorA2);
        } else {
            renderer2D.a(f, f13, f5, f5, I * f4, colorA, MatrixStackVar);
        }
        fontRenderer.a(potionDefinition.b, f + f5 + f6, f12 - (fontRenderer.b(potionDefinition.b) / HUD_HEADER_ICON_GAP), colorA, MatrixStackVar);
        float fA = ((fontRenderer2.a(potionDefinition.c) + (f8 * I)) + (f9 * I)) - (I * f4);
        float f14 = ((this.b + this.d) - f7) - fA;
        float f15 = (f12 - (f10 / I)) - f9;
        float f16 = f10 + (f9 * I);
        Color colorA3 = a(v, f3);
        Color colorA4 = a(w, f3);
        Color colorA5 = a(t, f3);
        Color colorA6 = a(u, f3);
        renderer2D.a(f14, f15, fA, f16, f11, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
        renderer2D.a(f14 + f9, f15 + f9, fA - (f9 * I), f10, f11 - f9, colorA5, colorA5, colorA6, colorA6, MatrixStackVar);
        fontRenderer2.a(potionDefinition.c, f14 + ((fA - fontRenderer2.a(potionDefinition.c)) / I), f12 - (fontRenderer2.b(potionDefinition.c) / HUD_HEADER_ICON_GAP), colorA, MatrixStackVar);
        float fA2 = f + f5 + f6 + fontRenderer.a(potionDefinition.b) + f6;
        float f17 = f14 - f6;
        if (f17 <= fA2 + (E * f4)) {
            return;
        }
        renderer2D.a(fA2, f12 - (M * f4), f17 - fA2, S * f4, 0.0f, a(s, f3), MatrixStackVar);
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, FontRenderer fontRenderer, FontRenderer fontRenderer2, float f, float f2, ActiveEffectEntry activeEffectEntry, float f3, float f4) {
        Color colorA;
        float f5 = 8.0f * f4;
        float f6 = G * f4;
        float f7 = A * f4;
        float f8 = L * f4;
        float f9 = M * f4;
        float f10 = 12.0f * f4;
        float f11 = 6.0f * f4;
        float f12 = f2 + ((12.0f * f4) / I);
        Color colorA2 = a(r, f3);
        Color colorA3 = a(Color.WHITE, f3);
        Identifier IdentifierVarB = b(activeEffectEntry.a);
        float f13 = f12 - (f5 / I);
        if (IdentifierVarB != null) {
            drawEffectIcon(renderer2D, MatrixStackVar, IdentifierVarB, f, f13, f5, colorA3);
        } else {
            renderer2D.a(f, f13, f5, f5, I * f4, colorA2, MatrixStackVar);
        }
        fontRenderer.a(activeEffectEntry.b, f + f5 + f6, f12 - (fontRenderer.b(activeEffectEntry.b) / HUD_HEADER_ICON_GAP), colorA2, MatrixStackVar);
        float fA = ((fontRenderer2.a(activeEffectEntry.c) + (f8 * I)) + (f9 * I)) - (I * f4);
        float f14 = ((this.b + this.d) - f7) - fA;
        float f15 = (f12 - (f10 / I)) - f9;
        float f16 = f10 + (f9 * I);
        Color colorA4 = a(v, f3);
        Color colorA5 = a(w, f3);
        Color colorA6 = a(t, f3);
        Color colorA7 = a(u, f3);
        renderer2D.a(f14, f15, fA, f16, f11, colorA4, colorA4, colorA5, colorA5, MatrixStackVar);
        renderer2D.a(f14 + f9, f15 + f9, fA - (f9 * I), f10, f11 - f9, colorA6, colorA6, colorA7, colorA7, MatrixStackVar);
        if (activeEffectEntry.d <= 0 || activeEffectEntry.d > 200) {
            colorA = colorA2;
        } else {
            colorA = a(a(r, x, (float) ((Math.sin(System.currentTimeMillis() / 150.0d) * 0.5d) + 0.5d)), f3);
        }
        fontRenderer2.a(activeEffectEntry.c, f14 + ((fA - fontRenderer2.a(activeEffectEntry.c)) / I), f12 - (fontRenderer2.b(activeEffectEntry.c) / HUD_HEADER_ICON_GAP), colorA, MatrixStackVar);
        float fA2 = f + f5 + f6 + fontRenderer.a(activeEffectEntry.b) + f6;
        float f17 = f14 - f6;
        if (f17 > fA2 + (E * f4)) {
            renderer2D.a(fA2, f12 - (M * f4), f17 - fA2, S * f4, 0.0f, a(s, f3), MatrixStackVar);
        }
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, FontRenderer fontRenderer, FontRenderer fontRenderer2, float f, float f2, ActivePotionEntry activePotionEntry, float f3, float f4) {
        Color colorA;
        float f5 = 8.0f * f4;
        float f6 = G * f4;
        float f7 = A * f4;
        float f8 = L * f4;
        float f9 = M * f4;
        float f10 = 12.0f * f4;
        float f11 = 6.0f * f4;
        float f12 = f2 + ((12.0f * f4) / I);
        Color colorA2 = a(r, f3);
        Color colorA3 = a(Color.WHITE, f3);
        float f13 = f12 - (f5 / I);
        if (activePotionEntry.f && activePotionEntry.d != null) {
            Identifier IdentifierVarB = b(activePotionEntry.d);
            if (IdentifierVarB != null) {
                drawEffectIcon(renderer2D, MatrixStackVar, IdentifierVarB, f, f13, f5, colorA3);
            } else {
                renderer2D.a(f, f13, f5, f5, I * f4, colorA2, MatrixStackVar);
            }
        } else if (activePotionEntry.e != null) {
            a(MatrixStackVar, new ItemStack(activePotionEntry.e), f, f13, f5, f3);
        } else {
            renderer2D.a(f, f13, f5, f5, I * f4, colorA2, MatrixStackVar);
        }
        fontRenderer.a(activePotionEntry.a, f + f5 + f6, f12 - (fontRenderer.b(activePotionEntry.a) / HUD_HEADER_ICON_GAP), colorA2, MatrixStackVar);
        float fA = ((fontRenderer2.a(activePotionEntry.b) + (f8 * I)) + (f9 * I)) - (I * f4);
        float f14 = ((this.b + this.d) - f7) - fA;
        float f15 = (f12 - (f10 / I)) - f9;
        float f16 = f10 + (f9 * I);
        Color colorA4 = a(v, f3);
        Color colorA5 = a(w, f3);
        Color colorA6 = a(t, f3);
        Color colorA7 = a(u, f3);
        renderer2D.a(f14, f15, fA, f16, f11, colorA4, colorA4, colorA5, colorA5, MatrixStackVar);
        renderer2D.a(f14 + f9, f15 + f9, fA - (f9 * I), f10, f11 - f9, colorA6, colorA6, colorA7, colorA7, MatrixStackVar);
        if (activePotionEntry.c <= 0 || activePotionEntry.c > 200) {
            colorA = colorA2;
        } else {
            colorA = a(a(r, x, (float) ((Math.sin(System.currentTimeMillis() / 150.0d) * 0.5d) + 0.5d)), f3);
        }
        fontRenderer2.a(activePotionEntry.b, f14 + ((fA - fontRenderer2.a(activePotionEntry.b)) / I), f12 - (fontRenderer2.b(activePotionEntry.b) / HUD_HEADER_ICON_GAP), colorA, MatrixStackVar);
        float fA2 = f + f5 + f6 + fontRenderer.a(activePotionEntry.a) + f6;
        float f17 = f14 - f6;
        if (f17 > fA2 + (E * f4)) {
            renderer2D.a(fA2, f12 - (M * f4), f17 - fA2, S * f4, 0.0f, a(s, f3), MatrixStackVar);
        }
    }

    private void a(MatrixStack MatrixStackVar, ItemStack ItemStackVar, float f, float f2, float f3, float f4) {
        if (this.a.player == null || ItemStackVar.isEmpty() || f4 < 0.01f) {
            return;
        }
        DrawContext DrawContextVar = new DrawContext(this.a, this.a.getBufferBuilders().getEntityVertexConsumers());
        MatrixStack MatrixStackVarGetMatrices = DrawContextVar.getMatrices();
        MatrixStackVarGetMatrices.push();
        MatrixStackVarGetMatrices.translate(f, f2, 0.0f);
        float f5 = f3 / D;
        MatrixStackVarGetMatrices.scale(f5, f5, S);
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(S, S, S, f4);
        DrawContextVar.drawItem(ItemStackVar, 0, 0);
        RenderSystem.setShaderColor(S, S, S, S);
        RenderSystem.disableBlend();
        MatrixStackVarGetMatrices.pop();
    }

    private Color a(Color color, float f) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(0, Math.min(255, (int) (color.getAlpha() * f))));
    }

    private Color a(Color color, Color color2, float f) {
        float fMax = Math.max(0.0f, Math.min(S, f));
        float red = color.getRed();
        int red2 = color2.getRed();
        int red3 = color.getRed();
        int i = (int) (red + (((2 * (red2 & (red3 ^ (-1)))) - (red2 ^ red3)) * fMax));
        float green = color.getGreen();
        int green2 = color2.getGreen();
        int green3 = color.getGreen();
        int i2 = (int) (green + (((green2 & (green3 ^ (-1))) - ((green2 ^ (-1)) & green3)) * fMax));
        float blue = color.getBlue();
        int blue2 = color2.getBlue();
        int blue3 = color.getBlue();
        int i3 = (int) (blue + (((blue2 & (blue3 ^ (-1))) - ((blue2 ^ (-1)) & blue3)) * fMax));
        float alpha = color.getAlpha();
        int alpha2 = color2.getAlpha();
        int alpha3 = color.getAlpha();
        return new Color(i, i2, i3, (int) (alpha + (((2 * (alpha2 & (alpha3 ^ (-1)))) - (alpha2 ^ alpha3)) * fMax)));
    }

    private List<PotionSnapshot> x() {
        ArrayList arrayList = new ArrayList();
        PotionEffectService potionEffectService = HudServiceRegistry.POTION_EFFECTS;
        if (potionEffectService == null) {
            return arrayList;
        }
        for (PotionEffectService.PotionEffectInfo potionEffectInfo : potionEffectService.g()) {
            arrayList.add(new PotionSnapshot(potionEffectInfo.a(), c(potionEffectInfo.e()), potionEffectInfo.e(), potionEffectInfo.c(), potionEffectInfo.b(), potionEffectInfo.d(), potionEffectInfo.h()));
        }
        return arrayList;
    }

    private List<EffectEntry> y() {
        ArrayList arrayList = new ArrayList();
        if (this.a.player == null) {
            return arrayList;
        }
        for (Map.Entry entry : this.a.player.getActiveStatusEffects().entrySet()) {
            RegistryEntry<StatusEffect> RegistryEntryVar = (RegistryEntry) entry.getKey();
            StatusEffectInstance StatusEffectInstanceVar = (StatusEffectInstance) entry.getValue();
            String str = effectDisplayName(RegistryEntryVar) + formatAmplifierSuffix(StatusEffectInstanceVar.getAmplifier());
            int iGetDuration = StatusEffectInstanceVar.getDuration();
            arrayList.add(new EffectEntry(RegistryEntryVar, str, formatEffectDuration(iGetDuration), iGetDuration));
        }
        arrayList.sort(Comparator.comparingInt((EffectEntry effectEntry) -> effectEntry.d));
        return arrayList;
    }

    private String a(RegistryEntry<StatusEffect> RegistryEntryVar) {
        return effectDisplayName(RegistryEntryVar);
    }

    private String a(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        String[] strArrSplit = str.split("_");
        StringBuilder sb = new StringBuilder();
        for (String str2 : strArrSplit) {
            if (!str2.isEmpty()) {
                if (!sb.isEmpty()) {
                    sb.append(' ');
                }
                sb.append(Character.toUpperCase(str2.charAt(0)));
                if (str2.length() > 1) {
                    sb.append(str2.substring(1).toLowerCase());
                }
            }
        }
        return sb.toString().trim();
    }

    private String b(int i) {
        return formatAmplifierSuffix(i).trim();
    }

    private String c(int i) {
        return formatEffectDuration(i);
    }

    private Identifier b(RegistryEntry<StatusEffect> RegistryEntryVar) {
        Identifier id = Registries.STATUS_EFFECT.getId((StatusEffect) RegistryEntryVar.value());
        if (id == null) {
            return null;
        }
        return Identifier.of(id.getNamespace(), "textures/mob_effect/" + id.getPath() + ".png");
    }

    private String c(RegistryEntry<StatusEffect> RegistryEntryVar) {
        return effectIconKey(RegistryEntryVar);
    }

    public static String b(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
