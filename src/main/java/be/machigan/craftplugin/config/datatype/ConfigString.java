package be.machigan.craftplugin.config.datatype;

import be.machigan.craftplugin.config.Config;
import be.machigan.craftplugin.config.ConfigChecker;
import org.jetbrains.annotations.Nullable;

public class ConfigString extends Config<String> {
    public ConfigString(String pathInConfig, String defaultValue, @Nullable ConfigChecker<String> checker) {
        super(pathInConfig, defaultValue, checker);
    }

    public ConfigString(String pathInConfig, String defaultValue) {
        super(pathInConfig, defaultValue, null);
    }

    @Override
    protected @Nullable String getValueFromConfig() {
        return handler.getConfigFile().getString(this.pathInConfig);
    }

    @Override
    public String toString() {
        return this.value;
    }
}
