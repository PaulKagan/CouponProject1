package facade;

import java.util.ArrayList;

import beans.Company;
import beans.Customer;
import dataBaseDAO.CompanyDBDAO;
import dataBaseDAO.CouponDBDAO;
import dataBaseDAO.CustomerDBDAO;
import exceptions.CouponSystemException;

public class AdminFacade implements CouponClientFacade {
	
	private CompanyDBDAO compDBDAO;
	private CustomerDBDAO custDBDAO;
	private CouponDBDAO coupDBDAO;

	/**
	 * Constructor for the admin facade.
	 * 
	 */
	public AdminFacade() {
		compDBDAO = new CompanyDBDAO();
		custDBDAO = new CustomerDBDAO(); 
		coupDBDAO = new CouponDBDAO(); 

	}

	/**
	 * Creates a company in the database.
	 * 
	 * @param comp is the company to be added.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public void createCompany(Company comp) throws CouponSystemException {
		if(compDBDAO.getCompanyByName(comp.getCompName())==null){
			compDBDAO.createCompany(comp);
		}else {
			throw new CouponSystemException("Error: Name already taken.");
		}
		
		
	}
	
	/**
	 * Removes a company from the database.
	 * 
	 * @param comp is the company to be removed.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public void removeCompany(Company comp) throws CouponSystemException {
		coupDBDAO.removeCustomerCoupons(comp.getCoupons());
		coupDBDAO.removeCoupons(comp.getCoupons());
		coupDBDAO.removeCompanyCoupons(comp.getCoupons());
		compDBDAO.removeCompany(comp);		
		
	}
	
	/**
	 * Updates a company in the database.
	 * 
	 * @param comp is the new version of the company that is to be updated.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public void updateCompany(Company comp) throws CouponSystemException {
		if (compDBDAO.getCompanyByName(comp.getCompName()) != null
				&& compDBDAO.getCompanyByName(comp.getCompName()).getId() == comp.getId()) {
			compDBDAO.updateCompany(comp);
		} else {
			throw new CouponSystemException("Error: Cannot update Company. Incorrect name.");
		}

	}
	
	/**
	 * Retrieves a company from the database by it's id.
	 * 
	 * @param id the Id the method will search by.
	 * @return a company with the id, or null in case a company with such id wasnt found.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public Company getCompany(long id) throws CouponSystemException {
		 return compDBDAO.getCompany(id);
	}
	
	/**
	 * Retrieves all the companies from the database.
	 * 
	 * @return List of all the companies.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public ArrayList<Company> getAllCompanies() throws CouponSystemException{
		return compDBDAO.getAllCompanies();
	}
	
	/**
	 * Creates a customer in the database.
	 * 
	 * @param cust is the customer to be added.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public void createCustomer(Customer cust) throws CouponSystemException {
		if(custDBDAO.getCustomerByName(cust.getCustName())==null){
			custDBDAO.createCustomer(cust);
		}else {
			throw new CouponSystemException("Error: Name already taken.");
		}
		
		
	}
	
	/**
	 * Removes a customer from the database.
	 * 
	 * @param cust is the customer to be removed.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public void removeCustomer(Customer cust) throws CouponSystemException {
		coupDBDAO.removeCustomerCoupons(cust.getCoupons());
		custDBDAO.removeCustomer(cust);
		
		
	}
	
	/**
	 * Updates a customer in the database.
	 * 
	 * @param cust is the new version of the customer that is to be updated.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public void updateCustomer(Customer cust) throws CouponSystemException {
		if (custDBDAO.getCustomerByName(cust.getCustName()) != null
				&& custDBDAO.getCustomerByName(cust.getCustName()).getId() == cust.getId()) {
			custDBDAO.updateCustomer(cust);
		} else {
			throw new CouponSystemException("Error: Cannot update Customer. Incorrect name.");
		}

	}
	
	/**
	 * Retrieves a customer from the database by it's id.
	 * 
	 * @param id the Id the method will search by.
	 * @return a customer with the id, or null in case a customer with such id wasnt found.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public Customer getCustomer(long id) throws CouponSystemException {
		return custDBDAO.getCustomer(id);
	}
	
	/**
	 * Retrieves all the customers from the database.
	 * 
	 * @return List of all the customers.
	 * @throws CouponSystemException if there were issues during method runtime.
	 */
	public ArrayList<Customer> getAllCustomers() throws CouponSystemException {
		return custDBDAO.getAllCustomers();
	}
	
}
