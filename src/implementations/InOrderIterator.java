package implementations;

import utilities.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class InOrderIterator<E> implements Iterator<E> {
    private Stack<BSTreeNode<E>> stack;

    public InOrderIterator(BSTreeNode<E> root) {
        stack = new Stack<>();
        pushLeft(root);
    }

    private void pushLeft(BSTreeNode<E> node) {
        while (node != null) {
            stack.push(node);
            node = node.getLeft();
        }
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

        if (node.getRight() != null) {
            pushLeft(node.getRight());
        }

        return result;
    }
}
