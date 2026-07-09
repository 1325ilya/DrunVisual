package drunvisual.module;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import drunvisual.config.ConfigManager;
import drunvisual.events.EventBusService;
import drunvisual.hud.notifications.HudNotificationCenter;
import drunvisual.hud.core.HudServiceRegistry;
import drunvisual.client.MinecraftContext;
import drunvisual.config.LocalConfigManager;
import drunvisual.settings.Setting;

public abstract class ClientModule implements MinecraftContext {
    private static final Logger LOG = LogManager.getLogger("drunvisual/config");
    private final String name;
    private final String description;
    private final ModuleCategory category;
    private boolean subscribed;
    private final List<Setting<?>> settings = new ArrayList();
    private int bindKey = 0;
    private boolean enabled = false;

    public ClientModule() {
        ModuleInfo moduleInfo = (ModuleInfo) getClass().getAnnotation(ModuleInfo.class);
        if (moduleInfo == null) {
            throw new IllegalStateException("Missing @ModuleInfo on " + getClass().getName());
        }
        this.name = moduleInfo.a();
        this.description = moduleInfo.b();
        this.category = moduleInfo.c();
    }

    public String name() {
        return this.name;
    }

    public String description() {
        return this.description;
    }

    public ModuleCategory category() {
        return this.category;
    }

    public int bindKey() {
        return this.bindKey;
    }

    public void setBindKey(int i) {
        if (this.bindKey != i) {
            this.bindKey = i;
            if (LocalConfigManager.get().isApplying()) {
                return;
            }
            ConfigManager.a().h();
        }
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean z) {
        setEnabledState(z);
    }

    public void toggle() {
        setEnabled(!this.enabled);
    }

    public boolean isSubscribed() {
        return this.subscribed;
    }

    public boolean isActive() {
        if (this.subscribed) {
            return HudServiceRegistry.MODULE_ACCESS.a(this.name);
        }
        return false;
    }

    public List<Setting<?>> settings() {
        return this.settings;
    }

    public void collectSettings() {
        this.settings.clear();
        for (Field field : getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object obj = field.get(this);
                if (obj instanceof Setting) {
                    this.settings.add((Setting) obj);
                }
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Cannot read setting field " + field.getName(), e);
            }
        }
    }

    public void setEnabledState(boolean z) {
        if (this.enabled != z) {
            this.enabled = z;
            if (!LocalConfigManager.get().isApplying()) {
                LOG.info("[DrunVisual] Module '{}' -> {}", this.name, z ? "ON" : "OFF");
                LocalConfigManager.get().flushSaveNow("module-" + this.name);
            }
        }
        if (!z || HudServiceRegistry.MODULE_ACCESS.a(this.name)) {
            setSubscribed(z);
        }
    }

    public void setEnabledStateQuiet(boolean z) {
        if (this.enabled == z) {
            return;
        }
        this.enabled = z;
        if (!z || HudServiceRegistry.MODULE_ACCESS.a(this.name)) {
            setSubscribed(z);
        }
    }

    public void setBindKeyQuiet(int i) {
        this.bindKey = i;
    }

    public void setSubscribed(boolean z) {
        if (this.subscribed != z) {
            this.subscribed = z;
            if (z) {
                onEnable();
            } else {
                onDisable();
            }
            HudNotificationCenter.a(this.name, z);
        }
    }

    public void syncState() {
        setSubscribed(this.enabled);
    }

    public void onEnable() {
        EventBusService.EVENT_BUS.subscribe(this);
    }

    public void onDisable() {
        EventBusService.EVENT_BUS.unsubscribe(this);
    }

    public void a(int i) {
        setBindKey(i);
    }

    public boolean a() {
        return isEnabled();
    }

    public void a(boolean z) {
        setEnabled(z);
    }

    public void b() {
        collectSettings();
    }

    public void b(boolean z) {
        setEnabledState(z);
    }

    public void c(boolean z) {
        setSubscribed(z);
    }

    public void c() {
        syncState();
    }

    public void d() {
        toggle();
    }

    public void e() {
        onEnable();
    }

    public void f() {
        onDisable();
    }

    public String g() {
        return name();
    }

    public String h() {
        return description();
    }

    public ModuleCategory i() {
        return category();
    }

    public int j() {
        return bindKey();
    }

    public boolean k() {
        return isActive();
    }

    public boolean l() {
        return isSubscribed();
    }

    public List<Setting<?>> m() {
        return settings();
    }
}
