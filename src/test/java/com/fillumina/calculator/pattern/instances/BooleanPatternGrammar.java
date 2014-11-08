package com.fillumina.calculator.pattern.instances;

import com.fillumina.calculator.element.VariableContextManager;
import com.fillumina.calculator.grammar.Grammar;
import com.fillumina.calculator.pattern.AbstractPatternOperator;
import com.fillumina.calculator.pattern.PatternCloseParentheses;
import com.fillumina.calculator.pattern.PatternConstantElement;
import com.fillumina.calculator.pattern.PatternOpenParentheses;
import com.fillumina.calculator.pattern.PatternWhiteSpace;
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
public class BooleanPatternGrammar extends Grammar<Boolean, Map<String, Boolean>>
        implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final BooleanPatternGrammar INSTANCE = new BooleanPatternGrammar();

    @SuppressWarnings("unchecked")
    private BooleanPatternGrammar() {
        super(//TODO spaces around letters is required
            new AbstractPatternOperator<Boolean,Map<String,Boolean>>(
                    "(and|AND|And|&&|&)", 1, 1, 1) {
                private static final long serialVersionUID = 1L;

                @Override
                public Boolean evaluate(final String value,
                        final List<Boolean> params,
                        final Map<String,Boolean> context) {
                    return params.get(0) && params.get(1);
                }
            },

            new AbstractPatternOperator<Boolean,Map<String,Boolean>>(
                    "(or|OR|Or|\\|\\||\\|)", 1, 1, 1) {
                private static final long serialVersionUID = 1L;

                @Override
                public Boolean evaluate(final String value,
                        final List<Boolean> params,
                        final Map<String,Boolean> context) {
                    return params.get(0) || params.get(1);
                }
            },

            new AbstractPatternOperator<Boolean,Map<String,Boolean>>(
                    "(not|NOT|Not|~|!)", 1, 0, 1) {
                private static final long serialVersionUID = 1L;

                @Override
                public Boolean evaluate(final String value,
                        final List<Boolean> params,
                        final Map<String,Boolean> context) {
                    return !params.get(0);
                }
            },

            new PatternOpenParentheses<Boolean,Map<String,Boolean>>("\\("),
            new PatternCloseParentheses<Boolean,Map<String,Boolean>>("\\)"),
            new PatternWhiteSpace<Boolean,Map<String,Boolean>>("[\\ ,]+"),

            new PatternConstantElement<Boolean,Map<String,Boolean>>(
                    "true|TRUE|True", true, 0),
            new PatternConstantElement<Boolean,Map<String,Boolean>>(
                    "false|FALSE|False", false, 0),

            new VariableContextManager<Boolean>()
        );
    }
}
