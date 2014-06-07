package zhscraft.UUIDSystemHome;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Home implements CommandExecutor {

	Client c;

	public Home(Client c) {
		this.c = c;
	}

	@Override
	public boolean onCommand(CommandSender PlayerSender, Command command, String cmd, String[] a) {
		Player p = (Player) PlayerSender;

		if (a[0].contains("set")) {
			Vector vec = new Vector(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
			try {
				if (c.checkDatabase_homeset("homeset", p.getUniqueId().toString()) == true) {
					c.setHomeset(p.getUniqueId().toString(), "" + vec.getX(), "" + vec.getY(), "" + vec.getZ(), false, true);
					p.sendMessage(ChatColor.AQUA + "you're home has been updated :)");
				} else {
					c.setHomeset(p.getUniqueId().toString(), "" + vec.getX(), "" + vec.getY(), "" + vec.getZ(), false, false);
					p.sendMessage(ChatColor.AQUA + "Welcome to you're new home :)");
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (p.getPlayer().hasPermission("myhome.delete")) {

		}

		return false;
	}

}
