package com.arknesia.plugin;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldInitEvent;

public class WorldLoadListener implements Listener {

    private final main plugin;

    public WorldLoadListener(main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        World world = event.getWorld();
        plugin.applyGameRules(world);
    }

    @EventHandler
    public void onWorldInit(WorldInitEvent event) {
        World world = event.getWorld();
        plugin.applyGameRules(world);
    }

}
