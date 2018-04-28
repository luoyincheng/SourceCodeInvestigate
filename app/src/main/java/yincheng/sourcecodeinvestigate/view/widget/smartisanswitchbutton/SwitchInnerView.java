package yincheng.sourcecodeinvestigate.view.widget.smartisanswitchbutton;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import yincheng.sourcecodeinvestigate.R;
import yincheng.sourcecodeinvestigate.view.BaseView;

/**
 * Created by yincheng on 2018/4/28/18:41.
 * github:luoyincheng
 */
public class SwitchInnerView extends BaseView {
    public SwitchInnerView(Context context) {
        this(context, null);
    }

    public SwitchInnerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchInnerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBase();
    }

    private RectF rectFLeft, rectFRight;
    private float measuredHeight = 200f;
    private float innerShadowWidth = 5;
    private float innerViewRealWidth;

    public void initBase() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mBaseStrokePaint.setColor(getResources().getColor(R.color.colorBlack));
        mBaseStrokePaint.setStrokeWidth(20);
        mBaseStrokePaint.setMaskFilter(new BlurMaskFilter(6, BlurMaskFilter.Blur.INNER));
        rectFLeft = new RectF(2, 2, 900 - 2, 180 - 2);// TODO: 2018/4/26 为什么这里传入measuredHeight不行
        rectFRight = new RectF(900 + 2, 0 + 2, 900 - 2, 180 - 2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(900, 180);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBorder(canvas);
    }

    private void drawBorder(Canvas canvas) {
        mBasePath.arcTo(rectFLeft, 90, 180);
        mBasePath.lineTo(900, 0);
        mBasePath.arcTo(rectFRight, -90, 180);
        mBasePath.close();
        canvas.drawPath(mBasePath, mBaseStrokePaint);
    }
}
