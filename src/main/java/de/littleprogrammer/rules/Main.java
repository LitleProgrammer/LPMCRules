package de.littleprogrammer.rules;

import de.littleprogrammer.rules.commands.KickCommand;
import de.littleprogrammer.rules.commands.RulesCommand;
import de.littleprogrammer.rules.commands.TestInvCommand;
import de.littleprogrammer.rules.files.DatabaseConfig;
import de.littleprogrammer.rules.listeners.Listeners;
import de.littleprogrammer.rules.listeners.MessageListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Main extends JavaPlugin {

    private static Main instance;
    private DatabaseConfig databaseConfig;
    private Database database;
    private StringBuilder sb = new StringBuilder();

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new MessageListener());

        //Commands
        getCommand("Test").setExecutor(new TestInvCommand());
        getCommand("rules").setExecutor(new RulesCommand());
        getCommand("kack").setExecutor(new KickCommand());

        //Listeners
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);

        //Config
        databaseConfig = new DatabaseConfig();
        databaseConfig.createFile();

        //Database
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {
            database = new Database();
            try {
                database.connect();
                System.out.println("Server Ranks Reconnect");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Reconnect Failed (Server Ranks)");
            }
        }, 3600, 3600);


        database = new Database();
        try {
            database.connect();
        } catch (SQLException e) {
            System.out.println("Can not connect to the database");
            e.printStackTrace();
        }
        System.out.println("Database is connected = " + database.isConnected());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
        database.disconnect();
    }

    public static Main getInstance() {
        return instance;
    }

    public DatabaseConfig getDatabaseConfig() {
        return databaseConfig;
    }

    public Database getDatabase() {
        return database;
    }
}
