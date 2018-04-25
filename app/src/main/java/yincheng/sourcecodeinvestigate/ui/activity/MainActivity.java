package yincheng.sourcecodeinvestigate.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import yincheng.sourcecodeinvestigate.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv_lrucache = findViewById(R.id.tv_lrucache);
        tv_lrucache.setOnClickListener(this);
        TextView tv_timer = findViewById(R.id.tv_timer);
        tv_timer.setOnClickListener(this);
        TextView tv_rxjava = findViewById(R.id.tv_rxjava);
        tv_rxjava.setOnClickListener(this);
        TextView tv_threadpool = findViewById(R.id.tv_threadpool);
        tv_threadpool.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v instanceof TextView) {
            try {
                startActivity(new Intent(this, Class.forName("yincheng.sourcecodeinvestigate.ui.activity." + ((TextView) v).getText().toString() + "Activity")));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
