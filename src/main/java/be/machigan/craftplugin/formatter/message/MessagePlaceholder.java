package be.machigan.craftplugin.formatter.message;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface MessagePlaceholder {
    @NotNull Map<String, String> getPlaceholdersMap();
}
