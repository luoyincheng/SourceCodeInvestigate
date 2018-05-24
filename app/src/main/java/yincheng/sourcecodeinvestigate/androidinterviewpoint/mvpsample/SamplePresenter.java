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

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import yincheng.sourcecodeinvestigate.androidinterviewpoint.mvp.TiPresenter;
import yincheng.sourcecodeinvestigate.androidinterviewpoint.mvpsample
      .rx2.RxTiPresenterDisposableHandler;
import yincheng.sourcecodeinvestigate.androidinterviewpoint.mvpsample.rx2.RxTiPresenterUtils;


public class SamplePresenter extends TiPresenter<SampleView> {

    private RxTiPresenterDisposableHandler mDisposableHandler
            = new RxTiPresenterDisposableHandler(this);

    @Override
    protected void onCreate() {
        super.onCreate();

        mDisposableHandler.manageDisposable(Observable.interval(0, 37, TimeUnit.MILLISECONDS)
                .compose(RxTiPresenterUtils.deliverLatestToView(this))
                .subscribe(alive -> {
                    // deliverLatestToView makes getView() here @NonNull
                    getView().showText("I'm a fragment and alive for " + (alive * 37) + "ms");
                }));
    }
}
