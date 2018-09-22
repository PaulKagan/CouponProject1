package dataBaseDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import beans.Company;
import beans.Coupon;
import beans.CouponType;
import beans.Customer;
import connections.ConnectionPool;
import dataAccessObject.CouponDAO;
import exceptions.CoupSysRuntimeException;
import exceptions.CouponSystemException;
import exceptions.DatabaseDAOException;

public class CouponDBDAO implements CouponDAO {

	ConnectionPool pool;

	/**
	 * Constructor of the coupon database data access object, which grants access to the C.R.U.D commands.
	 * 
	 * @throws CouponSystemException if there were issues during the connection pool creation.
	 */
	public CouponDBDAO() throws CouponSystemException {
		super();
		this.pool = ConnectionPool.getInstance();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createCoupon(Coupon c) throws CouponSystemException {
		Connection con = pool.getConnection();
		String sql = "INSERT INTO coupon (title,start_date,end_date,amount,type,message,price,image) VALUES(?,?,?,?,?,?,?,?)";

		try (PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, c.getTitle());
			stmt.setDate(2, c.getStartDate());
			stmt.setDate(3, c.getEndDate());
			stmt.setInt(4, c.getAmount());
			stmt.setString(5, c.getType().toString());
			stmt.setString(6, c.getMessage());
			stmt.setDouble(7, c.getPrice());
			stmt.setString(8, c.getImage());
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			long coupId = rs.getLong(1);
			c.setId(coupId);
			
		} catch (SQLException e) {
			throw new DatabaseDAOException("ERROR: Creation failed. " + e.getMessage());
		} finally {
			pool.returnConnection(con);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeCoupon(Coupon c) throws CouponSystemException {
		Connection con = pool.getConnection();
		String s = "DELETE from Coupon where ID=" + c.getId();

		try {
			Statement stm = con.createStatement();
			stm.executeUpdate(s);
		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Coupon removal failed. " + e.getMessage());
		} finally {
			pool.returnConnection(con);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateCoupon(Coupon c) throws CouponSystemException {
		Connection con = pool.getConnection();
		String sql = "UPDATE coupon SET TITLE = ?, START_DATE = ?, END_DATE = ?, AMOUNT = ?, TYPE = ?, MESSAGE = ?, PRICE = ?, IMAGE = ? WHERE ID = ?";

		try (PreparedStatement stm = con.prepareStatement(sql);) {
			stm.setString(1, c.getTitle());
			stm.setDate(2, c.getStartDate());
			stm.setDate(3, c.getEndDate());
			stm.setInt(4, c.getAmount());
			stm.setString(5, c.getType().name());
			stm.setString(6, c.getMessage());
			stm.setDouble(7, c.getPrice());
			stm.setString(8, c.getImage());
			stm.setLong(9, c.getId());
			stm.executeUpdate();

		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Coupon update failed. " + e.getMessage());
		} finally {
			pool.returnConnection(con);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Coupon getCoupon(long id) throws CouponSystemException {
		Connection con = pool.getConnection();
		String sql = "select * from coupon where id=" + id;
		Coupon c = null;

		try (Statement stm = con.createStatement();) {
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				c = new Coupon(rs.getLong(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getInt(5),
						CouponType.valueOf(rs.getString(6)), rs.getString(7), rs.getDouble(8), rs.getString(9));
			}

		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Coupon search failed. " + e.getMessage());
		} finally {
			pool.returnConnection(con);
		}

		return c;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Coupon> getAllCoupons() throws CouponSystemException {
		Connection con = pool.getConnection();
		String sql = "select * from coupon";
		ArrayList<Coupon> coupList = new ArrayList<>();

		try (Statement stm = con.createStatement();) {
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				coupList.add(new Coupon(rs.getLong(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getInt(5),
						CouponType.valueOf(rs.getString(6)), rs.getString(7), rs.getDouble(8), rs.getString(9)));
			}

		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Getting all coupons failed. " + e.getMessage());
		} finally {
			pool.returnConnection(con);
		}

		return coupList;

	}


	/**
	 * Removes a list of coupons from the database coupon table.
	 * 
	 * @param coupList the list that is to be deleted.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public void removeCoupons(ArrayList<Coupon> coupList) throws CouponSystemException {
		Connection con = pool.getConnection();
		try (Statement stm = con.createStatement();) {
			for (Coupon coupon : coupList) {
				String sql = "DELETE from Coupon where ID=" + coupon.getId();
				stm.executeUpdate(sql);
			}
		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Coupons removal failed. " + e.getMessage());
		} finally {
			pool.returnConnection(con);
		}

	}
	
	/**
	 * Removes the entry of the company and it's coupon from the database company-coupon table.
	 * 
	 * @param coup the coupon to delete.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public void removeCompanyCoupon(Coupon coup) throws CouponSystemException {
		Connection con = pool.getConnection();
		String sql = "DELETE from Company_Coupon where Coupon_ID=" + coup.getId();
		try (Statement stm = con.createStatement();) {
			stm.executeUpdate(sql);
		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Company coupons removal failed. " + e.getMessage());
		} finally {
			pool.returnConnection(con);
		}
		
	}

	/**
	 * Removes the entry of the company and it's coupons from the database company-coupon table.
	 * 
	 * @param coupList the coupons to delete.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public void removeCompanyCoupons(ArrayList<Coupon> coupList) throws CouponSystemException {
		Connection con = pool.getConnection();
		try (Statement stm = con.createStatement();) {
			for (Coupon coupon : coupList) {
				String s = "DELETE from Company_Coupon where Coupon_ID=" + coupon.getId();
				stm.executeUpdate(s);
			}
		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Company coupons removal failed. " + e.getMessage());
		} finally {
			pool.returnConnection(con);
		}

	}
	
	/**
	 * Removes the entry of the customer and it's coupon from the database customer-coupon table.
	 * 
	 * @param coup the coupon to delete.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public void removeCustomerCoupon(Coupon coup) throws CouponSystemException {
		Connection con = pool.getConnection();
		String sql = "DELETE from Customer_Coupon where Coupon_ID=" + coup.getId();
		try (Statement stm = con.createStatement();) {
			stm.executeUpdate(sql);
		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Customer coupons removal failed. " + e.getMessage());
		} finally {
			pool.returnConnection(con);
		}
		
	}

	/**
	 * Removes an entry of the customer and it's coupon from the database customer-coupon table.
	 * 
	 * @param coupList the coupons to delete.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public void removeCustomerCoupons(ArrayList<Coupon> coupList) throws CouponSystemException {
		Connection con = pool.getConnection();
		try (Statement stm = con.createStatement();) {
			for (Coupon coupon : coupList) {
				String sql = "DELETE from Customer_Coupon where Coupon_ID=" + coupon.getId();
				stm.executeUpdate(sql);
			}
		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Customer coupons removal failed. " + e.getMessage());
		} finally {
			pool.returnConnection(con);
		}

	}

	/**
	 * Retrieve a coupon by a title, that assumed to be unique, from the database coupon table.
	 * 
	 * @param name is the title of the coupon to look for.
	 * @return a coupon by the title name, or null if wasn't found one.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public Coupon getCouponByTitle(String name) throws CouponSystemException {
		Connection con = pool.getConnection();
		String sql = "SELECT * FROM coupon where title='" + name + "'";
		Coupon c = null;
		try (Statement stm = con.createStatement();) {
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				c = new Coupon(rs.getLong(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getInt(5),
						CouponType.valueOf(rs.getString(6)), rs.getString(7), rs.getDouble(8), rs.getString(9));
			}
			return c;
		} catch (SQLException e) {

			throw new DatabaseDAOException("Error: Getting company failed. " + e.getMessage());
		} finally {
			pool.returnConnection(con);
		}

	}

	/**
	 * Retrieves coupons owned by a company,
	 *  by searching with one more parameters from the database coupon and company-coupon tables.
	 * 
	 * @param comp is the company owning the coupons.
	 * @param title is the coupon title.
	 * @param startDate is the coupon start date.
	 * @param endDate is the coupon end date.
	 * @param amount is the amount of such coupons.
	 * @param type is the coupon type.
	 * @param price is the coupon price
	 * @return a list of coupons that were found by the parameters.
	 * @throws CouponSystemException  if there were issues during method runtime.
	 */
	public ArrayList<Coupon> getAllOwnedCouponsByParameters(Company comp, String title, Date startDate, Date endDate,
			Integer amount, CouponType type, Double price) throws CouponSystemException {
		// check if the user sent at least one parameter.
		if (comp == null && title == null && startDate == null && endDate == null && amount == -1 && type == null
				&& price == -1d) {
			throw new CoupSysRuntimeException("Error: No parameters were given.");
		} else {
			Connection con = pool.getConnection();
			String sql = "SELECT coupon.* from company_coupon right join coupon on company_coupon.coupon_id = coupon.id where ";
			ArrayList<Coupon> coupList = new ArrayList<>();

			if (comp != null) {
				sql += "company_coupon.comp_id =" + comp.getId() + " and ";
			}
			if (title != null) {
				sql += "coupon.title like '%" + title + "%' and ";
			}
			if (startDate != null) {
				sql += "coupon.start_date >= '" + startDate + "' and ";
			}
			if (endDate != null) {
				sql += "coupon.end_date <= '" + endDate + "' and ";
			}
			if (amount != -1) {
				sql += "coupon.amount >= " + amount + " and ";
			}
			if (type != null) {
				sql += "type = '" + type + "' and ";
			}
			if (price != -1) {
				sql += "coupon.price <= " + price;
			}
			// corrects the sql command so it won't give error from the additions it got.
			if (sql.substring(sql.length() - 4, sql.length()).equals("and ")) {
				sql = sql.substring(0, sql.length() - 4);
			}

			try (Statement stm = con.createStatement();) {

				ResultSet rs = stm.executeQuery(sql);
				while (rs.next()) {
					coupList.add(new Coupon(rs.getLong(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getInt(5),
							CouponType.valueOf(rs.getString(6)), rs.getString(7), rs.getDouble(8), rs.getString(9)));
				}
			} catch (SQLException e) {
				throw new DatabaseDAOException("Error: Getting coupons by parameters failed. " + e.getMessage());

			} finally {
				pool.returnConnection(con);
			}

			return coupList;
		}
	}

	/**
	 * Retrieves coupons owned by a customer,
	 *  by searching with one more parameters from the database coupon and customer-coupon tables.
	 * 
	 * @param comp is the customer owning the coupons.
	 * @param title is the coupon title.
	 * @param startDate is the coupon start date.
	 * @param endDate is the coupon end date.
	 * @param amount is the amount of such coupons.
	 * @param type is the coupon type.
	 * @param price is the coupon price
	 * @return a list of coupons that were found by the parameters.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public ArrayList<Coupon> getAllOwnedCouponsByParameters(Customer cust, String title, Date startDate, Date endDate,
			Integer amount, CouponType type, Double price) throws CouponSystemException {
		// check if the user sent at least one parameter.
		if (cust == null && title == null && startDate == null && endDate == null && amount == -1 && type == null
				&& price == -1d) {
			throw new CoupSysRuntimeException("Error: No parameters were given.");
		} else {

			Connection con = pool.getConnection();
			String sql = "SELECT coupon.* from customer_coupon right join coupon on customer_coupon.coupon_id = coupon.id where ";
			ArrayList<Coupon> coupList = new ArrayList<>();

			if (cust != null) {
				sql += "customer_coupon.cust_id =" + cust.getId() + " and ";
			}
			if (title != null) {
				sql += "coupon.title like '%" + title + "%' and ";
			}
			if (startDate != null) {
				sql += "coupon.start_date >= '" + startDate + "' and ";
			}
			if (endDate != null) {
				sql += "coupon.end_date <= '" + endDate + "' and ";
			}
			if (amount != -1) {
				sql += "coupon.amount >= " + amount + " and ";
			}
			if (type != null) {
				sql += "type = '" + type + "' and ";
			}
			if (price != -1) {
				sql += "coupon.price <= " + price;
			}
			// corrects the sql command so it won't give error from the additions it got.
			if (sql.substring(sql.length() - 4, sql.length()).equals("and ")) {
				sql = sql.substring(0, sql.length() - 4);
			}

			try (Statement stm = con.createStatement();) {

				ResultSet rs = stm.executeQuery(sql);
				while (rs.next()) {
					coupList.add(new Coupon(rs.getLong(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getInt(5),
							CouponType.valueOf(rs.getString(6)), rs.getString(7), rs.getDouble(8), rs.getString(9)));
				}
			} catch (SQLException e) {
				throw new DatabaseDAOException("Error: Getting coupons by parameters failed. " + e.getMessage());

			} finally {
				pool.returnConnection(con);
			}

			return coupList;
		}
	}
	
	/**
	 * Retrieves coupons owned by a customer,
	 *  by searching with one more parameters from the database coupon and customer-coupon tables.
	 * 
	 * @param comp is the customer owning the coupons.
	 * @param title is the coupon title.
	 * @param startDate is the coupon start date.
	 * @param endDate is the coupon end date.
	 * @param amount is the amount of such coupons.
	 * @param type is the coupon type.
	 * @param price is the coupon price
	 * @return a list of coupons that were found by the parameters.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public ArrayList<Coupon> getAllCouponsByParameters(String title, Date startDate, Date endDate,
			Integer amount, CouponType type, Double price) throws CouponSystemException {
		// check if the user sent at least one parameter.
		if (title == null && startDate == null && endDate == null && amount == -1 && type == null
				&& price == -1d) {
			throw new CoupSysRuntimeException("Error: No parameters were given.");
		} else {
			
			Connection con = pool.getConnection();
			String sql = "SELECT * from  coupon where ";
			ArrayList<Coupon> coupList = new ArrayList<>();
			
			if (title != null) {
				sql += "coupon.title like '%" + title + "%' and ";
			}
			if (startDate != null) {
				sql += "coupon.start_date >= '" + startDate + "' and ";
			}
			if (endDate != null) {
				sql += "coupon.end_date <= '" + endDate + "' and ";
			}
			if (amount != -1) {
				sql += "coupon.amount >= " + amount + " and ";
			}
			if (type != null) {
				sql += "type = '" + type + "' and ";
			}
			if (price != -1) {
				sql += "coupon.price <= " + price;
			}
			// corrects the sql command so it won't give error from the additions it got.
			if (sql.substring(sql.length() - 4, sql.length()).equals("and ")) {
				sql = sql.substring(0, sql.length() - 4);
			}
			
			try (Statement stm = con.createStatement();) {
				
				ResultSet rs = stm.executeQuery(sql);
				while (rs.next()) {
					coupList.add(new Coupon(rs.getLong(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getInt(5),
							CouponType.valueOf(rs.getString(6)), rs.getString(7), rs.getDouble(8), rs.getString(9)));
				}
			} catch (SQLException e) {
				throw new DatabaseDAOException("Error: Getting coupons by parameters failed. " + e.getMessage());
				
			} finally {
				pool.returnConnection(con);
			}
			
			return coupList;
		}
	}

	/**
	 * Retrieves a coupon that is owned by a customer by coupon id
	 * from the database coupon and customer coupon tables.
	 * 
	 * @param id is the coupon id.
	 * @return a coupon that is owned by a customer,
	 * or null if the customer doesn't possess such coupon. 
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public Coupon getCustomersCoupon(long id) throws CouponSystemException {
		Connection con = pool.getConnection();
		String sql = "SELECT coupon.* from customer_coupon right join coupon on customer_coupon.coupon_id = coupon.id where customer_coupon.coupon_id ="
				+ id;
		Coupon c;

		try (Statement stm = con.createStatement();) {
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				c = new Coupon(rs.getLong(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getInt(5),
						CouponType.valueOf(rs.getString(6)), rs.getString(7), rs.getDouble(8), rs.getString(9));
			} else {
				return null;
			}

		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Getting customer's coupons failed. " + e.getMessage());

		} finally {
			pool.returnConnection(con);
		}
		return c;

	}

	/**
	 * Removes the coupon who's end date passed, and is expired,
	 * from the database coupon, customer-coupon and company-coupon tables.
	 * 
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public void deleteExpiredCoupons() throws CouponSystemException {
		Connection con = pool.getConnection();
		String sql = "SELECT * FROM coupon WHERE END_DATE < ?";
		String sql2 = "DELETE FROM customer_coupon WHERE coupon_id = ?";
		String sql3 = "DELETE FROM company_coupon WHERE coupon_id = ?";
		String sql4 = "DELETE FROM coupon WHERE id = ?";

		try (PreparedStatement stm = con.prepareStatement(sql);
				PreparedStatement stm2 = con.prepareStatement(sql2);
				PreparedStatement stm3 = con.prepareStatement(sql3);
				PreparedStatement stm4 = con.prepareStatement(sql4);) {

			stm.setDate(1, new Date(System.currentTimeMillis()));
			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				stm2.setLong(1, rs.getLong(1));
				stm2.executeUpdate();
				stm3.setLong(1, rs.getLong(1));
				stm3.executeUpdate();
				stm4.setLong(1, rs.getLong(1));
				stm4.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Daily expired coupons removal faild" + e.getMessage());
		} finally {
			pool.returnConnection(con);
		}

	}


}
