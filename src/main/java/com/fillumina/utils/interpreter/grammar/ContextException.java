package com.fillumina.utils.interpreter.grammar;

import com.fillumina.utils.interpreter.SyntaxErrorException;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ContextException extends SyntaxErrorException {
    private static final long serialVersionUID = 1L;

    private final String offendingElement;

    public ContextException(final String offendingElement) {
        this(offendingElement, null);
    }

    public ContextException(
            final String offendingElement,
            final Throwable cause) {
        super(createMessage(offendingElement), cause);
        this.offendingElement = offendingElement;
    }

    public String getOffendingElement() {
        return offendingElement;
    }

    private static String createMessage(final String offendingElement) {
        return "variable not in context: " + offendingElement;
    }

}
