package me.blancocl.completoscore.command;

import me.blancocl.completoscore.CompletosCore;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.blancocl.completoscore.util.Hex;

public class SpawnCommand implements CommandExecutor {
    private final CompletosCore plugin;

    public SpawnCommand(CompletosCore plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //Make sure that they are a player
        if (sender instanceof Player){

            Player player = (Player) sender;

            player.hasPermission("CompletosCore.spawncommand");
            //see if the plugin has a spawn point set in the config
            Location location = plugin.getConfig().getLocation("spawn");
            if (location != null){

                //teleport the player to the spawn point
                player.teleport(location);

                //send a message to the player
                player.sendMessage(Hex.message("#4287f5Has sido teleportado al spawn principal."));

            }else{
                player.sendMessage(Hex.message("#f5427bNo hay spawn principal."));
            }

        }else{
            System.out.println("Bruh anda al server y pon la wea, eso.");
        }

        return true;
    }
}