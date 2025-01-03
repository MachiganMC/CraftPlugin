package be.machigan.craftplugin.service;

import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;

public class PlaceholderAPIService {
    @Getter
    private static final boolean isEnabled;

    static {
        boolean isEnabledTemp;
        try {
            Class.forName("me.clip.placeholderapi.PlaceholderAPI");
            Plugin placeholderAPIPlugin = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");
            isEnabledTemp =  placeholderAPIPlugin != null && placeholderAPIPlugin.isEnabled();
        } catch (ClassNotFoundException ignored) {
            isEnabledTemp = false;
        }
        isEnabled = isEnabledTemp;
    }

    @Contract("null, _ -> null; !null, _ -> !null")
    public static String setPlaceholdersIfEnabled(String message, Player player) {
        if (!isEnabled()) return message;
        return PlaceholderAPI.setPlaceholders(player, message);
    }
}
