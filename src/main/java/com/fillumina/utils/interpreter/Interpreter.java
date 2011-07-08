package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.treebuilder.ParenthesisCleaner;
import com.fillumina.utils.interpreter.treebuilder.TreeBuilder;
import com.fillumina.utils.interpreter.grammar.GrammarElement;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author fra
 */
public class Interpreter implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Tokenizer tokenizer;
    private final UnrecognizedElementParser unrecognizedElementParser;
    private final TreeBuilder treeBuilder;
    private final WhiteSpaceCleaner whiteSpaceCleaner;
    private final ParenthesisCleaner parenthesisCleaner;

    public Interpreter(final List<GrammarElement> grammar) {
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
