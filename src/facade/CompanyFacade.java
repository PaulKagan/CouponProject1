package facade;

import java.sql.Date;
import java.util.ArrayList;
import beans.Company;
import beans.Coupon;
import beans.CouponType;
import dataBaseDAO.CompanyDBDAO;
import dataBaseDAO.CouponDBDAO;
import exceptions.CouponSystemException;

public class CompanyFacade implements CouponClientFacade {
	
	private CompanyDBDAO compDBDAO;
	private CouponDBDAO coupDBDAO;
	Company company;
	
	/**
	 * Constructor for the company facade.
	 * 
	 * @param comp is the company the facade associated with.
	 */
	public CompanyFacade(Company comp){
		compDBDAO = new CompanyDBDAO();
		coupDBDAO = new CouponDBDAO();
		company = comp;
		
	}

	
	/**
	 * Creates a new coupon for the company in the database coupon and company-coupon tables.
	 * Only if the title isn't different
	 * @param coup is the coupon to be added.
	 * @throws CouponSystemException if there were issues during method runtime or if the name was already taken.
	 */
	public void createCoupon(Coupon coup) throws CouponSystemException {
		if(coupDBDAO.getCouponByTitle(coup.getTitle())==null){
			coupDBDAO.createCoupon(coup);
			compDBDAO.addToCompanyCoupon(company, coup);
		}else {
			throw new CouponSystemException("Coupon title is already taken.");
		}
		
	}
	
	/**
	 * Removes a coupon of the company from the database coupon, customer-coupon and company-coupon tables.
	 * 
	 * @param coup is the coupon to be removed.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public void removeCoupon(Coupon coup) throws CouponSystemException {
		coupDBDAO.removeCustomerCoupon(coup);
		coupDBDAO.removeCompanyCoupon(coup);
		coupDBDAO.removeCoupon(coup);
		
	}
	
	/**
	 * Updates the coupon of the company in the database coupon table.
	 * Only if the title, start date, amount, type, message and image weren't touched.
	 *   
	 * @param coup is the coupon that is to be updated.
	 * @throws CouponSystemException if there were issues during method runtime or the conditions for the update weren't met.
	 */
	public void updateCoupon(Coupon coup) throws CouponSystemException {
		Coupon c = coupDBDAO.getCoupon(coup.getId());
		if(c.getTitle().equals(coup.getTitle()) && c.getStartDate().toLocalDate().compareTo(coup.getStartDate().toLocalDate()) == 0
				&& c.getAmount() == coup.getAmount() && c.getType() == coup.getType()
				&& c.getMessage().equals(coup.getMessage()) && c.getImage().equals(coup.getImage())){			
			
			coupDBDAO.updateCoupon(coup);
		}else {
			throw new CouponSystemException("Error: Cannot update coupon.");
		}
		
	}
	
	/**
	 * Retrieves the company details.
	 * 
	 * @return the company the facade is associated with.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public Company getCopanyDetails() throws CouponSystemException {
		return company;
	}
	
	/**
	 * Retrieves all the coupons the company owns.
	 * @return list of all the coupons the company owns.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public ArrayList<Coupon> getCompanyCoupons() throws CouponSystemException{
		return compDBDAO.getCoupons(company);
	}
	
	/**
	 * Retrieves all the coupons the company owns by the parameters that were requested.
	 * 
	 * @param param the parameters that were requested.
	 * @return list of all the coupons the company owns matching the parameters.
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
		
		
		return coupDBDAO.getAllCouponsByParameters(company, title, startDate, endDate, amount, type, price);
	}
	
	
	
	
	
	

}
