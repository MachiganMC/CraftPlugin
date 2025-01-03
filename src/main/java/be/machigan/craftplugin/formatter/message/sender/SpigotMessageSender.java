package be.machigan.craftplugin.formatter.message.sender;

import be.machigan.craftplugin.formatter.color.SpigotColor;
import be.machigan.craftplugin.formatter.message.MessagePart;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

@SuppressWarnings("deprecation")
@ApiStatus.Internal
public class SpigotMessageSender implements ComponentSender {
    private static final SpigotColor color = new SpigotColor();

    @Override
    public void sendInChat(MessageContent content, Player player) {
        TextComponent chatComponent = getChatComponent(content);
        if (chatComponent != null)
            player.sendMessage(chatComponent);
    }

    @Override
    public void sendInChat(MessageContent content, Collection<Player> players) {
        TextComponent chatComponent = getChatComponent(content);
        if (chatComponent != null)
            players.forEach(player -> player.sendMessage(chatComponent));
    }

    private static @Nullable TextComponent getChatComponent(MessageContent content) {
        if (content.getContent() == null) return null;
        TextComponent chatComponent = color.toColoredTextComponent(content.getContent());
        String hover = content.getAdditionalContent(MessagePart.HOVER);
        if (hover != null)
            chatComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(color.toColoredComponent(hover))));
        String runCommand = content.getAdditionalContent(MessagePart.RUN_COMMAND);
        if (runCommand != null)
            chatComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, runCommand));
        String suggestedCommand = content.getAdditionalContent(MessagePart.SUGGESTED_COMMAND);
        if (suggestedCommand != null)
            chatComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, suggestedCommand));
        String copyToClipboard = content.getAdditionalContent(MessagePart.COPY_TO_CLIPBOARD);
        if (copyToClipboard != null)
            chatComponent.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, copyToClipboard));
        return chatComponent;
    }


    @Override
    public void sendInHotbar(MessageContent content, Player player) {
        String hotbar = content.getAdditionalContent(MessagePart.HOTBAR);
        if (hotbar != null)
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, color.toColoredTextComponent(hotbar));
    }

    @Override
    public void sendInHotbar(MessageContent content, Collection<Player> players) {
        String hotbar = content.getAdditionalContent(MessagePart.HOTBAR);
        if (hotbar == null) return;
        TextComponent hotbarComponent = color.toColoredTextComponent(hotbar);
        players.forEach(player -> player.spigot().sendMessage(ChatMessageType.ACTION_BAR, hotbarComponent));
    }

    @Override
    public void broadcast(MessageContent content) {
        TextComponent chatComponent = getChatComponent(content);
        if (chatComponent != null)
            Bukkit.getServer().broadcast(chatComponent);
    }

    @Override
    public void broadcastInHotbar(MessageContent content) {
        String hotbar = content.getAdditionalContent(MessagePart.HOTBAR);
        if (hotbar == null) return;
        TextComponent hotbarComponent = color.toColoredTextComponent(hotbar);
        Bukkit.getOnlinePlayers().forEach(player -> player.sendActionBar(hotbarComponent));
    }
}
