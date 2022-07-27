package calculator.exception;

/**
 * Class used to denote {@link Exception} due to division by zero. This
 * exception that is thrown when division with zero is detected in one of the
 * Calculator classes.
 * 
 * @author Aleksa Majkic
 *
 */
@SuppressWarnings("serial")
public class DivisionByZeroException extends Exception {
	public DivisionByZeroException() {
		super("Division with zero isn't permitted.");
	}
}