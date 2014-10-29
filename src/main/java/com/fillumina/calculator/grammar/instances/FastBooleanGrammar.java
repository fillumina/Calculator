package com.fillumina.calculator.grammar.instances;

import com.fillumina.calculator.grammar.Grammar;
import com.fillumina.calculator.grammar.element.AbstractFastMultiOperator;
import com.fillumina.calculator.grammar.element.FastCloseParentheses;
import com.fillumina.calculator.grammar.element.FastOpenParentheses;
import com.fillumina.calculator.grammar.element.FastVariableSetterOperator;
import com.fillumina.calculator.grammar.element.ValueFastMultiConstant;
import com.fillumina.calculator.grammar.element.VeryFastWhiteSpace;
import com.fillumina.calculator.grammar.pattern.VariableContextManager;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Defines a simple boolean grammar useful to perform simple calculations
 * on boolean values. It supports variables that can be passed through a
 * context.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class FastBooleanGrammar extends Grammar<Boolean, Map<String, Boolean>>
        implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final FastBooleanGrammar INSTANCE = new FastBooleanGrammar();

    @SuppressWarnings("unchecked")
    private FastBooleanGrammar() {
        super(new AbstractFastMultiOperator<Boolean,Map<String,Boolean>>(
                    1, 1, 1, "and", "AND", "And", "&&", "&") {
                private static final long serialVersionUID = 1L;

                @Override
                public Boolean evaluate(final String value,
                        final List<Boolean> params,
                        final Map<String,Boolean> context) {
                    return params.get(0) && params.get(1);
                }
            },

            new AbstractFastMultiOperator<Boolean,Map<String,Boolean>>(
                    1, 1, 1, "or", "OR", "Or", "||", "|") {
                private static final long serialVersionUID = 1L;

                @Override
                public Boolean evaluate(final String value,
                        final List<Boolean> params,
                        final Map<String,Boolean> context) {
                    return params.get(0) || params.get(1);
                }
            },

            new AbstractFastMultiOperator<Boolean,Map<String,Boolean>>(
                    1, 0, 1, "not", "NOT", "Not", "~", "!") {
                private static final long serialVersionUID = 1L;

                @Override
                public Boolean evaluate(final String value,
                        final List<Boolean> params,
                        final Map<String,Boolean> context) {
                    return !params.get(0);
                }
            },

            FastVariableSetterOperator.<Boolean>instance(),

            FastOpenParentheses.<Boolean,Map<String,Boolean>>round(),

            FastCloseParentheses.<Boolean,Map<String,Boolean>>round(),

            VeryFastWhiteSpace.<Boolean,Map<String,Boolean>>instance(),

            new ValueFastMultiConstant<Boolean,Map<String,Boolean>>(
                    true, 0, "true", "TRUE", "True"),
            new ValueFastMultiConstant<Boolean,Map<String,Boolean>>(
                    false, 0, "false", "FALSE", "False"),

            new VariableContextManager<Boolean>()
        );
    }
}
