package couponSystem;

import connections.ConnectionPool;
import daily.removal.thread.DailyCouponExpirationTask;
import exceptions.CouponSystemException;
import facade.CouponClientFacade;
import facade.CouponClientFacade.ClientType;

/**
 * Gives access to the whole system, and manages its availability.
 * @author Pavel
 *
 */
public class CouponSystem {
	
	private static CouponSystem instance = new CouponSystem();
	private DailyCouponExpirationTask DCET;
	
	/**
	 * Constructor for the Coupon System.
	 * Starts the daily deletion of expired coupons.
	 * The construction for this class is private to prevent creation of more
	 * coupon systems.
	 */
	private CouponSystem() {
		this.DCET = new DailyCouponExpirationTask();
		Thread t1 = new Thread(DCET, "Daily coupon deletion thread");
		t1.start();
		
		
	}

	/**
	 * Gives an instance of the CouponSystem, thus granting access to the system.
	 * @return the singleton instance of the class
	 */
	public static CouponSystem getInstance() {
		return instance;
	}
	
	/**
	 * Gives access to the facade, according to the client type.
	 * @param name
	 * @param password
	 * @param clientType
	 * @return
	 * @throws CouponSystemException
	 */
	public CouponClientFacade login(String name, String password, ClientType clientType) throws CouponSystemException {
		CouponClientFacade facade;
		try {
			facade = CouponClientFacade.login(name, password, clientType);
		} catch (CouponSystemException e) {
			throw new CouponSystemException("Error: Could not login.");
		}
		return facade;
	}
	/**
	 * Shuts down the coupon system.
	 * @throws CouponSystemException in case the shutdown has failed.
	 */
	public void shutdown() throws CouponSystemException {
		try {
			ConnectionPool.getInstance().closeAllConnections();
			DCET.stopTask();
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			throw new CouponSystemException("Error: Coupon System shutdown has failed." + e.getMessage());
		}

		
		
	}

	
	
	

}
