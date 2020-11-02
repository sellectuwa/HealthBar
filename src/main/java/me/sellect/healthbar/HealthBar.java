package me.sellect.healthbar;

import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Objects;

public final class HealthBar extends JavaPlugin {

    private Scoreboard scoreboard;

    @Override
    public void onEnable() {
        // UpdateChecker
        new UpdateChecker(this, 85218).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                getLogger().info("There is not a new update available.");
            }else{
                getLogger().info("There is a new update available.");
            }
        });

        // bStats Metrics
        new Metrics(this, 9217);

        // Config
        new ConfigLoader(this).configSetup();

        scoreboard = getServer().getScoreboardManager().getMainScoreboard();
        registerHealthBar();
    }

    public void registerHealthBar() {
        if (scoreboard.getObjective("health") != null) {
            scoreboard.getObjective("health").unregister();
        }
        Objective objective = scoreboard.registerNewObjective("health", "health");

        String suffix = ChatColor.RED + "❤";

        if(getConfig().getString("suffix") != null) {
            suffix = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("suffix")));
        }

        objective.setDisplayName(suffix);
        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
    }
}
