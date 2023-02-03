package de.littleprogrammer.rules;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private final String HOST = Main.getInstance().getDatabaseConfig().getCfg().getString("HOST");
    private final int PORT = Main.getInstance().getDatabaseConfig().getCfg().getInt("PORT");
    private final String DATABASE = Main.getInstance().getDatabaseConfig().getCfg().getString("DATABASE");
    private final String USERNAME = Main.getInstance().getDatabaseConfig().getCfg().getString("USERNAME");
    private final String PASSWORD = Main.getInstance().getDatabaseConfig().getCfg().getString("PASSWORD");

    private Connection connection;

    public void connect() throws SQLException {

        connection = DriverManager.getConnection(
                "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?autoReconnect=true&interactiveClient=true",
                USERNAME,
                PASSWORD);

    }

    public boolean isConnected(){ return connection != null; }

    public Connection getConnection() {
        return connection;
    }

    public void disconnect(){
        if (isConnected()){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
