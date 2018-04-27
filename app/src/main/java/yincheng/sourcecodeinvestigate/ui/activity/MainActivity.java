package yincheng.sourcecodeinvestigate.ui.activity;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import yincheng.sourcecodeinvestigate.R;
import yincheng.sourcecodeinvestigate.view.InflateTestFramelayout;
import yincheng.sourcecodeinvestigate.view.MainRowView;
import yincheng.sourcecodeinvestigate.view.ReboundFramelayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView lv_main;
    private static List<ItemHolder> itemHolders = new ArrayList<>();
    private mainAdapter mainAdapter;
    private FrameLayout fl_maincontainer;

    //// TODO: 2018/4/27 为什么static
    static {
        itemHolders.add(new ItemHolder(ReboundFramelayout.class, "我的世界", "开始下雪"));
        itemHolders.add(new ItemHolder(InflateTestFramelayout.class, "LayoutInflater.from(Context)", "inflate()方法中三个参数的意义"));
        itemHolders.add(new ItemHolder(ReboundFramelayout.class, "我的世界", "开始下雪"));
    }

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
        fl_maincontainer = findViewById(R.id.fl_maincontainer);
        mainAdapter = new mainAdapter();
        lv_main = findViewById(R.id.lv_main);
        lv_main.setAdapter(mainAdapter);
        lv_main.setOnItemClickListener(this);
    }


    @Override
    public void onClick(View v) {
//        if (v instanceof TextView) {
//            try {
//                startActivity(new Intent(this, Class.forName("yincheng.sourcecodeinvestigate.ui.activity." + ((TextView) v).getText().toString() + "Activity")));
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("wodeshijie", "onItemClick()");
        Class<? extends View> clazz = itemHolders.get(position).viewClass;
        View constructedView = null;
        try {
            Constructor<? extends View> constructor = clazz.getConstructor(Context.class);
            //由构造函数来生成class(刚好这个class是个继承自Framelayout的View)
            constructedView = constructor.newInstance(MainActivity.this);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (constructedView == null) return;
        fl_maincontainer.addView(constructedView);
    }

    private class mainAdapter implements ListAdapter {

        @Override
        public boolean areAllItemsEnabled() {
            return true;
        }

        @Override
        public boolean isEnabled(int position) {
            return true;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getCount() {
            return itemHolders.size();
        }

        @Override
        public Object getItem(int position) {
            return itemHolders.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MainRowView mainRowView;
            if (convertView != null)
                mainRowView = (MainRowView) convertView;
            else
                mainRowView = new MainRowView(MainActivity.this);
            mainRowView.setTitleText(itemHolders.get(position).title);
            mainRowView.setContentText(itemHolders.get(position).content);
            return mainRowView;
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return itemHolders.isEmpty();
        }
    }

    private static class ItemHolder {
        public Class<? extends View> viewClass;
        public String title;
        public String content;

        public ItemHolder(Class<? extends View> viewClass, String title, String content) {
            this.viewClass = viewClass;
            this.title = title;
            this.content = content;
        }
    }
}
