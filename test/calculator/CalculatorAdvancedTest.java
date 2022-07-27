package calculator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import calculator.exception.NotSupportedOperationException;
import calculator.exception.NumberNotInAreaException;

/**
 * Testing class used to fully test the code in the {@link CalculatorAdvanced}
 * class. It used parameterized test and Hamcrest matchers.
 * 
 * @author Aleksa Majkic
 * @version 1.0.1
 * @since 2021-03-30
 *
 */
@DisplayName("CalculatorAdvanced class test")
class CalculatorAdvancedTest {

	/**
	 * CalculatorAdvanced object that is used in all test methods.
	 */
	private CalculatorAdvanced calculator = new CalculatorAdvanced();

	/**
	 * Test the reference for the {@link CalculatorAdvanced} object once before the
	 * entire test fixture.
	 */
	@BeforeEach
	public void beforeEach() {
		assertThat(calculator, notNullValue(CalculatorAdvanced.class));
	}

	/**
	 * Test the initial value stored in the {@link CalculatorAdvanced} class.
	 */
	@Test
	void testGetCurrentValue() {
		assertThat(calculator.getCurrentValue(), is(Double.valueOf(0.0d)));
	}

	/**
	 * Method generates a stream of arguments (argument objects) for testing
	 * {@link CalculatorAdvanced#hasCharacteristic(char) hasCharacteristic(char)}
	 * method with a method {@link #testHasCharacteristic(double, char, Boolean)}.
	 * 
	 * @return {@link Stream} of {@link Arguments} objects.
	 */
	private static Stream<Arguments> calculateAdvancedParameters() {
		return Stream.of(Arguments.of(0.0, '0', 1.0), Arguments.of(0.0, '1', 0.0), Arguments.of(0.0, '9', 0.0),
				Arguments.of(26_598.0, '0', 1.0), Arguments.of(-5.0, '1', -5.0), Arguments.of(-1.0, '3', -1.0),
				Arguments.of(-1.0, '4', 1.0), Arguments.of(-5.5659852, '4', 625.0),

				Arguments.of(0.0, '!', 1.0), Arguments.of(1.0, '!', 1.0), Arguments.of(5.0, '!', 120.0),
				Arguments.of(5.9659, '!', 120.0), Arguments.of(10.0, '!', 3_628_800.0));
	}

	/**
	 * Tests the factorial and exponentiation calculation for the
	 * {@link CalculatorAdvanced#calculateAdvanced(char) calculateAdvanced(char)}
	 * method, parameterized to take parameters from
	 * {@link #calculateAdvancedParameters()} method.
	 * 
	 * @param currentValue is a value that will be stored as a calculators current
	 *                     value.
	 * @param action       is a value used to distinguish between factorial and
	 *                     exponentiation calculation.
	 * @param result       is the expected result for the calculation.
	 * @throws NotSupportedOperationException
	 * @throws NumberNotInAreaException
	 */
	@ParameterizedTest
	@MethodSource("calculateAdvancedParameters")
	public void testCalculateAdvanced(double currentValue, char action, double result)
			throws NotSupportedOperationException, NumberNotInAreaException {
		calculator.setCurrentValue(currentValue);
		calculator.calculateAdvanced(action);
		assertThat(calculator.getCurrentValue(), is(result));
	}

	/**
	 * Method generates a stream of arguments (argument objects) for testing
	 * {@link CalculatorAdvanced#hasCharacteristic(char) hasCharacteristic(char)}
	 * method with a method {@link #testHasCharacteristic(double, char, Boolean)}.
	 * 
	 * @return {@link Stream} of {@link Arguments} objects.
	 */
	private static Stream<Arguments> hasCharacteristicParameters() {
		return Stream.of(Arguments.of(370.0, 'A', true), Arguments.of(371.0, 'A', true),
				Arguments.of(370.59659, 'A', true), Arguments.of(10.0, 'A', false), Arguments.of(1.5, 'A', true),

				Arguments.of(6.0, 'P', true), Arguments.of(28.0, 'P', true), Arguments.of(30.0, 'P', false),
				Arguments.of(8_128.0156, 'P', true), Arguments.of(1.0, 'P', false));
	}

	/**
	 * Tests the {@link CalculatorAdvanced#hasCharacteristic(char)
	 * hasCharacteristic(char)} method by checking if the given numbers are
	 * Armstrong or Perfect, parameterized to take parameters from
	 * {@link #hasCharacteristicParameters()} method.
	 * 
	 * @param currentValue is a value that will be stored as a calculators current
	 *                     value.
	 * @param parameter    is a value used to distinguish between Armstrong or
	 *                     Perfect number check.
	 * @param result       is the expected result for the calculation.
	 * @throws NotSupportedOperationException
	 * @throws NumberNotInAreaException
	 */
	@ParameterizedTest
	@MethodSource("hasCharacteristicParameters")
	public void testHasCharacteristic(double currentValue, char parameter, Boolean result)
			throws NotSupportedOperationException, NumberNotInAreaException {
		calculator.setCurrentValue(currentValue);
		assertThat(calculator.hasCharacteristic(parameter), is(result));
	}

	/**
	 * Tests all of the invalid cases for the
	 * {@link CalculatorAdvanced#calculateAdvanced(char) calculateAdvanced(char)}
	 * method when calculating Factorial.
	 */
	@Test
	public void testFactorialInvalidCases() {
		char action = '!';
		calculator.setCurrentValue(10.00016);
		assertThrows(NumberNotInAreaException.class, () -> calculator.calculateAdvanced(action));
		calculator.setCurrentValue(-0.00095);
		assertThrows(NumberNotInAreaException.class, () -> calculator.calculateAdvanced(action));
	}

	/**
	 * Tests all of the invalid case for the
	 * {@link CalculatorAdvanced#calculateAdvanced(char) calculateAdvanced(char)}
	 * method when using invalid action.
	 */
	@Test
	public void testCalculateAdvancedInvalidCases() {
		char action = 'A';
		assertThrows(NotSupportedOperationException.class, () -> calculator.calculateAdvanced(action));
	}

	/**
	 * Tests all of the invalid cases for the
	 * {@link CalculatorAdvanced#hasCharacteristic(char) hasCharacteristic(char)}
	 * method.
	 */
	@Test
	public void testHasCharacteristicInvalidCases() {
		char parameter = '|';
		calculator.setCurrentValue(26.55);
		assertThrows(NotSupportedOperationException.class, () -> calculator.hasCharacteristic(parameter));
		calculator.setCurrentValue(0.265296526);
		Exception e = assertThrows(NumberNotInAreaException.class, () -> calculator.hasCharacteristic(parameter));
		assertThat(e.getMessage(), containsString("can't be smaller than 1"));
	}
}
