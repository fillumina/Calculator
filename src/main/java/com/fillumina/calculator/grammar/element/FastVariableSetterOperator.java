package com.fillumina.calculator.grammar.element;

import com.fillumina.calculator.GrammarElementMatcher;
import com.fillumina.calculator.grammar.AbstractComparableGrammarElement;
import com.fillumina.calculator.grammar.GrammarElementType;
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
public class FastVariableSetterOperator<T>
        extends AbstractComparableGrammarElement<T,Map<String, T>>
        implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final FastVariableSetterOperator<?> INSTANCE =
            new FastVariableSetterOperator<>(0);

    @SuppressWarnings("unchecked")
    public static <T> FastVariableSetterOperator<T> instance() {
        return (FastVariableSetterOperator<T>) INSTANCE;
    }

    public FastVariableSetterOperator(final int priority) {
        super(priority);
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

    //"[A-Za-z\\d]+\\ *="
    @Override
    public GrammarElementMatcher match(final String expression) {
        final int idx = expression.indexOf('=');
        if (idx == -1 || idx == 0) {
            return FastElementMatcher.NOT_FOUND;
        }
        //if (true) throw new RuntimeException("GOTCHA");
        final char[] carray = expression.toCharArray();
        boolean variable = false;
        for (int i=idx-1; i>=0; i--) {
            final char c = carray[i];
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') ||
                    (c >= '0' && c <= '9')) {
                variable = true;
            } else if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
                if (variable) {
                    return new FastElementMatcher(i + 1, idx + 1);
                }
            } else {
                if (variable) {
                    return new FastElementMatcher(i + 1, idx + 1);
                } else {
                    return FastElementMatcher.NOT_FOUND;
                }
            }
        }
        if (variable) {
            return new FastElementMatcher(0, idx + 1);
        }
        return FastElementMatcher.NOT_FOUND;
    }

    @Override
    public int getRequiredOperandsAfter() {
        return 1;
    }

    @Override
    public int getRequiredOperandsBefore() {
        return 0;
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.OPERATOR;
    }
}
