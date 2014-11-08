package com.fillumina.calculator.pattern;

import com.fillumina.calculator.GrammarElementType;

/**
 * Defines characters that will be ignored during parsing (usually spaces,
 * commas, tabs, linefeeds and the like).
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class PatternWhiteSpace<T,C> extends UnevaluablePatternElement<T,C> {
    private static final long serialVersionUID = 1L;

    public static final PatternWhiteSpace<?,?> INSTANCE =
            new PatternWhiteSpace<>("\\s+");

    public PatternWhiteSpace(final String symbolRegexp) {
        super(symbolRegexp, 0);
    }

    @Override
    public GrammarElementType getType() {
        return GrammarElementType.WHITE_SPACE;
    }
}
