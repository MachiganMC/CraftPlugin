package be.machigan.craftplugin.menu.item;

import be.machigan.craftplugin.CraftPlugin;
import be.machigan.craftplugin.lambda.ParameterRunnable;
import be.machigan.craftplugin.menu.event.click.InventoryMenuClickEvent;
import be.machigan.craftplugin.menu.inventory.InventoryMenu;
import be.machigan.craftplugin.menu.item.builder.ItemBuilder;
import be.machigan.craftplugin.menu.persistentdatatype.UuidDataType;
import org.bukkit.NamespacedKey;

import java.util.UUID;

public class InventoryItem {
    public static final NamespacedKey CANCEL_CLICK_KEY = new NamespacedKey(CraftPlugin.getPlugin(), "craft-plugin-cancel-click-key");
    public static final NamespacedKey CLICK_EVENT_KEY = new NamespacedKey(CraftPlugin.getPlugin(), "craft-plugin-click-event-key");
    private final ItemBuilder builder;

    public InventoryItem(Item item) {
        this.builder = item.builder();
    }

    public InventoryItem cancelClick() {
        this.builder.addEmptyNamedSpacedKey(CANCEL_CLICK_KEY);
        return this;
    }

    public InventoryItem onClick(InventoryMenu menu, ParameterRunnable<InventoryMenuClickEvent> onClickEvent) {
        UUID eventIdentifier = UUID.randomUUID();
        this.builder.addNamedSpacedKey(CLICK_EVENT_KEY, UuidDataType.INSTANCE, eventIdentifier);
        menu.addClickEvent(eventIdentifier, onClickEvent);
        return this;
    }

    public InventoryItem onClickAndCancel(InventoryMenu menu, ParameterRunnable<InventoryMenuClickEvent> onClickEvent) {
        return this.
                onClick(menu, onClickEvent)
                .cancelClick()
                ;
    }

    public ItemBuilder builder() {
        return this.builder;
    }

    public Item item() {
        return this.builder.build();
    }
}
