package com.fillumina.utils.interpreter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A Grammar cannot force the type (T) and Context (C) to its members
 * because it is not a requirement for the {@link Interpreter}. Anyway it is
 * required by the {@link AbstractSolver} that has to define an appropriate
 * context.
 *
 * {@link GrammarElement}s should be added in order of parsing priority,
 * higher priority first. This means that if you have a function which
 * name is 'cos' and a variable name which name is 'constant' the constant
 * element should come first.<br />
 *
 * <b>IMPORTANT:</b> There could be only <b>one</b> (on none at all)
 * <code>UnrecognizedElement</code> defined in a grammar.
 *
 * @author fra
 */
public class Grammar<T,C>
        extends ArrayList<GrammarElement<T,C>>
        implements Serializable {
    private static final long serialVersionUID = 1L;

    public Grammar<T,C> put(final GrammarElement<T,C> ge) {
        add(ge);
        return this;
    }
}
