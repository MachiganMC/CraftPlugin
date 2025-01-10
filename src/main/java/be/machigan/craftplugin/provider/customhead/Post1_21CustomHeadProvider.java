package be.machigan.craftplugin.provider.customhead;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class Post1_21CustomHeadProvider implements CustomHeadProvider {
    @Override
    public ItemStack customHeadFromBase64(String b64) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        head.editMeta(SkullMeta.class, skullMeta -> {
            UUID uuid = UUID.randomUUID();
            PlayerProfile playerProfile = Bukkit.createProfile(uuid, uuid.toString().substring(0, 16));
            playerProfile.setProperty(new ProfileProperty("textures", b64));
            skullMeta.setPlayerProfile(playerProfile);
        });
        return head;
    }
}
