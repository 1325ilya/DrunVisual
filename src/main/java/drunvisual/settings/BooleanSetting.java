package drunvisual.settings;

import java.util.function.Supplier;

public class BooleanSetting extends Setting<Boolean> {
    public BooleanSetting(String str, String str2, boolean z) {
        super(str, str2, Boolean.valueOf(z));
    }

    public BooleanSetting(String str, boolean z) {
        this(str, "", z);
    }

    public boolean get() {
        return k().booleanValue();
    }

    public void set(boolean z) {
        super.a(Boolean.valueOf(z));
    }

    public void toggle() {
        set(!get());
    }

    @Override
    public Setting<Boolean> visibleWhen(Supplier<Boolean> supplier) {
        super.visibleWhen(supplier);
        return this;
    }

    public boolean a() {
        return get();
    }

    public void a(boolean z) {
        set(z);
    }

    public void b() {
        toggle();
    }

    public BooleanSetting a(Supplier<Boolean> supplier) {
        return (BooleanSetting) visibleWhen(supplier);
    }

    public /* bridge */ /* synthetic */ Setting<Boolean> visibleWhen2(Supplier supplier) {
        return visibleWhen((Supplier<Boolean>) supplier);
    }
}
