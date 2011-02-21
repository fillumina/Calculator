package com.fillumina.utils.interpreter.grammar;

import com.fillumina.utils.interpreter.Node;
import java.util.List;

/**
 *
 * @author fra
 */
public abstract class EvaluableGrammarElement<T,C> extends GrammarElement {
    private static final long serialVersionUID = 1L;

    public EvaluableGrammarElement(final String symbolRegexp,
            final int priority) {
        super(symbolRegexp, priority);
    }

    public abstract T evaluate(final Node node, final List<T> params,
            final C context);

}
