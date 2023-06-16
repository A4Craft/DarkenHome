package a4craft.darkenhome;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class SetHome implements CommandExecutor {
    public DarkenHome client;
    public SetHome(DarkenHome darkenHome) {
        this.client = darkenHome;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!client.useHomeSystem)
            return false;

        Player p = (Player)sender;
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
            if (p.hasPermission("dark.home.sethome")) {
                canUseCommand = true;
            }
        }

        if (!canUseCommand)
            return false;

        if(label.equalsIgnoreCase("sethome")){
            if(args.length == 0){

                p.sendMessage("Home spawn set! /home to return here anytime");
                yml.set("home.public", false);
                yml.set("home.message", "");
                yml.set("home.position", p.getLocation().toVector());
                yml.set("home.world", p.getLocation().toVector());
                SaveMyConfig(yml, new File(DarkenHome.UserFile + p.getUniqueId() + ".yml"));
            }else if(args.length > 0){

                boolean saveConfig = false;
                if(args[0].equalsIgnoreCase("help")){
                    p.sendMessage("/sethome | this set the default pos");
                    p.sendMessage("/sethome public/private | this sets the home public or private");
                    p.sendMessage("/sethome message msg | this sets the message they will send when they teleport");

                }else if(args[0].equalsIgnoreCase("public")){
                    yml.set("home.public", true);
                    p.sendMessage("you're home is now public");
                    saveConfig = true;
                }else if(args[0].equalsIgnoreCase("private")){
                    yml.set("home.public", false);
                    p.sendMessage("you're home is now private");
                    saveConfig = true;
                }else if(args[0].equalsIgnoreCase("message")){
                    StringBuilder stringbuilding = new StringBuilder();
                    for(int i = 1; i < args.length; i++){
                        stringbuilding.append(args[i] + " ");
                    }
                    String msgString = stringbuilding.toString().trim();
                    yml.set("home.message", msgString);
                    p.sendMessage("Home Message updated!");
                    p.sendMessage(ChatColor.GREEN + " " + msgString);
                    saveConfig = true;

                }

                if(saveConfig){
                    SaveMyConfig(yml, new File(DarkenHome.UserFile + p.getUniqueId() + ".yml"));
                }
            }
        }
        return false;
    }

    public void SaveMyConfig(YamlConfiguration yml, File file){
        try {
            yml.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
