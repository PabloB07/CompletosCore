package me.blancocl.completoscore.listener;

import me.blancocl.completoscore.util.Vector3;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;

public final class JumpPlates implements CommandExecutor, Listener {
    private Vector3 jumpForce = new Vector3(0, 1, 0); // Puedes ajustar la fuerza de salto según tus necesidades
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando solo se puede usar en el juego.");
            return true;
        }

        Player player = (Player) sender;
        Location location = player.getLocation().getBlock().getLocation();

        // Coloca una placa de salto en la posición actual del jugador
        Block jumpPlate = location.getBlock();
        jumpPlate.setType(Material.STONE_PRESSURE_PLATE);
        sender.sendMessage("¡Placa de salto creada!");

        return true;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        Block block = location.getBlock();

        if (block.getType() == Material.STONE_PRESSURE_PLATE) {
            player.setVelocity(new org.bukkit.util.Vector(jumpForce.getX(), jumpForce.getY(), jumpForce.getZ()));
        }
    }
}
