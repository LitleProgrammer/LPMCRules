package de.littleprogrammer.rules.files;

import de.littleprogrammer.rules.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DatabaseConfig {

    private final File file = new File(Main.getInstance().getDataFolder().getPath(), "DatabaseConfig.yml");
    private final FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    public void createFile(){

        if (!file.exists()){

            try {
                file.getParentFile().mkdir();
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }

        }
        addDefaults();
    }

    private void addDefaults(){
        cfg.options().copyDefaults(true);
        cfg.addDefault("HOST", "localhost");
        cfg.addDefault("PORT", 3306);
        cfg.addDefault("DATABASE", "lpmc_db");
        cfg.addDefault("USERNAME", "LpmcRules");
        cfg.addDefault("PASSWORD", "(xA0EUq2vOe-6Ng/");
        save();
    }

    public void save(){

        try {
            cfg.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public FileConfiguration getCfg() {
        return cfg;
    }
}
