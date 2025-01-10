package be.machigan.craftplugin.provider;

import be.machigan.craftplugin.provider.customhead.CustomHeadProvider;
import be.machigan.craftplugin.provider.customhead.Post1_21CustomHeadProvider;
import be.machigan.craftplugin.provider.customhead.Pre1_21CustomHeadProvider;
import be.machigan.craftplugin.utils.version.MinecraftVersion;
import be.machigan.craftplugin.utils.version.ServerVersion;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Provider {
    private static final CustomHeadProvider CUSTOM_HEAD_PROVIDER = getCustomHeadProvider();

    public static CustomHeadProvider getCustomHeadProvider() {
        if (CUSTOM_HEAD_PROVIDER != null) return CUSTOM_HEAD_PROVIDER;
        return ServerVersion.CURRENT_MINECRAFT_VERSION.isRecentThan(new MinecraftVersion(21, 0)) ?
                new Post1_21CustomHeadProvider() :
                new Pre1_21CustomHeadProvider()
                ;

    }
}
