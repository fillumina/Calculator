package com.fillumina.calculator.treebuilder;

import com.fillumina.calculator.SyntaxErrorException;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ParenthesesMismatchedException extends SyntaxErrorException {
    private static final long serialVersionUID = 1L;

    public ParenthesesMismatchedException() {
        this(null);
    }

    public ParenthesesMismatchedException(final Throwable cause) {
        super("mismatched parenthesis", cause);
    }

}
