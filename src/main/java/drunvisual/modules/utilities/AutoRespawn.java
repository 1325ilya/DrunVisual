package drunvisual.modules.utilities;

import java.util.Objects;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.screen.Screen;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.BooleanSetting;
import drunvisual.settings.TokenSetting;
import drunvisual.events.ClientTickEvent;
import drunvisual.events.PlayerDeathEvent;
import drunvisual.media.chat.ChatMessages;

@ModuleInfo(a = "Auto Respawn", b = "Автоматически возрождает игрока после смерти", c = ModuleCategory.UTILITIES)
public class AutoRespawn extends ClientModule {
    private final BooleanSetting e = new BooleanSetting("Авто возрождение", "Автоматически возрождаться после смерти", true);
    private final BooleanSetting f = new BooleanSetting("Отправлять команду после возрождения", "Отправлять команду после возрождения", false);
    private final TokenSetting g;
    private boolean h;
    private boolean i;
    private int j;
    public static int a;
    public static boolean b;

    public AutoRespawn() {
        TokenSetting tokenSetting = new TokenSetting("Команда", TokenSetting.TokenType.COMMAND, "/home");
        BooleanSetting booleanSetting = this.f;
        Objects.requireNonNull(booleanSetting);
        this.g = tokenSetting.a(booleanSetting::k);
        this.h = false;
    }

    @EventHandler
    public void a(PlayerDeathEvent playerDeathEvent) {
        if (playerDeathEvent.a() == c.player) {
            this.i = true;
        }
    }

    @EventHandler
    public void a(ClientTickEvent clientTickEvent) {
        if (c.player == null || c.world == null) {
            return;
        }
        if (c.currentScreen instanceof DeathScreen) {
            int i = this.j;
            this.j = (2 * (i | 1)) - (i ^ 1);
        }
        if (this.i && !(c.currentScreen instanceof DeathScreen) && c.player.age > 30) {
            ChatMessages.a(this.g.a());
            this.i = false;
            this.j = 0;
        }
        Screen ScreenVar = c.currentScreen;
        if (ScreenVar instanceof DeathScreen) {
            DeathScreen DeathScreenVar = (DeathScreen) ScreenVar;
            if (!this.h) {
                this.h = true;
            }
            if (this.e.k().booleanValue() && a(DeathScreenVar)) {
                c.player.requestRespawn();
                c.currentScreen = null;
                this.j = 0;
                return;
            }
            return;
        }
        if (!this.h) {
            return;
        }
        if (c.player.isAlive()) {
            this.h = false;
            if (!this.f.k().booleanValue() || this.g.a().isEmpty()) {
                return;
            }
            ChatMessages.a(this.g.a());
        }
    }

    private boolean a(DeathScreen DeathScreenVar) {
        try {
            for (Object obj : DeathScreenVar.children()) {
                if ((obj instanceof ButtonWidget) && ((ButtonWidget) obj).active) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void f() {
        super.f();
        this.h = false;
    }

    public static String c(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
