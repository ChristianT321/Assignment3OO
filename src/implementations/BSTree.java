package implementations;

import utilities.BSTreeADT;
import utilities.Iterator;
import java.util.NoSuchElementException;

public class BSTree<E extends Comparable<? super E>> implements BSTreeADT<E> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BSTreeNode<E> root;
    private int size;

    public BSTree() {
        root = null;
        size = 0;
    }
    
    public BSTree(E element) {
        if (element == null) throw new NullPointerException("Element cannot be null.");
        this.root = new BSTreeNode<>(element);
        this.size = 1;
    }


    @Override
    public BSTreeNode<E> getRoot() {
        if (root == null) throw new NullPointerException("Tree is empty.");
        return root;
    }

    @Override
    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(BSTreeNode<E> node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight()));
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
        if (entry == null) throw new NullPointerException();
        return contains(entry, root);
    }

    private boolean contains(E entry, BSTreeNode<E> node) {
        if (node == null) return false;
        int cmp = entry.compareTo(node.getElement());
        if (cmp == 0) return true;
        else if (cmp < 0) return contains(entry, node.getLeft());
        else return contains(entry, node.getRight());
    }

    @Override
    public BSTreeNode<E> search(E entry) {
        if (entry == null) throw new NullPointerException();
        return search(entry, root);
    }

    private BSTreeNode<E> search(E entry, BSTreeNode<E> node) {
        if (node == null) return null;
        int cmp = entry.compareTo(node.getElement());
        if (cmp == 0) return node;
        else if (cmp < 0) return search(entry, node.getLeft());
        else return search(entry, node.getRight());
    }

    @Override
    public boolean add(E newEntry) {
        if (newEntry == null) throw new NullPointerException();
        if (contains(newEntry)) return false;
        root = add(root, newEntry);
        size++;
        return true;
    }

    private BSTreeNode<E> add(BSTreeNode<E> node, E newEntry) {
        if (node == null) return new BSTreeNode<>(newEntry);
        int cmp = newEntry.compareTo(node.getElement());
        if (cmp < 0) node.setLeft(add(node.getLeft(), newEntry));
        else if (cmp > 0) node.setRight(add(node.getRight(), newEntry));
        return node;
    }

    @Override
    public BSTreeNode<E> removeMin() {
        if (root == null) return null;
        BSTreeNode<E>[] result = removeMin(root);
        root = result[0];
        size--;
        return result[1];
    }

    private BSTreeNode<E>[] removeMin(BSTreeNode<E> node) {
        if (node.getLeft() == null) return new BSTreeNode[] { node.getRight(), node };
        BSTreeNode<E>[] result = removeMin(node.getLeft());
        node.setLeft(result[0]);
        result[0] = node;
        return result;
    }

    @Override
    public BSTreeNode<E> removeMax() {
        if (root == null) return null;
        BSTreeNode<E>[] result = removeMax(root);
        root = result[0];
        size--;
        return result[1];
    }

    private BSTreeNode<E>[] removeMax(BSTreeNode<E> node) {
        if (node.getRight() == null) return new BSTreeNode[] { node.getLeft(), node };
        BSTreeNode<E>[] result = removeMax(node.getRight());
        node.setRight(result[0]);
        result[0] = node;
        return result;
    }

    @Override
    public Iterator<E> inorderIterator() {
        return new InOrderIterator<>(root);
    }

    @Override
    public Iterator<E> preorderIterator() {
        return new PreOrderIterator<>(root);
    }

    @Override
    public Iterator<E> postorderIterator() {
        return new PostOrderIterator<>(root);
    }
}

