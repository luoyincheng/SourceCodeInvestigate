package yincheng.sourcecodeinvestigate.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import yincheng.sourcecodeinvestigate.R;

/**
 * Created by yincheng on 2018/4/25/18:42.
 * github:luoyincheng
 */
public class Rxjava2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava2);
        doSomeWork();
    }

    public void doSomeWork() {
        getObservable()
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notifyed on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver());
    }

    private Observable<String> getObservable() {
        return Observable.just("我", "的", "世", "界");
    }

    private Observer<String> getObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e("wodeshijie", "onSubscribe()");

            }

            @Override
            public void onNext(String s) {
                Log.e("wodeshijie", "onNext()::" + s);

            }

            @Override
            public void onError(Throwable e) {
                Log.e("wodeshijie", "onError()::" + e.getMessage());

            }

            @Override
            public void onComplete() {
                Log.e("wodeshijie", "onComplete()");
            }
        };
    }

}
