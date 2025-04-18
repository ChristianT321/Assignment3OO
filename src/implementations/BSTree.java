package implementations;

import utilities.BSTreeADT;
import utilities.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class BSTree<E extends Comparable<? super E>> implements BSTreeADT<E> {

    private BSTreeNode<E> root;
    private int size;

    public BSTree() {
        root = null;
        size = 0;
    }

    @Override
    public BSTreeNode<E> getRoot() {
        if (root == null) throw new NullPointerException("Tree is empty.");
        return root;
    }

    @Override
    public int getHeight() {
        return calculateHeight(root);
    }

    private int calculateHeight(BSTreeNode<E> node) {
        if (node == null) return 0;
        return 1 + Math.max(calculateHeight(node.getLeft()), calculateHeight(node.getRight()));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean contains(E entry) {
        if (entry == null) throw new NullPointerException("Cannot search for null.");
        return search(entry) != null;
    }

    @Override
    public BSTreeNode<E> search(E entry) {
        if (entry == null) throw new NullPointerException("Cannot search for null.");
        return searchRecursive(root, entry);
    }

    private BSTreeNode<E> searchRecursive(BSTreeNode<E> node, E entry) {
        if (node == null) return null;
        int cmp = entry.compareTo(node.getElement());
        if (cmp == 0) return node;
        else if (cmp < 0) return searchRecursive(node.getLeft(), entry);
        else return searchRecursive(node.getRight(), entry);
    }

    @Override
    public boolean add(E newEntry) {
        if (newEntry == null) throw new NullPointerException("Cannot add null.");
        if (root == null) {
            root = new BSTreeNode<>(newEntry);
            size++;
            return true;
        }
        return addRecursive(root, newEntry);
    }

    private boolean addRecursive(BSTreeNode<E> node, E entry) {
        int cmp = entry.compareTo(node.getElement());
        if (cmp == 0) return false;
        else if (cmp < 0) {
            if (node.getLeft() == null) {
                node.setLeft(new BSTreeNode<>(entry));
                size++;
                return true;
            }
            return addRecursive(node.getLeft(), entry);
        } else {
            if (node.getRight() == null) {
                node.setRight(new BSTreeNode<>(entry));
                size++;
                return true;
            }
            return addRecursive(node.getRight(), entry);
        }
    }

    @Override
    public BSTreeNode<E> removeMin() {
        if (root == null) return null;
        if (root.getLeft() == null) {
            BSTreeNode<E> min = root;
            root = root.getRight();
            size--;
            return min;
        }
        return removeMinRecursive(root, root.getLeft());
    }

    private BSTreeNode<E> removeMinRecursive(BSTreeNode<E> parent, BSTreeNode<E> current) {
        if (current.getLeft() == null) {
            parent.setLeft(current.getRight());
            size--;
            return current;
        }
        return removeMinRecursive(current, current.getLeft());
    }

    @Override
    public BSTreeNode<E> removeMax() {
        if (root == null) return null;
        if (root.getRight() == null) {
            BSTreeNode<E> max = root;
            root = root.getLeft();
            size--;
            return max;
        }
        return removeMaxRecursive(root, root.getRight());
    }

    private BSTreeNode<E> removeMaxRecursive(BSTreeNode<E> parent, BSTreeNode<E> current) {
        if (current.getRight() == null) {
            parent.setRight(current.getLeft());
            size--;
            return current;
        }
        return removeMaxRecursive(current, current.getRight());
    }

    @Override
    public Iterator<E> inorderIterator() {
        LinkedList<E> elements = new LinkedList<>();
        inorderRecursive(root, elements);
        return new SimpleIterator(elements);
    }

    private void inorderRecursive(BSTreeNode<E> node, LinkedList<E> list) {
        if (node != null) {
            inorderRecursive(node.getLeft(), list);
            list.add(node.getElement());
            inorderRecursive(node.getRight(), list);
        }
    }

    @Override
    public Iterator<E> preorderIterator() {
        LinkedList<E> elements = new LinkedList<>();
        preorderRecursive(root, elements);
        return new SimpleIterator(elements);
    }

    private void preorderRecursive(BSTreeNode<E> node, LinkedList<E> list) {
        if (node != null) {
            list.add(node.getElement());
            preorderRecursive(node.getLeft(), list);
            preorderRecursive(node.getRight(), list);
        }
    }

    @Override
    public Iterator<E> postorderIterator() {
        LinkedList<E> elements = new LinkedList<>();
        postorderRecursive(root, elements);
        return new SimpleIterator(elements);
    }

    private void postorderRecursive(BSTreeNode<E> node, LinkedList<E> list) {
        if (node != null) {
            postorderRecursive(node.getLeft(), list);
            postorderRecursive(node.getRight(), list);
            list.add(node.getElement());
        }
    }

    private class SimpleIterator implements Iterator<E> {
        private LinkedList<E> data;

        public SimpleIterator(LinkedList<E> list) {
            this.data = list;
        }

        @Override
        public boolean hasNext() {
            return !data.isEmpty();
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements.");
            }
            return data.removeFirst();
        }
    }
}
