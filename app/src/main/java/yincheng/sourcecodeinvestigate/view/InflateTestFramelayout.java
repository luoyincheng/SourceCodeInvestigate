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
     * 1.第二个参数如果设置为null，表示当前将要被填充的childview不受接收它的parentview的布局约束，因此childview的根布局的layout_width,layout_height属性将不起作用，此时第三个参数无论是true或者false都不会自动添加该childview到parentview中去，必须调用addView方法来手动添加
     * 2.第二个参数如果不设置为null，表示当前要被填充的childview将要受到parentview的布局约束，因此childview的根布局的layout_width,layout_height属性会起作用，此时第三个参数如果为true，那么childview将被自动添加到parentview中去，不能(不是不用)再次调用addView方法来手动添加，否则会异常，如果第三个参数为false，那就只有手动调用了addView()方法以后才会把childview添加到parentview中去
     */
    public InflateTestFramelayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        mRootView = (ViewGroup) layoutInflater.inflate(R.layout.framelayout_inflatetest, null, false);//设置为null，为了全屏
        addView(mRootView);//在构造函数里面添加子View
    }
}
