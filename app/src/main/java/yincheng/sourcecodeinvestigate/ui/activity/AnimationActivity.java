package yincheng.sourcecodeinvestigate.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.TextView;
import android.widget.Toast;

import yincheng.sourcecodeinvestigate.R;

/**
 * Created by yincheng on 2018/5/8/16:13.
 * github:luoyincheng
 */
public class AnimationActivity extends AppCompatActivity {
    private TextView tv_anim;
    private AnimationSet animationSet;
    private AnimatorSet animatorSet;

    /**
     * ofInt的作用：1.创建动画实例
     * 2.将传入的多个Int参数进行平滑过渡，这里传入0和3，表示值将从0匀速增长到3
     * 如果传入了三个Int参数a,b,c,表示先从a平滑过渡到b，再从b平滑过渡到c
     * ValutAnimator内置了整型估值器
     */
    private ValueAnimator valueAnimator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        tv_anim = findViewById(R.id.tv_anim);
        valueAnimator = ValueAnimator.ofInt(tv_anim.getLayoutParams().width, 1000);
        {
            valueAnimator.setDuration(3000);
            //ValueAnimator.RESTART表示正序播放
            //ValueAnimator.REVERSE表示倒序播放
            valueAnimator.setRepeatMode(ValueAnimator.RESTART);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.setStartDelay(0);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int currentValue = (int) animation.getAnimatedValue();
                    Log.e("wodeshijie", currentValue + "");
                    tv_anim.getLayoutParams().width = currentValue;
                    tv_anim.requestLayout();
                }
            });

        }



        findViewById(R.id.tv_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueAnimator.start();
            }
        });

        findViewById(R.id.tv_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void startAnim() {
        for (Animator animator : animatorSet.getChildAnimations()) {
            if (animator instanceof ValueAnimator) {// TODO: 2018/5/8 to un
                ((ValueAnimator) animator).setRepeatCount(10);
                ((ValueAnimator) animator).setRepeatMode(ValueAnimator.RESTART);

            }
        }
    }
}
