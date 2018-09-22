package exceptions;

public class ConnectionPullException extends CouponSystemException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a connection pool exception that is unique to the coupon system.
	 * 
	 * @param message is the message that will be shown to the user.
	 */
	public ConnectionPullException(String message) {
		super(message);
	}

}
