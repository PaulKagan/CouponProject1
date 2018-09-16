package beans;

import java.sql.Date;


public class Coupon {




	private long id;
	private String title;
	private Date startDate;
	private Date endDate;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String image;

	/**
	 * Constructs a new coupon object.
	 * It is assumed that no two coupons can have the same id or name.
	 * This constructor is made for testing
	 * and creating already existing coupon objects from the database.
	 * @param id is the coupon id.
	 * @param title is the coupon title .
	 * @param startDate is the coupon start date.
	 * @param endDate is the coupon end date.
	 * @param amount is the amount of such coupons available.
	 * @param type is the coupon type.
	 * @param message is the coupon description.
	 * @param price is the coupon price.
	 * @param image is the coupon image.
	 */
	public Coupon(long id, String title, Date startDate, Date endDate, int amount, CouponType type, String message,
			double price, String image) {
		super();
		this.id = id;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.type = type;
		this.message = message;
		this.price = price;
		this.image = image;
	}
	/**
	 * Constructs a new coupon object.
	 * It is assumed that no two coupons can have the same id or name.
	 * This constructor is made for creating new coupons for the first time.
	 * @param title is the coupon title .
	 * @param startDate is the coupon start date.
	 * @param endDate is the coupon end date.
	 * @param amount is the amount of such coupons available.
	 * @param type is the coupon type.
	 * @param message is the coupon description.
	 * @param price is the coupon price.
	 * @param image is the coupon image.
	 */
	public Coupon(String title, Date startDate, Date endDate, int amount, CouponType type, String message,
			double price, String image) {
		super();
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.type = type;
		this.message = message;
		this.price = price;
		this.image = image;
	}
	
	/**
	 * Retrieves the coupon id.
	 * @return the coupon id.
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Changes the coupon id.
	 * @param id the new coupon id.
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * Retrieves the coupon title.
	 * @return the coupon title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Changes the coupon title.
	 * @param title the new coupon title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Retrieves the coupon start date.
	 * @return the coupon start date.
	 */
	public Date getStartDate() {
		return startDate;
	}
	
	/**
	 * Changes the coupon start date.
	 * @param startDate the new coupon start date.
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * Retrieves the coupon end date.
	 * @return the coupon end date.
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Changes the coupon end date.
	 * @param endDate the new coupon end date.
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * Retrieves the coupon amount.
	 * @return the coupon amount.
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Changes the coupon amount.
	 * @param amount the new coupon amount.
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	/**
	 * Retrieves the coupon type.
	 * @return the coupon type.
	 */
	public CouponType getType() {
		return type;
	}

	/**
	 * Changes the coupon type.
	 * @param type the new coupon type.
	 */
	public void setType(CouponType type) {
		this.type = type;
	}
	
	/**
	 * Retrieves the coupon description message.
	 * @return the coupon description message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Changes the coupon description message.
	 * @param message the new coupon description message.
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Retrieves the coupon price.
	 * @return the coupon price.
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Changes the coupon price.
	 * @param price the new coupon price.
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	/**
	 * Retrieves the coupon image.
	 * @return the coupon image.
	 */
	public String getImage() {
		return image;
	}

	/**
	 * Changes the coupon image.
	 * @param image the new coupon image.
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", amount=" + amount + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
				+ image + "]";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coupon other = (Coupon) obj;
		if (amount != other.amount)
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (id != other.id)
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}
