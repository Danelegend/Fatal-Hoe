package net.danelegend.fatalhoes.guis;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.danelegend.fatalhoes.FatalHoes;
import net.danelegend.fatalhoes.hoe.enchants.HoeEnchantManager;
import net.danelegend.fatalhoes.hoe.enchants.HoeEnchantTypes;
import net.danelegend.fatalhoes.util.Util;

public class HoeUpgradeGUI {

	private Inventory inv;
	private FatalHoes plugin;
	private HoeEnchantManager em;
	
	private static String title = "&c&lHoe &8&l» &c&lUpgrades";
	
	public HoeUpgradeGUI(FatalHoes plugin, ItemStack hoe) {
		this.plugin = plugin;
		em = plugin.getHoeManager().getEnchantManager();
		
		inv = Bukkit.createInventory(null, 18, Util.c(title));
		
		ItemStack filler = this.createFiller();
		
		ItemStack autosell = this.createEnchant(HoeEnchantTypes.AUTOSELL, hoe);
		ItemStack autosellBoost = this.createEnchant(HoeEnchantTypes.AUTOSELLBOOSTER, hoe);
		ItemStack spawnerDrop = this.createEnchant(HoeEnchantTypes.SPAWNERLOOTING, hoe);
		ItemStack keyDrop = this.createEnchant(HoeEnchantTypes.KEYLOOTING, hoe);
		ItemStack dropBooster = this.createEnchant(HoeEnchantTypes.DROPBOOSTER, hoe);
		ItemStack haste = this.createEnchant(HoeEnchantTypes.HASTE, hoe);
		ItemStack shockwave = this.createEnchant(HoeEnchantTypes.SHOCKWAVE, hoe);
		ItemStack tokenBooster = this.createEnchant(HoeEnchantTypes.TOKENBOOSTER, hoe);
		
		inv.setItem(HoeEnchantTypes.AUTOSELL.getGuiPos(), autosell);
		inv.setItem(HoeEnchantTypes.AUTOSELLBOOSTER.getGuiPos(), autosellBoost);
		inv.setItem(HoeEnchantTypes.SPAWNERLOOTING.getGuiPos(), spawnerDrop);
		inv.setItem(HoeEnchantTypes.KEYLOOTING.getGuiPos(), keyDrop);
		inv.setItem(HoeEnchantTypes.DROPBOOSTER.getGuiPos(), dropBooster);
		inv.setItem(HoeEnchantTypes.HASTE.getGuiPos(), haste);
		inv.setItem(HoeEnchantTypes.SHOCKWAVE.getGuiPos(), shockwave);
		inv.setItem(HoeEnchantTypes.TOKENBOOSTER.getGuiPos(), tokenBooster);
		
		for (int i = 0; i < 18; i++) {
			if (inv.getItem(i) == null || !inv.getItem(i).getType().equals(Material.ENCHANTED_BOOK)) {
				inv.setItem(i, filler);
			}
		}
	}
	
	public void openInv(HumanEntity e) {
		e.openInventory(inv);
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
	
	private ItemStack createEnchant(HoeEnchantTypes type, ItemStack hoe) {
		ItemStack is = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta im = is.getItemMeta();
		
		int progress = em.getEnchantLevel(hoe, type);
		int bar = 30;
		
		int colourProgress = ((progress*bar) / type.getMaxLevel());
		
		int tokenCost = (int) ((int) type.getBasePrice() + type.getBasePrice() * type.getPriceMultiplier() * progress);
		
		if (em.getEnchantLevel(hoe, type) >= type.getMaxLevel()) {
			im.setDisplayName(Util.c(type.getCustomName()) + type.getColour() + ChatColor.BOLD + " " + Util.arabicToRoman(progress));
		} else {
			im.setDisplayName(Util.c(type.getCustomName()) + type.getColour() + ChatColor.BOLD + " " + Util.arabicToRoman(progress + 1));
		}
		
		List<String> lore = new ArrayList<String>();
		
		String s = "&8[ ";
		
		for (int i = 0; i < bar; i++) {
			if (i < colourProgress) {
				s = s + "&a|";
			} else {
				s = s + "&8|";
			}
		}
		
		s = s + " &8]";
		
		lore.add(Util.c(s));
		lore.add("");
		lore.add(Util.c("&8[&a*&8] &fProgress&8: ") + ChatColor.GREEN + progress + ChatColor.DARK_GRAY + "/" 
				+ ChatColor.GREEN + type.getMaxLevel());
		lore.add(Util.c("&8[&a*&8] &fCost&8: ") + ChatColor.GREEN + tokenCost + ChatColor.WHITE + " Tokens");
		
		im.setLore(lore);
		is.setItemMeta(im);
		
		return is;
	}

	public static String getTitle() {
		return title;
	}
	
}
