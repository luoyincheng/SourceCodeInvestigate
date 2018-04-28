package yincheng.sourcecodeinvestigate.view.stackview;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by yincheng on 2018/4/28/15:07.
 * github:luoyincheng
 */
public abstract class CardAdapter {

    // TODO: 2018/4/28 to un
    private final DataSetObservable mDataSetObservable = new DataSetObservable();

    public abstract int getItemLayoutId();

    public abstract int getCount();

    public abstract void bindView(View view, int index);

    public abstract Object getItemData(int intex);

    public RectF obtainDraggableArea(View view) {
        return null;
    }

    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }
}
