package com.fillumina.calculator.grammar.fast;

import com.fillumina.calculator.GrammarElementMatcher;
import java.io.Serializable;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class FastElementMatcher
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

    public FastElementMatcher(final int start, final int end) {
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
