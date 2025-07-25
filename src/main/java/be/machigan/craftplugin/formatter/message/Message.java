package be.machigan.craftplugin.formatter.message;


import be.machigan.craftplugin.CraftPlugin;
import be.machigan.craftplugin.formatter.color.AnsiColor;
import be.machigan.craftplugin.formatter.color.StringColor;
import be.machigan.craftplugin.formatter.message.sender.ComponentSender;
import be.machigan.craftplugin.formatter.message.sender.MessageContent;
import be.machigan.craftplugin.utils.Tools;
import com.google.common.base.Preconditions;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Collection;
import java.util.Map;


public class Message implements MessageRecipient {
    private static ComponentSender sender;
    @Setter
    private static MessageSettings settings = new MessageSettings();
    private final MessageContent content;
    private boolean isCustomPlaceholdersEnable = true;
    private static final StringColor STRING_COLOR = new StringColor();

    Message(String path, OfflinePlayer player, boolean isNecessary) {
        this.content = new MessageContent(settings, path, player);
        if (this.content.getContent() == null && isNecessary) {
            String error = settings.getMessageNotFoundError().run(path);
            this.content.setContent(error);
            CraftPlugin.getPlugin().getLogger().warning(error);
        }
    }

    Message(String content) {
        this.content = new MessageContent(content);
    }

    @Contract("_ -> new")
    public static @NotNull Message getMessage(@NotNull String path) {
        return getMessage(path, null, true);
    }

    @Contract("_, _ -> new")
    public static @NotNull Message getMessage(@NotNull String path, @Nullable OfflinePlayer player) {
        return getMessage(path, player, true);
    }

    @Contract("_, _ -> new")
    public static @NotNull Message getMessage(@NotNull String path, boolean isNecessary) {
        return getMessage(path, null, isNecessary);
    }

    @Contract("_, _, _ -> new")
    public static @NotNull Message getMessage(@NotNull String path, @Nullable OfflinePlayer player, boolean isNecessary) {
        return new Message(path, player, isNecessary);
    }

    @Contract("_ -> new")
    public static @NotNull Message getStaticMessage(@NotNull String content) {
        return new Message(content);
    }

    @Override
    public @NotNull Message replace(@NotNull String from, @NotNull String to) {
        this.content.replace(from, to);
        return this;
    }

    @Override
    public @NotNull MessageRecipient replace(@NotNull Map<String, String> fromToMap) {
        fromToMap.forEach(this::replace);
        return this;
    }

    @Override
    public void send(@NotNull CommandSender sender) {
        if (sender instanceof Player player) {
            this.send(player);
        } else {
            Bukkit.getConsoleSender().sendMessage(STRING_COLOR.toColoredComponent(this.content.getContent()));
        }
    }

    @Override
    public @NotNull MessageRecipient replace(@NotNull MessagePlaceholder messagePlaceholder) {
        return this.replace(messagePlaceholder.getPlaceholdersMap());
    }

    @Override
    public @NotNull MessageRecipient replace(@NotNull MessagePlaceholder... messagePlaceholders) {
        for (MessagePlaceholder placeholder : messagePlaceholders)
            this.replace(placeholder);
        return this;
    }

    private void replaceCustomPlaceholders() {
        if (this.isCustomPlaceholdersEnable)
            this.replace(settings.getCustomPlaceholders());
    }

    @Override
    public void send(@NotNull Player player) {
        this.replaceCustomPlaceholders();
        sender.sendInChat(this.content, player);
        sender.sendInHotbar(this.content, player);
    }

    @Override
    public void send(@NotNull Collection<Player> players) {
        this.replaceCustomPlaceholders();
        sender.sendInChat(this.content, players);
        sender.sendInHotbar(this.content, players);
    }

    @Override
    public void broadcast() {
        this.replaceCustomPlaceholders();
        sender.broadcast(this.content);
        sender.broadcastInHotbar(this.content);
    }

    @Override
    public void mail(@NotNull OfflinePlayer player) {
        this.replaceCustomPlaceholders();
        Tools.makeServerExecuteCommand(
                "mail send " + player.getName() + " " + STRING_COLOR.toColoredComponent(this.content.getContent())
        );
    }

    @Override
    public void mail(@NotNull Collection<OfflinePlayer> players) {
        this.replaceCustomPlaceholders();
        players.forEach(player -> Tools.makeServerExecuteCommand(
                "mail send " + player.getName() + " " + STRING_COLOR.toColoredComponent(this.content.getContent())
        ));
    }

    private MessageRecipient setAdditionalContent(MessagePart part, @Nullable String additionalContent) {
        this.content.setAdditionalContent(part, additionalContent);
        return this;
    }

    @Override
    public @NotNull MessageRecipient disableHoverContent() {
        return this.setAdditionalContent(MessagePart.HOVER, null);
    }

    @Override
    public @NotNull MessageRecipient disableHotbarContent() {
        return this.setAdditionalContent(MessagePart.HOTBAR, null);
    }

    @Override
    public @NotNull MessageRecipient disableRunCommandContent() {
        return this.setAdditionalContent(MessagePart.RUN_COMMAND, null);
    }

    @Override
    public @NotNull MessageRecipient disableSuggestCommandContent() {
        return this.setAdditionalContent(MessagePart.SUGGESTED_COMMAND, null);
    }

    @Override
    public @NotNull MessageRecipient disableCopyToClipboard() {
        return this.setAdditionalContent(MessagePart.COPY_TO_CLIPBOARD, null);
    }

    @Override
    public @NotNull MessageRecipient disableCustomPlaceholders() {
        this.isCustomPlaceholdersEnable = false;
        return this;
    }

    @Override
    public @NotNull MessageRecipient setHoverContent(@NotNull String hoverContent) {
        return this.setAdditionalContent(MessagePart.HOVER, hoverContent);
    }

    @Override
    public @NotNull MessageRecipient setHotbarContent(@NotNull String hotbarContent) {
        return this.setAdditionalContent(MessagePart.HOTBAR, hotbarContent);
    }

    @Override
    public @NotNull MessageRecipient setRunCommandContent(@NotNull String runCommandContent) {
        return this.setAdditionalContent(MessagePart.RUN_COMMAND, runCommandContent);
    }

    @Override
    public @NotNull MessageRecipient setSuggestedCommandContent(@NotNull String suggestedCommandContent) {
        return this.setAdditionalContent(MessagePart.RUN_COMMAND, suggestedCommandContent);
    }

    @Override
    public @NotNull MessageRecipient setCopyToClipboardContent(@NotNull String copyToClipboardContent) {
        return this.setAdditionalContent(MessagePart.COPY_TO_CLIPBOARD, copyToClipboardContent);
    }

    public void applySettings(@NotNull MessageSettings newSettings) {
        Preconditions.checkNotNull(newSettings);
        settings = newSettings;
    }

    public static void reload() {
        settings.reload();
    }

    @ApiStatus.Internal
    public static void registerSender(ComponentSender sender) {
        Message.sender = sender;
        settings.setMessagesFile(new File(CraftPlugin.getPlugin().getDataFolder(), "messages.yml"));
    }

    public static void log(String message, String pluginPrefix) {
        Bukkit.getConsoleSender().sendMessage(AnsiColor.ansi(pluginPrefix + " " + message));
    }

    public static void log(String message) {
        log(message, "[" + CraftPlugin.getPlugin().getName() + "]");
    }
}
