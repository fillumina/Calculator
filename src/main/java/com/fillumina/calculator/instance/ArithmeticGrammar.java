package com.fillumina.calculator.instance;

import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.grammar.SettableContextedGrammarBuilder;
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
public class ArithmeticGrammar extends Grammar<Double,Map<String,Double>> {
    private static final long serialVersionUID = 1L;

    public static final Iterable<GrammarElement<Double,Map<String,Double>>> ITERABLE =
        new SettableContextedGrammarBuilder<Double>()
                .addFloatingPointOperand(new Evaluator<Double, Map<String, Double>>() {
                    @Override
                    public Double evaluate(String value, Map<String, Double> context) {
                        return Double.valueOf(value);
                    }
                })

                .addOperator()
                    .priority(5)
                    .symbols("!")
                    .operandsBefore(1)
                    .operandsAfter(0)
                    .evaluator(new ParametricEvaluator<Double, Map<String, Double>>() {
                        @Override
                        public Double evaluate(String value, List<Double> params,
                                Map<String, Double> context) {
                            final Double number = params.get(0);
                            final long ivalue = round(number);
                            long result = 1;
                            for (long l=1; l<=ivalue; l++) {
                                result *= l;
                            }
                            return Double.valueOf(String.valueOf(result));
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(4)
                    .symbols("asin")
                    .operandsBefore(0)
                    .operandsAfter(1)
                    .evaluator(new ParametricEvaluator<Double, Map<String, Double>>() {
                        @Override
                        public Double evaluate(String value, List<Double> params,
                                Map<String, Double> context) {
                            return asin(params.get(0));
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(4)
                    .symbols("acos")
                    .operandsBefore(0)
                    .operandsAfter(1)
                    .evaluator(new ParametricEvaluator<Double, Map<String, Double>>() {
                        @Override
                        public Double evaluate(String value, List<Double> params,
                                Map<String, Double> context) {
                            return acos(params.get(0));
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(4)
                    .symbols("atan")
                    .operandsBefore(0)
                    .operandsAfter(1)
                    .evaluator(new ParametricEvaluator<Double, Map<String, Double>>() {
                        @Override
                        public Double evaluate(String value, List<Double> params,
                                Map<String, Double> context) {
                            return atan(params.get(0));
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(4)
                    .symbols("sin")
                    .operandsBefore(0)
                    .operandsAfter(1)
                    .evaluator(new ParametricEvaluator<Double, Map<String, Double>>() {
                        @Override
                        public Double evaluate(String value, List<Double> params,
                                Map<String, Double> context) {
                            return sin(params.get(0));
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(4)
                    .symbols("cos")
                    .operandsBefore(0)
                    .operandsAfter(1)
                    .evaluator(new ParametricEvaluator<Double, Map<String, Double>>() {
                        @Override
                        public Double evaluate(String value, List<Double> params,
                                Map<String, Double> context) {
                            return cos(params.get(0));
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(4)
                    .symbols("tan")
                    .operandsBefore(0)
                    .operandsAfter(1)
                    .evaluator(new ParametricEvaluator<Double, Map<String, Double>>() {
                        @Override
                        public Double evaluate(String value, List<Double> params,
                                Map<String, Double> context) {
                            return tan(params.get(0));
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(4)
                    .symbols("log")
                    .operandsBefore(0)
                    .operandsAfter(1)
                    .evaluator(new ParametricEvaluator<Double, Map<String, Double>>() {
                        @Override
                        public Double evaluate(String value, List<Double> params,
                                Map<String, Double> context) {
                            return log10(params.get(0));
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(4)
                    .symbols("ln")
                    .operandsBefore(0)
                    .operandsAfter(1)
                    .evaluator(new ParametricEvaluator<Double, Map<String, Double>>() {
                        @Override
                        public Double evaluate(String value, List<Double> params,
                                Map<String, Double> context) {
                            return log(params.get(0));
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(4)
                    .symbols("sqrt", "sqr")
                    .operandsBefore(0)
                    .operandsAfter(1)
                    .evaluator(new ParametricEvaluator<Double, Map<String, Double>>() {
                        @Override
                        public Double evaluate(String value, List<Double> params,
                                Map<String, Double> context) {
                            return sqrt(params.get(0));
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(4)
                    .symbols("rnd")
                    .operandsBefore(0)
                    .operandsAfter(0)
                    .evaluator(new ParametricEvaluator<Double, Map<String, Double>>() {
                        @Override
                        public Double evaluate(String value, List<Double> params,
                                Map<String, Double> context) {
                            return Math.random();
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(4)
                    .symbols("avg")
                    .operandsBefore(0)
                    .allAvailableOperandsAfter()
                    .evaluator(new ParametricEvaluator<Double, Map<String, Double>>() {
                        @Override
                        public Double evaluate(String value, List<Double> params,
                                Map<String, Double> context) {
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
                    })
                    .buildOperator()

                .addOperator()
                    .priority(3)
                    .symbols("^")
                    .operandsBefore(1)
                    .operandsAfter(1)
                    .evaluator(new ParametricEvaluator<Double, Map<String, Double>>() {
                        @Override
                        public Double evaluate(String value, List<Double> params,
                                Map<String, Double> context) {
                            return pow(params.get(0), params.get(1));
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(2)
                    .symbols("*")
                    .operandsBefore(1)
                    .operandsAfter(1)
                    .evaluator(new ParametricEvaluator<Double, Map<String, Double>>() {
                        @Override
                        public Double evaluate(String value, List<Double> params,
                                Map<String, Double> context) {
                            return params.get(0) * params.get(1);
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(2)
                    .symbols("/")
                    .operandsBefore(1)
                    .operandsAfter(1)
                    .evaluator(new ParametricEvaluator<Double, Map<String, Double>>() {
                        @Override
                        public Double evaluate(String value, List<Double> params,
                                Map<String, Double> context) {
                            return params.get(0) / params.get(1);
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(1)
                    .symbols("+")
                    .operandsBefore(1)
                    .operandsAfter(1)
                    .evaluator(new ParametricEvaluator<Double, Map<String, Double>>() {
                        @Override
                        public Double evaluate(String value, List<Double> params,
                                Map<String, Double> context) {
                            if (params.size() == 1) {
                                // + signum
                                return params.get(0);
                            }
                            return params.get(0) + params.get(1);
                        }
                    })
                    .buildOperator()

                .addOperator()
                    .priority(1)
                    .symbols("-")
                    .operandsBefore(1)
                    .operandsAfter(1)
                    .evaluator(new ParametricEvaluator<Double, Map<String, Double>>() {
                        @Override
                        public Double evaluate(String value, List<Double> params,
                                Map<String, Double> context) {
                            if (params.size() == 1) {
                                // - signum
                                return - params.get(0);
                            }
                            return params.get(0) - params.get(1);
                        }
                    })
                    .buildOperator()

                .addConstant("E", E)
                .addConstant("PI", PI)
        .buildDefaultGrammar();

    @SuppressWarnings("unchecked")
    public static final ArithmeticGrammar INSTANCE =
            new ArithmeticGrammar(ITERABLE);

    private ArithmeticGrammar(
            final Iterable<GrammarElement<Double, Map<String, Double>>>... iterables) {
        super(iterables);
    }
}
