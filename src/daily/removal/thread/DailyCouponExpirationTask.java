package daily.removal.thread;



import java.util.concurrent.TimeUnit;

import dataBaseDAO.CouponDBDAO;
import exceptions.CouponSystemException;

public class DailyCouponExpirationTask implements Runnable {
	
	private CouponDBDAO coupDBDAO = null;
	private boolean quit = false;
	
	
	
	
	/**
	 * Generates a single object to be put into a thread to enable a single start.
	 * 
	 * @throws CouponSystemException if there were issues during the data access objects creation.
	 * 
	 */
	public DailyCouponExpirationTask() throws CouponSystemException {
		super();
		coupDBDAO = new CouponDBDAO();
	}




	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		
		while(!quit) {
			try {
				coupDBDAO.deleteExpiredCoupons();
				System.out.println("Daily Coupon Expiration Task executed.");
//				Thread.sleep(86400000l);
				TimeUnit.MINUTES.sleep(2);//For testing.
			} catch (CouponSystemException e) {
//				e.addSuppressed( new CouponSystemException("Error: Could not perform daily coupon deletion."));
				continue;
			} catch (InterruptedException e) {
				continue;
			}
			
		}
		
		System.out.println("GoodBye!");
		
	}
	
	/**
	 * Stops the work of the daily task thread.
	 */
	public void stopTask() {
		this.quit = true;
	}
	

}
