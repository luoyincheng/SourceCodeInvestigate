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

package yincheng.sourcecodeinvestigate.androidinterviewpoint.mvpsample.fragmentlifecycle;


import yincheng.sourcecodeinvestigate.androidinterviewpoint.mvp.TiConfiguration;
import yincheng.sourcecodeinvestigate.androidinterviewpoint.mvp.TiPresenter;
import yincheng.sourcecodeinvestigate.androidinterviewpoint.mvp.TiView;

public class TestPresenter extends TiPresenter<TestPresenter.TestView> {

    public interface TestView extends TiView {

    }

    private final String mName;

    public TestPresenter(final String name) {
        mName = name;
    }

    public TestPresenter(final TiConfiguration config, final String name) {
        super(config);

        mName = name;
    }

    @Override
    public String toString() {
        return mName + "#" + super.toString();
    }
}
