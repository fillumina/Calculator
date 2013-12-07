package com.fillumina.utils.interpreter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Collection of {@link GrammarElement}s defining a grammar.
 * {@link GrammarElement}s should be added in order of parsing priority,
 * higher priority first. This means that if you have a function which
 * name is 'cos' and a variable which name is 'constant' the variable
 * element should come first in the list.<p>
 *
 * <b>IMPORTANT:</b> There should be only <b>one</b> (or none at all)
 * <code>UnrecognizedElement</code> defined in a grammar. At any rate only
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

    private final List<GrammarElement<T,C>> elements;

    public Grammar(final GrammarElement<T,C>... elementsArray) {
        elements = Collections.unmodifiableList(
                Arrays.asList(elementsArray.clone()));
    }

    public Grammar(final Collection<GrammarElement<T,C>> collection) {
        elements = Collections.unmodifiableList(new ArrayList<>(collection));
    }

    @Override
    public Iterator<GrammarElement<T, C>> iterator() {
        return elements.iterator();
    }

    /**
     * Creates a <b>new</b> {@link Grammar} with the new elements added, in the
     * given order, having the present elements first.
     */
    public Grammar<T,C> join(final Grammar<T,C>... grammars) {
        final List<GrammarElement<T,C>> list = new ArrayList<>(elements);
        for (final Grammar<T,C> g: grammars) {
            list.addAll(g.elements);
        }
        return new Grammar<>(list);
    }
}
