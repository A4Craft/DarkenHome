package a4craft.darkenhome;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

//TODO config to disable the abilty to use /spawn
public class Spawn implements CommandExecutor {
    public DarkenHome client;
    public Spawn(DarkenHome darkenHome) {
        this.client = darkenHome;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        YamlConfiguration YmlConfig = new YamlConfiguration();
        try {
            YmlConfig.load(new File(client.ConfigFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }


        if(label.equalsIgnoreCase("spawn")){
            if(YmlConfig.get("spawnPos") != null){
                Player p = (Player)sender;
                Vector pos = YmlConfig.getVector("spawnPos");
                String spawnName = YmlConfig.getString("spawnWorld");
                p.teleport(new Location(Bukkit.getWorld(spawnName), pos.getX(), pos.getY(), pos.getZ()));
                p.sendMessage("Welcome to Spawn");
            }
        }
        return false;
    }
}
