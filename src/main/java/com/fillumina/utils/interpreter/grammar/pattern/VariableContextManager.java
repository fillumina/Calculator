package com.fillumina.utils.interpreter.grammar.pattern;

import com.fillumina.utils.interpreter.ContextException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Using this class requires the context to be of type
 * <code>Map&lt;String,T&gt;</code>.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class VariableContextManager<T>
        extends AbstractUnrecognizedElement<T, Map<String, T>> {
    private static final long serialVersionUID = 1L;

    @Override
    public T evaluate(final String value, final List<T> params,
            final Map<String, T> context) {
        Objects.requireNonNull(context, "context must be not null");
        final T variableValue = context.get(value);
        if (variableValue == null) {
            throw new ContextException(value);
        }
        return variableValue;
    }
}