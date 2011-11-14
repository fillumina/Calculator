package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.grammar.GrammarElement;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Uses the grammar to recognize the grammar's element in a string expression.
 * 
 * @author fra
 */
public class Tokenizer implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<GrammarElement> grammar;

    public Tokenizer(final List<GrammarElement> grammar) {
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
            final GrammarElement ge) {
        final Pattern pattern = ge.getPattern();

        ListIterator<Node> iterator = list.listIterator();
        while (iterator.hasNext()) {
            final Node node = iterator.next();

            if (node.isUnrecognized()) {
                final String value = node.getValue();
                final Matcher matcher = pattern.matcher(value);
                if (matcher.find()) {
                    splitNode(iterator, node, matcher, ge);
                    iterator = list.listIterator(); // starts it over again
                }
            }
        }
    }

    private void splitNode(final ListIterator<Node> iterator,
            final Node node,
            final Matcher matcher,
            final GrammarElement ge) {

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

    private void assertGrammarNotNull(final List<GrammarElement> grammar) {
        if (grammar == null) {
            throw new IllegalArgumentException("grammar must not be null");
        }
    }

}
