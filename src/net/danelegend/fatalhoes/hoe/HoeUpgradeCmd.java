package net.danelegend.fatalhoes.hoe;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.danelegend.fatalhoes.FatalHoes;
import net.danelegend.fatalhoes.guis.HoeStatsGUI;
import net.danelegend.fatalhoes.util.Util;

public class HoeUpgradeCmd implements CommandExecutor {

	private FatalHoes plugin;
	
	public HoeUpgradeCmd(FatalHoes plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			System.out.println("[FatalHoes]: This command is only executable by a player!");
			return false;
		}
		
		Player p = (Player) sender;
		ItemStack is = p.getItemInHand();
		
		// Check if they're holding a harvester hoe.
		if (is.getType().equals(Material.DIAMOND_HOE) && is.getItemMeta().getDisplayName().equals(Util.c(plugin.getConfig().getString("harvester-hoe.name")))) {
			HoeStatsGUI menu = new HoeStatsGUI(plugin, p);
			menu.openInventory(p);
		}
		
		return false;
	}
	
}
