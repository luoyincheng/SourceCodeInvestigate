package yincheng.sourcecodeinvestigate.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by yincheng on 2018/4/27/16:13.
 * github:luoyincheng
 */
public class CardItemView extends FrameLayout {
    public CardItemView(@NonNull Context context) {
        this(context, null);
    }

    public CardItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void bindLayoutResId(int layoutResId) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(layoutResId, null);// TODO: 2018/4/27 to un 为什么是null

    }

}
