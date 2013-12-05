package com.fillumina.utils.interpreter.grammar;

import com.fillumina.utils.interpreter.EvaluationException;
import com.fillumina.utils.interpreter.GrammarElement;
import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * It's the base class of a hierarchy based on the use of a regular expression
 * to find elements in a string.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractPatternGrammarElement<T,C>
        implements GrammarElement<T,C>, Serializable {
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
        return priority < ge.priority ?
            -1 : (priority == ge.priority ? 0 : 1);
    }

    @Override
    public String toString() {
        return symbolRegexp;
    }

    @Override
    public T evaluate(final String value, final List<T> params, final C context) {
        throw new EvaluationException("Element not evaluable: " + value +
                ", parameters: " + params);
    }

    @Override
    public int getRequiredOperandsAfter() {
        return 0;
    }

    @Override
    public int getRequiredOperandsBefore() {
        return 0;
    }

    public static class PatternMatchedIndexes
            implements GrammarElementMatchIndex, Serializable {
        private static final long serialVersionUID = 1L;

        private final Matcher matcher;

        public PatternMatchedIndexes(final Matcher matcher) {
            this.matcher = matcher;
        }

        @Override
        public boolean found() {
            return matcher.find();
        }

        @Override
        public int start() {
            return matcher.start();
        }

        @Override
        public int end() {
            return matcher.end();
        }
    }

    @Override
    public GrammarElementMatchIndex match(final String expression) {
        return new PatternMatchedIndexes(pattern.matcher(expression));
    }
}
