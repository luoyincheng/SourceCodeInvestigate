/*
 *    Copyright (C) 2016 Amit Shekhar
 *    Copyright (C) 2011 Android Open Source Project
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package yincheng.sourcecodeinvestigate.rx2androidnetworking.networking.internal;


import okhttp3.Response;
import yincheng.sourcecodeinvestigate.rx2androidnetworking.networking.common.ANRequest;
import yincheng.sourcecodeinvestigate.rx2androidnetworking.networking.common.ANResponse;
import yincheng.sourcecodeinvestigate.rx2androidnetworking.networking.common.Priority;
import yincheng.sourcecodeinvestigate.rx2androidnetworking.networking.common.ResponseType;
import yincheng.sourcecodeinvestigate.rx2androidnetworking.networking.core.Core;
import yincheng.sourcecodeinvestigate.rx2androidnetworking.networking.error.ANError;
import yincheng.sourcecodeinvestigate.rx2androidnetworking.networking.utils.SourceCloseUtil;
import yincheng.sourcecodeinvestigate.rx2androidnetworking.networking.utils.Utils;

import static yincheng.sourcecodeinvestigate.rx2androidnetworking.networking.common.RequestType.DOWNLOAD;
import static yincheng.sourcecodeinvestigate.rx2androidnetworking.networking.common.RequestType.MULTIPART;
import static yincheng.sourcecodeinvestigate.rx2androidnetworking.networking.common.RequestType.SIMPLE;

/**
 * Created by amitshekhar on 22/03/16.
 */
public class InternalRunnable implements Runnable {

    private final Priority priority;
    public final int sequence;
    public final ANRequest request;

    public InternalRunnable(ANRequest request) {
        this.request = request;
        this.sequence = request.getSequenceNumber();
        this.priority = request.getPriority();
    }

    @Override
    public void run() {
        request.setRunning(true);
        switch (request.getRequestType()) {
            case SIMPLE:
                executeSimpleRequest();
                break;
            case DOWNLOAD:
                executeDownloadRequest();
                break;
            case MULTIPART:
                executeUploadRequest();
                break;
        }
        request.setRunning(false);
    }

    private void executeSimpleRequest() {
        Response okHttpResponse = null;
        try {
            okHttpResponse = InternalNetworking.performSimpleRequest(request);

            if (okHttpResponse == null) {
                deliverError(request, Utils.getErrorForConnection(new ANError()));
                return;
            }

            if (request.getResponseAs() == ResponseType.OK_HTTP_RESPONSE) {
                request.deliverOkHttpResponse(okHttpResponse);
                return;
            }
            if (okHttpResponse.code() >= 400) {
                deliverError(request, Utils.getErrorForServerResponse(new ANError(okHttpResponse),
                        request, okHttpResponse.code()));
                return;
            }

            ANResponse response = request.parseResponse(okHttpResponse);
            if (!response.isSuccess()) {
                deliverError(request, response.getError());
                return;
            }
            response.setOkHttpResponse(okHttpResponse);
            request.deliverResponse(response);
        } catch (Exception e) {
            deliverError(request, Utils.getErrorForConnection(new ANError(e)));
        } finally {
            SourceCloseUtil.close(okHttpResponse, request);
        }
    }

    private void executeDownloadRequest() {
        Response okHttpResponse;
        try {
            okHttpResponse = InternalNetworking.performDownloadRequest(request);
            if (okHttpResponse == null) {
                deliverError(request, Utils.getErrorForConnection(new ANError()));
                return;
            }
            if (okHttpResponse.code() >= 400) {
                deliverError(request, Utils.getErrorForServerResponse(new ANError(okHttpResponse),
                        request, okHttpResponse.code()));
                return;
            }
            request.updateDownloadCompletion();
        } catch (Exception e) {
            deliverError(request, Utils.getErrorForConnection(new ANError(e)));
        }
    }

    private void executeUploadRequest() {
        Response okHttpResponse = null;
        try {
            okHttpResponse = InternalNetworking.performUploadRequest(request);

            if (okHttpResponse == null) {
                deliverError(request, Utils.getErrorForConnection(new ANError()));
                return;
            }

            if (request.getResponseAs() == ResponseType.OK_HTTP_RESPONSE) {
                request.deliverOkHttpResponse(okHttpResponse);
                return;
            }

            if (okHttpResponse.code() >= 400) {
                deliverError(request, Utils.getErrorForServerResponse(new ANError(okHttpResponse),
                        request, okHttpResponse.code()));
                return;
            }
            ANResponse response = request.parseResponse(okHttpResponse);
            if (!response.isSuccess()) {
                deliverError(request, response.getError());
                return;
            }
            response.setOkHttpResponse(okHttpResponse);
            request.deliverResponse(response);
        } catch (Exception e) {
            deliverError(request, Utils.getErrorForConnection(new ANError(e)));
        } finally {
            SourceCloseUtil.close(okHttpResponse, request);
        }
    }

    public Priority getPriority() {
        return priority;
    }

    private void deliverError(final ANRequest request, final ANError anError) {
        Core.getInstance().getExecutorSupplier().forMainThreadTasks().execute(new Runnable() {
            public void run() {
                request.deliverError(anError);
                request.finish();
            }
        });
    }
}
