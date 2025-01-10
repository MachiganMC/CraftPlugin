package be.machigan.craftplugin.provider.customhead;

import org.bukkit.inventory.ItemStack;

public interface CustomHeadProvider {
    ItemStack customHeadFromBase64(String b64);
}
