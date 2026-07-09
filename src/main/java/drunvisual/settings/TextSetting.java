package drunvisual.settings;

import java.util.function.Supplier;

public class TextSetting extends Setting<String> {
    public TextSetting(String str, String str2, String str3) {
        super(str, str2, str3);
    }

    public TextSetting(String str, String str2) {
        this(str, "", str2);
    }

    public String get() {
        return k();
    }

    public void set(String str) {
        super.a(str);
    }

    @Override
    public Setting<String> visibleWhen(Supplier<Boolean> supplier) {
        super.visibleWhen(supplier);
        return this;
    }

    public String a() {
        return get();
    }

    @Override
    public void a(String str) {
        set(str);
    }

    public TextSetting a(Supplier<Boolean> supplier) {
        return (TextSetting) visibleWhen(supplier);
    }

    public /* bridge */ /* synthetic */ Setting<String> visibleWhen2(Supplier supplier) {
        return visibleWhen((Supplier<Boolean>) supplier);
    }
}
