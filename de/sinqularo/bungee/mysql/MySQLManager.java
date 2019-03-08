package de.sinqularo.bungee.mysql;


import de.sinqularo.bungee.BungeeCordSystem;
import net.md_5.bungee.BungeeCord;

import java.sql.*;
import java.text.MessageFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MySQLManager {

    private final String host;
    private final int port;

    private final String database;

    private final String username;
    private final String password;

    private Connection con;

    private final ExecutorService service = Executors.newCachedThreadPool();

    public MySQLManager(final String host, final int port, final String database, final String username, final String password) {

        this.host = host;
        this.port = port;

        this.database = database;

        this.username = username;
        this.password = password;

    }

    public MySQLManager openConnection() {
        final String url = MessageFormat.format("jdbc:mysql://{0}:{1}/{2}?autoReconnect=true&useUnicode=yes", this.host, String.valueOf(this.port), this.database);
        try {
            this.con = DriverManager.getConnection(url, this.username, this.password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public MySQLManager openConnection(final Runnable runable) throws SQLException {
        final String url = MessageFormat.format("jdbc:mysql://{0}:{1}/{2}?autoReconnect=true&useUnicode=yes", this.host, String.valueOf(this.port), this.database);
        this.con = DriverManager.getConnection(url, this.username, this.password);
        runable.run();
        return this;
    }

    public void update(final PreparedStatement statement) {
        if(!BungeeCordSystem.getBungeeCordSystem().isBackendUpdating()) {
            this.service.execute(() -> {
                try {
                    this.syncUpdate(statement);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } else {
           // Bukkit.getScheduler().scheduleSyncDelayedTask(BungeeCordSystem.getBungeeCordSystem(), () -> {
            BungeeCord.getInstance().getScheduler().schedule(BungeeCordSystem.getBungeeCordSystem(), ()->{
                this.service.execute(() -> {
                    try {
                        this.syncUpdate(statement);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }, 1, TimeUnit.SECONDS);
        }
    }

    private void syncUpdate(final PreparedStatement statement) throws SQLException {
        this.checkConnection();

        statement.executeUpdate();
        statement.close();
    }

    public void query(final PreparedStatement query, final Callback<ResultSet> callback, final Lock lock, final Condition condition) {
        this.service.execute(new QueryRunnable(query, callback, this, lock, condition));
    }

    public void query(final PreparedStatement query, final Callback<ResultSet> callback) {
        this.service.execute(new QueryRunnable(query, callback, this, null, null));
    }

    public final PreparedStatement prepareStatement(final String update) {
        try {
            return this.con.prepareStatement(update);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void checkConnection() throws SQLException {
        if(!this.isConnected())
            this.openConnection();
    }

    public void closeConnection() {
        if(!this.isConnected())
            return;

        try {
            this.con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.service.shutdown();
        this.con = null;
    }

    private boolean isConnected() {

        try {
            return this.con != null && !this.con.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public final Connection getConnection() {
        return this.con;
    }

}
