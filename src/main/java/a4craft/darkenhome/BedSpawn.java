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
        if(!client.useBedSystem)
            return false;

        Player p = (Player) sender;
        boolean hasPermissions = true;
        if (label.equalsIgnoreCase("bed")) {
            if (client.usePermissions) {
                hasPermissions = false;
                if (p.hasPermission("darkhome.use.bed")) {
                    hasPermissions = true;
                }
            }

            if (hasPermissions) {
                if (p.getBedSpawnLocation() != null) {
                    p.teleport(p.getBedSpawnLocation());
                } else {
                    p.sendMessage("No bed has been set");
                }
            }
        }
        return false;
    }
}
