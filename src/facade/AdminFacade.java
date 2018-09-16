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


	public AdminFacade() {
		compDBDAO = new CompanyDBDAO();
		custDBDAO = new CustomerDBDAO(); 
		coupDBDAO = new CouponDBDAO(); 

	}

	
	public void createCompany(Company comp) throws CouponSystemException {
		if(compDBDAO.getCompanyByName(comp.getCompName())==null){
			compDBDAO.createCompany(comp);
		}else {
			throw new CouponSystemException("Error: Name already taken.");
		}
		
		
	}
	
	public void removeCompany(Company comp) throws CouponSystemException {
		coupDBDAO.removeCustomerCoupons(comp.getCoupons());
		coupDBDAO.removeCoupons(comp.getCoupons());
		coupDBDAO.removeCompanyCoupons(comp.getCoupons());
		compDBDAO.removeCompany(comp);		
		
	}
	
	public void updateCompany(Company comp) throws CouponSystemException {
		if (compDBDAO.getCompanyByName(comp.getCompName()) != null
				&& compDBDAO.getCompanyByName(comp.getCompName()).getId() == comp.getId()) {
			compDBDAO.updateCompany(comp);
		} else {
			throw new CouponSystemException("Error: Cannot update Company. Incorrect name.");
		}

	}
	
	public Company getCompany(long id) throws CouponSystemException {
		 return compDBDAO.getCompany(id);
	}
	
	public ArrayList<Company> getAllCompanies() throws CouponSystemException{
		return compDBDAO.getAllCompanies();
	}
	
	public void createCustomer(Customer cust) throws CouponSystemException {
		if(custDBDAO.getCustomerByName(cust.getCustName())==null){
			custDBDAO.createCustomer(cust);
		}else {
			throw new CouponSystemException("Error: Name already taken.");
		}
		
		
	}
	
	public void removeCustomer(Customer cust) throws CouponSystemException {
		coupDBDAO.removeCustomerCoupons(cust.getCoupons());
		custDBDAO.removeCustomer(cust);
		
		
	}
	
	public void updateCustomer(Customer cust) throws CouponSystemException {
		if (custDBDAO.getCustomerByName(cust.getCustName()) != null
				&& custDBDAO.getCustomerByName(cust.getCustName()).getId() == cust.getId()) {
			custDBDAO.updateCustomer(cust);
		} else {
			throw new CouponSystemException("Error: Cannot update Customer. Incorrect name.");
		}

	}
	
	public Customer getCustomer(long id) throws CouponSystemException {
		return custDBDAO.getCustomer(id);
	}
	
	public ArrayList<Customer> getAllCustomers() throws CouponSystemException {
		return custDBDAO.getAllCustomers();
	}
	
}
