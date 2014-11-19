package com.fillumina.calculator;

import com.fillumina.calculator.grammar.GrammarException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 * Uses a grammar to recognize the elements in a string expression.
 *
 * @param T the type of the result
 * @param C the type of the context
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
        Node<T,C> node = null;
        GrammarElement<T,C> previousGrammarElement;
        while (iterator.hasNext()) {
            previousGrammarElement = (node == null) ?
                    null : node.getGrammarElement();
            
            node = iterator.next();

            if (node.isGrammarElementUnassigned()) {
                final GrammarElementMatcher matcher =
                        ge.match(previousGrammarElement, node.getExpression());
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
            throw new GrammarException("Cannot match an empty string: " + ge);
        }
    }

    private static class SplittingIterator<T,C> implements Iterator<Node<T,C>> {
        private static final long serialVersionUID = 1L;

        private final List<Node<T,C>> list;
        private ListIterator<Node<T,C>> delegate;

        public SplittingIterator(final List<Node<T, C>> list) {
            this.list = list;
            this.delegate = list.listIterator();
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
                delegate.remove();

                if (start != 0) {
                    delegate.add(new Node<T,C>(value.substring(0, start)));
                }

                final Node<T, C> createdNode =
                        new Node<>(value.substring(start, end));
                delegate.add(createdNode);

                if (end != valueLength) {
                    delegate.add(new Node<T,C>(value.substring(end, valueLength)));
                }

                return createdNode;
            }
        }

        @Override
        public boolean hasNext() {
            return delegate.hasNext();
        }

        @Override
        public Node<T, C> next() {
            return delegate.next();
        }

        @Override
        public void remove() {
            delegate.remove();
        }
    }
}
