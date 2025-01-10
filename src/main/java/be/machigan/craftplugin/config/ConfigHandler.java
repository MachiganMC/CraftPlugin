package be.machigan.craftplugin.config;

import be.machigan.craftplugin.CraftPlugin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfigHandler {
    private File file = new File(CraftPlugin.getPlugin().getDataFolder(), "config.yml");
    private final List<Config<?>> configValues = new ArrayList<>();
    private FileConfiguration configFile = YamlConfiguration.loadConfiguration(this.file);
    private ConfigError errorLogger = (configValue, invalidValue) ->
            "Value from the config \"" + configValue.pathInConfig + "\" is invalid (" + invalidValue + ")"
            ;

    public void addConfigValue(Config<?> configValue) {
        this.configValues.add(configValue);
    }

    public void reload() {
        this.configFile = YamlConfiguration.loadConfiguration(this.file);
        this.configValues.forEach(Config::reload);
    }
}
