package com.fillumina.utils.interpreter.grammar.pattern;

import com.fillumina.utils.interpreter.GrammarElementType;

/**
 * Defines characters that will be ignored during parsing (usually spaces,
 * commas, tabs, linefeeds and the like).
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class WhiteSpace<T,C> extends UnevaluablePatternGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;

    public WhiteSpace(final String symbolRegexp) {
        super(symbolRegexp, 0);
    }

    @Override
    public boolean isType(final GrammarElementType type) {
        return GrammarElementType.WHITE_SPACE.equals(type);
    }
}
