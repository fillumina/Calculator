package com.fillumina.calculator;

import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.Node;
import com.fillumina.calculator.SolutionTreeFilter;
import com.fillumina.calculator.grammar.GrammarElementType;
import java.io.Serializable;
import java.util.List;
import java.util.ListIterator;

/**
 * The {@link WhiteSpace} defines characters in the input that may be
 * safely ignored. This class removes them from the list of tokens.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class WhiteSpaceCleaner implements SolutionTreeFilter, Serializable {
    private static final long serialVersionUID = 1L;

    public static final WhiteSpaceCleaner INSTANCE = new WhiteSpaceCleaner();

    private WhiteSpaceCleaner() {}

    @Override
    public <T,C> void executeOn(final List<Node<T,C>> list) {
        final ListIterator<Node<T,C>> iterator = list.listIterator();
        while(iterator.hasNext()) {
            final Node<T,C> node = iterator.next();

            if (isAWhiteSpace(node)) {
                iterator.remove();
            } else {
                executeOn(node.getChildren());
            }
        }
    }

    private <T,C> boolean isAWhiteSpace(final Node<T,C> node) {
        final GrammarElement<T, C> ge = node.getGrammarElement();
        return ge == null ? false :
                ge.getType() == GrammarElementType.WHITE_SPACE;
    }
}
