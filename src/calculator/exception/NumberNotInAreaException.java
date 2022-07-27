package calculator.exception;

/**
 * Class used to denote {@link Exception} due to usage of a number that is out
 * of range.
 * 
 * @author Aleksa Majkic
 *
 */
@SuppressWarnings("serial")
public class NumberNotInAreaException extends Exception {
	public NumberNotInAreaException(String errorMsg) {
		super(errorMsg);
	}
}