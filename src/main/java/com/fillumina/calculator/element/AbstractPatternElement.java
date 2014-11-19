package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.GrammarElementMatcher;
import com.fillumina.calculator.grammar.AbstractComparableGrammarElement;
import java.util.regex.Pattern;

/**
 * Useful to write your own {@link GrammarElement}s based on a regular
 * expression (REGEXP). Note that patterns are usually slower to evaluate than
 * a direct approach (about 3 times slower on average).
 *
 * @see com.fillumina.calculator.util.PatternBuilder
 * @see <a href='http://www.regular-expressions.info/lookaround.html'>
 *      http://www.regular-expressions.info/lookaround.html</a>
 * @see <a href='http://ocpsoft.org/opensource/guide-to-regular-expressions-in-java-part-2/'>
 *      http://ocpsoft.org/opensource/guide-to-regular-expressions-in-java-part-2/</a>
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractPatternElement<T,C>
        extends AbstractComparableGrammarElement<T, C> {
    private static final long serialVersionUID = 1L;

    public static final String NOT_STARTING_WITH_ALPHA = "(?<![a-zA-Z])";
    public static final String NOT_ENDING_WITH_ALPHA = "(?![a-zA-Z])";

    private final String regexp;
    private final Pattern pattern;

    /**
     *
     *
     * @param regexp  A regular expression used to recognize the element
     *                      in a string. Consider using
     *                      {@link #transform(java.lang.String) }.
     * @param priority      the highest the number, the more priority has
     *                      the element.
     */
    public AbstractPatternElement(final int priority, final String regexp) {
        super(priority);
        this.regexp = regexp;
        this.pattern = Pattern.compile(regexp);
    }

    public Pattern getPattern() {
        return pattern;
    }

    public String getRegexp() {
        return regexp;
    }

    @Override
    public String toString() {
        return regexp;
    }

    @Override
    public GrammarElementMatcher match(
            final GrammarElement<T,C> previousGrammarElement,
            final String expression) {
        return new ElementRegexpMatcher(pattern.matcher(expression));
    }

    /**
     * If a symbol is passed than it makes sure that the name is not preceded
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
