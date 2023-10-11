package me.blancocl.completoscore.command;

import me.blancocl.completoscore.CompletosCore;
import me.blancocl.completoscore.util.Hex;
import net.md_5.bungee.api.ChatColor;
//import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public final class TagCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Hex.message(Hex.prefix("&6CC> ") + (("&cSolo jugadores pueden usarlo!"))));

            return true;
        }

        if (args.length != 1)
            return false;

        // /tag <X>
        String tag = args[0];
        Player player = (Player) sender;
        UUID uniqueId = player.getUniqueId();

        if ("reset".equals(tag)) {
            CompletosCore.getPlayerTags().remove(uniqueId);

            player.sendMessage(Hex.message(Hex.prefix("&6CC> ") + ("Tu tag ha sido borrada por defecto.")));

        } else {
            tag = ChatColor.translateAlternateColorCodes('&', Hex.message(tag));

            if (tag.length() > 16)
                tag = tag.substring(0, 16);

            CompletosCore.getPlayerTags().put(uniqueId, tag);
            player.sendMessage(Hex.message(Hex.prefix("&6CC> ") + ("Tu tag ahora es: " + tag + Hex.prefix(Hex.message(".")))));
        }

        //sender.sendMessage(Hex.prefix(Hex.message("Please uncomment TagCommand, code in ProtocolLib hook and pom.xml imports to make this command work.")));
        /*EntityPlayer handle = ((CraftPlayer) player).getHandle();

        for (Player online : Bukkit.getOnlinePlayers()) {
            PlayerConnection connection = ((CraftPlayer) online).getHandle().playerConnection;

            connection.sendPacket(new PacketPlayOutPlayerInfo(
                    PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, handle));

            connection.sendPacket(new PacketPlayOutPlayerInfo(
                    PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, handle));

            if (!online.equals(player)) {
                connection.sendPacket(new PacketPlayOutEntityDestroy(handle.getId()));
                connection.sendPacket(new PacketPlayOutNamedEntitySpawn(handle));
            }
        }
*/
        return true;
    }
}