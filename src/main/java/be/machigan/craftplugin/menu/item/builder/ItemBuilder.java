package be.machigan.craftplugin.menu.item.builder;

import be.machigan.craftplugin.lambda.ParameterRunnable;
import be.machigan.craftplugin.menu.item.InventoryItem;
import be.machigan.craftplugin.menu.item.Item;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public abstract class ItemBuilder {
    protected final Item item;

    public ItemBuilder applyToMeta(ParameterRunnable<ItemMeta> applyToMeta) {
        this.item.applyToMeta(applyToMeta);
        return this;
    }

    public abstract ItemBuilder name(String name);

    public abstract ItemBuilder lore(List<String> lore);

    public abstract ItemBuilder addLine(String line);

    public ItemBuilder addLines(List<String> lines) {
        lines.forEach(this::addLine);
        return this;
    }

    public ItemBuilder addLines(String... lines) {
        return this.addLines(Arrays.asList(lines));
    }

    public abstract ItemBuilder setLoreLine(int line, String value);

    public ItemBuilder lore(String... lore) {
        return this.lore(Arrays.asList(lore));
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        return this.applyToMeta(meta -> meta.addEnchant(enchantment, level, true));
    }

    public ItemBuilder enchants(Map<Enchantment, Integer> enchantments) {
        return this.applyToMeta(meta ->
                enchantments.forEach((enchant, level) -> meta.addEnchant(enchant, level, true))
        );
    }

    public ItemBuilder flags(ItemFlag... flags) {
        return this.applyToMeta(meta -> meta.addItemFlags(flags));
    }

    public ItemBuilder amount(@Range(from = 1, to = 64) int amount) {
        this.item.getItemStack().setAmount(amount);
        return this;
    }

    public ItemBuilder enchantEffect() {
        return this.applyToMeta(meta -> {
            meta.addEnchant(
                    this.item.getItemStack().getType().equals(Material.FISHING_ROD) ?
                            Enchantment.ARROW_FIRE :
                            Enchantment.LURE,
                    1, false
            );
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        });
    }

    public <T> ItemBuilder addNamedSpacedKey(NamespacedKey key, PersistentDataType<?, T> type, T value) {
        return this.applyToMeta(meta -> meta.getPersistentDataContainer().set(key, type, value));
    }

    public ItemBuilder addEmptyNamedSpacedKey(NamespacedKey key) {
        return this.addNamedSpacedKey(key, PersistentDataType.BYTE, (byte) 0);
    }

    public ItemBuilder increaseAmount(@Range(from = 1, to = 63) int amount, boolean canBeGreaterMaxLegacy) {
        this.item.getItemStack().setAmount(Math.min(
                this.item.getItemStack().getAmount() + amount,
                canBeGreaterMaxLegacy ? this.item.getItemStack().getMaxStackSize() : 64
        ));
        return this;
    }

    public ItemBuilder increaseAmount(int amount) {
        return this.increaseAmount(amount, true);
    }

    public ItemBuilder increaseAmount() {
        return this.increaseAmount(1, true);
    }

    public ItemBuilder decreaseAmount(@Range(from = 1, to = 63) int amount) {
        this.item.getItemStack().setAmount(Math.max(
                this.item.getItemStack().getAmount() - amount,
                0
        ));
        return this;
    }

    public ItemBuilder decreaseAmount() {
        return this.decreaseAmount(1);
    }

    public Item build() {
        return this.item;
    }

    public InventoryItem inventoryItem() {
        return new InventoryItem(this.item);
    }

    public ItemBuilder applyIf(boolean condition, ParameterRunnable<Item> applyIfTrue, @Nullable ParameterRunnable<Item> applyElse) {
        if (condition)
            applyIfTrue.run(this.item);
        else if (applyElse != null)
            applyElse.run(this.item);
        return this;
    }

    public ItemBuilder applyIf(boolean condition, ParameterRunnable<Item> applyIfTrue) {
        return this.applyIf(condition, applyIfTrue, null);
    }
}
