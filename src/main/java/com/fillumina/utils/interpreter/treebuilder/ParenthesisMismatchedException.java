package com.fillumina.utils.interpreter.treebuilder;

import com.fillumina.utils.interpreter.SyntaxErrorException;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ParenthesisMismatchedException extends SyntaxErrorException {
    private static final long serialVersionUID = 1L;

    public ParenthesisMismatchedException() {
        this(null);
    }

    public ParenthesisMismatchedException(final Throwable cause) {
        super("mismatched parenthesis", cause);
    }

}
