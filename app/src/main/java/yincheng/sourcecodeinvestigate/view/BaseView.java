package yincheng.sourcecodeinvestigate.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yincheng on 2018/4/28/18:21.
 * github:luoyincheng
 */
public abstract class BaseView extends View {
    public Paint mBaseFillPaint;
    public Paint mBaseStrokePaint;
    public TextPaint mBaseTextPaint;
    public RectF mBaseBorderRectf;
    public Path mBasePath;

    private Path indicatorPath;
    private Paint mIndicatorPaint;
    private float indicatorWidth = 10;
    private float indicatorBorderLength = 50;

    public BaseView(Context context) {
        this(context, null);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBaseFillPaint = new Paint();
        mBaseFillPaint.setAntiAlias(true);
        mBaseFillPaint.setStyle(Paint.Style.FILL);

        mBaseStrokePaint = new Paint();
        mBaseStrokePaint.setAntiAlias(true);
        mBaseStrokePaint.setStyle(Paint.Style.STROKE);

        mBaseTextPaint = new TextPaint();
        mBaseTextPaint.setAntiAlias(true);

        mIndicatorPaint = new Paint();
        mIndicatorPaint.setAntiAlias(true);
        mIndicatorPaint.setColor(Color.parseColor("#ff4444"));
        mIndicatorPaint.setStyle(Paint.Style.FILL);

        indicatorPath = new Path();
        mBasePath = new Path();

        mBaseBorderRectf = new RectF();
    }


    public void drawIndicator(Canvas canvas) {
        indicatorPath.lineTo(indicatorBorderLength, 0.0f);
        indicatorPath.lineTo(indicatorBorderLength, indicatorWidth);
        indicatorPath.lineTo(indicatorWidth, indicatorWidth);
        indicatorPath.lineTo(indicatorWidth, indicatorBorderLength);
        indicatorPath.lineTo(0.0f, indicatorBorderLength);
        indicatorPath.close();
        canvas.drawPath(indicatorPath, mIndicatorPaint);
    }

}
