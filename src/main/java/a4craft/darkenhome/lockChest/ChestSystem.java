package a4craft.darkenhome.lockChest;

import a4craft.darkenhome.DarkenHome;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ChestSystem implements Listener {
    //TODO create dir for chests
    //TODO on block place check if is a chest the
    //TODO if is a chest lock this chest by coord location for file name, make sure this only returns an int
    //TODO lock owner in the config for the chest using ONLY UUID and username, username for checking info
    //UUID for namechanges that could happen and stuff
    //TODO had users that can unlock this chest

    //not this update but another one, log system for said chests


    public DarkenHome client;
    public ChestSystem(DarkenHome darkenhome) {
        this.client = darkenhome;
        try {
            File file = new File(DarkenHome.PATH_Chests);
            File[] files = file.listFiles();

            for (int x = 0; x < Objects.requireNonNull(files).length; x++) {
                YamlConfiguration yml = new YamlConfiguration();
                yml.load(files[x]);
                String owner = yml.getString("chest.owner");
                String username = yml.getString("chest.username");
                String world = yml.getString("chest.world");
                ArrayList<String> allowed = (ArrayList<String>) yml.getList("chest.allowed");
                client.chestData.add(new ChestData(owner, username, world, allowed));
            }
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    @EventHandler
    public void OnItemPlacement(BlockPlaceEvent event) throws IOException, InvalidConfigurationException {
        Material placedMaterial = event.getBlockPlaced().getBlockData().getMaterial();
        if(placedMaterial == Material.CHEST) {
            Player p = (Player) event.getPlayer();
            UUID generateChestID = UUID.randomUUID();
            File ChestFile = new File(DarkenHome.PATH_Chests + "/"+generateChestID.toString()+".yml");
            if (!ChestFile.exists()) {
                ChestFile.createNewFile();

                YamlConfiguration yml = new YamlConfiguration();
                yml.load(ChestFile);
                yml.set("chest.owner", p.getUniqueId().toString());
                yml.set("chest.username", p.getName());
                yml.set("chest.world", p.getWorld().getName());
                yml.set("chest.allowed", new ArrayList<String>());
                yml.save(ChestFile);

                client.chestData.add(new ChestData(p));
            }

        }

    }

    @EventHandler
    public void OnBlockBreakEvent(BlockBreakEvent e) throws IOException, InvalidConfigurationException {
        Material placedMaterial = e.getBlock().getBlockData().getMaterial();
        boolean canBreakBlock = true;
        if(placedMaterial == Material.CHEST) {
            Player p = (Player) e.getPlayer();
            for (int i = 0; i < client.chestData.size(); i++) {
                if (client.chestData.get(i).uuid.equals(p.getUniqueId().toString())) {
                    File ChestFile = new File(DarkenHome.PATH_Chests + "/" + p.getLocation().getBlockX() + "," + p.getLocation().getBlockY() + "," + p.getLocation().getBlockZ() + ".yml");
                    ChestFile.delete();
                } else {
                    p.sendMessage("Only the owner can destory this block");
                    canBreakBlock = false;
                }
            }
        }

        if(!canBreakBlock){
            e.setCancelled(true);
        }
    }


}
