package com.fillumina.calculator.grammar.instance;

import com.fillumina.calculator.grammar.Grammar;
import com.fillumina.calculator.grammar.element.AbstractMultiOperator;
import com.fillumina.calculator.grammar.element.CloseParentheses;
import com.fillumina.calculator.grammar.element.OpenParentheses;
import com.fillumina.calculator.grammar.element.VariableSetterOperator;
import com.fillumina.calculator.grammar.element.ValuedMultiConstant;
import com.fillumina.calculator.grammar.element.VariableContextManager;
import com.fillumina.calculator.grammar.element.FastWhiteSpace;
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
public class BooleanGrammar extends Grammar<Boolean, Map<String, Boolean>>
        implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final BooleanGrammar INSTANCE = new BooleanGrammar();

    @SuppressWarnings("unchecked")
    private BooleanGrammar() {
        super(new AbstractMultiOperator<Boolean,Map<String,Boolean>>(
                    1, 1, 1, "and", "AND", "And", "&&", "&") {
                private static final long serialVersionUID = 1L;

                @Override
                public Boolean evaluate(final String value,
                        final List<Boolean> params,
                        final Map<String,Boolean> context) {
                    return params.get(0) && params.get(1);
                }
            },

            new AbstractMultiOperator<Boolean,Map<String,Boolean>>(
                    1, 1, 1, "or", "OR", "Or", "||", "|") {
                private static final long serialVersionUID = 1L;

                @Override
                public Boolean evaluate(final String value,
                        final List<Boolean> params,
                        final Map<String,Boolean> context) {
                    return params.get(0) || params.get(1);
                }
            },

            new AbstractMultiOperator<Boolean,Map<String,Boolean>>(
                    1, 0, 1, "not", "NOT", "Not", "~", "!") {
                private static final long serialVersionUID = 1L;

                @Override
                public Boolean evaluate(final String value,
                        final List<Boolean> params,
                        final Map<String,Boolean> context) {
                    return !params.get(0);
                }
            },

            VariableSetterOperator.<Boolean>instance(),

            OpenParentheses.<Boolean,Map<String,Boolean>>round(),

            CloseParentheses.<Boolean,Map<String,Boolean>>round(),

            FastWhiteSpace.<Boolean,Map<String,Boolean>>instance(),

            new ValuedMultiConstant<Boolean,Map<String,Boolean>>(
                    true, 0, "true", "TRUE", "True"),
            new ValuedMultiConstant<Boolean,Map<String,Boolean>>(
                    false, 0, "false", "FALSE", "False"),

            new VariableContextManager<Boolean>()
        );
    }
}
