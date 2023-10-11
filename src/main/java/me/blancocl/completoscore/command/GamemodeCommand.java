package me.blancocl.completoscore.command;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public final class GamemodeCommand implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando solo se puede usar en el juego.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("Usa: /gm <0|1|2> (Survival|Creative|Adventure)");
            return true;
        }

        Player player = (Player) sender;
        GameMode newGameMode = null;

        if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival")) {
            newGameMode = GameMode.SURVIVAL;
        } else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative")) {
            newGameMode = GameMode.CREATIVE;
        } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure")) {
            newGameMode = GameMode.ADVENTURE;
        }

        if (newGameMode != null) {
            player.setGameMode(newGameMode);
            sender.sendMessage("Modo de juego cambiado a " + newGameMode);
        } else {
            sender.sendMessage("Modo de juego no válido. Usar 0, 1 o 2 (Survival, Creative, Adventure).");
        }

        return true;
    }
}
