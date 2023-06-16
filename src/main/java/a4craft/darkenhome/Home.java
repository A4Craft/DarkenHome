package a4craft.darkenhome;

import net.kyori.adventure.identity.Identified;
import net.kyori.adventure.identity.Identity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Home implements CommandExecutor {

    public DarkenHome client;

    public Home(DarkenHome c) {
        this.client = c;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!client.useHomeSystem)
            return false;

        Player p = (Player) sender;
        UUID uuid = p.getUniqueId();
        YamlConfiguration yml = new YamlConfiguration();
        File userConfig = new File(DarkenHome.UserFile + p.getUniqueId() + ".yml");
        boolean canUseCommand = true;
        try {
            yml.load(userConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        if (client.usePermissions) {
            canUseCommand = false;
            if (p.hasPermission("dark.home.user")) {
                canUseCommand = true;
            }
        }

        if (!canUseCommand)
            return false;


        if (label.equalsIgnoreCase("home") && args.length == 0) {


            if (yml.get("home.position") == null) {
                p.sendMessage("no home set do /sethome");
            } else {
                Vector homePos = yml.getVector("home.position");
                p.teleport(new Location(p.getWorld(), homePos.getX(), homePos.getY(), homePos.getZ()));
                p.sendMessage("Welcome Home " + p.getName());
            }
            try {
                yml.save(userConfig);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else if (label.equalsIgnoreCase("home") && args.length > 0) {

            Player otherPlayer = Bukkit.getPlayer(args[0]);

            YamlConfiguration yml2 = new YamlConfiguration();
            File OtherUserConfig = new File(DarkenHome.UserFile + otherPlayer.getUniqueId() + ".yml");
            try {
                yml2.load(OtherUserConfig);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }

            if (yml.get("home.position") == null) {
                p.sendMessage(otherPlayer.getPlayer() + " has no home set");
            } else {
                if (yml2.getBoolean("home.public")) {
                    Vector homePos = yml2.getVector("home.position");
                    p.teleport(new Location(p.getWorld(), homePos.getX(), homePos.getY(), homePos.getZ()));
                    p.sendMessage("Welcome To " + otherPlayer.getName() + " Home ");
                    p.sendMessage(ChatColor.YELLOW + yml2.getString("home.message"));
                } else {
                    p.sendMessage(ChatColor.RED + "This home is private");
                }
            }

        }


        return false;
    }

}
