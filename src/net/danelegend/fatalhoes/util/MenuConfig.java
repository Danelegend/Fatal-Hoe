package net.danelegend.fatalhoes.util;

import net.danelegend.fatalhoes.FatalHoes;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MenuConfig {
    private static File file;
    private static FileConfiguration config;

    public static void setup() {
        file = new File(FatalHoes.getPlugin().getDataFolder(), "menus.yml");

        if (!file.exists()) {
            FatalHoes.getPlugin().saveResource("menus.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return config;
    }

    public static void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }

}
