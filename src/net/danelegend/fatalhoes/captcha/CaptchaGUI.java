package net.danelegend.fatalhoes.captcha;

import net.danelegend.fatalhoes.FatalHoes;
import net.danelegend.fatalhoes.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Random;

public class CaptchaGUI implements Listener {
    private final Inventory inv;
    private final CaptchaItemData item;

    private FatalHoes plugin;
    private Player player;
    private CaptchaManager capMan;
    private String title;

    private int pos;
    private boolean end;

    public CaptchaGUI(FatalHoes plugin, Player p) {
        this.plugin = plugin;
        this.player = p;
        this.end = false;

        this.capMan = plugin.getCapatchaManager();

        this.item = CaptchaItemData.getRandomItem();
        this.title = "&8Find the &c" + item.getDisplayName();
        this.inv = Bukkit.createInventory(null, 45, Util.c(this.title));

        this.pos = generateInvItems();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        player.openInventory(inv);
    }

    private int generateInvItems() {
        for (int i = 0; i < 45; i++) {
            CaptchaItemData item = CaptchaItemData.getRandomItem();
            while (item.equals(this.item)) {
                item = CaptchaItemData.getRandomItem();
            }

            inv.setItem(i, new ItemStack(item.getMaterial()));
        }

        Random rand = new Random();
        int pos = rand.nextInt(45);

        inv.setItem(pos, new ItemStack(this.item.getMaterial()));

        return pos;
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!Arrays.equals(inv.getTitle().toCharArray(), e.getInventory().getTitle().toCharArray())) {
            return;
        }

        e.setCancelled(true);

        end = true;

        if (e.getSlot() == pos) {
            player.closeInventory();

            capMan.removeFromCaptcha(player);
        } else {
            player.closeInventory();

            try {
                capMan.addCaptchaFail(player);
                new CaptchaGUI(plugin, player);
            } catch (Exception ex) {
                ;
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (!Arrays.equals(inv.getTitle().toCharArray(), e.getInventory().getTitle().toCharArray()) || end) {
            return;
        }

        e.getPlayer().openInventory(inv);
    }
}
