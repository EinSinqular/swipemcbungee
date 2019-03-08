package de.sinqularo.bungee.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedStatementBuilder {

    private int index;

    private final PreparedStatement statement;

    public PreparedStatementBuilder(final String update, final MySQLManager manager) {
        this.statement = manager.prepareStatement(update);
        this.index = 1;
    }

    public final PreparedStatementBuilder bindString(final String bind) {
        try {
            this.statement.setString(this.index, bind);
            this.index++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public final PreparedStatementBuilder bindInt(final int bind) {
        try {
            this.statement.setInt(this.index, bind);
            this.index++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public final PreparedStatementBuilder bindBoolean(final boolean bind) {
        try {
            this.statement.setBoolean(this.index, bind);
            this.index++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public final PreparedStatement build() {
        return this.statement;
    }

}