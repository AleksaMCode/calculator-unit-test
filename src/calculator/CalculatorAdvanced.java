package calculator;

import calculator.exception.NotSupportedOperationException;
import calculator.exception.NumberNotInAreaException;

/**
 * Class used to simulate simple calculator that allows exponentiation and
 * factorial operations, Armstrong and Perfect number inquiry in addition to the
 * four basic arithmetic operations inherited from the class {@link Calculator}.
 * 
 * @author Aleksa Majkic
 * @version 1.0.1
 * @since 2021-03-30
 */
public class CalculatorAdvanced extends Calculator {
	/***
	 * Method allows user to calculate the factorial for the floor value of the
	 * current calculator value or to calculate exponentiation of the current
	 * calculator value to the power of the number {@code action} that is in range
	 * [0, 9].
	 * 
	 * @param action parameter used to determine which calculation is going to be
	 *               conducted. If the value is '!' then the numbers factorial is
	 *               going to be calculated; for this calculation the current
	 *               calculator value has to be in range [0, 9] otherwise
	 *               {@link NumberNotInAreaException} will be thrown. If the value
	 *               is numerical character in the range [0, 9] then the
	 *               exponentiation of the calculator value will be calculated and
	 *               stored as a new value for the calculator. Other values will
	 *               cause the method to throw an
	 *               {@link NotSupportedOperationException}.
	 * @throws NumberNotInAreaException       when an action isn't a valid operator,
	 *                                        '!' or a numerical charter in range
	 *                                        [0, 9].
	 * @throws NotSupportedOperationException when the operation is set on factorial
	 *                                        but the internal value of the
	 *                                        calculator is not in range [0, 9].
	 */
	public void calculateAdvanced(char action) throws NumberNotInAreaException, NotSupportedOperationException {
		double currentValue = getCurrentValue();
		int integerValue = mathFloor(currentValue);

		if (action >= '0' && action <= '9') {
			setCurrentValue(mathPower(integerValue, action - '0'));
		} else if (action == '!') {
			if (currentValue >= 0.0d && currentValue <= 10.0d) {
				setCurrentValue(factorial(integerValue));
			} else {
				throw new NumberNotInAreaException("Number '" + String.format("%.2f", getCurrentValue())
						+ "' can't be used to calculate a factorial beacause it's not in a range [0, 10].");
			}
		} else {
			throw new NotSupportedOperationException("Action '" + action + "' isn't supported.");
		}
	}

	/**
	 * Method checks if the stored value inside of the calculator is Armstrong or
	 * Perfect number. For both calculations floor value of the calculator value has
	 * to be equal to or greater than 1, otherwise {@link NumberNotInAreaException}
	 * will be thrown.
	 * 
	 * @param value is parameter used to distinguish between Armstrong or Perfect
	 *              number check.
	 * @return true if the current calculator value has the requested property,
	 *         otherwise false.
	 * @throws NumberNotInAreaException       when a floor value of
	 *                                        {@link #currentValue} is smaller than
	 *                                        1
	 * @throws NotSupportedOperationException when an unknown parameter
	 *                                        {@code value} is used
	 */
	public Boolean hasCharacteristic(char value) throws NumberNotInAreaException, NotSupportedOperationException {
		int integerValue = mathFloor(getCurrentValue());

		if (integerValue < 1) {
			throw new NumberNotInAreaException(
					"Integer part of current value (" + String.format("%.2f", getCurrentValue())
							+ ") can't be smaller than 1 when calculating Armstrong or Perfect number.");
		}

		switch (value) {
		case 'A':
			return armstrongNumber(integerValue);
		case 'P':
			return perfectNumber(integerValue);
		default:
			throw new NotSupportedOperationException("Parameter '" + value + "' isn't supported.");
		}
	}

	/**
	 * Helper method that checks if a number is perfect.
	 * 
	 * @param number is a value for which the property <i>perfect</i> is checked.
	 * @return true if the number is perfect, otherwise false.
	 */
	private Boolean perfectNumber(int number) {
		int sum = 0;
		for (int i = 1; i < number; i++) {
			if (number % i == 0) {
				sum += i;
			}
		}
		return sum == number;
	}

	/**
	 * Helper method that checks if a number is an <i>Armstrong</i> number.
	 * 
	 * @param number is a value for which the property <i>Armstrong</i> is checked.
	 * @return true if the number is <i>Armstrong</i>, otherwise false.
	 */
	private Boolean armstrongNumber(int number) {
		int result = 0;
		int remainder = 0;
		int numOfDigits = numberOfDigits(number);

		for (int originalNum = number; originalNum != 0; originalNum /= 10) {
			remainder = originalNum % 10;
			result += mathPower(remainder, numOfDigits);
		}

		return number == result;
	}

	/**
	 * Helper method used to calculate number of digits of a given number.
	 * 
	 * @param number is a value used to calculate number of digits.
	 * @return number of digits of the given value.
	 */
	private int numberOfDigits(int number) {
		int numOfDigits = 0;

		for (; number != 0; numOfDigits++, number /= 10)
			;

		return numOfDigits;
	}

	/**
	 * Helper method that takes as input a real number and gives as output the
	 * greatest integer less than or equal to number.
	 * 
	 * @param number is the value whose floor value is calculated.
	 * @return greatest integer less than or equal to input number.
	 */
	private int mathFloor(double number) {
		return (int) number;
	}

	/**
	 * Helper method that computes power of base to the exponent by <i>Binary
	 * Decomposition</i> of exponent.
	 * 
	 * @param base     is a number whose power is calculated.
	 * @param exponent is a number that corresponds to the number of times the base
	 *                 is used as a factor.
	 * @return calculated power of a given base value.
	 */
	private int mathPower(int base, int exponent) {
		/*
		 * if (base == 0 && exponent == 0) { return 1; }
		 */

		// This operation is optimal for all exponent < 15 according to Henry
		// Warren in his book "Hacker's Delight".
		int result = 1;

		while (true) {
			// If exponent is odd, multiply by base.
			if ((exponent & 1) != 0) {
				result = base * result;
			}

			// Position next bit of n.
			exponent = exponent >> 1;

			// If there are no more bits in exponent.
			if (exponent == 0) {
				return result;
			}

			// Power for the next bit of n.
			base *= base;
		}
	}

	/**
	 * Helper method that recursively calculates a factorial.
	 * 
	 * @param number is a value whose factorial is calculated.
	 * @return factorial of the given number.
	 */
	private int factorial(int number) {
		return (number == 0 || number == 1) ? 1 : number * factorial(number - 1);
	}
}