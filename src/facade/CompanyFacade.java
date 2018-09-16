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
	
	public CompanyFacade(Company comp){
		compDBDAO = new CompanyDBDAO();
		coupDBDAO = new CouponDBDAO();
		company = comp;
		
	}

	
	
	public void createCoupon(Coupon coup) throws CouponSystemException {
		if(coupDBDAO.getCouponByTitle(coup.getTitle())==null){
			coupDBDAO.createCoupon(coup);
			compDBDAO.addToCompanyCoupon(company, coup);
		}
		
	}
	
	public void removeCoupon(Coupon coup) throws CouponSystemException {
		coupDBDAO.removeCustomerCoupon(coup);
		coupDBDAO.removeCompanyCoupon(coup);
		coupDBDAO.removeCoupon(coup);
		
	}
	
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
	
	public Company getCopanyDetails() throws CouponSystemException {
		return company;
	}
	public ArrayList<Coupon> getCompanyCoupons() throws CouponSystemException{
		return compDBDAO.getCoupons(company);
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
		
		
		return coupDBDAO.getAllCouponsByParameters(company, title, startDate, endDate, amount, type, price);
	}
	
	
	
	
	
	

}
