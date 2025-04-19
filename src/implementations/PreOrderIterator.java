package implementations;

import utilities.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class PreOrderIterator<E> implements Iterator<E> {
    private Stack<BSTreeNode<E>> stack;

    public PreOrderIterator(BSTreeNode<E> root) {
        stack = new Stack<>();
        if (root != null) stack.push(root);
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public E next() {
        if (!hasNext()) throw new NoSuchElementException();

        BSTreeNode<E> node = stack.pop();
        E result = node.getElement();

        // Push right first so that left is processed first
        if (node.getRight() != null) stack.push(node.getRight());
        if (node.getLeft() != null) stack.push(node.getLeft());

        return result;
    }
}
