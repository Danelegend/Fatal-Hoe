package net.danelegend.fatalhoes.hoe;

import java.util.List;
import java.util.Map;
import java.util.Random;

import net.danelegend.fatalhoes.captcha.CaptchaGUI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.danelegend.fatalhoes.FatalHoes;
import net.danelegend.fatalhoes.cane.CaneManager;
import net.danelegend.fatalhoes.hoe.enchants.HoeEnchantTypes;
import net.danelegend.fatalhoes.token.TokenManager;
import net.danelegend.fatalhoes.util.Util;
import net.milkbowl.vault.economy.Economy;

public class HoeListeners implements Listener {

	private FatalHoes plugin;
	private Economy econ;
	
	public HoeListeners(FatalHoes plugin) {
		this.plugin = plugin;
		this.econ = plugin.getEco();
	}
	
	@EventHandler
	public void onCaneMine(BlockBreakEvent e) {
		if (!e.getBlock().getType().equals(Material.SUGAR_CANE_BLOCK)) {
			return;
		}

		Player p = e.getPlayer();
		ItemStack is = p.getItemInHand();
		ItemMeta im = is.getItemMeta();
		
		CaneManager caneMan = plugin.getCaneManager();
		TokenManager tokenMan = plugin.getTokenManager();

		this.captchaLogic(p);

		if (!is.getType().equals(Material.DIAMOND_HOE) || !im.getDisplayName().equals(Util.c(
				plugin.getConfig().getString("harvester-hoe.name")))) {

			caneMan.addPlayerRawCane(p, 1);
			caneMan.addPlayerTotalCane(p, 1);
			
			return;
		}

		e.setCancelled(true);
		
		Block og = e.getBlock();
		
		// Does the hoe have autosell
		
		boolean autosellActive = false;
		
		if (plugin.getHoeManager().getEnchantManager().getEnchantLevel(is, HoeEnchantTypes.AUTOSELL) == 1) {
			autosellActive = true;
		}
		
		int multiplier = 1;
		
		// Get the top cane of the lot
		
		Block b = og;
		
		while (getAboveBlock(b).getType().equals(Material.SUGAR_CANE_BLOCK)) {
			b = getAboveBlock(b);
		}
		
		Boolean checker = false;
		
		while (getBelowBlock(b).getType().equals(Material.SUGAR_CANE_BLOCK)) {
			b.setType(Material.AIR);
			
			int caneAmount = this.dropBoosterLogic(plugin.getHoeManager().getEnchantManager().getEnchantLevel(is, HoeEnchantTypes.DROPBOOSTER));
			
			if (!autosellActive) {
				p.getInventory().addItem(new ItemStack(Material.SUGAR_CANE, caneAmount));
			} else {
				double moneyMultiplier = this.sellBoosterLogic(plugin.getHoeManager().getEnchantManager().getEnchantLevel(p.getItemInHand(), HoeEnchantTypes.AUTOSELLBOOSTER));
				econ.depositPlayer(p, plugin.getCanePrice()*caneAmount*moneyMultiplier);
			}
			
			caneMan.addPlayerTotalCane(p, 1);
			checker = true;
			b = getBelowBlock(b);
		}
		
		if (plugin.getHoeManager().getEnchantManager().getEnchantLevel(is, HoeEnchantTypes.SHOCKWAVE) == 1) {
			this.shockwaveLogic(b, p, autosellActive);
		}
		
		if (checker) {
			this.keyDropLogic(plugin.getHoeManager().getEnchantManager().getEnchantLevel(is, HoeEnchantTypes.KEYLOOTING), p);
			this.spawnerDropLogic(plugin.getHoeManager().getEnchantManager().getEnchantLevel(is, HoeEnchantTypes.SPAWNERLOOTING), p);
			this.hasteLogic(plugin.getHoeManager().getEnchantManager().getEnchantLevel(is, HoeEnchantTypes.HASTE), p);
			tokenMan.addPlayerTokens(p, this.tokensToAdd(plugin.getHoeManager().getEnchantManager().getEnchantLevel(is, HoeEnchantTypes.TOKENBOOSTER)));
			caneMan.addPlayerRawCane(p, 1);
		}
	}

	private void captchaLogic(Player p) {
		if (!plugin.getConfig().getBoolean("captcha.enabled")) {
			return;
		}

		Random random = new Random();
		int upperBound = plugin.getConfig().getInt("captcha.occurence");

		if (random.nextInt(upperBound + 1) == upperBound) {
			new CaptchaGUI(plugin, p);
		}
	}

	private void shockwaveLogic(Block b, Player p, boolean autosellActive) {
		char direction = this.playerDirection(p);
		
		if (direction == 'N' || direction == 'S') {
			// Check +- x
			Block b1 = Bukkit.getWorld(b.getWorld().getName()).getBlockAt(b.getX() + 1, b.getY(), b.getZ());
			
			this.destroyCaneLogic(b1, p, autosellActive);
			
			b1 = Bukkit.getWorld(b.getWorld().getName()).getBlockAt(b.getX() - 1, b.getY(), b.getZ());
			
			this.destroyCaneLogic(b1, p, autosellActive);
			
		} else if (direction == 'E' || direction == 'W') {
			// Check +- z
			
			Block b1 = Bukkit.getWorld(b.getWorld().getName()).getBlockAt(b.getX(), b.getY(), b.getZ() + 1);
			
			this.destroyCaneLogic(b1, p, autosellActive);
			
			b1 = Bukkit.getWorld(b.getWorld().getName()).getBlockAt(b.getX(), b.getY(), b.getZ() - 1);
			
			this.destroyCaneLogic(b1, p, autosellActive);
		}
		
	}
	
	private void destroyCaneLogic(Block b, Player p, boolean autosellActive) {
		if (b.getType().equals(Material.SUGAR_CANE_BLOCK)) {
			
			while (this.getAboveBlock(b).getType().equals(Material.SUGAR_CANE_BLOCK)) {
				b = this.getAboveBlock(b);
			}
			
			while (this.getBelowBlock(b).getType().equals(Material.SUGAR_CANE_BLOCK)) {
				b.setType(Material.AIR);
				
				int caneAmount = this.dropBoosterLogic(plugin.getHoeManager().getEnchantManager().getEnchantLevel(p.getItemInHand(), HoeEnchantTypes.DROPBOOSTER));
				
				if (!autosellActive) {
					p.getInventory().addItem(new ItemStack(Material.SUGAR_CANE, caneAmount));
				} else {
					double moneyMultiplier = this.sellBoosterLogic(plugin.getHoeManager().getEnchantManager().getEnchantLevel(p.getItemInHand(), HoeEnchantTypes.AUTOSELLBOOSTER));
					econ.depositPlayer(p, plugin.getCanePrice()*caneAmount*moneyMultiplier);
				}
				
				plugin.getCaneManager().addPlayerTotalCane(p, 1);
				b = this.getBelowBlock(b);
			}
			
		}
	}
	
	private char playerDirection(Player p) {
		float yaw = p.getLocation().getYaw();
		
		if (yaw < 0) {
			yaw += 360;
		}
		
		if (yaw >= 315 || yaw < 45) {
			return 'S';
		} else if (yaw < 135) {
			return 'W';
		} else if (yaw < 225) {
			return 'N';
		} else {
			return 'E';
		}
		
	}
	
	private int tokensToAdd(int tokenEnchantLevel) {
		int levelsPerToken = plugin.getConfig().getInt("harvester-hoe.TokenBooster.Levels-Per-Token");

		int tokenNum = Math.floorDiv(tokenEnchantLevel, levelsPerToken);
		int range = tokenEnchantLevel % levelsPerToken;

		Random rand = new Random();

		if (rand.nextInt(levelsPerToken) < range) {
			tokenNum++;
		}

		return tokenNum;
	}
	
	private int dropBoosterLogic(int dropEnchantLevel) {
		if (dropEnchantLevel == 0) {
			return 1;
		} 
		
		int levelsPerCane = plugin.getConfig().getInt("harvester-hoe.DropBooster.Levels-Per-Cane");

		int cane = Math.floorDiv(dropEnchantLevel, levelsPerCane);
		int range = dropEnchantLevel % levelsPerCane;

		Random rand = new Random();

		if (rand.nextInt(levelsPerCane) < range) {
			cane++;
		}

		return cane;
	}
	
	private double sellBoosterLogic(int sellBoosterLevel) {
		if (sellBoosterLevel == 0) {
			return 1;
		}
		
		return 1 + sellBoosterLevel * plugin.getConfig().getInt("harvester-hoe.AutosellBooster.Sell-Boost-Multiple");
	}
	
	private void spawnerDropLogic(int spawnerDropLevel, Player p) {
		if (spawnerDropLevel == 0) {
			return;
		}

		if (!plugin.isSilkSpawnersEnabled()) {
			return;
		}

		int maxLevel = plugin.getConfig().getInt("harvester-hoe.SpawnerLooting.max-chance");

		
		Random rand = new Random();
		int probability = 1/plugin.getConfig().getInt("harvester-hoe.SpawnerLooting.max-chance");
		int randNum = rand.nextInt((int) probability * maxLevel / spawnerDropLevel);

		if (randNum == 1) {
			List<String> mobNames = plugin.getConfig().getStringList("harvester-hoe.SpawnerLooting.spawners");

			int spawnerNum = rand.nextInt(mobNames.size());

			String mobName = mobNames.get(spawnerNum);

			String cmd = "ss give " + p.getName() + " " + mobName + " 1";

			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
		}
	}
	
	private void keyDropLogic(int keyDropLevel, Player p) {
		if (keyDropLevel == 0) {
			return;
		}
		
		Random rand = new Random();

		int maxLevel = plugin.getConfig().getInt("harvester-hoe.KeyLooting.Max-Level");
		int probability = 1/plugin.getConfig().getInt("harvester-hoe.KeyLooting.max-chance");
		int randNum = rand.nextInt((int) probability * maxLevel / keyDropLevel);
		
		if (randNum == 1) {
			String cmd = plugin.getConfig().getString("harvester-hoe.KeyLooting.key-command");
			cmd = cmd.replaceAll("\\{player}", p.getName());
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
		}
		
		return;
	}
	
	private void hasteLogic(int hasteLevel, Player p) {
		double time1 = hasteLevel/10;
		int time = (int) Math.floor(time1);
		int maxLevel = HoeEnchantTypes.HASTE.getMaxLevel();
		int intermediate = (int) maxLevel/6;
		
		int ticks = time * 2;
		
		Random rand = new Random();
		
		if (hasteLevel >= 1 && this.randomNumFunction(rand, intermediate - hasteLevel) == 1) {
			PotionEffect potion = new PotionEffect(PotionEffectType.FAST_DIGGING, ticks, 1);
			p.addPotionEffect(potion);
		}
		
		if (hasteLevel > intermediate && this.randomNumFunction(rand, intermediate*2 - hasteLevel) == 1) {
			PotionEffect potion = new PotionEffect(PotionEffectType.SPEED, ticks, 1);
			p.addPotionEffect(potion);
		}
		
		if (hasteLevel > intermediate*2 && this.randomNumFunction(rand, intermediate*3 - hasteLevel) == 1) {
			PotionEffect potion = new PotionEffect(PotionEffectType.FAST_DIGGING, ticks, 2);
			p.addPotionEffect(potion);
		}
		
		if (hasteLevel > intermediate*3 && this.randomNumFunction(rand, intermediate*4 - hasteLevel) == 1) {
			PotionEffect potion = new PotionEffect(PotionEffectType.SPEED, ticks, 2);
			p.addPotionEffect(potion);
		}
		
		if (hasteLevel > intermediate*4 && this.randomNumFunction(rand, intermediate*5 - hasteLevel) == 1) {
			PotionEffect potion = new PotionEffect(PotionEffectType.FAST_DIGGING, ticks, 3);
			p.addPotionEffect(potion);
		}
		
		if (hasteLevel > intermediate*5 && this.randomNumFunction(rand, intermediate*6 - hasteLevel) == 1) {
			PotionEffect potion = new PotionEffect(PotionEffectType.SPEED, ticks, 3);
			p.addPotionEffect(potion);
		}
		
	}
	
	private int randomNumFunction(Random rand, int n) {
		if (n <= 0) {
			return 1;
		}
		
		return rand.nextInt(n);
	}
	
	private Block getAboveBlock(Block b) {
		Location l = new Location(b.getWorld(), b.getX(), b.getY() + 1, b.getZ());
		return l.getBlock();
	}
	
	private Block getBelowBlock(Block b) {
		Location l = new Location(b.getWorld(), b.getX(), b.getY() - 1, b.getZ());
		return l.getBlock();
	}
	
}
