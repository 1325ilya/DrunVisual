package drunvisual.settings;

import java.util.function.Supplier;

public class StringSetting extends Setting<String> {
    public StringSetting(String str, String str2, String str3) {
        super(str, str2, str3 != null ? str3 : "");
    }

    public StringSetting(String str, String str2) {
        this(str, "", str2);
    }

    public String get() {
        return k();
    }

    public void set(String str) {
        super.a(str != null ? str : "");
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

    public StringSetting a(Supplier<Boolean> supplier) {
        return (StringSetting) visibleWhen(supplier);
    }

    public /* bridge */ /* synthetic */ Setting<String> visibleWhen2(Supplier supplier) {
        return visibleWhen((Supplier<Boolean>) supplier);
    }
}
