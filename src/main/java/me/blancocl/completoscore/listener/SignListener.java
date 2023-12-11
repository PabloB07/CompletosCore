package me.blancocl.completoscore.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public final class SignListener implements Listener {

        @EventHandler
        public void onSignSomething(SignChangeEvent e){

            e.getBlock().setType(Material.RED_WOOL);
    }
}
