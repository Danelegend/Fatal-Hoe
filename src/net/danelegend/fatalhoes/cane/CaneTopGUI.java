package net.danelegend.fatalhoes.cane;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.danelegend.fatalhoes.FatalHoes;
import net.danelegend.fatalhoes.util.Util;

import java.lang.reflect.Field;
import java.util.*;

public class CaneTopGUI {

	private FatalHoes plugin;
	private HashMap<UUID, Integer> totalCaneData;
	private HashMap<UUID, Integer> rawCaneData;
	private CaneManager canMan;
	private Inventory inv;
	
	public CaneTopGUI(FatalHoes plugin) {
		this.plugin = plugin;
		this.canMan = plugin.getCaneManager();

		totalCaneData = canMan.getTotalCaneData();
		rawCaneData = canMan.getRawCaneData();
		String title = plugin.getConfig().getString("harvester-hoe.cane-top-gui.title");
		
		List<Player> topNine = getTopNine();
		inv = Bukkit.createInventory(null, 27, Util.c(title));

		for (int i = 0; i < 27; i++) {
			inv.setItem(i, createFiller());
		}

		for (int i = 0; i < 9; i++) {
			switch (i) {
				case 0: inv.setItem(4, getHead(topNine.get(0), 1));
				case 1: inv.setItem(12, getHead(topNine.get(1), 2));
				case 2: inv.setItem(13, getHead(topNine.get(2), 3));
				case 3: inv.setItem(14, getHead(topNine.get(3), 4));
				case 4: inv.setItem(20, getHead(topNine.get(4), 5));
				case 5: inv.setItem(21, getHead(topNine.get(5), 6));
				case 6: inv.setItem(22, getHead(topNine.get(6), 7));
				case 7: inv.setItem(23, getHead(topNine.get(7), 8));
				case 8: inv.setItem(24, getHead(topNine.get(8), 9));
			}
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

	private ItemStack getHead(Player p, int place) {
		ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta skull = (SkullMeta) is.getItemMeta();
		
		if (p == null) {
			skull.setOwner("FlyntCoal");
			skull.setDisplayName(" ");
		} else {
			skull.setOwner(p.getName());

			String displayName = Util.c("&c[&f&l!&c] &c&l" + p.getDisplayName() + " &c(&f#" + place + "&c)");
			List<String> displayLore = new ArrayList<String>();

			displayLore.add(" ");
			displayLore.add(Util.c("&cTotal Cane: &f" + totalCaneData.get(p.getUniqueId())));
			displayLore.add(Util.c("&cRaw Cane: &f" + rawCaneData.get(p.getUniqueId())));

			skull.setDisplayName(displayName);
			skull.setLore(displayLore);
		}

		is.setItemMeta(skull);

		return is;
	}

	private ItemStack createFiller() {
		ItemStack is = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemMeta im = is.getItemMeta();

		im.setDisplayName(" ");

		im.addEnchant(Enchantment.DURABILITY, 1, true);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);

		im.setLore(null);

		is.setItemMeta(im);

		return is;
	}
}
