package dataBaseDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import beans.Company;
import beans.Coupon;
import beans.CouponType;
import connections.ConnectionPool;
import dataAccessObject.CompanyDAO;
import exceptions.CouponSystemException;
import exceptions.DatabaseDAOException;

public class CompanyDBDAO implements CompanyDAO {

	ConnectionPool pool;

	/**
	 * Constructor of the company database data access object, which grants access to the C.R.U.D commands.
	 * @throws CouponSystemException if there were issues during the connection pool creation.
	 */
	public CompanyDBDAO() throws CouponSystemException {
		super();
		pool = ConnectionPool.getInstance();
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createCompany(Company c) throws CouponSystemException {
		Connection con = pool.getConnection();
		String sql = "INSERT INTO company(comp_name,password,email) VALUES(?,?,?)";

		try (PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, c.getCompName());
			stmt.setString(2, c.getPassword());
			stmt.setString(3, c.getEmail());
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			long compId = rs.getLong(1);
			c.setId(compId);
			
		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Company creation failed. " + e.getMessage());
		} finally {
			pool.returnConnection(con);

		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeCompany(Company c) throws CouponSystemException {
		Connection con = pool.getConnection();
		String sql = "DELETE FROM Company WHERE id=" + c.getId();

		try (Statement stm = con.createStatement();) {
			stm.execute(sql);
		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Company removal failed. " + e.getMessage());
		} finally {
			pool.returnConnection(con);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateCompany(Company c) throws CouponSystemException {
		Connection con = pool.getConnection();
		String sql = "UPDATE company SET Comp_Name = ?, PASSWORD = ?, EMAIL = ? WHERE ID = ?";

		try (PreparedStatement stm = con.prepareStatement(sql);) {
			stm.setString(1, c.getCompName());
			stm.setString(2, c.getPassword());
			stm.setString(3, c.getEmail());
			stm.setLong(4, c.getId());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Company update failed. " + e.getMessage());
		} finally {
			pool.returnConnection(con);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Company getCompany(long id) throws CouponSystemException {
		Connection con = pool.getConnection();
		String sql = "SELECT * FROM COMPANY where ID=" + id;
		Company c = null;

		try (Statement stm = con.createStatement();) {
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				c = new Company(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4));
			}
		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Getting company failed. " + e.getMessage());
		} finally {
			pool.returnConnection(con);
		}
		return c;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Company> getAllCompanies() throws CouponSystemException {
		Connection con = pool.getConnection();
		String sql = "SELECT * FROM COMPANY";
		ArrayList<Company> compList = new ArrayList<>();

		try (Statement stm = con.createStatement();) {
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				compList.add(new Company(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4)));
			}
		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Getting all companies failed. " + e.getMessage());
		} finally {
			pool.returnConnection(con);
		}

		return compList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Coupon> getCoupons(Company c) throws CouponSystemException {
		Connection con = pool.getConnection();
		String sql = "SELECT coupon.* from COMPANY_COUPON right join coupon on COMPANY_COUPON.coupon_id = coupon.id where COMPANY_COUPON.comp_id ="
				+ c.getId();
		ArrayList<Coupon> list = new ArrayList<>();

		try (Statement stm = con.createStatement();) {
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				list.add(new Coupon(rs.getLong(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getInt(5),
						CouponType.valueOf(rs.getString(6)), rs.getString(7), rs.getDouble(8), rs.getString(9)));
			}
		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Getting company's coupons failed. " + e.getMessage());
		} finally {
			pool.returnConnection(con);
		}
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean login(String compName, String password) throws CouponSystemException {// return company or customer.
		Connection con = pool.getConnection();
		String sql = "Select * from COMPANY where COMP_NAME=" + "'" + compName + "'" + " and password='" + password
				+ "'";

		try (Statement stm = con.createStatement()) {
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Company login failed. " + e.getMessage());
		} finally {
			pool.returnConnection(con);
		}
		return false;
	}

	/**
	 * Retrieving a single company details from the database company table by name.
	 * 
	 * @param name is the company name the method will search for.
	 * @return a company that was found by name, or null if one wasn't found.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public Company getCompanyByName(String name) throws CouponSystemException {
		Connection con = pool.getConnection();
		String sql = "SELECT * FROM COMPANY where Comp_name='" + name + "'";
		Company c = null;

		try (Statement stm = con.createStatement();) {
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				c = new Company(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4));
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
	 * Creates an entry of the company and it's coupon in the database company-coupon table.
	 * 
	 * @param comp is the company.
	 * @param coup is the company owned coupon.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public void addToCompanyCoupon(Company comp, Coupon coup) throws CouponSystemException {
		String sql = "INSERT INTO company_coupon VALUES(?,?)";
		Connection con = pool.getConnection();

		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setLong(1, comp.getId());
			stmt.setLong(2, coup.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseDAOException("Error: Adding to company coupon failed. " + e.getMessage());
		} finally {
			pool.returnConnection(con);

		}

	}

}
