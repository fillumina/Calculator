package com.fillumina.utils.interpreter.grammar.pattern;

import com.fillumina.utils.interpreter.GrammarElement;
import java.io.Serializable;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractComparableGrammarElement<T, C>
        implements GrammarElement<T, C>, Serializable {
    private static final long serialVersionUID = 1L;

    protected final int priority;

    public AbstractComparableGrammarElement(final int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
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
