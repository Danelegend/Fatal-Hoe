package net.danelegend.fatalhoes.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
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
		totalCane = new HashMap<UUID, Integer>();
		tokens = new HashMap<UUID, Integer>();
		totalTokens = new HashMap<UUID, Integer>();
		
		try {
			Scanner reader = new Scanner(file);
			
			while (reader.hasNextLine()) {
				String s = reader.nextLine();
				String[] parts = s.split(splitter);
				UUID uuid = UUID.fromString(parts[0]);
				
				int rC = Integer.parseInt(parts[1]);
				int tC = Integer.parseInt(parts[2]);
				int tok = Integer.parseInt(parts[3]);
				int totTok = Integer.parseInt(parts[4]);
				
				rawCane.put(uuid, rC);
				totalCane.put(uuid, tC);
				tokens.put(uuid, tok);
				totalTokens.put(uuid, totTok);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private File createFile() {
		try {
			File file = new File(plugin.getDataFolder(), "data.base");
			file.createNewFile();
			
			return file;
		} catch (IOException e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	public void saveToFile() {
		if (file.exists()) {
			file.delete();
		}
		
		file = this.createFile();
		
		for (UUID key : rawCane.keySet()) {
			int rawCane = this.rawCane.get(key);
			int totalCane = this.totalCane.get(key);
			
			int tokens = this.tokens.get(key);
			int totalTokens = this.totalTokens.get(key);
			
			saveSingleToFile(key, rawCane, totalCane, tokens, totalTokens);
		}
	}
	
	private void saveSingleToFile(UUID uuid, int rawCane, int totalCane, int tokens, int totalTokens) {
		String s = uuid.toString() + splitter + Integer.toString(rawCane) + splitter + Integer.toString(totalCane)
		+ splitter + Integer.toString(tokens) + splitter + Integer.toString(totalTokens);
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			writer.append(s + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setRawCane(HashMap<UUID, Integer> rawCane) {
		this.rawCane = rawCane;
	}
	
	public void setTotalCane(HashMap<UUID, Integer> totalCane) {
		this.totalCane = totalCane;
	}
	
	public void setTokens(HashMap<UUID, Integer> tokens) {
		this.tokens = tokens;
	}
	
	public void setTotalTokens(HashMap<UUID, Integer> totalTokens) {
		this.totalTokens = totalTokens;
	}
	
	public HashMap<UUID, Integer> getRawCane() {
		return rawCane;
	}
	
	public HashMap<UUID, Integer> getTotalCane() {
		return totalCane;
	}
	
	public HashMap<UUID, Integer> getTokens() {
		return tokens;
	}
	
	public HashMap<UUID, Integer> getTotalTokens() {
		return totalTokens;
	}
	
}
