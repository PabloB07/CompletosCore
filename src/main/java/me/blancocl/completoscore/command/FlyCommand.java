package me.blancocl.completoscore.command;

import me.blancocl.completoscore.util.Hex;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlyCommand implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Solo jugadores pueden usar esto!");

            return true;
        }

        Player player = (Player) sender;

        if (player.getAllowFlight()) {
            player.setAllowFlight(false);
            player.sendMessage(Hex.message(Hex.prefix(" &cFly desactivado!")));

        } else {
            player.setAllowFlight(true);
            player.sendMessage(Hex.message(Hex.prefix(" &aFly activado!")));
        }

        return true;
    }
}
