package com.fillumina.calculator.grammar;

import com.fillumina.calculator.GrammarElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Collection of {@link GrammarElement}s defining a grammar.
 *
 * {@link GrammarElement}s should be added in order of parsing priority, with
 * higher priority first. This means that if you have a function which
 * name is {@code cons} and a variable which name is {@code constant}
 * the variable element should come first in the list.<p>
 *
 * Grammar is designed to be not mutable.
 *
 * <b>IMPORTANT:</b> There should be at most <b>one</b>
 * {@link com.fillumina.calculator.GrammarElementType#UNRECOGNIZED} element
 * defined in a grammar. <i>Only the first one will be considered</i>.<p>
 *
 * <b>NOTE:</b> This class is just an helper and every method that works
 * with grammars should accept an {@link Iterable} over {@link GrammarElement}s.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class Grammar<T,C>
        implements Iterable<GrammarElement<T,C>>, Serializable {
    private static final long serialVersionUID = 1L;

    private final List<GrammarElement<T,C>> elements;

    public Grammar(final GrammarElement<T,C>... elementsArray) {
        elements = Collections.unmodifiableList(
                Arrays.asList(elementsArray.clone()));
    }

    public Grammar(final Iterable<GrammarElement<T,C>>... iterables) {
        elements = Collections.unmodifiableList(join(iterables));
    }

    /** Always returns an immutable {@link Iterator}. */
    @Override
    public Iterator<GrammarElement<T, C>> iterator() {
        return new Iterator<GrammarElement<T, C>>() {
            private final Iterator<GrammarElement<T,C>> iterator =
                    elements.iterator();

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            @SuppressWarnings("unchecked")
            public GrammarElement<T, C> next() {
                return (GrammarElement<T, C>) iterator.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Removal not supported.");
            }
        };
    }

    /**
     * Creates a <b>new</b> {@link Grammar} with the new elements added, in the
     * given order, <i>after</i> the elements already present in the grammar.
     */
    public Grammar<T,C> joinGrammar(final Grammar<T,C>... grammars) {
        final List<GrammarElement<T,C>> list =
                new ArrayList<>(elements);
        for (final Grammar<T,C> g: grammars) {
            list.addAll(g.elements);
        }
        return new Grammar<>(list);
    }

    public static <T,C> List<GrammarElement<T,C>> join(
            final Iterable<GrammarElement<T,C>>... iterables) {
        final List<GrammarElement<T,C>> list = new ArrayList<>();
        for (final Iterable<GrammarElement<T,C>> iterable: iterables) {
            for (final GrammarElement<T,C> ge: iterable) {
                list.add(ge);
            }
        }
        return list;
    }
}
