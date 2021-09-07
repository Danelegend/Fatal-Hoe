package net.danelegend.fatalhoes.hoe;

import net.danelegend.fatalhoes.FatalHoes;
import net.danelegend.fatalhoes.hoe.enchants.HoeEnchantManager;

public class HoeManager {

	private int canePrice;
	private FatalHoes plugin;
	private HoeEnchantManager enchMan;
	
	
	public HoeManager(FatalHoes plugin) {
		this.plugin = plugin;
		
		this.canePrice = plugin.getCanePrice();
		
		this.enchMan = new HoeEnchantManager(plugin);
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
