package facade;


import dataBaseDAO.CompanyDBDAO;
import dataBaseDAO.CustomerDBDAO;
import exceptions.CouponSystemException;
import exceptions.FacadeException;

public interface CouponClientFacade {
	
	
	/**
	 * The types of clients that exist in the CouponSystem.
	 */
	static public enum ClientType {
		Admin, Customer, Company
	}

	/**
	 * This method is used by client to log into the CouponSystem.
	 * 
	 * @param name is the user name.
	 * @param password is the password.
	 * @param clientType is the client type.
	 * @throws CouponSystemException if the login details are incorrect.
	 */
	public static CouponClientFacade login(String name, String password, ClientType clientType)
			throws CouponSystemException {

		switch (clientType) {
		case Admin:
			if (name.equals("admin") && password.equals("1234"))
				return new AdminFacade();
			break;

		case Company:
			CompanyDBDAO compDBDAO = new CompanyDBDAO();
			if(compDBDAO.login(name, password)) {
				return new CompanyFacade(compDBDAO.getCompanyByName(name));
				
				
			}
			break;

		case Customer:
			CustomerDBDAO custDBDAO = new CustomerDBDAO();
			if(custDBDAO.login(name, password)) {
				return new CustomerFacade(custDBDAO.getCustomerByName(name));				
			}
			break;
		}

		throw new FacadeException("ERROR: Log-In failed. Details are incorrect.");
	}

}
