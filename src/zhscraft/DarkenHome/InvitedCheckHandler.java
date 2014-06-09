package zhscraft.DarkenHome;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Bukkit;

public class InvitedCheckHandler {

	public static boolean isInivted(String UUID, String myUUID) {
		Connection c = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + Client.PATH + "home.db");
			stmt = c.prepareStatement("SELECT * FROM homeinvited WHERE UUID = '" + UUID + "'");
			rs = stmt.executeQuery();
			ArrayList<String> array = new ArrayList<String>();

			while (rs.next()) {
				array.add(rs.getString(3));
			}
			
			if (c != null) {
				c.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}


			for(int i = 0; i < array.size(); i++){
				if(myUUID.equalsIgnoreCase(array.get(i).toString()) == true){
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
}
