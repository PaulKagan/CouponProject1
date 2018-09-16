package beans;

import java.util.ArrayList;


public class Customer {

	private long id;
	private String custName;
	private String password;
	private ArrayList<Coupon> coupons = new ArrayList<Coupon>();

	/**
	 * A constructor for a customer object that has no coupons initially.
	 * It is assumed that no two customer can have the same id or name.
	 * This constructor is made for testing
	 * and creating already existing customer objects from the database.
	 * @param id the customer id.
	 * @param custName
	 * @param password
	 */
	public Customer(long id, String custName, String password) {
		super();
		this.id = id;
		this.custName = custName;
		this.password = password;
	}
	/**
	 * A constructor for a customer object that has no coupons initially.
	 * It is assumed that two customer can't have the same id or name.
	 * This constructor for creating new customer objects for the first time.
	 * @param custName is the customer name.
	 * @param password is the customer password.
	 */
	public Customer(String custName, String password) {
		super();
		this.custName = custName;
		this.password = password;
	}
	
	/**
	 * Retrieves the customer ID.
	 * @return the Id of the customer.
	 */
	public long getId() {
		return id;
	}
	/**
	 * Sets the customer's id.
	 * @param id, is the new id.
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * Retrieves the customer's name.
	 * @return the customer name.
	 */
	public String getCustName() {
		return custName;
	}
	/**
	 * Sets the customer's name.
	 * @param custName, is the new id.
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}
	/**
	 * Retrieves the customer's password.
	 * @return the customer's password.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Sets the customer's password.
	 * @param password, is the new password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * Retrieves all the customer's owned coupon list. 
	 * Initially created empty.
	 * @return collection of the client coupons.
	 */
	public ArrayList<Coupon> getCoupons() {
		return coupons;
	}
	/**
	 * Sets the customer's coupons.
	 * @param coupons, the new customer's coupon list. 
	 */
	 public void setCoupons(ArrayList<Coupon> coupons) {
		 this.coupons = coupons;
	 }
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Customer [id=" + id + ", custName=" + custName + ", password=" + password + "]";
	}

}
