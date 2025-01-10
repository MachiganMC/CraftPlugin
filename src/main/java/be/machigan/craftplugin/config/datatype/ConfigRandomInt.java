package be.machigan.craftplugin.config.datatype;

import be.machigan.craftplugin.config.ConfigChecker;
import be.machigan.craftplugin.utils.Random;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

public class ConfigRandomInt extends ConfigRandom<Integer> {
    public ConfigRandomInt(String pathInConfig, Integer defaultMin, Integer defaultMax, @Nullable ConfigChecker<Integer> checker) {
        super(pathInConfig, defaultMin, defaultMax, checker);
    }

    public ConfigRandomInt(String pathInConfig, Integer defaultMin, Integer defaultMax) {
        super(pathInConfig, defaultMin, defaultMax, null);
    }

    @Override
    protected Integer getRandom(Integer min, Integer max) {
        return Random.getRandom(min, max);
    }

    @Override
    protected Integer getFromSection(ConfigurationSection section, String pathInSection) {
        return section.getInt(pathInSection);
    }

    @Override
    protected boolean isMinOrMaxInvalid() {
        return this.min > this.max;
    }
}
