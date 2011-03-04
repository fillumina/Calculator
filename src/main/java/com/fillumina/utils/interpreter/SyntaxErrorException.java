package com.fillumina.utils.interpreter;

/**
 *
 * @author fra
 */
public class SyntaxErrorException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final String offendingElement;

    /** the message is there just for help, should not be used because of I18N */
    public SyntaxErrorException(final String offendingElement,
            final String message) {
        super(createMessage(message, offendingElement));
        this.offendingElement = offendingElement;
    }

    /** the message is there just for help, should not be used because of I18N */
    public SyntaxErrorException(final String offendingElement,
            final String message, final Throwable e) {
        super(createMessage(message, offendingElement), e);
        this.offendingElement = offendingElement;
    }

    public String getOffendingElement() {
        return offendingElement;
    }

    private static String createMessage(final String message,
            final String offendingElement) {
        return message + ": " + offendingElement;
    }

}
