package com.fillumina.calculator.grammar.pattern.instances;

import static java.lang.Math.*;
import com.fillumina.calculator.grammar.pattern.CloseParentheses;
import com.fillumina.calculator.grammar.Grammar;
import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.grammar.pattern.OpenParentheses;
import com.fillumina.calculator.grammar.pattern.AbstractOperatorSymbol;
import com.fillumina.calculator.grammar.pattern.ConstantElement;
import com.fillumina.calculator.grammar.pattern.AbstractDoublePatternElement;
import com.fillumina.calculator.grammar.pattern.VariableContextManager;
import com.fillumina.calculator.grammar.pattern.VariableSetterOperator;
import com.fillumina.calculator.grammar.pattern.WhiteSpace;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Defines a simple arithmetic grammar useful to perform calculations
 * on double values. It supports variables that can be passed through a
 * context. It can even define its own variables directly in the
 * expression using the equal symbol.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ArithmeticGrammar extends Grammar<Double, Map<String, Double>>
        implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final ArithmeticGrammar INSTANCE = new ArithmeticGrammar();

    @SuppressWarnings("unchecked")
    private ArithmeticGrammar() {
        super(
        new AbstractDoublePatternElement<Double,Map<String, Double>>(0) {
            private static final long serialVersionUID = 1L;
            
            @Override
            public Double evaluate(final String value,
                    final Map<String, Double> context) {
                return Double.valueOf(value);
            }
        },

        new AbstractOperatorSymbol<Double,Map<String, Double>>("!", 5, 1, 0) {
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

        new AbstractOperatorSymbol<Double,Map<String, Double>>("asin", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return asin(params.get(0));
            }
        },

        new AbstractOperatorSymbol<Double,Map<String, Double>>("acos", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return acos(params.get(0));
            }

        },

        new AbstractOperatorSymbol<Double,Map<String, Double>>("atan", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return atan(params.get(0));
            }
        },

        new AbstractOperatorSymbol<Double,Map<String, Double>>("sin", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return sin(params.get(0));
            }
        },

        new AbstractOperatorSymbol<Double,Map<String, Double>>("cos", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return cos(params.get(0));
            }

        },

        new AbstractOperatorSymbol<Double,Map<String, Double>>("tan", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return tan(params.get(0));
            }
        },

        new AbstractOperatorSymbol<Double,Map<String, Double>>("log", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return log10(params.get(0));
            }
        },

        new AbstractOperatorSymbol<Double,Map<String, Double>>("ln", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return log(params.get(0));
            }
        },

        new AbstractOperatorSymbol<Double,Map<String, Double>>("sqr", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return sqrt(params.get(0));
            }
        },

        // doesn't accept any parameter
        new AbstractOperatorSymbol<Double,Map<String, Double>>("rnd", 4, 0, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return Math.random();
            }
        },

        // accepts many parameters
        new AbstractOperatorSymbol<Double,Map<String, Double>>("avg", 4, 0,
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

        new AbstractOperatorSymbol<Double,Map<String, Double>>("^", 3, 1, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return pow(params.get(0), params.get(1));
            }
        },

        new AbstractOperatorSymbol<Double,Map<String, Double>>("*", 2, 1, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return params.get(0) * params.get(1);
            }
        },

        new AbstractOperatorSymbol<Double,Map<String, Double>>("/", 2, 1, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return params.get(0) / params.get(1);
            }
        },

        new AbstractOperatorSymbol<Double,Map<String, Double>>("+", 1, 1, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return params.get(0) + params.get(1);
            }
        },

        new AbstractOperatorSymbol<Double,Map<String, Double>>("-", 1, 1, 1) {
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

        (GrammarElement<Double,Map<String, Double>>)VariableSetterOperator.INSTANCE,

        (GrammarElement<Double,Map<String, Double>>)OpenParentheses.ROUND,
        (GrammarElement<Double,Map<String, Double>>)CloseParentheses.ROUND,
        new WhiteSpace<Double,Map<String, Double>>("[\\ ,]+"),

        new ConstantElement<Double,Map<String, Double>>("e", E, 0),
        new ConstantElement<Double,Map<String, Double>>("pi", PI,  0),

        new VariableContextManager<Double>()
        );
    }
}
