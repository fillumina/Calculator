package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.GrammarElement.MatchedIndexes;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

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

    public List<Node> tokenize(final String expression) {
        // LinkedList is very efficient for this algorithm
        final LinkedList<Node> list = new LinkedList<Node>();
        list.add(new Node(expression));

        for (GrammarElement ge: grammar) {
            recognizeGrammarElement(list, ge);
        }

        return list;
    }

    private void recognizeGrammarElement(final List<Node> list,
            final GrammarElement<?,?> ge) {

        ListIterator<Node> iterator = list.listIterator();
        while (iterator.hasNext()) {
            final Node node = iterator.next();

            if (node.isUnrecognized()) {
                final String value = node.getValue();
                final MatchedIndexes matcher = ge.match(value);
                if (matcher.found()) {
                    splitNode(iterator, node, matcher, ge);
                    iterator = list.listIterator(); // starts it over again
                }
            }
        }
    }

    private void splitNode(final ListIterator<Node> iterator,
            final Node node,
            final MatchedIndexes matcher,
            final GrammarElement<?,?> ge) {

        final int start = matcher.start();
        final int end = matcher.end();
        final String value = node.getValue();
        final int valueLength = value.length();

        if (start == end) {
            throw new RuntimeException("* jolly not allowed in " + ge);
        } else if (start == 0 && end == valueLength) {
            node.setGrammarElement(ge);
        } else {
            iterator.remove();

            if (start != 0) {
                iterator.add(new Node(value.substring(0, start)));
            }

            iterator.add(new Node(value.substring(start, end), ge));

            if (end != valueLength) {
                iterator.add(new Node(value.substring(end, valueLength)));
            }
        }
    }

    private void assertGrammarNotNull(final List<GrammarElement<T,C>> grammar) {
        if (grammar == null) {
            throw new IllegalArgumentException("grammar must not be null");
        }
    }

}
