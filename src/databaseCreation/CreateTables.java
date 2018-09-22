package databaseCreation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CreateTables {

	
	/**
	 * Main method that creates anew the Coupon System database tables.
	 * @param args not used.
	 */
	public static void main(String[] args) {

		List<String> sqlList = new ArrayList<String>();
		// Drops all Coupon System database tables
		String sql1 = "DROP TABLE COMPANY";
		String sql2 = "DROP TABLE CUSTOMER";
		String sql3 = "DROP TABLE COUPON";
		String sql4 = "DROP TABLE CUSTOMER_COUPON";
		String sql5 = "DROP TABLE COMPANY_COUPON";

		
		 // Recreates all Coupon System database tables. All the id's cannot be Null and are generated automatically so they start at 0 and incremented by 1.
		 
		String sql6 = "CREATE TABLE COMPANY(ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1), COMP_NAME VARCHAR(255), PASSWORD VARCHAR(255), EMAIL VARCHAR(255),CONSTRAINT PK_Company PRIMARY KEY (ID))";
		String sql7 = "CREATE TABLE CUSTOMER(ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1), CUST_NAME VARCHAR(255), PASSWORD VARCHAR(255),CONSTRAINT PK_Customer PRIMARY KEY (ID))";
		String sql8 = "CREATE TABLE COUPON(ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1), TITLE VARCHAR(255), START_DATE DATE,END_DATE DATE,AMOUNT INTEGER,TYPE VARCHAR(255),MESSAGE VARCHAR(255),PRICE FLOAT,IMAGE VARCHAR(255),CONSTRAINT PK_Coupon PRIMARY KEY (ID))";
		String sql9 = "CREATE TABLE CUSTOMER_COUPON(CUST_ID BIGINT NOT NULL,COUPON_ID BIGINT NOT NULL,CONSTRAINT PK_CustomerCoupon PRIMARY KEY(CUST_ID,COUPON_ID))";
		String sql10 = "CREATE TABLE COMPANY_COUPON(COMP_ID BIGINT NOT NULL,COUPON_ID BIGINT NOT NULL,CONSTRAINT PK_CompanyCoupon PRIMARY KEY(COMP_ID,COUPON_ID))";
		sqlList.add(sql1);
		sqlList.add(sql2);
		sqlList.add(sql3);
		sqlList.add(sql4);
		sqlList.add(sql5);
		sqlList.add(sql6);
		sqlList.add(sql7);
		sqlList.add(sql8);
		sqlList.add(sql9);
		sqlList.add(sql10);

		String url = "jdbc:derby://localhost/CouponSystem1;create=true";

		try (Connection con = DriverManager.getConnection(url);) {

			System.out.println("Connected to the database, and recreating new tables");

			for (int i = 0; i < sqlList.size(); i++) {
				String sql = sqlList.get(i);
				Statement stmt = con.createStatement();
				stmt.executeUpdate(sql);
				System.out.println(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Done.");
	}

}
