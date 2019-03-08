package de.sinqularo.bungee.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;


public class QueryRunnable implements Runnable {

    private final PreparedStatement query;

    private final Callback<ResultSet> callback;

    private final MySQLManager manager;

    private final Lock lock;

    private final Condition condition;

    public QueryRunnable(final PreparedStatement query, final Callback<ResultSet> callback, final MySQLManager manager, final Lock lock, final Condition condition) {
        this.query = query;
        this.callback = callback;
        this.manager = manager;
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        try {
            this.manager.checkConnection();
        } catch (SQLException e) {
            this.callback.onFailure(e.getCause());
        }

        PreparedStatement statement = this.query;

        try {

            final ResultSet resultset = statement.executeQuery();

            if(resultset == null) {
                this.callback.onFailure(new NullPointerException());
            } else {
                this.callback.onSuccess(resultset);
                if(this.condition != null && this.lock != null) {
                    this.lock.lock();
                    this.condition.signal();
                    this.lock.unlock();
                }
            }
        } catch (SQLException e) {
            this.callback.onFailure(e.getCause());
        } finally {
            if(statement != null)
                try {
                    statement.close();
                } catch (SQLException e) {
                    this.callback.onFailure(e.getCause());
                }
        }

    }

}