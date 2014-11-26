package com.fillumina.calculator.element;

import com.fillumina.calculator.GrammarElement;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class AbstractDoubleOperandTest extends GrammarElementTestBase {

    private static final GrammarElement<Double,Void> DOUBLE_OPERAND =
            new AbstractDoubleOperand<Double, Void>(0) {
        private static final long serialVersionUID = 1L;

        @Override
        public Double evaluate(String value, List<Double> params, Void context) {
            return Double.valueOf(value);
        }
    };

    @Override
    protected GrammarElement<Double, Void> getGrammarElement() {
        return DOUBLE_OPERAND;
    }

    @Test
    public void shouldNotRecognizeAnAlpha() {
        notRecognize("alpha beta g a m m a ");
    }

    @Test
    public void shouldRecognizeTheSimpleNumber() {
        recognize("1", "1");
    }

    @Test
    public void shouldRecognizeThePoint() {
        recognize("1.0", "1.0");
    }

    @Test
    public void shouldRejectThePointWithoutDecimal() {
        recognize("1", "1.");
    }

    @Test
    public void shouldRecognizeANumberStartingWithAPoint() {
        recognize(".1", ".1");
    }

    @Test
    public void shouldRecognizeANumberStartingWithAPointAndSpace() {
        recognize(".1", " .1");
    }

    @Test
    public void shouldRecognizeANumberStartingWithEByAvoidingE() {
        recognize("12", "E-12");
    }

    @Test
    public void shouldNotRecognizeTwoPoints() {
        recognize("12.10", "12.10.12");
    }

    @Test
    public void shouldNotRecognizeTwoPointsWhenOneIsThefirstCharacter() {
        recognize(".10", ".10.12");
    }

    @Test
    public void shouldNotRecognizeTwoConsecutivePoints() {
        recognize("10", "10..12");
    }

    @Test
    public void shouldRecognizeABigNumberWithSpacesAround() {
        recognize("0100.12345", "  0100.12345 ");
    }

    @Test
    public void shouldRecognizeADigitInAlphaString() {
        recognize("1", " this is 1_ string");
    }

    @Test
    public void shouldNotRecognizeASignedDigitInAlphaString() {
        recognize("1", " this is -1_ string");
    }

    @Test
    public void shouldNotRecognizeASignedDigitInAlphaStringWithoutParOrOperator() {
        recognize("-1", " -1_ string");
    }

    @Test
    public void shouldRecognizeABigNumberWithSpacesAndCharactersAround() {
        recognize("0100.12345", " a l pha 0100.12345beta ");
    }

    @Test
    public void shouldRecognizeABigNumberWithSpacesAndCharactersAround2() {
        recognize("0100.12345", " a l pha0100.12345 b e ta ");
    }

    @Test
    public void shouldRecognizeANumberWithAnotherNumberAfter() {
        recognize("0100.12345", " 0100.12345 123 ");
    }

    @Test
    public void shouldRecognizeANumberWithMultiplePoints() {
        recognize("0100.12345", " 0100.12345.123 ");
    }

    @Test
    public void shoudRecognizeTheScientificNotation() {
        recognize("12.34E567", " 12.34E567 E e");
    }

    @Test
    public void shoudRecognizeTheScientificNotationEndingWithAnA() {
        recognize("12.34E567", " 12.34E567A a");
    }

    @Test
    public void shoudRecognizeTheScientificNotationEndingWithAnE() {
        recognize("12.34E567", " 12.34E567E e");
    }

    @Test
    public void shoudRecognizeTheScientificNotationWithSignumInExp() {
        recognize("12.34E+567", " 12.34E+567 ");
    }

    @Test
    public void shoudRecognizeTheScientificNotationWithSignumMinusInExp() {
        recognize("12.34E-567", " 12.34E-567 ");
    }

    @Test
    public void shoudRecognizeTheSignedScientificNotationWithSignumInExp() {
        recognize("-12.34E-567", " -12.34E-567 ");
    }

    @Test
    public void shoudRecognizeTheSignedScientificNotationWithSmallCaseE() {
        recognize("12.34e-567", "12.34e-567");
    }

    @Test
    public void shouldNotRecognizeADoubleStartingWithE() {
        recognize("2", "E-2");
    }

    @Test
    public void shouldNotRecognizeADoubleStartingWithADot() {
        recognize("-2", ".-2");
    }

    /**
     * The string here is not converted into an actual double but only
     * recognized as such.
     */
    @Test
    public void shouldReadAVeryLongDouble() {
        final String veryLongNumber =
                "123456789012345678901234567890.12345678901234567890";
        recognize(veryLongNumber, veryLongNumber);
    }
}
