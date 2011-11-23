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

    private final Tokenizer tokenizer;
    private final UnrecognizedElementParser unrecognizedElementParser;
    private final TreeBuilder treeBuilder;
    private final WhiteSpaceCleaner whiteSpaceCleaner;
    private final ParenthesisCleaner parenthesisCleaner;

    public Interpreter(final List<GrammarElement<T,C>> grammar) {
        this.tokenizer = new Tokenizer(grammar);
        this.unrecognizedElementParser = new UnrecognizedElementParser(grammar);
        this.treeBuilder = new TreeBuilder();
        this.whiteSpaceCleaner = new WhiteSpaceCleaner();
        this.parenthesisCleaner = new ParenthesisCleaner();
    }

    /** can return a multi root tree */
    public List<Node> parse(final String expression) {
        final List<Node> list = tokenizer.tokenize(expression);
        whiteSpaceCleaner.clean(list);
        unrecognizedElementParser.parse(list);
        treeBuilder.createTree(list);
        parenthesisCleaner.clean(list);
        return list;
    }

}
