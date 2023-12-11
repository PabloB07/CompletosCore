package me.blancocl.completoscore.listener;

import me.blancocl.completoscore.CompletosCore;
import me.blancocl.completoscore.util.Keys;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public final class PlayerListener implements @NotNull Listener {
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Item item = event.getItemDrop();

        new BukkitRunnable() {
            @Override
            public void run() {

                if (item.isDead() || !item.isValid()) {
                    this.cancel();

                    return;
                }

                if (item.isOnGround()) {
                    final Location location = item.getLocation().add(0.5, -0.8, -0.5);

                    location.setPitch(0);
                    location.setYaw(0);

                    final ArmorStand armorstand = location.getWorld().spawn(location, ArmorStand.class);

                    armorstand.setVisible(false);
                    armorstand.setGravity(false);
                    armorstand.setArms(true);
                    armorstand.setItemInHand(item.getItemStack());

                    armorstand.getPersistentDataContainer()
                            .set(Keys.ITEM_DROP_TIME, PersistentDataType.LONG, System.currentTimeMillis());

                    item.remove();
                    this.cancel();
                }
            }
        }.runTaskTimer(CompletosCore.getInstance(), 0, 2); // 20 ticks = 1 second
    }
}
