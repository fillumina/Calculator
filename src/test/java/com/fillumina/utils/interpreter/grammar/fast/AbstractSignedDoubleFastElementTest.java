package com.fillumina.utils.interpreter.grammar.fast;

import com.fillumina.utils.interpreter.GrammarElement;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class AbstractSignedDoubleFastElementTest
        extends GrammarElementTestBase {

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

    @Override
    protected GrammarElement<Double, Void> getGrammarElement() {
        return new AbstractSignedDoubleFastElement<Double, Void>(0) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value,
                    final List<Double> params,
                    final Void context) {
                return Double.valueOf(value);
            }
        };
    }
}
