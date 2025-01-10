package be.machigan.craftplugin.menu.event.lyfecycle;

import be.machigan.craftplugin.CraftPlugin;
import be.machigan.craftplugin.menu.inventory.builder.InventoryMenuBuilder;
import be.machigan.craftplugin.menu.item.Item;
import com.google.common.base.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@RequiredArgsConstructor
public class InventoryLifeCycleEvent {
    @Getter
    private final int position;
    private final Function<Item, Item> runnable;
    private final long ticksToWait;
    private Integer runnableId;

    public void onOpen(InventoryMenuBuilder builder) {
        runnableId = Bukkit.getScheduler().scheduleSyncRepeatingTask(
                CraftPlugin.getPlugin(), () -> builder.doIfHasItem(this.position, this.runnable::apply),
                ticksToWait,
                ticksToWait
        );
    }

    public void onClose() {
        if (runnableId != null)
            Bukkit.getScheduler().cancelTask(runnableId);
    }
}
