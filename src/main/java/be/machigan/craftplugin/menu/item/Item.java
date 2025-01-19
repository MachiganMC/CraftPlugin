package be.machigan.craftplugin.menu.item;

import be.machigan.craftplugin.lambda.ParameterRunnable;
import be.machigan.craftplugin.menu.item.builder.ItemBuilder;
import be.machigan.craftplugin.menu.item.builder.PaperItemBuilder;
import be.machigan.craftplugin.menu.item.builder.SpigotItemBuilder;
import be.machigan.craftplugin.provider.Provider;
import be.machigan.craftplugin.utils.version.ServerVersion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Item {
    private ItemStack itemStack;
    private static final Map<String, ItemStack> CUSTOM_HEAD_CACHE = new HashMap<>();
    private static final Map<UUID, ItemStack> PLAYER_HEAD_CACHE = new HashMap<>();

    public static ItemBuilder builder(ItemStack itemStack) {
        Item item = new Item(itemStack);
        if (ServerVersion.isPaperServer()) {
            return new PaperItemBuilder(item);
        } else {
            return new SpigotItemBuilder(item);
        }
    }

    public static ItemBuilder builder(Material material, @Range(from = 1, to = 64) int quantity) {
        return builder(new ItemStack(material, quantity));
    }

    public static ItemBuilder builder(Material material) {
        return builder(material, 1);
    }

    public static ItemBuilder builder(Item item) {
        return builder(item.getItemStack());
    }

    public ItemBuilder builder() {
        return builder(this);
    }

    @Contract("null -> null; !null -> new")
    public static Item fromItemStack(@Nullable ItemStack itemStack) {
        return itemStack != null ? new Item(itemStack) : null;
    }

    @Contract("null -> null; !null -> new")
    public static Item fromMaterial(@Nullable Material material) {
        return material != null ? fromItemStack(new ItemStack(material, 1)) : null;
    }

    public void addToInventory(Inventory inventory) {
        inventory.addItem(this.itemStack);
    }

    public void addTo(Player player) {
        player.getInventory().addItem(this.itemStack);
    }

    public static ItemBuilder customHeadFromBase64(@NotNull String b64) {
        if (CUSTOM_HEAD_CACHE.containsKey(b64))
            return builder(CUSTOM_HEAD_CACHE.get(b64).clone());
        ItemStack head = Provider.getCustomHeadProvider().customHeadFromBase64(b64);
        CUSTOM_HEAD_CACHE.put(b64, head);
        return builder(head.clone());
    }

    public static ItemBuilder playerHead(@NotNull OfflinePlayer player) {
        if (PLAYER_HEAD_CACHE.containsKey(player.getUniqueId()))
            return builder(PLAYER_HEAD_CACHE.get(player.getUniqueId()).clone());
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(player);
        item.setItemMeta(meta);
        PLAYER_HEAD_CACHE.put(player.getUniqueId(), item);
        return builder(item.clone());
    }

    public void applyToMeta(ParameterRunnable<ItemMeta> applyToMeta) {
        ItemMeta meta = this.itemStack.getItemMeta();
        applyToMeta.run(meta);
        this.itemStack.setItemMeta(meta);
    }

    public boolean hasNamedSpacedKey(NamespacedKey key) {
        return this.itemStack.getItemMeta().getPersistentDataContainer().has(key);
    }

    public <T> boolean hasNamedSpacedKey(NamespacedKey key, PersistentDataType<?, T> type) {
        return this.itemStack.getItemMeta().getPersistentDataContainer().has(key, type);
    }

    @Nullable
    public <T> T getNamedSpacedKeyValue(NamespacedKey key, PersistentDataType<?, T> type) {
        PersistentDataContainer container = this.itemStack.getItemMeta().getPersistentDataContainer();
        if (container.has(key, type))
            return container.get(key, type);
        return null;
    }
}
