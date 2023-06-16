package a4craft.darkenhome;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Teleport implements CommandExecutor {
    public DarkenHome client;
    public int CommandType;
    public Teleport(DarkenHome darkenHome, int type) {
        this.client = darkenHome;
        this.CommandType = type;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player)sender;
        if(p.isOp() || p.hasPermission("dark.home.admin.teleports")){
            if(label.equalsIgnoreCase("tphere")){
                if(args.length > 0){
                    Player otherPlayer = Bukkit.getPlayer(args[0]);
                    otherPlayer.teleport(p.getLocation());
                    otherPlayer.sendMessage("you have been teleported to "+p.getName());
                    p.sendMessage("you have teleported " + otherPlayer.getName() + " to you");
                }
            }else if(label.equalsIgnoreCase("tpto")){
                if(args.length > 0){
                    Player otherPlayer = Bukkit.getPlayer(args[0]);
                    p.teleport(otherPlayer.getLocation());
                    p.sendMessage("you have been teleported to "+otherPlayer.getName());
                    otherPlayer.sendMessage(p.getName() + " has teleported to you");
                }
            }
        }
        return false;
    }
}
