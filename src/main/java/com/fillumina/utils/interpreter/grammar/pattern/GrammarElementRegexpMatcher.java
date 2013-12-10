package com.fillumina.utils.interpreter.grammar.pattern;

import com.fillumina.utils.interpreter.GrammarElementMatcher;
import java.io.Serializable;
import java.util.regex.Matcher;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class GrammarElementRegexpMatcher
        implements GrammarElementMatcher, Serializable {
    private static final long serialVersionUID = 1L;
    final Matcher matcher;

    public GrammarElementRegexpMatcher(final Matcher matcher) {
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
