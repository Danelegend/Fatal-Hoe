package net.danelegend.fatalhoes.hoe;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.danelegend.fatalhoes.FatalHoes;
import net.danelegend.fatalhoes.hoe.enchants.HoeEnchantManager;
import net.danelegend.fatalhoes.hoe.enchants.HoeEnchantTypes;
import net.danelegend.fatalhoes.util.Util;

public class HoeCmd implements CommandExecutor {
	private FatalHoes plugin;
	private HoeManager hoeMan;
	private HoeEnchantManager enchMan;
	private Configuration config;
	
	public HoeCmd(FatalHoes plugin, HoeManager hoeMan) {
		this.plugin = plugin;
		this.hoeMan = hoeMan;
		this.enchMan = hoeMan.getEnchantManager();
		this.config = plugin.getConfig();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
		
		// Command Syntax = /harvester give player  {Max}
		
		int length = args.length;
		
		if (length == 0) {
			sender.sendMessage(Util.c("&cSyntax is /harvester give [player] [number] {max}"));
			return false;
		}
		
		if (!sender.hasPermission("eratools.give")) {
			return false;
		}
		
		
		if (length >= 2 && args[0].equalsIgnoreCase("give")) {
			ItemStack is = new ItemStack(Material.DIAMOND_HOE);
			ItemMeta im = is.getItemMeta();
			
			if (config.getBoolean("harvester-hoe.glow")) {
				im.addEnchant(Enchantment.DIG_SPEED, 5, true);
				im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			}
			
			im.setDisplayName(Util.c(config.getString("harvester-hoe.name")));
			
			List<String> isl = new ArrayList<String>();
			
			for (String s : config.getStringList("harvester-hoe.lore")) {
				isl.add(Util.c(s));
			}
			
			isl.add(" ");
			isl.add(Util.c("&c&lEnchantments:"));
			isl.add(" ");
			im.setLore(isl);
			is.setItemMeta(im);
			// Gives a max level hoe
			if (length >= 3 && args[2].equalsIgnoreCase("max")) {
				is = enchMan.addEnchant(is, HoeEnchantTypes.AUTOSELL, HoeEnchantTypes.AUTOSELL.getMaxLevel());
				is = enchMan.addEnchant(is, HoeEnchantTypes.AUTOSELLBOOSTER, HoeEnchantTypes.AUTOSELLBOOSTER.getMaxLevel());
				is = enchMan.addEnchant(is, HoeEnchantTypes.SHOCKWAVE, HoeEnchantTypes.SHOCKWAVE.getMaxLevel());
				is = enchMan.addEnchant(is, HoeEnchantTypes.DROPBOOSTER, HoeEnchantTypes.DROPBOOSTER.getMaxLevel());
				is = enchMan.addEnchant(is, HoeEnchantTypes.TOKENBOOSTER, HoeEnchantTypes.TOKENBOOSTER.getMaxLevel());
				is = enchMan.addEnchant(is, HoeEnchantTypes.KEYLOOTING, HoeEnchantTypes.KEYLOOTING.getMaxLevel());
				is = enchMan.addEnchant(is, HoeEnchantTypes.HASTE, HoeEnchantTypes.HASTE.getMaxLevel());
				
				if (config.getBoolean("harvester-hoe.spawner-drops.enabled")) {
					is = enchMan.addEnchant(is, HoeEnchantTypes.SPAWNERLOOTING, HoeEnchantTypes.SPAWNERLOOTING.getMaxLevel());
				}
				
			}
			
			is.setAmount(1);
			
			Player p = plugin.getServer().getPlayer(args[1]);
			p.getInventory().addItem(is);
		}
		
		return true;
	}

	
	
}
