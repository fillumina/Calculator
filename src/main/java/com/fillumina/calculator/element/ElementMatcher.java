package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElementMatcher;
import java.io.Serializable;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ElementMatcher
        implements GrammarElementMatcher, Serializable {
    private static final long serialVersionUID = 1L;

    private final int start;
    private final int end;

    public ElementMatcher(final int start, final int end) {
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
