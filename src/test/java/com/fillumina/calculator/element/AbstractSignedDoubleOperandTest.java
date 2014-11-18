package com.fillumina.calculator.element;

import com.fillumina.calculator.ContextCalculator;
import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.grammar.ContextedGrammarBuilder;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class AbstractSignedDoubleOperandTest
        extends GrammarElementTestBase {

    @Override
    protected GrammarElement<Double, Void> getGrammarElement() {
        return new SignedDoubleOperand(0);
    }

    @Test
    public void shouldCaptureTheSign() {
        recognize("-1", "-1");
    }

    @Test
    public void shouldCaptureTheSignWithSpacesBefore() {
        recognize("-1234", "    -1234");
    }

    @Test
    public void shouldCaptureTheSignInADecimalNumber() {
        recognize("-.2234", "  -.2234 ");
    }

    @Test
    public void shouldNotCaptureTheSignIfThereIsSpacesBetweenNumber() {
        recognize("1", "- 1");
    }

    @Test
    public void shouldCaptureTheExponent() {
        recognize("1E-4", "1E-4");
    }

    @Test
    public void shouldUseADifferentSeparator() {
        final ContextCalculator<Double> calc = new ContextCalculator<>(
            new ContextedGrammarBuilder<Double>()
                .add(new AbstractDoubleOperand<Double,Map<String,Double>>(0, '?') {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public Double evaluate(String value, List<Double> params,
                            Map<String, Double> context) {
                        final String v = value.replace("?", ".");
                        return Double.valueOf(v);
                    }
                }).buildGrammar());

        assertEquals(12.4, calc.solveSingleValue("12?4"), 0);
    }

    private static class SignedDoubleOperand
            extends AbstractDoubleOperand<Double, Void> {
        private static final long serialVersionUID = 1L;

        public SignedDoubleOperand(int priority) {
            super(priority);
        }

        @Override
        public Double evaluate(final String value,
                final List<Double> params,
                final Void context) {
            return null;//Double.valueOf(value);
        }
    }
}
