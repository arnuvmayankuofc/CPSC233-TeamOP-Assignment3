package model;

import java.lang.Exception;

/**
 * Handles invalid data related to a poll in TextApplication and PollTrackerApp.
 * {@code PollFullException} is a subclass of {@code Exception}
 * 
 * @author Michaela Kasongo
 * @since 1.0
 *
 */
public class PollFullException extends Exception {

	/**
	 * Constructs new PollFullException with a null message. The cause is not initialized,
	 * and may subsequently be initialized by a call to {@link #initCause}.
	 */
	public PollFullException() {
		super();
	}

	/**
	 * A new PollFullException with specified message. The cause is
	 * not initialized, and may subsequently be initialized by a call to
	 * {@link #initCause}.
	 *
	 * @param message the detail message. The detail message is saved for later
	 *                retrieval by the {@link #getMessage()} method.
	 */
	public PollFullException(String message) {
		super(message);
	}

	/**
	 * Constructs a new PollFullException with the specified detail message and cause.
	 * <p>
	 * Note that the detail message associated with {@code cause} is <i>not</i>
	 * automatically incorporated in this exception's detail message.
	 *
	 * @param message the detail message (which is saved for later retrieval by the
	 *                {@link #getMessage()} method).
	 * @param cause   the cause (which is saved for later retrieval by the
	 *                {@link #getCause()} method). (A {@code null} value is
	 *                permitted, and indicates that the cause is nonexistent or
	 *                unknown.)
	 * @since 1.4
	 */
	public PollFullException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new PollFullException with the specified cause and a detail message of
	 * {@code (cause==null ? null : cause.toString())} (which typically contains the
	 * class and detail message of {@code cause}). This constructor is useful for
	 * exceptions that are little more than wrappers for other throwables (for
	 * example, {@link java.security.PrivilegedActionException}).
	 *
	 * @param cause the cause (which is saved for later retrieval by the
	 *              {@link #getCause()} method). (A {@code null} value is permitted,
	 *              and indicates that the cause is nonexistent or unknown.)
	 * @since 1.4
	 */
	public PollFullException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new PollFullException with the specified detail message, cause,
	 * suppression enabled or disabled, and writable stack trace enabled or
	 * disabled.
	 *
	 * @param message            the detail message.
	 * @param cause              the cause. (A {@code null} value is permitted, and
	 *                           indicates that the cause is nonexistent or
	 *                           unknown.)
	 * @param enableSuppression  whether or not suppression is enabled or disabled
	 * @param writableStackTrace whether or not the stack trace should be writable
	 * @since 1.7
	 */
	protected PollFullException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
