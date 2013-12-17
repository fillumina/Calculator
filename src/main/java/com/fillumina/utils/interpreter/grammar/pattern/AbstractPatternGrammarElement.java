package com.fillumina.utils.interpreter.grammar.pattern;

import com.fillumina.utils.interpreter.AbstractComparableGrammarElement;
import com.fillumina.utils.interpreter.GrammarElementMatcher;
import java.util.regex.Pattern;

/**
 * It's the base class of a hierarchy based on the use of a regular expression
 * to recognize the element in a string.
 *
 * @see <a href='http://www.regular-expressions.info/lookaround.html'>
 *      http://www.regular-expressions.info/lookaround.html</a>
 * @see <a href='http://ocpsoft.org/opensource/guide-to-regular-expressions-in-java-part-2/'>
 *      http://ocpsoft.org/opensource/guide-to-regular-expressions-in-java-part-2/</a>
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractPatternGrammarElement<T,C>
        extends AbstractComparableGrammarElement<T, C> {
    private static final long serialVersionUID = 1L;

    public static final String NOT_STARTING_WITH_ALPHA = "(?<![a-zA-Z])";
    public static final String NOT_ENDING_WITH_ALPHA = "(?![a-zA-Z])";

    private final String symbolRegexp;
    private final Pattern pattern;

    /**
     *
     * @param symbolRegexp  a regular expression used to recognize the element
     *                      in a string
     * @param priority      the highest the number, the more priority has
     *                      the element.
     */
    public AbstractPatternGrammarElement(final String symbolRegexp,
            final int priority) {
        super(priority);
        this.symbolRegexp = symbolRegexp;
        this.pattern = Pattern.compile(symbolRegexp);
    }

    public Pattern getPattern() {
        return pattern;
    }

    public String getSymbolRegexp() {
        return symbolRegexp;
    }

    @Override
    public String toString() {
        return symbolRegexp;
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
    public GrammarElementMatcher match(final String expression) {
        return new GrammarElementRegexpMatcher(pattern.matcher(expression));
    }

    /**
     * If a name is passed than it makes sure that the name is not preceded
     * by other characters (like <code>sin</code> and <code>asin</code>).
     * If a single symbol is passed than it is recognized
     * in every context without restrictions.
     * It's somewhat arbitrary but it seems to make sense with usual
     * arithmetics at least.
     */
    public static String transform(final String name) {
        if (name.length() == 1) {
            if (" *.\\\"()[]?+-^$".indexOf(name.charAt(0)) != -1) {
                return "\\" + name;
            }
            return name;
        }
        return NOT_STARTING_WITH_ALPHA +
                Pattern.quote(name) + NOT_ENDING_WITH_ALPHA;
    }
}
