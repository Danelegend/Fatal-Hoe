package net.danelegend.fatalhoes.hoe;

import org.bukkit.Bukkit;

import net.danelegend.fatalhoes.FatalHoes;
import net.danelegend.fatalhoes.cane.CaneTopCmd;
import net.danelegend.fatalhoes.hoe.enchants.HoeEnchantManager;

public class HoeManager {

	private int canePrice;
	private FatalHoes plugin;
	private HoeEnchantManager enchMan;
	
	
	public HoeManager(FatalHoes plugin) {
		this.plugin = plugin;
		
		this.canePrice = plugin.getCanePrice();
		
		this.enchMan = new HoeEnchantManager(plugin);
		
		System.out.println("[FatalHoes]: Loading Commands");
		
		plugin.getCommand("harvester").setExecutor(new HoeCmd(plugin, this));
		plugin.getCommand("upgrade").setExecutor(new HoeUpgradeCmd(plugin));
		plugin.getCommand("cane").setExecutor(new CaneTopCmd(plugin));
		
		Bukkit.getPluginManager().registerEvents(plugin.getCaneManager(), plugin);
		Bukkit.getPluginManager().registerEvents(new HoeListeners(plugin), plugin);
		
		System.out.println("[FatalHoes]: Commands Loaded");
	}
	
	public int getCanePrice() {
		return canePrice;
	}
	
	public FatalHoes getPlugin() {
		return plugin;
	}
	
	public HoeEnchantManager getEnchantManager() {
		return enchMan;
	}
	
}
