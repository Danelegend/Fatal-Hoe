package net.danelegend.fatalhoes;

import java.io.File;
import java.util.logging.Logger;

import net.danelegend.fatalhoes.captcha.CaptchaManager;
import net.danelegend.fatalhoes.guis.GUIListener;
import org.bukkit.Bukkit;
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
	
	private TokenManager   tokMan;
	private CaneManager    canMan;
	private HoeManager	   hoeMan;
	private CaptchaManager capMan;
	
	private boolean factionsEnabled;
	private boolean skyblockEnabled;

	private boolean silkSpawnersEnabled = true;
	
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

		if (!checkSilkSpawners()) {
			log.severe(String.format("[FatalHoes] SilkSpawners not found! Spawner drops will be disabled!"));
			silkSpawnersEnabled = false;
		}
		
		setCanePrice();
		
		data = new DataFile(this);
		
		tokMan = new TokenManager(this);
		canMan = new CaneManager(this);
		hoeMan = new HoeManager(this);
		capMan = new CaptchaManager(this);
		
		Bukkit.getPluginManager().registerEvents(tokMan, this);
		Bukkit.getPluginManager().registerEvents(canMan, this);

		Bukkit.getPluginManager().registerEvents(new GUIListener(this), this);
		
		Bukkit.getPluginCommand("fatal").setExecutor(new FatalCmd(this));
	}

	@Override
	public void onDisable() {
		System.out.println("[FatalHoes]: Disabling EraTools!");

		try {
			saveDataFile();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

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

	private boolean checkSilkSpawners() {
		if (getServer().getPluginManager().getPlugin("SilkSpawners") == null) {
			return false;
		}

		return true;
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
		return econ;
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

	public CaptchaManager getCapatchaManager() {
		return capMan;
	}

	public boolean isSilkSpawnersEnabled() { return this.silkSpawnersEnabled; }
	
}
