package be.machigan.craftplugin.config;

import be.machigan.craftplugin.CraftPlugin;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

@Getter
public abstract class Config<T> {
    protected static ConfigHandler handler = new ConfigHandler();
    protected String pathInConfig;
    protected T value;
    protected final T defaultValue;
    @Nullable
    protected final ConfigChecker<T> checker;

    protected Config(String pathInConfig, T defaultValue, @Nullable ConfigChecker<T> checker) {
        this.pathInConfig = pathInConfig;
        this.defaultValue = defaultValue;
        this.checker = checker;
        handler.addConfigValue(this);
    }

    protected abstract @Nullable T getValueFromConfig();

    public void reload() {
        T valueInConfig = this.getValueFromConfig();
        if (this.isValid(valueInConfig)) {
            this.value = valueInConfig;
        } else {
            this.value = this.defaultValue;
            CraftPlugin.getPlugin().getLogger().log(
                    Level.WARNING,
                    () -> handler.getErrorLogger().getErrorLog(this, String.valueOf(valueInConfig))
            );
        }
    }

    protected boolean isValid(T valueInConfig) {
        if (valueInConfig == null) return false;
        if (this.checker == null) return true;
        return checker.check(valueInConfig);
    }

    public static void registerConfigHandler(ConfigHandler newHandler) {
        handler = newHandler;
    }

    public static void reloadConfigValues() {
        handler.reload();
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
