package be.machigan.craftplugin.menu.inventory.builder;

import be.machigan.craftplugin.formatter.color.PaperColor;
import be.machigan.craftplugin.menu.inventory.InventoryMenu;
import be.machigan.craftplugin.menu.inventory.InventoryMenuSize;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class PaperInventoryMenuCreator implements InventoryMenuCreator {
    private final PaperColor color = new PaperColor();

    @Override
    public Inventory createInventory(String name, InventoryMenuSize size, InventoryMenu holder) {
        return Bukkit.createInventory(
                holder,
                size.getSize(),
                this.color.toColoredComponent(name)
        );
    }
}
