package com.arknesia.plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.logging.Level;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        applyGameRules();

        // Register event for world load and world creation
        getServer().getPluginManager().registerEvents(new WorldLoadListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
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
            boolean rulesApplied = false;
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
                            rulesApplied = true;
                        }
                    }
                }
            }
            if (rulesApplied) {
                Bukkit.getConsoleSender().sendMessage("[ASPGamerule] Applied game rules to world: " + ChatColor.GREEN + worldName);
            } else {
                Bukkit.getConsoleSender().sendMessage("[ASPGamerule] No game rules found for world: " + ChatColor.RED + worldName);
            }
        } else {
            getLogger().log(Level.WARNING, "No worlds section in configuration.");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("aspg")) {
            if (args.length == 0) {
                sender.sendMessage("ASPGamerule-" + getDescription().getVersion() + " by Lexivale");
                return true;
            } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                reloadConfig();
                applyGameRules();
                sender.sendMessage("Plugin configuration reloaded and game rules applied.");
                return true;
            }
        }
        return false;
    }
}
