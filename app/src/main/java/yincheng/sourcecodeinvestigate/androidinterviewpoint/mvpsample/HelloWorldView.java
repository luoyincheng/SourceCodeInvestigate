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


import io.reactivex.Observable;
import yincheng.sourcecodeinvestigate.androidinterviewpoint.mvp.TiView;
import yincheng.sourcecodeinvestigate.androidinterviewpoint.mvp.callonmainthread.CallOnMainThread;
import yincheng.sourcecodeinvestigate.androidinterviewpoint.mvp.distinctuntilchanged
      .DistinctUntilChanged;

public interface HelloWorldView extends TiView {

    Observable<Void> onButtonClicked();

    void showPresenterUpTime(Long uptime);

    @CallOnMainThread
    @DistinctUntilChanged
    void showText(final String text);
}
