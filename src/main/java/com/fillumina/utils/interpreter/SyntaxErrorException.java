package com.fillumina.utils.interpreter;

/**
 *
 * @author fra
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
    }
}
