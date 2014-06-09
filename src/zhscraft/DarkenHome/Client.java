package zhscraft.DarkenHome;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Client extends JavaPlugin {
<<<<<<< HEAD
=======
	//TODO remove all invited command
	//TODO add a uninvite command
>>>>>>> c046d564c7f5bd47231f3662f0f90f9c1fd067ab
	//TODO think of what i'm missing
	//TODO find and fix bugs if any

	public static String PATH = "./plugins/DarkenHome/";

	public void setupCommands(String cmd, CommandExecutor args) {
		Bukkit.getServer().getPluginCommand(cmd).setExecutor(args);
	}

	public void setupEvents(Listener list, Plugin plug){
		Bukkit.getServer().getPluginManager().registerEvents(list, plug);
	}
	public void createDir(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
			createSQL_HOME();
		}
	}

	public void onEnable() {
		this.createDir(PATH);
		setupCommands("home", new Home(this));
		setupCommands("myhome", new MyHome(this));
		setupEvents(new LoginEvent(), this);
	}

	public void createSQL_HOME() {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + PATH + "home.db");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "CREATE TABLE homeset " + "(UUID TEXT NOT NULL, " + " getX INT NOT NULL, " + " getY INT NOT NULL, " + " getZ INT NOT NULL, " + " public BOOLEAN NOT NULL)";
			String invites = "CREATE TABLE homeinvited " + "(id INT, UUID TEXT NOT NULL, invitedUUID TEXT NOT NULL)";
			String UUIDStorage = "CREATE TABLE UUIDStorage " + "(id INT, UUID TEXT NOT NULL, GameName TEXT NOT NULL, isAdded TEXT NOT NULL)";
			
			stmt.executeUpdate(sql);
			stmt.executeUpdate(invites);
			stmt.executeUpdate(UUIDStorage);
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		System.out.println("Table created successfully");
	}

}
