package yincheng.sourcecodeinvestigate.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import yincheng.sourcecodeinvestigate.R;

/**
 * Created by yincheng on 2018/4/27/16:23.
 * github:luoyincheng
 */
public class InflateTestFramelayout extends FrameLayout {
    private ViewGroup mRootView;

    public InflateTestFramelayout(@NonNull Context context) {
        this(context, null);
    }

    public InflateTestFramelayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * layoutInflater.inflate方法的说明
     * 1.第二个参数控制childview的根view的layout_width,layout_height属性在该view被添加到parentview中以后是否依然有效
     * 2.第三个参数用来控制是否自动添加childview到parentview中，如果第二个参数不为null就不能手动添加了，如果第二个参数为null，那么第三个参数将失效，true或者false会有一样的效果，再次用addView()方法来添加也不会报异常
     * 1.第二个参数如果设置为null，表示当前将要被填充的childview不受接收它的parentview的布局约束，因此childview的根布局的layout_width,layout_height属性将不起作用，此时第三个参数无论是true或者false都不会自动添加该childview到parentview中去，必须调用addView方法来手动添加
     * 2.第二个参数如果不设置为null，表示当前要被填充的childview将要受到parentview的布局约束，因此childview的根布局的layout_width,layout_height属性会起作用，此时第三个参数如果为true，那么childview将被自动添加到parentview中去，不能(不是不用)再次调用addView方法来手动添加，否则会异常，如果第三个参数为false，那就只有手动调用了addView()方法以后才会把childview添加到parentview中去
     * 3.虽然第二个参数如果为null就会让childview的第三个参数失去作用，也会让其自身的根view的layout_width,layout_height属性失效,但是却可以通过addView中方法中的第二个参数来设置layout_width,layout_height属性
     */
    public InflateTestFramelayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        mRootView = (ViewGroup) layoutInflater.inflate(R.layout.framelayout_inflatetest, null, true);
        addView(mRootView);
//        addView(mRootView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));//在构造函数里面添加子View
//        addView(mRootView,500,200);
    }
}
