package test;

import java.sql.Date;

import beans.Company;
import beans.Coupon;
import beans.CouponType;
import beans.Customer;
import dataBaseDAO.CompanyDBDAO;
import dataBaseDAO.CouponDBDAO;
import dataBaseDAO.CustomerDBDAO;
import exceptions.CouponSystemException;

public class TestDBDAO {

	public static void main(String[] args) {
		
		Company comp = new Company(12345, "Umbrella", "asd123", "mail@mail.net");
		Company comp1 = new Company(12344, "Universal", "asd123", "mail@mail.ru");
		Coupon coup = new Coupon(1111l, "test1", new Date(System.currentTimeMillis()), new Date((System.currentTimeMillis()+20000)), 1, CouponType.valueOf("FOOD"), "nope", 200d, "no img");
		Coupon coup1 = new Coupon(2222l, "test2", new Date(System.currentTimeMillis()), new Date((System.currentTimeMillis()+20000)), 1, CouponType.valueOf("FOOD"), "nope", 200d, "no img");
		Customer cust = new Customer(888, "John", "asdasd");
		Customer cust1 = new Customer(999, "Jonny", "asd");
//		comp.addCoupon(coup);
		CompanyDBDAO compDBDAO = new CompanyDBDAO();
		CouponDBDAO coupDBDAO = new CouponDBDAO();
		CustomerDBDAO custDBDAO = new CustomerDBDAO();
		System.out.println(coup.getType().name());
		
		
		try {
			custDBDAO.createCustomer(cust);
			custDBDAO.createCustomer(cust1);
			coupDBDAO.createCoupon(coup);
			coupDBDAO.createCoupon(coup1);
			compDBDAO.createCompany(comp);
			compDBDAO.createCompany(comp1);
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			System.out.println(compDBDAO.getAllCompanies());
			System.out.println(coupDBDAO.getAllCoupons());
			System.out.println(custDBDAO.getAllCustomers());
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		comp.setEmail("mail@mail.com");
		coup.setMessage("doge");
		cust.setCustName("Voldemort");
		System.out.println("check");
		try {
			compDBDAO.updateCompany(comp);
			coupDBDAO.updateCoupon(coup);
			custDBDAO.updateCustomer(cust);
			System.out.println(compDBDAO.getCompany(12345));
			System.out.println(coupDBDAO.getCoupon(1111));
			System.out.println(custDBDAO.getCustomer(888));
			System.out.println(compDBDAO.login("Universal", "asd123"));
			System.out.println(compDBDAO.login("Umbrella corp.", "ad123"));
			System.out.println(custDBDAO.login("Jonny", "ad"));
			System.out.println(custDBDAO.login("Voldemort", "asdasd"));
			
			compDBDAO.removeCompany(comp1);
			coupDBDAO.removeCoupon(coup);
			custDBDAO.removeCustomer(cust);
			
			System.out.println(compDBDAO.getAllCompanies());
			System.out.println(coupDBDAO.getAllCoupons());
			System.out.println(custDBDAO.getAllCustomers());
			
			
			coupDBDAO.removeCoupon(coup1);
			custDBDAO.removeCustomer(cust1);
			compDBDAO.removeCompany(comp);
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}
	
}
