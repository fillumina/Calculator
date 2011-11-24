package com.fillumina.utils.interpreter.arithmetic;

import com.fillumina.utils.interpreter.grammar.CloseParenthesis;
import com.fillumina.utils.interpreter.grammar.AbstractOperand;
import com.fillumina.utils.interpreter.Grammar;
import com.fillumina.utils.interpreter.grammar.OpenParenthesis;
import com.fillumina.utils.interpreter.grammar.AbstractOperator;
import com.fillumina.utils.interpreter.grammar.ConstantElement;
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
        add(new AbstractOperator<Boolean,Void>("(and|AND|And)", 1, 1, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Boolean eval(final String value, final List<Boolean> params,
                    final Void context) {
                return params.get(0) && params.get(1);
            }
        });

        add(new AbstractOperator<Boolean,Void>("(or|OR|Or)", 1, 1, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Boolean eval(final String value, final List<Boolean> params,
                    final Void context) {
                return params.get(0) || params.get(1);
            }
        });

        add(new AbstractOperator<Boolean,Void>("(not|NOT|Not)", 1, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Boolean eval(final String value, final List<Boolean> params,
                    final Void context) {
                return !params.get(0);
            }
        });

        add(new ConstantElement<Boolean,Void>("true|TRUE|True", true, 0));
        add(new ConstantElement<Boolean,Void>("false|FALSE|False", false, 0));

        add(new OpenParenthesis<Boolean,Void>("\\("));
        add(new CloseParenthesis<Boolean,Void>("\\)"));
        add(new WhiteSpace<Boolean,Void>("[\\ ,]+"));

    }
}
