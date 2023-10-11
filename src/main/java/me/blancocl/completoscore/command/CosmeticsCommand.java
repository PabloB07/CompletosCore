package me.blancocl.completoscore.command;

import me.blancocl.completoscore.command.inv.CosmeticsInvCommand;
import me.blancocl.completoscore.command.inv.HeadsInvCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CosmeticsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Solo jugadores pueden usar este comando!");

            return true;
        }

        Player player = (Player) sender;

        new CosmeticsInvCommand().open(player);
        return true;
    }
}