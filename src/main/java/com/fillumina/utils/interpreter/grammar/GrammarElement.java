package com.fillumina.utils.interpreter.grammar;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * It's a element in the grammar that has a specific priority over other elements
 * and a regular expression which is used to identify it in a string expression.
 *
 * @author fra
 */
public class GrammarElement
        implements Comparable<GrammarElement>, Serializable {
    private static final long serialVersionUID = 1L;

    private final String symbolRegexp; // regexp expression
    private final int priority; // priority

    private final Pattern pattern;

    /**
     *
     * @param symbolRegexp  a regular expression used to recognize the element
     *                      in a string
     * @param priority      the highest the number the more priority has
     *                      the element.
     */
    public GrammarElement(final String symbolRegexp,
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

    public boolean isGreaterThan(final GrammarElement ge) {
        return compareTo(ge) > 0;
    }

    @Override
    public int compareTo(final GrammarElement ge) {
        return priority < ge.priority ?
            -1 : (priority == ge.priority ? 0 : 1);
    }

    @Override
    public String toString() {
        return symbolRegexp;
    }

}
