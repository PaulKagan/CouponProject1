package test;

import java.sql.Date;

import beans.Coupon;
import beans.CouponType;
import couponSystem.CouponSystem;
import exceptions.CouponSystemException;
import facade.CompanyFacade;
import facade.CouponClientFacade.ClientType;

public class TestCompanyFacade {


	public static void main(String[] args) {
		
		CouponSystem coupSys = CouponSystem.getInstance();
		CompanyFacade company = null;
		
		System.out.println("---------Check Log in function.----------");
		System.out.println();
		try {
			company = (CompanyFacade) coupSys.login("ExampleCompany", "134", ClientType.Company);
		} catch (CouponSystemException e) {
			System.out.println("Faild to log in(Has to happen). " + e.getMessage());
		}
		try {
			company = (CompanyFacade) coupSys.login("ExampleCompany", "example", ClientType.Company);
			System.out.println("Loged in successfully.");
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			System.out.println("Faild to log in. " + e.getMessage());
		}
		
		
		
		Coupon coup = new Coupon(1, "test1", new Date((long) (System.currentTimeMillis()*0.9)), new Date((System.currentTimeMillis() + 10000)), 1, CouponType.FOOD, "nope", 200d, "no img");
		Coupon coup2 = new Coupon(2, "example2", new Date((long) (System.currentTimeMillis() * 1.25)), new Date((System.currentTimeMillis() * 2 + 20000)), 2, CouponType.HEALTH, "nope", 250d, "no img");
		Coupon coup3 = new Coupon(3, "test3", new Date((long) (System.currentTimeMillis() * 1.5)), new Date((System.currentTimeMillis() * 3 + 30000)), 3, CouponType.ELECTRICITY, "nope", 300d, "no img");
		Coupon coup4 = new Coupon(4, "test3", new Date(System.currentTimeMillis() * 2), new Date((System.currentTimeMillis() * 4 + 40000)), 4, CouponType.valueOf("FOOD"), "nope", 200d, "no img");

		System.out.println("---------Check Create function.----------");
		System.out.println();
		
		try {
			company.createCoupon(coup);
			company.createCoupon(coup2);
			company.createCoupon(coup3);
			System.out.println("Created coupons 1 to 3");
		} catch (CouponSystemException e) {
			System.out.println("Faild to create coupons 1 to 3. " + e.getMessage());
		}
		try {
			company.createCoupon(coup4);
		} catch (CouponSystemException e) {
			System.out.println("Faild to create coupon 4 (has to fail). " + e.getMessage());
		}
		
		
		try {
			System.out.println(company.getCompanyCoupons());
		} catch (CouponSystemException e) {
			System.out.println("Faild to get all coupons. " + e.getMessage());
		}
		
		System.out.println("---------Check Update function.----------");
		System.out.println();
		
		coup.setPrice(999);
		
		try {
			company.updateCoupon(coup);
			System.out.println("Updated 'coup'.");
		} catch (CouponSystemException e) {
			System.out.println("Faild to update 'coup'. " + e.getMessage());
		}
		coup2.setStartDate(new Date ((long) (System.currentTimeMillis() * 1.5)));
		try {
			company.updateCoupon(coup2);
		} catch (CouponSystemException e) {
			System.out.println("Faild to update coupon 2 (has to fail). " + e.getMessage());
		}
		
		System.out.println("---------Check Get functions.----------");
		System.out.println();
		try {
			System.out.println(company.getCompanyCoupons());
		} catch (CouponSystemException e) {
			System.out.println("faild to get all coupons. " + e.getMessage());
		}
		
		try {
			System.out.println(company.getCopanyDetails());
		} catch (CouponSystemException e) {
			System.out.println("Faild to get company details. " + e.getMessage());
		}
		
		System.out.println("---------Check Get coupons by parameters function.----------");
		System.out.println();
		try {
			System.out.println("*SHOULD GET ALL COMPANY COUPONS*");
			System.out.println(company.getAllCouponsByParameters());
		} catch (CouponSystemException e) {
			System.out.println("Faild to get coupons without parameters. " +e.getMessage());
		}
		System.out.println("----------------------------------");
		try {
			System.out.println("*SHOULD GET ALL COMPANY COUPONS WITH 'TEST' IN THE TITLE(1 & 3)*");
			System.out.println(company.getAllCouponsByParameters("test"));
		} catch (CouponSystemException e) {
			System.out.println("Faild to get coupons with 'test' in title. " +e.getMessage());
		}
		System.out.println("----------------------------------");
		System.out.println("*SHOULD GET ALL COMPANY COUPONS WITH LATER DATE THAN, AND AT THE GIVEN DATE*");
		try {
			System.out.println("Given Date: " + new Date(System.currentTimeMillis()+10000));
			System.out.println(company.getAllCouponsByParameters(new Date((System.currentTimeMillis()+10000))));
		} catch (CouponSystemException e) {
			System.out.println("Faild to get coupons with later start date. " +e.getMessage());
		}
		
		System.out.println("----------------------------------");
		System.out.println("*SHOULD GET ALL COMPANY COUPONS WITH LATER OR EQUAL START DATE TO THE FIRST DATE AND END DATE BRFORE OR EQUAL TO THE SECOND DATE*");
		try {
			System.out.println("Given Dates: " + new Date(System.currentTimeMillis()) + " and, " + new Date((System.currentTimeMillis()* 2 + 25000)));
			System.out.println(company.getAllCouponsByParameters(new Date(System.currentTimeMillis()),new Date((System.currentTimeMillis()* 2 + 25000))));
		} catch (CouponSystemException e) {
			System.out.println("Faild to get valid coupons between the 2 dates. " +e.getMessage());
		}
		System.out.println("----------------------------------");
		System.out.println("*SHOULD GET ALL COMPANY COUPONS WITH MORE DUPLICATES THAN THE GIVEN AMOUNT*");
		try {
			System.out.println(company.getAllCouponsByParameters((int)2));
		} catch (CouponSystemException e) {
			System.out.println("Faild to get coupons with given amount. " +e.getMessage());
		}
		System.out.println("----------------------------------");
		System.out.println("*SHOULD GET ALL COMPANY COUPONS WITH THE SPECIFIED TYPE*");
		try {
			System.out.println(company.getAllCouponsByParameters(CouponType.HEALTH));
		} catch (CouponSystemException e) {
			System.out.println("Faild to get coupons with Coupon Type. " +e.getMessage());
		}
		System.out.println("----------------------------------");
		System.out.println("*SHOULD GET ALL COMPANY COUPONS CHEAPER THAN THE GIVEN PRICE*");
		try {
			System.out.println(company.getAllCouponsByParameters(300d));
		} catch (CouponSystemException e) {
			System.out.println("Faild to get ceaper coupons. " +e.getMessage());
		}
		
		System.out.println("---------Cleaning the table with the Remove function for future testing.----------");
		System.out.println();
		
		try {
			company.removeCoupon(coup);
			company.removeCoupon(coup2);
			company.removeCoupon(coup3);
			System.out.println("The Companies were removed successfully.");
		} catch (CouponSystemException e) {
			System.out.println("Faild to delete company 1-3. ");
		}
		
		try {
			System.out.println("company coupons after deletion.");
			System.out.println(company.getCompanyCoupons());
		} catch (CouponSystemException e) {
			System.out.println("Faild to get all coupons. " + e.getMessage());
		}
		
		System.out.println("---------Check Coupon System shut down function.----------");
		System.out.println();
		
		try {
			coupSys.shutdown();
			System.out.println("Coupon System shuted down successfully.");
		} catch (CouponSystemException e) {
			System.out.println("Faild to shut down the Coupon System. " + e.getMessage());
		}
		
		
		
		
		

	}

}
