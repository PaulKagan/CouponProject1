//package create;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//import connections.ConnectionPool;
//import exceptions.CouponSystemException;
//
//public class CreateTable {
//
//	public static ConnectionPool pool = ConnectionPool.getInstance();
//
//	public static void createCompanyTable() {
//
//		Connection con;
//		try {
//			con = pool.getConnection();
//		} catch (CouponSystemException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//
//		String sql = "CREATE TABLE Company(" + "ID bigint ," + "COMP_NAME varchar(75), " + "PASSWORD varchar(25), "
//				+ "EMAIL varchar(50), " + "PRIMARY KEY (ID))";
//		
//		try(PreparedStatement stm = con.prepareStatement(sql);) {
//			
//			stm.executeUpdate();
//
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
//
//		
//			try {
//				pool.returnConnection(con);
//			} catch (CouponSystemException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		
//
//	}
//	
//	
//	
//	
//	
//	
//
//}
