package com.fillumina.calculator.util;

import com.fillumina.performance.consumer.assertion.PerformanceAssertion;
import com.fillumina.performance.producer.TestContainer;
import com.fillumina.performance.producer.timer.RunnableSink;
import com.fillumina.performance.template.ProgressionConfigurator;
import com.fillumina.performance.util.junit.JUnitAutoProgressionPerformanceTemplate;
import java.util.Random;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class CharacterUtilIsDigitPerformanceTest
        extends JUnitAutoProgressionPerformanceTemplate {

    public static void main(String[] args) {
        new CharacterUtilIsDigitPerformanceTest().executeWithIntermediateOutput();
    }

    @Override
    public void init(ProgressionConfigurator config) {
        config.setMaxStandardDeviation(4);
    }

    @Override
    public void addTests(TestContainer tests) {
        final Random rnd = new Random();

        tests.addTest("Character", new RunnableSink() {

            @Override
            public Object sink() {
                return Character.isDigit(
                        (char)rnd.nextInt(Character.MAX_VALUE));
            }
        });

        tests.addTest("Fast", new RunnableSink() {

            @Override
            public Object sink() {
                return CharacterUtil.isDigit(
                        (char)rnd.nextInt(Character.MAX_VALUE));
            }
        });
    }

    @Override
    public void addAssertions(PerformanceAssertion assertion) {
        assertion.assertTest("Character").slowerThan("Fast");
    }
}
