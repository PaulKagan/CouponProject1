package exceptions;

public class FacadeException extends CouponSystemException {

	private static final long serialVersionUID = 1L;

	/**
     * Constructs a facade exception that is unique to the coupon system.
     *
     * @param message is the message that will be shown to the user.
     */
	public FacadeException(String message) {
		super(message);
	}

}
