package com.fillumina.calculator.grammar;

import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.grammar.element.AbstractFastDateOperand;
import com.fillumina.calculator.grammar.element.AbstractFastDoubleOperand;
import com.fillumina.calculator.grammar.element.AbstractFastIntegerOperand;
import com.fillumina.calculator.grammar.element.AbstractFastMultiOperator;
import com.fillumina.calculator.grammar.element.AbstractFastStringOperand;
import com.fillumina.calculator.grammar.element.FastCloseParentheses;
import com.fillumina.calculator.grammar.element.FastConstantOperand;
import com.fillumina.calculator.grammar.element.FastOpenParentheses;
import com.fillumina.calculator.grammar.element.ValueFastMultiConstant;
import com.fillumina.calculator.grammar.element.VeryFastWhiteSpace;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Helps building {@link Grammar}s with predefined components like round
 * parentheses and white spaces.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class GrammarBuilder<T,C> {
    private static final long serialVersionUID = 1L;

    private final List<GrammarElement<T,C>> elements = new ArrayList<>();
    private boolean doubleDefined;

    public GrammarBuilder() {
    }

    /** Extends an existing grammar. */
    public GrammarBuilder(final Iterable<GrammarElement<T,C>> grammar) {
        for (GrammarElement<T,C> ge : grammar) {
            elements.add(ge);
        }
    }

    /**
     * Defines this operand <i>before</i>
     * {@link #addFloatOperand(int, StringEvaluator)}.
     */
    public GrammarBuilder<T,C> addIntOperand(
            final StringEvaluator<T,C> evaluator) {
        if (doubleDefined) {
            throw new IllegalStateException(
                    "Define the integer operator before the double");
        }
        elements.add(new AbstractFastIntegerOperand<T,C>(0) {
            private static final long serialVersionUID = 1L;

            @Override
            public T evaluate(String value, List<T> params, C context) {
                return evaluator.evaluate(value, context);
            }
        });
        return this;
    }

    public GrammarBuilder<T,C> addFloatOperand(
            final StringEvaluator<T,C> evaluator) {
        elements.add(new AbstractFastDoubleOperand<T,C>(0) {
            private static final long serialVersionUID = 1L;

            @Override
            public T evaluate(String value, List<T> params, C context) {
                return evaluator.evaluate(value, context);
            }
        });
        doubleDefined = true;
        return this;
    }

    public GrammarBuilder<T,C> addDateOperand(final String pattern,
            final StringEvaluator<T,C> evaluator) {
        elements.add(new AbstractFastDateOperand<T,C>(0, pattern) {
            private static final long serialVersionUID = 1L;

            @Override
            public T evaluate(String value, List<T> params, C context) {
                return evaluator.evaluate(value, context);
            }
        });
        return this;
    }

    /**
     * Adds the {@code true} and {@code false} constants:
     * <ul>
     * <li>{@code true}, {@code TRUE}, {@code True};
     * <li>{@code false}, {@code FALSE}, {@code False}.
     * </ul>
     *
     * @param trueValue     sets the constant to return in case of {@code true}
     * @param falseValue    sets the constant to return in case of {@code false}
     */
    public GrammarBuilder<T,C> addBooleanOperand(final T trueValue,
            final T falseValue) {
        elements.add(new ValueFastMultiConstant<T,C>(
                    trueValue, 0, "true", "TRUE", "True"));
        elements.add(new ValueFastMultiConstant<T,C>(
                    falseValue, 0, "false", "FALSE", "False"));
        return this;
    }

    /** Adds unquoted, single-quoted and double-quoted strings. */
    public GrammarBuilder<T,C> addStringOperand(
            final StringEvaluator<T,C> evaluator) {
        elements.add(new AbstractFastStringOperand<T,C>(0) {
            private static final long serialVersionUID = 1L;

            @Override
            public T evaluate(String value, List<T> params, C context) {
                return evaluator.evaluate(value, context);
            }
        });
        return this;
    }

    public GrammarBuilder<T,C> addConstant(final String symbol, final T constant) {
        elements.add(new FastConstantOperand<T,C>(symbol, constant, 0));
        return this;
    }

    public OperatorBuilder addOperator() {
        return new OperatorBuilder();
    }

    public class OperatorBuilder {
        private int priority;
        private int operandsBefore;
        private int operandsAfter;
        private StringParametricEvaluator<T,C> evaluator;
        private String[] symbols;

        public OperatorBuilder priority(final int value) {
            this.priority = value;
            return this;
        }

        public OperatorBuilder operandsBefore(final int value) {
            this.operandsBefore = value;
            return this;
        }

        public OperatorBuilder operandsAfter(final int value) {
            this.operandsAfter = value;
            return this;
        }

        public OperatorBuilder evaluator(final StringParametricEvaluator<T,C> value) {
            this.evaluator = value;
            return this;
        }

        public OperatorBuilder symbols(final String... value) {
            this.symbols = value;
            return this;
        }

        public GrammarBuilder<T,C> buildOperator() {
            elements.add(new AbstractFastMultiOperator<T, C>(priority,
                    operandsBefore, operandsAfter, symbols) {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public T evaluate(String value, List<T> params, C context) {
                            return evaluator.evaluate(value, params, context);
                        }
                    });
            return GrammarBuilder.this;
        }
    }

    public GrammarBuilder<T,C> add(final GrammarElement<T,C> ge) {
        elements.add(ge);
        return this;
    }

    /** Intermediate results are independent between each other. */
    @SuppressWarnings("unchecked")
    public Iterable<GrammarElement<T,C>> buildGrammar() {
        add(FastOpenParentheses.<T,C>round());
        add(FastCloseParentheses.<T,C>round());
        add(VeryFastWhiteSpace.<T,C>instance());

        return Collections.unmodifiableList(new ArrayList<>(elements));
    }
}
