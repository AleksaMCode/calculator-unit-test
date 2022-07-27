package calculator.exception;

/**
 * Class used to denote {@link Exception} due to usage of an unknown
 * operation/parameter.
 * 
 * @author Aleksa Majkic
 *
 */
@SuppressWarnings("serial")
public class NotSupportedOperationException extends Exception {
	public NotSupportedOperationException(String errorMsg) {
		super(errorMsg);
	}
}