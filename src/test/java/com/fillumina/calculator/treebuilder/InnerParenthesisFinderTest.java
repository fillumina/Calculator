package com.fillumina.calculator.treebuilder;

import com.fillumina.calculator.Node;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class InnerParenthesisFinderTest
        extends InnerParenthesisFinderTestHelper {

    private InnerParenthesisFinder ipf;

    @Before
    public void initInnerParenthesisFinder() {
        ipf = InnerParenthesisFinder.INSTANCE;
    }

    @Test
    public void shouldRecognizeOneParenthesisWithOneNodeInIt() {
        final List<Node<Long, Void>> list = buildNodeList("1(23)");
        ipf.find(list);
        assertEquals(2, list.get(1).getChildren().size());
    }

    @Test
    public void shouldNotTouchTheListIfNoParenthesisArePresent() {
        final List<Node<Long, Void>> list = buildNodeList("123");
        ipf.find(list);
        assertEquals(3, list.size());
    }

    @Test(expected=ParenthesesMismatchedException.class)
    public void shouldFireAnExceptionIfTheRightParenthesisIsMissing() {
        final List<Node<Long, Void>> list = buildNodeList("12(3");
        ipf.find(list);
    }

    @Test(expected=ParenthesesMismatchedException.class)
    public void shouldFireAnExceptionIfTheLeftParenthesisIsMissing() {
        final List<Node<Long, Void>> list = buildNodeList("12)3");
        ipf.find(list);
    }

    @Test
    public void shouldAParenthesisWithoutNodesBeRemoved() {
        final List<Node<Long, Void>> list = buildNodeList("12()3");
        ipf.find(list);
        assertEquals("123", buildExpression(list));
    }

    @Test
    public void shouldDoubleParenthesisBeManaged() {
        final List<Node<Long, Void>> list = buildNodeList("12((34)5)6");
        ipf.find(list);
        assertEquals("12((5)6", buildExpression(list));
    }

    @Test
    public void shouldCaptureManyNodesInsedeTheParenthesis() {
        final List<Node<Long, Void>> list = buildNodeList("12(3456)7");
        ipf.find(list);
        assertEquals("12(7", buildExpression(list));
    }
}
