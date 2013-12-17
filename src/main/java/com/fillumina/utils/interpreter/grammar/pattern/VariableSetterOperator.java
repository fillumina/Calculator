package com.fillumina.utils.interpreter.grammar.pattern;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Defines an operator that can create or set a variable in the context and
 * returns the value of the variable.
 * <p>
 * <code><pre>
 * (x = 4 / 2 + 1) * x
 * </pre></code>
 * the result is {@code 9}.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class VariableSetterOperator<T>
        extends AbstractOperator<T,Map<String, T>>
        implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final VariableSetterOperator<?> INSTANCE =
            new VariableSetterOperator<>();

    public VariableSetterOperator() {
        super("[A-Za-z\\d]+\\ *=", 0, 0, 1);
    }

    @Override
    public T evaluate(String value, List<T> params, Map<String, T> context) {
        final String nodeValue = value;
        final String varName = nodeValue.substring(0,
                nodeValue.length() - 1).trim();
        final T parameter = params.get(0);

        context.put(varName, parameter);
        return parameter;
    }
}
