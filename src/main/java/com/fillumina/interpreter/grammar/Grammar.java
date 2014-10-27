package com.fillumina.interpreter.grammar;

import com.fillumina.interpreter.GrammarElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
 * <b>IMPORTANT:</b> There should be at most <b>one</b>
 * <code>UnrecognizedElement</code> defined in a grammar. Only
 * the first one will be considered.<p>
 *
 * <b>NOTE:</b> This class is just an helper and every method that works
 * with grammars should accept an {@link Iterable} over {@link GrammarElement}s.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class Grammar<T,C>
        implements Iterable<GrammarElement<T,C>>, Serializable {
    private static final long serialVersionUID = 1L;

    private final List<GrammarElement<? extends T,? extends C>> elements;

    public Grammar(final GrammarElement<? extends T,? extends C>... elementsArray) {
        elements = Collections.unmodifiableList(
                Arrays.asList(elementsArray.clone()));
    }

    public Grammar(final Collection<GrammarElement<? extends T,? extends C>> collection) {
        elements = Collections.unmodifiableList(new ArrayList<>(collection));
    }

    /** Always returns an immutable {@link Iterator}. */
    @Override
    public Iterator<GrammarElement<T, C>> iterator() {
        return new Iterator<GrammarElement<T, C>>() {
            private final Iterator<GrammarElement<? extends T, ? extends C>>
                    iterator = elements.iterator();

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
    public Grammar<T,C> join(final Grammar<? extends T,? extends C>... grammars) {
        final List<GrammarElement<? extends T,? extends C>> list =
                new ArrayList<>(elements);
        for (final Grammar<? extends T,? extends C> g: grammars) {
            list.addAll(g.elements);
        }
        return new Grammar<>(list);
    }
}
