package com.fillumina.utils.interpreter;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class EvaluationException extends SyntaxErrorException {
    private static final long serialVersionUID = 1L;

    private final String offendingElement;

    public EvaluationException(final String offendingElement) {
        this(offendingElement, null);
    }

    public EvaluationException(
            final String offendingElement,
            final Throwable cause) {
        super(createMessage(offendingElement), cause);
        this.offendingElement = offendingElement;
    }

    public String getOffendingElement() {
        return offendingElement;
    }

    private static String createMessage(final String offendingElement) {
        return "syntax error on element : " + offendingElement;
    }

}
