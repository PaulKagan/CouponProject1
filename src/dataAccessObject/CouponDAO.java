package dataAccessObject;

import java.util.ArrayList;

import beans.Coupon;
import exceptions.CouponSystemException;

public interface CouponDAO {

	/**
	 * Creation of a single coupon in the database coupon table.
	 * 
	 * @param c the coupon that will be added.
	 * @throws CouponSystemException if there was issues during method runtime.
	 * 
	 */
	public void createCoupon(Coupon c) throws CouponSystemException;

	/**
	 * Deletion of a single coupon in the database coupon table.
	 * 
	 * @param c the coupon that will be deleted.
	 * @throws CouponSystemException if there we issues during method runtime.
	 * 
	 */
	public void removeCoupon(Coupon c) throws CouponSystemException;

	/**
	 * Updating of a single coupon entry details in the database coupon table.
	 * 
	 * @param c the coupon that will be updated.
	 * @throws CouponSystemException if there we issues during method runtime.
	 * 
	 */
	public void updateCoupon(Coupon c) throws CouponSystemException;

	/**
	 * Retrieving a single coupon details from the database coupon table.
	 * 
	 * @param id is the Id of the coupon that will be retrieved.
	 * @return a coupon the id belongs to.
	 * @throws CouponSystemException if there were issues during method runtime.
	 * 
	 */
	public Coupon getCoupon(long id) throws CouponSystemException;

	/**
	 * Retrieving the details of all the coupons from the database coupon table.
	 * 
	 * @return array list of all the existing coupons.
	 * @throws CouponSystemException if there were issues during method runtime.
	 * 
	 */
	public ArrayList<Coupon> getAllCoupons() throws CouponSystemException;


}
