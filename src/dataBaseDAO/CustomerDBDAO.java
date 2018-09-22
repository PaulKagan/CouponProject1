package dataBaseDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import beans.Coupon;
import beans.CouponType;
import beans.Customer;
import connections.ConnectionPool;
import dataAccessObject.CustomerDAO;
import exceptions.CouponSystemException;
import exceptions.DatabaseDAOException;

public class CustomerDBDAO implements CustomerDAO {

	ConnectionPool pool;

	/**
	 * Constructor of the customer database data access object, which grants access to the C.R.U.D commands.
	 * 
	 * @throws CouponSystemException if there were issues during the connection pool creation.
	 */
	public CustomerDBDAO() throws CouponSystemException {
		super();
		pool = ConnectionPool.getInstance();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createCustomer(Customer c) throws CouponSystemException {
		Connection con = pool.getConnection();
		String sql = "INSERT INTO Customer (cust_name,password) VALUES(?,?)";

		try (PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, c.getCustName());
			stmt.setString(2, c.getPassword());
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			long custId = rs.getLong(1);
			c.setId(custId);
			
		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Customer creation failed. " + e.getMessage());
		}finally {
			pool.returnConnection(con);			
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeCustomer(Customer c) throws CouponSystemException {
		Connection con = pool.getConnection();
		String sql = "DELETE FROM customer WHERE id=" + c.getId();
		
		try (Statement stm = con.createStatement();) {
			stm.execute(sql);
		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Customer deletion failed. " + e.getMessage());
		}finally {
			pool.returnConnection(con);			
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateCustomer(Customer c) throws CouponSystemException {
		Connection con = pool.getConnection();
		String sql = "UPDATE customer SET Cust_NAME = ?, PASSWORD = ? WHERE ID = ?";
		
		try (PreparedStatement stm = con.prepareStatement(sql);) {
			stm.setString(1, c.getCustName());
			stm.setString(2, c.getPassword());
			stm.setLong(3, c.getId());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Customer update failed. " + e.getMessage());
		}finally {
			pool.returnConnection(con);			
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Customer getCustomer(long id) throws CouponSystemException {
		Connection con = pool.getConnection();
		String sql = "SELECT * FROM customer where ID=" + id;
		Customer c = null;
		
		try (Statement stm = con.createStatement();) {
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				c = new Customer(rs.getLong(1), rs.getString(2), rs.getString(3));
			}
		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Getting customer failed. " + e.getMessage());
		}finally {
			pool.returnConnection(con);			
		}
		return c;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Customer> getAllCustomers() throws CouponSystemException {
		Connection con = pool.getConnection();
		String sql = "select * from customer";
		ArrayList<Customer> list = new ArrayList<>();
		
		try (Statement stm = con.createStatement();) {
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				list.add(new Customer(rs.getLong(1), rs.getString(2), rs.getString(3)));
			}

		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Getting all customers failed. " + e.getMessage());
		}finally {
			pool.returnConnection(con);			
		}
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Coupon> getCoupons(Customer c) throws CouponSystemException {
		Connection con = pool.getConnection();
		String sql = "SELECT coupon.* from customer_coupon right join coupon on customer_coupon.coupon_id = coupon.id where customer_coupon.cust_id ="
				+ c.getId();
		ArrayList<Coupon> list = new ArrayList<>();
		
		try (Statement stm = con.createStatement();) {
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				list.add(new Coupon(rs.getLong(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getInt(5),
						CouponType.valueOf(rs.getString(6)), rs.getString(7), rs.getDouble(8), rs.getString(9)));
			}

		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Getting customer's coupons failed. " + e.getMessage());
		}finally {
			pool.returnConnection(con);			
		}
		return list;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean login(String custName, String password) throws CouponSystemException {
		Connection con = pool.getConnection();
		String sql = "Select * from customer where Cust_NAME=" + "'" + custName + "'" + " and password=" + "'"
				+ password + "'";
		
		try (Statement stm = con.createStatement()) {
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Login failed. " + e.getMessage());
		} finally {
			pool.returnConnection(con);
		}
		return false;
	}
	
	/**
	 * Retrieving a single customer details from the database customer table by name.
	 * 
	 * @param name is the customer name the method will search for.
	 * @return a customer that was found by name, or null if one wasn't found.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public Customer getCustomerByName(String name) throws CouponSystemException {
		Connection con = pool.getConnection();
		String sql = "SELECT * FROM customer where Cust_name='" + name + "'";
		Customer c = null;
		
		try (Statement stm = con.createStatement();) {
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				c = new Customer(rs.getLong(1), rs.getString(2), rs.getString(3));
			}else {
				return null;
			}
		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Getting company failed. " + e.getMessage());
		} finally {
			pool.returnConnection(con);
		}
		return c;
	}

	/**
	 * Creates an entry of the customer and it's coupon in the database customer-coupon table.
	 * 
	 * @param comp is the customer.
	 * @param coup is the customer owned coupon.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public void addToCustomerCoupon(Customer cust, Coupon coup) throws CouponSystemException {
		Connection con = pool.getConnection();
		String sql = "INSERT INTO Customer_coupon VALUES(?,?)";

		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setLong(1, cust.getId());
			stmt.setLong(2, coup.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Customer creation failed. " + e.getMessage());
		} finally {
			pool.returnConnection(con);
		}

	}
	


}
