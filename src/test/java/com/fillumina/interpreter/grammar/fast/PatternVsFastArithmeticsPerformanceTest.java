package com.fillumina.interpreter.grammar.fast;

import com.fillumina.interpreter.grammar.fast.instances.FastArithmeticGrammar;
import com.fillumina.performance.consumer.assertion.PerformanceAssertion;
import com.fillumina.performance.producer.TestContainer;
import com.fillumina.performance.template.ProgressionConfigurator;
import com.fillumina.performance.util.junit.JUnitAutoProgressionPerformanceTemplate;
import com.fillumina.interpreter.DefaultCalculator;
import com.fillumina.interpreter.DefaultSolver;
import com.fillumina.interpreter.grammar.pattern.instances.ArithmeticGrammar;
import java.util.Map;
import static org.junit.Assert.*;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class PatternVsFastArithmeticsPerformanceTest
        extends JUnitAutoProgressionPerformanceTemplate {

    public static void main(final String[] args) {
        new PatternVsFastArithmeticsPerformanceTest().executeWithOutput();
    }

    @Override
    public void init(ProgressionConfigurator config) {
    }

    @Override
    public void addTests(TestContainer tests) {
        final String expression = "atan( (5*sin(pi/4)) / (cos(pi/4)*-5) ) * 4/pi";
        final double result = -1;

        tests.addTest("pattern", new Runnable() {
            final DefaultCalculator<Double, Map<String,Double>> c =
                    new DefaultCalculator<>(ArithmeticGrammar.INSTANCE,
                            DefaultSolver.INSTANCE);

            @Override
            public void run() {
                assertEquals(result, c.solve(expression, null).get(0), 0.001);
            }
        });

        tests.addTest("fast", new Runnable() {
            final DefaultCalculator<Double, Map<String,Double>> c =
                    new DefaultCalculator<>(FastArithmeticGrammar.INSTANCE,
                            DefaultSolver.INSTANCE);

            @Override
            public void run() {
                assertEquals(result, c.solve(expression, null).get(0), 0.001);
            }
        });
    }

    @Override
    public void addAssertions(PerformanceAssertion assertion) {
        assertion.assertTest("fast").fasterThan("pattern");
        assertion.assertPercentageFor("fast").lessThan(35);
    }
}
