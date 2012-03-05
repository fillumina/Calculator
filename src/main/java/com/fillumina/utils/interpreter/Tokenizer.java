package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.GrammarElement.MatchIndex;
import com.fillumina.utils.interpreter.util.ExtendedListIterator;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Uses the grammar to recognize the grammar's element in a string expression.
 *
 * @author fra
 */
public class Tokenizer<T,C> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<GrammarElement<T,C>> grammar;

    public Tokenizer(final List<GrammarElement<T,C>> grammar) {
        assertGrammarNotNull(grammar);
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
                final MatchIndex matcher = ge.match(node.getValue());
                if (matcher.found()) {
                    assertMatchANotEmptyRegion(matcher, ge);
                    final Node<T, C> matchedNode = iterator.splitNode(node, matcher);
                    matchedNode.setGrammarElement(ge);
                    iterator.reset();
                }
            }
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
                final MatchIndex matcher) {

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

    private void assertMatchANotEmptyRegion(final MatchIndex matcher,
            final GrammarElement<T,C> ge) {
        if (matcher.start() == matcher.end()) {
            throw new GrammarException("* jollies not allowed in " + ge);
        }

    }

    private void assertGrammarNotNull(final List<GrammarElement<T,C>> grammar) {
        if (grammar == null) {
            throw new IllegalArgumentException("grammar must not be null");
        }
    }

}
