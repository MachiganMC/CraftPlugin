package be.machigan.craftplugin.menu.inventory.builder;

import be.machigan.craftplugin.lambda.ParameterRunnable;
import be.machigan.craftplugin.menu.event.lyfecycle.InventoryLifeCycleEvent;
import be.machigan.craftplugin.menu.inventory.InventoryMenu;
import be.machigan.craftplugin.menu.item.Item;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
public class InventoryMenuBuilder {
    private final InventoryMenu menu;

    public InventoryMenuBuilder setItem(int position, @Nullable Item item) {
        this.menu.getInventory().setItem(
                position,
                item != null ? item.getItemStack() : null
        );
        return this;
    }

    public InventoryMenuBuilder setItem(int position, Function<InventoryMenu, @Nullable Item> runnableItem) {
        return this.setItem(position, runnableItem.apply(this.menu));
    }

    public InventoryMenuBuilder fill(@Nullable Item item, int from, int to) {
        Preconditions.checkArgument(from < to, "'From' must be tinier than 'to'");
        for (int i = from; i <= to; i++) {
            this.setItem(i, item);
        }
        return this;
    }

    public InventoryMenuBuilder fill(Function<InventoryMenu, @Nullable Item> runnableItem, int from, int to) {
        return this.fill(runnableItem.apply(this.menu), from, to);
    }

    public InventoryMenuBuilder fill(@Nullable Item item) {
        return this.fill(item, 0, this.menu.getInventory().getSize() - 1);
    }

    public InventoryMenuBuilder fill(Function<InventoryMenu, @Nullable Item> runnableItem) {
        return this.fill(runnableItem.apply(this.menu));
    }

    public InventoryMenuBuilder fillRow(@Nullable Item item, int row) {
        return this.fill(item, row * 9, (row * 9) + 8);
    }

    public InventoryMenuBuilder fillRow(Function<InventoryMenu, @Nullable Item> runnableItem, int row) {
        return this.fillRow(runnableItem.apply(this.menu), row);
    }

    public InventoryMenuBuilder fillFirstRow(@Nullable Item item) {
        return this.fillRow(item, 0);
    }

    public InventoryMenuBuilder fillFirstRow(Function<InventoryMenu, @Nullable Item> runnableItem) {
        return this.fillFirstRow(runnableItem.apply(this.menu));
    }

    public InventoryMenuBuilder fillLastRow(@Nullable Item item) {
        return this.fillRow(item, (this.menu.getInventory().getSize() / 9) - 1);
    }

    public InventoryMenuBuilder fillLastRow(Function<InventoryMenu, @Nullable Item> runnableItem) {
        return this.fillLastRow(runnableItem.apply(this.menu));
    }

    public InventoryMenuBuilder removeItem(int position) {
        return this.setItem(position, (Item) null);
    }

    public InventoryMenuBuilder clear() {
        return this.fill((Item) null);
    }

    public InventoryMenuBuilder doIfHasItem(int positon, ParameterRunnable<@NotNull Item> runnable) {
        if (this.menu.hasItemAt(positon))
            runnable.run(this.menu.getItem(positon));
        return this;
    }

    public InventoryMenuBuilder startItemUpdater(int positon, Function<Item, Item> updater, long ticksToWait) {
        Preconditions.checkArgument(ticksToWait > 0, "Ticks to wait must be positive (" + ticksToWait + ")");
        this.menu.addLifeCycleEvent(new InventoryLifeCycleEvent(
                positon,
                updater,
                ticksToWait
        ));
        return this;
    }

    public InventoryMenuBuilder stopItemUpdater(int position) {
        this.menu
                .getLifeCycleEvents()
                .stream()
                .filter(event -> event.getPosition() == position)
                .forEach(InventoryLifeCycleEvent::onClose)
                ;
        return this;
    }

    public InventoryMenu build() {
        return this.menu;
    }
}
