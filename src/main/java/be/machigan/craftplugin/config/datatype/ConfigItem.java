package be.machigan.craftplugin.config.datatype;

import be.machigan.craftplugin.config.Config;
import be.machigan.craftplugin.config.ConfigChecker;
import be.machigan.craftplugin.lambda.ParameterReturnableRunnable;
import be.machigan.craftplugin.lambda.ParameterRunnable;
import be.machigan.craftplugin.menu.item.Item;
import com.google.common.base.CaseFormat;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConfigItem extends Config<Item> {
    private static final Map<ItemFlag, String> FLAGS_STRING = Stream
            .of(ItemFlag.values())
            .collect(Collectors.toMap(
                    flag -> flag,
                    flag -> CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, flag.name()))
            )
            ;
    private ParameterRunnable<Item> editor;

    public ConfigItem(String pathInConfig, Item defaultValue, @Nullable ConfigChecker<Item> checker) {
        super(pathInConfig, defaultValue, checker);
    }

    public ConfigItem(String pathInConfig, Item defaultValue) {
        super(pathInConfig, defaultValue, null);
    }

    @Override
    public @Nullable Item getValueFromConfig() {
        ConfigurationSection section = handler.getConfigFile().getConfigurationSection(this.pathInConfig);
        if (section == null) return null;
        Material material = this.getMaterial(section);
        if (material == null) return null;
        Item item =  Item
                .builder(material)
                .name(this.getName(section))
                .lore(this.getLore(section))
                .flags(this.getItemFlags(section))
                .enchants(this.getEnchants(section))
                .applyToMeta(meta -> meta.setUnbreakable(this.isUnbreakable(section)))
                .build()
                ;
        if (this.editor != null)
            this.editor.run(item);
        return item;
    }

    protected @Nullable Material getMaterial(ConfigurationSection section) {
        String materialStr = section.getString("Material");
        if (materialStr == null) return null;
        return Material.matchMaterial(materialStr);
    }

    protected @NotNull String getName(ConfigurationSection section) {
        return Objects.requireNonNullElse(section.getString("Name"), "");
    }

    protected @NotNull List<String> getLore(ConfigurationSection section) {
        return section.getStringList("Lore");
    }

    protected ItemFlag @NotNull [] getItemFlags(ConfigurationSection section) {
        return FLAGS_STRING
                .keySet()
                .stream()
                .filter(flag -> section.getBoolean(FLAGS_STRING.get(flag)))
                .toArray(ItemFlag[]::new)
                ;
    }

    protected Map<Enchantment, Integer> getEnchants(ConfigurationSection section) {
        return section.getStringList("Enchants")
                .stream()
                .map(enchant -> Enchantment.getByKey(NamespacedKey.fromString("minecraft:" + enchant)))
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        enchant -> enchant,
                        enchant -> 1
                ))
                ;
    }

    protected boolean isUnbreakable(ConfigurationSection section) {
        return section.getBoolean("IsUnbreakable");
    }

    public ConfigItem edit(ParameterRunnable<Item> editor) {
        this.editor = editor;
        return this;
    }
}
