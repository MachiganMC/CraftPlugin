package be.machigan.craftplugin.utils;

import be.machigan.craftplugin.CraftPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtils {
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void generateFileFromResourcesIfNotExists(File file) throws IOException {
        String dataFolder = CraftPlugin.getPlugin().getDataFolder().getPath();
        String resourcesPath = file.getPath().replace(dataFolder, "");
        if (file.exists()) return;
        try (InputStream resourcesToCopy = CraftPlugin.getPlugin().getClass().getResourceAsStream(resourcesPath)) {
            if (resourcesToCopy == null)
                throw new FileNotFoundException("File '" + resourcesPath + "' not found in resources");
            file.mkdirs();
            Files.copy(
                    resourcesToCopy,
                    Paths.get(file.getPath()),
                    StandardCopyOption.REPLACE_EXISTING
            );
        }
    }
}
