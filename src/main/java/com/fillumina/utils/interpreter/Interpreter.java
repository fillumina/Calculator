package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.treebuilder.ParenthesisCleaner;
import com.fillumina.utils.interpreter.treebuilder.TreeBuilder;
import java.io.Serializable;
import java.util.List;

/**
 * It parses a string expression and build a solving tree according to the
 * given grammar.
 *
 * @author fra
 */
public class Interpreter<T,C> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Tokenizer<T,C> tokenizer;
    private final UnrecognizedElementParser<T,C> unrecognizedElementParser;
    private final TreeBuilder<T,C> treeBuilder;
    private final WhiteSpaceCleaner<T,C> whiteSpaceCleaner;
    private final ParenthesisCleaner<T,C> parenthesisCleaner;

    public Interpreter(final List<GrammarElement<T,C>> grammar) {
        this.tokenizer = new Tokenizer<T,C>(grammar);
        this.unrecognizedElementParser = new UnrecognizedElementParser<T,C>(grammar);
        this.treeBuilder = new TreeBuilder<T,C>();
        this.whiteSpaceCleaner = new WhiteSpaceCleaner<T,C>();
        this.parenthesisCleaner = new ParenthesisCleaner<T,C>();
    }

    /** can return a multi root tree */
    public List<Node<T,C>> parse(final String expression) {
        final List<Node<T,C>> list = tokenizer.tokenize(expression);
        whiteSpaceCleaner.clean(list);
        unrecognizedElementParser.parse(list);
        treeBuilder.createTree(list);
        parenthesisCleaner.clean(list);
        return list;
    }

}
