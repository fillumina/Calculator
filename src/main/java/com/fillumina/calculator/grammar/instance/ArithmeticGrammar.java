package com.fillumina.calculator.grammar.instance;

import com.fillumina.calculator.grammar.Grammar;
import com.fillumina.calculator.grammar.element.AbstractDoubleOperand;
import com.fillumina.calculator.grammar.element.AbstractOperator;
import com.fillumina.calculator.grammar.element.CloseParentheses;
import com.fillumina.calculator.grammar.element.ConstantOperand;
import com.fillumina.calculator.grammar.element.FastWhiteSpace;
import com.fillumina.calculator.grammar.element.OpenParentheses;
import com.fillumina.calculator.grammar.element.VariableContextManager;
import com.fillumina.calculator.grammar.element.VariableSetterOperator;
import java.io.Serializable;
import static java.lang.Math.E;
import static java.lang.Math.PI;
import static java.lang.Math.acos;
import static java.lang.Math.asin;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.log;
import static java.lang.Math.log10;
import static java.lang.Math.pow;
import static java.lang.Math.round;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.tan;
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
public class ArithmeticGrammar extends Grammar<Double, Map<String, Double>>
        implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final ArithmeticGrammar INSTANCE =
            new ArithmeticGrammar();

    @SuppressWarnings("unchecked")
    private ArithmeticGrammar() {
        super(new AbstractDoubleOperand<Double,Map<String, Double>>(0) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return Double.valueOf(value);
            }
        },

        new AbstractOperator<Double,Map<String, Double>>(5, 1, 0, "!") {
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

        new AbstractOperator<Double,Map<String, Double>>(4, 0, 1, "asin") {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return asin(params.get(0));
            }
        },

        new AbstractOperator<Double,Map<String, Double>>(4, 0, 1, "acos") {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return acos(params.get(0));
            }

        },

        new AbstractOperator<Double,Map<String, Double>>(4, 0, 1, "atan") {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return atan(params.get(0));
            }
        },

        new AbstractOperator<Double,Map<String, Double>>(4, 0, 1, "sin") {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return sin(params.get(0));
            }
        },

        new AbstractOperator<Double,Map<String, Double>>(4, 0, 1, "cos") {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return cos(params.get(0));
            }

        },

        new AbstractOperator<Double,Map<String, Double>>(4, 0, 1, "tan") {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return tan(params.get(0));
            }
        },

        new AbstractOperator<Double,Map<String, Double>>(4, 0, 1, "log") {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return log10(params.get(0));
            }
        },

        new AbstractOperator<Double,Map<String, Double>>(4, 0, 1, "ln") {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return log(params.get(0));
            }
        },

        new AbstractOperator<Double,Map<String, Double>>(4, 0, 1, "sqr") {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return sqrt(params.get(0));
            }
        },

        new AbstractOperator<Double,Map<String, Double>>(4, 0, 0, "rnd") {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return Math.random();
            }
        },

        new AbstractOperator<Double,Map<String, Double>>(4, 0,
                Integer.MAX_VALUE, "avg") {
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

        new AbstractOperator<Double,Map<String, Double>>(3, 1, 1, "^") {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return pow(params.get(0), params.get(1));
            }
        },

        new AbstractOperator<Double,Map<String, Double>>(2, 1, 1, "*") {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return params.get(0) * params.get(1);
            }
        },

        new AbstractOperator<Double,Map<String, Double>>(2, 1, 1, "/") {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return params.get(0) / params.get(1);
            }
        },

        new AbstractOperator<Double,Map<String, Double>>(1, 1, 1, "+") {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final String value, final List<Double> params,
                    final Map<String, Double> context) {
                return params.get(0) + params.get(1);
            }
        },

        new AbstractOperator<Double,Map<String, Double>>(1, 1, 1, "-") {
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

        VariableSetterOperator.<Double>instance(),

        OpenParentheses.<Double,Map<String, Double>>round(),
        CloseParentheses.<Double,Map<String, Double>>round(),

        FastWhiteSpace.<Double,Map<String, Double>>instance(),

        new ConstantOperand<Double,Map<String, Double>>("e", E, 0),
        new ConstantOperand<Double,Map<String, Double>>("pi", PI,  0),

        new VariableContextManager<Double>()
        );
    }
}
