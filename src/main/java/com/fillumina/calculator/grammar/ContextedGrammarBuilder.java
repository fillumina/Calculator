package com.fillumina.calculator.grammar;

import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.element.VariableContextManager;
import java.util.Map;


/**
 * Helps building {@link Grammar}s with a string map context (the most
 * common one) with predefined components like round parentheses and white
 * spaces.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ContextedGrammarBuilder<T> extends GrammarBuilder<T,Map<String,T>> {
    private static final long serialVersionUID = 1L;

    public ContextedGrammarBuilder() {
    }

    public ContextedGrammarBuilder(
            Iterable<GrammarElement<T, Map<String, T>>> grammar) {
        super(grammar);
    }

    @Override
    public Iterable<GrammarElement<T, Map<String, T>>> buildGrammar() {
        add(VariableContextManager.<T>instance());
        return super.buildGrammar();
    }
}
