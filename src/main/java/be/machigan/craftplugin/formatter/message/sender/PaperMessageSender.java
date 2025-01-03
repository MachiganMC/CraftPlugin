package be.machigan.craftplugin.formatter.message.sender;

import be.machigan.craftplugin.formatter.color.PaperColor;
import be.machigan.craftplugin.formatter.message.MessagePart;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

@ApiStatus.Internal
public class PaperMessageSender implements ComponentSender {
    private static final PaperColor color = new PaperColor();

    @Override
    public void sendInChat(MessageContent content, Player player) {
        Component chatComponent = getChatComponent(content);
        if (chatComponent != null)
            player.sendMessage(chatComponent);
    }

    @Override
    public void sendInChat(MessageContent content, Collection<Player> players) {
        Component chatComponent = getChatComponent(content);
        if (chatComponent == null) return;
        players.forEach(player -> player.sendMessage(chatComponent));
    }

    private static @Nullable Component getChatComponent(MessageContent content) {
        if (content.getContent() == null) return null;
        Component chatComponent = color.toColoredComponent(content.getContent());
        String hover = content.getAdditionalContent(MessagePart.HOVER);
        if (hover != null)
            chatComponent = chatComponent.hoverEvent(HoverEvent.showText(color.toColoredComponent(hover)));
        String runCommand = content.getAdditionalContent(MessagePart.SUGGESTED_COMMAND);
        if (runCommand != null)
            chatComponent = chatComponent.clickEvent(ClickEvent.runCommand(runCommand));
        String suggestedCommand = content.getAdditionalContent(MessagePart.SUGGESTED_COMMAND);
        if (suggestedCommand != null)
            chatComponent = chatComponent.clickEvent(ClickEvent.suggestCommand(suggestedCommand));
        String copyText = content.getAdditionalContent(MessagePart.COPY_TO_CLIPBOARD);
        if (copyText != null)
            chatComponent = chatComponent.clickEvent(ClickEvent.copyToClipboard(copyText));
        return chatComponent;
    }

    @Override
    public void sendInHotbar(MessageContent content, Player player) {
        String hotbar = content.getAdditionalContent(MessagePart.HOTBAR);
        if (hotbar != null)
            player.sendActionBar(color.toColoredComponent(hotbar));
    }

    @Override
    public void sendInHotbar(MessageContent content, Collection<Player> players) {
        String hotbar = content.getAdditionalContent(MessagePart.HOTBAR);
        if (hotbar == null) return;
        Component hotbarComponent = color.toColoredComponent(hotbar);
        players.forEach(player -> player.sendActionBar(hotbarComponent));
    }

    @Override
    public void broadcast(MessageContent content) {
        Component chatComponent = getChatComponent(content);
        if (chatComponent != null)
            Bukkit.getServer().broadcast(chatComponent);
    }

    @Override
    public void broadcastInHotbar(MessageContent content) {
        String hotbar = content.getAdditionalContent(MessagePart.HOTBAR);
        if (hotbar == null) return;
        Component hotbarComponent = color.toColoredComponent(hotbar);
        Bukkit.getOnlinePlayers().forEach(player -> player.sendActionBar(hotbarComponent));
    }
}
