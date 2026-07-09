package drunvisual.hud.snap;

import java.util.ArrayList;
import java.util.List;
import drunvisual.hud.snap.HudSnapGuide;
import drunvisual.core.Bool;
import drunvisual.hud.core.HudElement;

public class HudSnapCalculator {
    private static final float c = 6.0f;
    private static final float d = 5.0f;
    private static final float e = 10.0f;
    public static int a;
    public static boolean b;

    private static class SnapCandidate {
        final float a;
        final float b;
        final HudSnapGuide c;
        public static int d;
        public static boolean e;

        SnapCandidate(float f, float f2, HudSnapGuide hudSnapGuide) {
            this.a = f;
            this.b = f2;
            this.c = hudSnapGuide;
        }

        public static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return null;
        }
    }

    public static HudSnapResult a(HudElement hudElement, float f, float f2, float f3, float f4, List<HudElement> list, boolean z) {
        if (z) {
            return HudSnapResult.a(Math.max(d, Math.min(f, (f3 - hudElement.n()) - d)), Math.max(d, Math.min(f2, (f4 - hudElement.o()) - d)));
        }
        float fN = hudElement.n();
        float fO = hudElement.o();
        ArrayList arrayList = new ArrayList();
        float f5 = f;
        float f6 = f2;
        int i = 0;
        int i2 = 0;
        float f7 = f + fN;
        float f8 = f + (fN / 2.0f);
        float f9 = f2 + fO;
        float f10 = f2 + (fO / 2.0f);
        SnapCandidate snapCandidateA = a(f, f7, f8, fN, f3, f4, list, hudElement, f2, f9);
        if (snapCandidateA != null) {
            f5 = snapCandidateA.b;
            arrayList.add(snapCandidateA.c);
            i = 1;
        }
        SnapCandidate snapCandidateB = b(f2, f9, f10, fO, f3, f4, list, hudElement, f, f7);
        if (snapCandidateB != null) {
            f6 = snapCandidateB.b;
            arrayList.add(snapCandidateB.c);
        }
        return new HudSnapResult(Math.max(d, Math.min(f5, (f3 - fN) - d)), Math.max(d, Math.min(f6, (f4 - fO) - d)), arrayList, Bool.from(i), Bool.from(i2));
    }

    private static SnapCandidate a(float f, float f2, float f3, float f4, float f5, float f6, List<HudElement> list, HudElement hudElement, float f7, float f8) {
        SnapCandidate snapCandidateA = Math.abs(f - d) < c ? a(null, Math.abs(f - d), d, new HudSnapGuide(HudSnapGuide.Orientation.VERTICAL, HudSnapGuide.Anchor.SCREEN_EDGE, d, Math.max(0.0f, f7 - e), Math.min(f6, f8 + e))) : null;
        if (Math.abs(f2 - (f5 - d)) < c) {
            snapCandidateA = a(snapCandidateA, Math.abs(f2 - (f5 - d)), (f5 - d) - f4, new HudSnapGuide(HudSnapGuide.Orientation.VERTICAL, HudSnapGuide.Anchor.SCREEN_EDGE, f5 - d, Math.max(0.0f, f7 - e), Math.min(f6, f8 + e)));
        }
        float f9 = f5 / 2.0f;
        if (Math.abs(f3 - f9) < c) {
            snapCandidateA = a(snapCandidateA, Math.abs(f3 - f9), f9 - (f4 / 2.0f), new HudSnapGuide(HudSnapGuide.Orientation.VERTICAL, HudSnapGuide.Anchor.SCREEN_CENTER, f9, Math.max(0.0f, f7 - e), Math.min(f6, f8 + e)));
        }
        for (HudElement hudElement2 : list) {
            if (hudElement2 != hudElement) {
                float fL = hudElement2.l();
                float fL2 = hudElement2.l() + hudElement2.n();
                float fL3 = hudElement2.l() + (hudElement2.n() / 2.0f);
                float fM = hudElement2.m();
                float fM2 = hudElement2.m() + hudElement2.o();
                if (Math.abs(f - fL) < c) {
                    snapCandidateA = a(snapCandidateA, Math.abs(f - fL), fL, a(fL, f7, f8, fM, fM2, HudSnapGuide.Anchor.ELEMENT_EDGE));
                }
                if (Math.abs(f2 - fL2) < c) {
                    snapCandidateA = a(snapCandidateA, Math.abs(f2 - fL2), fL2 - f4, a(fL2, f7, f8, fM, fM2, HudSnapGuide.Anchor.ELEMENT_EDGE));
                }
                if (Math.abs(f - fL2) < c) {
                    snapCandidateA = a(snapCandidateA, Math.abs(f - fL2), fL2, a(fL2, f7, f8, fM, fM2, HudSnapGuide.Anchor.ELEMENT_EDGE));
                }
                if (Math.abs(f2 - fL) < c) {
                    snapCandidateA = a(snapCandidateA, Math.abs(f2 - fL), fL - f4, a(fL, f7, f8, fM, fM2, HudSnapGuide.Anchor.ELEMENT_EDGE));
                }
                if (Math.abs(f3 - fL3) < c) {
                    snapCandidateA = a(snapCandidateA, Math.abs(f3 - fL3), fL3 - (f4 / 2.0f), a(fL3, f7, f8, fM, fM2, HudSnapGuide.Anchor.ELEMENT_CENTER));
                }
            }
        }
        return snapCandidateA;
    }

    private static SnapCandidate b(float f, float f2, float f3, float f4, float f5, float f6, List<HudElement> list, HudElement hudElement, float f7, float f8) {
        SnapCandidate snapCandidateA = null;
        if (Math.abs(f - d) < c) {
            snapCandidateA = a(null, Math.abs(f - d), d, new HudSnapGuide(HudSnapGuide.Orientation.HORIZONTAL, HudSnapGuide.Anchor.SCREEN_EDGE, d, Math.max(0.0f, f7 - e), Math.min(f5, f8 + e)));
        } else if (b) {
        }
        if (Math.abs(f2 - (f6 - d)) < c) {
            snapCandidateA = a(snapCandidateA, Math.abs(f2 - (f6 - d)), (f6 - d) - f4, new HudSnapGuide(HudSnapGuide.Orientation.HORIZONTAL, HudSnapGuide.Anchor.SCREEN_EDGE, f6 - d, Math.max(0.0f, f7 - e), Math.min(f5, f8 + e)));
        }
        float f9 = f6 / 2.0f;
        if (Math.abs(f3 - f9) < c) {
            snapCandidateA = a(snapCandidateA, Math.abs(f3 - f9), f9 - (f4 / 2.0f), new HudSnapGuide(HudSnapGuide.Orientation.HORIZONTAL, HudSnapGuide.Anchor.SCREEN_CENTER, f9, Math.max(0.0f, f7 - e), Math.min(f5, f8 + e)));
        }
        for (HudElement hudElement2 : list) {
            if (hudElement2 != hudElement) {
                float fM = hudElement2.m();
                float fM2 = hudElement2.m() + hudElement2.o();
                float fM3 = hudElement2.m() + (hudElement2.o() / 2.0f);
                float fL = hudElement2.l();
                float fL2 = hudElement2.l() + hudElement2.n();
                if (Math.abs(f - fM) < c) {
                    snapCandidateA = a(snapCandidateA, Math.abs(f - fM), fM, b(fM, f7, f8, fL, fL2, HudSnapGuide.Anchor.ELEMENT_EDGE));
                }
                if (Math.abs(f2 - fM2) < c) {
                    snapCandidateA = a(snapCandidateA, Math.abs(f2 - fM2), fM2 - f4, b(fM2, f7, f8, fL, fL2, HudSnapGuide.Anchor.ELEMENT_EDGE));
                }
                if (Math.abs(f - fM2) < c) {
                    snapCandidateA = a(snapCandidateA, Math.abs(f - fM2), fM2, b(fM2, f7, f8, fL, fL2, HudSnapGuide.Anchor.ELEMENT_EDGE));
                }
                if (Math.abs(f2 - fM) < c) {
                    snapCandidateA = a(snapCandidateA, Math.abs(f2 - fM), fM - f4, b(fM, f7, f8, fL, fL2, HudSnapGuide.Anchor.ELEMENT_EDGE));
                }
                if (Math.abs(f3 - fM3) < c) {
                    snapCandidateA = a(snapCandidateA, Math.abs(f3 - fM3), fM3 - (f4 / 2.0f), b(fM3, f7, f8, fL, fL2, HudSnapGuide.Anchor.ELEMENT_CENTER));
                }
            }
        }
        return snapCandidateA;
    }

    private static HudSnapGuide a(float f, float f2, float f3, float f4, float f5, HudSnapGuide.Anchor anchor) {
        float fMin = Math.min(f2, f4) - e;
        return new HudSnapGuide(HudSnapGuide.Orientation.VERTICAL, anchor, f, Math.max(0.0f, fMin), Math.max(f3, f5) + e);
    }

    private static HudSnapGuide b(float f, float f2, float f3, float f4, float f5, HudSnapGuide.Anchor anchor) {
        float fMin = Math.min(f2, f4) - e;
        return new HudSnapGuide(HudSnapGuide.Orientation.HORIZONTAL, anchor, f, Math.max(0.0f, fMin), Math.max(f3, f5) + e);
    }

    private static SnapCandidate a(SnapCandidate snapCandidate, float f, float f2, HudSnapGuide hudSnapGuide) {
        return (snapCandidate == null || f < snapCandidate.a) ? new SnapCandidate(f, f2, hudSnapGuide) : snapCandidate;
    }

    public static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
