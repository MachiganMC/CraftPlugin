package be.machigan.craftplugin.menu.event.click;

import be.machigan.craftplugin.menu.inventory.builder.InventoryMenuBuilder;
import be.machigan.craftplugin.menu.item.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;

public record InventoryMenuClickEvent(
        ClickType click,
        InventoryAction action,
        InventoryType.SlotType slotType,
        int whichSlot,
        int rawSlot,
        Item clickedItem,
        int hotbarKey,
        InventoryMenuBuilder builder,
        InventoryView view,
        Player whoClicked
) {
}
