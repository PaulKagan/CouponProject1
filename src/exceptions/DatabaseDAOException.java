package exceptions;

public class DatabaseDAOException extends CouponSystemException {

	private static final long serialVersionUID = 1L;

	/**
     * Constructs a database data access object exception that is unique to the coupon system.
     *
     * @param message is the message that will be shown to the user.
     */
	public DatabaseDAOException(String message) {
		super(message);
	}

}
