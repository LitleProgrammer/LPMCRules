package de.littleprogrammer.rules;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CustomePlayer {

    private UUID uuid;
    private String rank;
    private byte acceptRules;

    public CustomePlayer(UUID uuid) throws SQLException {
        this.uuid = uuid;

        PreparedStatement statement = null;
        try {
            statement = Main.getInstance().getDatabase().getConnection().prepareStatement("SELECT RANK, ACCEPTRULES FROM players WHERE UUID = ?;");
            statement.setString(1, uuid.toString());
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                rank = rs.getString("RANK");
                acceptRules = rs.getByte("ACCEPTRULES");
            }else {
                rank = "SPIELER";
                acceptRules = 0;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public byte getAcceptRules() {
        return acceptRules;
    }

    public void setAcceptRules(byte acceptRules) {
        this.acceptRules = acceptRules;

        try {
            PreparedStatement statement = Main.getInstance().getDatabase().getConnection().prepareStatement("UPDATE players SET ACCEPTRULES = '" + acceptRules + "' WHERE UUID = '" + uuid + "';");
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String getRank() {
        return rank;
    }
}
