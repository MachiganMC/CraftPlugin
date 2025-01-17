package be.machigan.craftplugin.formatter.message;

import be.machigan.craftplugin.formatter.message.sender.Sender;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;

public interface MessageRecipient extends Sender {

    void send(@NotNull Player player);

    void send(@NotNull CommandSender sender);

    void send(@NotNull Collection<Player> players);

    void broadcast();

    void mail(@NotNull OfflinePlayer player);

    void mail(@NotNull Collection<OfflinePlayer> players);

    @Contract("_, _ -> this")
    @NotNull MessageRecipient replace(@NotNull String from, @NotNull String to);

    @Contract("_ -> this")
    @NotNull MessageRecipient replace(@NotNull Map<String, String> fromToMap);

    @Contract("_ -> this")
    @NotNull MessageRecipient replace(@NotNull MessagePlaceholder messagePlaceholder);

    @Contract("_ -> this")
    @NotNull MessageRecipient replace(@NotNull MessagePlaceholder... messagePlaceholders);

    @Contract("-> this")
    @NotNull MessageRecipient disableHoverContent();

    @Contract("-> this")
    @NotNull MessageRecipient disableHotbarContent();

    @Contract("-> this")
    @NotNull MessageRecipient disableRunCommandContent();

    @Contract("-> this")
    @NotNull MessageRecipient disableSuggestCommandContent();

    @Contract("-> this")
    @NotNull MessageRecipient disableCopyToClipboard();

    @Contract("-> this")
    @NotNull MessageRecipient disableCustomPlaceholders();

    @Contract("_ -> this")
    @NotNull MessageRecipient setHoverContent(@NotNull String hoverContent);

    @Contract("_ -> this")
    @NotNull MessageRecipient setHotbarContent(@NotNull String hotbarContent);

    @Contract("_ -> this")
    @NotNull MessageRecipient setRunCommandContent(@NotNull String runCommandContent);

    @Contract("_ -> this")
    @NotNull MessageRecipient setSuggestedCommandContent(@NotNull String suggestedCommandContent);

    @Contract("_ -> this")
    @NotNull MessageRecipient setCopyToClipboardContent(@NotNull String copyToClipboardContent);
}
