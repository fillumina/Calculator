package com.fillumina.utils.interpreter.arithmetic;

import static java.lang.Math.*;
import com.fillumina.utils.interpreter.Node;
import com.fillumina.utils.interpreter.SyntaxErrorException;
import com.fillumina.utils.interpreter.grammar.CloseParenthesis;
import com.fillumina.utils.interpreter.grammar.EvaluableGrammarElement;
import com.fillumina.utils.interpreter.grammar.Grammar;
import com.fillumina.utils.interpreter.grammar.OpenParenthesis;
import com.fillumina.utils.interpreter.grammar.Operator;
import com.fillumina.utils.interpreter.grammar.UnrecognizedElement;
import com.fillumina.utils.interpreter.grammar.WhiteSpace;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author fra
 */
public class ArithmeticGrammar extends Grammar<Double,Map<String, Double>>
        implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final ArithmeticGrammar INSTANCE = new ArithmeticGrammar();

    protected ArithmeticGrammar() {
        super();
        addGrammarElements();
    }

    public Map<String, Double> createContext() {
        return new HashMap<String, Double>();
    }

    private void addGrammarElements() {
        // the problem here is that the - and + symbols shouldn't be included
        // in the number if there is a digit before (eventually separated by spaces)
        // see http://www.regular-expressions.info/refadv.html
        //
        // (\\D\\ {100}) is wrong but works recognizing non digit chars
        //
        add(new EvaluableGrammarElement<Double,Map<String, Double>>(
                "((?<=(([\\*\\+\\-/^]\\ {0,100})|(\\D\\ {100})|(\\(\\ {0,100})))[\\+\\-])?" +
                "\\d+(\\.\\d+)?([Ee][\\+\\-]?\\d+)?", 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final Node node, final List<Double> params,
                    final Map<String, Double> context) {
                return Double.parseDouble(node.getValue());
            }
        });

        add(new Operator<Double,Map<String, Double>>("asin", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final Node node, final List<Double> params,
                    final Map<String, Double> context) {
                return asin(params.get(0));
            }
        });

        add(new Operator<Double,Map<String, Double>>("acos", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final Node node, final List<Double> params,
                    final Map<String, Double> context) {
                return acos(params.get(0));
            }

        });

        add(new Operator<Double,Map<String, Double>>("atan", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final Node node, final List<Double> params,
                    final Map<String, Double> context) {
                return atan(params.get(0));
            }
        });

        add(new Operator<Double,Map<String, Double>>("sin", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final Node node, final List<Double> params,
                    final Map<String, Double> context) {
                return sin(params.get(0));
            }
        });

        add(new Operator<Double,Map<String, Double>>("cos", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final Node node, final List<Double> params,
                    final Map<String, Double> context) {
                return cos(params.get(0));
            }

        });

        add(new Operator<Double,Map<String, Double>>("tan", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final Node node, final List<Double> params,
                    final Map<String, Double> context) {
                return tan(params.get(0));
            }
        });

        add(new Operator<Double,Map<String, Double>>("log", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final Node node, final List<Double> params,
                    final Map<String, Double> context) {
                return log10(params.get(0));
            }
        });

        add(new Operator<Double,Map<String, Double>>("ln", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final Node node, final List<Double> params,
                    final Map<String, Double> context) {
                return log(params.get(0));
            }
        });

        add(new Operator<Double,Map<String, Double>>("sqr", 4, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final Node node, final List<Double> params,
                    final Map<String, Double> context) {
                return sqrt(params.get(0));
            }
        });

        // accepts many parameters
        add(new Operator<Double,Map<String, Double>>("avg", 4, 0, 1000) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final Node node, final List<Double> params,
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
        });

        add(new Operator<Double,Map<String, Double>>("\\^", 3, 1, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final Node node, final List<Double> params,
                    final Map<String, Double> context) {
                return pow(params.get(0), params.get(1));
            }
        });

        add(new Operator<Double,Map<String, Double>>("\\*", 2, 1, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final Node node, final List<Double> params,
                    final Map<String, Double> context) {
                return params.get(0) * params.get(1);
            }
        });

        add(new Operator<Double,Map<String, Double>>("/", 2, 1, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final Node node, final List<Double> params,
                    final Map<String, Double> context) {
                return params.get(0) / params.get(1);
            }
        });

        add(new Operator<Double,Map<String, Double>>("\\+", 1, 1, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final Node node, final List<Double> params,
                    final Map<String, Double> context) {
                return params.get(0) + params.get(1);
            }
        });

        add(new Operator<Double,Map<String, Double>>("\\-", 1, 1, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final Node node, final List<Double> params,
                    final Map<String, Double> context) {
                if (params.size() == 1) {
                    return - params.get(0);
                }
                return params.get(0) - params.get(1);
            }
        });

        add(new Operator<Double,Map<String, Double>>("[A-Za-z]+\\ *=", 0, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final Node node, final List<Double> params,
                    final Map<String, Double> context) {
                final String nodeValue = node.getValue();
                final String varName = nodeValue.substring(0,
                        nodeValue.length() - 1).trim();
                final Double parameter = params.get(0);

                context.put(varName, parameter);
                return parameter;
            }
        });

        add(new EvaluableGrammarElement<Double,Map<String, Double>>("e", 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final Node node, final List<Double> params,
                    final Map<String, Double> context) {
                return Math.E;
            }
        });

        add(new EvaluableGrammarElement<Double,Map<String, Double>>("pi", 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final Node node, final List<Double> params,
                    final Map<String, Double> context) {
                return Math.PI;
            }
        });

        add(new UnrecognizedElement<Double,Map<String, Double>>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Double evaluate(final Node node, final List<Double> params,
                    final Map<String, Double> context) {
                final Double value = context.get(node.getValue());
                if (value == null) {
                    throw new SyntaxErrorException("variable '" +
                            node.getValue() + "' not in context");
                }
                return value;
            }
        });

        add(new OpenParenthesis("\\("));
        add(new CloseParenthesis("\\)"));
        add(new WhiteSpace("[\\ ,]+"));

    }

}
