package com.fillumina.calculator.grammar;

import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.element.CloseParentheses;
import com.fillumina.calculator.element.DefaultWhiteSpace;
import com.fillumina.calculator.element.OpenParentheses;

/**
 * Adds to the grammar round parenthesis and white spaces.
 * 
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DefaultGrammarBuilder<T,C> extends GrammarBuilder<T,C> {
    private static final long serialVersionUID = 1L;

    public DefaultGrammarBuilder() {
        super();
    }

    public DefaultGrammarBuilder(
            final Iterable<GrammarElement<T,C>>... grammars) {
        super(grammars);
    }

    /**
     * @return a grammar able to manage round parentheses and
     * common white spaces.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Iterable<GrammarElement<T,C>> buildGrammar() {
        add(OpenParentheses.<T,C>round());
        add(CloseParentheses.<T,C>round());
        add(DefaultWhiteSpace.<T,C>instance());

        return super.buildGrammar();
    }
}
