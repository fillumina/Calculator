package com.fillumina.calculator.element;

import com.fillumina.calculator.Calculator;
import com.fillumina.calculator.DefaultSolver;
import com.fillumina.calculator.instance.BooleanGrammar;
import com.fillumina.calculator.pattern.instances.BooleanPatternGrammar;
import com.fillumina.performance.consumer.assertion.PerformanceAssertion;
import com.fillumina.performance.producer.TestContainer;
import com.fillumina.performance.template.ProgressionConfigurator;
import com.fillumina.performance.util.junit.JUnitAutoProgressionPerformanceTemplate;
import java.util.Map;
import static org.junit.Assert.assertEquals;

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
        config.setMaxStandardDeviation(6);
    }

    @Override
    public void addTests(TestContainer tests) {
        final String expression = "NOT (false OR(true AND true))";
        final boolean result = false;

        tests.addTest("pattern", new Runnable() {
            final Calculator<Boolean, Map<String,Boolean>> c =
                    new Calculator<>(BooleanPatternGrammar.INSTANCE,
                            DefaultSolver.INSTANCE);

            @Override
            public void run() {
                assertEquals(result, c.solve(null, expression).get(0));
            }
        });

        tests.addTest("fast", new Runnable() {
            final Calculator<Boolean, Map<String,Boolean>> c =
                    new Calculator<>(BooleanGrammar.INSTANCE,
                            DefaultSolver.INSTANCE);

            @Override
            public void run() {
                assertEquals(result, c.solve(null, expression).get(0));
            }
        });
    }

    @Override
    public void addAssertions(PerformanceAssertion assertion) {
        assertion.assertTest("fast").fasterThan("pattern");
        assertion.assertPercentageFor("fast").lessThan(65);
    }
}
