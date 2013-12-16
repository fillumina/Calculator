package com.fillumina.utils.interpreter.grammar.fast.instances;

import static java.lang.Math.*;
import com.fillumina.utils.interpreter.Grammar;
import com.fillumina.utils.interpreter.GrammarElement;
import com.fillumina.utils.interpreter.grammar.fast.NumberOperandGrammarElement;
import com.fillumina.utils.interpreter.grammar.fast.FastCloseParentheses;
import com.fillumina.utils.interpreter.grammar.fast.FastOperatorGrammarElement;
import com.fillumina.utils.interpreter.grammar.fast.FastConstantGrammarElement;
import com.fillumina.utils.interpreter.grammar.fast.FastOpenParentheses;
import com.fillumina.utils.interpreter.grammar.fast.FastWhiteSpace;
import com.fillumina.utils.interpreter.grammar.pattern.VariableContextManager;
import com.fillumina.utils.interpreter.grammar.pattern.VariableSetterOperator;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Defines a simple arithmetic grammar useful to perform calculations
 * on double values. It supports variables that can be passed through a
 * context. It can even define its own variables directly in the
 * expression using the equal symbol.
 * It uses the faster alternatives to most of its components.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class FastArithmeticGrammar extends Grammar<Double, Map<String, Double>>
        implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final FastArithmeticGrammar INSTANCE =
            new FastArithmeticGrammar();

    @SuppressWarnings("unchecked")
    private FastArithmeticGrammar() {
        super(
        new NumberOperandGrammarElement<Double,Map<String, Double>>(0, false),

        new FastOperatorGrammarElement<Double,Map<String, Double>>("!", 5, 1, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                final Double number = params.get(0);
                final long ivalue = round(number);
                long result = 1;
                for (long l=1; l<=ivalue; l++) {
                    result *= l;
                }
                return Double.valueOf(String.valueOf(result));
            }
        },

        new FastOperatorGrammarElement<Double,Map<String, Double>>("asin", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return asin(params.get(0));
            }
        },

        new FastOperatorGrammarElement<Double,Map<String, Double>>("acos", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return acos(params.get(0));
            }

        },

        new FastOperatorGrammarElement<Double,Map<String, Double>>("atan", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return atan(params.get(0));
            }
        },

        new FastOperatorGrammarElement<Double,Map<String, Double>>("sin", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return sin(params.get(0));
            }
        },

        new FastOperatorGrammarElement<Double,Map<String, Double>>("cos", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return cos(params.get(0));
            }

        },

        new FastOperatorGrammarElement<Double,Map<String, Double>>("tan", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return tan(params.get(0));
            }
        },

        new FastOperatorGrammarElement<Double,Map<String, Double>>("log", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return log10(params.get(0));
            }
        },

        new FastOperatorGrammarElement<Double,Map<String, Double>>("ln", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return log(params.get(0));
            }
        },

        new FastOperatorGrammarElement<Double,Map<String, Double>>("sqr", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return sqrt(params.get(0));
            }
        },

        // doesn't accept any parameter
        new FastOperatorGrammarElement<Double,Map<String, Double>>("rnd", 4, 0, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return Math.random();
            }
        },

        // accepts many parameters
        new FastOperatorGrammarElement<Double,Map<String, Double>>("avg", 4, 0,
                Integer.MAX_VALUE) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                final int size = params.size();
                if (size == 0) {
                    return 0.0;
                }
                double total = 0;
                for (Double d: params) {
                    total += d;
                }
                return total / size;
            }
        },

        new FastOperatorGrammarElement<Double,Map<String, Double>>("^", 3, 1, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return pow(params.get(0), params.get(1));
            }
        },

        new FastOperatorGrammarElement<Double,Map<String, Double>>("*", 2, 1, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return params.get(0) * params.get(1);
            }
        },

        new FastOperatorGrammarElement<Double,Map<String, Double>>("/", 2, 1, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return params.get(0) / params.get(1);
            }
        },

        new FastOperatorGrammarElement<Double,Map<String, Double>>("+", 1, 1, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return params.get(0) + params.get(1);
            }
        },

        new FastOperatorGrammarElement<Double,Map<String, Double>>("-", 1, 1, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                if (params.size() == 1) {
                    return - params.get(0);
                }
                return params.get(0) - params.get(1);
            }
        },

        new VariableSetterOperator<Double>(),

        (GrammarElement<Double,Map<String, Double>>)FastOpenParentheses.ROUND,
        (GrammarElement<Double,Map<String, Double>>)FastCloseParentheses.ROUND,

        (GrammarElement<Double,Map<String, Double>>)FastWhiteSpace.INSTANCE,

        new FastConstantGrammarElement<Double,Map<String, Double>>("e", E, 0),
        new FastConstantGrammarElement<Double,Map<String, Double>>("pi", PI,  0),

        new VariableContextManager<Double>()
        );
    }
}
