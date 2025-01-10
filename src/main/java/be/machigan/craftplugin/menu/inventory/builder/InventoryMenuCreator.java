package be.machigan.craftplugin.menu.inventory.builder;

import be.machigan.craftplugin.menu.inventory.InventoryMenu;
import be.machigan.craftplugin.menu.inventory.InventoryMenuSize;
import org.bukkit.inventory.Inventory;

public interface InventoryMenuCreator {
    Inventory createInventory(String name, InventoryMenuSize size, InventoryMenu holder);
}
