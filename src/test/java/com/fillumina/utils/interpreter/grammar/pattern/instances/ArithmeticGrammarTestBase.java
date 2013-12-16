package com.fillumina.utils.interpreter.grammar.pattern.instances;

import com.fillumina.utils.interpreter.Calculator;
import com.fillumina.utils.interpreter.SyntaxErrorException;
import com.fillumina.utils.interpreter.treebuilder.ParenthesisMismatchedException;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class ArithmeticGrammarTestBase {

    private final Map<String, Double> context = new HashMap<>();
    private Calculator<Double, Map<String, Double>> calculator;

    public abstract Calculator<Double, Map<String, Double>> getCalculator();

    @Before
    public void init() {
        calculator = getCalculator();
    }

    protected void assertEvaluateTo(final double expected,
            final String expression) {
        final Double result = calculate(expression);
        assertEquals("\"" + expression + "\"", expected, result, 1E-7);
    }

    public Double calculate(final String expression) {
        return calculator.solve(expression, context).get(0);
    }

    @Test
    public void shouldRecognizeASingleDigit() {
        assertEvaluateTo(2, "2");
    }

    @Test
    public void shouldRecognizeASimpleNumber() {
        assertEvaluateTo(123, "123");
    }

    @Test
    public void shouldRecognizeARealNumber() {
        assertEvaluateTo(123.45, "123.45");
    }

    @Test
    public void shouldRecognizeARealNumberInScientificNotation() {
        assertEvaluateTo(12345, "123.45E2");
    }

    @Test
    public void shouldRecognizeARealNumberInScientificNotationWithNegativeExp() {
        assertEvaluateTo(1.2345, "123.45E-2");
    }

    @Test
    public void shouldRecognizeARealNumberInScientificNotationWithPositiveExp() {
        assertEvaluateTo(12345, "123.45E+2");
    }

    @Test
    public void shouldRecognizeARealNegativeNumberInScientificNotationWithPositiveExp() {
        assertEvaluateTo(-12345, "-123.45E+2");
    }

    @Test
    public void shouldSolveAnAddition() {
        assertEvaluateTo(12, "10+2");
    }

    @Test
    public void shouldSolveAnAdditionAndASubtraction() {
        assertEvaluateTo(8, "10+2-4");
    }

    @Test
    public void shouldIgnoreWhiteSpaces() {
        assertEvaluateTo(8, " 10+ 2 -  4 ");
    }

    @Test
    public void shouldSolveOperatorsWithDifferentPriority() {
        assertEvaluateTo(18, "10+2*4");
    }

    @Test
    public void shouldSolveComplexOperatorsWithDifferentPriority() {
        assertEvaluateTo(18, "10+4^2/2");
    }

    @Test
    public void shouldSolveAConstant() {
        assertEvaluateTo(Math.PI, "pi");
    }

    @Test
    public void shouldSolveAConstantAndAnOperator() {
        assertEvaluateTo(Math.PI / 4, "pi/4");
    }

    @Test
    public void shouldSolveAFunction() {
        assertEvaluateTo(1, "sin(pi/2)");
    }

    @Test
    public void shouldManageNestedParenthesis() {
        assertEvaluateTo(0, "2 - ((8 + 4) / (2 * 3))");
    }

    public void shouldManageAnEmptyParenthesis() {
        assertEvaluateTo(-1, "2 - ()3");
    }

    @Test
    public void shouldSolveAMixOfFunctionAndOperators() {
        assertEvaluateTo(1.7, "sin(pi/(4/2)) + 0.7");
    }

    @Test
    public void shouldEvaluateSqr() {
        assertEvaluateTo(32, "sqr(1024)");
    }

    @Test
    public void shouldSolveANoParameterOperator() {
        final Double result = calculator.solve("rnd()", context).get(0);
        assertTrue(result > 0 && result < 1);
    }

    @Test
    public void shouldReadANegativeNumber() {
        assertEvaluateTo(-8, " -8");
    }

    @Test
    public void shouldManageSpacedNegativeNumbers() {
        assertEvaluateTo(-18, "-8 - 10");
    }

    @Test
    public void shouldManageSpacedNegativeNumbersWithExtraSpace() {
        assertEvaluateTo(-18, "- 8 - 10");
    }

    @Test
    public void shouldInterpretASequenceOfNegativeNumbers() {
        assertEvaluateTo(-18, "-8-10");
    }

    @Test
    public void shouldInterpretASequenceOfNegativeNumbersWithOneSpace() {
        assertEvaluateTo(-18, "-8 -10");
    }

    @Test
    public void shouldInterpretASequenceOfNegativeNumbersWithTwoSpaces() {
        assertEvaluateTo(-18, "-8  -10");
    }

    @Test
    public void shouldInterpretASequenceOfNegativeNumbersWithThreeSpaces() {
        assertEvaluateTo(-18, "-8   -10");
    }

    @Test
    public void shouldManageOperandsPriorityOverNegativeValuesWithoutSpaces() {
        assertEvaluateTo(-80, "-8*+10");
    }

    @Test
    public void shouldManageOperandsPriorityOverNegativeValues() {
        assertEvaluateTo(-80, "-8 * +10");
    }

    @Test
    public void shouldManageOperatorsWithDifferentPriorities() {
        assertEvaluateTo(-8, "-8*sin(+2*pi/4)");
    }

    @Test
    public void shouldManageParenthesisWithOnlyOneElementInIt() {
        assertEvaluateTo(-80, "-8 * (+10)");
    }

    @Test
    public void shouldManageParenthesisWithOnlyOneElementInItAndSpaces() {
        assertEvaluateTo(-80, "-8 * (  +10)");
    }

    @Test
    public void shouldManageMinusOperatorWithOnlyOneParameter() {
        assertEvaluateTo(-10, "-(8 + 2)");
    }

    @Test
    public void shouldCalculateTheExpression() {
        assertEvaluateTo(-13, "-3 * sin(pi/2) -(8 + 2)");
    }

    @Test(expected = ParenthesisMismatchedException.class)
    public void shouldDetectTheMissingClosingParenthesis() {
        assertEvaluateTo(-13, "-3 * sin(pi/2 -(8 + 2)");
    }

    @Test
    public void shouldCalculateTheExpressionAvoidingExtraParameters() {
        assertEvaluateTo(-13, "-3 * sin(pi/2 9) -(8 + 2)");
    }

    @Test
    public void shouldCalculateTheAverageOfOneOperand() {
        assertEvaluateTo(6, "avg(6)");
    }

    @Test
    public void shouldCalculateTheAverageOfOneOperandInAnExpression() {
        assertEvaluateTo(6, "2 * avg(6) / 2");
    }

    @Test
    public void shouldCalculateTheAverageOfTwoOperands() {
        assertEvaluateTo(5, "avg(4 6)");
    }

    @Test
    public void shouldCalculateTheAverageOfThreeOperands() {
        assertEvaluateTo(6, "avg(4 6 8)");
    }

    @Test
    public void shouldCalculateTheAverageOfFiveOperands() {
        assertEvaluateTo(8, "avg(4 6 8 10 12)");
    }

    @Test
    public void shouldCalculateTheAverageOfFiveOperandsAndSubtractAValue() {
        assertEvaluateTo(0, "avg(4 6 8 10 12) - 8");
    }

    @Test
    public void shouldCalculateTheComplexExpression() {
        assertEvaluateTo(-1, "atan((5*sin(pi/4))/(cos(pi/4)*-5))*4/pi");
    }

    @Test
    public void shouldCalculateTheComplexExpression2() {
        assertEvaluateTo(-82, "9^2*sin(6*pi/-12)-ln(e)");
    }

    @Test
    public void shouldCalculateTheComplexExpression3() {
        assertEvaluateTo(7, "7^2 * 7^5 * 7^-6");
    }

    @Test
    public void shouldIgnoreUnusedNestedParentheses() {
        assertEvaluateTo(16, "4 * ((2 + 2))");
    }

    @Test
    public void shouldCalculateTheComplexExpression4() {
        assertEvaluateTo(-5.0 / 4, "-(acos(0) + pi/4 - asin(   -1))/pi");
    }

    @Test
    public void shouldEvaluatedTheVariableGivenInTheContext() {
        context.put("x", 123.0);
        assertEvaluateTo(246.0, "x*2");
    }

    @Test
    public void shouldAssignTheVariableToTheContext() {
        assertEvaluateTo(35.0, "y = 5 *(x=7)");
        assertEquals(7.0, context.get("x"), 1E-7);
        assertEquals(35.0, context.get("y"), 1E-7);
    }

    @Test
    public void shouldCalculateTheFactorial() {
        assertEvaluateTo(24.0, "4!");
    }

    @Test
    public void shouldCalculateTheFactorialOfAParenthesis() {
        assertEvaluateTo(24.0, "(1 / 2 * 8)!");
    }

    @Test
    public void shouldRecognizePriorities() {
        assertEvaluateTo(4.0, "4! / 8 + 1");
    }

    @Test
    public void shoudFlatifyTheArgumentsOnParenthesis() {
        assertEvaluateTo(2.0, "avg 1 (2 3)");
    }

    @Test
    public void shoudReadOnlyTheArgumentsOnParenthesis() {
        assertEvaluateTo(2.5, "avg(2 3) 1");
    }

    @Test(expected=SyntaxErrorException.class)
    public void shouldDetectAnEmptyExpression() {
        calculate("");
    }

    @Test
    public void shouldUseTheVariableJustSet() {
        assertEvaluateTo(9.0, "(x = 4 / 2 + 1) * x");
    }

    @Test
    public void shouldDistinguishBetweenAVariableContainingAnOperator() {
        context.put("sina", 0.5d);
        context.put("osin", Math.PI);
        assertEvaluateTo(Math.sin(Math.PI/2d), "sin(sina*osin)");
    }

    @Test
    public void shouldDistinguishBetweenAEAndAVariableNameContainingE() {
        context.put("ellelle", 1d);
        context.put("elle", 2d);
        context.put("el", 3d);
        context.put("le", 4d);
        assertEvaluateTo(1d + Math.E * 48d, "ellelle+2*elle*el*le*e");
    }

    @Test
    public void shouldDistinguishBetweenAVariableNameContainingAnOperator() {
        context.put("asintoto", Math.PI);
        assertEvaluateTo(0, "sin(asintoto)");
    }
}
