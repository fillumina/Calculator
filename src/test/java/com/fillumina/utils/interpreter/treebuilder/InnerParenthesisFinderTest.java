package com.fillumina.utils.interpreter.treebuilder;

import org.junit.Before;
import com.fillumina.utils.interpreter.Node;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author fra
 */
public class InnerParenthesisFinderTest
        extends InnerParenthesisFinderTestHelper {

    private InnerParenthesisFinder ipf;

    @Before
    public void initInnerParenthesisFinder() {
        ipf = new InnerParenthesisFinder();
    }

    @Test
    public void shouldRecognizeOneParenthesisWithOneNodeInIt() {
        final List<Node> list = buildNodeList("1(2)");
        ipf.find(list);
        assertEquals(1, list.get(1).getChildren().size());
    }

    @Test
    public void shouldNotTouchTheListIfNoParenthesisArePresent() {
        final List<Node> list = buildNodeList("123");
        ipf.find(list);
        assertEquals(3, list.size());
    }

    @Test(expected=ParenthesisMismatchedException.class)
    public void shouldFireAnExceptionIfTheRightParenthesisIsMissing() {
        final List<Node> list = buildNodeList("12(3");
        ipf.find(list);
    }

    @Test(expected=ParenthesisMismatchedException.class)
    public void shouldFireAnExceptionIfTheLeftParenthesisIsMissing() {
        final List<Node> list = buildNodeList("12)3");
        ipf.find(list);
    }

    @Test
    public void shouldAParenthesisWithoutNodesBeRemoved() {
        final List<Node> list = buildNodeList("12()3");
        ipf.find(list);
        assertEquals("123", buildExpression(list));
    }

    @Test
    public void shouldDoubleParenthesisBeManaged() {
        final List<Node> list = buildNodeList("12((3)4)5");
        ipf.find(list);
        assertEquals("12((4)5", buildExpression(list));
    }

    @Test
    public void shouldCaptureManyNodesInsedeTheParenthesis() {
        final List<Node> list = buildNodeList("12(3456)7");
        ipf.find(list);
        assertEquals("12(7", buildExpression(list));
    }

}
