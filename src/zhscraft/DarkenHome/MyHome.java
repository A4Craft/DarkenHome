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

public class MyHome implements CommandExecutor {

	Client c;

	public MyHome(Client c) {
		this.c = c;
	}

	@Override
	@SuppressWarnings("all")
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] a) {
		Player p = (Player) arg0;

		if (a[0].contains("help")) {
			p.sendMessage("/home");
			p.sendMessage("/home <player>");
			p.sendMessage("/myhome set");
			p.sendMessage("/myhome invite <player>");
			p.sendMessage("/myhome remove");
			p.sendMessage("/myhome clear | clears the invite list for you're homeset");
			if(p.hasPermission("darken.home.admin")){
				p.sendMessage("/myhome admin delete <player> | delete players home");
			}
		}
		if (a[0].contains("set")) {
			Location loc = p.getLocation();
			Vector vec = new Vector(loc.getX(), loc.getY(), loc.getZ());
			try {
				if (sqlHandler.checkDatabase_homeset("homeset", p.getUniqueId().toString()) == true) {
					sqlHandler.setHomeset(p.getUniqueId().toString(), "" + vec.getX(), "" + vec.getY(), "" + vec.getZ(), false, true);
					p.sendMessage(ChatColor.AQUA + "you're home has been updated :)");
				} else {
					sqlHandler.setHomeset(p.getUniqueId().toString(), "" + vec.getX(), "" + vec.getY(), "" + vec.getZ(), false, false);
					p.sendMessage(ChatColor.AQUA + "Welcome to you're new home :)");
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (a[0].contains("invite")) {

			String input = Bukkit.getOfflinePlayer(a[1]).getUniqueId().toString();
			String MyUUID = p.getUniqueId().toString();
			String UUIDtoName = Bukkit.getOfflinePlayer(UUID.fromString(input)).getName();



			sqlHandler.addInvited(MyUUID, input);
			p.sendMessage(ChatColor.DARK_GRAY + "You have invited " + UUIDtoName);

			try {
				if (Bukkit.getPlayer(UUID.fromString(input)).isOnline()) {
					Bukkit.getPlayer(a[1]).sendMessage(ChatColor.DARK_GRAY + p.getName() + " has invited you to there home");
				}
			} catch (NullPointerException ex) {

			}

		}
		
		if(a[0].contains("clear")){
			String MyUUID = p.getUniqueId().toString();
			sqlHandler.removePlayerInvite(MyUUID);
			p.sendMessage(ChatColor.DARK_GRAY + "you have cleared you're invite list!");
			
			
		}

		if (a[0].contains("remove")) {
			String MyUUID = p.getUniqueId().toString();
			sqlHandler.removePlayerByUUID(MyUUID);
			p.sendMessage(ChatColor.DARK_GRAY + "you have delete you're home");
		}

		if (p.hasPermission("darken.home.admin")) {
			if (a[0].contains("admin")) {
				
				if(a[1].contains("delete")){
					String input = Bukkit.getOfflinePlayer(a[2]).getUniqueId().toString();
					String UUIDtoName = Bukkit.getOfflinePlayer(UUID.fromString(input)).getName();
					
					sqlHandler.removePlayerByUUID(input);
					p.sendMessage(ChatColor.DARK_GRAY + "you have delete "+ UUIDtoName +" home");
				}
								
			}
		}

		return false;
	}

}
