package com.fillumina.utils.interpreter.grammar;

import com.fillumina.utils.interpreter.GrammarElementMatcher;
import java.io.Serializable;
import java.util.regex.Matcher;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class GrammarElementRegexpMatcher implements GrammarElementMatcher, Serializable {
    private static final long serialVersionUID = 1L;
    final Matcher matcher;

    public GrammarElementRegexpMatcher(final Matcher matcher) {
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
