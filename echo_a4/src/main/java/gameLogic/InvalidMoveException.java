/**
 * echo
 * 20201034
 */
package gameLogic;

/**
 * @author agbod
 *
 */
public class InvalidMoveException extends RuntimeException { //Exception thrown to signify illegal moves

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidMoveException() {
		super();
	}

	/**
	 * @param message
	 */
	public InvalidMoveException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public InvalidMoveException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidMoveException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public InvalidMoveException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
