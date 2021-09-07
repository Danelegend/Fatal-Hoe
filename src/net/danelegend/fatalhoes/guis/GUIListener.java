package net.danelegend.fatalhoes.guis;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.danelegend.fatalhoes.FatalHoes;
import net.danelegend.fatalhoes.hoe.enchants.HoeEnchantManager;
import net.danelegend.fatalhoes.hoe.enchants.HoeEnchantTypes;
import net.danelegend.fatalhoes.util.Util;

public class GUIListener {

	private FatalHoes plugin;
	private HoeEnchantManager em;
	
	public GUIListener(FatalHoes plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Inventory inv = e.getInventory();
		String title = inv.getTitle();
		
		if (title.equals(Util.c(HoeUpgradeGUI.getTitle()))) {
			this.upgradeGUILogic(e);
		
			return;
		}
		
		if (title.equals(Util.c(HoeStatsGUI.getTitle()))) {
			this.statsGUILogic(e);
			
			return;
		}
		
		if (title.equals(Util.c(plugin.getConfig().getString("harvester-hoe.cane-top-gui.title")))) {
			
			
			return;
		}
		
		
		return;
	}
	
	private void upgradeGUILogic(InventoryClickEvent e) {
		e.setCancelled(true);
		
		em = plugin.getHoeManager().getEnchantManager();
		
		Player p = (Player) e.getWhoClicked();
		ItemStack is = p.getItemInHand();
		
		if (e.getSlot() == HoeEnchantTypes.AUTOSELL.getGuiPos()) {
			this.canBuyEnchant(HoeEnchantTypes.AUTOSELL, p, is);
			
			p.closeInventory();
			
			HoeUpgradeGUI gui = new HoeUpgradeGUI(plugin, p.getItemInHand());
			gui.openInv(p);
			
			return;
		}
		
		if (e.getSlot() == HoeEnchantTypes.AUTOSELLBOOSTER.getGuiPos()) {
			this.canBuyEnchant(HoeEnchantTypes.AUTOSELLBOOSTER, p, is);
			
			p.closeInventory();
			
			HoeUpgradeGUI gui = new HoeUpgradeGUI(plugin, p.getItemInHand());
			gui.openInv(p);
			
			return;
		}
		
		if (e.getSlot() == HoeEnchantTypes.SHOCKWAVE.getGuiPos()) {
			this.canBuyEnchant(HoeEnchantTypes.SHOCKWAVE, p, is);
			p.closeInventory();
			
			HoeUpgradeGUI gui = new HoeUpgradeGUI(plugin, p.getItemInHand());
			gui.openInv(p);
			
			return;
		}
		
		if (e.getSlot() == HoeEnchantTypes.DROPBOOSTER.getGuiPos()) {
			this.canBuyEnchant(HoeEnchantTypes.DROPBOOSTER, p, is);
			
			p.closeInventory();
			
			HoeUpgradeGUI gui = new HoeUpgradeGUI(plugin, p.getItemInHand());
			gui.openInv(p);
			
			return;
		}
		
		if (e.getSlot() == HoeEnchantTypes.TOKENBOOSTER.getGuiPos()) {
			this.canBuyEnchant(HoeEnchantTypes.TOKENBOOSTER, p, is);
			
			p.closeInventory();
			
			HoeUpgradeGUI gui = new HoeUpgradeGUI(plugin, p.getItemInHand());
			gui.openInv(p);
			
			return;
		}
		
		if (e.getSlot() == HoeEnchantTypes.SPAWNERLOOTING.getGuiPos()) {
			this.canBuyEnchant(HoeEnchantTypes.SPAWNERLOOTING, p, is);
			
			p.closeInventory();
			
			HoeUpgradeGUI gui = new HoeUpgradeGUI(plugin, p.getItemInHand());
			gui.openInv(p);
			
			return;
		}
		
		if (e.getSlot() == HoeEnchantTypes.KEYLOOTING.getGuiPos()) {
			this.canBuyEnchant(HoeEnchantTypes.KEYLOOTING, p, is);
			
			p.closeInventory();
			
			HoeUpgradeGUI gui = new HoeUpgradeGUI(plugin, p.getItemInHand());
			gui.openInv(p);
			
			return;
		}
		
		if (e.getSlot() == HoeEnchantTypes.HASTE.getGuiPos()) {
			this.canBuyEnchant(HoeEnchantTypes.HASTE, p, is);
			
			p.closeInventory();
			
			HoeUpgradeGUI gui = new HoeUpgradeGUI(plugin, p.getItemInHand());
			gui.openInv(p);
			
			return;
		}
		
		return;
	}
	
	private void canBuyEnchant(HoeEnchantTypes type, Player p, ItemStack is) {
		int playerTokens = plugin.getTokenManager().getPlayerTokens(p);
		int level = em.getEnchantLevel(is, type);
		
		if (level >= type.getMaxLevel()) {
			p.sendMessage(Util.c("&cThis enchant is already at max level!"));
			return;
		}
		
		int tokenCost = (int) ((int) type.getBasePrice() + type.getBasePrice() * type.getPriceMultiplier() * level);
		
		if (playerTokens >= tokenCost) {
			em.addEnchant(is, type);
			plugin.getTokenManager().subtractPlayerTokens(p, tokenCost);
			p.sendMessage(Util.c("&cSuccessfully added " + type.getCustomName() + " at level " + (level + 1)));
		} else {
			p.sendMessage(Util.c("&cFailed to add " + type.getCustomName() + " at level " + (level + 1)));
		}
		
	}
	
	private void statsGUILogic(InventoryClickEvent e) {
		e.setCancelled(true);
		
		if (e.getSlot() != 6) {
			return;
		}
		
		Player p = (Player) e.getWhoClicked();
		HoeUpgradeGUI gui = new HoeUpgradeGUI(plugin, p.getItemInHand());
		
		gui.openInv(p);
	}
	
}
