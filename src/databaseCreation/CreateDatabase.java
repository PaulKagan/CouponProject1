package databaseCreation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateDatabase {

	/**
	 * Main method that creates a new Apache Derby DB called - CouponSystem1
	 * @param args not used.
	 */
	public static void main(String[] args) {
		Connection con = null;
		try {
			// 1. load the driver
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			System.out.println("driver loaded");
			// 2. establish a connection to the database - using DriverManager
			String url = "jdbc:derby://localhost/CouponSystem1;create=true";
			con = DriverManager.getConnection(url);
			System.out.println("connection established" + con);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			// 3. close the connection
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			System.out.println("connection closed " + con);
		}
	}

}
