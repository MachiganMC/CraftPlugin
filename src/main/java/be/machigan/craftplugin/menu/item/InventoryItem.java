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
    public static final NamespacedKey CLICK_CLOSE_INVENTORY = new NamespacedKey(CraftPlugin.getPlugin(), "craft-plugin-click-close-event");
    private final ItemBuilder builder;

    public InventoryItem(Item item) {
        this.builder = item.builder();
    }

    public InventoryItem cancelClick() {
        this.builder.addEmptyNamedSpacedKey(CANCEL_CLICK_KEY);
        return this;
    }

    public InventoryItem onClick(InventoryMenu menu, ParameterRunnable<InventoryMenuClickEvent> onClickEvent) {
        UUID eventIdentifier = this.builder.inventoryItem().hasClickEvent() ?
                builder.build().getNamedSpacedKeyValue(CLICK_EVENT_KEY, UuidDataType.INSTANCE) :
                UUID.randomUUID()
                ;
        this.builder.addNamedSpacedKey(CLICK_EVENT_KEY, UuidDataType.INSTANCE, eventIdentifier);
        menu.addClickEvent(eventIdentifier, onClickEvent);
        return this;
    }

    public InventoryItem closeOnClick() {
        this.builder.addEmptyNamedSpacedKey(CLICK_CLOSE_INVENTORY).addEmptyNamedSpacedKey(CANCEL_CLICK_KEY);
        return this;
    }

    public InventoryItem onClickAndCancel(InventoryMenu menu, ParameterRunnable<InventoryMenuClickEvent> onClickEvent) {
        return this.
                onClick(menu, onClickEvent)
                .cancelClick()
                ;
    }

    public boolean hasCancelClick() {
        return this.item().hasNamedSpacedKey(CANCEL_CLICK_KEY);
    }

    public boolean hasClickEvent() {
        return this.item().hasNamedSpacedKey(CLICK_EVENT_KEY);
    }

    public boolean hasCloseInventoryOnClick() {
        return this.item().hasNamedSpacedKey(CLICK_CLOSE_INVENTORY);
    }

    public ItemBuilder builder() {
        return this.builder;
    }

    public Item item() {
        return this.builder.build();
    }
}
