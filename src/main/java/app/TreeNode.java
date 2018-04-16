package app;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<E> {
    private E item;
    private TreeNode<E> parent;
    private List<TreeNode<E>> childen;

    public TreeNode (E item) {
        this.item = item;
        this.parent = null;
        this.childen = new ArrayList<>();
    }

    public E getItem () {
        return item;
    }

    public TreeNode<E> getParent () {
        return parent;
    }

    public List<TreeNode<E>> getChilden () {
        return childen;
    }

    public void addChild(TreeNode<E> child) {
        child.parent = this;
        childen.add(child);
    }
}
