package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.treebuilder.ParenthesisCleaner;
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
public class Interpreter<T,C> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Tokenizer<T,C> tokenizer;
    private final UnrecognizedElementParser<T,C> unrecognizedElementParser;

    public Interpreter(final Iterable<GrammarElement<T,C>> grammar) {
        this.tokenizer = new Tokenizer<>(grammar);
        this.unrecognizedElementParser = new UnrecognizedElementParser<>(grammar);
    }

    /** Parses the expression and return a (eventually multi-headed) node tree. */
    public List<Node<T,C>> parse(final String expression) {
        final List<Node<T,C>> list = tokenizer.tokenize(expression);
        WhiteSpaceCleaner.INSTANCE.executeOn(list);
        unrecognizedElementParser.executeOn(list);
        TreeBuilder.INSTANCE.executeOn(list);
        ParenthesisCleaner.INSTANCE.executeOn(list);
        return list;
    }
}
