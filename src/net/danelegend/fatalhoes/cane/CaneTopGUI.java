package net.danelegend.fatalhoes.cane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import net.danelegend.fatalhoes.FatalHoes;
import net.danelegend.fatalhoes.util.Util;

public class CaneTopGUI {

	private FatalHoes plugin;
	private HashMap<UUID, Integer> totalCaneData;
	private Inventory inv;
	
	public CaneTopGUI(FatalHoes plugin) {
		this.plugin = plugin;
		
		totalCaneData = plugin.getCaneManager().getTotalCaneData();
		String title = plugin.getConfig().getString("harvester-hoe.cane-top-gui.title");
		
		List<Player> topNine = getTopNine();
		inv = Bukkit.createInventory(null, 9, Util.c(title));
		
		for (int i = 0; i < 8; i++) {
			inv.setItem(i+1, getHead(topNine.get(i)));
		}
	}
	
	public void openInventory(HumanEntity ent) {
		ent.openInventory(inv);
	}
	
	private List<Player> getTopNine() {
		List<Player> topNine = new ArrayList<Player>();
		
		for (int i = -1; i < 9; i++) {
			int caneTop = -1;
			Player p = null;
			
			for (Map.Entry<UUID, Integer> cane : totalCaneData.entrySet()) {
				if (cane.getValue() > caneTop) {
					if (!topNine.contains(Bukkit.getPlayer(cane.getKey()))) {
						p = Bukkit.getPlayer(cane.getKey());
						caneTop = cane.getValue();
					}
				}
			}
			
			topNine.add(p);
		}
		
		return topNine;
	}
	
	private ItemStack getHead(Player p) {
		ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta skull = (SkullMeta) is.getItemMeta();
		
		if (p == null) {
			skull.setOwner("FlyntCoal");
		} else {
			skull.setOwner(p.getName());
		}
		
		return is;
	}
	
}
