package de.sinqularo.bungee.listener.player;

import de.sinqularo.bungee.BungeeCordSystem;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerSwitchServerListener implements Listener {

    @EventHandler
    public void onServerSwitch(ServerSwitchEvent e) {
        ProxiedPlayer pp = e.getPlayer();
        if(BungeeCordSystem.reportedPlayers.contains(pp)) {
            BungeeCordSystem.reportedPlayers.remove(pp);
        }
        if(BungeeCordSystem.alreadyWatchDog.contains(pp)) {
            BungeeCordSystem.alreadyWatchDog.remove(pp);
        }

    }

}
