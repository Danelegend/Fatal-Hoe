package net.danelegend.fatalhoes.util;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import net.danelegend.fatalhoes.FatalHoes;

public class DataFile {
	private FatalHoes plugin;
	private File file;
	
	private HashMap<UUID, Integer> rawCane;
	private HashMap<UUID, Integer> totalCane;
	
	private HashMap<UUID, Integer> tokens;
	private HashMap<UUID, Integer> totalTokens;
	
	private String splitter = " / ";
	
	public DataFile(FatalHoes plugin) {
		this.plugin = plugin;
		
		file = new File(plugin.getDataFolder(), "data.base");
		
		// Form: UUID / rawCane / totalCane / tokens / totalTokens
		
		rawCane = new HashMap<UUID, Integer>();
	}
	
}
