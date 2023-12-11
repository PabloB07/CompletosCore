package me.blancocl.completoscore.particles;

import me.blancocl.completoscore.CompletosCore;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public final class Particles implements Listener {

    private BukkitRunnable particleTask;
    public Particles() {
    }


    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location playerLocation = player.getLocation();
        if (player.isOnline()) {
            if (particleTask != null) {
                particleTask.cancel();
            }

            particleTask = new BukkitRunnable() {

                @Override
                public void run() {
                    Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(0, 127, 255), 1.0F);
                    player.spawnParticle(Particle.REDSTONE, player.getLocation(), 50, dustOptions);
                }
            };

            particleTask.runTaskTimerAsynchronously(CompletosCore.getInstance(), 0, 0);
        }
    }
}
