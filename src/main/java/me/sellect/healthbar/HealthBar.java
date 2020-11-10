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
    private static final String HEALTH_OBJECTIVE_NAME = "health";

    @Override
    public void onEnable() {
        // UpdateChecker
        new UpdateChecker(this, 85218).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                getLogger().info("There is not a new update available.");
            } else {
                getLogger().info("There is a new update available.");
            }
        });

        // bStats Metrics
        new Metrics(this, 9217);

        // Config
        new ConfigLoader(this).configSetup();

        scoreboard = Objects.requireNonNull(getServer().getScoreboardManager()).getMainScoreboard();
        registerHealthBar();
    }

    @Override
    public void onDisable() {
        unregisterObjective(scoreboard, HEALTH_OBJECTIVE_NAME);
    }

    private void registerHealthBar() {
        unregisterObjective(scoreboard, HEALTH_OBJECTIVE_NAME);
        final Objective objective = scoreboard.registerNewObjective(HEALTH_OBJECTIVE_NAME, "health", "health");

        final String suffix = getTranslatedStringOrDefault("suffix", ChatColor.RED + "❤");
        objective.setDisplayName(suffix);
        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
    }


    /**
     * Unregisters an objective from the scoreboard, if it exists
     *
     * @param name Name of the Objective
     */
    private void unregisterObjective(Scoreboard scoreboard, String name) {
        final Objective objective = scoreboard.getObjective(name);
        if (objective != null) {
            objective.unregister();
        }
    }

    /**
     * Fetches a String value from the plugin config file and translates the '&' ColorCodes.
     * If no value or not a string value can be found at the specified path, the default value will be used
     * and also gets translated.
     *
     * @param path Path of the String in the config file
     * @param def  Non-Null Default Value if the String cannot be found or is not a String type
     * @return String from config or default, with '&' ColorCodes translated
     * @throws NullPointerException if both, the value from config and the default value are null
     */
    private String getTranslatedStringOrDefault(String path, String def) {
        final String rawString = getConfig().getString(path, def);
        Objects.requireNonNull(rawString, "No non-null value for config path '" + path + "' available. Default: '" + def + "'");
        return ChatColor.translateAlternateColorCodes('&', rawString);
    }

}
