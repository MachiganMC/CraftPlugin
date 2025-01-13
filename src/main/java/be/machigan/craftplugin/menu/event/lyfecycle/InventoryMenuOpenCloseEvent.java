package be.machigan.craftplugin.menu.event.lyfecycle;

import be.machigan.craftplugin.menu.event.EventConverter;
import be.machigan.craftplugin.menu.event.InventoryEventHandler;
import be.machigan.craftplugin.menu.inventory.InventoryMenu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class InventoryMenuOpenCloseEvent extends InventoryEventHandler {
    @EventHandler
    @SuppressWarnings("unused")
    public void onOpen(InventoryOpenEvent event) {
        InventoryMenu menu = this.getInventoryMenu(event.getInventory());
        if (menu == null) return;
        menu.setOpen(true);
        menu.fireOpenEvents();
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void onClose(InventoryCloseEvent event) {
        InventoryMenu menu = this.getInventoryMenu(event.getInventory());
        if (menu == null) return;
        menu.setOpen(false);
        menu.fireCloseEvents(EventConverter.closeEvent(event));
    }
}
