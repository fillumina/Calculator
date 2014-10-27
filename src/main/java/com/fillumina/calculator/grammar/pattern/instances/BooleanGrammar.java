package com.fillumina.calculator.grammar.pattern.instances;

import com.fillumina.calculator.grammar.pattern.CloseParentheses;
import com.fillumina.calculator.grammar.Grammar;
import com.fillumina.calculator.grammar.pattern.OpenParentheses;
import com.fillumina.calculator.grammar.pattern.AbstractOperator;
import com.fillumina.calculator.grammar.pattern.ConstantElement;
import com.fillumina.calculator.grammar.pattern.VariableContextManager;
import com.fillumina.calculator.grammar.pattern.WhiteSpace;
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
        super(
                //TODO spaces around letters is required
            new AbstractOperator<Boolean,Map<String,Boolean>>(
                    "(and|AND|And|&&|&)", 1, 1, 1) {
                private static final long serialVersionUID = 1L;

                @Override
                public Boolean evaluate(final String value,
                        final List<Boolean> params,
                        final Map<String,Boolean> context) {
                    return params.get(0) && params.get(1);
                }
            },

            new AbstractOperator<Boolean,Map<String,Boolean>>(
                    "(or|OR|Or|\\|\\||\\|)", 1, 1, 1) {
                private static final long serialVersionUID = 1L;

                @Override
                public Boolean evaluate(final String value,
                        final List<Boolean> params,
                        final Map<String,Boolean> context) {
                    return params.get(0) || params.get(1);
                }
            },

            new AbstractOperator<Boolean,Map<String,Boolean>>(
                    "(not|NOT|Not|~|!)", 1, 0, 1) {
                private static final long serialVersionUID = 1L;

                @Override
                public Boolean evaluate(final String value,
                        final List<Boolean> params,
                        final Map<String,Boolean> context) {
                    return !params.get(0);
                }
            },

            new OpenParentheses<Boolean,Map<String,Boolean>>("\\("),
            new CloseParentheses<Boolean,Map<String,Boolean>>("\\)"),
            new WhiteSpace<Boolean,Map<String,Boolean>>("[\\ ,]+"),

            new ConstantElement<Boolean,Map<String,Boolean>>(
                    "true|TRUE|True", true, 0),
            new ConstantElement<Boolean,Map<String,Boolean>>(
                    "false|FALSE|False", false, 0),

            new VariableContextManager<Boolean>()
        );
    }
}
