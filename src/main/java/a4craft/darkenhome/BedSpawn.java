package a4craft.darkenhome;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BedSpawn implements CommandExecutor {
    public DarkenHome client;
    public BedSpawn(DarkenHome darkenHome) {
        this.client = darkenHome;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player)sender;

        if(label.equalsIgnoreCase("bed")){
            if(p.getBedSpawnLocation() != null) {
                
                p.teleport(p.getBedSpawnLocation());
            }else{
                p.sendMessage("No bed has been set");
            }
        }
        return false;
    }
}
