package yincheng.sourcecodeinvestigate.view.widget.smartisanswitchbutton;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import yincheng.sourcecodeinvestigate.R;
import yincheng.sourcecodeinvestigate.view.BaseView;

/**
 * Created by yincheng on 2018/4/28/18:40.
 * github:luoyincheng
 */
public class smartisanswitchbutton extends ViewGroup {
   private SwitchInnerView innerView;
   private SwitchBorderView borderView;
   private int borderWidth = 50;
   private int circleRadius;
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
   }

   @Override
   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      measureChildren(widthMeasureSpec, heightMeasureSpec);
      int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
      int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
      setMeasuredDimension(
            resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
            resolveSizeAndState(maxHeight, heightMeasureSpec, 0));
      circleRadius = getMeasuredHeight() / 6;
   }

   @Override
   protected void onLayout(boolean changed, int l, int t, int r, int b) {
      innerView.layout(0, 0, innerView.getMeasuredWidth(), innerView.getMeasuredHeight());
      borderView.layout(0, 0, borderView.getMeasuredWidth(), borderView.getMeasuredHeight());
   }

   private class DragHelperCallback extends ViewDragHelper.Callback {

      @Override
      public boolean tryCaptureView(@NonNull View child, int pointerId) {
         return false;
      }
   }

   /**
    * ========================================================================================
    */
   class SwitchInnerView extends BaseView {
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

      public void initBase() {
         mBaseFillPaint.setColor(getResources().getColor(R.color.gray));
      }

      @Override
      protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
         int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
         int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
         // TODO: 2018/4/28  resolveSizeAndState()是
         setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
               resolveSizeAndState(maxHeight, heightMeasureSpec, 0));
         rectFLeft = new RectF(borderWidth, borderWidth, getMeasuredHeight() - borderWidth, getMeasuredHeight() - borderWidth);
         rectFRight = new RectF(getMeasuredWidth() - getMeasuredHeight() + borderWidth, borderWidth, getMeasuredWidth() - borderWidth, getMeasuredHeight() - borderWidth);
      }

      @Override
      protected void onDraw(Canvas canvas) {
         super.onDraw(canvas);
         drawInnerBackground(canvas);
         drawCircle(canvas);
      }

      private void drawInnerBackground(Canvas canvas) {
         mBasePath.arcTo(rectFLeft, 90, 180);
         mBasePath.lineTo(getMeasuredWidth() - getMeasuredHeight(), borderWidth);
         mBasePath.arcTo(rectFRight, -90, 180);
         mBasePath.close();
         canvas.drawPath(mBasePath, mBaseFillPaint);
      }

      private void drawCircle(Canvas canvas) {
         mBaseFillPaint.setColor(getResources().getColor(R.color.lightergray));
         canvas.drawCircle(getMeasuredHeight() / 2, getMeasuredHeight() / 2, circleRadius, mBaseFillPaint);
      }
   }

   /**
    * ========================================================================================
    */
   class SwitchBorderView extends BaseView {
      private RectF rectFLeft, rectFRight;

      public SwitchBorderView(Context context) {
         this(context, null);
      }

      public SwitchBorderView(Context context, @Nullable AttributeSet attrs) {
         this(context, attrs, 0);
      }

      public SwitchBorderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
         super(context, attrs, defStyleAttr);
         initBase();
      }

      public void initBase() {
         setLayerType(LAYER_TYPE_SOFTWARE, null);
         mBaseStrokePaint.setColor(getResources().getColor(R.color.colorBlack));
         mBaseStrokePaint.setStrokeWidth(borderWidth);
         mBaseStrokePaint.setMaskFilter(new BlurMaskFilter(6, BlurMaskFilter.Blur.INNER));
      }

      @Override
      protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
         int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
         int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
         setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
               resolveSizeAndState(maxHeight, heightMeasureSpec, 0));
         rectFLeft = new RectF(borderWidth / 2, borderWidth / 2, getMeasuredHeight() - borderWidth / 2, getMeasuredHeight() - borderWidth / 2);
         rectFRight = new RectF(getMeasuredWidth() - getMeasuredHeight() + borderWidth / 2, borderWidth / 2, getMeasuredWidth() - borderWidth / 2, getMeasuredHeight() - borderWidth / 2);

      }

      @Override
      protected void onDraw(Canvas canvas) {
         super.onDraw(canvas);
         drawBorder(canvas);
      }

      private void drawBorder(Canvas canvas) {
         mBasePath.arcTo(rectFLeft, 90, 180);
         mBasePath.lineTo(getMeasuredWidth() - getMeasuredHeight(), borderWidth / 2);
         mBasePath.arcTo(rectFRight, -90, 180);
         mBasePath.close();
         canvas.drawPath(mBasePath, mBaseStrokePaint);
      }

   }
}
