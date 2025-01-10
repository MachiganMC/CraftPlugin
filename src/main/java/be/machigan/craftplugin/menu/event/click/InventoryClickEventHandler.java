package be.machigan.craftplugin.menu.event.click;

import be.machigan.craftplugin.menu.event.EventConverter;
import be.machigan.craftplugin.menu.event.InventoryEventHandler;
import be.machigan.craftplugin.menu.inventory.InventoryMenu;
import be.machigan.craftplugin.menu.item.InventoryItem;
import be.machigan.craftplugin.menu.item.Item;
import be.machigan.craftplugin.menu.persistentdatatype.UuidDataType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickEventHandler extends InventoryEventHandler {
    @EventHandler
    @SuppressWarnings("unused")
    public void onClick(InventoryClickEvent event) {
        InventoryMenu inventoryMenu = this.getInventoryMenu(event.getClickedInventory());
        if (inventoryMenu == null) return;
        if (event.getCurrentItem() == null) return;
        Item item = Item.builder(event.getCurrentItem()).build();
        if (item.hasNamedSpacedKey(InventoryItem.CANCEL_CLICK_KEY))
            event.setCancelled(true);
        if (item.hasNamedSpacedKey(InventoryItem.CLICK_EVENT_KEY, UuidDataType.INSTANCE))
            fireClickEvent(inventoryMenu, event, item);
    }

    private static void fireClickEvent(InventoryMenu menu, InventoryClickEvent event, Item item) {
        menu.fireClickEvent(
                item.getNamedSpacedKeyValue(InventoryItem.CLICK_EVENT_KEY, UuidDataType.INSTANCE),
                EventConverter.clickEvent(event)
        );
    }
}
