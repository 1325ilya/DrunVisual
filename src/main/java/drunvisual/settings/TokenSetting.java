package drunvisual.settings;

import java.util.function.Supplier;

public class TokenSetting extends Setting<String> {
    private final TokenType tokenType;
    private final String suffix;

    public enum TokenType {
        TEXT,
        COMMAND,
        PLAYER,
        NUMBER,
        PRICE
    }

    public TokenSetting(String str, String str2, TokenType tokenType, String str3, String str4) {
        super(str, str2, str3);
        this.tokenType = tokenType;
        this.suffix = str4;
    }

    public TokenSetting(String str, TokenType tokenType, String str2, String str3) {
        this(str, "", tokenType, str2, str3);
    }

    public TokenSetting(String str, String str2) {
        this(str, "", TokenType.TEXT, str2, "");
    }

    public TokenSetting(String str, TokenType tokenType, String str2) {
        this(str, "", tokenType, str2, "");
    }

    public String get() {
        return k();
    }

    public void set(String str) {
        super.a(str);
    }

    public TokenType tokenType() {
        return this.tokenType;
    }

    public String suffix() {
        return this.suffix;
    }

    public boolean isValid() {
        String strK = k();
        if (strK == null || strK.isEmpty()) {
            return false;
        }
        if (this.tokenType != TokenType.NUMBER && this.tokenType != TokenType.PRICE) {
            return true;
        }
        try {
            Double.parseDouble(strK);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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

    public TokenType b() {
        return tokenType();
    }

    public String c() {
        return suffix();
    }

    public boolean d() {
        return isValid();
    }

    public TokenSetting a(Supplier<Boolean> supplier) {
        return (TokenSetting) visibleWhen(supplier);
    }

    public /* bridge */ /* synthetic */ Setting<String> visibleWhen2(Supplier supplier) {
        return visibleWhen((Supplier<Boolean>) supplier);
    }
}
