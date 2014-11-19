package com.fillumina.calculator.treebuilder;

import com.fillumina.calculator.MappedContextSimplifyingCalculator;
import com.fillumina.calculator.grammar.ContextedGrammarBuilder;
import com.fillumina.calculator.grammar.ParametricEvaluator;
import com.fillumina.calculator.grammar.instance.ArithmeticGrammar;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class OperatorParametersReaderTest {

    @Test
    public void shouldEvaluateFiveParametersBefore() {
        assertRetrieveBeforeParameter(5, "0 1 2 3 4 @");
    }

    @Test
    public void shouldEvaluateTheOnlyFourParametersBefore() {
        assertRetrieveBeforeParameter(4, "0 1 2 3 @");
    }

    @Test
    public void shouldEvaluateTheOnlyThreeParametersBefore() {
        assertRetrieveBeforeParameter(3, "0 1 2 @");
    }

    @Test
    public void shouldEvaluateTheOnlyTwoParametersBefore() {
        assertRetrieveBeforeParameter(2, "0 1 @");
    }

    @Test
    public void shouldEvaluateTheOnlyParametersBefore() {
        assertRetrieveBeforeParameter(1, "0 @");
    }

    @Test
    public void shouldEvaluateZeroParameterBefore() {
        assertRetrieveBeforeParameter(0, " @");
    }

    @Test
    public void shouldEvaluateFiveParametersAfter() {
        assertRetrieveBeforeParameter(5, "@ 0 1 2 3 4");
    }

    @Test
    public void shouldEvaluateTheOnlyFourParametersAfter() {
        assertRetrieveBeforeParameter(4, "@ 0 1 2 3");
    }

    @Test
    public void shouldEvaluateTheOnlyThreeParametersAfter() {
        assertRetrieveBeforeParameter(3, "@ 0 1 2");
    }

    @Test
    public void shouldEvaluateTheOnlyTwoParametersAfter() {
        assertRetrieveBeforeParameter(2, "@ 0 1");
    }

    @Test
    public void shouldEvaluateTheOnlyParametersAfter() {
        assertRetrieveBeforeParameter(1, "@ 0");
    }

    @Test
    public void shouldEvaluateZeroParameterAfter() {
        assertRetrieveBeforeParameter(0, "@ ");
    }

    @Test
    public void shouldEvaluateFiveParameters() {
        assertRetrieveBeforeParameter(5, "-1 0 @ 0 1 2");
    }

    @Test
    public void shouldEvaluateTheOnlyFourParameters() {
        assertRetrieveBeforeParameter(4, "-1 0 @ 0 1");
    }

    @Test
    public void shouldEvaluateTheOnlyThreeParameters() {
        assertRetrieveBeforeParameter(3, "-1 0 @ 0");
    }

    @Test
    public void shouldEvaluateTheOnlyTwoParameters() {
        assertRetrieveBeforeParameter(2, "0 @ 0");
    }

    private void assertRetrieveBeforeParameter(final int parameterNumber,
            final String expression) {
        final MappedContextSimplifyingCalculator<Double> calc =
            new MappedContextSimplifyingCalculator<>(
                new ContextedGrammarBuilder<>(ArithmeticGrammar.INSTANCE)
                .addOperator()
                    .priority(1)
                    .symbols("@")
                    .operandsBefore(5)
                    .operandsAfter(5)
                    .evaluator(new ParametricEvaluator<Double, Map<String, Double>>() {
                        @Override
                        public Double evaluate(String value, List<Double> params,
                                Map<String, Double> context) {
                            assertEquals(parameterNumber, params.size());
                            return 0d;
                        }
                    })
                    .buildOperator()
                .buildGrammar());

        assertEquals(0, calc.solveSingleValue(expression), 0);
    }
}
