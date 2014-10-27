package com.fillumina.interpreter.grammar.fast;

import com.fillumina.performance.consumer.assertion.PerformanceAssertion;
import com.fillumina.performance.producer.TestContainer;
import com.fillumina.performance.template.ProgressionConfigurator;
import com.fillumina.performance.util.junit.JUnitAutoProgressionPerformanceTemplate;
import com.fillumina.interpreter.DefaultCalculator;
import com.fillumina.interpreter.DefaultSolver;
import com.fillumina.interpreter.grammar.fast.instances.FastBooleanGrammar;
import com.fillumina.interpreter.grammar.pattern.instances.BooleanGrammar;
import java.util.Map;
import static org.junit.Assert.*;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class PatternVsFastBooleanPerformanceTest
        extends JUnitAutoProgressionPerformanceTemplate {

    public static void main(final String[] args) {
        new PatternVsFastBooleanPerformanceTest().executeWithOutput();
    }

    @Override
    public void init(ProgressionConfigurator config) {
    }

    @Override
    public void addTests(TestContainer tests) {
        final String expression = "NOT (false OR(true AND true))";
        final boolean result = false;

        tests.addTest("pattern", new Runnable() {
            final DefaultCalculator<Boolean, Map<String,Boolean>> c =
                    new DefaultCalculator<>(BooleanGrammar.INSTANCE,
                            DefaultSolver.INSTANCE);

            @Override
            public void run() {
                assertEquals(result, c.solve(expression, null).get(0));
            }
        });

        tests.addTest("fast", new Runnable() {
            final DefaultCalculator<Boolean, Map<String,Boolean>> c =
                    new DefaultCalculator<>(FastBooleanGrammar.INSTANCE,
                            DefaultSolver.INSTANCE);

            @Override
            public void run() {
                assertEquals(result, c.solve(expression, null).get(0));
            }
        });
    }

    @Override
    public void addAssertions(PerformanceAssertion assertion) {
        assertion.assertTest("fast").fasterThan("pattern");
        assertion.assertPercentageFor("fast").lessThan(65);
    }
}
