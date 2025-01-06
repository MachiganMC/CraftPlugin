package be.machigan.craftplugin.utils.version;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MinecraftVersion {
    private int majorVersion;
    private int minorVersion;

    public static MinecraftVersion fromString(String fullVersionString) throws IllegalArgumentException {
        String[] versions = fullVersionString.replaceFirst("1\\.", "").split("\\.");
        if (versions.length != 2)
            throw new IllegalArgumentException("Minecraft version not valid (" + fullVersionString + ") required 1.x.x");
        return new MinecraftVersion(
                Integer.parseInt(versions[0]),
                Integer.parseInt(versions[1])
        );
    }

    public boolean isRecentThan(MinecraftVersion versionToCompare) {
        if (this.majorVersion > versionToCompare.majorVersion) return true;
        if (this.majorVersion < versionToCompare.majorVersion) return false;
        return this.minorVersion > versionToCompare.minorVersion;
    }

    public boolean isRecentOrSameThan(MinecraftVersion versionToCompare) {
        return this.isRecentThan(versionToCompare) || this.equals(versionToCompare);
    }

    public boolean isOlderThan(MinecraftVersion versionToCompare) {
        return !this.isRecentOrSameThan(versionToCompare);
    }

    public boolean isOlderOrSameThan(MinecraftVersion versionToCompare) {
        return this.isOlderThan(versionToCompare) || this.equals(versionToCompare);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)return false;
        if (!(obj instanceof MinecraftVersion versionToCompare)) return false;
        return
                this.majorVersion == versionToCompare.majorVersion
                && this.minorVersion == versionToCompare.minorVersion;
    }

    @Override
    public String toString() {
        return "1." + this.majorVersion + "." + this.minorVersion;
    }
}
