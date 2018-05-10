package yincheng.sourcecodeinvestigate.androidinterviewpoint.treeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Px;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import yincheng.sourcecodeinvestigate.R;

public class TreeView extends AdapterView<TreeAdapter> implements GestureDetector.OnGestureListener {

    private static final int DEFAULT_LINE_LENGTH = 100;
    private static final int DEFAULT_LINE_THICKNESS = 10;
    private static final int DEFAULT_LINE_COLOR = Color.BLACK;
    public static final boolean DEFAULT_USE_MAX_SIZE = false;
    private static final int INVALID_INDEX = -1;

    Path mLinePath = new Path();
    Paint mLinePaint = new Paint();
    private int mLineThickness;
    private int mLineColor;
    private int mLevelSeparation;

    private boolean mUseMaxSize;

    private TreeAdapter mAdapter;
    private int mMaxChildWidth;
    private int mMaxChildHeight;
    private int mMinChildHeight;
    private Rect mRect;
    private Rect mBoundaries = new Rect();

    private DataSetObserver mDataSetObserver;

    private GestureDetector mGestureDetector;

    public TreeView(Context context) {
        this(context, null);
    }

    public TreeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TreeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TreeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TreeView, 0, 0);
        try {
            mLevelSeparation = a.getDimensionPixelSize(R.styleable.TreeView_levelSeparation, DEFAULT_LINE_LENGTH);
            mLineThickness = a.getDimensionPixelSize(R.styleable.TreeView_lineThickness, DEFAULT_LINE_THICKNESS);
            mLineColor = a.getColor(R.styleable.TreeView_lineColor, DEFAULT_LINE_COLOR);
            mUseMaxSize = a.getBoolean(R.styleable.TreeView_useMaxSize, DEFAULT_USE_MAX_SIZE);
        } finally {
            a.recycle();
        }
    }

    private void init(Context context, AttributeSet attrs) {
        mGestureDetector = new GestureDetector(context, this);

        if (attrs != null) {
            initAttrs(context, attrs);
        }
        initPaint();
    }

    private void positionItems() {
        int maxLeft = Integer.MAX_VALUE;
        int maxRight = Integer.MIN_VALUE;
        int maxTop = Integer.MAX_VALUE;
        int maxBottom = Integer.MIN_VALUE;

        int globalPadding = 0;
        int localPadding = 0;
        int currentLevel = 0;
        for (int index = 0; index < mAdapter.getCount(); index++) {
            final View child = mAdapter.getView(index, null, this);

            addAndMeasureChild(child);

            final int width = child.getMeasuredWidth();
            final int height = child.getMeasuredHeight();

            final Point screenPosition = mAdapter.getScreenPosition(index);
            TreeNode node = mAdapter.getNode(index);

            if (height > mMinChildHeight) {
                localPadding = Math.max(localPadding, height - mMinChildHeight);
            }

            if (currentLevel != node.getLevel()) {
                globalPadding += localPadding;
                localPadding = 0;
                currentLevel = node.getLevel();
            }

            // calculate the size and position of this child
            final int left = screenPosition.x + getScreenXCenter();
            final int top = screenPosition.y * mMinChildHeight + (node.getLevel() * mLevelSeparation) + globalPadding;
            final int right = left + width;
            final int bottom = top + height;

            child.layout(left, top, right, bottom);
            node.setX(left);
            node.setY(top);

            maxRight = Math.max(maxRight, right);
            maxLeft = Math.min(maxLeft, left);
            maxBottom = Math.max(maxBottom, bottom);
            maxTop = Math.min(maxTop, top);
        }

        mBoundaries.set(maxLeft - (getWidth() - Math.abs(maxLeft)) - Math.abs(maxLeft), -getHeight(), maxRight, maxBottom);
    }

    /**
     * 返回包含该坐标(x,y)的Child下标
     */
    private int getContainingChildIndex(final int x, final int y) {
        if (mRect == null) {
            mRect = new Rect();
        }
        for (int index = 0; index < getChildCount(); index++) {
            /**
             * 获取该Child的可点击的方形区域
             */
            getChildAt(index).getHitRect(mRect);
            Log.e("wodeshijie", getPivotX() + "");
            if (mRect.contains(x, y)) {
                return index;
            }
        }
        return INVALID_INDEX;
    }

    private void clickChildAt(final int x, final int y) {
        final int index = getContainingChildIndex(x, y);
        if (index == INVALID_INDEX) {
            return;
        }

        final View itemView = getChildAt(index);
        final long id = mAdapter.getItemId(index);
        performItemClick(itemView, index, id);
    }

    private void addAndMeasureChild(final View child) {
        LayoutParams params = child.getLayoutParams();
        if (params == null) {
            params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }

        addViewInLayout(child, -1, params, false);

        int widthSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        int heightSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);

        if (mUseMaxSize) {
            widthSpec = MeasureSpec.makeMeasureSpec(
                    mMaxChildWidth, MeasureSpec.EXACTLY);
            heightSpec = MeasureSpec.makeMeasureSpec(
                    mMaxChildHeight, MeasureSpec.EXACTLY);
        }

        child.measure(widthSpec, heightSpec);
    }

    private void longClickChildAt(final int x, final int y) {
        final int index = getContainingChildIndex(x, y);
        if (index == INVALID_INDEX) {
            return;
        }

        final View itemView = getChildAt(index);
        final long id = mAdapter.getItemId(index);
        OnItemLongClickListener listener = getOnItemLongClickListener();
        if (listener != null) {
            listener.onItemLongClick(this, itemView, index, id);
        }
    }

    private void drawLines(Canvas canvas, TreeNode treeNode) {
        if (treeNode.hasChildren()) {
            for (TreeNode child : treeNode.getChildren()) {
                drawLines(canvas, child);
            }
        }

        if (treeNode.hasParent()) {
            mLinePath.reset();

            TreeNode parent = treeNode.getParent();
            mLinePath.moveTo(treeNode.getX() + (treeNode.getWidth() / 2), treeNode.getY());
            mLinePath.lineTo(treeNode.getX() + (treeNode.getWidth() / 2), treeNode.getY() - (mLevelSeparation / 2));
            mLinePath.lineTo(parent.getX() + (parent.getWidth() / 2),
                    treeNode.getY() - mLevelSeparation / 2);

            canvas.drawPath(mLinePath, mLinePaint);
            mLinePath.reset();

            mLinePath.moveTo(parent.getX() + (parent.getWidth() / 2),
                    treeNode.getY() - mLevelSeparation / 2);
            mLinePath.lineTo(parent.getX() + (parent.getWidth() / 2),
                    parent.getY() + parent.getHeight());

            canvas.drawPath(mLinePath, mLinePaint);
        }
    }

    /**
     * Returns the value of how much space should be used between two levels.
     *
     * @return level separation value
     */
    @Px
    public int getLevelSeparation() {
        return mLevelSeparation;
    }

    /**
     * Sets a new value of how much space should be used between two levels. A change to this value
     * invokes a re-drawing of the tree.
     *
     * @param levelSeparation new value for the level separation
     */
    public void setLevelSeparation(@Px int levelSeparation) {
        mLevelSeparation = levelSeparation;
        invalidate();
        requestLayout();
    }

    /**
     * @return <code>true</code> if using same size for each node, <code>false</code> otherwise.
     */
    public boolean isUsingMaxSize() {
        return mUseMaxSize;
    }

    /**
     * Whether to use the max available size for each node, so all nodes have the same size. A
     * change to this value invokes a re-drawing of the tree.
     *
     * @param useMaxSize <code>true</code> if using same size for each node, <code>false</code> otherwise.
     */
    public void setUseMaxSize(boolean useMaxSize) {
        mUseMaxSize = useMaxSize;
        invalidate();
        requestLayout();
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        clickChildAt((int) e.getX() + getScrollX(), (int) e.getY() + getScrollY());
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent downEvent, MotionEvent event, float distanceX, float distanceY) {
        final float newScrollX = getScrollX() + distanceX;
        final float newScrollY = getScrollY() + distanceY;

        if (mBoundaries.contains((int) newScrollX, (int) newScrollY)) {
            scrollBy((int) distanceX, (int) distanceY);
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        longClickChildAt((int) event.getX() + getScrollX(), (int) event.getY() + getScrollY());
    }

    @Override
    public void setAdapter(TreeAdapter adapter) {
        //“&&”的特点：如果mAdapter为空，就不会再判断mDataSetObserver是否为空了
        if (mAdapter != null && mDataSetObserver != null)
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        mAdapter = adapter;
        mDataSetObserver = new TreeDataSetObserver();
        mAdapter.registerDataSetObserver(mDataSetObserver);
        /**
         * Call this when something has changed which has invalidated the
         * layout of this view. This will schedule a layout pass of the view
         * tree. This should not be called while the view hierarchy is currently in a layout
         * pass ({@link #isInLayout()}. If layout is happening, the request may be honored at the
         * end of the current layout pass (and then layout will run again) or after the current
         * frame is drawn and the next layout occurs.
         *
         * <p>Subclasses which override this method should call the superclass method to
         * handle possible request-during-layout errors correctly.</p>
         * <p>如果要重写该方法,必须要先调用super.requestLayout()方法(该方法使用了@CallSuper注解),因为
         * super.requestLayout()方法能很好地处理“布局时”会出现的各种问题。</p>
         */
        requestLayout();
    }

    @Override
    public View getSelectedView() {
        return null;
    }

    @Override
    public void setSelection(int position) {
    }

    private int getScreenXCenter() {
        return (int) getPivotX() - getChildAt(0).getMeasuredWidth() / 2;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        TreeNode rootNode = mAdapter.getNode(0);
        if (rootNode != null) {
            drawLines(canvas, rootNode);
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        return mGestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mAdapter == null) {
            return;
        }
        int maxWidth = 0;
        int maxHeight = 0;
        int minHeight = Integer.MAX_VALUE;

        for (int i = 0; i < mAdapter.getCount(); i++) {
            View child = mAdapter.getView(i, null, this);

            LayoutParams params = child.getLayoutParams();
            if (params == null) {
                params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            }
            /**
             * Adds a view during layout. This is useful if in your onLayout() method,
             * you need to add more views (as does the list view for example).
             *
             * If index is negative, it means put it at the end of the list.
             *
             * @param child the view to add to the group
             * @param index the index at which the child must be added or -1 to add last
             * @param params the layout parameters to associate with the child
             * @param preventRequestLayout if true, calling this method will not trigger a
             *        layout request on child
             * @return true if the child was added, false otherwise
             */
            addViewInLayout(child, -1, params, true);
            /**
             * <p>
             * This is called to find out how big a view should be. The parent
             * supplies constraint information in the width and height parameters.
             * </p>
             *
             * <p>
             * The actual measurement work of a view is performed in
             * {@link #onMeasure(int, int)}, called by this method. Therefore, only
             * {@link #onMeasure(int, int)} can and must be overridden by subclasses.
             * </p>
             *
             * @param widthMeasureSpec Horizontal space requirements as imposed by the
             *        parent,父布局强加给ChildView的在水平方向上的要求
             * @param heightMeasureSpec Vertical space requirements as imposed by the
             *        parent,父布局强加给ChildView的在垂直方向上的要求
             *
             * @see #onMeasure(int, int)
             */
            //measure方法实际上还是调用的onMeasure()方法，这里有点模糊
            // TODO: 2018/5/10 父类对子类的布局约束到底如何起作用
            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            TreeNode node = mAdapter.getNode(i);
            final int measuredWidth = child.getMeasuredWidth();
            final int measuredHeight = child.getMeasuredHeight();
            node.setSize(measuredWidth, measuredHeight);

            maxWidth = Math.max(maxWidth, measuredWidth);
            maxHeight = Math.max(maxHeight, measuredHeight);
            minHeight = Math.min(minHeight, measuredHeight);
        }

        mMaxChildWidth = maxWidth;
        mMaxChildHeight = maxHeight;
        mMinChildHeight = minHeight;

        if (mUseMaxSize) {
            removeAllViewsInLayout();
            for (int i = 0; i < mAdapter.getCount(); i++) {
                View child = mAdapter.getView(i, null, this);

                LayoutParams params = child.getLayoutParams();
                if (params == null) {
                    params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                }
                addViewInLayout(child, -1, params, true);

                final int widthSpec = MeasureSpec.makeMeasureSpec(mMaxChildWidth, MeasureSpec.EXACTLY);
                final int heightSpec = MeasureSpec.makeMeasureSpec(mMaxChildHeight, MeasureSpec.EXACTLY);
                child.measure(widthSpec, heightSpec);

                TreeNode node = mAdapter.getNode(i);
                node.setSize(child.getMeasuredWidth(), child.getMeasuredHeight());
            }
        }

        mAdapter.notifySizeChanged();
    }


    private class TreeDataSetObserver extends DataSetObserver {

        @Override
        public void onChanged() {
            super.onChanged();
            refresh();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            refresh();
        }

        private void refresh() {
            invalidate();
            requestLayout();
        }


    }

    /*********************************************************************************************/
    public void setLineThickness(int lineThickness) {
        mLineThickness = lineThickness;
        initPaint();
        invalidate();
    }

    public int getLineThickness() {
        return mLineThickness;
    }

    @ColorInt
    public int getLineColor() {
        return mLineColor;
    }

    public void setLineColor(@ColorInt int lineColor) {
        mLineColor = lineColor;
        initPaint();
        invalidate();//重绘整个View
    }

    private void initPaint() {
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStrokeWidth(mLineThickness);
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeJoin(Paint.Join.ROUND);    // set the join to round you want
//        mLinePaint.setStrokeCap(Paint.Cap.ROUND);      // set the paint cap to round too
        mLinePaint.setPathEffect(new CornerPathEffect(10));   // set the path effect when they join.
    }

    @Override
    protected void onLayout(final boolean changed, final int left, final int top, final int right,
                            final int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mAdapter == null) {
            return;
        }
        //Layout之前先移除之前存在的所有childView
        removeAllViewsInLayout();
        positionItems();
        invalidate();
    }

    @Override
    public TreeAdapter getAdapter() {
        return mAdapter;
    }

    /**
     * ##快速滑过
     * Notified of a fling event when it occurs with the initial on down {@link MotionEvent}
     * and the matching up {@link MotionEvent}. The calculated velocity is supplied along
     * the x and y axis in pixels per second.
     *
     * @param event1    The first down motion event that started the fling.
     * @param event2    The move motion event that triggered the current onFling.
     * @param velocityX The velocity of this fling measured in pixels per second
     *                  along the x axis.
     * @param velocityY The velocity of this fling measured in pixels per second
     *                  along the y axis.
     * @return true if the event is consumed, else false
     */
    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
        return true;
    }
}
