package a4craft.darkenhome;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

public class LoginEvent implements Listener {

    @EventHandler
    public void JoinEvent(PlayerJoinEvent e) throws IOException, InvalidConfigurationException {
        YamlConfiguration yml = new YamlConfiguration();
        File userConfig = new File(DarkenHome.UserFile + e.getPlayer().getUniqueId() + ".yml");
        if(!userConfig.exists()){
            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "Welcome " + e.getPlayer().getName() + " To the server!");
            userConfig.createNewFile();
            yml.load(userConfig);
            yml.set("username", e.getPlayer().getName());
            yml.save(userConfig);
        }



    }
}
