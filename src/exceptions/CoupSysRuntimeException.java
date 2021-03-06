package exceptions;

public class CoupSysRuntimeException extends CouponSystemException {

	private static final long serialVersionUID = 1L;

	/**
     * Constructs a runtime exception that is unique to the coupon system.
     *
     * @param message is the message that will be shown to the user.
     */
	public CoupSysRuntimeException(String message) {
		super(message);
	}

}
