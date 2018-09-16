package beans;

import java.util.ArrayList;


public class Company {
	
	
	private long id;
	private String compName;
	private String password;
	private String email;
	private ArrayList<Coupon> coupons = new ArrayList<Coupon>();

	/**
	 * Constructs a company object that initially has no coupons.
	 * It is assumed that no two companies can have the same id or name.
	 * This constructor is made for testing
	 * and creating already existing company objects from the database.
	 * @param id is the company id.
	 * @param compName is the company name.
	 * @param password is the company password.
	 * @param email is the company email.
	 */
	public Company(long id, String compName, String password, String email) {
		super();
		this.id = id;
		this.compName = compName;
		this.password = password;
		this.email = email;
	}
	/**
	 * Constructs a company object that initially has no coupons.
	 * It is assumed that no two companies can have the same id or name.
	 * This constructor is made for creating new company for the first time.
	 * @param compName is the company name.
	 * @param password is the company password.
	 * @param email is the company email.
	 */
	public Company(String compName, String password, String email) {
		super();
		this.compName = compName;
		this.password = password;
		this.email = email;
	}
	
	/**
	 * Retrieves the company id.
	 * @return the company id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * Changes the company id.
	 * @param id the new company id.
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * Retrieves the company name.
	 * @return the company name.
	 */
	public String getCompName() {
		return compName;
	}
	/**
	 * Changes the company name.
	 * @param compName the new company name.
	 */
	public void setCompName(String compName) {
		this.compName = compName;
	}
	/**
	 * Retrieves the company password.
	 * @return the company password.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Changes the company password.
	 * @param password the new company password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * Retrieves the company email.
	 * @return the company email.
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Changes the company email.
	 * @param email the new company email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * Retrieves the company coupon list.
	 * @return the company coupon list.
	 */
	public ArrayList<Coupon> getCoupons() {
		return coupons;
	}
	/**
	 * Adds a single new coupon to the company's coupon list.
	 * @param c is the new coupon.
	 */
	public void addCoupon(Coupon c) {
		coupons.add(c);
	}
	/**
	 * Changes the company coupon list.
	 * @param coupons the new company coupon list.
	 */
	public void setCoupons(ArrayList<Coupon> coupons) {
		this.coupons = coupons;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Company [id=" + id + ", compName=" + compName + ", password=" + password + ", email=" + email + "]";
	}
	
	
	
	

}
