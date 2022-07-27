package calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import calculator.exception.DivisionByZeroException;
import calculator.exception.NotSupportedOperationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

/**
 * Testing class used to fully test the code in the {@link Calculator} class. It
 * used parameterized test and Hamcrest matchers.
 * 
 * @author Aleksa Majkic
 * @version 1.0.1
 * @since 2021-03-30
 *
 */
@DisplayName("Calculator class test")
class CalculatorTest {

	/**
	 * Calculator object that is used in all test methods.
	 */
	private Calculator calculator = new Calculator();

	/**
	 * Delta value used to approx. value stored inside of the <@link Calculator>
	 * class after the numerical operation with tolerance up to 8 decimals.
	 */
	private final static Double DELTA = 0.00000001;

	/**
	 * Test the reference for the {@link Calculator} object once before the entire
	 * test fixture.
	 */
	@BeforeEach
	public void beforeEach() {
		assertThat(calculator, notNullValue(Calculator.class));
	}

	/**
	 * Test the initial value stored in the {@link Calculator} class.
	 */
	@Test
	@Order(1)
	@DisplayName("Tests the initial value of Calculator.")
	public void testGetCurrentValue() {
		assertThat(calculator.getCurrentValue(), is(Double.valueOf(0.0d)));
	}

	/**
	 * Tests the {@link Calculator} class setter.
	 */
	@Test
	@DisplayName("Tests the setter for Calculator.")
	public void testSetCurrentValue() {
		double value = 2.986d;
		calculator.setCurrentValue(value);
		assertThat(calculator.getCurrentValue(), is(value));
	}

	/**
	 * Method generates a stream of arguments (argument objects) for testing
	 * arithmetic operations.
	 * 
	 * @return {@link Stream} of {@link Arguments} objects.
	 */
	private static Stream<Arguments> calculateParameters() {
		return Stream.of(Arguments.of(0.0, 0.0, '+', 0.0), Arguments.of(0.0, 65.5, '+', 65.5),
				Arguments.of(126.65, 0.0, '+', 126.65), Arguments.of(0.0, -17.0, '+', -17.0),
				Arguments.of(-56.2, 0.0, '+', -56.2), Arguments.of(-28.0, -5.0, '+', -33.0),
				Arguments.of(-15.0, 3.0, '+', -12.0), Arguments.of(15.0, -3.0, '+', 12.0),
				Arguments.of(-2.0, -7.0, '+', -9.0), Arguments.of(365.0, 42.0, '+', 407.0),
				Arguments.of(45.0, -45.0, '+', 0.0),

				Arguments.of(0.0, 0.0, '-', 0.0), Arguments.of(0.0, 65.5, '-', -65.5),
				Arguments.of(126.65, 0.0, '-', 126.65), Arguments.of(0.0, -17.0, '-', 17.0),
				Arguments.of(-56.2, 0.0, '-', -56.2), Arguments.of(-28.0, -5.0, '-', -23.0),
				Arguments.of(-15.0, 3.0, '-', -18.0), Arguments.of(15.0, -3.0, '-', 18.0),
				Arguments.of(-2.0, -7.0, '-', 5.0), Arguments.of(365.0, 42.0, '-', 323.0),
				Arguments.of(45.0, 45.0, '-', 0.0),

				Arguments.of(0.0, 0.0, '*', 0.0), Arguments.of(0.0, 65.5, '*', 0.0),
				Arguments.of(126.65, 0.0, '*', 0.0), Arguments.of(0.0, -17.0, '*', 0.0),
				Arguments.of(-56.2, 0.0, '*', 0.0), Arguments.of(-28.0, -5.0, '*', 140.0),
				Arguments.of(-15.0, 3.0, '*', -45.0), Arguments.of(15.598, -3.235, '*', -50.45953),
				Arguments.of(-2.0, -7.0, '*', 14.0), Arguments.of(365.059, 0.15468048, '*', 56.46750134),
				Arguments.of(45.0, -45.0, '*', -2_025.0),

				Arguments.of(0.0, 65.5, '/', 0.0), Arguments.of(0.0, -17.0, '/', 0.0),
				Arguments.of(-28.0, -5.0, '/', 5.6), Arguments.of(-15.0, 3.0, '/', -5.0),
				Arguments.of(15.0, -3.0, '/', -5.0), Arguments.of(-2.0, -7.0, '/', 0.28571428),
				Arguments.of(365.0, 42.0, '/', 8.69047619), Arguments.of(45.0, -45.0, '/', -1.0));
	}

	/**
	 * Tests the main method from the {@link Calculator} class by performing 4
	 * different operations on number of different operands, parameterized to take
	 * parameters from {@link #calculateParameters()} method.
	 * 
	 * @param currentValue is a value that will be stored as a first operand.
	 * @param value        is used as a second operand.
	 * @param operator     is used to denote which arithmetics operation will be
	 *                     performed inside of the <@link Calculator> class.
	 * @param result       is the expected result for the calculation.
	 * @throws DivisionByZeroException
	 * @throws NotSupportedOperationException
	 */
	@ParameterizedTest
	@MethodSource("calculateParameters")
	public void testCalculate(double currentValue, Double value, char operator, double result)
			throws DivisionByZeroException, NotSupportedOperationException {
		calculator.setCurrentValue(currentValue);
		calculator.calculate(value, operator);
		assertThat(calculator.getCurrentValue(), is(closeTo(result, DELTA)));
	}

	/**
	 * Tests the invalid operator input {@link Calculator#calculate(Double, char)
	 * calculate(Double, char)} method.
	 */
	@Test
	public void testInvalidOperatorParameter() {
		char operator = '^';
		Double value = 11.26d;
		Exception e = assertThrows(NotSupportedOperationException.class, () -> calculator.calculate(value, operator));
		assertThat(e.getMessage(), containsString("Operator"));
	}

	/**
	 * Tests the division by zero for {@link Calculator#calculate(Double, char)
	 * calculate(Double, char)} method.
	 */
	@Test
	public void testDivisionByZero() {
		Double value = 0.0d;
		char operator = '/';
		Exception e = assertThrows(DivisionByZeroException.class, () -> calculator.calculate(value, operator));
		assertThat(e, is(instanceOf(DivisionByZeroException.class)));
	}
}