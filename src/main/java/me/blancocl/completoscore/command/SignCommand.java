package me.blancocl.completoscore.command;

import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

public final class SignCommand implements CommandExecutor {

    @Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (args.length != 2){
                player.sendMessage("Especifica una palabra");
                player.sendMessage("Como esto: /cartel 3 diamante");
            }else if(args.length == 2){
                //Put a sign at a players location
                player.getWorld().getBlockAt(player.getLocation()).setType(Material.CHERRY_SIGN);
                //Get an instance of the sign so you can edit it
                org.bukkit.block.Sign sign = (org.bukkit.block.Sign) player.getWorld().getBlockAt(player.getLocation()).getState();

                int line = Integer.parseInt(args[0]) - 1; //The line the player specified
                String word = args[1]; //Word the player specified
                sign.setLine(line, word); //Edit the sign
                //Update the state of the sign
                sign.update();
            }
            //Can be used to place a sign
            //player.getWorld().getBlockAt(player.getLocation()).setType(Material.SIGN);
            //Get the sign/block
            //Sign sign1 = (Sign) player.getWorld().getBlockAt(player.getLocation()).getState();
            //sign1.setLine(1, "Booty"); //Edit the signs second line
            // sign1.update(); //Updates the state of the sign
        }


        return true;
    }
}
