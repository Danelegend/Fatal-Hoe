package net.danelegend.fatalhoes.hoe.enchants;

import org.bukkit.ChatColor;

import net.danelegend.fatalhoes.FatalHoes;

public enum HoeEnchantTypes {
	AUTOSELL("&a&lAutoSell", FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.Autosell.Max-Level"), ChatColor.GREEN, FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.Autosell.Base-Token-Cost"), FatalHoes.getPlugin().getConfig().getDouble("harvester-hoe.Autosell.Price-Multiplier"), 1, "AutoSell"),
	AUTOSELLBOOSTER("&a&lAuto Sell Booster", FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.AutosellBooster.Max-Level"), ChatColor.GREEN, FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.AutosellBooster.Base-Token-Cost"), FatalHoes.getPlugin().getConfig().getDouble("harvester-hoe.AutosellBooster.Price-Multiplier"), 2, "Auto Sell Booster"),
	SHOCKWAVE("&c&lShockwave", FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.Shockwave.Max-Level"), ChatColor.RED, FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.Shockwave.Base-Token-Cost"), FatalHoes.getPlugin().getConfig().getDouble("harvester-hoe.Shockwave.Price-Multiplier"), 11, "Shockwave"),
	DROPBOOSTER("&b&lDrop Booster", FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.DropBooster.Max-Level"), ChatColor.AQUA, FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.DropBooster.Base-Token-Cost"), FatalHoes.getPlugin().getConfig().getDouble("harvester-hoe.DropBooster.Price-Multiplier"), 6, "Drop Booster"),
	TOKENBOOSTER("&d&lToken Booster", FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.TokenBooster.Max-Level"), ChatColor.LIGHT_PURPLE, FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.TokenBooster.Base-Token-Cost"), FatalHoes.getPlugin().getConfig().getDouble("harvester-hoe.TokenBooster.Price-Multiplier"), 15, "Token Booster"),
	SPAWNERLOOTING("&7&lSpawner Looting", FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.SpawnerLooting.Max-Level"), ChatColor.GRAY, FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.SpawnerLooting.Base-Token-Cost"), FatalHoes.getPlugin().getConfig().getDouble("harvester-hoe.SpawnerLooting.Price-Multiplier"), 3, "Spawner Looting"),
	KEYLOOTING("&6&lKey Looting", FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.KeyLooting.Max-Level"), ChatColor.GOLD, FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.KeyLooting.Base-Token-Cost"), FatalHoes.getPlugin().getConfig().getDouble("harvester-hoe.KeyLooting.Price-Multiplier"), 5, "Key Looting"),
	HASTE("&e&lHaste", FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.Haste.Max-Level"), ChatColor.YELLOW, FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.Haste.Base-Token-Cost"), FatalHoes.getPlugin().getConfig().getDouble("harvester-hoe.Haste.Price-Multiplier"), 7, "Haste");
	
	private String customName;
	private ChatColor color;
	private int max, basePrice;
	private double priceMultiplier;
	private int guiPosition;
	private String name;
	
	HoeEnchantTypes(final String customName, final int maxLevel, final ChatColor colour, final int basePrice,
			final double priceMultiplier, final int guiPos, final String name) {
		this.customName = customName;
		this.color = colour;
		this.max = maxLevel;
		this.basePrice = basePrice;
		this.priceMultiplier = priceMultiplier;
		this.guiPosition = guiPos;
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getCustomName() {
		return this.customName;
	}
	
	public ChatColor getColour() {
		return this.color;
	}
	
	public int getMaxLevel() {
		return this.max;
	}
	
	public int getBasePrice() {
		return this.basePrice;
	}
	
	public double getPriceMultiplier() {
		return this.priceMultiplier;
	}
	
	public int getGuiPos() {
		return this.guiPosition;
	}
}
