package me.blancocl.completoscore.listener;

import me.blancocl.completoscore.CompletosCore;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class SpawnListener implements Listener {

    private final CompletosCore plugin;

    public SpawnListener(CompletosCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        //When a player joins for the first time, teleport them to the spawn if it is set
        if (!e.getPlayer().hasPlayedBefore()) {
            Location location = plugin.getConfig().getLocation("spawn");
            if (location != null) {
                //spawn them
                e.getPlayer().teleport(location);
            }
        }
        Location location = plugin.getConfig().getLocation("spawn");
        if (location != null){
            if (e.getPlayer().isOnline())
                e.getPlayer().teleport(location); //spawn always when you join
        }

    }

    @EventHandler
    public void onPlayerDeath(PlayerRespawnEvent e) {
        //When the player dies, respawn them at the spawn location if set
        Location location = plugin.getConfig().getLocation("spawn");
        if (location != null) {
            //spawn them
            e.setRespawnLocation(location);
        }
    }
}