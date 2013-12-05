package com.fillumina.utils.interpreter.arithmetic;

import com.fillumina.utils.interpreter.grammar.CloseParenthesis;
import com.fillumina.utils.interpreter.Grammar;
import com.fillumina.utils.interpreter.grammar.OpenParenthesis;
import com.fillumina.utils.interpreter.grammar.AbstractOperator;
import com.fillumina.utils.interpreter.grammar.ConstantElement;
import com.fillumina.utils.interpreter.grammar.WhiteSpace;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class BooleanGrammar extends Grammar<Boolean,Void>
        implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final BooleanGrammar INSTANCE = new BooleanGrammar();

    private BooleanGrammar() {
        super(
        new AbstractOperator<Boolean,Void>("(and|AND|And)", 1, 1, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Boolean eval(final String value, final List<Boolean> params,
                    final Void context) {
                return params.get(0) && params.get(1);
            }
        },

        new AbstractOperator<Boolean,Void>("(or|OR|Or)", 1, 1, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Boolean eval(final String value, final List<Boolean> params,
                    final Void context) {
                return params.get(0) || params.get(1);
            }
        },

        new AbstractOperator<Boolean,Void>("(not|NOT|Not)", 1, 0, 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public Boolean eval(final String value, final List<Boolean> params,
                    final Void context) {
                return !params.get(0);
            }
        },

        new ConstantElement<Boolean,Void>("true|TRUE|True", true, 0),
        new ConstantElement<Boolean,Void>("false|FALSE|False", false, 0),

        new OpenParenthesis<Boolean,Void>("\\("),
        new CloseParenthesis<Boolean,Void>("\\)"),
        new WhiteSpace<Boolean,Void>("[\\ ,]+")
        );

    }
}
