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

import java.io.File;
import java.io.IOException;

public class SpawnSetup implements CommandExecutor {
    public DarkenHome client;
    public SpawnSetup(DarkenHome darkenHome) {
        this.client = darkenHome;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player)sender;
        if(label.equalsIgnoreCase("setspawn") && p.isOp()){
            YamlConfiguration YmlConfig = new YamlConfiguration();
            try {
                YmlConfig.load(new File(client.ConfigFile));
                Vector spawnLocation = p.getLocation().toVector();
                String spawnName = p.getWorld().getName();
                YmlConfig.set("spawnPos", spawnLocation);
                YmlConfig.set("spawnWorld", spawnName);
                p.sendMessage("Spawn was set at your location");
                YmlConfig.save(new File(client.ConfigFile));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
