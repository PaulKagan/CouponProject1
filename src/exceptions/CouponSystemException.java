package exceptions;

public class CouponSystemException extends Exception {


	private static final long serialVersionUID = 1L;


	/**
     * Constructs an exception that is unique to the coupon system.
     *
     * @param  message is the message that will be shown to the user.
     * 
     */
	public CouponSystemException(String message) {
		super(message);
	}

}
