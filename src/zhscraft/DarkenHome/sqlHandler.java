package zhscraft.DarkenHome;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.util.Vector;

public class sqlHandler {

	public static boolean checkDatabase_homeset(String tablename, String UUID) throws ClassNotFoundException, SQLException {
		Connection c = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
		
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:" + Client.PATH + "home.db");
		stmt = c.prepareStatement("SELECT * FROM homeset");
		rs = stmt.executeQuery();

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

		} catch (SQLException | ClassNotFoundException ex) {
			Bukkit.broadcastMessage("SQL was not excuted!");
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

	public static void setHomeset(String UUID, String getX, String getY, String getZ, boolean flag, boolean isUpdating) {
		Connection c = null;
		PreparedStatement stmt = null;
		Statement st = null;

		try {

			if (isUpdating == true) {

				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:" + Client.PATH + "home.db");

				String query = "update homeset set getX = ?, getY = ?, getZ = ?, public = ? where UUID = ?";
				stmt = c.prepareStatement(query);
				stmt.setString(1, getX);
				stmt.setString(2, getY);
				stmt.setString(3, getZ);
				stmt.setBoolean(4, flag);
				stmt.setString(5, UUID);

				stmt.execute();

			} else {

				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:" + Client.PATH + "home.db");

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

	public static Vector GetHomeCoords(String UUID) {
		Vector vec = new Vector();

		Connection c = null;
		Statement pst = null;
		ResultSet rs = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + Client.PATH + "home.db");

			pst = c.createStatement();

			rs = pst.executeQuery("SELECT * FROM homeset WHERE UUID = '" + UUID + "'");

			while (rs.next()) {
				vec.setX(rs.getInt(2));
				vec.setY(rs.getInt(3));
				vec.setZ(rs.getInt(4));
			}

			pst.close();
			c.close();
		} catch (SQLException | ClassNotFoundException ex) {
			Bukkit.broadcastMessage("SQL was not excuted!");
			ex.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return vec;
	}

	public static void addInvited(String UUID, String invitedPlayerUUID) {
		Connection c = null;
		PreparedStatement stmt = null;
		Statement st = null;

		try {

			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + Client.PATH + "home.db");

			String sql = "INSERT INTO homeinvited(UUID, invitedUUID) VALUES(?,?)";
			stmt = c.prepareStatement(sql);
			Bukkit.broadcastMessage("MaxRows " + stmt.getMaxRows());
			// stmt.setInt(1, stmt.getMaxRows() + 1);

			stmt.setString(1, UUID);
			stmt.setString(2, invitedPlayerUUID);

			stmt.execute();

		} catch (SQLException | ClassNotFoundException ex) {
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

	public static void removePlayerByUUID(String myUUID) {
		Connection c = null;
		Statement st = null;
		

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + Client.PATH + "home.db");
			st = c.createStatement();
			String SQL = "DELETE FROM homeset WHERE UUID = '" + myUUID + "' ";
			st.executeUpdate(SQL);
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
				if (st != null) {
					st.close();
				}

			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

	}

}
