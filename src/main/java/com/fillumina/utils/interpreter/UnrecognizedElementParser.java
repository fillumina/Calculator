package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.grammar.GrammarElement;
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
public class UnrecognizedElementParser implements Serializable {
    private static final long serialVersionUID = 1L;

    private final AbstractUnrecognizedElement<?,?> unrecognizedElement;

    public UnrecognizedElementParser(final List<GrammarElement> grammar) {
        assertGrammarNotNull(grammar);
        unrecognizedElement = getUnrecognizeElement(grammar);
    }

    public void parse(final List<Node> nodes) {
        if (unrecognizedElement != null) {
            for (Node node: nodes) {
                if (node.isUnrecognized()) {
                    node.setGrammarElement(unrecognizedElement);
                }
            }
        }
    }

    private AbstractUnrecognizedElement getUnrecognizeElement(
            final List<GrammarElement> grammar) {
        if (grammar != null) {
            for (GrammarElement ge: grammar) {
                if (ge instanceof AbstractUnrecognizedElement<?,?>) {
                    return (AbstractUnrecognizedElement<?,?>) ge;
                }
            }
        }
        return null;
    }

    private void assertGrammarNotNull(final List<GrammarElement> grammar) {
        if (grammar == null) {
            throw new NullPointerException("grammar must not be null");
        }
    }

}
