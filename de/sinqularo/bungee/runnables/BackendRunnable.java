package de.sinqularo.bungee.runnables;

import de.sinqularo.bungee.BungeeCordSystem;
import de.sinqularo.bungee.mysql.PreparedStatementBuilder;
import de.sinqularo.bungee.mysql.entity.BungeePlayer;
import de.sinqularo.bungee.mysql.entity.FriendEntity;
import net.md_5.bungee.BungeeCord;

import java.util.concurrent.TimeUnit;

public class BackendRunnable {


    public static void startTimer() {
        BungeeCord.getInstance().getScheduler().schedule(BungeeCordSystem.getBungeeCordSystem(), () -> {
            BungeeCordSystem.getBungeeCordSystem().getMySQLManager().update(new PreparedStatementBuilder("SELECT * FROM Player WHERE UUID=?", BungeeCordSystem.getBungeeCordSystem().getMySQLManager()).build());
            BungeeCordSystem.getBungeeCordSystem().getMySQLManager().update(new PreparedStatementBuilder("SELECT * FROM Freunde WHERE UUID=?", BungeeCordSystem.getBungeeCordSystem().getMySQLManager()).build());
            BungeeCord.getInstance().getPlayers().forEach(proxiedPlayer -> {
                BungeePlayer bungeePlayer = BungeeCordSystem.getBungeeCordSystem().getPlayerManager().getPlayers(proxiedPlayer.getUniqueId().toString());
                FriendEntity friendEntity = BungeeCordSystem.getBungeeCordSystem().getFreundeManager().getFriends(proxiedPlayer.getUniqueId().toString());
                BungeeCordSystem.getBungeeCordSystem().setBackendUpdating(true);
                BungeeCordSystem.getBungeeCordSystem().getFreundeManager().updateFriends(friendEntity);
                BungeeCordSystem.getBungeeCordSystem().getPlayerManager().updatePlayer(bungeePlayer);
                BungeeCordSystem.getBungeeCordSystem().setBackendUpdating(false);
            });


        }, 10, 10, TimeUnit.SECONDS);
    }
}


