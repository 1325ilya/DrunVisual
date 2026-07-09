package drunvisual.modules.utilities;

import java.util.regex.Pattern;
import meteordevelopment.orbit.EventHandler;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.TokenSetting;
import drunvisual.events.ClientTickEvent;

@ModuleInfo(a = "Auto Invest", b = "Automatically invests clan money above the configured limit.", c = ModuleCategory.UTILITIES)
public class AutoInvest extends ClientModule {
    private final TokenSetting limit = new TokenSetting("Limit", "Minimum balance before investing.", TokenSetting.TokenType.PRICE, "1000000", "$");
    private final Pattern numberPattern = Pattern.compile("\\d+");
    private long lastInvestTime = -1;

    @EventHandler
    private void a(ClientTickEvent clientTickEvent) {
    }

    private long n() {
        try {
            return Long.parseLong(this.limit.k().replace("$", "").replace(" ", ""));
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    private long o() {
        return this.lastInvestTime;
    }

    @Override
    public void f() {
        super.f();
        this.lastInvestTime = -1L;
    }
}
