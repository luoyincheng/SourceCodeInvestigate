package yincheng.sourcecodeinvestigate.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import yincheng.sourcecodeinvestigate.R;

/**
 * Created by yincheng on 2018/4/27/14:42.
 * github:luoyincheng
 */
public class MainRowView extends FrameLayout {
    private final TextView mTextTitle;
    private final TextView mTextContent;

    public MainRowView(@NonNull Context context) {
        this(context, null);
    }

    public MainRowView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainRowView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_main, this, false);
        mTextTitle = view.findViewById(R.id.text_view);
        mTextContent = view.findViewById(R.id.subtext_view);
        setBackgroundResource(R.drawable.background_item_main);
        addView(view);
    }

    public void setTitleText(String s) {
        mTextTitle.setText(s);
    }

    public void setContentText(String s) {
        mTextContent.setText(s);
    }

}
