package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElementMatcher;
import java.io.Serializable;
import java.util.regex.Matcher;

/**
 * Implementation of {@link GrammarElementMatcher} that wraps a
 * REGEXP's {@link Matcher}.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ElementRegexpMatcher
        implements GrammarElementMatcher, Serializable {
    private static final long serialVersionUID = 1L;
    final Matcher matcher;

    public ElementRegexpMatcher(final Matcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean isFound() {
        return matcher.find();
    }

    @Override
    public int getStart() {
        return matcher.start();
    }

    @Override
    public int getEnd() {
        return matcher.end();
    }
}
