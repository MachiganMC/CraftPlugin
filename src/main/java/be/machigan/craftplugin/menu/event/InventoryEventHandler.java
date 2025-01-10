package be.machigan.craftplugin.menu.event;

import be.machigan.craftplugin.CraftPlugin;
import be.machigan.craftplugin.menu.event.click.InventoryClickEventHandler;
import be.machigan.craftplugin.menu.event.lyfecycle.InventoryMenuOpenCloseEvent;
import be.machigan.craftplugin.menu.inventory.InventoryMenu;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public class InventoryEventHandler implements Listener {
    protected InventoryMenu getInventoryMenu(@Nullable Inventory inventory) {
        if (
                inventory != null
                && inventory.getHolder() != null
                && inventory.getHolder() instanceof InventoryMenu menu
        ) return menu;
        return null;
    }

    public static void registerEvents() {
        JavaPlugin plugin = CraftPlugin.getPlugin();
        plugin.getServer().getPluginManager().registerEvents(new InventoryClickEventHandler(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new InventoryMenuOpenCloseEvent(), plugin);
    }
}
