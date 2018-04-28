package yincheng.sourcecodeinvestigate.ui;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import yincheng.sourcecodeinvestigate.view.stackview.StackCardView;

/**
 * Created by yincheng on 2018/4/27/16:13.
 * github:luoyincheng
 */
public class CardItemView extends FrameLayout {
    private ObjectAnimator alphaAnimator;// TODO: 2018/4/27 为什么objectAnimator
    private StackCardView parentView;

    public CardItemView(@NonNull Context context) {
        this(context, null);
    }

    public CardItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setParentView(StackCardView cardView) {
        this.parentView = cardView;
    }

    public void bindLayoutResId(int layoutResId) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        /**
         * 第二个参数为null，则需要将要被添加的childview的宽高交给addView去设置
         */
        View view = inflater.inflate(layoutResId, null);
        addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setVisibilieyWithAnimation(final int visibiliey, int delayIndex) {
        if (visibiliey == View.VISIBLE && getVisibility() != View.VISIBLE) {
            setAlpha(0);//完全透明
            setVisibility(VISIBLE);
            if (alphaAnimator != null) {
                alphaAnimator.cancel();
            }
            alphaAnimator = ObjectAnimator.ofFloat(this, "alpha", 0.0f, 1.0f);
            alphaAnimator.setDuration(2000);
            alphaAnimator.setStartDelay(delayIndex * 200);// TODO: 2018/4/27 to un
            alphaAnimator.start();
        }
    }
}
