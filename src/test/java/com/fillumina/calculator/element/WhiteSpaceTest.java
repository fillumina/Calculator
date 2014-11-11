package com.fillumina.calculator.element;

import com.fillumina.calculator.Calculator;
import com.fillumina.calculator.grammar.Evaluator;
import com.fillumina.calculator.grammar.SettableContextedGrammarBuilder;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class WhiteSpaceTest {

    @Test
    public void shouldFilterOutCommonWhiteSpaces() {
        final Calculator<Integer,Map<String,Integer>> calc = new Calculator<>(
            new SettableContextedGrammarBuilder<Integer>()
                .addIntOperand(new Evaluator
                            <Integer, Map<String,Integer>>() {
                        @Override
                        public Integer evaluate(String value,
                                Map<String,Integer> context) {
                            return Integer.valueOf(value);
                        }
                    })
                .add(WhiteSpace.<Integer,Map<String,Integer>>instance())
                .buildGrammar());

        assertEquals(12, calc.solveSingleValue(" \t\n12 "), 0);
    }

    @Test
    public void shouldShouldFilterOutGivenWhiteSpaces() {
        final Calculator<Integer,Map<String,Integer>> calc = new Calculator<>(
            new SettableContextedGrammarBuilder<Integer>()
                .addIntOperand(new Evaluator
                            <Integer, Map<String,Integer>>() {
                        @Override
                        public Integer evaluate(String value,
                                Map<String,Integer> context) {
                            return Integer.valueOf(value);
                        }
                    })
                .add(new WhiteSpace<Integer,Map<String,Integer>>(0, "_#"))
                .buildGrammar());

        assertEquals(12, calc.solveSingleValue("#___12__#"), 0);
    }

}
