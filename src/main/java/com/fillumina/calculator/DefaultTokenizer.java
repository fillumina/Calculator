package com.fillumina.calculator;

import com.fillumina.calculator.grammar.GrammarException;
import com.fillumina.calculator.util.ExtendedListIterator;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Uses a grammar to recognize the elements in a string expression.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DefaultTokenizer<T,C> implements Serializable, Tokenizer<T, C> {
    private static final long serialVersionUID = 1L;

    private final Iterable<GrammarElement<T,C>> grammar;

    public DefaultTokenizer(final Iterable<GrammarElement<T,C>> grammar) {
        Objects.requireNonNull(grammar, "grammar must not be null.");
        this.grammar = grammar;
    }

    @Override
    public List<Node<T,C>> tokenize(final String expression) {
        // LinkedList is very efficient for this algorithm
        // that needs to remove nodes a lot (which is inefficient in
        // ArrayList).
        final List<Node<T,C>> list = new LinkedList<>();
        list.add(new Node<T,C>(expression));

        recognizeGrammarElementInOrderOfPriority(list);

        return list;
    }

    private void recognizeGrammarElementInOrderOfPriority(
            final List<Node<T, C>> list) {
        for (GrammarElement<T,C> ge: grammar) {
            recognizeGrammarElement(ge, list);
        }
    }

    private void recognizeGrammarElement(final GrammarElement<T,C> ge,
            final List<Node<T,C>> list) {
        final SplittingIterator<T,C> iterator = new SplittingIterator<>(list);
        while (iterator.hasNext()) {
            final Node<T,C> node = iterator.next();

            if (node.isUnassignedGrammarElement()) {
                final GrammarElementMatcher matcher =
                        ge.match(node.getExpression());
                if (matcher.isFound()) {
                    assertMatchingANotEmptyRegion(matcher, ge);
                    final Node<T, C> matchedNode =
                            iterator.splitNode(node, matcher);
                    matchedNode.setGrammarElement(ge);
                    iterator.reset();
                }
            }
        }
    }

    private void assertMatchingANotEmptyRegion(
            final GrammarElementMatcher matcher,
            final GrammarElement<T,C> ge) {
        if (matcher.getStart() == matcher.getEnd()) {
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

        /** Reset the iterator to start. */
        public void reset() {
            delegate = list.listIterator();
        }

        /**
         * Splits the list to isolate the new node found by the matcher.
         * @return the created node
         */
        public Node<T,C> splitNode(
                final Node<T,C> originalNode,
                final GrammarElementMatcher matcher) {

            final int start = matcher.getStart();
            final int end = matcher.getEnd();
            final String value = originalNode.getExpression();
            final int valueLength = value.length();

            if (start == 0 && end == valueLength) {
                return originalNode;
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
