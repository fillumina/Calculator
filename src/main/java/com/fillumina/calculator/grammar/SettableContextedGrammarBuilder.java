package com.fillumina.calculator.grammar;

import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.grammar.Grammar;
import com.fillumina.calculator.grammar.element.FastVariableSetterOperator;
import java.util.Map;

/**
 * Helps building {@link Grammar}s with a string map context (the most
 * common one) with predefined components like round parentheses and white
 * spaces and the possibility to set a variable in the context using the
 * assignment operator '='.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class SettableContextedGrammarBuilder<T>
        extends ContextedGrammarBuilder<T> {
    private static final long serialVersionUID = 1L;

    public SettableContextedGrammarBuilder() {
    }

    public SettableContextedGrammarBuilder(
            Iterable<GrammarElement<T, Map<String, T>>> grammar) {
        super(grammar);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterable<GrammarElement<T, Map<String, T>>> buildGrammar() {
        add(FastVariableSetterOperator.<T>instance());
        return super.buildGrammar();
    }
}
