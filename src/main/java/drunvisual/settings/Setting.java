package drunvisual.settings;

import java.util.Objects;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import drunvisual.config.ConfigManager;
import drunvisual.config.LocalConfigManager;
import drunvisual.gui.config.ConfigTextDialog;

public abstract class Setting<ConfigTextDialog> {
    private static final Logger LOG = LogManager.getLogger("drunvisual/config");
    private final String name;
    private final String description;
    private ConfigTextDialog value;
    private final ConfigTextDialog defaultValue;
    private final ConfigTextDialog minValue;
    private final ConfigTextDialog maxValue;
    private Supplier<Boolean> visibleCondition;

    protected Setting(String str, String str2, ConfigTextDialog configtextdialog) {
        this(str, str2, configtextdialog, null, null);
    }

    protected Setting(String str, String str2, ConfigTextDialog configtextdialog, ConfigTextDialog configtextdialog2, ConfigTextDialog configtextdialog3) {
        this.visibleCondition = () -> {
            return true;
        };
        this.name = str;
        this.description = str2;
        this.value = configtextdialog;
        this.defaultValue = configtextdialog;
        this.minValue = configtextdialog2;
        this.maxValue = configtextdialog3;
    }

    public String name() {
        return this.name;
    }

    public String description() {
        return this.description;
    }

    public ConfigTextDialog value() {
        return this.value;
    }

    public ConfigTextDialog defaultValue() {
        return this.defaultValue;
    }

    public ConfigTextDialog minValue() {
        return this.minValue;
    }

    public ConfigTextDialog maxValue() {
        return this.maxValue;
    }

    public void setValue(ConfigTextDialog configtextdialog) {
        if (Objects.equals(this.value, configtextdialog)) {
            return;
        }
        this.value = configtextdialog;
        if (LocalConfigManager.get().isApplying()) {
            return;
        }
        LOG.debug("[DrunVisual] Setting '{}' -> {}", this.name, configtextdialog);
        ConfigManager.a().h();
    }

    public Setting<ConfigTextDialog> visibleWhen(Supplier<Boolean> supplier) {
        this.visibleCondition = supplier != null ? supplier : () -> {
            return true;
        };
        return this;
    }

    public boolean isVisible() {
        return this.visibleCondition.get().booleanValue();
    }

    public void reset() {
        this.value = this.defaultValue;
    }

    public String f() {
        return name();
    }

    public String g() {
        return description();
    }

    public Supplier<Boolean> h() {
        return this.visibleCondition;
    }

    public ConfigTextDialog i() {
        return defaultValue();
    }

    public ConfigTextDialog j() {
        return minValue();
    }

    public ConfigTextDialog k() {
        return value();
    }

    public void a(ConfigTextDialog configtextdialog) {
        setValue(configtextdialog);
    }

    public void l() {
        reset();
    }

    public Setting<ConfigTextDialog> b(Supplier<Boolean> supplier) {
        return visibleWhen(supplier);
    }

    public boolean m() {
        return isVisible();
    }
}
