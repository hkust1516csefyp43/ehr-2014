package sight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
*
* @author Stephen, Alton
* @version 1.0
* @date 22/05/2015
* 
*/

public class JDBC {

	private static final String localurl = "jdbc:mysql://localhost:3306/sight";
	//private static final String syncurl = "jdbc:mysql://localhost:3306/sightserver";
	private static final String syncurl = "jdbc:mysql://192.168.0.98:3306/sight";
	private static final String foreignurl = "jdbc:mysql://192.168.0.99:3306/sight";
	private static final String syncIP = "192.168.0.98";
	private static boolean isLocal = false;
	private static final String accountName = "root";
	private static final String password = "1234";

	public static String getURL() {
		if (isLocal) {
			return localurl;
		} else {
			return foreignurl;
		}
	}

	public static String getTestingURL()
	{
		return localurl;
	}

	public static String getRealURL()
	{
		return foreignurl;
	}

	public static String getSyncURL()
	{
		return syncurl;
	}

	public static String getaccountName() {
		return accountName;
	}

	public static String getpassword() {
		return password;
	}

	public static String getSyncIP() {
		return syncIP;
	}
	public static void setLocal(boolean isLocal) {
		JDBC.isLocal = isLocal;
	}

	public static void close(ResultSet rs) {
		try {
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void close(Connection conn) {
		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void close(Statement stat) {
		try {
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void close(PreparedStatement prestat) {
		try {
			prestat.close();
		} catch (SQLException ex) {
			Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
