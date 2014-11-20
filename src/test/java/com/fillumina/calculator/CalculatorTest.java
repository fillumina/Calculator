package com.fillumina.calculator;

import com.fillumina.calculator.grammar.instance.DoubleArithmeticGrammar;
import com.fillumina.calculator.treebuilder.ParenthesesMismatchedException;
import com.fillumina.calculator.util.Mapper;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class CalculatorTest {

    @SuppressWarnings("unchecked")
    private static final Map<String, Double> EMPTY_MAP =
            (Map<String,Double>)Collections.EMPTY_MAP;

    private Calculator<Double, Map<String,Double>> calc =
            new Calculator<>(DoubleArithmeticGrammar.INSTANCE);

    @Test
    public void shouldSolveMultipleResults() {
        assertEquals(Arrays.asList(3d, 5d), calc.solve("2 + 1 5"));
    }

    @Test
    public void shouldGetASingleSolution() {
        assertEquals(4d, calc.solveSingleValue("2 + 2"), 0);
    }

    @Test
    public void shouldSolveWithContext() {
        assertEquals(Arrays.asList(2d, 5d),
                calc.solve(Mapper.<Double>create("x", 2d), "x x + 3"));
    }

    @Test
    public void shouldSolveSingleSolutionWithContext() {
        assertEquals(5d,
                calc.solveSingleValue(Mapper.<Double>create("x", 2d), "x + 3"),
                0);
    }

    @Test(expected=SyntaxErrorException.class)
    public void shouldThrowAnExceptionIfEmptyExpression() {
        calc.solve("");
    }

    @Test(expected=SyntaxErrorException.class)
    public void shouldThrowAnExceptionIfNullExpression() {
        calc.solve(null);
    }

    @Test(expected=ContextException.class)
    public void shouldThrowAnExceptionIfNoSolutionIsFound() {
        calc.solve("x + 2");
    }

    @Test(expected=ParenthesesMismatchedException.class)
    public void shouldThrowAnExceptionIfMismatchedParentheses() {
        calc.solve("(2 +");
    }

    @Test(expected=EvaluationException.class)
    public void shouldThrowAnExceoptionIfSyntaxError() {
        calc.solve("+");
    }

    @Test(expected=SyntaxErrorException.class)
    public void shouldThrowAnExceptionIfEmptyExpressionInSingleValue() {
        calc.solveSingleValue("");
    }

    @Test(expected=SyntaxErrorException.class)
    public void shouldThrowAnExceptionIfNullExpressionInSingleValue() {
        calc.solveSingleValue(null);
    }

    @Test(expected=ContextException.class)
    public void shouldThrowAnExceptionIfNoSolutionIsFoundInSingleValue() {
        calc.solveSingleValue("x + 2");
    }

    @Test(expected=ParenthesesMismatchedException.class)
    public void shouldThrowAnExceptionIfMismatchedParenthesesInSingleValue() {
        calc.solveSingleValue("(2 +");
    }

    @Test(expected=EvaluationException.class)
    public void shouldThrowAnExceoptionIfSyntaxErrorInSingleValue() {
        calc.solveSingleValue("+");
    }

    @Test(expected=SyntaxErrorException.class)
    public void shouldThrowAnExceptionIfEmptyExpressionInCreateSolutionTree() {
        calc.createSolutionTree("");
    }

    @Test(expected=SyntaxErrorException.class)
    public void shouldThrowAnExceptionIfNullExpressionInCreateSolutionTree() {
        calc.createSolutionTree(null);
    }

    @Test
    public void should_NOT_ThrowAContextExceptionIfNoSolutionIsFoundInCreateSolutionTree() {
        calc.createSolutionTree("x + 2");
    }

    @Test(expected=ParenthesesMismatchedException.class)
    public void shouldThrowAnExceptionIfMismatchedParenthesesInCreateSolutionTree() {
        calc.createSolutionTree("(2 +");
    }

    @Test
    public void should_NOT_ThrowAnEvaluationExceoptionIfSyntaxErrorInCreateSolutionTree() {
        calc.createSolutionTree("+");
    }

    @Test
    public void shouldReturnTheSolve() {
        assertNotNull(calc.getSolver());
    }

    @Test
    public void shouldHaveAStaticSolution() {
        final SolutionTree<Double, Map<String,Double>> solution =
                calc.createSolutionTree("(1 / 4)");
        solution.solve();
        assertTrue(solution.isSolved());
        assertEquals(0.25d, solution.getSingleSolution(), 0.01);
    }

    @Test
    public void shouldSolveAStaticSolutionAnyway() {
        final SolutionTree<Double, Map<String,Double>> solution =
                calc.createSolutionTree("(1 / 4)");
        solution.solve();
        assertTrue(solution.isSolved());
        assertEquals(0.25d, solution.solve(EMPTY_MAP).get(0), 0.01);
    }

    @Test
    public void shouldNotHaveAStaticSolution() {
        final SolutionTree<Double, Map<String,Double>> solution =
                calc.createSolutionTree("sin(x + 1 / 4)");

        assertFalse(solution.isSolved());
    }

    @Test
    public void shouldCalculateTheSolutionOnceAVariableIsInserted() {
        final SolutionTree<Double, Map<String,Double>> solution =
                calc.createSolutionTree("sin(x + 1 / 4)");

        final Map<String,Double> context = Mapper.create("x", 3.0/4.0);
        assertEquals(0.8414709848078965, solution.solve(context).get(0), 0.001);
    }

    @Test
    public void shouldCalculateTheSolutionOnceASecondVariableIsInserted() {
        final SolutionTree<Double, Map<String,Double>> solution =
                calc.createSolutionTree("sin(x + 1 / 4) + y");

        final Map<String,Double> context =
                Mapper.create("x", 3.0/4.0, "y", 1.0);
        assertEquals(1.8414709848078965, solution.solve(context).get(0), 0.001);
    }
}
