package com.fillumina.utils.interpreter.grammar;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * 
 * @author fra
 */
public class GrammarElement
        implements Comparable<GrammarElement>, Serializable {
    private static final long serialVersionUID = 1L;

    private final String symbolRegexp; // regexp expression
    private final int priority; // priority

    private final Pattern regexp;

    public GrammarElement(final String symbolRegexp,
            final int priority) {
        this.symbolRegexp = symbolRegexp;
        this.priority = priority;

        regexp = Pattern.compile(symbolRegexp);
    }

    public Pattern getPattern() {
        return regexp;
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
