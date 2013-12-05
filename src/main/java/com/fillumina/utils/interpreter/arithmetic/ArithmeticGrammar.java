package com.fillumina.utils.interpreter.arithmetic;

import static java.lang.Math.*;
import com.fillumina.utils.interpreter.grammar.CloseParenthesis;
import com.fillumina.utils.interpreter.grammar.AbstractOperand;
import com.fillumina.utils.interpreter.Grammar;
import com.fillumina.utils.interpreter.grammar.OpenParenthesis;
import com.fillumina.utils.interpreter.grammar.AbstractOperator;
import com.fillumina.utils.interpreter.grammar.ConstantElement;
import com.fillumina.utils.interpreter.grammar.VariableContextManager;
import com.fillumina.utils.interpreter.grammar.WhiteSpace;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Defines a simple arithmetic grammar useful to perform simple calculations
 * on double values. It supports variables that can be passed through a
 * context.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ArithmeticGrammar extends Grammar<Double, Map<String, Double>>
        implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final ArithmeticGrammar INSTANCE = new ArithmeticGrammar();

    private ArithmeticGrammar() {
        super(
        // DECIMAL in scientific notation
        // the problem here is that the - and + symbols shouldn't be included
        // in the number if there is a digit before (eventually separated by spaces)
        // see http://www.regular-expressions.info/refadv.html
        //
        // (\\D\\ {100}) is wrong but works recognizing non digit chars
        //
        new AbstractOperand<Double,Map<String, Double>>(
                "((?<=(([\\*\\+\\-/^]\\ {0,100})|" +
                "(\\D\\ {100})|(\\(\\ {0,100})))[\\+\\-])?" +
                "\\d+(\\.\\d+)?([Ee][\\+\\-]?\\d+)?", 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value,
                    final Map<String, Double> context) {
                return Double.parseDouble(value);
            }
        },

        new AbstractOperator<Double,Map<String, Double>>("!", 5, 1, 0) {
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

        new AbstractOperator<Double,Map<String, Double>>("asin", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return asin(params.get(0));
            }
        },

        new AbstractOperator<Double,Map<String, Double>>("acos", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return acos(params.get(0));
            }

        },

        new AbstractOperator<Double,Map<String, Double>>("atan", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return atan(params.get(0));
            }
        },

        new AbstractOperator<Double,Map<String, Double>>("sin", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return sin(params.get(0));
            }
        },

        new AbstractOperator<Double,Map<String, Double>>("cos", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return cos(params.get(0));
            }

        },

        new AbstractOperator<Double,Map<String, Double>>("tan", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return tan(params.get(0));
            }
        },

        new AbstractOperator<Double,Map<String, Double>>("log", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return log10(params.get(0));
            }
        },

        new AbstractOperator<Double,Map<String, Double>>("ln", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return log(params.get(0));
            }
        },

        new AbstractOperator<Double,Map<String, Double>>("sqr", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return sqrt(params.get(0));
            }
        },

        // doesn't accept any parameter
        new AbstractOperator<Double,Map<String, Double>>("rnd", 4, 0, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return Math.random();
            }
        },

        // accepts many parameters
        new AbstractOperator<Double,Map<String, Double>>("avg", 4, 0,
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

        new AbstractOperator<Double,Map<String, Double>>("\\^", 3, 1, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return pow(params.get(0), params.get(1));
            }
        },

        new AbstractOperator<Double,Map<String, Double>>("\\*", 2, 1, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return params.get(0) * params.get(1);
            }
        },

        new AbstractOperator<Double,Map<String, Double>>("/", 2, 1, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return params.get(0) / params.get(1);
            }
        },

        new AbstractOperator<Double,Map<String, Double>>("\\+", 1, 1, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return params.get(0) + params.get(1);
            }
        },

        new AbstractOperator<Double,Map<String, Double>>("\\-", 1, 1, 1) {
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

        new AbstractOperator<Double,Map<String, Double>>("[A-Za-z]+\\ *=", 0, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                final String nodeValue = value;
                final String varName = nodeValue.substring(0,
                        nodeValue.length() - 1).trim();
                final Double parameter = params.get(0);

                context.put(varName, parameter);
                return parameter;
            }
        },

        new ConstantElement<Double,Map<String, Double>>("e", E, 0),
        new ConstantElement<Double,Map<String, Double>>("pi", PI,  0),

        new VariableContextManager<Double>(),

        new OpenParenthesis<Double,Map<String, Double>>("\\("),
        new CloseParenthesis<Double,Map<String, Double>>("\\)"),
        new WhiteSpace<Double,Map<String, Double>>("[\\ ,]+")
        );
    }
}
