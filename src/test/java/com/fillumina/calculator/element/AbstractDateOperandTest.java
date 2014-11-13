package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.GrammarElementMatcher;
import com.fillumina.calculator.grammar.DateOperand;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class AbstractDateOperandTest extends GrammarElementTestBase {

    @Override
    protected GrammarElement<Double, Void> getGrammarElement() {
        return DateOperand.DATE;
    }

    @Test
    public void shouldPatternDateFormatReadSingleDigit() throws ParseException {
        new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse("1/1/3 1:2:3");
    }

    @Test
    public void shouldGetTheDate() {
        recognize("29/01/14", "29/01/14");
    }

    @Test
    public void shouldGetTheDateWithASingleDigitMonth() {
        recognize("29/1/14", "29/1/14");
    }

    @Test
    public void shouldGetTheDateWithASingleDigitDay() {
        recognize("1/1/14", "1/1/14");
    }

    @Test
    public void shouldGetTheDateWithASingleDigitYear() {
        recognize("1/1/4", "1/1/4");
    }

    @Test
    public void shouldNotGetA3DigitDay() {
        recognize("23/1/14", "123/1/14");
    }

    @Test
    public void shouldNotGetA3DigitMonth() {
        notRecognize("1/123/14");
    }

    @Test
    public void shouldNotRecognizeGibberish() {
        notRecognize("alfabeta");
    }

    @Test
    public void shouldNotGetA3DigitYear() {
        recognize("1/12/14", "1/12/143");
    }

    @Test
    public void shouldGetADateWithSpacesBefore() {
        recognize("1/12/14", "  1/12/14");
    }

    @Test
    public void shouldGetADateWithSpacesAfter() {
        recognize("1/12/14", "1/12/14   ");
    }

    @Test
    public void shouldGetADateWithSpacesBeforeAndAfter() {
        recognize("1/12/14", "   1/12/14   ");
    }

    @Test
    public void shouldGetADateWithOneSpaceBeforeAndAfter() {
        recognize("1/12/14", " 1/12/14 ");
    }

    @Test
    public void shouldGetDateFollowedByNumber() {
        recognize("1/12/14", "1/12/14 1");
    }

    @Test
    public void shouldNotRecognizeAnEmptyString() {
        notRecognize("");
    }

    @Test
    public void shouldNotRecognizeAStringNotRepresentingADate() {
        notRecognize("not a date");
    }

    @Test
    public void shouldNotRecognizeAAllNumberString() {
        notRecognize("123456");
    }

    private final GrammarElement<Date,Void> DATE_OPERAND =
            new AbstractDateOperand<Date,Void>(0, "ddMMyy") {
                private static final long serialVersionUID = 1L;

                @Override
                public Date evaluate(String value, List<Date> params, Void context) {
                    return evaluateDate(value);
                }
            };

    private void assertMatch(final String expected, final String expression) {
        GrammarElementMatcher matcher = DATE_OPERAND.match(null, expression);
        assertEquals(expected,
                expression.substring(matcher.getStart(), matcher.getEnd()));
    }

    @Test
    public void shouldUnderstandADateOfOnlyDigits() {
        assertMatch("120214", "bla bla 120214 pinco");
    }

    @Test
    public void shouldConvertADateOfOnlyDigits() {
        final String expression = "bla bla 120214 pinco";
        GrammarElementMatcher matcher = DATE_OPERAND.match(null, expression);
        String parsed = expression.substring(matcher.getStart(), matcher.getEnd());
        Date date = DATE_OPERAND.evaluate(parsed, null, null);
        assertEquals(new GregorianCalendar(2014, 1, 12).getTime(), date);
    }
}
