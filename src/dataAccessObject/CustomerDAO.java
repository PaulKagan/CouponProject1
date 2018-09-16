package dataAccessObject;

import java.util.ArrayList;

import beans.Coupon;
import beans.Customer;
import exceptions.CouponSystemException;

public interface CustomerDAO {
	
	/**
	 * Creation of a single customer in the database customer table.
	 * 
	 * @param c is the customer which entry details will be added.
	 * @throws CouponSystemException if there we issues during method runtime.
	 * 
	 */
	public void createCustomer(Customer c) throws CouponSystemException;
	
	/**
	 * Deletion of a single customer in the database customer table.
	 * 
	 * @param c is the customer which entry details will be removed.
	 * @throws CouponSystemException if there we issues during method runtime.
	 */
	public void removeCustomer(Customer c) throws CouponSystemException;
	
	/**
	 * Updating the details of a single customer in the database customer table.
	 * 
	 * @param c is the customer which entry details will be updated.
	 * @throws CouponSystemException if there we issues during method runtime.
	 */
	public void updateCustomer(Customer c) throws CouponSystemException;
	
	/**
	 * Retrieving a single customer details from the database customer table.
	 * 
	 * @param id is the Id of the customer that will be retrieved.
	 * @return a customer the id belongs to.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public Customer getCustomer(long id) throws CouponSystemException;
	
	/**
	 * Retrieving the details of all the customers from the database customer table.
	 * 
	 * @return array list of all the existing customers.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public ArrayList<Customer> getAllCustomers() throws CouponSystemException;
	
	/**
	 * Retrieving the details of all the customer owned coupons from the database customer-coupon table.
	 * 
	 * @param c is the customer.
	 * @return array list of all the coupons of the customer.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public ArrayList<Coupon> getCoupons(Customer c) throws CouponSystemException;
	
	/**
	 * Enables login by searching and matching the company name and password in the database customer table.
	 * 
	 * @param custName is the customer name to login to.
	 * @param Password is the customer password to login to.
	 * @return a boolean that indicates if the match was successful.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public boolean login(String custName, String Password) throws CouponSystemException;
	
	

}
