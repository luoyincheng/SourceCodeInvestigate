package yincheng.sourcecodeinvestigate.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import yincheng.sourcecodeinvestigate.R;

/**
 * Created by yincheng on 2018/4/27/12:32.
 * github:luoyincheng
 */
public class ReboundFramelayout extends FrameLayout {
    private final FrameLayout mRootView;
    private final View imageview;

    public ReboundFramelayout(@NonNull Context context) {
        this(context, null);
    }

    public ReboundFramelayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReboundFramelayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater layoutInflater = LayoutInflater.from(context);//有Context就有LayoutInflater
        /**
         * @param attachToRoot Whether the inflated hierarchy should be attached to
         * the root parameter? If false, root is only used to create the
         * correct subclass of LayoutParams for the root view in the XML.
         * TODO: 2018/4/27 第二个参数和第三个参数的意义
         */
        mRootView = (FrameLayout) layoutInflater.inflate(R.layout.framelayout_rebound, this, false);
        imageview = mRootView.findViewById(R.id.iv_show);
        addView(mRootView);//在构造函数里面添加子View
    }
}
