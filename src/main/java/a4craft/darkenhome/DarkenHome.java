package a4craft.darkenhome;

import java.io.File;

import a4craft.darkenhome.lockChest.ChestData;
import a4craft.darkenhome.lockChest.ChestSystem;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class DarkenHome extends JavaPlugin {
    public static String PATH = "./plugins/DarkenHome/homes/";
    public static String PATH_Chests = "./plugins/DarkenHome/chests/";
    public static String UserFile = "./plugins/DarkenHome/homes/";
    public static String ConfigFile = "./plugins/DarkenHome/Config.yml";
    public YamlConfiguration ConfigYml = new YamlConfiguration();

    public boolean usePermissions;
    public boolean useBedSystem;
    public boolean useHomeSystem;
    public boolean useSpawn;

    public ArrayList<ChestData> chestData = new ArrayList<ChestData>();

    @Override
    public void onEnable() {
        try {
            this.createDir(PATH);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        ChestSystem chest = new ChestSystem(this);
        setupEvents(new LoginEvent(), this);
        setupEvents(chest, this);

        setupCommands("home", new Home(this));
        setupCommands("sethome", new SetHome(this));
        setupCommands("spawn", new Spawn(this));
        setupCommands("bed", new BedSpawn(this));

        //admin commands only
        setupCommands("setspawn", new SpawnSetup(this));
        setupCommands("tphere", new Teleport(this, 0));//id 0 just means use "tphere"
        setupCommands("tpto", new Teleport(this, 1));// same as ^ but "tpto"

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public void createDir(String path) throws  IOException, InvalidConfigurationException {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        File file2 = new File(ConfigFile);
        if (!file2.exists()) {
            file2.createNewFile();
            ConfigYml.load(ConfigFile);
            ConfigYml.set("usePermissions", false);
            usePermissions = false;
            ConfigYml.set("useBedSystem", true);
            useBedSystem = true;
            ConfigYml.set("useHomeSystem", true);
            useHomeSystem = true;
            ConfigYml.set("useSpawn", true);
            useSpawn = true;
            ConfigYml.save(ConfigFile);


        }
        ConfigYml = new YamlConfiguration();
        ConfigYml.load(ConfigFile);
        usePermissions = ConfigYml.getBoolean("usePermissions");
        useBedSystem = ConfigYml.getBoolean("useBedSystem");
        useHomeSystem = ConfigYml.getBoolean("useHomeSystem");
        useSpawn = ConfigYml.getBoolean("useSpawn");

        File file3 = new File(PATH_Chests);
        if (!file3.exists()) {
            file3.mkdirs();
        }

    }

    public void setupCommands(String command, CommandExecutor args){
        Bukkit.getServer().getPluginCommand(command).setExecutor(args);
    }

    public void setupEvents(Listener list, Plugin plug) {
        Bukkit.getServer().getPluginManager().registerEvents(list, plug);
    }

}
