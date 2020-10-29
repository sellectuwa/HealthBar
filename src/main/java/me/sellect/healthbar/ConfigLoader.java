package me.sellect.healthbar;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class ConfigLoader {
    private final Plugin plugin;

    public ConfigLoader(Plugin plugin){
        this.plugin = plugin;
    }

    public void configSetup(){
        File configFile = new File("plugins/HealthBar/config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        plugin.getConfig().options().header("HealthBar\n" +
                "How to format text: https://pastebin.com/raw/4kjcQGrh");
        plugin.getConfig().addDefault("suffix", "&c❤");
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
    }
}
