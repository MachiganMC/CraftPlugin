package be.machigan.craftplugin.menu.inventory.builder;

import be.machigan.craftplugin.formatter.color.StringColor;
import be.machigan.craftplugin.menu.inventory.InventoryMenu;
import be.machigan.craftplugin.menu.inventory.InventoryMenuSize;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

@SuppressWarnings("deprecation")
public class SpigotInventoryMenuCreator implements InventoryMenuCreator {
    private final StringColor color = new StringColor();

    @Override
    public Inventory createInventory(String name, InventoryMenuSize size, InventoryMenu holder) {
        return Bukkit.createInventory(
                holder,
                size.getSize(),
                this.color.toColoredComponent(name)
        );
    }
}
