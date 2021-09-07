package net.danelegend.fatalhoes;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.danelegend.fatalhoes.cane.CaneManager;
import net.danelegend.fatalhoes.hoe.HoeManager;
import net.danelegend.fatalhoes.token.TokenManager;
import net.danelegend.fatalhoes.util.DataFile;
import net.milkbowl.vault.economy.Economy;

public class FatalHoes extends JavaPlugin {

	private static FatalHoes instance;
	private static final Logger log = Logger.getLogger("Minecraft");
	private Economy econ = null;
	
	private TokenManager tokMan;
	private CaneManager  canMan;
	private HoeManager	 hoeMan;
	
	private boolean factionsEnabled;
	private boolean skyblockEnabled;
	
	private int canePrice;
	
	private DataFile data;
	
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
		
		data = new DataFile(this);
		
		tokMan = new TokenManager(this);
		canMan = new CaneManager(this);
		hoeMan = new HoeManager(this);
		
		Bukkit.getPluginManager().registerEvents(tokMan, this);
		Bukkit.getPluginManager().registerEvents(canMan, this);
		
		
	}
	
	@Override
	public void onDisable() {
		System.out.println("[FatalHoes]: Disabling EraTools!");
		
		saveDataFile();
		
		System.out.println("[FatalHoes]: FatalHoes is disabled!");
	}
	
	public void saveDataFile() {
		System.out.println("[FatalHoes]: Saving Data");
		
		data.setRawCane(canMan.getRawCaneData());
		data.setTotalCane(canMan.getTotalCaneData());
		
		data.setTokens(tokMan.getTokenData());
		data.setTotalTokens(tokMan.getTotalTokenData());
		
		data.saveToFile();
		
		System.out.println("[FatalHoes]: All data saved!");
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
	
	public DataFile getDataFile() {
		return data;
	}
	
	public Economy getEco() {
		return this.econ;
	}
	
	public HoeManager getHoeManager() {
		return hoeMan;
	}
	
	public CaneManager getCaneManager() {
		return canMan;
	}
	
	public TokenManager getTokenManager() {
		return tokMan;
	}
	
}
