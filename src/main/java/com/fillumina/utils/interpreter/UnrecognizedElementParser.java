package com.fillumina.utils.interpreter;

import com.fillumina.utils.interpreter.grammar.GrammarElement;
import com.fillumina.utils.interpreter.grammar.UnrecognizedElement;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author fra
 */
public class UnrecognizedElementParser implements Serializable {
    private static final long serialVersionUID = 1L;

    private final UnrecognizedElement<?,?> unrecognizedElement;

    public UnrecognizedElementParser(final List<GrammarElement> grammar) {
         unrecognizedElement = getUnrecognizeElement(grammar);
    }

    public void parse(final List<Node> nodes) {
        if (unrecognizedElement != null) {
            for (Node node: nodes) {
                if (node.isUnrecognized()) {
                    node.setGrammarElement(unrecognizedElement);
                }
            }
        }
    }

    private UnrecognizedElement getUnrecognizeElement(
            final List<GrammarElement> grammar) {
        for (GrammarElement ge: grammar) {
            if (ge instanceof UnrecognizedElement<?,?>) {
                return (UnrecognizedElement<?,?>) ge;
            }
        }
        return null;
    }

}
