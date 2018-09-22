package test;

import java.sql.Date;

import beans.Coupon;
import beans.CouponType;
import daily.removal.thread.DailyCouponExpirationTask;
import dataBaseDAO.CouponDBDAO;
import exceptions.CouponSystemException;

public class TestDailyCouponDeleteThread {

	public static void main(String[] args) {
		Coupon c = new Coupon("test5", new Date((long) (System.currentTimeMillis()*0.7)), new Date((long) (System.currentTimeMillis() * 0.9)), 1, CouponType.FOOD, "nope", 200d, "no img");
		CouponDBDAO coupDBDAO = null;
		
		try {
			coupDBDAO = new CouponDBDAO();
		} catch (CouponSystemException e) {
			System.out.println("Creation of the coupon data access object has faild." + e.getMessage());
		}
		try {
			coupDBDAO.createCoupon(c);
			System.out.println("Coupon c was created.");
		} catch (CouponSystemException e) {
			System.out.println("Faild to add Coupon 'c'. " + e.getMessage());
		}
		
		try {
			System.out.println("Have to see the example coupon, and coupon 'c'.");
			System.out.println(coupDBDAO.getAllCoupons());
		} catch (CouponSystemException e) {
			System.out.println("Faild to get all coupons. " + e.getMessage());
		}
		
		DailyCouponExpirationTask runnable = null;
		try {
			runnable = new DailyCouponExpirationTask();
		} catch (CouponSystemException e) {
			System.out.println("Creation of the daily deletion task has faild." + e.getMessage());
		}
		Thread t1 = new Thread(runnable,"t1");
		t1.start();
		
		System.out.println("Thread started.");
		
		try {
			System.out.println("Main went to sleep for half a min.");
			Thread.sleep(30000l);
		} catch (InterruptedException e) {
			System.out.println("Main interrupted!" + e.getMessage());
		}
		
		try {
			System.out.println("Now it must get only the example coupon.");
			System.out.println(coupDBDAO.getAllCoupons());
		} catch (CouponSystemException e) {
			System.out.println("Failed to get all coupons. " + e.getMessage());
		}
		
		runnable.stopTask();
	}

}
