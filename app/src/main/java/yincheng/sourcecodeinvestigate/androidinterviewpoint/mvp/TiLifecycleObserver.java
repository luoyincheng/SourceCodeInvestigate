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

package yincheng.sourcecodeinvestigate.androidinterviewpoint.mvp;

/**
 * Can be added to a {@link TiPresenter} with {@link TiPresenter#addLifecycleObserver(TiLifecycleObserver)}
 * to get notifications when the lifecycle changes
 */
public interface TiLifecycleObserver {

    /**
     * gets called when the {@link TiPresenter.State} changes
     *
     * @param state                        the new state of the {@link TiPresenter}
     * @param hasLifecycleMethodBeenCalled {@code false} when called before the {@code on...}
     *                                     lifecycle methods, {@code true} when called after
     * @see TiPresenter#onCreate()
     * @see TiPresenter#onAttachView(TiView)
     * @see TiPresenter#onDetachView()
     * @see TiPresenter#onDestroy()
     */
    void onChange(final TiPresenter.State state, final boolean hasLifecycleMethodBeenCalled);
}
