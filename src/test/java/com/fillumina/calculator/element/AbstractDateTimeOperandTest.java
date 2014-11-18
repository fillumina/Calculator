package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class AbstractDateTimeOperandTest extends GrammarElementTestBase {

    private static final GrammarElement<Double,Void> DATE_TIME =
            new AbstractDateOperand<Double,Void>(0, "dd/MM/yy HH:mm:ss") {
        private static final long serialVersionUID = 1L;

        @Override
        public Double evaluate(String value, List<Double> params,
                Void context) {
            return null;
        }
    };

    @Override
    protected GrammarElement<Double, Void> getGrammarElement() {
        return DATE_TIME;
    }

    @Test
    public void shouldPatternDateFormatReadSingleDigit() throws ParseException {
        new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse("1/1/3 1:2:3");
    }

    @Test
    public void shouldGetADateAndTime() {
        recognize("1/12/14 12:00:00", "1/12/14 12:00:00");
    }

    @Test
    public void shouldGetTimeWithSingleDigitHour() {
        recognize("1/12/14 1:00:00", "1/12/14 1:00:00");
    }

    @Test
    public void shouldGetTimeWithSingleDigitMinute() {
        recognize("1/12/14 1:0:00", "1/12/14 1:0:00");
    }

    @Test
    public void shouldGetTimeWithSingleDigitSecond() {
        recognize("1/12/14 1:0:0", "1/12/14 1:0:0");
    }

    @Test
    public void shouldNotGetTimeWithoutSeconds() {
        notRecognize("1/12/14 1:0");
    }

    @Test
    public void shouldNotGetDateFollowedByNumber() {
        notRecognize("1/12/14 1");
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
}
