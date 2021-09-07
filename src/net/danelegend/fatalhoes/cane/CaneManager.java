package net.danelegend.fatalhoes.cane;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.danelegend.fatalhoes.FatalHoes;

public class CaneManager implements Listener {

	private FatalHoes plugin;
	private HashMap<UUID, Integer> rawCane;
	private HashMap<UUID, Integer> totalCane;
	
	public CaneManager(FatalHoes plugin) {
		this.plugin = plugin;
		
		rawCane = loadRawCane();
		totalCane = loadTotalCane();
	}
	
	@EventHandler
	public void onNewPlayerJoin(PlayerJoinEvent e) {
		UUID playerUUID = e.getPlayer().getUniqueId();
		
		if (!rawCane.containsKey(playerUUID)) {
			rawCane.put(playerUUID, 0);
		}
		
		if (!totalCane.containsKey(playerUUID)) {
			totalCane.put(playerUUID, 0);
		}
	}
	
	private HashMap<UUID, Integer> loadRawCane() {
		return plugin.getDataFile().getRawCane();
	}
	
	private HashMap<UUID, Integer> loadTotalCane() {
		return plugin.getDataFile().getTotalCane();
	}
	
	public int getPlayerRawCane(Player p) {
		int cane = 0;
		UUID playerUUID = p.getUniqueId();
		
		cane = rawCane.get(playerUUID);
		
		return cane;
	}
	
	public int getPlayerTotalCane(Player p) {
		int cane = 0;
		UUID playerUUID = p.getUniqueId();
		
		cane = totalCane.get(playerUUID);
		
		return cane;
	}
	
	public void addPlayerRawCane(Player p, int n) {
		rawCane.replace(p.getUniqueId(), getPlayerRawCane(p) + n);
	}
	
	public void addPlayerTotalCane(Player p, int n) {
		totalCane.replace(p.getUniqueId(), getPlayerTotalCane(p) + n);
	}
	
	public void setPlayerRawCane(Player p, int n) {
		rawCane.replace(p.getUniqueId(), n);
	}
	
	public void setPlayerTotalCane(Player p, int n) {
		totalCane.replace(p.getUniqueId(), n);
	}
	
	public HashMap<UUID, Integer> getRawCaneData() {
		return rawCane;
	}
	
	public HashMap<UUID, Integer> getTotalCaneData() {
		return totalCane;
	}
	
}
