package com.fillumina.utils.interpreter.grammar;

/**
 * If the grammar supports them, this element is well suited to contain
 * variables. Variables could be considered tokens which could not
 * be recognized as other kind of {@link GrammarElement}s.
 * 
 * @author fra
 */
public abstract class UnrecognizedElement<T,C> 
        extends EvaluableGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;

    public UnrecognizedElement() {
        // '$a' is the a char after the end of the input, which will never match!!
        super("$a", 0);
    }

}
