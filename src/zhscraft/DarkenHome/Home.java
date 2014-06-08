package zhscraft.DarkenHome;

import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import zhscraft.DarkenHome.sql.sqlHandler;

public class Home implements CommandExecutor {

	Client c;

	public Home(Client c) {
		this.c = c;
	}

	public boolean onCommand(CommandSender pa, Command arg1, String arg2, String[] a) {
		Player p = (Player) pa;
		UUID i = p.getUniqueId();

		if (arg2.equalsIgnoreCase("home") && a.length == 0) {
			try {
				if (sqlHandler.checkDatabase_homeset("homeset", p.getUniqueId().toString()) == true) {
					Vector v = sqlHandler.GetHomeCoords(p.getUniqueId().toString());
					p.teleport(new Location(p.getWorld(), v.getX(), v.getY(), v.getZ()));
					p.sendMessage(ChatColor.DARK_GRAY + "Welcome to you're home");
				} else {
					p.sendMessage(ChatColor.DARK_GRAY + "you do not have a home use '/home set' to setup you're homeset");
				}

			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

		} else if (arg2.equalsIgnoreCase("home") && a.length > 0) {
			if (Bukkit.getOfflinePlayer(a[0]) != null || Bukkit.getPlayer(a[0]) != null) {
				if (a[0].contains(Bukkit.getOfflinePlayer(a[0]).getName()) || a[0].contains(Bukkit.getPlayer(a[0]).getName())) {

					try {
						String myUUID = p.getUniqueId().toString();
						String OtherPlayerUUID = Bukkit.getOfflinePlayer(a[0]).getUniqueId().toString();
						String OwnerName = Bukkit.getOfflinePlayer(UUID.fromString(OtherPlayerUUID)).getName();
						try {
							if (sqlHandler.checkDatabase_homeset("homeset", OtherPlayerUUID) == true) {
							
								Vector v = sqlHandler.GetHomeCoords(Bukkit.getOfflinePlayer(OtherPlayerUUID).getName());

								p.teleport(new Location(p.getWorld(), v.getX(), v.getY(), v.getZ()));

								p.sendMessage(ChatColor.DARK_GRAY + "Welcome to " + OwnerName + " home");
							}else{
								p.sendMessage(ChatColor.DARK_GRAY + OwnerName + " dosen't have a home");
							}
						} catch (ClassNotFoundException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} catch (NullPointerException e) {
						e.printStackTrace();
					}
				}

			}
		}

		return false;
	}

}
