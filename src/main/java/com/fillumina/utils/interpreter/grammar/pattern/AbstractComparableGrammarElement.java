package com.fillumina.utils.interpreter.grammar.pattern;

import com.fillumina.utils.interpreter.GrammarElement;
import com.fillumina.utils.interpreter.GrammarElementMatcher;
import java.io.Serializable;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractComparableGrammarElement<T, C>
        implements GrammarElement<T, C>, Serializable {
    private static final long serialVersionUID = 1L;

    protected static final GrammarElementMatcher NOT_FOUND =
            new GrammarElementMatcher() {

        @Override
        public boolean isFound() {
            return false;
        }

        @Override
        public int getStart() {
            return -1;
        }

        @Override
        public int getEnd() {
            return -1;
        }
    };

    protected static class InnerGrammarElementMatcher
            implements GrammarElementMatcher, Serializable {
        private static final long serialVersionUID = 1L;

        private final int start, end;

        public InnerGrammarElementMatcher(final int start, final int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public boolean isFound() {
            return true;
        }

        @Override
        public int getStart() {
            return start;
        }

        @Override
        public int getEnd() {
            return end;
        }
    }

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
