package implementations;

import utilities.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class PostOrderIterator<E> implements Iterator<E> {
    private Stack<BSTreeNode<E>> stack1;
    private Stack<E> stack2;

    public PostOrderIterator(BSTreeNode<E> root) {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
        if (root != null) stack1.push(root);

        while (!stack1.isEmpty()) {
            BSTreeNode<E> node = stack1.pop();
            stack2.push(node.getElement());

            if (node.getLeft() != null) stack1.push(node.getLeft());
            if (node.getRight() != null) stack1.push(node.getRight());
        }
    }

    @Override
    public boolean hasNext() {
        return !stack2.isEmpty();
    }

    @Override
    public E next() {
        if (!hasNext()) throw new NoSuchElementException();
        return stack2.pop();
    }
}
