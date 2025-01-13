package be.machigan.craftplugin.menu.item.builder;

import be.machigan.craftplugin.formatter.color.StringColor;
import be.machigan.craftplugin.menu.item.Item;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("deprecation")
public class SpigotItemBuilder extends ItemBuilder {
    private final StringColor color = new StringColor();

    public SpigotItemBuilder(Item item) {
        super(item);
    }

    @Override
    public ItemBuilder name(String name) {
        return this.applyToMeta(meta -> meta.setDisplayName(this.color.toColoredComponent(name)));
    }

    @Override
    public ItemBuilder lore(List<String> lore) {
        return this.applyToMeta(meta -> meta.setLore(this.color.toColoredComponents(lore)));
    }

    @Override
    public ItemBuilder setLoreLine(int line, String value) {
        Preconditions.checkArgument(line >= 0, "The lore line must be greater or equals to 0 (" + line + ")");
        this.applyToMeta(meta -> {
            List<String> currentLore = Objects.requireNonNullElse(meta.getLore(), new ArrayList<>());
            int currentLoreSize = currentLore.size();
            if (line > currentLoreSize - 1)
                currentLore.addAll(Collections.nCopies((line - currentLoreSize) + 1, ""));
            currentLore.set(line, this.color.toColoredComponent(value));
            meta.setLore(currentLore);
        });
        return this;
    }
}
