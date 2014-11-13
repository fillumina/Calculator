package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElementMatcher;
import com.fillumina.calculator.GrammarElementType;
import com.fillumina.calculator.grammar.AbstractComparableGrammarElement;
import com.fillumina.calculator.grammar.QuotedStringElement;
import com.fillumina.performance.consumer.assertion.PerformanceAssertion;
import com.fillumina.performance.producer.TestContainer;
import com.fillumina.performance.producer.timer.RunnableSink;
import com.fillumina.performance.template.ProgressionConfigurator;
import com.fillumina.performance.util.junit.JUnitAutoProgressionPerformanceTemplate;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class PatternVsFastStringPerformanceTest
        extends JUnitAutoProgressionPerformanceTemplate {

    private static class PatternStringElement
            extends AbstractComparableGrammarElement<Void,Void> {
        private static final long serialVersionUID = 1L;

        // see http://www.metaltoad.com/blog/regex-quoted-string-escapable-quotes
        private static final String PATTERN_STRING =
                "((?<![\\\\])['\"])((?:.(?!(?<![\\\\])\\1))*.?)\\1";
        private static final Pattern PATTERN =
                Pattern.compile(PATTERN_STRING);

        public PatternStringElement() {
            super(0);
        }

        @Override
        public GrammarElementMatcher match(final String expression) {
            Matcher matcher = PATTERN.matcher(expression);
            matcher.find();
            if (matcher.groupCount() == 2) {
                return new ElementMatcher(matcher.start(2), matcher.end(2));
            }
            return ElementMatcher.NOT_FOUND;
        }

        @Override
        public Void evaluate(String value, List<Void> params, Void context) {
            return null;
        }

        @Override
        public int getRequiredOperandsAfter() {
            return 0;
        }

        @Override
        public int getRequiredOperandsBefore() {
            return 0;
        }

        @Override
        public GrammarElementType getType() {
            return GrammarElementType.OPERAND;
        }
    }

    public static void main(final String[] args) {
        new PatternVsFastStringPerformanceTest().executeWithOutput();
    }

    @Override
    public void init(ProgressionConfigurator config) {
        config.setTimeout(1, TimeUnit.MINUTES)
                .setMaxStandardDeviation(6);
    }

    @Override
    public void addTests(TestContainer tests) {
        final AbstractComparableGrammarElement<Void,Void> patternString =
                new PatternStringElement();

        final QuotedStringElement fastString =
                QuotedStringElement.INSTANCE;

        final String string = "allkjldskjf'hello kitti'akl";

        tests.addTest("pattern", new RunnableSink() {

            @Override
            public Object sink() {
                GrammarElementMatcher matcher = patternString.match(string);
                assertEquals(12, matcher.getStart());
                assertEquals(23, matcher.getEnd());
                return matcher;
            }
        });

        tests.addTest("fast", new RunnableSink() {

            @Override
            public Object sink() {
                GrammarElementMatcher matcher = fastString.match(string);
                // this matcher returns the quotes too (which are needed!)
                assertEquals(11, matcher.getStart());
                assertEquals(24, matcher.getEnd());
                return matcher;
            }
        });
    }

    @Override
    public void addAssertions(PerformanceAssertion assertion) {
        assertion.assertTest("fast").fasterThan("pattern");
    }
}
