package net.danelegend.fatalhoes.cane;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.danelegend.fatalhoes.FatalHoes;

public class CaneTopCmd implements CommandExecutor {

	private FatalHoes plugin;
	
	public CaneTopCmd(FatalHoes plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		
		if (args[0].equalsIgnoreCase("top")) {
			Player p = (Player) sender;
			
			CaneTopGUI inv = new CaneTopGUI(plugin);
			
			inv.openInventory(p);
			
			return false;
		}
		
		return false;
	}
	
}
