package com.fillumina.utils.interpreter.grammar;

/**
 * Defines characters that will be ignored during parsing (usually spaces,
 * commas, tabs, linefeeds and the like).
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class WhiteSpace<T,C> extends AbstractPatternGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;

    public WhiteSpace(final String symbolRegexp) {
        super(symbolRegexp, 0);
    }

    @Override
    public boolean isType(final Type type) {
        return Type.WHITE_SPACE.equals(type);
    }

}
