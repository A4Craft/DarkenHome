package a4craft.darkenhome.lockChest;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ChestData {
    public String uuid;
    public String username;
    public String world;
    public ArrayList<String> users = new ArrayList<String>();
    public boolean isPublic;
    public ChestData(Player player) {
        uuid = player.getUniqueId().toString();
        username = player.getName();
        world = player.getWorld().getName();

    }

    public ChestData(String owner, String username, String world, ArrayList<String> allowed) {
        this.uuid = owner;
        this.username = username;
        this.world = world;
        this.users = allowed;
    }
}
