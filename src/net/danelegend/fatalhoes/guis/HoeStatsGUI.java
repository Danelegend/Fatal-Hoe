package net.danelegend.fatalhoes.guis;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.danelegend.fatalhoes.FatalHoes;
import net.danelegend.fatalhoes.cane.CaneManager;
import net.danelegend.fatalhoes.token.TokenManager;
import net.danelegend.fatalhoes.util.Util;

public class HoeStatsGUI implements Listener {

	private final Inventory inv;
	private FatalHoes plugin;
	private CaneManager caneMan;
	private TokenManager tokenMan;
	
	private static String title = "&c&lHoe Stats";
	
	public HoeStatsGUI(FatalHoes plugin, Player p) {
		this.plugin = plugin;
		caneMan = this.plugin.getCaneManager();
		tokenMan = this.plugin.getTokenManager();
		inv = Bukkit.createInventory(null, 9, Util.c(title));
		
		this.initializeItems(p);
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	public void openInventory(final HumanEntity ent) {
		ent.openInventory(inv);
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
	
	private ItemStack createEnchantMenu() {
		ItemStack is = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta im = is.getItemMeta();
		
		im.setDisplayName(Util.c("&c&lUpgrades"));
		
		
		is.setItemMeta(im);
		
		return is;
	}
	
	private ItemStack createStats(Player p) {
		ItemStack is = new ItemStack(Material.COMPASS);
		ItemMeta im = is.getItemMeta();
		
		String title = "&c&lInformation";
		
		im.setDisplayName(Util.c(title));
		
		im.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		
		List<String> lore = new ArrayList<String>();
		lore.add(" ");
		lore.add(Util.c("&8&l[&a&l!&8&l] &fRaw Cane Collected: ") + ChatColor.GREEN + caneMan.getPlayerRawCane(p));
		lore.add(Util.c("&8&l[&a&l!&8&l] &fTotal Cane Collected: ") + ChatColor.GREEN + caneMan.getPlayerTotalCane(p));
		lore.add(" ");
		lore.add(Util.c("&8&l[&c&l!&8&l] &fCurrent Tokens: ") + ChatColor.RED + tokenMan.getPlayerTokens(p));
		lore.add(Util.c("&8&l[&c&l!&8&l] &fTotal Tokens Collected: ") + ChatColor.RED + tokenMan.getPlayerTotalTokens(p));
		
		im.setLore(lore);
		
		is.setItemMeta(im);
		
		return is;
	}
	
	private void initializeItems(Player p) {
		inv.setItem(2, this.createStats(p));
		inv.setItem(6, this.createEnchantMenu());
		
		for (int i = 0; i < 9; i++) {
			if (i != 2 && i != 6) {
				inv.setItem(i, this.createFiller());
			}
		}
	}
	
	public static String getTitle() {
		return title;
	}
	
}
