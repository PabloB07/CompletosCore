package me.blancocl.completoscore.util;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

public final class Bungee implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] message) {
        System.out.println("Recibiendo mensaje de " + channel);

        if (!channel.equals("BungeeCord"))
            return;

        ByteArrayDataInput in = ByteStreams.newDataInput(message);

        String argument = in.readUTF();
        System.out.println("El argumento es: " + argument);

        if (argument.equals("IP")) {
            String ip = in.readUTF();
            int port = in.readInt();

            System.out.println("ip: " + ip + " puerto: " + port);
        }
    }
}