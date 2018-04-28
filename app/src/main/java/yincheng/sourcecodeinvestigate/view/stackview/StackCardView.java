package yincheng.sourcecodeinvestigate.view.stackview;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import yincheng.sourcecodeinvestigate.ui.CardItemView;

/**
 * Created by yincheng on 2018/4/27/15:58.
 * github:luoyincheng
 */

public class StackCardView extends ViewGroup {
    private int itemMarginTop = 20;
    private int yOffsetStep = 30;
    private static final float SCALE_STEP = 0.08f;
    private List<CardItemView> viewList = new ArrayList<>();
    private static final int VIEW_COUNT = 5;
    private CardAdapter adapter;
    private WeakReference<Object> savedFirstItemData;

    public StackCardView(Context context) {
        this(context, null);
    }

    public StackCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StackCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.e("wodeshijie", "onGlobalLayout");
                //这在onLayout()之后调用
                if (getChildCount() != VIEW_COUNT) {
                    doBindAdapter();
                }
            }
        });
    }

    private void doBindAdapter() {
        if (adapter == null) return;// TODO: 2018/4/28
        /**
         * 添加view到viewgroup中去
         */
        for (int i = 0; i < VIEW_COUNT; i++) {
            CardItemView itemView = new CardItemView(getContext());
            //为生成的CardItemView设置界面
            itemView.bindLayoutResId(adapter.getItemLayoutId());
            itemView.setParentView(this);
            addView(itemView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            if (i == 0)
                itemView.setAlpha(0);// TODO: 2018/4/28 透明？
        }
        /**
         * viewgroup中有了view之后将所有的view添加到ViewList中去
         */
        viewList.clear();
        for (int i = 0; i < VIEW_COUNT; i++) {
            viewList.add((CardItemView) getChildAt(i));
        }
        /**
         * // TODO: 2018/4/28
         */
        int count = adapter.getCount();
        for (int i = 0; i < VIEW_COUNT; i++) {
            if (i < count) {
                adapter.bindView(viewList.get(i), i);
                if (i == 0) {
                    savedFirstItemData = new WeakReference<>(adapter.getItemData(i));
                }
            } else {
//                throw new IndexOutOfBoundsException();
                // TODO: 2018/4/28 待处理
                viewList.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }

    public void setAdapter(final CardAdapter cardAdapter) {
        this.adapter = cardAdapter;
        doBindAdapter();
        cardAdapter.registerDataSetObserver(new DataSetObserver() {// TODO: 2018/4/28 new 了一个？这里
            @Override
            public void onChanged() {
                super.onChanged();
            }

            @Override
            public void onInvalidated() {
                super.onInvalidated();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("wodeshijie", "onMeasure()");

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("wodeshijie", "onLayout()");
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View viewItem = viewList.get(i);
            int childHeight = viewItem.getMeasuredHeight();// TODO: 2018/4/28 getheight() getmeasuredheight()的区别
            int viewLeft = (getWidth() - viewItem.getMeasuredWidth()) / 2;
            viewItem.layout(viewLeft, itemMarginTop, viewLeft + viewItem.getMeasuredWidth(), itemMarginTop + childHeight);

            int offset = yOffsetStep * i;
            float scale = 1 - SCALE_STEP * i;
            if (i > 2) {
                offset = yOffsetStep * 2;
                scale = 1 - SCALE_STEP * 2;
            }
            //设置垂直方向上的偏移量
            viewItem.offsetTopAndBottom(offset);// TODO: 2018/4/28 为什么不是负数？
            viewItem.setPivotX(viewItem.getMeasuredWidth() / 2);
            viewItem.setPivotY(viewItem.getMeasuredHeight());// TODO: 2018/4/28 why not /2
//            viewItem.setScaleX(scale);
            viewItem.setScaleY(scale);

            if (childCount > 0) {

            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("wodeshijie", "onDraw()");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e("wodeshijie", "onAttachedToWindow()");
    }


}
