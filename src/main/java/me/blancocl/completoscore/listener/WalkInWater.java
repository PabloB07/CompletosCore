package me.blancocl.completoscore.listener;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public final class WalkInWater implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE || event.getPlayer().getGameMode() == GameMode.SPECTATOR) {
            // Ignore players in Creative or Spectator mode
            return;
        }

        if (isWalkingOnWater(event.getPlayer())) {
            // Cancel the vertical fall damage to prevent damage when walking on water
            event.getPlayer().setFallDistance(0);
        }
    }

    private boolean isWalkingOnWater(org.bukkit.entity.Player player) {
        // Check if the player is walking on water
        if (player.getLocation().getBlock().getType() == Material.WATER) {
            // Additional checks based on your requirements can be added here
            return true;
        }

        return false;
    }
}
