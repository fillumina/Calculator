package com.fillumina.calculator.util;

import java.io.Serializable;
import java.util.List;
import java.util.ListIterator;

/**
 * Adds some methods to the {@link ListIterator}.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ExtendedListIterator<T> implements ListIterator<T>, Serializable {
    private static final long serialVersionUID = 1L;

    protected ListIterator<T> delegate;

    public ExtendedListIterator(final List<T> list, final int startIndex) {
        this.delegate = list.listIterator(startIndex);
    }

    public ExtendedListIterator(final List<T> list) {
        this.delegate = list.listIterator();
    }

    public ExtendedListIterator(final ListIterator<T> delegate) {
        this.delegate = delegate;
    }

    public void next(final int steps) {
        for (int s=0; s<steps; s++) {
            next();
        }
    }

    public void previous(final int steps) {
        for (int s=0; s<steps; s++) {
            previous();
        }
    }

    public T nextAndRemove() {
        final T t = next();
        remove();
        return t;
    }

    public T previousAndRemove() {
        final T t = previous();
        remove();
        return t;
    }

    // delegated methods

    @Override
    public void set(final T e) {
        delegate.set(e);
    }

    @Override
    public void remove() {
        delegate.remove();
    }

    @Override
    public int previousIndex() {
        return delegate.previousIndex();
    }

    @Override
    public T previous() {
        return delegate.previous();
    }

    @Override
    public int nextIndex() {
        return delegate.nextIndex();
    }

    @Override
    public T next() {
        return delegate.next();
    }

    @Override
    public boolean hasPrevious() {
        return delegate.hasPrevious();
    }

    @Override
    public boolean hasNext() {
        return delegate.hasNext();
    }

    @Override
    public void add(final T e) {
        delegate.add(e);
    }

}