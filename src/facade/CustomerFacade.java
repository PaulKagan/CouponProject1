package facade;

import java.sql.Date;
import java.util.ArrayList;

import beans.Coupon;
import beans.CouponType;
import beans.Customer;
import dataBaseDAO.CouponDBDAO;
import dataBaseDAO.CustomerDBDAO;
import exceptions.CouponSystemException;

public class CustomerFacade implements CouponClientFacade {

	CustomerDBDAO custDBDAO;
	CouponDBDAO coupDBDAO;
	Customer customer;

	/**
	 * Constructor for the customer facade.
	 * 
	 * @param cust is the customer the facade associated with.
	 */
	public CustomerFacade(Customer cust) {

		custDBDAO = new CustomerDBDAO();
		coupDBDAO = new CouponDBDAO();
		customer = cust;
	}

	/**
	 * Adds an unpurchased coupon to the customer owned coupons,
	 *  and adds it to the database customer-coupon table.
	 * @param coup is the coupon that is to be purchased.
	 * @throws CouponSystemExceptionif there were issues during method runtime,
	 *  or if the coupon was already in possession.
	 */
	public void purchaseCoupon(Coupon coup) throws CouponSystemException {
		if (coupDBDAO.getCustomersCoupon(coup.getId()) == null) {
			if (coup.getAmount() > 0) {
				custDBDAO.addToCustomerCoupon(customer, coup);
				coup.setAmount(coup.getAmount() - 1);
				coupDBDAO.updateCoupon(coup);
			} else {
				throw new CouponSystemException("Error: Cannot purchase this coupon.");
			}
		} else {
			throw new CouponSystemException("Error: Cannot purchase this coupon again.");
		}

	}

	/**
	 * Retrieves all the owned coupons by the customer.
	 * 
	 * @return a list of all the owned coupons by the customer.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public ArrayList<Coupon> getAllPurchasedCoupon() throws CouponSystemException {;
		return custDBDAO.getCoupons(customer);
	}

	/**
	 * Retrieves all the owned coupons by the customer by type.
	 * @param coupType the type the method will search by.
	 * @return a list of all the owned coupons by the customer that match the type.
	 * @throws CouponSystemException  if there were issues during method runtime.
	 */
	public ArrayList<Coupon> getAllPurchasedCouponByType(CouponType coupType) throws CouponSystemException {
		return coupDBDAO.getAllCouponsByParameters(customer, null, null, null, -1, coupType, -1d);
	}

	/**
	 * Retrieves all the owned coupons by the customer by price.
	 * @param price the type the method will search by.
	 * @return a list of all the owned coupons by the customer that match the price.
	 * @throws CouponSystemException  if there were issues during method runtime.
	 */
	public ArrayList<Coupon> getAllPurchasedCouponByPrice(double price) throws CouponSystemException {
		return coupDBDAO.getAllCouponsByParameters(customer, null, null, null, -1, null, price);
	}
	
	/**
	 * Retrieves all the coupons the customer owns by the parameters that were requested.
	 * 
	 * @param param the parameters that were requested.
	 * @return list of all the coupons the customer owns matching the parameters.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public ArrayList<Coupon> getAllCouponsByParameters(Object... param) throws CouponSystemException {
		String title = null;
		Date startDate = null;
		Date endDate = null;
		int amount = -1;
		CouponType type = null;
		double price = -1D;
		for (Object object : param) {
			if(object instanceof String && title==null) {
				title = (String) object;	
			}else if(object instanceof Date) {
				if(startDate == null) {
					startDate = (Date) object;
				}else if(endDate == null){
					endDate = (Date) object;
					if(startDate.toLocalDate().compareTo(((Date) object).toLocalDate()) > 0) {
						endDate = startDate;
						startDate = (Date) object;
						
					}
				}
			}else if(object instanceof Integer && amount == -1) {
				amount = Integer.parseInt(object.toString());
				
			}else if(object instanceof CouponType && type == null ){
				type = CouponType.valueOf(object.toString());	
			}else if(object instanceof Double && price ==-1) {
				price = Double.parseDouble(object.toString());
			}
			
		}
		
		
		return coupDBDAO.getAllCouponsByParameters(customer, title, startDate, endDate, amount, type, price);
	}
	
	/**
	 * Retrieves all the coupons that are not in possession of the customer. 
	 * 
	 * @return all the coupons that are not purchased by the customer.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public ArrayList<Coupon> getUnpurchasedCoupons() throws CouponSystemException {
		ArrayList<Coupon> notPurchasedCoupons = new ArrayList<Coupon>();
		ArrayList<Coupon> allCoupons = coupDBDAO.getAllCoupons();
		ArrayList<Coupon> customerCoupons = custDBDAO.getCoupons(customer);

		for (Coupon coupon : allCoupons) {
			if (!customerCoupons.contains(coupon)) {
				notPurchasedCoupons.add(coupon);
			}
		}
		return notPurchasedCoupons;
	}
	
	/**
	 * Retrieves all the coupons that are not in possession of the customer by variables.
	 * @param param the parameters the method will search by.
	 * @return all the coupons that are not purchased by the customer, matching the variables.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public ArrayList<Coupon> getUnpurchasedCouponsByVariables(Object... param) throws CouponSystemException {
		String title = null;
		Date startDate = null;
		Date endDate = null;
		int amount = -1;
		CouponType type = null;
		double price = -1D;
		for (Object object : param) {
			if(object instanceof String && title==null) {
				title = (String) object;	
			}else if(object instanceof Date) {
				if(startDate == null) {
					startDate = (Date) object;
				}else if(endDate == null){
					endDate = (Date) object;
					if(startDate.toLocalDate().compareTo(((Date) object).toLocalDate()) > 0) {
						endDate = startDate;
						startDate = (Date) object;
						
					}
				}
			}else if(object instanceof Integer && amount == -1) {
				amount = Integer.parseInt(object.toString());
				
			}else if(object instanceof CouponType && type == null ){
				type = CouponType.valueOf(object.toString());	
			}else if(object instanceof Double && price ==-1) {
				price = Double.parseDouble(object.toString());
			}	
		}
		ArrayList<Coupon> notPurchasedCoupons = new ArrayList<Coupon>();
		ArrayList<Coupon> allCouponsByParameters = coupDBDAO.getAllCouponsByParameters(customer, title, startDate, endDate, amount, type, price);
		ArrayList<Coupon> customerCoupons = custDBDAO.getCoupons(customer);
		
		for (Coupon coupon : allCouponsByParameters) {
			if (!customerCoupons.contains(coupon)) {
				notPurchasedCoupons.add(coupon);
			}
		}
		return notPurchasedCoupons;
	}

}
