package com.fillumina.utils.interpreter.util;

import java.util.Iterator;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ImmutableIterator<T> implements Iterator<T> {
    private final Iterator<T> delegate;

    public ImmutableIterator(final Iterator<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean hasNext() {
        return delegate.hasNext();
    }

    @Override
    public T next() {
        return delegate.next();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Removal not supported.");
    }
}
