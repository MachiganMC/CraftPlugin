package be.machigan.craftplugin.utils;

import be.machigan.craftplugin.CraftPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class FileUtils {
    public static void generateFileFromResourcesIfNotExists(File file) throws IOException {
        String dataFolder = CraftPlugin.getPlugin().getDataFolder().getPath();
        String resourcesPath = file.getPath().replace(dataFolder, "");
        if (!file.exists()) {
            Files.copy(
                    Objects.requireNonNull(CraftPlugin.getPlugin().getClass().getResourceAsStream(resourcesPath)),
                    Paths.get(file.getPath()),
                    StandardCopyOption.REPLACE_EXISTING
            );
        }
    }
}
