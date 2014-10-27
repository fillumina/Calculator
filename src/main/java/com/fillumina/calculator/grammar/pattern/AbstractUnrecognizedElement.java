package com.fillumina.calculator.grammar.pattern;

import com.fillumina.calculator.grammar.GrammarElementType;

/**
 * If the grammar supports them, this element is well suited to represents
 * variables. Variables could be considered tokens which could not
 * be recognized as other kind of {@link GrammarElement}s.<br />
 *
 * <b>IMPORTANT:</b> There could be only <b>one</b>
 * <code>UnrecognizedElement</code> defined in a grammar.
 *
 * @see VariableContextManager
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractUnrecognizedElement<T,C>
        extends AbstractPatternElement<T,C> {
    private static final long serialVersionUID = 1L;

    public AbstractUnrecognizedElement() {
        // '$a' is the regexp for the character past the end of the input,
        // which will never match!! In practice this is a never matching regexp.
        super("$a", 0);
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.UNRECOGNIZED;
    }
}
