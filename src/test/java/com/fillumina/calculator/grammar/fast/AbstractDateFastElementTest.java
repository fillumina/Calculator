package com.fillumina.calculator.grammar.fast;

import com.fillumina.calculator.GrammarElement;
import com.fillumina.calculator.grammar.DateFastElement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class AbstractDateFastElementTest extends GrammarElementTestBase {

    @Override
    protected GrammarElement<Double, Void> getGrammarElement() {
        return DateFastElement.DATE;
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
}
