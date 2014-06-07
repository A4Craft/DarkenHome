package zhscraft.UUIDSystemHome;

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
import org.bukkit.plugin.java.JavaPlugin;

public class Client extends JavaPlugin {
	// public SQLite sqlite;

	public static String PATH = "./plugins/MyHome/";

	public void setupCommands(String cmd, CommandExecutor args) {
		Bukkit.getServer().getPluginCommand(cmd).setExecutor(args);
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

	}

	public void onDisable() {
		// sqlite.stop();
	}

	public void setHomeset(String UUID, String getX, String getY, String getZ, boolean flag, boolean isUpdating) {
		Connection c = null;
		PreparedStatement stmt = null;
		Statement st = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + PATH + "home.db");
			st = c.createStatement();

			if (isUpdating == true) {
				c.setAutoCommit(false);
				st.executeUpdate("UPDATE homeset SET "
						+ "getX = '"+getX+"',"
						+ "getY = '"+getY+"',"
						+ "getZ = '"+getZ+"', " 
						+ "WHERE UUID = "+UUID+"");
			} else {
				String sql = "INSERT INTO homeset(UUID, getX,getY,getZ,public) VALUES(?,?,?,?,?)";
				stmt = c.prepareStatement(sql);
				stmt.setString(1, UUID);
				stmt.setString(2, getX);
				stmt.setString(3, getY);
				stmt.setString(4, getZ);
				stmt.setBoolean(5, flag);
				stmt.execute();
			}

		} catch (SQLException | ClassNotFoundException ex) {
			Bukkit.broadcastMessage("SQL was not excuted!");
			ex.printStackTrace();
		} finally {

			try {
				if (stmt != null) {
					stmt.close();
				}
				if (c != null) {
					c.close();
				}

			} catch (SQLException ex) {
				Bukkit.broadcastMessage("SQL was not excuted!");
			}
		}

	}

	public boolean checkDatabase_homeset(String tablename, String UUID) throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		Connection c = DriverManager.getConnection("jdbc:sqlite:" + PATH + "home.db");
		PreparedStatement stmt = c.prepareStatement("SELECT * FROM homeset");
		ResultSet rs = stmt.executeQuery();

		ArrayList<String> data = new ArrayList<String>();
		while (rs.next()) {
			data.add(rs.getString(1));
		}
		String[] data1 = new String[data.size()];
		for (int a = 0; a < data1.length; ++a) {
			data1[a] = data.get(a);
			if (UUID.equals(data.get(a))) {
				return true;
			} else {
				return false;
			}
		}
		return false;

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

			String invites = "CREATE TABLE homeinvited " + "(UUID TEXT NOT NULL, " + " invitedUUID TEXT NOT NULL)";
			stmt.executeUpdate(sql);
			stmt.executeUpdate(invites);
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		System.out.println("Table created successfully");
	}

}
