package be.machigan.craftplugin.menu.inventory;

import be.machigan.craftplugin.lambda.ParameterRunnable;
import be.machigan.craftplugin.menu.event.click.InventoryMenuClickEvent;
import be.machigan.craftplugin.menu.event.lyfecycle.InventoryLifeCycleEvent;
import be.machigan.craftplugin.menu.event.lyfecycle.InventoryMenuCloseEvent;
import be.machigan.craftplugin.menu.inventory.builder.InventoryMenuBuilder;
import be.machigan.craftplugin.menu.inventory.builder.InventoryMenuCreator;
import be.machigan.craftplugin.menu.inventory.builder.PaperInventoryMenuCreator;
import be.machigan.craftplugin.menu.inventory.builder.SpigotInventoryMenuCreator;
import be.machigan.craftplugin.menu.item.Item;
import be.machigan.craftplugin.utils.version.ServerVersion;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class InventoryMenu implements InventoryHolder {
    private final Inventory inventory;
    @Getter @Setter
    private boolean isOpen = false;
    @Getter @Setter
    private boolean cancelClick = false;
    private final Map<UUID, ParameterRunnable<InventoryMenuClickEvent>> clickEvents = new HashMap<>();
    private final List<ParameterRunnable<InventoryMenuCloseEvent>> closeEvents = new ArrayList<>();
    @Getter
    private final List<InventoryLifeCycleEvent> lifeCycleEvents = new ArrayList<>();
    private static final InventoryMenuCreator menuCreator = ServerVersion.isPaperServer() ?
            new PaperInventoryMenuCreator() :
            new SpigotInventoryMenuCreator()
            ;

    private InventoryMenu(String name, InventoryMenuSize size) {
        this.inventory = menuCreator.createInventory(name, size, this);
    }

    public static InventoryMenuBuilder builder(InventoryMenu menu) {
        return new InventoryMenuBuilder(menu);
    }

    public static InventoryMenuBuilder builder(String name, InventoryMenuSize size) {
        return builder(new InventoryMenu(name, size));
    }

    public InventoryMenuBuilder builder() {
        return builder(this);
    }

    public void addClickEvent(UUID eventIdentifier, ParameterRunnable<InventoryMenuClickEvent> event) {
        this.clickEvents.put(eventIdentifier, event);
    }

    public void fireClickEvent(UUID key, InventoryMenuClickEvent eventData) {
        ParameterRunnable<InventoryMenuClickEvent> event = this.clickEvents.get(key);
        if (event == null)
            throw new IllegalArgumentException("Event with uuid not found : " + key);
        event.run(eventData);
    }

    public void addLifeCycleEvent(InventoryLifeCycleEvent event) {
        this.lifeCycleEvents.add(event);
        if (this.isOpen)
            event.onOpen(this.builder());
    }

    public void fireOpenEvents() {
        this.lifeCycleEvents.forEach(eventsHolder -> eventsHolder.onOpen(this.builder()));
    }

    public void addCloseEvent(ParameterRunnable<InventoryMenuCloseEvent> onClose) {
        this.closeEvents.add(onClose);
    }

    public void fireCloseEvents(InventoryMenuCloseEvent event) {
        this.lifeCycleEvents.forEach(InventoryLifeCycleEvent::onClose);
        this.closeEvents.forEach(r -> r.run(event));
    }

    public Item getItem(int position) {
        return Item.fromItemStack(this.inventory.getItem(position));
    }

    public boolean hasItemAt(int positon) {
        return this.inventory.getItem(positon) != null;
    }

    public void openTo(Player player) {
        player.openInventory(this.inventory);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }
}
