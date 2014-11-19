package com.fillumina.calculator.grammar.instance;

import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.grammar.ContextedGrammarBuilder;
import com.fillumina.calculator.grammar.Evaluator;
import com.fillumina.calculator.grammar.Grammar;
import com.fillumina.calculator.grammar.ParametricEvaluator;
import static java.lang.Math.E;
import static java.lang.Math.PI;
import static java.lang.Math.acos;
import static java.lang.Math.asin;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.log;
import static java.lang.Math.log10;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.tan;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Defines a simple arithmetic grammar useful to perform calculations
 * on {@link BigDecimal} values. Remember that all trigonometric functions
 * and some other (like SQRT) are performed using double arithmetic and so
 * they are not exact. The scale of the results is actually set to 16.
 *
 * It supports variables that can be passed through a
 * context. It can even define its own variables directly in the
 * expression using the equal symbol.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DecimalArithmeticGrammar
        extends Grammar<BigDecimal,Map<String,BigDecimal>> {
    private static final long serialVersionUID = 1L;
    private static final int SCALE = 16;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    // just a shortcut to avoid writing the type every time
    private static interface DecEvaluator
            extends Evaluator<BigDecimal, Map<String, BigDecimal>> {
    }

    // just a shortcut to avoid writing the type every time
    private static interface DecParmEvaluator
            extends ParametricEvaluator<BigDecimal, Map<String, BigDecimal>> {
    }

    public static final Iterable
            <GrammarElement<BigDecimal,Map<String,BigDecimal>>> ITERABLE =
        new ContextedGrammarBuilder<BigDecimal>()
                .addFloatingPointOperand(new DecEvaluator() {
                    @Override
                    public BigDecimal evaluate(String value,
                            Map<String, BigDecimal> context) {
                        return new BigDecimal(value);
                    }
                })

                .addOperator()
                    .priority(5)
                    .symbols("!")
                    .operandsBefore(1)
                    .operandsAfter(0)
                    .evaluator(new DecParmEvaluator() {
                        @Override
                        public BigDecimal evaluate(String value, List<BigDecimal> params,
                                Map<String, BigDecimal> context) {
                            final BigDecimal number = params.get(0);
                            final long ivalue = number.intValue();
                            long result = 1;
                            for (long l=1; l<=ivalue; l++) {
                                result *= l;
                            }
                            return BigDecimal.valueOf(result);
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(4)
                    .symbols("asin")
                    .operandsBefore(0)
                    .operandsAfter(1)
                    .evaluator(new DecParmEvaluator() {
                        @Override
                        public BigDecimal evaluate(String value, List<BigDecimal> params,
                                Map<String, BigDecimal> context) {
                            return BigDecimal.valueOf(asin(params.get(0).doubleValue()));
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(4)
                    .symbols("acos")
                    .operandsBefore(0)
                    .operandsAfter(1)
                    .evaluator(new DecParmEvaluator() {
                        @Override
                        public BigDecimal evaluate(String value, List<BigDecimal> params,
                                Map<String, BigDecimal> context) {
                            return BigDecimal.valueOf(acos(params.get(0).doubleValue()));
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(4)
                    .symbols("atan")
                    .operandsBefore(0)
                    .operandsAfter(1)
                    .evaluator(new DecParmEvaluator() {
                        @Override
                        public BigDecimal evaluate(String value, List<BigDecimal> params,
                                Map<String, BigDecimal> context) {
                            return BigDecimal.valueOf(atan(params.get(0).doubleValue()));
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(4)
                    .symbols("sin")
                    .operandsBefore(0)
                    .operandsAfter(1)
                    .evaluator(new DecParmEvaluator() {
                        @Override
                        public BigDecimal evaluate(String value, List<BigDecimal> params,
                                Map<String, BigDecimal> context) {
                            return BigDecimal.valueOf(sin(params.get(0).doubleValue()));
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(4)
                    .symbols("cos")
                    .operandsBefore(0)
                    .operandsAfter(1)
                    .evaluator(new DecParmEvaluator() {
                        @Override
                        public BigDecimal evaluate(String value, List<BigDecimal> params,
                                Map<String, BigDecimal> context) {
                            return BigDecimal.valueOf(cos(params.get(0).doubleValue()));
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(4)
                    .symbols("tan")
                    .operandsBefore(0)
                    .operandsAfter(1)
                    .evaluator(new DecParmEvaluator() {
                        @Override
                        public BigDecimal evaluate(String value, List<BigDecimal> params,
                                Map<String, BigDecimal> context) {
                            return BigDecimal.valueOf(tan(params.get(0).doubleValue()));
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(4)
                    .symbols("log")
                    .operandsBefore(0)
                    .operandsAfter(1)
                    .evaluator(new DecParmEvaluator() {
                        @Override
                        public BigDecimal evaluate(String value, List<BigDecimal> params,
                                Map<String, BigDecimal> context) {
                            return BigDecimal.valueOf(log10(params.get(0).doubleValue()));
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(4)
                    .symbols("ln")
                    .operandsBefore(0)
                    .operandsAfter(1)
                    .evaluator(new DecParmEvaluator() {
                        @Override
                        public BigDecimal evaluate(String value, List<BigDecimal> params,
                                Map<String, BigDecimal> context) {
                            return BigDecimal.valueOf(log(params.get(0).doubleValue()));
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(4)
                    .symbols("sqrt", "sqr")
                    .operandsBefore(0)
                    .operandsAfter(1)
                    .evaluator(new DecParmEvaluator() {
                        @Override
                        public BigDecimal evaluate(String value, List<BigDecimal> params,
                                Map<String, BigDecimal> context) {
                            return BigDecimal.valueOf(sqrt(params.get(0).doubleValue()));
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(4)
                    .symbols("rnd")
                    .operandsBefore(0)
                    .operandsAfter(0)
                    .evaluator(new DecParmEvaluator() {
                        @Override
                        public BigDecimal evaluate(String value, List<BigDecimal> params,
                                Map<String, BigDecimal> context) {
                            return BigDecimal.valueOf(
                                    ThreadLocalRandom.current().nextDouble());
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(4)
                    .symbols("avg")
                    .operandsBefore(0)
                    .allAvailableOperandsAfter()
                    .evaluator(new DecParmEvaluator() {
                        @Override
                        public BigDecimal evaluate(String value, List<BigDecimal> params,
                                Map<String, BigDecimal> context) {
                            final int size = params.size();
                            if (size == 0) {
                                return BigDecimal.ZERO;
                            }
                            BigDecimal total = BigDecimal.ZERO;
                            for (BigDecimal d: params) {
                                total = total.add(d);
                            }
                            return total.divide(BigDecimal.valueOf(size));
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(3)
                    .symbols("^")
                    .operandsBefore(1)
                    .operandsAfter(1)
                    .evaluator(new DecParmEvaluator() {
                        @Override
                        public BigDecimal evaluate(String value,
                                List<BigDecimal> params,
                                Map<String, BigDecimal> context) {
                            final int exponent = params.get(1).intValue();
                            switch (Integer.compare(exponent, 0)) {
                                case -1:
                                    return BigDecimal.ONE
                                    .divide(params.get(0)
                                        .pow(-exponent), SCALE, ROUNDING_MODE);

                                case 0:
                                    return BigDecimal.ONE;

                                case 1:
                                    return params.get(0).pow(exponent);

                                default:
                                    throw new AssertionError("WTF?");
                            }
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(2)
                    .symbols("*")
                    .operandsBefore(1)
                    .operandsAfter(1)
                    .evaluator(new DecParmEvaluator() {
                        @Override
                        public BigDecimal evaluate(String value, List<BigDecimal> params,
                                Map<String, BigDecimal> context) {
                            return params.get(0).multiply(params.get(1));
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(2)
                    .symbols("/")
                    .operandsBefore(1)
                    .operandsAfter(1)
                    .evaluator(new DecParmEvaluator() {
                        @Override
                        public BigDecimal evaluate(String value, List<BigDecimal> params,
                                Map<String, BigDecimal> context) {
                            return params.get(0)
                                    .divide(params.get(1), SCALE, ROUNDING_MODE);
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(1)
                    .symbols("+")
                    .operandsBefore(1)
                    .operandsAfter(1)
                    .evaluator(new DecParmEvaluator() {
                        @Override
                        public BigDecimal evaluate(String value, List<BigDecimal> params,
                                Map<String, BigDecimal> context) {
                            if (params.size() == 1) {
                                // + signum
                                return params.get(0);
                            }
                            return params.get(0).add(params.get(1));
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(1)
                    .symbols("-")
                    .operandsBefore(1)
                    .operandsAfter(1)
                    .evaluator(new DecParmEvaluator() {
                        @Override
                        public BigDecimal evaluate(String value, List<BigDecimal> params,
                                Map<String, BigDecimal> context) {
                            if (params.size() == 1) {
                                // - signum
                                return params.get(0).negate();
                            }
                            return params.get(0).subtract(params.get(1));
                        }
                    })
                    .buildOperator()

                .addConstant("E", BigDecimal.valueOf(E))
                .addConstant("PI", BigDecimal.valueOf(PI))
        .buildDefaultGrammarWithSettableVariables();

    @SuppressWarnings("unchecked")
    public static final DecimalArithmeticGrammar INSTANCE =
            new DecimalArithmeticGrammar(ITERABLE);

    private DecimalArithmeticGrammar(
            final Iterable<GrammarElement<BigDecimal, Map<String, BigDecimal>>>... iterables) {
        super(iterables);
    }
}
