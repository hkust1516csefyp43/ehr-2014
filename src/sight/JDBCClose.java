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

public class JDBCClose {

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
			Logger.getLogger(JDBCClose.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
