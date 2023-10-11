package me.blancocl.completoscore.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class SpeedCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando solo se puede usar en el juego.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("Usa: /velocidad <valor>");
            return true;
        }

        Player player = (Player) sender;

        try {
            float speed = Float.parseFloat(args[0]);
            if (speed < 0.1f || speed > 10.0f) {
                sender.sendMessage("La velocidad debe estar entre 0.1 y 10.0.");
                return true;
            }

            player.setWalkSpeed(speed);
            sender.sendMessage("Tu velocidad se ha ajustado a " + speed);
        } catch (NumberFormatException e) {
            sender.sendMessage("Por favor, ingresa un valor numérico válido.");
        }

        return true;
    }
}
