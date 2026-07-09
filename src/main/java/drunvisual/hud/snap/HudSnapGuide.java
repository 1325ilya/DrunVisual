package drunvisual.hud.snap;

public class HudSnapGuide {
    private final Orientation orientation;
    private final Anchor anchor;
    private final float position;
    private final float start;
    private final float end;

    public enum Anchor {
        SCREEN_EDGE,
        SCREEN_CENTER,
        ELEMENT_EDGE,
        ELEMENT_CENTER
    }

    public enum Orientation {
        HORIZONTAL,
        VERTICAL
    }

    public HudSnapGuide(Orientation orientation, Anchor anchor, float f, float f2, float f3) {
        this.orientation = orientation;
        this.anchor = anchor;
        this.position = f;
        this.start = f2;
        this.end = f3;
    }

    public Orientation orientation() {
        return this.orientation;
    }

    public Anchor anchor() {
        return this.anchor;
    }

    public float position() {
        return this.position;
    }

    public float start() {
        return this.start;
    }

    public float end() {
        return this.end;
    }

    public Orientation a() {
        return orientation();
    }

    public Anchor b() {
        return anchor();
    }

    public float c() {
        return position();
    }

    public float d() {
        return start();
    }

    public float e() {
        return end();
    }
}
