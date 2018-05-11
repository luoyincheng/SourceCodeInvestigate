package yincheng.sourcecodeinvestigate.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import yincheng.sourcecodeinvestigate.R;
import yincheng.sourcecodeinvestigate.rx2androidnetworking.Rx2AndroidNetworking;
import yincheng.sourcecodeinvestigate.rx2androidnetworking.api.ApiUser;
import yincheng.sourcecodeinvestigate.rx2androidnetworking.dao.User;
import yincheng.sourcecodeinvestigate.rx2androidnetworking.dao.UserDetail;

/**
 * Created by yincheng on 2018/4/25/18:42.
 * github:luoyincheng
 */
public class Rxjava2Activity extends AppCompatActivity {
    private static final String TAG = Rxjava2Activity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava2);
        doSomeWork();
        Log.e("wodeshijie", TAG);
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

    //********************************************************************************************
    public void mapNetworking(View view) {
        Rx2AndroidNetworking
                .get("https://fierce-cove-29863.herokuapp.com/getAnUser/{userId}")
                .addPathParameter("userId", "1")
                .build()
                .getObjectObservable(ApiUser.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ApiUser, User>() {
                    @Override
                    public User apply(ApiUser apiUser) throws Exception {
                        //apiUser是从服务器获取到的，在这里将它转化为User类
                        return new User(apiUser);
                    }
                })
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "mapNetworking:onSubscribe()" + d.toString());
                    }

                    @Override
                    public void onNext(User user) {
                        Log.e(TAG, "mapNetworking:onNext()" + user.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "mapNetworking:onError()" + e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "mapNetworking:onComplete()");

                    }
                });
    }

    //********************************************************************************************

    public void zipNetworking(View view) {
        findUsersWhoLovesBoth();
    }

    /**
     * 获取喜欢cricket的用户列表，Observable要发送的数据：list<User>
     */
    private Observable<List<User>> getCricketFansObservable() {
        return Rx2AndroidNetworking
                .get("https://fierce-cove-29863.herokuapp.com/getAllCricketFans")
                .build()
                .getObjectListObservable(User.class);
    }

    /**
     * 获取喜欢football的用户列表，Observable要发送的数据：list<User>
     */
    private Observable<List<User>> getFootballFansObservable() {
        return Rx2AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAllFootballFans")
                .build()
                .getObjectListObservable(User.class);
    }

    private void findUsersWhoLovesBoth() {
        Observable
                .zip(getCricketFansObservable(), getFootballFansObservable(), new BiFunction<List<User>, List<User>, List<User>>() {
                    @Override
                    public List<User> apply(List<User> users, List<User> users2) throws Exception {
                        return filterUserWhoLovesBoth(users, users2);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "zipNetworking:onNext()" + d.toString());
                    }

                    @Override
                    public void onNext(List<User> users) {
                        for (User user : users) {
                            Log.e(TAG, "zipNetworking:onNext()" + user.toString());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "zipNetworking:onNext()" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "zipNetworking:onNext()");
                    }
                });

    }

    private List<User> filterUserWhoLovesBoth(List<User> cricketFans, List<User> footballFans) {
        List<User> usersWhoLoveBoth = new ArrayList<>();
        for (User footballFan : footballFans) {
            if (cricketFans.contains(footballFan))
                usersWhoLoveBoth.add(footballFan);
        }
        return usersWhoLoveBoth;
    }

    //********************************************************************************************

    public void takeNetworking(View view) {//限制发送的数量
        getUserListObservable()
                .flatMap(new Function<List<User>, ObservableSource<User>>() {//flatMap:to return users one by one
                    @Override
                    public ObservableSource<User> apply(List<User> userList) throws Exception {
                        return Observable.fromIterable(userList);//return user one by one from userList
                    }
                })
                .take(4)//只发送所有userList中的前四个,take用来限制发送数据的数量
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "takeNetworking:onSubscribe()" + d.toString());
                    }

                    @Override
                    public void onNext(User user) {
                        //只会收到四个User，一个接一个的
                        Log.e(TAG, "takeNetworking:onNext()" + user.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "takeNetworking:onError()" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "takeNetworking:onComplete()");
                    }
                });

    }

    //********************************************************************************************
    public void flatmapNetworking(View view) {
        getUserListObservable()
                .flatMap(new Function<List<User>, ObservableSource<User>>() {//to return users one by one
                    @Override
                    public ObservableSource<User> apply(List<User> userList) throws Exception {
                        return Observable.fromIterable(userList);//returning user one by one from userList
                    }
                })
                .flatMap(new Function<User, ObservableSource<UserDetail>>() {
                    @Override
                    public ObservableSource<UserDetail> apply(User user) throws Exception {
                        //这里得到了一个又一个的User，通过该User的id来获取User的更加详细的信息
                        return getUserDetailObservable(user.id);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserDetail>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "flatMapNetworking:onSubscribe()" + d.toString());
                    }

                    @Override
                    public void onNext(UserDetail userDetail) {
                        Log.e(TAG, "flatMapNetworking:onNext()" + userDetail.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "flatMapNetworking:onError()" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "flatMapNetworking:onComplete()");
                    }
                });
    }

    //*********************************************************************************************
    public void flatmapwithzipNetworking(View view) {
        getUserListObservable()
                .flatMap(new Function<List<User>, ObservableSource<User>>() {
                    @Override
                    public ObservableSource<User> apply(List<User> users) throws Exception {
                        return Observable.fromIterable(users);
                    }
                })
                .flatMap(new Function<User, ObservableSource<Pair<UserDetail, User>>>() {
                    @Override
                    public ObservableSource<Pair<UserDetail, User>> apply(User user) throws Exception {
                        return Observable.zip(getUserDetailObservable(user.id),
                                Observable.just(user),
                                new BiFunction<UserDetail, User, Pair<UserDetail, User>>() {
                                    @Override
                                    public Pair<UserDetail, User> apply(UserDetail userDetail, User user) throws Exception {
                                        return new Pair<>(userDetail, user);
                                    }
                                });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Pair<UserDetail, User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "flatmapwithzipNetworking.onSubscribe()" + d.toString());
                    }

                    @Override
                    public void onNext(Pair<UserDetail, User> userDetailUserPair) {
                        Log.e(TAG, "flatmapwithzipNetworking.onNext()" + userDetailUserPair.toString());

                        UserDetail userDetail = userDetailUserPair.first;
                        User user = userDetailUserPair.second;
                        Log.e(TAG,"user::" + user.toString());
                        Log.e(TAG,"userDetail::" + userDetail.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "flatmapwithzipNetworking.onError()" + e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "flatmapwithzipNetworking.onComplete()");

                    }
                });
    }

    //**************************************common*************************************************
    //*********************************************************************************************
    private Observable<List<User>> getUserListObservable() {
        return Rx2AndroidNetworking
                .get("https://fierce-cove-29863.herokuapp.com/getAllUsers/{pageNumber}")
                .addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "10")
                .build()
                .getObjectListObservable(User.class);
    }

    private Observable<UserDetail> getUserDetailObservable(long id) {
        return Rx2AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAnUserDetail/{userId}")
                .addPathParameter("userId", String.valueOf(id))
                .build()
                .getObjectObservable(UserDetail.class);
    }

}
