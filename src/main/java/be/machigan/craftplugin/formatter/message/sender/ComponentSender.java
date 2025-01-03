package be.machigan.craftplugin.formatter.message.sender;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

import java.util.Collection;

@ApiStatus.Internal
public interface ComponentSender {
    void sendInChat(MessageContent content, Player player);

    void sendInChat(MessageContent content, Collection<Player> players);

    void sendInHotbar(MessageContent content, Player player);

    void sendInHotbar(MessageContent content, Collection<Player> player);

    void broadcast(MessageContent content);

    void broadcastInHotbar(MessageContent content);
}
