package com.fillumina.calculator.grammar.treebuilder;

import com.fillumina.performance.consumer.assertion.SuiteExecutionAssertion;
import com.fillumina.performance.producer.suite.ParameterContainer;
import com.fillumina.performance.producer.suite.ParametrizedExecutor;
import com.fillumina.performance.producer.suite.ParametrizedRunnable;
import com.fillumina.performance.template.ProgressionConfigurator;
import com.fillumina.performance.util.junit.JUnitParametrizedPerformanceTemplate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/**
 * {@code ArrayList} is faster to index so sublist is faster while
 * {@code LinkedList} is faster for inserting.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ArrayListOrLinkedListPerformanceTest
        extends JUnitParametrizedPerformanceTemplate<List<String>> {
    private static final int SIZE = 40;

    public static void main(String[] args) {
        new ArrayListOrLinkedListPerformanceTest()
                .executeWithIntermediateOutput();
    }

    @Override
    public void init(ProgressionConfigurator config) {
        config.setMaxStandardDeviation(9);
    }

    @Override
    public void addParameters(ParameterContainer<List<String>> parameters) {
        parameters
                .addParameter("ArrayList", new ArrayList<String>(SIZE))
                .addParameter("LinkedList", new LinkedList<String>());
    }

    @Override
    public void executeTests(ParametrizedExecutor<List<String>> executor) {
        executor.executeTest("insertions",
                new ParametrizedRunnable<List<String>>() {
            private Random rnd = new Random();

            @Override
            public void setUp(List<String> list) {
                list.clear();
                for (int i=0; i<SIZE - 5; i++) {
                    list.add("" + i);
                }
            }

            @Override
            public void call(List<String> list) {
                ListIterator<String> it = list.listIterator(rnd.nextInt(SIZE - 7));
                it.next();
                it.add("alfa");
                it.add("beta");
                it.add("gamma");
                it.previous();
                it.remove();
                it.previous();
                it.remove();
                it.previous();
                it.remove();
                //System.out.println("list" + list.toString());
            }
        });

        executor.executeTest("sublists", new ParametrizedRunnable<List<String>>() {
            private Random rnd = new Random();

            @Override
            public void setUp(List<String> list) {
                list.clear();
                for (int i=0; i<SIZE; i++) {
                    list.add("" + i);
                }
            }

            @Override
            public void call(List<String> list) {
                int start = rnd.nextInt(SIZE/2);
                int end = SIZE / 2 + rnd.nextInt(SIZE/2);
                list.subList(start, end);
            }
        });
    }

    @Override
    public void addAssertions(SuiteExecutionAssertion assertion) {
        assertion.forExecution("insertions")
                .assertTest("ArrayList").slowerThan("LinkedList");
        assertion.forExecution("sublists")
                .assertTest("ArrayList").fasterThan("LinkedList");
    }
}
