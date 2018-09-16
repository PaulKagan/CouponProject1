package create;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import beans.Company;
import beans.Coupon;
import beans.CouponType;
import beans.Customer;

public class DBObjectsForExample {

	public DBObjectsForExample() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		String url = "jdbc:derby://localhost/CouponSystem1;create=true";
		String sql = "INSERT INTO company VALUES(?,?,?,?)";
		String sql2 = "INSERT INTO coupon VALUES(?,?,?,?,?,?,?,?,?)";
		String sql3 = "INSERT INTO Customer VALUES(?,?,?)";
		Company comp = new Company (0l,"ExampleCompany","example","example@mail.com");
		Coupon coup = new Coupon(0l, "ExampleCoupon", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()*5), 1, CouponType.FOOD, "example", 0d, "none");
		Customer cust = new Customer(0l, "ExampleCustomer", "example");
		try (Connection con = DriverManager.getConnection(url); PreparedStatement stmt = con.prepareStatement(sql);PreparedStatement stmt2 = con.prepareStatement(sql2);
				PreparedStatement stmt3 = con.prepareStatement(sql3);) {

			stmt.setLong(1, comp.getId());
			stmt.setString(2, comp.getCompName());
			stmt.setString(3, comp.getPassword());
			stmt.setString(4, comp.getEmail());
			stmt.executeUpdate();
			stmt2.setLong(1, coup.getId());
			stmt2.setString(2, coup.getTitle());
			stmt2.setDate(3, coup.getStartDate());
			stmt2.setDate(4, coup.getEndDate());
			stmt2.setInt(5, coup.getAmount());
			stmt2.setString(6, coup.getType().toString());
			stmt2.setString(7, coup.getMessage());
			stmt2.setDouble(8, coup.getPrice());
			stmt2.setString(9, coup.getImage());
			stmt2.executeUpdate();
			stmt3.setLong(1, cust.getId());
			stmt3.setString(2, cust.getCustName());
			stmt3.setString(3, cust.getPassword());
			stmt3.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub

	}

}
