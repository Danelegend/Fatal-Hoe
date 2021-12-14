package net.danelegend.fatalhoes.captcha;

import net.danelegend.fatalhoes.FatalHoes;
import org.apache.commons.lang.time.DateUtils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class CaptchaManager {

    private FatalHoes plugin;
    private HashMap<UUID, Integer> liveCaptchas;

    public CaptchaManager(FatalHoes plugin) {
        this.plugin = plugin;

        this.liveCaptchas = new HashMap<UUID, Integer>();
    }

    public int getCaptchaFails(Player p) {
        if (!liveCaptchas.containsKey(p.getUniqueId())) {
            return 0;
        }

        return liveCaptchas.get(p.getUniqueId());
    }

    public void addCaptchaFail(Player p) throws Exception {
        if (!liveCaptchas.containsKey(p.getUniqueId())) {
            liveCaptchas.put(p.getUniqueId(), 1);
        } else {
            if (getCaptchaFails(p) + 1 >= plugin.getConfig().getInt("captcha.max-fails")) {
                issueCaptchaPunishment(p);

                throw new Exception(p.getDisplayName() + " has been issued a punishment!");
            }
            liveCaptchas.replace(p.getUniqueId(), getCaptchaFails(p) + 1);
        }
    }

    public void removeFromCaptcha(Player p) {
        liveCaptchas.remove(p.getUniqueId());
    }

    public void raiseCaptcha(Player p) {
        CaptchaGUI cap = new CaptchaGUI(plugin, p);
    }

    private void issueCaptchaPunishment(Player p) {
        int time = plugin.getConfig().getInt("captcha.temp-ban");

        if (time == 0) {
            Bukkit.getPlayer(p.getUniqueId()).kickPlayer(plugin.getConfig().getString("captcha.kick-msg"));
        } else {
            Bukkit.getBanList(BanList.Type.NAME).addBan(p.getName(), plugin.getConfig().getString("captcha.ban-msg"),
                    DateUtils.addSeconds(new Date(), time), "Console");
            Bukkit.getPlayer(p.getUniqueId()).kickPlayer(plugin.getConfig().getString("captcha.kick-msg"));
        }

        removeFromCaptcha(p);
    }

}
