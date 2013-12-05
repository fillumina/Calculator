package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.GrammarElement.GrammarElementMatchIndex;
import com.fillumina.utils.interpreter.util.ExtendedListIterator;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Uses the grammar to recognize the elements' hierarchy in a string expression.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class Tokenizer<T,C> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Iterable<GrammarElement<T,C>> grammar;

    public Tokenizer(final Iterable<GrammarElement<T,C>> grammar) {
        Objects.requireNonNull(grammar, "grammar must not be null.");
        this.grammar = grammar;
    }

    public List<Node<T,C>> tokenize(final String expression) {
        // LinkedList is very efficient for this algorithm
        final LinkedList<Node<T,C>> list = new LinkedList<>();
        list.add(new Node<T,C>(expression));

        for (GrammarElement<T,C> ge: grammar) {
            recognizeGrammarElement(list, ge);
        }

        return list;
    }

    private void recognizeGrammarElement(final List<Node<T,C>> list,
            final GrammarElement<T,C> ge) {

        final SplittingIterator<T,C> iterator = new SplittingIterator<>(list);
        while (iterator.hasNext()) {
            final Node<T,C> node = iterator.next();

            if (node.isUnrecognized()) {
                final GrammarElementMatchIndex matcher = ge.match(node.getValue());
                if (matcher.found()) {
                    assertMatchANotEmptyRegion(matcher, ge);
                    final Node<T, C> matchedNode =
                            iterator.splitNode(node, matcher);
                    matchedNode.setGrammarElement(ge);
                    iterator.reset();
                }
            }
        }
    }

    private void assertMatchANotEmptyRegion(final GrammarElementMatchIndex matcher,
            final GrammarElement<T,C> ge) {
        if (matcher.start() == matcher.end()) {
            throw new GrammarException("* jollies not allowed in " + ge);
        }
    }

    private static class SplittingIterator<T,C>
            extends ExtendedListIterator<Node<T,C>> {
        private static final long serialVersionUID = 1L;

        private final List<Node<T,C>> list;

        public SplittingIterator(final List<Node<T, C>> list) {
            super(list);
            this.list = list;
        }

        public void reset() {
            delegate = list.listIterator();
        }

        public Node<T,C> splitNode(
                final Node<T,C> node,
                final GrammarElementMatchIndex matcher) {

            final int start = matcher.start();
            final int end = matcher.end();
            final String value = node.getValue();
            final int valueLength = value.length();

            if (start == 0 && end == valueLength) {
                return node;
            } else {
                remove();

                if (start != 0) {
                    add(new Node<T,C>(value.substring(0, start)));
                }

                final Node<T, C> createdNode =
                        new Node<>(value.substring(start, end));
                add(createdNode);

                if (end != valueLength) {
                    add(new Node<T,C>(value.substring(end, valueLength)));
                }

                return createdNode;
            }
        }
    }
}
