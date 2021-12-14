package net.danelegend.fatalhoes.token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.danelegend.fatalhoes.FatalHoes;
import net.danelegend.fatalhoes.util.Util;

public class TokenCmd implements CommandExecutor {

	private FatalHoes plugin;
	private TokenManager tokMan;
	
	public TokenCmd(FatalHoes plugin, TokenManager tokMan) {
		this.plugin = plugin;
		this.tokMan = tokMan;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		int length = args.length;
		
		if (length == 0) {
			
			if (!(sender instanceof Player)) {
				return false;
			}
			
			Player p = (Player) sender;
			UUID playerUUID = p.getUniqueId();
			
			HashMap<UUID, Integer> tokens = tokMan.getTokenData();
			int playerTokens = tokens.get(playerUUID);
			
			String prefix = "&6&lTokens&f: ";
			
			p.sendMessage(Util.c(prefix) + ChatColor.WHITE + playerTokens);
			
			return true;
		}
		
		if (length == 1 && args[0].equalsIgnoreCase("top")) {
			Player p = (Player) sender;
			
			List<Player> topFive = new ArrayList<Player>();
			
			// Note: Dont add player if no such player exists
			for (int i = 5; i > 0; i--) {
				int caneTop = -1;
				Player biggest = null;
				
				for (Map.Entry<UUID, Integer> token : tokMan.getTokenData().entrySet()) {
					if (token.getValue() >= caneTop) {
						if (!topFive.contains(Bukkit.getPlayer(token.getKey()))) {
							biggest = Bukkit.getPlayer(token.getKey());
							caneTop = token.getValue();
						} 
					}
				}

				topFive.add(biggest);

			}

			// Send player message.
			p.sendMessage(Util.c("    &cToken Top Placements"));

			for (int i = 0; i < 5; i++) {
				if (topFive.get(i) != null) {
					p.sendMessage(ChatColor.WHITE + "#" + (i+1) + " " + topFive.get(i).getDisplayName() + " - " + tokMan.getPlayerTokens(topFive.get(i)) + " tokens");
				}
			}
			
			
			return true;
		}
		
		// Give and take player tokens
		// give {name} {amount}
		
		if (length == 3 && (args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("add"))) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (!player.hasPermission("eratools.admin")) {
					return false;
				}
			}

			UUID uuid = Bukkit.getPlayer(args[1]).getUniqueId();
			
			if (!tokMan.getTotalTokenData().containsKey(uuid)) {				
				tokMan.getTotalTokenData().put(uuid, 0);
			}

			if (!this.isNumeric(args[2])) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					player.sendMessage(Util.c("&cMust use form /tokens give {Player} {Amount}"));
				} else {
					System.out.println("Amount given not an integer");
				}
				return false;
			}
			
			tokMan.addPlayerTokens(Bukkit.getPlayer(uuid), Integer.parseInt(args[2]));
			
			if (sender instanceof Player) {
				Player player = (Player) sender;
				player.sendMessage(Util.c("&cTokens successfully added"));
			}
			
			return true;
		}
		
		if (length == 3 && (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("subtract"))) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (!player.hasPermission("eratools.admin")) {
					return false;
				}
			}

			UUID uuid = Bukkit.getPlayer(args[1]).getUniqueId();
			
			if (!tokMan.getTotalTokenData().containsKey(uuid)) {				
				tokMan.getTotalTokenData().put(uuid, 0);
			}

			if (!this.isNumeric(args[2])) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					player.sendMessage(Util.c("&cMust use form /tokens remove {Player} {Amount}"));
				} else {
					System.out.println("Amount given not an integer");
				}
				return false;
			}
			
			tokMan.subtractPlayerTokens(Bukkit.getPlayer(uuid), Integer.parseInt(args[2]));
			
			if (sender instanceof Player) {
				Player player = (Player) sender;
				player.sendMessage(Util.c("&cTokens successfully removed"));
			}
			
			return true;
		}
		
		if (length == 3 && args[0].equalsIgnoreCase("set")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (!player.hasPermission("eratools.admin")) {
					return false;
				}
			}

			UUID uuid = Bukkit.getPlayer(args[1]).getUniqueId();
			
			if (!tokMan.getTotalTokenData().containsKey(uuid)) {				
				tokMan.getTotalTokenData().put(uuid, 0);
			}

			if (!this.isNumeric(args[2])) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					player.sendMessage(Util.c("&cMust use form /tokens set {Player} {Amount}"));
				} else {
					System.out.println("Amount given not an integer");
				}
				return false;
			}
			
			tokMan.setPlayerTokens(Bukkit.getPlayer(uuid), Integer.parseInt(args[2]));
			
			if (sender instanceof Player) {
				Player player = (Player) sender;
				player.sendMessage(Util.c("&cTokens successfully set"));
			}
			
			return true;
		}
			
		
		return false;
	}
	
	private boolean isNumeric(String strnum) {
		if (strnum == null) {
			return false;
		}
		
		try {
			Integer.parseInt(strnum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		
		return true;
	}
	

}

