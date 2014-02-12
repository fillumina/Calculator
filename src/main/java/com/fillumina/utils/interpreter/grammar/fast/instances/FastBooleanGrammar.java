package com.fillumina.utils.interpreter.grammar.fast.instances;

import com.fillumina.utils.interpreter.Grammar;
import com.fillumina.utils.interpreter.GrammarElement;
import com.fillumina.utils.interpreter.grammar.fast.FastCloseParentheses;
import com.fillumina.utils.interpreter.grammar.fast.ValueFastMultiConstant;
import com.fillumina.utils.interpreter.grammar.fast.AbstractFastMultiOperator;
import com.fillumina.utils.interpreter.grammar.fast.FastOpenParentheses;
import com.fillumina.utils.interpreter.grammar.fast.FastVariableSetterOperator;
import com.fillumina.utils.interpreter.grammar.fast.VeryFastWhiteSpace;
import com.fillumina.utils.interpreter.grammar.pattern.VariableContextManager;
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
        super(
            new AbstractFastMultiOperator<Boolean,Map<String,Boolean>>(
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

            (GrammarElement<Boolean,Map<String, Boolean>>)
                    FastVariableSetterOperator.INSTANCE,

            (GrammarElement<Boolean,Map<String, Boolean>>)
                    FastOpenParentheses.ROUND,

            (GrammarElement<Boolean,Map<String, Boolean>>)
                    FastCloseParentheses.ROUND,

            (GrammarElement<Boolean,Map<String, Boolean>>)
                    VeryFastWhiteSpace.INSTANCE,

            new ValueFastMultiConstant<Boolean,Map<String,Boolean>>(
                    true, 0, "true", "TRUE", "True"),
            new ValueFastMultiConstant<Boolean,Map<String,Boolean>>(
                    false, 0, "false", "FALSE", "False"),

            new VariableContextManager<Boolean>()
        );
    }
}
