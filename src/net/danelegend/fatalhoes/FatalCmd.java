package net.danelegend.fatalhoes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.danelegend.fatalhoes.util.Util;

public class FatalCmd implements CommandExecutor {
	// Admin commands - reload command
	// Admin permissions: fatalhoes.admin
	
	private FatalHoes plugin;
	
	public FatalCmd(FatalHoes plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			return false;
		}

		if (!args[0].equalsIgnoreCase("reload")) {
			return false;
		}
		
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (!p.hasPermission("fatalhoes.admin")) {
				return false;
			}
			
			p.sendMessage(Util.c(Util.prefix() + "Reloading FatalHoes"));
		}
		
		System.out.println("[FatalHoes]: Reloading FatalHoes");
		
		plugin.saveDataFile();
		
		if (sender instanceof Player) {
			Player p = (Player) sender;
			p.sendMessage(Util.c(Util.prefix() + "Reload complete!"));
		}
		
		System.out.println("[FatalHoes]: Reload complete!");
		
		return true;
	}
	
}
