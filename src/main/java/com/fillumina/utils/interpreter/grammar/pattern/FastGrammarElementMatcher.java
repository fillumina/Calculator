package com.fillumina.utils.interpreter.grammar.pattern;

import com.fillumina.utils.interpreter.GrammarElementMatcher;
import java.io.Serializable;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class FastGrammarElementMatcher
        implements GrammarElementMatcher, Serializable {
    private static final long serialVersionUID = 1L;

    public static final GrammarElementMatcher NOT_FOUND =
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

    private final int start;
    private final int end;

    public FastGrammarElementMatcher(final int start, final int end) {
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
