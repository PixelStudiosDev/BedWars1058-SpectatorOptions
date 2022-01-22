package me.cubecrafter.spectatoroptions.utils;

import me.cubecrafter.spectatoroptions.SpectatorOptions;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class FileManager {

    private YamlConfiguration yml;
    private final File config;

    public FileManager(String filename, String path){
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        config = new File(path + "/" + filename + ".yml");
        if(!config.exists()){
            try{
               config.createNewFile();
            }catch(IOException ex){
                ex.printStackTrace();
            }
            InputStream inputStream = SpectatorOptions.getInstance().getResource(filename + ".yml");
            Reader reader = new InputStreamReader(inputStream);
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(reader);
            try{
                cfg.save(config);
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
        load();
    }

    public void load(){
        yml = YamlConfiguration.loadConfiguration(config);
    }

    public void save(){
        try{
           yml.save(config);
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public YamlConfiguration getYml(){
        return yml;
    }

}
