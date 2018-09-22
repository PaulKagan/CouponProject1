package facade;

import java.util.ArrayList;

import beans.Company;
import beans.Customer;
import dataBaseDAO.CompanyDBDAO;
import dataBaseDAO.CouponDBDAO;
import dataBaseDAO.CustomerDBDAO;
import exceptions.CouponSystemException;
import exceptions.FacadeException;

public class AdminFacade implements CouponClientFacade {
	
	private CompanyDBDAO compDBDAO;
	private CustomerDBDAO custDBDAO;
	private CouponDBDAO coupDBDAO;

	/**
	 * Constructor for the Admin facade.
	 * 
	 * @throws CouponSystemException if there were issues during the data access objects creation.
	 * 
	 */
	public AdminFacade() throws CouponSystemException {
		compDBDAO = new CompanyDBDAO();
		custDBDAO = new CustomerDBDAO(); 
		coupDBDAO = new CouponDBDAO(); 

	}

	/**
	 * Creates a company in the database.
	 * 
	 * @param comp is the company to be added.
	 * @throws CouponSystemException if there were issues during method runtime,
	 * or if a company with such name already exists.
	 */
	public void createCompany(Company comp) throws CouponSystemException {
		if(compDBDAO.getCompanyByName(comp.getCompName())==null){
			compDBDAO.createCompany(comp);
		}else {
			throw new FacadeException("Error: Name already taken.");
		}
		
		
	}
	
	/**
	 * Removes a company from the database.
	 * 
	 * @param comp is the company to be removed.
	 * @throws CouponSystemException if there were issues during method runtime,
	 * or if no such company was found.
	 */
	public void removeCompany(Company comp) throws CouponSystemException {
		if(compDBDAO.getCompany(comp.getId()) != null) {
			coupDBDAO.removeCustomerCoupons(comp.getCoupons());
			coupDBDAO.removeCoupons(comp.getCoupons());
			coupDBDAO.removeCompanyCoupons(comp.getCoupons());
			compDBDAO.removeCompany(comp);			
		} else {
			throw new FacadeException("Error: no such company exists.");
		}
		
	}
	
	/**
	 * Removes a list of companies from the database.
	 * 
	 * @param companies is the list of companies to be removed.
	 * @throws CouponSystemException if there were issues during method runtime,
	 * or if no such company was found.
	 */
	public void removeCompanies(ArrayList<Company> companies) throws CouponSystemException {
		for (Company comp : companies) {
			if(compDBDAO.getCompany(comp.getId()) != null) {
				coupDBDAO.removeCustomerCoupons(comp.getCoupons());
				coupDBDAO.removeCoupons(comp.getCoupons());
				coupDBDAO.removeCompanyCoupons(comp.getCoupons());
				compDBDAO.removeCompany(comp);			
			} else {
				throw new FacadeException("Error: A company from the list doesn't exists.");
			}	
		}
		
	}
	
	/**
	 * Updates a company in the database.
	 * 
	 * @param comp is the new version of the company that is to be updated.
	 * @throws CouponSystemException if there were issues during method runtime,
	 * or if the name or id were incorrect.
	 */
	public void updateCompany(Company comp) throws CouponSystemException {
		if (compDBDAO.getCompanyByName(comp.getCompName()) != null
				&& compDBDAO.getCompanyByName(comp.getCompName()).getId() == comp.getId()) {
			compDBDAO.updateCompany(comp);
		} else {
			throw new FacadeException("Error: Cannot update Company. Incorrect name.");
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
	 * @throws CouponSystemException if there were issues during method runtime,
	 * or if customer with such name already exists.
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
	 * @throws CouponSystemException if there were issues during method runtime,
	 * or if no such customer was found.
	 */
	public void removeCustomer(Customer cust) throws CouponSystemException {
		if(custDBDAO.getCustomer(cust.getId()) != null) {
			coupDBDAO.removeCustomerCoupons(cust.getCoupons());
			custDBDAO.removeCustomer(cust);
		} else {
			throw new FacadeException("Error: no such customer exists.");
		}
		
		
	}
	/**
	 * Removes a list customers from the database.
	 * 
	 * @param customers is the customer list to be removed.
	 * @throws CouponSystemException if there were issues during method runtime,
	 * or if no such customer was found.
	 */
	public void removeCustomers(ArrayList<Customer> customers) throws CouponSystemException {
		for (Customer cust : customers) {
			if(custDBDAO.getCustomer(cust.getId()) != null) {
				coupDBDAO.removeCustomerCoupons(cust.getCoupons());
				custDBDAO.removeCustomer(cust);
			} else {
				throw new FacadeException("Error: A customer from the list doesn't exists.");
			}	
		}
		
		
	}
	
	/**
	 * Updates a customer in the database.
	 * 
	 * @param cust is the new version of the customer that is to be updated.
	 * @throws CouponSystemException if there were issues during method runtime,
	 * or if the name or id were incorrect.
	 */
	public void updateCustomer(Customer cust) throws CouponSystemException {
		if (custDBDAO.getCustomerByName(cust.getCustName()) != null
				&& custDBDAO.getCustomerByName(cust.getCustName()).getId() == cust.getId()) {
			custDBDAO.updateCustomer(cust);
		} else {
			throw new FacadeException("Error: Cannot update Customer. Incorrect name.");
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
