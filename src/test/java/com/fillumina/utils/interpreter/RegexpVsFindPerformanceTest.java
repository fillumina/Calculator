package com.fillumina.utils.interpreter;

import com.fillumina.performance.consumer.assertion.PerformanceAssertion;
import com.fillumina.performance.producer.TestsContainer;
import com.fillumina.performance.template.ProgressionConfigurator;
import com.fillumina.performance.util.junit.JUnitAutoProgressionPerformanceTemplate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.Assert.*;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class RegexpVsFindPerformanceTest
        extends JUnitAutoProgressionPerformanceTemplate {

    public static final void main(final String[] args) {
        new RegexpVsFindPerformanceTest().executeWithOutput();
    }

    @Override
    public void init(ProgressionConfigurator config) {
    }

    @Override
    public void addTests(TestsContainer tests) {
        final String str = "abcdefghijklmnopqrstuvwxyz";
        final String search = "uvw";

        tests.addTest("regexp", new Runnable() {
            final Pattern pattern = Pattern.compile(search);

            @Override
            public void run() {
                final Matcher matcher = pattern.matcher(str);
                matcher.find();
                assertEquals(20, matcher.start());
            }
        });

        tests.addTest("find", new Runnable() {

            @Override
            public void run() {
                assertEquals(20, str.indexOf(search));
            }
        });
    }

    @Override
    public void addAssertions(PerformanceAssertion assertion) {
    }
}
