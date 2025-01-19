package be.machigan.craftplugin.menu.event;

import be.machigan.craftplugin.menu.event.click.InventoryMenuClickEvent;
import be.machigan.craftplugin.menu.event.lyfecycle.InventoryMenuCloseEvent;
import be.machigan.craftplugin.menu.inventory.InventoryMenu;
import be.machigan.craftplugin.menu.item.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class EventConverter {
    public static InventoryMenuClickEvent clickEvent(InventoryClickEvent event) {
        failIfNotInventoryMenu(event.getClickedInventory());
        return new InventoryMenuClickEvent(
                event.getClick(),
                event.getAction(),
                event.getSlotType(),
                event.getSlot(),
                event.getRawSlot(),
                Item.builder(event.getCurrentItem()).build(),
                event.getHotbarButton(),
                InventoryMenu.builder((InventoryMenu) event.getClickedInventory().getHolder()),
                event.getView(),
                (Player) event.getWhoClicked()
        );
    }

    public static InventoryMenuCloseEvent closeEvent(InventoryCloseEvent event) {
        failIfNotInventoryMenu(event.getInventory());
        return new InventoryMenuCloseEvent(
                event.getReason(),
                InventoryMenu.builder((InventoryMenu) event.getInventory().getHolder()),
                event.getView(),
                (Player) event.getPlayer()
        );
    }

    private static void failIfNotInventoryMenu(Inventory inventory) {
        if (
                        inventory == null
                        || inventory.getHolder() == null
                        || !(inventory.getHolder() instanceof InventoryMenu)
        )
            throw new IllegalArgumentException("The event doesn't concern an InventoryMenu");
    }
}
