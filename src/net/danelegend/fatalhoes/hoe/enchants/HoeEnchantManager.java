package net.danelegend.fatalhoes.hoe.enchants;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.danelegend.fatalhoes.FatalHoes;
import net.danelegend.fatalhoes.util.Util;

public class HoeEnchantManager {
	private FatalHoes plugin;
	
	public HoeEnchantManager(FatalHoes plugin) {
		this.plugin = plugin;
	}
	
	public int getEnchantLevel(ItemStack is, HoeEnchantTypes enchant) {
		int level = 0;
		
		ItemMeta im = is.getItemMeta();
		List<String> isl = im.getLore();
		int i = 0;
		boolean found = false;
		while (i < isl.size() && found == false) {
			if (isl.get(i).contains(enchant.getName())) {
				found = true;

				String replace = isl.get(i).replace(enchant.getName(), "");
				String numeral = "";
				
				for (int j = 0; j < replace.length(); j++) {
					if (replace.charAt(j) == 'I' || replace.charAt(j) == 'V' || replace.charAt(j) == 'X' || replace.charAt(j) == 'L'
							|| replace.charAt(j) == 'C') {
						numeral = numeral + replace.charAt(j);
					}
				}
				
				level = Util.romanToArabic(numeral);
			}
			i++;
		}
		
		return level;
	}
	
	public ItemStack addEnchant(ItemStack is, HoeEnchantTypes enchant) {
		ItemMeta im = is.getItemMeta();
		List<String> isl = im.getLore();
		
		int level = this.getEnchantLevel(is, enchant);
		
		if (level == 0) {
			isl.add(Util.c(enchant.getCustomName()) + " " + enchant.getColour() + ChatColor.BOLD + Util.arabicToRoman(1));
		} else if (level < enchant.getMaxLevel()) {
			int i = 0;
			boolean found = false;
			while (i < isl.size() && found == false) {
				if (isl.get(i).contains(enchant.getName())) {
					found = true;
					int newLevel = level + 1;
					isl.set(i, Util.c(enchant.getCustomName()) + " " + enchant.getColour() + ChatColor.BOLD + Util.arabicToRoman(newLevel));
				}
				i++;
			}
		}
		
		im.setLore(isl);
		is.setItemMeta(im);
		
		return is;
	}
	
	public ItemStack addEnchant(ItemStack is, HoeEnchantTypes enchant, int level) {
		ItemMeta im = is.getItemMeta();
		List<String> isl = im.getLore();
		
		isl.add(Util.c(enchant.getCustomName()) + " " + enchant.getColour() + ChatColor.BOLD + Util.arabicToRoman(level));
		
		im.setLore(isl);
		is.setItemMeta(im);
		
		return is;
	}
	
}
