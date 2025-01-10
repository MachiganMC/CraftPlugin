package be.machigan.craftplugin.config.datatype;

import be.machigan.craftplugin.config.Config;
import be.machigan.craftplugin.config.ConfigChecker;
import org.jetbrains.annotations.Nullable;

public class ConfigInt extends Config<Integer> {
    public ConfigInt(String pathInConfig, Integer defaultValue, @Nullable ConfigChecker<Integer> checker) {
        super(pathInConfig, defaultValue, checker);
    }

    public ConfigInt(String pathInConfig, Integer defaultValue) {
        super(pathInConfig, defaultValue, null);
    }

    @Override
    protected @Nullable Integer getValueFromConfig() {
        return handler.getConfigFile().getInt(this.pathInConfig);
    }
}
