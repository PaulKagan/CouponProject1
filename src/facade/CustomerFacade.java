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

	public CustomerFacade(Customer cust) {

		custDBDAO = new CustomerDBDAO();
		coupDBDAO = new CouponDBDAO();
		customer = cust;
	}


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

	public ArrayList<Coupon> getAllPurchasedCoupon() throws CouponSystemException {;
		return custDBDAO.getCoupons(customer);
	}

	public ArrayList<Coupon> getAllPurchasedCouponByType(CouponType coupType) throws CouponSystemException {
		return coupDBDAO.getAllCouponsByParameters(customer, null, null, null, -1, coupType, -1d);
	}

	public ArrayList<Coupon> getAllPurchasedCouponByPrice(double price) throws CouponSystemException {
		return coupDBDAO.getAllCouponsByParameters(customer, null, null, null, -1, null, price);
	}
	
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
