package implementations;

public class BSTreeNode<E>
{
    public E element;
    public BSTreeNode<E> left;
    public BSTreeNode<E> right;
     
    public BSTreeNode(E element)
    {
        this.element = element;
        left = null;
        right = null;
    }

    public E getElement()
    {
        return element;
    }

    public void setElement(E element)
    {
        this.element = element;
    }

    public BSTreeNode<E> getLeft()
    {
        return left;
    }

    public void setLeft(BSTreeNode<E> left)
    {
        this.left = left;
    }

    public BSTreeNode<E> getRight()
    {
        return right;
    }

    public void setRight(BSTreeNode<E> right)
    {
        this.right = right;
    }

}
