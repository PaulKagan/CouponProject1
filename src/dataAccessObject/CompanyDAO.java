package dataAccessObject;

import java.util.ArrayList;

import beans.Company;
import beans.Coupon;
import exceptions.CouponSystemException;

public interface CompanyDAO {

	/**
	 * Creation of a single company in the database company table.
	 * 
	 * @param c the company that will be added.
	 * @throws CouponSystemException if there was issues during method runtime.
	 * 
	 */
	public void createCompany(Company c) throws CouponSystemException;

	/**
	 * Deletion of a single company in the database company table.
	 * 
	 * @param c the company that will be deleted.
	 * @throws CouponSystemException if there we issues during method runtime.
	 * 
	 */
	public void removeCompany(Company c) throws CouponSystemException;

	/**
	 * Updating of a single company entry details in the database company table.
	 * 
	 * @param c the company that will be updated.
	 * @throws CouponSystemException if there we issues during method runtime.
	 * 
	 */
	public void updateCompany(Company c) throws CouponSystemException;

	/**
	 * Retrieving a single company details from the database company table.
	 * 
	 * @param id is the Id of the company that will be retrieved.
	 * @return a company the id belongs to.
	 * @throws CouponSystemException if there were issues during method runtime.
	 * 
	 */
	public Company getCompany(long id) throws CouponSystemException;
	
	/**
	 * Retrieving the details of all the companies from the database company table.
	 * 
	 * @return array list of all the existing companies.
	 * @throws CouponSystemException if there were issues during method runtime.
	 * 
	 */
	public ArrayList<Company> getAllCompanies() throws CouponSystemException;
	
	/**
	 * Retrieving the details of all the company owned coupons from the database company-coupon table.
	 * 
	 * @param c is the company.
	 * @return array list of all the coupons of the company.
	 * @throws CouponSystemException if there were issues during method runtime.
	 * 
	 */
	public ArrayList<Coupon> getCoupons(Company c) throws CouponSystemException;
	
	/**
	 * Enables login by searching and matching the company name and password in the database company table.
	 * 
	 * @param compName the company name to login to.
	 * @param password the company password to login to.
	 * @return a boolean that indicates if the match was successful.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public boolean login(String compName, String password) throws CouponSystemException;
	
	
	
	

}
