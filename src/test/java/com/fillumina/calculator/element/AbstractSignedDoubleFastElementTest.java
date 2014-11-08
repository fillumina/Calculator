package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElement;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class AbstractSignedDoubleFastElementTest
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

    private static class SignedDoubleOperand
            extends AbstractSignedDoubleOperand<Double, Void> {
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
