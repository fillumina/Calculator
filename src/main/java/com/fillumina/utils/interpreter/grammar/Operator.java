package com.fillumina.utils.interpreter.grammar;

/**
 *
 * @param <T> the base element type this operator work on
 * @author fra
 */
public abstract class Operator<T,C> extends EvaluableGrammarElement<T,C> {
    private static final long serialVersionUID = 1L;

    private final int requiredOperandsBefore;
    private final int requiredOperandsAfter;

    public Operator(final String symbolRegexp,
            final int priority,
            final int requiredOperandsBefore,
            final int requiredOperandsAfter) {
        super(symbolRegexp, priority);
        this.requiredOperandsBefore = requiredOperandsBefore;
        this.requiredOperandsAfter = requiredOperandsAfter;
    }

    public int getRequiredOperandsAfter() {
        return requiredOperandsAfter;
    }

    public int getRequiredOperandsBefore() {
        return requiredOperandsBefore;
    }
}
