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

		if (p.hasPermission("darken.home.user")) {
			CommandMethods.help(p, arg2, a);
			CommandMethods.set(p, arg2, a);
			CommandMethods.invite(p, arg2, a);
			CommandMethods.clear(p, arg2, a);
			CommandMethods.remove(p, arg2, a);
			CommandMethods.removeinvited(p, arg2, a);
		} else {
			p.sendMessage("you do not have permissions to use this command");
		}

		if (p.hasPermission("darken.home.admin")) {
			CommandMethods.admin(p, arg2, a);
		}

		return false;
	}

}
