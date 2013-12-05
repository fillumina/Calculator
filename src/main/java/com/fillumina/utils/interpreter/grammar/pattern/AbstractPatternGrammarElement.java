package com.fillumina.utils.interpreter.grammar.pattern;

import com.fillumina.utils.interpreter.GrammarElement;
import com.fillumina.utils.interpreter.GrammarElementMatcher;
import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * It's the base class of a hierarchy based on the use of a regular expression
 * to recognize the element in a string.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractPatternGrammarElement<T,C>
        implements GrammarElement<T,C>, Serializable {
    private static final long serialVersionUID = 1L;

    private final String symbolRegexp;
    private final int priority;

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
        this.symbolRegexp = symbolRegexp;
        this.priority = priority;
        this.pattern = Pattern.compile(symbolRegexp);
    }

    public Pattern getPattern() {
        return pattern;
    }

    public int getPriority() {
        return priority;
    }

    public String getSymbolRegexp() {
        return symbolRegexp;
    }

    @Override
    public int compareTo(final GrammarElement<T,C> grammarElement) {
        if (!(grammarElement instanceof AbstractPatternGrammarElement)) {
            return 0;
        }
        final AbstractPatternGrammarElement<T,C> ge =
                (AbstractPatternGrammarElement<T,C>) grammarElement;
        return Integer.compare(priority, ge.priority);
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
}
