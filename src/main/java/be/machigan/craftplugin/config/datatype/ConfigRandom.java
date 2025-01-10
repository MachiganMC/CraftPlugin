package be.machigan.craftplugin.config.datatype;

import be.machigan.craftplugin.config.Config;
import be.machigan.craftplugin.config.ConfigChecker;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

public abstract class ConfigRandom<T extends Number> extends Config<T> {
    protected T min;
    protected T max;
    protected final T defaultMin;
    protected final T defaultMax;
    @Setter
    protected static String minPath = "Min";
    @Setter
    protected static String maxPath = "Max";

    protected ConfigRandom(String pathInConfig, T defaultMin, T defaultMax, @Nullable ConfigChecker<T> checker) {
        super(pathInConfig, defaultMin, checker);
        this.defaultMin = defaultMin;
        this.defaultMax = defaultMax;
    }

    protected abstract T getRandom(T min, T max);
    protected abstract T getFromSection(ConfigurationSection section, String pathInSection);
    protected abstract boolean isMinOrMaxInvalid();

   private boolean isMinMaxInvalidOrNull() {
       return
               this.min == null
               || this.max == null
               || this.isMinOrMaxInvalid()
               ;
   }

    @Override
    protected @Nullable T getValueFromConfig() {
        ConfigurationSection section = handler.getConfigFile().getConfigurationSection(this.pathInConfig);
        if (section == null)return null;
        this.min = this.getFromSection(section, minPath);
        this.max = this.getFromSection(section, maxPath);
        if (this.min == null || this.max == null) return null;
        if (this.isMinMaxInvalidOrNull()) return null;
        return this.getRandom(this.min, this.max);
    }

    @Override
    protected boolean isValid(T valueInConfig) {
        if (this.isMinMaxInvalidOrNull())
            return false;
        if (this.checker != null)
            return this.checker.check(this.min) && this.checker.check(this.max);
        return true;
    }

    @Override
    public T getValue() {
        if (this.isValid(null))
            return this.getRandom(this.min, this.max);
        return this.getRandom(this.defaultMin, this.defaultMax);
    }
}
