package databaseCreation;



import java.sql.Date;

import beans.Company;
import beans.Coupon;
import beans.CouponType;
import beans.Customer;
import dataBaseDAO.CompanyDBDAO;
import dataBaseDAO.CouponDBDAO;
import dataBaseDAO.CustomerDBDAO;
import exceptions.CouponSystemException;

public class CreateExampleObject {

	public static void main(String[] args) {


		Company comp = new Company ("ExampleCompany","example","example@mail.com");
		Coupon coup = new Coupon("ExampleCoupon", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()*5), 1, CouponType.FOOD, "example", 0d, "none");
		Customer cust = new Customer("ExampleCustomer", "example");
		
		try {
			CouponDBDAO coupDBDAO = new CouponDBDAO();
			CompanyDBDAO compDBDAO = new CompanyDBDAO();
			CustomerDBDAO custDBDAO = new CustomerDBDAO();
			
			coupDBDAO.createCoupon(coup);
			compDBDAO.createCompany(comp);
			custDBDAO.createCustomer(cust);
			
			System.out.println(coupDBDAO.getAllCoupons());
			System.out.println(compDBDAO.getAllCompanies());
			System.out.println(custDBDAO.getAllCustomers());
			
			
		} catch (CouponSystemException e) {
			System.out.println("Example objects creation faild.");
			
			e.printStackTrace();
		}
		

	}

}
