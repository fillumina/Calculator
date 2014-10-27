package com.fillumina.calculator.grammar;

import com.fillumina.calculator.GrammarElement;
import java.io.Serializable;

/**
 * Priority is used when building the solution tree to put high priority
 * elements first. This abstract class provides a simple priority mechanism.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractComparableGrammarElement<T, C>
        implements GrammarElement<T, C>, Serializable {
    private static final long serialVersionUID = 1L;

    protected final int priority;

    /** Higher number is higher priority. */
    public AbstractComparableGrammarElement(final int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public static boolean isDigit(final char c) {
        return c >= '0' && c <= '9';
    }

    @Override
    public int compareTo(final GrammarElement<T, C> grammarElement) {
        if (!(grammarElement instanceof AbstractComparableGrammarElement)) {
            return 0;
        }
        final AbstractComparableGrammarElement<T, C> ge =
                (AbstractComparableGrammarElement<T, C>) grammarElement;
        return Integer.compare(priority, ge.priority);
    }
}
