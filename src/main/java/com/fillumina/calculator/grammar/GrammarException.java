package com.fillumina.calculator.grammar;

import com.fillumina.calculator.SyntaxErrorException;

/**
 * Misconfigured grammar element.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class GrammarException extends SyntaxErrorException {
    private static final long serialVersionUID = 1L;

    public GrammarException(final String message) {
        super(message);
    }
}
