package com.fillumina.utils.interpreter.grammar;

import com.fillumina.utils.interpreter.GrammarElement.Type;
import java.util.List;

/**
 *
 * @author fra
 */
public class ConstantElement<T,C>
        extends AbstractEvaluableGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;
    private final T constant;

    public ConstantElement(final String symbolRegexp, final T constant,
            final int priority) {
        super(symbolRegexp, priority);
        this.constant = constant;
    }

    @Override
    public T eval(final String value,
            final List<T> params, final C context) {
        return constant;
    }

    @Override
    public boolean isType(final Type type) {
        return Type.OPERAND.equals(type);
    }

}
