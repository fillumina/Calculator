package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.treebuilder.TreeBuilder;
import java.io.Serializable;
import java.util.List;

/**
 * Parses a string expression and builds a solving tree according to the
 * given grammar.
 *
 * @param T     the type of the expected result
 * @param C     the type of the context
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
// NOTE: cannot make an interface out of this because Node uses GrammarELement
public class Interpreter<T,C> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Tokenizer<T,C> tokenizer;
    private final SolutionTreeFilter filters;

    public Interpreter(final Iterable<GrammarElement<T,C>> grammar) {
        this(new Tokenizer<>(grammar),
            new SolutionTreeFilterChain(
                WhiteSpaceCleaner.INSTANCE,
                new UndefinedElementParser<>(grammar),
                TreeBuilder.INSTANCE,
                ParenthesisCleaner.INSTANCE)
        );
    }

    /** Defines your own tokenizer and filters. */
    public Interpreter(final Tokenizer<T, C> tokenizer,
            final SolutionTreeFilter filters) {
        this.tokenizer = tokenizer;
        this.filters = filters;
    }

    /** Parses the expression and return a (eventually multi-headed) node tree. */
    public List<Node<T,C>> parse(final String expression) {
        final List<Node<T,C>> list = tokenizer.tokenize(expression);
        filters.executeOn(list);
        return list;
    }
}
