package connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import exceptions.ConnectionPullException;
import exceptions.CoupSysRuntimeException;
import exceptions.CouponSystemException;

/**
 * The ConnectionPool class is responsible to give access to the Coupon System
 * to the derby database, also this class limits the number of the connections
 * to the database in order to lower the load on it.
 * 
 * @author Pavel
 *
 */
public class ConnectionPool {

	private Set<Connection> pool = new HashSet<Connection>(); // collection of 10 connections.
//	private Set<Connection> usedConnections = new HashSet<Connection>(); // collection of 10 connections.
	private static ConnectionPool instance = null;
	private static final int CON_MAX_NUM = 10;
	private boolean active;

	/**
	 * The construction for this class is private to prevent creation of more
	 * ConnectionPools. Make sure your data base is called CouponSystem1.
	 * 
//	 * @throws CouponSystemException If connection could not be established.
	 * @throws ConnectionPullException 
	 */
	private ConnectionPool() throws ConnectionPullException {
		String url = "jdbc:derby://localhost/CouponSystem1;create=true";
		// adding connections to the pool.
		for (int i = 0; i < CON_MAX_NUM; i++) {
			try {
				Connection c = DriverManager.getConnection(url);
				pool.add(c);
			} catch (SQLException e) {
				throw new ConnectionPullException("No conection to the server!" + e.getMessage());
			}

		}
		this.active = true;

	}
	
	/**
	 * Gives an instance of the ConnectionPool, thus granting access to the pool.
	 * @return the singleton instance of the class
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public static ConnectionPool getInstance() throws CouponSystemException {
		if (instance == null) {
			instance = new ConnectionPool();
		} 
		return instance;
	}

	
	/**
	 * Gets a single connection from the pool.
	 *  In case the pool is empty then the method will wait a connection is available.
	 * @return a single connection from the pool.
	 * @throws CouponSystemException if connection to the database wasn't available or runtime error.
	 */
	public synchronized Connection getConnection() throws CouponSystemException {
		if (!active) {
			throw new CouponSystemException("Cannot get connection, the connection pool is inactive.");
		} else {

			while (pool.isEmpty()) {

				// in case there are no free connections the thread will wait till notified
				try {
					Thread.currentThread().wait();
				} catch (InterruptedException e) {
					throw new CoupSysRuntimeException("Connection pool thread interruption." + e.getMessage());
				}
			}
			Iterator<Connection> iterator = pool.iterator();

			Connection c = iterator.next();
			iterator.remove();

			return c; // returns a connection that is ready to work
		}
	}

	/**
	 * Returns a connection that was taken back to the pool,
	 * then notifies the other threads that are waiting for a connection that a connection is available.
	 * @param c the connection that returns back to the pool.
	 * @throws CouponSystemException in case the pool is full or the ConnectionPool is closed.
	 */
	public synchronized void returnConnection(Connection c) throws CouponSystemException {
		if (!active) {
			throw new ConnectionPullException("Cannot return connection, the connection pool is inactive.");
		} else if (pool.size() == CON_MAX_NUM) {
			throw new ConnectionPullException("Cannot return connection, the connection pool is full.");
		} else {
			pool.add(c);
			notifyAll();
		}

	}
	
	/**
	 * Closes all the connections in the pool, and forcefully closes connections in use.
	 * @throws CouponSystemException on runtime error,
	 * or the connection to the database was impossible to establish.
	 */
	public void closeAllConnections() throws CouponSystemException {
		Iterator<Connection> iterator = pool.iterator();

		for (int i = 0; i < CON_MAX_NUM; i++) {
			while (pool.isEmpty()) {
				try {
					Thread.currentThread().wait();
				} catch (InterruptedException e) {
					throw new CoupSysRuntimeException("shutdown interrupted. " + e.getMessage());
				}
			}
			try {
				Connection c = iterator.next();
				if (c.isClosed()) {
					iterator.remove();

				} else {
					c.close();
					iterator.remove();
				}
			} catch (SQLException e) {
				throw new ConnectionPullException("There are no connections or an active one!" + e.getMessage());

			}
		}

	}

}
