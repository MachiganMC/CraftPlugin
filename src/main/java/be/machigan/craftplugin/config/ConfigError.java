package be.machigan.craftplugin.config;

public interface ConfigError {
    String getErrorLog(Config<?> configValue, String invalidValue);
}
