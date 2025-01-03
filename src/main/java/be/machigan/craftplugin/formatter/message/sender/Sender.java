package be.machigan.craftplugin.formatter.message.sender;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface Sender {
    void send(@NotNull Player player);

    void send(@NotNull Collection<Player> players);

    void broadcast();
}
