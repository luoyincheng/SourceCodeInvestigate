package yincheng.sourcecodeinvestigate.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import yincheng.sourcecodeinvestigate.R;

/**
 * Created by yincheng on 2018/4/25/18:29.
 * github:luoyincheng
 */
public class LruCacheActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regex);
        imageView = findViewById(R.id.iv_cache);
    }
}
