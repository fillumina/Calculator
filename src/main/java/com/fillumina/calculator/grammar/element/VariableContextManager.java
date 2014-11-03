package com.fillumina.calculator.grammar.element;

import com.fillumina.calculator.ContextException;
import com.fillumina.calculator.GrammarElementMatcher;
import com.fillumina.calculator.GrammarElementType;
import com.fillumina.calculator.SyntaxErrorException;
import com.fillumina.calculator.grammar.AbstractComparableGrammarOperand;
import java.util.List;
import java.util.Map;

/**
 * Using this class requires the context to be of type
 * <code>Map&lt;String,T&gt;</code>.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class VariableContextManager<T>
        extends AbstractComparableGrammarOperand<T, Map<String, T>> {
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
        if (value == null || value.isEmpty()) {
            throw new SyntaxErrorException("empty expression.");
        }
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
    public GrammarElementMatcher match(String expression) {
        return ElementMatcher.NOT_FOUND;
    }
}
