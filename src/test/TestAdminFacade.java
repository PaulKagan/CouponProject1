package test;

import java.util.ArrayList;

import beans.Company;
import beans.Customer;
import couponSystem.CouponSystem;
import exceptions.CouponSystemException;
import facade.AdminFacade;
import facade.CouponClientFacade.ClientType;

public class TestAdminFacade {


	public static void main(String[] args) {
		
		CouponSystem coupSys = null;
		AdminFacade admin = null;
		
		try {
			coupSys = CouponSystem.getInstance();
		} catch (CouponSystemException e) {
			System.out.println("Faild to create the coupon system"  + e.getMessage());
		}
		
		System.out.println("---------Check Log in function.----------");
		System.out.println();
		try {
			admin = (AdminFacade) coupSys.login("admin", "134", ClientType.Admin);
		} catch (CouponSystemException e) {
			System.out.println("faild to log in (should happen)." + e.getMessage());
		}
		try {
			admin = (AdminFacade) coupSys.login("admin", "1234", ClientType.Admin);
			System.out.println("Successful log in.");
		} catch (CouponSystemException e) {
			System.out.println("faild to log in (shouldn't happen)." + e.getMessage());
			
		}
		
		System.out.println();
		System.out.println();
		System.out.println("---------------------------------------------------------------------------------------");
		System.out.println("---------------COMPANY RELATED FUNCTIONS----------------------");
		
		// testing company related methods.
		Company comp = new Company("a", "aa", "a@mail.com");
		Company comp2 = new Company("b", "bb", "b@mail.com");
		Company comp3 = new Company("c", "cc", "c@mail.com");
		Company comp4 = new Company("c", "cc", "c@mail.com");
		
		
		System.out.println();
		System.out.println("---------Check Company Create and Get functions.----------");
		try {
			admin.createCompany(comp);
			admin.createCompany(comp2);
			admin.createCompany(comp3);
			System.out.println("comp to comp 3 were created successfully.");
		} catch (CouponSystemException e) {
			System.out.println("faild to create company 1 to 3" + e.getMessage());
		}
		
		try {
			admin.createCompany(comp4);// must fail.
		} catch (CouponSystemException e) {
			System.out.println("faild to create company 4(has to happen). " + e.getMessage());
		}
		
		try {
			System.out.println(admin.getCompany(comp.getId()));
		} catch (CouponSystemException e) {
			System.out.println("faild to get comp. " + e.getMessage());
		}
		
		try {
			System.out.println(admin.getAllCompanies());
		} catch (CouponSystemException e) {
			System.out.println("faild to get all companies(1-3). " + e.getMessage());
		}
		
		System.out.println();
		System.out.println("---------Check Remove function.----------");
		try {
			admin.removeCompany(comp3);
		} catch (CouponSystemException e) {
			System.out.println("faild to remove company(comp3). " + e.getMessage());
		}
		try {
			System.out.println("Must get null cause searching for comp3, which was deleted.");
			System.out.println(admin.getCompany(comp3.getId()));
		} catch (CouponSystemException e) {
			System.out.println("faild to find the removed company(comp3). " + e.getMessage());
		}
		
		System.out.println();
		System.out.println("---------Check Update function.----------");
		System.out.println("Updating 'comp' password from 'aa' to 'aaa'.");
		comp.setPassword("aaa");
		try {
			admin.updateCompany(comp);
		} catch (CouponSystemException e) {
			System.out.println("faild to update company(comp). " + e.getMessage());
		}
		try {
			System.out.println(admin.getCompany(comp.getId()));
		} catch (CouponSystemException e) {
			System.out.println("faild to get company(comp). " + e.getMessage());	
		}

		System.out.println("Updating 'comp2' name from 'b' to 'a'.");
		comp2.setCompName("a");
		 try {
			admin.updateCompany(comp2);// must fail.
		} catch (CouponSystemException e) {
			System.out.println("faild to update company 'comp2'(has to fail). " + e.getMessage());
		}
		 
		 try {
			System.out.println(admin.getCompany(comp2.getId()));
		} catch (CouponSystemException e) {
			System.out.println("faild to get 'comp2'. " + e.getMessage());
		}
		 System.out.println();
		 System.out.println("Seeing the final result.");
		 try {
			System.out.println(admin.getAllCompanies());//to see the final result.
		} catch (CouponSystemException e) {
			System.out.println("faild to get all companies. " + e.getMessage());
			
		}
		 System.out.println();
		 System.out.println("---------Cleaning the Company table with the Remove companies function.----------");
		 
		 try {
			 ArrayList<Company> companies = new ArrayList<Company>();
			 companies.add(comp);
			 companies.add(comp2);
			admin.removeCompanies(companies);
			System.out.println(admin.getAllCompanies());
			System.out.println("Company table cleaning was successful.");
		} catch (CouponSystemException e) {
			System.out.println("faild to clean companies after test." + e.getMessage());
		}
		
		System.out.println();
		System.out.println();
		System.out.println("---------------------------------------------------------------------------------------");
		System.out.println("---------------CUSTOMER RELATED FUNCTIONS----------------------");
		//testing customer related methods.
		 
		 Customer cust = new Customer("x", "xx");
		 Customer cust2 = new Customer("y", "yy");
		 Customer cust3 = new Customer("z", "zz");
		 Customer cust4 = new Customer("z", "zz");
		 
		 System.out.println();
		 System.out.println("---------Check Customer Create and Get functions.----------");
		 try {
			 
			admin.createCustomer(cust);
			admin.createCustomer(cust2);
			admin.createCustomer(cust3);
			System.out.println("Created cusromers 1-3.");
			
		} catch (CouponSystemException e) {
			System.out.println("faild to create customers 1 to 3. " + e.getMessage());
		}
		 try {
			admin.createCustomer(cust4);//must fail.
		} catch (CouponSystemException e) {
			System.out.println("faild to create customer 4(has to fail). " + e.getMessage());
		}
		
		try {
			System.out.println(admin.getCustomer(cust.getId()));
		} catch (CouponSystemException e) {
			System.out.println("faild to get customer(cust). " + e.getMessage());
		}
		
		try {
			System.out.println(admin.getAllCustomers());
		} catch (CouponSystemException e) {
			System.out.println("faild to get all customers. " + e.getMessage());
			
		}
		
		System.out.println();
		System.out.println("---------Check Remove function.----------");
		try {
			admin.removeCustomer(cust3);
		} catch (CouponSystemException e) {
			System.out.println("faild to remove customer(cust3). " + e.getMessage());
		}
		
		try {
			System.out.println("Must get null cause searching for cust3, which was deleted.");
			System.out.println(admin.getCustomer(cust3.getId()));
		} catch (CouponSystemException e) {
			System.out.println("faild to get the removed customer(cust3). " + e.getMessage());
		}
		
		System.out.println();
		System.out.println("---------Check Update function.----------");
		
		cust.setPassword("xxx");
		try {
			System.out.println("updating 'cust' password from 'xx' to 'xxx'.");
			admin.updateCustomer(cust);
		} catch (CouponSystemException e) {
			System.out.println("faild to update customer(cust). " + e.getMessage());
		}
		
		try {
			System.out.println(admin.getCustomer(cust.getId()));
		} catch (CouponSystemException e) {
			System.out.println("faild to get customer. " + e.getMessage());
		}
		
		cust2.setCustName("x");
		try {
			System.out.println("updating 'cust2' name from 'y' to 'x'.");
			admin.updateCustomer(cust2);//must fail
		} catch (CouponSystemException e) {
		System.out.println("faild to update customer'cust2'(has to fail). " + e.getMessage());
		}
		
		try {
			System.out.println(admin.getCustomer(cust2.getId()));
		} catch (CouponSystemException e) {
			System.out.println("faild to get customer(cust2). " + e.getMessage());
		}
		System.out.println();
		System.out.println("Seeing the final result.");
		try {
			System.out.println(admin.getAllCustomers());//to see the final result
		} catch (CouponSystemException e) {
			System.out.println("faild to get all customers. " + e.getMessage());
		}
		
		System.out.println();
		System.out.println("---------Cleaning the Customer table with the Remove customers function.----------");
		try {
			ArrayList<Customer> customers = new ArrayList<Customer>();
			customers.add(cust);
			customers.add(cust2);
			admin.removeCustomers(customers);
			System.out.println(admin.getAllCustomers());
			System.out.println("Customer table cleaning was successful.");
			
		} catch (CouponSystemException e) {
			System.out.println("faild to remove customers. " + e.getMessage());

		}
		
		
		
		System.out.println();
		System.out.println("---------Checking Coupon System shut down.----------");
		try {
			coupSys.shutdown();
		} catch (CouponSystemException e) {
			System.out.println("faild to shut down the Coupon System. " + e.getMessage());

		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	}

}
