package me.blancocl.completoscore.command;

import me.blancocl.completoscore.CompletosCore;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.blancocl.completoscore.util.Hex;

public class SetSpawnCommand implements CommandExecutor {
    private final CompletosCore spawnPlugin;

    public SetSpawnCommand(CompletosCore completosCore) {
        this.spawnPlugin = completosCore;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //Make sure that they are a player
        if (sender instanceof Player) {
            Player player = (Player) sender;

            player.hasPermission("CompletosCore.setspawn");
            //get the players location
            Location location = player.getLocation();

            //set the spawn location in the config.yml
//            spawnPlugin.getConfig().set("spawn.x", location.getX());
//            spawnPlugin.getConfig().set("spawn.y", location.getY());
//            spawnPlugin.getConfig().set("spawn.z", location.getZ());

            //A Location is a special type of object that can be saved to a config.yml automatically by bukkit
            //This is because it implements ConfigurationSerializable
            spawnPlugin.getConfig().set("spawn", location);

            //save the config.yml
            spawnPlugin.saveConfig();

            //send a message to the player
            player.sendMessage(Hex.message("#4287f5Has seteado el spawn principal."));

        } else {
            System.out.println(Hex.message("Bruh pon tu culo en el server y correlo, eso."));
        }

        return true;
    }
}