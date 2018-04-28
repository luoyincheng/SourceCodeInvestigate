package yincheng.sourcecodeinvestigate.view.widget.smartisanswitchbutton;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yincheng on 2018/4/28/18:40.
 * github:luoyincheng
 */
public class smartisanswitchbutton extends ViewGroup {
    private SwitchInnerView innerView;
    private SwitchBorderView borderView;
    private ViewDragHelper mDragHelper;

    public smartisanswitchbutton(Context context) {
        this(context, null);
    }

    public smartisanswitchbutton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public smartisanswitchbutton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        innerView = new SwitchInnerView(context);
        borderView = new SwitchBorderView(context);
        addView(borderView);
        addView(innerView);
        mDragHelper = ViewDragHelper.create(this, 10f, new DragHelperCallback());
        Log.e("fasdf", "初始化完成了");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(
                resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
                resolveSizeAndState(maxHeight, heightMeasureSpec, 0));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        innerView.layout(10, 10, innerView.getMeasuredWidth(), innerView.getMeasuredHeight());
        borderView.layout(0, 0, borderView.getMeasuredWidth(), borderView.getMeasuredHeight());
    }

    private class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return false;
        }
    }
}
