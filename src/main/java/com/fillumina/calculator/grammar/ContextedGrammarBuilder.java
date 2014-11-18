package com.fillumina.calculator.grammar;

import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.element.VariableContextManager;
import com.fillumina.calculator.element.VariableSetterOperator;
import java.util.Map;


/**
 * Helps building {@link Grammar}s with a string map context (the most
 * common one) with predefined components like round parentheses and white
 * spaces.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ContextedGrammarBuilder<T>
        extends GrammarBuilder<T, Map<String,T>, ContextedGrammarBuilder<T> > {
    private static final long serialVersionUID = 1L;

    public ContextedGrammarBuilder() {
        super();
    }

    public ContextedGrammarBuilder(
            final Iterable<GrammarElement<T, Map<String, T>>>... grammars) {
        super(grammars);
    }

    /**
     * @return a Grammar able to manage round parentheses, common white spaces
     *         and a variable context manager.
     */
    public Iterable<GrammarElement<T, Map<String, T>>>
                buildDefaultGrammarWithVariables() {
        add(VariableContextManager.<T>instance());
        return super.buildDefaultGrammar();
    }

    /**
     *
     * @return a Grammar able to manage round parentheses, common white spaces,
     *         a variable context manager and setter.
     */
    @SuppressWarnings("unchecked")
    public Iterable<GrammarElement<T, Map<String, T>>>
                buildDefaultGrammarWithSettableVariables() {
        add(VariableSetterOperator.<T>instance());
        return buildDefaultGrammarWithVariables();
    }
}
