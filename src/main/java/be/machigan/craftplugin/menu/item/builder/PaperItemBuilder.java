package be.machigan.craftplugin.menu.item.builder;

import be.machigan.craftplugin.formatter.color.PaperColor;
import be.machigan.craftplugin.menu.item.Item;
import com.google.common.base.Preconditions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@ApiStatus.Internal
public class PaperItemBuilder extends ItemBuilder {
    private final PaperColor color = new PaperColor();

    public PaperItemBuilder(Item item) {
        super(item);
    }

    private Component colorAndRemoveDefaultItalic(String s) {
        return this.color
                .toColoredComponent(s)
                .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public ItemBuilder name(String name) {
        return this.applyToMeta(meta -> meta.displayName(this.colorAndRemoveDefaultItalic(name)));
    }

    @Override
    public ItemBuilder lore(List<String> lore) {
        return this.applyToMeta(meta -> meta.lore(
                lore
                        .stream()
                        .map(this::colorAndRemoveDefaultItalic)
                        .toList()
        ));
    }

    @Override
    public ItemBuilder setLoreLine(int line, String value) {
        Preconditions.checkArgument(line >= 0, "The lore line must be greater or equals to 0 (" + line + ")");
        this.applyToMeta(meta -> {
            List<Component> currentLore = Objects.requireNonNullElse(meta.lore(), new ArrayList<>());
            int currentLoreSize = currentLore.size();
            if (line > currentLoreSize - 1)
                currentLore.addAll(Collections.nCopies((line - currentLoreSize) + 1, Component.empty()));
            currentLore.set(line, this.colorAndRemoveDefaultItalic(value));
            meta.lore(currentLore);
        });
        return this;
    }
}
