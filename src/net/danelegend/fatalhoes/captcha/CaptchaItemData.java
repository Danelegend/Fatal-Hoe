package net.danelegend.fatalhoes.captcha;

import org.bukkit.Material;

import java.util.Random;

public enum CaptchaItemData {
    ACTIVATOR_RAIL("ACTIVATOR_RAIL", Material.ACTIVATOR_RAIL),
    ANVIL("Anvil", Material.ANVIL),
    APPLE("Apple", Material.APPLE),
    BAKED_POTATO("Baked Potato", Material.BAKED_POTATO),
    BEACON("Beacon", Material.BEACON),
    BEDROCK("Bedrock", Material.BEDROCK),
    BLAZE_POWDER("Blaze Powder", Material.BLAZE_POWDER),
    BLAZE_ROD("Blaze Rod", Material.BLAZE_ROD),
    BONE("Bone", Material.BONE),
    BOOK("Book", Material.BOOK),
    BOOK_AND_QUILL("Book and Quill", Material.BOOK_AND_QUILL);

    private String displayName;
    private Material material;

    CaptchaItemData(final String displayName, final Material material) {
        this.displayName = displayName;
        this.material = material;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public Material getMaterial() {
        return this.material;
    }

    public static CaptchaItemData getRandomItem() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }

}
