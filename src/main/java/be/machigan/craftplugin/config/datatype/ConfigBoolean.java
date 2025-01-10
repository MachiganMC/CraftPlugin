package be.machigan.craftplugin.config.datatype;

import be.machigan.craftplugin.config.Config;
import org.jetbrains.annotations.Nullable;

public class ConfigBoolean extends Config<Boolean> {
    public ConfigBoolean(String pathInConfig, Boolean defaultValue) {
        super(pathInConfig, defaultValue, null);
    }

    @Override
    protected @Nullable Boolean getValueFromConfig() {
        return handler.getConfigFile().getBoolean(this.pathInConfig);
    }
}
