package be.machigan.craftplugin.formatter.message;

import be.machigan.craftplugin.formatter.message.sender.Sender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;

public interface MessageRecipient extends Sender {

    void reload();

    void send(@NotNull Player player);

    void send(@NotNull Collection<Player> players);

    void broadcast();

    @Contract("_, _ -> this")
    @NotNull MessageRecipient replace(@NotNull String from, @NotNull String to);

    @Contract("_ -> this")
    @NotNull MessageRecipient replace(@NotNull Map<String, String> fromToMap);

    @Contract("_ -> this")
    @NotNull MessageRecipient replace(@NotNull MessagePlaceholder messagePlaceholder);

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
