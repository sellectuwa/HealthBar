package me.sellect.healthbar;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public final class HealthBar extends JavaPlugin {

    private Scoreboard scoreboard;

    @Override
    public void onEnable() {
        // Metrics
        Metrics metrics = new Metrics(this, 9217);

        scoreboard = getServer().getScoreboardManager().getMainScoreboard();
        registerHealthBar();
    }

    public void registerHealthBar() {
        if (scoreboard.getObjective("health") != null) {
            scoreboard.getObjective("health").unregister();
        }
        Objective objective = scoreboard.registerNewObjective("health", "health");
        objective.setDisplayName(ChatColor.RED + "❤");
        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
    }
}
