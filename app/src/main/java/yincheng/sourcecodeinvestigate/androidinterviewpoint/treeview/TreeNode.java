package yincheng.sourcecodeinvestigate.androidinterviewpoint.treeview;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 */

public class TreeNode {
    private Object mData;
    private int mX;//左上角X坐标
    private int mY;//左上角Y坐标
    private int mWidth;
    private int mHeight;
    private int mLevel;
    private int mNodeCount = 1;//该TreeNode中包含的所有TreeNode，父子相加的总数
    private TreeNode mParent;//除了定点之外的TreeNode之外的所有TreeNode都有parentTreeNode
    private List<TreeNode> mChildren = new ArrayList<>();
    private List<TreeNodeObserver> mTreeNodeObservers = new ArrayList<>();

    public void setData(Object data) {
        mData = data;
        for (TreeNodeObserver observer : getTreeNodeObservers()) {
            observer.notifyDataChanged(this);
        }
    }

    private void notifyParentNodeCountChanged() {
        if (mParent != null) {
            mParent.notifyParentNodeCountChanged();
        } else {
            calculateNodeCount();
        }
    }

    public void addChild(TreeNode child) {
        mChildren.add(child);
        child.setParent(this);
        notifyParentNodeCountChanged();
        for (TreeNodeObserver observer : getTreeNodeObservers()) {
            observer.notifyNodeAdded(child, this);
        }
        Log.e("wodeshijie", "mTreeNodeObservers.size()" + mTreeNodeObservers.size());
    }

    public void removeChild(TreeNode child) {
        child.setParent(null);
        mChildren.remove(child);
        notifyParentNodeCountChanged();
        for (TreeNodeObserver observer : getTreeNodeObservers()) {
            observer.notifyNodeRemoved(child, this);
        }
    }

    void addTreeNodeObserver(TreeNodeObserver observer) {
        Log.e("wodeshijie", "addTreeNodeObserver()");
        mTreeNodeObservers.add(observer);
    }

    void removeTreeNodeObserver(TreeNodeObserver observer) {
        Log.e("wodeshijie", "removeTreeNodeObserver()");
        mTreeNodeObservers.remove(observer);
    }

    private List<TreeNodeObserver> getTreeNodeObservers() {
        List<TreeNodeObserver> observers = mTreeNodeObservers;
        if (observers.isEmpty() && mParent != null) {
            observers = mParent.getTreeNodeObservers();
        }
        return observers;
    }

    public boolean isFirstChild(TreeNode node) {
        return mChildren.indexOf(node) == 0;
    }

    /*********************************************************************************************/

    public boolean hasData() {
        return mData != null;
    }

    public void addChildren(TreeNode... children) {
        addChildren(Arrays.asList(children));
    }

    public void addChildren(List<TreeNode> children) {
        for (TreeNode child : children) {
            addChild(child);
        }
    }

    public int getNodeCount() {
        return mNodeCount;
    }

    public TreeNode getParent() {
        return mParent;
    }

    public void setParent(TreeNode parent) {
        mParent = parent;
    }

    public List<TreeNode> getChildren() {
        return mChildren;
    }

    public boolean hasChildren() {
        return !mChildren.isEmpty();
    }

    public boolean hasParent() {
        return mParent != null;
    }

    @Override
    public String toString() {
        String indent = "\t";
        for (int i = 0; i < (mY / 10); i++) {
            indent += indent;
        }
        return "\n" + indent + "TreeNode{" +
                " data=" + mData +
                ", mX=" + mX +
                ", mY=" + mY +
                ", mChildren=" + mChildren +
                '}';
    }

    public TreeNode() {
        this(null);
    }

    public TreeNode(Object data) {
        mData = data;
    }

    int getLevel() {
        return mLevel;
    }

    void setLevel(int level) {
        mLevel = level;
    }

    int getX() {
        return mX;
    }

    void setX(int x) {
        mX = x;
    }

    int getY() {
        return mY;
    }

    void setY(int y) {
        mY = y;
    }

    int getWidth() {
        return mWidth;
    }

    int getHeight() {
        return mHeight;
    }

    void setSize(int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    public Object getData() {
        return mData;
    }

    private int calculateNodeCount() {
        int size = 1;
        for (TreeNode child : mChildren) {
            size += child.calculateNodeCount();
        }
        return mNodeCount = size;
    }
}
