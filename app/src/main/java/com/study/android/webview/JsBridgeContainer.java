package com.study.android.webview;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsBridgeContainer {

    private SysInfoBridge sysInfoBridge = null;

    private ArrayList<JsBridgeBase> mJsBridges = new ArrayList<JsBridgeBase>();
    private Context context;
    private WebView mWebView;
    private Fragment mWebFragment;
    private FragmentActivity mFragmentActivity;
    Handler mHandler;

    public JsBridgeContainer(Context context, Fragment webFragment, WebView webView) {
        this.context = context;
        this.mWebFragment = webFragment;
        this.mWebView = webView;
        initJsBridge();
    }

    public JsBridgeContainer(Context context, FragmentActivity fragmentActivity, WebView webView) {
        this.context = context;
        this.mFragmentActivity = fragmentActivity;
        this.mWebView = webView;
        initJsBridge();
    }

    public JsBridgeContainer(Context context, WebView webView) {
        this.context = context;
        this.mWebView = webView;
        mHandler = new Handler();
        initJsBridge();
    }

    private void initJsBridge() {
        sysInfoBridge = new SysInfoBridge();

        mJsBridges.add(sysInfoBridge);
        for (JsBridgeBase bridge : mJsBridges) {
            bridge.registerWebView(context, mWebView);
        }
    }

    public void registerJsBridge(Context context, JsBridgeBase bridge) {
        bridge.registerWebView(context, mWebView);
        mJsBridges.add(bridge);
    }

    @JavascriptInterface
    public void callNative(String param) {
        Log.d("jsbridge", "callNative: param = " + param);
        try {
            JSONObject jparam = new JSONObject(param);
            int actionType = jparam.optInt(JSBridgeConstants.ACTION_TYPE);
            String callback = jparam.optString(JSBridgeConstants.CALLBACK);
            String params = jparam.optString(JSBridgeConstants.PARAMS);
            if (actionType == JSActionType.HIDE_WEBVIEW) {
                hideWebview();
                return;
            }

            for (JsBridgeBase bridge : mJsBridges) {
                if (bridge.handlerJs(actionType, callback, new JSONObject(params))) {
                    return;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("jsbridge", "callNative: e = " + e);
        }
    }

    public void destroy() {
        mJsBridges.clear();
    }

    public void hideWebview() {
        if (mFragmentActivity != null) {
            mFragmentActivity.finish();
        }
        Log.d("jsbridge", "hideWebview: ----------mWebFragment = " + mWebFragment);
    }
}