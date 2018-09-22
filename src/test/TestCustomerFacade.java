package test;

import java.sql.Date;

import beans.Coupon;
import beans.CouponType;
import couponSystem.CouponSystem;
import dataBaseDAO.CouponDBDAO;
import exceptions.CouponSystemException;
import facade.CustomerFacade;
import facade.CouponClientFacade.ClientType;

public class TestCustomerFacade {

	public static void main(String[] args) {

		CouponDBDAO coupDBDAO =null;// for testing.
		CouponSystem coupSys = null;
		CustomerFacade customer = null;
		
		try {
			coupDBDAO =  new CouponDBDAO();
		} catch (CouponSystemException e) {
			System.out.println("Faild to create the coupon data access object" + e.getMessage());
		}
		
		try {
			coupSys = CouponSystem.getInstance();
		} catch (CouponSystemException e) {
			System.out.println("Faild to create the coupon system" + e.getMessage());
		}
		
		System.out.println("---------Check Log in function.----------");
		System.out.println();
		try {
			customer = (CustomerFacade) coupSys.login("ExampleCustomer", "134", ClientType.Customer);
		} catch (CouponSystemException e) {
			System.out.println("Faild to log in(Has to happen). " + e.getMessage());
		}
		try {
			customer = (CustomerFacade) coupSys.login("ExampleCustomer", "example", ClientType.Customer);
			System.out.println("Loged in successfully.");
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			System.out.println("Faild to log in " + e.getMessage());
		}

		Coupon coup = new Coupon("test1", new Date((long) (System.currentTimeMillis() * 0.9)),
				new Date((System.currentTimeMillis() + 10000)), 1, CouponType.FOOD, "nope", 200d, "no img");
		Coupon coup2 = new Coupon("example2", new Date((long) (System.currentTimeMillis() * 1.25)),
				new Date((System.currentTimeMillis() * 2 + 20000)), 2, CouponType.HEALTH, "nope", 250d, "no img");
		Coupon coup3 = new Coupon("test3", new Date((long) (System.currentTimeMillis() * 1.5)),
				new Date((System.currentTimeMillis() * 3 + 30000)), 3, CouponType.ELECTRICITY, "nope", 300d, "no img");

		System.out.println("---------Check Create function.----------");
		System.out.println();
		try {
			coupDBDAO.createCoupon(coup);
			coupDBDAO.createCoupon(coup2);
			coupDBDAO.createCoupon(coup3);
			System.out.println("Coupons were created successfully.");
		} catch (CouponSystemException e) {
			System.out.println("Coupon Creation faild. " + e.getMessage());
		}
		System.out.println();
		System.out.println("---------Check Purchase and Get functions.----------");
		System.out.println();

		try {
			customer.purchaseCoupon(coup);
			customer.purchaseCoupon(coup2);
			customer.purchaseCoupon(coup3);
			System.out.println("Coupon purchase was successful.");
		} catch (CouponSystemException e) {
			System.out.println("Faild to purchase coupons 1-3. " + e.getMessage());
		}

		try {
			System.out.println(customer.getAllPurchasedCoupon());
			System.out.println("All the purchased coupons(1-3)");
		} catch (CouponSystemException e) {
			System.out.println("Faild to get all customers coupons. " + e.getMessage());
		}
		try {
			customer.purchaseCoupon(coup);
			System.out.println("Coupon purchase was successful(not supposed to happen.");
		} catch (CouponSystemException e) {
			System.out.println("Faild to purchase coupon 'coup'(has to fail). " + e.getMessage());
		}

		try {
			System.out.println("Check again if the same coupon was purchase.");
			System.out.println(customer.getAllPurchasedCoupon());
		} catch (CouponSystemException e) {
			System.out.println("Faild to get all customers coupons. " + e.getMessage());
		}

		System.out.println();
		System.out.println("---------Check Get Purchased By- function.----------");
		System.out.println();
		try {
			System.out.println("Must get coup3");
			System.out.println(customer.getAllCouponsByParameters(CouponType.ELECTRICITY));
		} catch (CouponSystemException e) {
			System.out.println("Faild to get coupons by Coupon Type. " + e.getMessage());
		}

		System.out.println("----------------------------------");

		try {
			System.out.println("Must get coup and coup2");
			System.out.println(customer.getAllCouponsByParameters(250d));
		} catch (CouponSystemException e) {
			System.out.println("Faild to get coupons by price. " + e.getMessage());
		}

		System.out.println("----------------------------------");
		System.out.println("---------Check Unpurchased function.----------");
		System.out.println();

		 try {
		 System.out.println("Must get the example coupon.");
		 System.out.println(customer.getUnpurchasedCoupons());
		 } catch (CouponSystemException e) {
		 System.out.println("Faild to get unpurchased coupons" + e.getMessage());
		 }
		 
		 System.out.println("----------------------------------");
		 System.out.println("---------Check Unpurchased By Variables function.----------");
		 System.out.println();
		 
		 try {
			 System.out.println("Must get the example coupon.");
			 System.out.println(customer.getUnpurchasedCouponsByVariables(CouponType.FOOD));
		 } catch (CouponSystemException e) {
			 System.out.println("Faild to get unpurchased coupons by parameters" + e.getMessage());
		 }

		System.out.println(
				"---------Cleaning the Coupon and Customer_Coupon tables with the Remove function for future testing..----------");
		System.out.println();
		try {
			coupDBDAO.removeCoupon(coup);
			coupDBDAO.removeCoupon(coup2);
			coupDBDAO.removeCoupon(coup3);
			coupDBDAO.removeCustomerCoupon(coup);
			coupDBDAO.removeCustomerCoupon(coup2);
			coupDBDAO.removeCustomerCoupon(coup3);
			System.out.println(coupDBDAO.getAllCoupons());
			System.out.println("Removal was successful.");
		} catch (CouponSystemException e) {
			System.out.println("Faild to delete customer coupons. " + e.getMessage());
		}

		System.out.println("---------Check Coupon System shut down.----------");
		System.out.println();
		try {
			coupSys.shutdown();
			System.out.println("Coupon System shuted down successfully.");
		} catch (CouponSystemException e) {
			System.out.println("Faild to shut down the Coupon System. " + e.getMessage());

		}

	}

}
