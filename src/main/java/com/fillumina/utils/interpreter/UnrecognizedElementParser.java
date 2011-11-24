package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.grammar.AbstractUnrecognizedElement;
import java.io.Serializable;
import java.util.List;

/**
 * An {@link UnrecognizedElement} is something the {@link Tokenizer} could not
 * recognize as part of the grammar. It can be use to represents
 * variables.<br />
 * This parser assign to unrecognized tokens the {@link UnrecognizedElement}
 * grammar element.
 *
 * @author fra
 */
public class UnrecognizedElementParser<T,C> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final AbstractUnrecognizedElement<T,C> unrecognizedElement;

    public UnrecognizedElementParser(final List<GrammarElement<T,C>> grammar) {
        assertGrammarNotNull(grammar);
        unrecognizedElement = getUnrecognizeElement(grammar);
    }

    public void parse(final List<Node<T,C>> nodes) {
        if (unrecognizedElement != null) {
            for (Node<T,C> node: nodes) {
                if (node.isUnrecognized()) {
                    node.setGrammarElement(unrecognizedElement);
                }
            }
        }
    }

    private AbstractUnrecognizedElement<T,C> getUnrecognizeElement(
            final List<GrammarElement<T,C>> grammar) {
        if (grammar != null) {
            for (GrammarElement<?,?> ge: grammar) {
                if (ge instanceof AbstractUnrecognizedElement<?,?>) {
                    return (AbstractUnrecognizedElement<T,C>) ge;
                }
            }
        }
        return null;
    }

    private void assertGrammarNotNull(final List<GrammarElement<T,C>> grammar) {
        if (grammar == null) {
            throw new NullPointerException("grammar must not be null");
        }
    }

}
