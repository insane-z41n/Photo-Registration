package pr.z41n.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	private static String UNAME = "zainmo";
	private static String PASS = "Zain28347";
	private static String CONN = "jdbc:mysql://localhost:3307/photo_registration";
	
	//Create Connection with database.
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(CONN, UNAME, PASS);
	}
	
	
}
