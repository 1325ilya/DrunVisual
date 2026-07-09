package drunvisual.animation;

public final class Easing {
    public static final double BACK_OVERSHOOT = 1.70158d;
    public static final double BACK_OVERSHOOT_IN_OUT = 2.5949095d;
    public static final double BACK_OVERSHOOT_PLUS_ONE = 2.70158d;
    public static final double ELASTIC_PERIOD = 2.0943951023931953d;
    public static final double ELASTIC_PERIOD_IN_OUT = 1.3962634015954636d;
    public static final EasingFunction f = d -> {
        return d;
    };
    public static final EasingFunction g = a(2.0d);
    public static final EasingFunction h = b(2.0d);
    public static final EasingFunction i = c(2.0d);
    public static final EasingFunction j = a(3.0d);
    public static final EasingFunction k = b(3.0d);
    public static final EasingFunction l = c(3.0d);
    public static final EasingFunction m = a(4.0d);
    public static final EasingFunction n = b(4.0d);
    public static final EasingFunction o = c(4.0d);
    public static final EasingFunction p = a(5.0d);
    public static final EasingFunction q = b(5.0d);
    public static final EasingFunction r = c(5.0d);
    public static final EasingFunction s = d -> {
        return 1.0d - Math.cos((d * 3.141592653589793d) / 2.0d);
    };
    public static final EasingFunction t = d -> {
        return Math.sin((d * 3.141592653589793d) / 2.0d);
    };
    public static final EasingFunction u = d -> {
        return (-(Math.cos(3.141592653589793d * d) - 1.0d)) / 2.0d;
    };
    public static final EasingFunction v = d -> {
        return 1.0d - Math.sqrt(1.0d - Math.pow(d, 2.0d));
    };
    public static final EasingFunction w = d -> {
        return Math.sqrt(1.0d - Math.pow(d - 1.0d, 2.0d));
    };
    public static final EasingFunction x = d -> {
        return d < 0.5d ? (1.0d - Math.sqrt(1.0d - Math.pow(2.0d * d, 2.0d))) / 2.0d : (Math.sqrt(1.0d - Math.pow(((-2.0d) * d) + 2.0d, 2.0d)) + 1.0d) / 2.0d;
    };
    public static final EasingFunction y = d -> {
        if (d == 0.0d) {
            return 0.0d;
        }
        return Math.pow(2.0d, (10.0d * d) - 10.0d);
    };
    public static final EasingFunction z = d -> {
        if (d == 1.0d) {
            return 1.0d;
        }
        return 1.0d - Math.pow(2.0d, (-10.0d) * d);
    };
    public static final EasingFunction A = d -> {
        return (d == 0.0d || d == 1.0d) ? d : d < 0.5d ? Math.pow(2.0d, (20.0d * d) - 10.0d) / 2.0d : (2.0d - Math.pow(2.0d, ((-20.0d) * d) + 10.0d)) / 2.0d;
    };
    public static final EasingFunction B = d -> {
        return (2.70158d * Math.pow(d, 3.0d)) - (1.70158d * Math.pow(d, 2.0d));
    };
    public static final EasingFunction C = d -> {
        return 1.0d + (2.70158d * Math.pow(d - 1.0d, 3.0d)) + (1.70158d * Math.pow(d - 1.0d, 2.0d));
    };
    public static final EasingFunction D = d -> {
        return d < 0.5d ? (Math.pow(2.0d * d, 2.0d) * ((7.189819d * d) - 2.5949095d)) / 2.0d : ((Math.pow((2.0d * d) - 2.0d, 2.0d) * ((3.5949095d * ((d * 2.0d) - 2.0d)) + 2.5949095d)) + 2.0d) / 2.0d;
    };
    public static final EasingFunction E = d -> {
        return (d == 0.0d || d == 1.0d) ? d : (-Math.pow(2.0d, (10.0d * d) - 10.0d)) * Math.sin(((d * 10.0d) - 10.75d) * 2.0943951023931953d);
    };
    public static final EasingFunction F = d -> {
        return (d == 0.0d || d == 1.0d) ? d : (Math.pow(2.0d, (-10.0d) * d) * Math.sin(((d * 10.0d) - 0.75d) * 2.0943951023931953d)) + 1.0d;
    };
    public static final EasingFunction G = d -> {
        return (d == 0.0d || d == 1.0d) ? d : d < 0.5d ? (-(Math.pow(2.0d, (20.0d * d) - 10.0d) * Math.sin(((20.0d * d) - 11.125d) * 1.3962634015954636d))) / 2.0d : ((Math.pow(2.0d, ((-20.0d) * d) + 10.0d) * Math.sin(((20.0d * d) - 11.125d) * 1.3962634015954636d)) / 2.0d) + 1.0d;
    };
    public static final EasingFunction H = d -> {
        if (d < 1.0d / 2.75d) {
            return 7.5625d * d * d;
        }
        if (d < 2.0d / 2.75d) {
            double d1 = d - (1.5d / 2.75d);
            return (7.5625d * d1 * d1) + 0.75d;
        }
        if (d < 2.5d / 2.75d) {
            double d2 = d - (2.25d / 2.75d);
            return (7.5625d * d2 * d2) + 0.9375d;
        }
        double d3 = d - (2.625d / 2.75d);
        return (7.5625d * d3 * d3) + 0.984375d;
    };
    public static final EasingFunction I = d -> {
        return 1.0d - H.ease(1.0d - d);
    };
    public static final EasingFunction J = d -> {
        return d < 0.5d ? (1.0d - H.ease(1.0d - (2.0d * d))) / 2.0d : (1.0d + H.ease((2.0d * d) - 1.0d)) / 2.0d;
    };

    private Easing() {
    }

    public static EasingFunction a(double d) {
        return d2 -> {
            return Math.pow(d2, d);
        };
    }

    public static EasingFunction a(int i2) {
        return a(i2);
    }

    public static EasingFunction b(double d) {
        return d2 -> {
            return 1.0d - Math.pow(1.0d - d2, d);
        };
    }

    public static EasingFunction b(int i2) {
        return b(i2);
    }

    public static EasingFunction c(double d) {
        return d2 -> {
            return d2 < 0.5d ? Math.pow(2.0d, d - 1.0d) * Math.pow(d2, d) : 1.0d - (Math.pow(((-2.0d) * d2) + 2.0d, d) / 2.0d);
        };
    }
}
