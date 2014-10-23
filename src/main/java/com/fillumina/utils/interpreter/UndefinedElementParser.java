package com.fillumina.utils.interpreter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Assigns to undefined tokens (those that don't have any
 * {@link GrammarElement} set) the {@link UnrecognizedElement}
 * grammar element.
 * An {@link UnrecognizedElement} is something the {@link Tokenizer} could not
 * recognize as part of the grammar. It can be used to represent
 * variables.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class UndefinedElementParser<T,C>
        implements SolutionTreeFilter, Serializable {
    private static final long serialVersionUID = 1L;

    private final GrammarElement<T,C> unrecognizedElement;

    public UndefinedElementParser(
            final Iterable<GrammarElement<T,C>> grammar) {
        Objects.requireNonNull(grammar, "grammar must not be null");
        unrecognizedElement = getUnrecognizedElement(grammar);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T,C> void executeOn(final List<Node<T,C>> nodes) {
        for (Node<T,C> node: nodes) {
            if (node.isUnassignedGrammarElement()) {
                assertUnrecognizedElementIsPresent(node);
                node.setGrammarElement(
                        (GrammarElement<T, C>) unrecognizedElement);
            }
        }
    }

    /**
     * @return the <b>first</b> {@link GrammarElement} in the grammar of type
     *          {@link GrammarElementType#UNRECOGNIZED}.
     */
    private static <T,C> GrammarElement<T,C> getUnrecognizedElement(
            final Iterable<GrammarElement<T,C>> grammar) {
        if (grammar != null) {
            for (GrammarElement<T,C> ge: grammar) {
                if (ge.getType() == GrammarElementType.UNRECOGNIZED) {
                    return ge;
                }
            }
        }
        return null;
    }

    private <T,C> void assertUnrecognizedElementIsPresent(
            final Node<T, C> node) {
        if (unrecognizedElement == null) {
            throw new SyntaxErrorException("Element '" +
                    node.getExpression() + "' not recognized");
        }
    }
}
