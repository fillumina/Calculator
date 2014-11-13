package com.fillumina.calculator.util;

/**
 * An helper to make regular expressions easier to write and read (and possibly
 * debug). It's an almost complete implementation.
 * <p>
 The following REGEXP is quite complex to follow:
 <pre>
    private static final String SCIENTIFIC_NOTATION_NUMBER_REGEXP =
            "((?<=(([\\*\\+\\-/\\^][\\t\\n\\ ]{0,100})|" +
                "(\\D\\ {100})|(\\(\\ {0,100})))[\\+\\-])?" +
                "\\d+\\.?\\d+([Ee][\\+\\-]?\\d+)?";
 * </pre>
 * It's much easier to read like this:
 * <pre>
    public static String getDoublePattern() {
        return p().group(
                p().positiveLookBehind(
                    p_or(
                        p_group().opt('*', '+', '-', '/', '^')
                            .opt('\t', '\n', ' ').repeate(0, 100),
                        p_group().notDigit().c(' ').repeateExactly(100),
                        p_group().c('(').c(' ').repeate(0, 100)
                    )
                ).opt('+', '-')
            ).eventually()

            .digit().many()
            .c('.').eventually().digit().atLeastOnce()

            .group(
                    p().opt('E', 'e').opt('+', '-').eventually()
                        .digit().atLeastOnce()
            ).eventually()
            .toString();
    }
 </pre>
 * And you can even comment the code with standard java comments!
 *
 * @see <a href='http://www.regular-expressions.info/reference.html'>
 *      http://www.regular-expressions.info/reference.html</a>
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class PatternBuilder {

    private final boolean parentheses;
    private final StringBuilder builder = new StringBuilder();

    /** Add a static import to use this as a shortcut. */
    public static PatternBuilder p() {
        return new PatternBuilder();
    }

    public static PatternBuilder p(final char character) {
        return new PatternBuilder().c(character);
    }

    public static PatternBuilder p_group() {
        return new PatternBuilder(true);
    }

    public static PatternBuilder p_or(final PatternBuilder... options) {
        return new PatternBuilder().or(options);
    }

    public PatternBuilder() {
        this(false);
    }

    /** *  Start a new {@link PatternBuilder} enclosed by parentheses. */
    public PatternBuilder(boolean parentheses) {
        this.parentheses = parentheses;
        if (parentheses) {
            put("(");
        }
    }

    /** Just append another {@link PatternBuilder} as it is. */
    public PatternBuilder append(final PatternBuilder pb) {
        builder.append(pb);
        return this;
    }

    /** Start a new {@link PatternBuilder} inserting a single escaped char. */
    public PatternBuilder c(final char c) {
        escape(c);
        return this;
    }

    /** Escape a sequence so it is not interpreted as commands. */
    public PatternBuilder literal(final String s) {
        builder.append("\\Q").append(s).append("\\E");
        return this;
    }

    public PatternBuilder or(final PatternBuilder... options) {
        builder.append('(').append(options[0]);
        for (int i=1; i<options.length; i++) {
            builder.append('|').append(options[i]);
        }
        builder.append(')');
        return this;
    }

    /**
     * NOTE: the sequence is not surrounded by parentheses,
     * the eventual quantifier that may follow applies only to the last element.
     */
    public PatternBuilder seq(final char... options) {
        addOptions(options);
        return this;
    }

    /**
     * Defines
     * It's used heavily and so it deserves a short name.
     */
    public PatternBuilder opt(final char... options) {
        builder.append('[');
        addOptions(options);
        builder.append(']');
        return this;
    }

    /** Defines a group of optional characters It's used heavily and deserves a short name. */
    public PatternBuilder nopt(final char... options) {
        builder.append("[^");
        addOptions(options);
        builder.append(']');
        return this;
    }

    private void addOptions(final char[] options) {
        for (char c: options) {
            escape(c);
        }
    }

    private void escape(char c) {
        switch (c) {
            case '\n': builder.append("\\n"); break;
            case '\t': builder.append("\\t"); break;

            case ' ':
            case '\\':
            case '(':
            case ')':
            case '[':
            case ']':
            case '{':
            case '}':
            case '-':
            case '+':
            case '*':
            case '.':
            case '^':
            case '$':
                builder.append('\\');

            default:
                builder.append(c);
        }
    }

    public PatternBuilder repeate(final int min, final int max) {
        builder.append('{').append(min).append(',').append(max).append('}');
        return this;
    }

    public PatternBuilder repeateExactly(final int number) {
        builder.append('{').append(number).append('}');
        return this;
    }

    public PatternBuilder repeateMinimum(final int number) {
        builder.append('{').append(number).append(",}");
        return this;
    }

    public PatternBuilder repeateMaximum(final int number) {
        builder.append("{,").append(number).append('}');
        return this;
    }

    public PatternBuilder matchGroup(final int n) {
        return put("\\" + n);
    }

    /** Put the content between parentheses. */
    public PatternBuilder group(final PatternBuilder p) {
        builder.append('(').append(p).append(')');
        return this;
    }

    /**
     * Makes the passed group optional. Greedy, so the optional item is
     * included in the match if possible.<p>
     * abc? matches abc or ab
     */
    public PatternBuilder eventually(final PatternBuilder p) {
        builder.append('(').append(p).append(")?");
        return this;
    }

    /**
     * Repeats passed group zero or more times. Greedy, so as many
     * items as possible will be matched before trying permutations with
     * less matches of the preceding item, up to the point where the preceding
     * item is not matched at all.<p>
     * ".*" matches "def" "ghi" in abc "def" "ghi" jkl
     */
    public PatternBuilder many(final PatternBuilder p) {
        builder.append('(').append(p).append(")*");
        return this;
    }

    /**
     * Repeats passed group once or more. Greedy, so as many items as
     * possible will be matched before trying permutations with less matches
     * of the preceding item, up to the point where the preceding item is
     * matched only once.<p>
     * ".+" matches "def" "ghi" in abc "def" "ghi" jkl
     */
    public PatternBuilder atLeastOnce(final PatternBuilder p) {
        builder.append('(').append(p).append(")+");
        return this;
    }

    /**
     * Repeats the previous item zero or more times. Greedy, so as many
     * items as possible will be matched before trying permutations with
     * less matches of the preceding item, up to the point where the preceding
     * item is not matched at all.<p>
     * ".*" matches "def" "ghi" in abc "def" "ghi" jkl
     */
    public PatternBuilder many() {
        return put("*");
    }

    /**
     * Repeats the previous item once or more. Greedy, so as many items as
     * possible will be matched before trying permutations with less matches
     * of the preceding item, up to the point where the preceding item is
     * matched only once.<p>
     * ".+" matches "def" "ghi" in abc "def" "ghi" jkl
     */
    public PatternBuilder atLeastOnce() {
        return put("+");
    }

    /**
     * Makes the preceding item optional. Greedy, so the optional item is
     * included in the match if possible.<p>
     * abc? matches abc or ab
     */
    public PatternBuilder eventually() {
        return put("?");
    }

    public PatternBuilder lazy() {
        return put("?");
    }

    public PatternBuilder possessive() {
        return put("+");
    }

    public PatternBuilder start() {
        return put("^");
    }

    public PatternBuilder end() {
        return put("$");
    }

    public PatternBuilder any() {
        return put(".");
    }

    public PatternBuilder digit() {
        return put("\\d");
    }

    public PatternBuilder notDigit() {
        return put("\\D");
    }

    public PatternBuilder newline() {
        return put("\\n");
    }

    public PatternBuilder notNewline() {
        return put("\\N");
    }

    public PatternBuilder linebreak() {
        return put("\\R");
    }

    public PatternBuilder alpha() {
        return put("[a-zA-Z]");
    }

    public PatternBuilder notAlpha() {
        return put("[^a-zA-Z]");
    }

    public PatternBuilder wordStart() {
        return put("\\b");
    }

    public PatternBuilder notWordStart() {
        return put("\\B");
    }

    public PatternBuilder wordCharacter() {
        return put("\\w");
    }

    public PatternBuilder notWordCharacter() {
        return put("\\W");
    }

    public PatternBuilder whitespace() {
        return put("\\s");
    }

    public PatternBuilder notWhitespace() {
        return put("\\S");
    }

    /**
     * Matches at a position where the pattern inside the lookahead can be
     * matched. Matches only the position. It does not consume any characters
     * or expand the match. In a pattern like one(?=two)three, both two and
     * three have to match at the position where the match of one ends.
     * <p>
     * t(?=s) matches the second t in streets.
     */
    public PatternBuilder positiveLookAhead(final PatternBuilder p) {
        return group(p().put("?=").put(p.toString()));
    }

    /**
     * Similar to positive lookahead, except that negative lookahead only
     * succeeds if the regex inside the lookahead fails to match.
     * <p>
     * t(?!s) matches the first t in streets.
     */
    public PatternBuilder negativeLookAhead(final PatternBuilder p) {
        return group(p().put("?!").put(p.toString()));
    }

    /**
     * Matches at a position if the pattern inside the lookbehind can be
     * matched ending at that position.
     * <p>
     * (?<=s)t matches the first t in streets.
     */
    public PatternBuilder positiveLookBehind(final PatternBuilder p) {
        return group(p().put("?<=").put(p.toString()));
    }

    /**
     * Matches at a position if the pattern inside the lookahead cannot be
     * matched ending at that position.
     * <p>
     * (?<!s)t matches the second t in streets.
     */
    public PatternBuilder negativeLookBehind(final PatternBuilder p) {
        return group(p().put("?<!").put(p.toString()));
    }

    /**
     * Allows to enter some regexp directly. Don't abuse it or you will
     * void the usefulness of this builder.
     */
    public PatternBuilder put(final String s) {
        builder.append(s);
        return this;
    }

    @Override
    public String toString() {
        if (parentheses) {
            builder.append(')');
        }
        return builder.toString();
    }
}
