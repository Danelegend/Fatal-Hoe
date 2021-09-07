package net.danelegend.fatalhoes.token;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.danelegend.fatalhoes.FatalHoes;

public class TokenManager implements Listener{

	private FatalHoes plugin;
	
	private HashMap<UUID, Integer> tokens;
	private HashMap<UUID, Integer> totalTokens;
	
	public TokenManager(FatalHoes plugin) {
		this.plugin = plugin;
		
	}
	
	@EventHandler
	public void onNewPlayerJoin(PlayerJoinEvent e) {
		UUID playerUUID = e.getPlayer().getUniqueId();
		
		if (!tokens.containsKey(playerUUID)) {
			tokens.put(playerUUID, 0);
		}
		
		if (!totalTokens.containsKey(playerUUID)) {
			totalTokens.put(playerUUID, 0);
		}
	}
	
	public int getPlayerTotalTokens(Player p) {
		int token = 0;
		UUID playerUUID = p.getUniqueId();
		
		if (!totalTokens.containsKey(playerUUID)) {
			return 0;
		}
		
		token = totalTokens.get(playerUUID);
		
		return token;
	}
	
	public int getPlayerTokens(Player p) {
		int token = 0;
		UUID playerUUID = p.getUniqueId();
		
		if (!tokens.containsKey(playerUUID)) {
			return 0;
		}
		
		token = tokens.get(playerUUID);
		
		return token;
	}
	
	public void addPlayerTokens(Player p, int n) {
		if (!tokens.containsKey(p.getUniqueId())) {
			this.initialisePlayerTokens(p);
		}
		
		if (!totalTokens.containsKey(p.getUniqueId())) {
			this.initialisePlayerTotalTokens(p);
		}
		
		tokens.replace(p.getUniqueId(), getPlayerTokens(p) + n);
		totalTokens.replace(p.getUniqueId(), getPlayerTotalTokens(p) + n);
	}
	
	public void subtractPlayerTokens(Player p, int n) {
		if (!tokens.containsKey(p.getUniqueId())) {
			this.initialisePlayerTokens(p);
		}

		tokens.replace(p.getUniqueId(), getPlayerTokens(p) - n);
	}
	
	public void setPlayerTokens(Player p, int n) {
		if (!tokens.containsKey(p.getUniqueId())) {
			this.initialisePlayerTokens(p);
		}
		tokens.replace(p.getUniqueId(), n);
	}
	
	public void setPlayerTotalTokens(Player p, int n) {
		if (!totalTokens.containsKey(p.getUniqueId())) {
			this.initialisePlayerTotalTokens(p);
		}
		totalTokens.replace(p.getUniqueId(), n);
	}
	
	private void initialisePlayerTokens(Player p) {
		UUID playerUUID = p.getUniqueId();
		tokens.put(playerUUID, 0);
	}
	
	private void initialisePlayerTotalTokens(Player p) {
		UUID playerUUID = p.getUniqueId();
		totalTokens.put(playerUUID, 0);
	}
	
	private HashMap<UUID, Integer> loadTokenData() {
		return plugin.getDataFile().getTokens();
	}
	
	private HashMap<UUID, Integer> loadTotalTokenData() {
		return plugin.getDataFile().getTotalTokens();
	}
	
	public HashMap<UUID, Integer> getTokenData() {
		return tokens;
	}
	
	public HashMap<UUID, Integer> getTotalTokenData() {
		return totalTokens;
	}
	
}
