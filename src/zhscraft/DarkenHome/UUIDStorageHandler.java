package zhscraft.DarkenHome;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class UUIDStorageHandler {

	public UUIDStorageHandler() {
		// TODO Auto-generated constructor stub
	}
	
	public static boolean CheckIfUUIDMatches(String UUID) throws ClassNotFoundException, SQLException{
		Connection c = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

		
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:" + Client.PATH + "home.db");
		stmt = c.prepareStatement("SELECT isAdded FROM UUIDStorage WHERE UUID = '" + UUID + "'");
		rs = stmt.executeQuery();

		while (rs.next()) {
			if(rs.getString(1).toString().equalsIgnoreCase("TRUE"))
			{
				return true;
			}
		}
	

		} catch (SQLException | ClassNotFoundException ex) {
			System.out.println("SQL was not excuted!");
			ex.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}

			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return false;
	}
	
	public static void updateDatabaseForNameChanges(boolean inDatabase, Player p){
		Connection c = null;
		PreparedStatement stmt = null;
		Statement st = null;

		try {

			if (inDatabase == true) {

				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:" + Client.PATH + "home.db");

				String query = "update UUIDStorage set UUID = ?, GameName = ? where UUID = ?";
				stmt = c.prepareStatement(query);
				stmt.setString(1, p.getUniqueId().toString());
				stmt.setString(2, p.getName().toString());
				stmt.setString(3, p.getUniqueId().toString());

				stmt.execute();

			} else {

				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:" + Client.PATH + "home.db");

				String sql = "INSERT INTO UUIDStorage(UUID, GameName, isAdded) VALUES(?, ?, ?)";
				stmt = c.prepareStatement(sql);
				stmt.setString(1, p.getUniqueId().toString());
				stmt.setString(2, p.getName().toString());
				stmt.setString(3, "TRUE");
				stmt.execute();

			}

		} catch (SQLException | ClassNotFoundException ex) {
			System.out.println("SQL was not excuted!");
			ex.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
				if (st != null) {
					st.close();
				}
				if (stmt != null) {
					stmt.close();
				}

			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

	}

	public static String getUUIDFromUsername(String GameName) {
		Connection c = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + Client.PATH + "home.db");
			stmt = c.prepareStatement("SELECT UUID FROM UUIDStorage WHERE GameName = '" + GameName + "'");
			rs = stmt.executeQuery();

			while (rs.next()) {
					return rs.getString(1);
				
			}
		

			} catch (SQLException | ClassNotFoundException ex) {
				System.out.println("SQL was not excuted!");
				ex.printStackTrace();
			} finally {
				try {
					if (c != null) {
						c.close();
					}
					if (rs != null) {
						rs.close();
					}
					if (stmt != null) {
						stmt.close();
					}

				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			return null;
	}
			
			
		
		
	

}
