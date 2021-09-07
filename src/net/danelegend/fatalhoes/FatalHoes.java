package net.danelegend.fatalhoes;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class FatalHoes extends JavaPlugin {

	private static FatalHoes instance;
	private static final Logger log = Logger.getLogger("Minecraft");
	private Economy econ = null;
	
	
	
	private int canePrice;
	
	@Override
	public void onEnable() {
		System.out.println("[FatalHoes]: Enabling FatalHoes");
		
		instance = this;
		
		if (!new File(this.getDataFolder() + "config.yml").exists()) {
			saveDefaultConfig();
		}
		
		if (!setupEconomy()) {
			log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		setCanePrice();
		
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public Economy getEco() {
		return this.econ;
	}
	
	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		
		if (rsp == null) {
			return false;
		}
		
		econ = rsp.getProvider();
		
		return (econ != null);
	}
	
	public void setCanePrice() {
		this.canePrice = 0;
		this.canePrice = this.getConfig().getInt("harvester-hoe.cane-price");
	}
	
	public int getCanePrice() {
		return this.canePrice;
	}
	
	public static FatalHoes getPlugin() {
		return instance;
	}
	
}
