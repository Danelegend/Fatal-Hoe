package net.danelegend.fatalhoes.hoe.enchants;

import net.danelegend.fatalhoes.util.MenuConfig;
import org.bukkit.ChatColor;

import net.danelegend.fatalhoes.FatalHoes;

public enum HoeEnchantTypes {
	AUTOSELL(FatalHoes.getPlugin().getConfig().getString("harvester-hoe.Autosell.Display-Name"), FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.Autosell.Max-Level"), ChatColor.GREEN, FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.Autosell.Base-Token-Cost"), FatalHoes.getPlugin().getConfig().getDouble("harvester-hoe.Autosell.Price-Multiplier"), MenuConfig.get().getInt("Upgrade-Menu.upgrades.autosell.slot"), "AutoSell"),
	AUTOSELLBOOSTER(FatalHoes.getPlugin().getConfig().getString("harvester-hoe.AutosellBooster.Display-Name"), FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.AutosellBooster.Max-Level"), ChatColor.GREEN, FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.AutosellBooster.Base-Token-Cost"), FatalHoes.getPlugin().getConfig().getDouble("harvester-hoe.AutosellBooster.Price-Multiplier"), MenuConfig.get().getInt("Upgrade-Menu.upgrades.autosell-booster.slot"), "Auto Sell Booster"),
	SHOCKWAVE(FatalHoes.getPlugin().getConfig().getString("harvester-hoe.Shockwave.Display-Name"), FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.Shockwave.Max-Level"), ChatColor.RED, FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.Shockwave.Base-Token-Cost"), FatalHoes.getPlugin().getConfig().getDouble("harvester-hoe.Shockwave.Price-Multiplier"), MenuConfig.get().getInt("Upgrade-Menu.upgrades.shockwave.slot"), "Shockwave"),
	DROPBOOSTER(FatalHoes.getPlugin().getConfig().getString("harvester-hoe.DropBooster.Display-Name"), FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.DropBooster.Max-Level"), ChatColor.AQUA, FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.DropBooster.Base-Token-Cost"), FatalHoes.getPlugin().getConfig().getDouble("harvester-hoe.DropBooster.Price-Multiplier"), MenuConfig.get().getInt("Upgrade-Menu.upgrades.drop-booster.slot"), "Drop Booster"),
	TOKENBOOSTER(FatalHoes.getPlugin().getConfig().getString("harvester-hoe.TokenBooster.Display-Name"), FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.TokenBooster.Max-Level"), ChatColor.LIGHT_PURPLE, FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.TokenBooster.Base-Token-Cost"), FatalHoes.getPlugin().getConfig().getDouble("harvester-hoe.TokenBooster.Price-Multiplier"), MenuConfig.get().getInt("Upgrade-Menu.upgrades.token-booster.slot"), "Token Booster"),
	SPAWNERLOOTING(FatalHoes.getPlugin().getConfig().getString("harvester-hoe.SpawnerLooting.Display-Name"), FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.SpawnerLooting.Max-Level"), ChatColor.GRAY, FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.SpawnerLooting.Base-Token-Cost"), FatalHoes.getPlugin().getConfig().getDouble("harvester-hoe.SpawnerLooting.Price-Multiplier"), MenuConfig.get().getInt("Upgrade-Menu.upgrades.spawner-looting.slot"), "Spawner Looting"),
	KEYLOOTING(FatalHoes.getPlugin().getConfig().getString("harvester-hoe.KeyLooting.Display-Name"), FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.KeyLooting.Max-Level"), ChatColor.GOLD, FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.KeyLooting.Base-Token-Cost"), FatalHoes.getPlugin().getConfig().getDouble("harvester-hoe.KeyLooting.Price-Multiplier"), MenuConfig.get().getInt("Upgrade-Menu.upgrades.key-looting.slot"), "Key Looting"),
	HASTE(FatalHoes.getPlugin().getConfig().getString("harvester-hoe.Haste.Display-Name"), FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.Haste.Max-Level"), ChatColor.YELLOW, FatalHoes.getPlugin().getConfig().getInt("harvester-hoe.Haste.Base-Token-Cost"), FatalHoes.getPlugin().getConfig().getDouble("harvester-hoe.Haste.Price-Multiplier"), MenuConfig.get().getInt("Upgrade-Menu.upgrades.haste.slot"), "Haste");
	
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
