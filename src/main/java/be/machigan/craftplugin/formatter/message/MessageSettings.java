package be.machigan.craftplugin.formatter.message;

import be.machigan.craftplugin.CraftPlugin;
import be.machigan.craftplugin.lambda.ParameterReturnableRunnable;
import be.machigan.craftplugin.service.PlaceholderAPIService;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class MessageSettings {
    private File messagesFile;
    private FileConfiguration messagesConfigFile;
    private final EnumMap<MessagePart, String> additionalPathMap = new EnumMap<>(MessagePart.class);
    @Setter
    @Nullable private String customPlaceholdersPath = "PersonalVariable";
    @Getter
    @Setter
    private ParameterReturnableRunnable<String> messageNotFoundError = path -> "The message from \"" + path + "\" doesn't exist.";

    public MessageSettings() {
        this.messagesFile = new File(CraftPlugin.getPlugin().getDataFolder(), "messages.yml");
        Arrays.stream(MessagePart.values()).forEach(part -> this.additionalPathMap.put(part, part.getDefaultPath()));
    }

    public void setPathValue(@NotNull MessagePart path, @Nullable String value) {
        additionalPathMap.put(path, value);
    }

    @ApiStatus.Internal
    public @NotNull Map<String, String> getCustomPlaceholders() {
        Map<String, String> placeholdersMap = new HashMap<>();
        if (this.customPlaceholdersPath == null) return placeholdersMap;
        ConfigurationSection section = this.messagesConfigFile.getConfigurationSection(this.customPlaceholdersPath);
        if (section == null) return placeholdersMap;
        section.getKeys(false).forEach(key -> placeholdersMap.put("{" + key + "}", section.getString(key)));
        return placeholdersMap;
    }

    public void setMessagesFile(@NotNull File newMessagesFile) {
        messagesFile = newMessagesFile;
        messagesConfigFile = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public void setMessagesFile(@NotNull String pathInPluginFolder) {
        setMessagesFile(new File(CraftPlugin.getPlugin().getDataFolder(), pathInPluginFolder));
    }

    @ApiStatus.Internal
    public void reload() {
        messagesConfigFile = YamlConfiguration.loadConfiguration(messagesFile);
    }

    @ApiStatus.Internal
    public @Nullable String getContent(String path, OfflinePlayer player) {
        String content = messagesConfigFile.getString(path);
        return content == null ? null :  PlaceholderAPIService.setPlaceholdersIfEnabled(content, player);
    }

    @ApiStatus.Internal
    public @Nullable String getAdditionalContent(String path, MessagePart part, OfflinePlayer player) {
        String additionalPathValue = additionalPathMap.get(part);
        if (additionalPathValue == null) return null;
        String additionalContent = messagesConfigFile.getString(path + additionalPathValue);
        if (additionalContent == null) return null;
        return PlaceholderAPIService.setPlaceholdersIfEnabled(additionalContent, player);
    }
}
