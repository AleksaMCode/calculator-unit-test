package calculator;

import calculator.exception.DivisionByZeroException;
import calculator.exception.NotSupportedOperationException;

/**
 * Class used to simulate simple calculator that allows four basic arithmetic
 * operations : addition, subtraction, division and multiplication.
 * 
 * @author Aleksa Majkic
 * @version 1.0.1
 * @since 2021-03-30
 */
public class Calculator {
	/**
	 * Value stored inside of the calculator.
	 */
	/*
	 * Initial value should be 0. Java does this by default so value assigning isn't
	 * necessary in this case.
	 */
	private double currentValue = 0.0d;

	/**
	 * Getter for the private property {@link #currentValue}.
	 * 
	 * @return value of the private property.
	 */
	public double getCurrentValue() {
		return currentValue;
	}

	/**
	 * Setter for the private property {@link #currentValue}
	 * 
	 * @param currentValue is a value used to set calculator's new value.
	 */
	public void setCurrentValue(double currentValue) {
		this.currentValue = currentValue;
	}

	/**
	 * Main method in calculator used to perform simple operation on the stored
	 * value. It allows execution of four available arithmetic operations on the
	 * current value of the calculator.
	 * 
	 * @param value    is a number used to calculate a new value inside of the.
	 *                 calculator by using it as a second operand. It cannot be zero
	 *                 if division is being performed.
	 * @param operator denotes operation being performed on the value inside of the
	 *                 calculator.
	 * @throws NotSupportedOperationException when an unknown operator is used
	 * @throws DivisionByZeroException        when a division by zero is attempted
	 */
	public void calculate(Double value, char operator) throws NotSupportedOperationException, DivisionByZeroException {
		// Check for the devision by zero.
		if (value == 0.0d && operator == '/') {
			throw new DivisionByZeroException();
		}

		switch (operator) {
		case '+':
			currentValue += value;
			break;
		case '-':
			currentValue -= value;
			break;
		case '*':
			currentValue *= value;
			break;
		case '/':
			currentValue /= value;
			break;
		default:
			throw new NotSupportedOperationException("Operator '" + operator + "' isn't defined.");
		}

	}
}