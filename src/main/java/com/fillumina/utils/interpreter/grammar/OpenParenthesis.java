package com.fillumina.utils.interpreter.grammar;

/**
 * Defines the symbols that open a parenthesis. Note that unbalanced different
 * kind of parenthesis will actually work. i.e.: <code>([2+1) *3]</code> is
 * all right.
 * <br />
 * This class is also used internally as the parameters holder when a parenthesis
 * is discovered.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class OpenParenthesis<T,C> extends AbstractPatternGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;

    public OpenParenthesis(final String symbolRegexp) {
        super(symbolRegexp, 0);
    }

    @Override
    public boolean isType(final Type type) {
        return Type.OPEN_PAR.equals(type);
    }

}
