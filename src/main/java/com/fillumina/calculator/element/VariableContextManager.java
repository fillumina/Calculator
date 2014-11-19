package com.fillumina.calculator.element;

import com.fillumina.calculator.ContextException;
import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.GrammarElementMatcher;
import com.fillumina.calculator.GrammarElementType;
import com.fillumina.calculator.grammar.AbstractComparableGrammarElement;
import java.util.List;
import java.util.Map;

/**
 * If a element cannot be recognize in the grammar it is recognized as
 * {@link GrammarElementType#UNRECOGNIZED} and assigned to this element if
 * present. It is then searched in the context and its value is used here if
 * found.
 * <br>
 * Using this class requires the context to be of type
 * <code>Map&lt;String,T&gt;</code>.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class VariableContextManager<T>
        extends AbstractComparableGrammarElement<T, Map<String, T>> {
    private static final long serialVersionUID = 1L;

    private static final VariableContextManager<?> INSTANCE =
            new VariableContextManager<>();

    @SuppressWarnings("unchecked")
    public static final <T> VariableContextManager<T> instance() {
        return (VariableContextManager<T>) INSTANCE;
    }

    public VariableContextManager() {
        super(0);
    }

    @Override
    public T evaluate(final String value, final List<T> params,
            final Map<String, T> context) {
        if (context == null) {
            throw new ContextException(value);
        }
        final T variableValue = context.get(value);
        if (variableValue == null) {
            throw new ContextException(value);
        }
        return variableValue;
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.UNRECOGNIZED;
    }

    @Override
    public GrammarElementMatcher match(
            final GrammarElement<T,Map<String,T>> previousGrammarElement,
            final String expression) {
        return ElementMatcher.NOT_FOUND;
    }
}
