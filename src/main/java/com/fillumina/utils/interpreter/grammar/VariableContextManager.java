package com.fillumina.utils.interpreter.grammar;

import com.fillumina.utils.interpreter.arithmetic.ContextException;
import java.util.List;
import java.util.Map;

/**
 * Using this class requires the context to be of type
 * <code>Map&lt;String,T&gt;</code>.
 * 
 * @author fra
 */
public class VariableContextManager<T>
        extends AbstractUnrecognizedElement<T, Map<String, T>> {
    private static final long serialVersionUID = 1L;

    @Override
    public T eval(final String value, final List<T> params,
            final Map<String, T> context) {
        final T variableValue = context.get(value);
        if (variableValue == null) {
            throw new ContextException(value);
        }
        return variableValue;
    }
}
