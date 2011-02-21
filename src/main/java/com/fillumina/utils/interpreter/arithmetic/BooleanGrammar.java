package com.fillumina.utils.interpreter.arithmetic;

import com.fillumina.utils.interpreter.Node;
import com.fillumina.utils.interpreter.grammar.CloseParenthesis;
import com.fillumina.utils.interpreter.grammar.EvaluableGrammarElement;
import com.fillumina.utils.interpreter.grammar.Grammar;
import com.fillumina.utils.interpreter.grammar.OpenParenthesis;
import com.fillumina.utils.interpreter.grammar.Operator;
import com.fillumina.utils.interpreter.grammar.WhiteSpace;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author fra
 */
public class BooleanGrammar extends Grammar<Boolean,Void>
        implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final BooleanGrammar INSTANCE = new BooleanGrammar();

    protected BooleanGrammar() {
        super();
        addElements();
    }

    public final void addElements() {
        add(new Operator<Boolean,Void>("(and|AND|And)", 1, 1, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Boolean evaluate(final Node node, final List<Boolean> params,
                    final Void context) {
                return params.get(0) && params.get(1);
            }
        });

        add(new Operator<Boolean,Void>("(or|OR|Or)", 1, 1, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Boolean evaluate(final Node node, final List<Boolean> params,
                    final Void context) {
                return params.get(0) || params.get(1);
            }
        });

        add(new Operator<Boolean,Void>("(not|NOT|Not)", 1, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Boolean evaluate(final Node node, final List<Boolean> params,
                    final Void context) {
                return !params.get(0);
            }
        });

        add(new EvaluableGrammarElement<Boolean,Void>("true|TRUE|True", 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public Boolean evaluate(final Node node, final List<Boolean> params,
                    final Void context) {
                return true;
            }
        });

        add(new EvaluableGrammarElement<Boolean,Void>("false|FALSE|False", 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public Boolean evaluate(final Node node, final List<Boolean> params,
                    final Void context) {
                return false;
            }
        });

        add(new OpenParenthesis("\\("));
        add(new CloseParenthesis("\\)"));
        add(new WhiteSpace("[\\ ,]+"));

    }
}
