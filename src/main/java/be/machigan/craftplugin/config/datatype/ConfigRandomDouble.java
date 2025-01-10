package be.machigan.craftplugin.config.datatype;

import be.machigan.craftplugin.config.ConfigChecker;
import be.machigan.craftplugin.utils.Random;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

public class ConfigRandomDouble extends ConfigRandom<Double> {
    public ConfigRandomDouble(String pathInConfig, Double defaultMin, Double defaultMax, @Nullable ConfigChecker<Double> checker) {
        super(pathInConfig, defaultMin, defaultMax, checker);
    }

    public ConfigRandomDouble(String pathInConfig, Double defaultMin, Double defaultMax) {
        super(pathInConfig, defaultMin, defaultMax, null);
    }

    @Override
    protected Double getRandom(Double min, Double max) {
        return Random.getRandom(min, max);
    }

    @Override
    protected Double getFromSection(ConfigurationSection section, String pathInSection) {
        return section.getDouble(pathInSection);
    }

    @Override
    protected boolean isMinOrMaxInvalid() {
        return this.min > this.max;
    }
}
