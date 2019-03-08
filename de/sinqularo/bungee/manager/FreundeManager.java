package de.sinqularo.bungee.manager;

import de.sinqularo.bungee.BungeeCordSystem;
import de.sinqularo.bungee.mysql.Callback;
import de.sinqularo.bungee.mysql.PreparedStatementBuilder;
import de.sinqularo.bungee.mysql.entity.FriendEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class FreundeManager {

    public boolean isFriendsExists(final String uuid) {

        final ReentrantLock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();

        lock.lock();

        final boolean[] exists = new boolean[1];

        BungeeCordSystem.getBungeeCordSystem().getMySQLManager().query(new PreparedStatementBuilder("SELECT * FROM Freunde WHERE uuid=?", BungeeCordSystem.getBungeeCordSystem().getMySQLManager()).bindString(uuid).build(), new Callback<ResultSet>() {

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

    public FriendEntity getFriends(final String uuid) {

        final ReentrantLock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();

        lock.lock();

        FriendEntity friend = new FriendEntity();

        if (!isFriendsExists(uuid)) {
            BungeeCordSystem.getBungeeCordSystem().getMySQLManager().update(new PreparedStatementBuilder("INSERT INTO Freunde(uuid, name, friends, request, favorites, settings) VALUES (?, ?, ?, ?, ?, ?)", BungeeCordSystem.getBungeeCordSystem().getMySQLManager()).bindString(uuid).bindString(" ").bindString(" ").bindString(" ").bindString(" ").bindString(" ").build());

            BungeeCordSystem.getBungeeCordSystem().getMySQLManager().query(new PreparedStatementBuilder("SELECT * FROM Freunde WHERE uuid=?", BungeeCordSystem.getBungeeCordSystem().getMySQLManager()).bindString(uuid).build(), new Callback<ResultSet>() {

                @Override
                public void onSuccess(final ResultSet result) {

                    try {
                        if (result.next()) {



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
        return friend;
    }
    public void updateFriends (final FriendEntity friend){

        String friends = null;
        String request = null;
        String favorites = null;
        String settings = null;

        BungeeCordSystem.getBungeeCordSystem().getMySQLManager().update(new PreparedStatementBuilder("UPDATE Freunde SET name=?, friends=?, request=?, favorites=? ,settings=? WHERE uuid=?", BungeeCordSystem.getBungeeCordSystem().getMySQLManager()).bindString(friend.getName()).bindString(friends).bindString(request).bindString(favorites).bindString(settings).bindString(friend.getUuid()).build());

    }
}
