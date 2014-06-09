package zhscraft.DarkenHome;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoginEvent implements Listener {

	@EventHandler
	public void Join(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		try {
			
			Bukkit.broadcastMessage("am i in database? " + UUIDStorageHandler.CheckIfUUIDMatches(p.getUniqueId().toString()));
			UUIDStorageHandler.updateDatabaseForNameChanges(UUIDStorageHandler.CheckIfUUIDMatches(p.getUniqueId().toString()), p);
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
	}
}
