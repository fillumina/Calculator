package com.fillumina.calculator;

/**
 * The given expression is not syntactically correct. This is also the
 * parent of all the exceptions related to this module.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class SyntaxErrorException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SyntaxErrorException(final Throwable cause) {
        super(cause);
    }

    public SyntaxErrorException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public SyntaxErrorException(final String message) {
        super(message);
    }

    public SyntaxErrorException() {
        super();
    }
}
