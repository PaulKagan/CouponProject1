package facade;


import dataBaseDAO.CompanyDBDAO;
import dataBaseDAO.CustomerDBDAO;
import exceptions.CouponSystemException;

public interface CouponClientFacade {
	
	
	
	static public enum ClientType {
		Admin, Customer, Company
	}

	
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

		throw new CouponSystemException("ERROR: Log-In failed. Details are incorrect.");
	}

}
