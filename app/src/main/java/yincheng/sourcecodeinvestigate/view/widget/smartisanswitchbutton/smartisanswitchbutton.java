package yincheng.sourcecodeinvestigate.view.widget.smartisanswitchbutton;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
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
   private GestureDetectorCompat moveDetector;
   private int mTouchSlop = 5;
   private Point downPoint = new Point();


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
      // TODO: 2018/4/29 参数意义
      mDragHelper = ViewDragHelper.create(this, 10f, new DragHelperCallback());
      ViewConfiguration configuration = ViewConfiguration.get(getContext());
      mTouchSlop = configuration.getScaledTouchSlop();
      moveDetector = new GestureDetectorCompat(context,
            new MoveDetector());
      moveDetector.setIsLongpressEnabled(false);
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

   @Override
   public boolean onInterceptTouchEvent(MotionEvent ev) {
      boolean shouldIntercept = mDragHelper.shouldInterceptTouchEvent(ev);
      boolean moveFlag = moveDetector.onTouchEvent(ev);
      int action = ev.getActionMasked();
      if (action == MotionEvent.ACTION_DOWN) {
         // ACTION_DOWN的时候就对view重新排序
         if (mDragHelper.getViewDragState() == ViewDragHelper.STATE_SETTLING) {
            mDragHelper.abort();
         }

         // 保存初次按下时arrowFlagView的Y坐标
         // action_down时就让mDragHelper开始工作，否则有时候导致异常
         mDragHelper.processTouchEvent(ev);
      }

      return shouldIntercept && moveFlag;
   }

   @Override
   public boolean dispatchTouchEvent(MotionEvent ev) {
      if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
         this.downPoint.x = (int) ev.getX();
         this.downPoint.y = (int) ev.getY();
      }
      return super.dispatchTouchEvent(ev);
   }

   @Override
   public boolean onTouchEvent(MotionEvent event) {
      try {
         // 统一交给mDragHelper处理，由DragHelperCallback实现拖动效果
         // 该行代码可能会抛异常，正式发布时请将这行代码加上try catch
         mDragHelper.processTouchEvent(event);
      } catch (Exception ex) {
         ex.printStackTrace();
      }
      return true;
   }

   private class DragHelperCallback extends ViewDragHelper.Callback {

      @Override
      public void onViewPositionChanged(View changedView, int left, int top,
                                        int dx, int dy) {
//         onViewPosChanged((CardItemView) changedView);
      }

      @Override
      public boolean tryCaptureView(View child, int pointerId) {

//         if (child.getVisibility() != View.VISIBLE || child.getScaleX() <= 1.0f - SCALE_STEP) {
            // 一般来讲，如果拖动的是第三层、或者第四层的View，则直接禁止
            // 此处用getScale的用法来巧妙回避
//            return false;
//         }

//         if (btnLock) {
//            return false;
//         }

         // 1. 只有顶部的View才允许滑动
//         int childIndex = viewList.indexOf(child);
//         if (childIndex > 0) {
//            return false;
//         }

         // 2. 获取可滑动区域
//         ((CardItemView) child).onStartDragging();
//         if (draggableArea == null) {
//            draggableArea = adapter.obtainDraggableArea(child);
//         }


         // 3. 判断是否可滑动
         boolean shouldCapture = true;
//         if (null != draggableArea) {
//            shouldCapture = draggableArea.contains(downPoint.x, downPoint.y);
//         }

         // 4. 如果确定要滑动，就让touch事件交给自己消费
         if (shouldCapture) {
            getParent().requestDisallowInterceptTouchEvent(shouldCapture);
         }
         return shouldCapture;
      }

      @Override
      public int getViewHorizontalDragRange(View child) {
         // 这个用来控制拖拽过程中松手后，自动滑行的速度
         return 256;
      }

      @Override
      public void onViewReleased(View releasedChild, float xvel, float yvel) {
//         animToSide((CardItemView) releasedChild, (int) xvel, (int) yvel);
      }

      @Override
      public int clampViewPositionHorizontal(View child, int left, int dx) {
         return left;
      }

      @Override
      public int clampViewPositionVertical(View child, int top, int dy) {
         return top;
      }
   }

   class MoveDetector extends GestureDetector.SimpleOnGestureListener {

      @Override
      public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx,
                              float dy) {
         Log.e("wodeshijie", "onScroll()");
         // 拖动了，touch不往下传递
         return Math.abs(dy) + Math.abs(dx) > mTouchSlop;
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
