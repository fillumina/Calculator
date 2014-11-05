package com.fillumina.calculator.element;

import com.fillumina.calculator.grammar.DateFastOperand;
import com.fillumina.calculator.pattern.AbstractOperand;
import com.fillumina.performance.consumer.assertion.PerformanceAssertion;
import com.fillumina.performance.producer.TestContainer;
import com.fillumina.performance.producer.timer.RunnableSink;
import com.fillumina.performance.template.ProgressionConfigurator;
import com.fillumina.performance.util.junit.JUnitAutoProgressionPerformanceTemplate;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class PatternVsFastDatePerformanceTest
        extends JUnitAutoProgressionPerformanceTemplate {

    private static class PatternDate extends AbstractOperand<Void,Void> {
        private static final long serialVersionUID = 1L;
        public static final String DATE_TIME_PATTERN =
            "\\d{1,2}/\\d{1,2}/\\d{1,4}(\\ +\\d{1,2}:\\d{1,2}:\\d{1,2})?";

        public PatternDate() {
            super(DATE_TIME_PATTERN, 0);
        }

        @Override
        public Void evaluate(final String val, final Void query) {
            return null;
        }
    }

    public static void main(final String[] args) {
        new PatternVsFastDatePerformanceTest().executeWithOutput();
    }

    @Override
    public void init(ProgressionConfigurator config) {
        config.setTimeout(1, TimeUnit.MINUTES)
                .setMaxStandardDeviation(3);
    }

    @Override
    public void addTests(TestContainer tests) {
        final PatternDate patternDate = new PatternDate();
        final DateFastOperand fastDate = DateFastOperand.DATE_TIME;
        final String strDate = "12/11/2014 10:22:55";

        tests.addTest("pattern", new RunnableSink() {
            @Override
            public Object sink() {
                return patternDate.match(strDate);
            }
        });

        tests.addTest("fast", new RunnableSink() {

            @Override
            public Object sink() {
                return fastDate.match(strDate);
            }
        });
    }

    @Override
    public void addAssertions(PerformanceAssertion assertion) {
        //assertion.assertTest("fast").fasterThan("pattern");
    }

}
