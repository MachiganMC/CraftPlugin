package be.machigan.craftplugin.menu.event;

import be.machigan.craftplugin.menu.event.click.InventoryMenuClickEvent;
import be.machigan.craftplugin.menu.inventory.InventoryMenu;
import be.machigan.craftplugin.menu.item.Item;
import org.bukkit.event.inventory.InventoryClickEvent;

public class EventConverter {
    public static InventoryMenuClickEvent clickEvent(InventoryClickEvent event) {
        if (
                event.getClickedInventory() == null
                || event.getClickedInventory().getHolder() == null
                || !(event.getClickedInventory().getHolder() instanceof InventoryMenu menu)
        )
            throw new IllegalArgumentException("The event doesn't concern an InventoryMenu");
        return new InventoryMenuClickEvent(
                event.getClick(),
                event.getAction(),
                event.getSlotType(),
                event.getSlot(),
                event.getRawSlot(),
                Item.builder(event.getCurrentItem()).build(),
                event.getHotbarButton(),
                InventoryMenu.builder(menu),
                event.getView()
        );
    }
}
