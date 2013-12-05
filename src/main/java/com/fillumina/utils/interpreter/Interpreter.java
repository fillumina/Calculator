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
    private final WhiteSpaceCleaner whiteSpaceCleaner;
    private final UnrecognizedElementParser<T,C> unrecognizedElementParser;
    private final TreeBuilder<T,C> treeBuilder;
    private final ParenthesisCleaner<T,C> parenthesisCleaner;

    public Interpreter(final Iterable<GrammarElement<T,C>> grammar) {
        this.tokenizer = new Tokenizer<>(grammar);
        this.whiteSpaceCleaner = WhiteSpaceCleaner.INSTANCE;
        this.unrecognizedElementParser = new UnrecognizedElementParser<>(grammar);
        this.treeBuilder = new TreeBuilder<>();
        this.parenthesisCleaner = new ParenthesisCleaner<>();
    }

    /** Parses the expression and return a (eventually multi-headed) node tree. */
    public List<Node<T,C>> parse(final String expression) {
        final List<Node<T,C>> list = tokenizer.tokenize(expression);
        whiteSpaceCleaner.clean(list);
        unrecognizedElementParser.parse(list);
        treeBuilder.createTree(list);
        parenthesisCleaner.clean(list);
        return list;
    }
}
