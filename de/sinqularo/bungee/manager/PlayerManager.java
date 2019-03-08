package de.sinqularo.bungee.manager;

import de.sinqularo.bungee.BungeeCordSystem;
import de.sinqularo.bungee.mysql.Callback;
import de.sinqularo.bungee.mysql.PreparedStatementBuilder;
import de.sinqularo.bungee.mysql.entity.BungeePlayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PlayerManager {

    public boolean isPlayerExist(final String uuid) {

        final ReentrantLock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();

        lock.lock();

        final boolean[] exists = new boolean[1];

        BungeeCordSystem.getBungeeCordSystem().getMySQLManager().query(new PreparedStatementBuilder("SELECT * FROM Player WHERE uuid=?", BungeeCordSystem.getBungeeCordSystem().getMySQLManager()).bindString(uuid).build(), new Callback<ResultSet>() {

            @Override
            public void onSuccess(final ResultSet result) {
                try {
                    if (result.next()) {
                        exists[0] = result.getString("uuid") != null;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(final Throwable cause) {
                cause.printStackTrace();
            }

        }, lock, condition);

        try {
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return exists[0];

    }

    public BungeePlayer getPlayers(final String uuid) {

        final ReentrantLock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();

        lock.lock();

        BungeePlayer bungeePlayer = new BungeePlayer();

        if (!isPlayerExist(uuid)) {
            BungeeCordSystem.getBungeeCordSystem().getMySQLManager().update(new PreparedStatementBuilder("INSERT INTO Player(uuid, name, ipadress, globalPoints, joinCount) VALUES (?, ?, ?, ?, ?)", BungeeCordSystem.getBungeeCordSystem().getMySQLManager()).bindString(uuid).bindString(" ").bindString(" ").bindInt(0).bindInt(0).build());

            BungeeCordSystem.getBungeeCordSystem().getMySQLManager().query(new PreparedStatementBuilder("SELECT * FROM Player WHERE uuid=?", BungeeCordSystem.getBungeeCordSystem().getMySQLManager()).bindString(uuid).build(), new Callback<ResultSet>() {

                @Override
                public void onSuccess(final ResultSet result) {

                    try {
                        if (result.next()) {
                        bungeePlayer.setUuid(uuid);
                        bungeePlayer.setName(result.getString("name"));
                        bungeePlayer.setIpAdress(result.getString("ipadress"));
                        bungeePlayer.setGlobalPoints(result.getInt("globalPoints"));
                        bungeePlayer.setJoinCount(result.getInt("joinCount"));


                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Throwable cause) {
                    cause.printStackTrace();
                }

            }, lock, condition);

            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }
        return bungeePlayer;
    }
    public void updatePlayer (final BungeePlayer player){

        String names, adresses;
        int  pointss, joinCounters;


        HashMap name = BungeeCordSystem.getPlayerName();
        HashMap adresse = BungeeCordSystem.getPlayerIP();
        HashMap points = BungeeCordSystem.getGlobalPoints();
        HashMap joinCoint = BungeeCordSystem.getJoinCounter();



        BungeeCordSystem.getBungeeCordSystem().getMySQLManager().update(new PreparedStatementBuilder("UPDATE Player SET name=?, ipadress=?, globalPoints=?, joinCount=? WHERE uuid=?", BungeeCordSystem.getBungeeCordSystem().getMySQLManager()).bindString(names).bindString(adresses).bindString(pointss).bindString(joinCounters).bindString(player.getUuid()).build());

    }
}
