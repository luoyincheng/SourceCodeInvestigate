/*
 * Copyright (C) 2017 grandcentrix GmbH
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package yincheng.sourcecodeinvestigate.androidinterviewpoint.mvpsample;

import android.support.annotation.NonNull;

import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import yincheng.sourcecodeinvestigate.androidinterviewpoint.mvp.TiPresenter;
import yincheng.sourcecodeinvestigate.androidinterviewpoint.mvpsample
      .rx2.RxTiPresenterDisposableHandler;
import yincheng.sourcecodeinvestigate.androidinterviewpoint.mvpsample.rx2.RxTiPresenterUtils;

public class HelloWorldPresenter extends TiPresenter<HelloWorldView> {

    private int mCounter = 0;

    private BehaviorSubject<String> mText = BehaviorSubject.create();

    private RxTiPresenterDisposableHandler rxDisposableHelper
            = new RxTiPresenterDisposableHandler(this);

    private PublishSubject<Void> triggerHeavyCalculation = PublishSubject.create();

    @Override
    protected void onCreate() {
        super.onCreate();

        mText.onNext("Hello World!");

        rxDisposableHelper.manageDisposable(Observable.interval(0, 1, TimeUnit.SECONDS)
                .compose(RxTiPresenterUtils.deliverLatestToView(this))
                .subscribe(uptime -> {
                    sendToView(view -> view.showPresenterUpTime(uptime));
                }));

//        rxDisposableHelper.manageDisposable(triggerHeavyCalculation
//                .onBackpressureDrop(aVoid -> mText.onNext("Don't hurry me!"))
//                .doOnNext(aVoid -> mText.onNext("calculating next number..."))
//                .flatMap(new Func1<Void, Observable<Integer>>() {
//                    @Override
//                    public Observable<Integer> call(final Void aVoid) {
//                        return increaseCounter();
//                    }
//                }, 1)
//                .doOnNext(integer -> mText.onNext("Count: " + mCounter))
//                .subscribe());
    }

    @Override
    protected void onAttachView(@NonNull final HelloWorldView view) {
        super.onAttachView(view);

//        final Subscription showTextSub = mText.().subscribe(view::showText);
//        final Subscription onButtonClickSub = view.onButtonClicked()
//                .subscribe(aVoid -> {
//                    triggerHeavyCalculation.onNext(null);
//                });
//
//        rxDisposableHelper.manageViewDisposable(showTextSub, onButtonClickSub);
    }

    /**
     * fake a heavy calculation
     */
    private Observable<Integer> increaseCounter() {
        return Observable.just(mCounter)
                .subscribeOn(Schedulers.computation())
                // fake heavy calculation
                .delay(2, TimeUnit.SECONDS)
                .doOnNext(integer -> {
                    mCounter++;
                    mText.onNext("value: " + mCounter);
                });
    }
}
