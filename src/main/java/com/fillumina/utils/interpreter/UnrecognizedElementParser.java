package com.fillumina.utils.interpreter;

import java.io.Serializable;
import java.util.List;

/**
 * This parser assigns to unrecognized tokens the {@link UnrecognizedElement}
 * grammar element.
 * An {@link UnrecognizedElement} is something the {@link Tokenizer} could not
 * recognize as part of the grammar. It can be used to represent
 * variables.<br />
 *
 * @author fra
 */
public class UnrecognizedElementParser<T,C> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final GrammarElement<T,C> unrecognizedElement;

    public UnrecognizedElementParser(final List<GrammarElement<T,C>> grammar) {
        assertGrammarNotNull(grammar);
        unrecognizedElement = getUnrecognizeElement(grammar);
    }

    public void parse(final List<Node<T,C>> nodes) {
        for (Node<T,C> node: nodes) {
            if (node.isUnrecognized()) {
                assertUnrecognizedElementIsPresent(node);
                node.setGrammarElement(unrecognizedElement);
            }
        }
    }

    private GrammarElement<T,C> getUnrecognizeElement(
            final List<GrammarElement<T,C>> grammar) {
        if (grammar != null) {
            for (GrammarElement<T,C> ge: grammar) {
                if (ge.isType(GrammarElement.Type.UNRECOGNIZED)) {
                    return ge;
                }
            }
        }
        return null;
    }

    private void assertUnrecognizedElementIsPresent(final Node<T, C> node) {
        if (unrecognizedElement == null) {
            throw new SyntaxErrorException("Element '" +
                    node.getValue() + "' not recognized");
        }
    }

    private void assertGrammarNotNull(final List<GrammarElement<T,C>> grammar) {
        if (grammar == null) {
            throw new NullPointerException("grammar must not be null");
        }
    }

}
