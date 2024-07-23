package com.arknesia.plugin;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.regex.Pattern;

public final class main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        applyGameRules();

        // Register event for world load and world creation
        getServer().getPluginManager().registerEvents(new WorldLoadListener(this), this);
    }

    public void applyGameRules() {
        for (World world : Bukkit.getWorlds()) {
            applyGameRules(world);
        }
    }

    public void applyGameRules(World world) {
        FileConfiguration config = getConfig();
        String worldName = world.getName();

        if (config.contains("worlds")) {
            for (String pattern : config.getConfigurationSection("worlds").getKeys(false)) {
                if (pattern.equals(worldName) || Pattern.matches(pattern, worldName)) {
                    Map<String, Object> gameRules = config.getConfigurationSection("worlds." + pattern).getValues(false);
                    for (Map.Entry<String, Object> entry : gameRules.entrySet()) {
                        String ruleName = entry.getKey();
                        Object value = entry.getValue();

                        GameRule<?> gameRule = GameRule.getByName(ruleName);
                        if (gameRule != null) {
                            if (value instanceof Boolean) {
                                world.setGameRule((GameRule<Boolean>) gameRule, (Boolean) value);
                            } else if (value instanceof Integer) {
                                world.setGameRule((GameRule<Integer>) gameRule, (Integer) value);
                            } else if (value instanceof String) {
                                world.setGameRule((GameRule<String>) gameRule, (String) value);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
