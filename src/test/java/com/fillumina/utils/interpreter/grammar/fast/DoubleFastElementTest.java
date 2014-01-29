package com.fillumina.utils.interpreter.grammar.fast;

import com.fillumina.utils.interpreter.GrammarElement;
import com.fillumina.utils.interpreter.grammar.FastDoubleElement;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DoubleFastElementTest
        extends NumberGrammarElementTestBase {

    @Override
    protected GrammarElement<Double, Void> getNumberGrammarElement() {
        return (GrammarElement<Double, Void>) FastDoubleElement.INSTANCE;
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
        recognize("1", " -1_ string");
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
    public void shoudNotRecognizeTheSignedScientificNotationWithSignumInExp() {
        recognize("12.34E-567", " -12.34E-567 ");
    }
}
