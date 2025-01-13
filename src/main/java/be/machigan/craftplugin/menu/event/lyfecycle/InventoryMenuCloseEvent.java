package be.machigan.craftplugin.menu.event.lyfecycle;

import be.machigan.craftplugin.menu.inventory.builder.InventoryMenuBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;

public record InventoryMenuCloseEvent(
        InventoryCloseEvent.Reason reason,
        InventoryMenuBuilder builder,
        InventoryView view,
        Player player
) {
}
