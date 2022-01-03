package com.study.android.webview;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.UiThread;

import org.json.JSONObject;

public abstract class JsBridgeBase {
    protected Context context;
    //bridge对象关联的WebView
    protected WebView webView;
    //js bridge方法会在WebView的线程中被调用，然后需要我们在主线程中进行回调。
    protected Handler handler = new Handler(Looper.getMainLooper());

    /**
     * 处理WebView JS 调用
     *
     * @param type
     * @param callback
     * @param jparam
     * @return
     */
    public abstract boolean handlerJs(int type, String callback, JSONObject jparam);

    protected JsBridgeBase() {
    }

    public void registerWebView(Context context, WebView webView) {
        this.context = context;
        this.webView = webView;
    }

    /**
     * 直接回调js，只能在UI线程中调用
     *
     * @param cb     回调js
     * @param result 回调结果
     */
    @UiThread
    protected void callBackWithOutTranscode(String cb, String result) {
        if (webView == null || TextUtils.isEmpty(cb)) {
            return;
        }
        result = escapeString(result, false);
        String cbString = "javascript:" + cb + "(" + result + ")";
        webView.loadUrl(cbString);
    }

    /**
     * 直接回调js，只能在UI线程中调用
     *
     * @param cb     回调js
     * @param result 回调结果
     */
    @UiThread
    protected void callBack(String cb, String result) {
        if (webView == null || TextUtils.isEmpty(cb)) {
            return;
        }
        result = escapeString(result, true);
        String cbString = "javascript:" + cb + "(\"" + result + "\");";
        webView.loadUrl(cbString);
    }

    private String escapeString(String str, boolean needBackslash) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (c) {
                case '\\':
                    result.append("\\\\");
                    break;
                case '"':
                    result.append(needBackslash ? "\\\"" : "\"");
                    break;
                default:
                    result.append(c);
                    break;
            }
        }
        return result.toString();
    }

    private class CallbackRunnable implements Runnable {
        private String cb;
        private String param;
        private boolean needBackslash;

        CallbackRunnable(String callback, String callbackParam, boolean backslash) {
            cb = callback;
            param = callbackParam;
            needBackslash = backslash;
        }

        @Override
        public void run() {
            if (needBackslash) {
                callBack(cb, param);
            } else {
                callBackWithOutTranscode(cb, param);
            }
        }
    }

    /**
     * 在UI线程回调，一般需要调用这个方法来回调js方法。
     *
     * @param cb            回调js方法名
     * @param result        回调结果
     * @param needBackslash
     */
    protected void postCallback(String cb, String result, boolean needBackslash) {
        handler.post(new CallbackRunnable(cb, result, needBackslash));
    }

    /**
     * 获取webview关联的context
     *
     * @return webview所在的Activity
     */
    protected Context getContext() {
        if (context != null) {
            return context;
        } else if (webView != null) {
            return webView.getContext();
        }
        return null;
    }

    public void showToast(final String msg) {
        if (handler != null && !TextUtils.isEmpty(msg)) {
            handler.post(() -> {
                if (getContext() != null) {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
