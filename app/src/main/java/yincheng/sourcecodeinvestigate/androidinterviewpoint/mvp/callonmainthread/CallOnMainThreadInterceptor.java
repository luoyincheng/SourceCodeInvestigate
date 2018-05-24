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

package yincheng.sourcecodeinvestigate.androidinterviewpoint.mvp.callonmainthread;

import java.lang.reflect.Proxy;

import yincheng.sourcecodeinvestigate.androidinterviewpoint.mvp.BindViewInterceptor;
import yincheng.sourcecodeinvestigate.androidinterviewpoint.mvp.TiLog;
import yincheng.sourcecodeinvestigate.androidinterviewpoint.mvp.TiView;

import static yincheng.sourcecodeinvestigate.androidinterviewpoint.mvp.util.AnnotationUtil
      .getInterfaceOfClassExtendingGivenInterface;
import static yincheng.sourcecodeinvestigate.androidinterviewpoint.mvp.util.AnnotationUtil
      .hasObjectMethodWithAnnotation;

public class CallOnMainThreadInterceptor implements BindViewInterceptor {

    private static final String TAG = CallOnMainThreadInterceptor.class.getSimpleName();

    @Override
    public <V extends TiView> V intercept(final V view) {
        final V wrapped = wrap(view);
        TiLog.v(TAG, "wrapping View " + view + " in " + wrapped);
        return wrapped;
    }

    @SuppressWarnings("unchecked")
    private <V extends TiView> V wrap(final V view) {

        Class<?> foundInterfaceClass = getInterfaceOfClassExtendingGivenInterface(
                view.getClass(), TiView.class);
        if (foundInterfaceClass == null) {
            throw new IllegalStateException("the interface extending View could not be found");
        }

        if (!hasObjectMethodWithAnnotation(view, CallOnMainThread.class)) {
            // not method has the annotation, returning original view
            // not creating a proxy
            return view;
        }

        return (V) Proxy.newProxyInstance(
                foundInterfaceClass.getClassLoader(), new Class<?>[]{foundInterfaceClass},
                new CallOnMainThreadInvocationHandler<>(view));
    }
}
