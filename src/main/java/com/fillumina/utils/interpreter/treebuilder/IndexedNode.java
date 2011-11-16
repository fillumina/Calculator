package com.fillumina.utils.interpreter.treebuilder;

import com.fillumina.utils.interpreter.Node;
import com.fillumina.utils.interpreter.grammar.GrammarElement;
import java.io.Serializable;

class IndexedNode implements Comparable<Node>, Serializable {
    private static final long serialVersionUID = 1L;
    public static final IndexedNode NULL =
            new IndexedNode(null, Integer.MIN_VALUE);

    private final Node node;
    private final int index;

    public IndexedNode(final Node node, final int index) {
        this.node = node;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public Node getNode() {
        return node;
    }

    public boolean lessThan(final Node o) {
        return compareTo(o) < 0;
    }

    @Override
    public int compareTo(final Node o) {
        final GrammarElement ge = o.getGrammarElement();
        return this.node.getGrammarElement().compareTo(ge);
    }

    @Override
    public String toString() {
        return "IndexedNode{" + "node=" + node + ", index=" + index + '}';
    }
}
