package be.machigan.craftplugin.config.datatype;

import be.machigan.craftplugin.config.Config;
import be.machigan.craftplugin.config.ConfigChecker;
import org.jetbrains.annotations.Nullable;

public class ConfigDouble extends Config<Double> {
    public ConfigDouble(String pathInConfig, Double defaultValue, @Nullable ConfigChecker<Double> checker) {
        super(pathInConfig, defaultValue, checker);
    }

    public ConfigDouble(String pathInConfig, Double defaultValue) {
        super(pathInConfig, defaultValue, null);
    }

    @Override
    protected @Nullable Double getValueFromConfig() {
        return handler.getConfigFile().getDouble(this.pathInConfig);
    }
}
