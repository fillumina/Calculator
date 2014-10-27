package com.fillumina.calculator.grammar;

import com.fillumina.calculator.SyntaxErrorException;

/**
 * Misconfigured grammar elements.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class GrammarException extends SyntaxErrorException {
    private static final long serialVersionUID = 1L;

    public GrammarException() {
        super();
    }

    public GrammarException(final String message) {
        super(message);
    }

    public GrammarException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public GrammarException(final Throwable cause) {
        super(cause);
    }
}
