package com.fillumina.utils.interpreter;

/**
 *
 * @author fra
 */
public class ParenthesisMismatchedException extends SyntaxErrorException {
    private static final long serialVersionUID = 1L;
    public static final String OFFENDING_ELEMENT = "parenthesis";

    public ParenthesisMismatchedException(final String message,
            final Throwable e) {
        super(OFFENDING_ELEMENT, message, e);
    }

    public ParenthesisMismatchedException(final String message) {
        super(OFFENDING_ELEMENT, message);
    }

}
