package com.study.android.webview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;

import com.study.android.BuildConfig;
import com.study.android.R;
import com.study.android.base.BaseSimpleActivity;
import com.study.android.utils.DisplayUtil;
import com.study.android.utils.SystemUIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends BaseSimpleActivity {
    private static final String TAG = "WebViewActivity";
    private static final String KEY_RIGHT_BTN = "KEY_RIGHT_BTN";
    private static final String KEY_SHOW_TITLE = "KEY_SHOW_TITLE";
    private String loadurl;
    private String title = null;
    private boolean mEnableJsBridge = true;
    private Uri imageUri;
    private JsBridgeContainer mJsBridgeObj = null;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallback;

    private boolean isShowRightBtn = false;
    private boolean isShowTitle = true;

    @BindView(R.id.title_left)
    AppCompatImageView titleBarLeftImage;

    @BindView(R.id.tv_webview_title)
    TextView titleTv;

    @BindView(R.id.title_right)
    AppCompatImageView titleBarRightImage;

    @BindView(R.id.ll_web_view_title)
    LinearLayout titleBar;

    @BindView(R.id.web_view)
    WebView mWebView;

    @BindView(R.id.root)
    LinearLayout root;

    @OnClick(R.id.title_left)
    public void onBackClicked() {
        goBack();
    }

    public static void openWebViewActivity(Context context, String url) {
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(context, "url must not be null", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(context, WebViewActivity.class);
        intent.setData(Uri.parse(url));

        if (url != null && url.contains("stable/my_earnings")) {
            intent.putExtra(KEY_RIGHT_BTN, true);
        }

        if (url != null && url.contains("stable/giant_event")) {
            intent.putExtra(KEY_SHOW_TITLE, false);
        }

        context.startActivity(intent);
    }

    public static void openMyEarnings(Context context) {
        Intent intent = new Intent(context, WebViewActivity.class);
        String url = "https://www.baidu.com/";
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUIUtils.setupTranslucentSystemBar(this);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);

        if (getIntent() == null) return;
        Uri uri = getIntent().getData();

        isShowRightBtn = getIntent().hasExtra(KEY_RIGHT_BTN)
                && getIntent().getBooleanExtra(KEY_RIGHT_BTN, false);

        if (getIntent().hasExtra(KEY_SHOW_TITLE)) {
            isShowTitle = getIntent().getBooleanExtra(KEY_SHOW_TITLE, true);
        }

        if (uri == null) {
            finish();
            return;
        }

        if (BuildConfig.DEBUG) {
            mWebView.setWebContentsDebuggingEnabled(true);
        } else {
            mWebView.setWebContentsDebuggingEnabled(false);
        }

        loadurl = uri.toString();
        initWebView();

        String authcookie = "test_auth_cookie";
        syncCookie(this, loadurl, authcookie);

        if (getIntent().hasExtra(WebArgsKey.ENABLE_JSBRIDGE)) {
            mEnableJsBridge = getIntent().getBooleanExtra(WebArgsKey.ENABLE_JSBRIDGE, true);
        }

        if (mEnableJsBridge) {
            //添加jsbridge支持
            createJsBridgeObj(mWebView);
            Log.d(TAG, "onCreate: ");
            mWebView.addJavascriptInterface(mJsBridgeObj, "JSAction");
            String ss = "{\"code\": \"A00000\",\"data\": {\"id\": \"hello\"},\"msg\": \"正常\"}";
        }

        if (!isShowTitle) {
            titleBar.setVisibility(View.GONE);
        } else {
            SystemUIUtils.setSystemBarTitle(this);
        }
        loadUrl();
    }

    private void createJsBridgeObj(WebView webView) {
        if (mJsBridgeObj == null) {
            mJsBridgeObj = new JsBridgeContainer(this, this, webView);
            for (JsBridgeBase bridge : mBridges) {
                mJsBridgeObj.registerJsBridge(this, bridge);
            }
        }
    }

    private ArrayList<JsBridgeBase> mBridges = new ArrayList<JsBridgeBase>();

    @Override
    protected void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.onPause();
        }
    }

    @Override
    public void onBackPressed() {
        goBack();
        super.onBackPressed();
    }

    private void goBack() {
        if (mWebView != null) {
            Log.d("webview", "dispatchKeyEvent = " + mWebView.canGoBack());
        }

        Log.d(TAG, "mWebView.getUrl() = " + mWebView.getUrl());
        Uri loadUri = null;
        if (!TextUtils.isEmpty(mWebView.getUrl())) {
            loadUri = Uri.parse(mWebView.getUrl());
        }
        if (mWebView != null && !mWebView.canGoBack()) {
            Log.d(TAG, "loadUri.Host = " + loadUri.getHost());
            finish();
        } else if (mWebView != null && mWebView.canGoBack()
                && loadUri != null
                && loadUri.getHost() != null) {
            mWebView.goBack();
        }
    }

    @Override
    protected void onDestroy() {
        if (mJsBridgeObj != null) {
            mJsBridgeObj.destroy();
            mJsBridgeObj = null;
        }
        if (mWebView != null) {
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.stopLoading();
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    private void loadUrl() {
        Log.d(TAG, "loadUrl: loadurl = " + loadurl);
        initCookie();
        if (!TextUtils.isEmpty(loadurl)) {
//            mWebView.getSettings().setDefaultTextEncodingName("gbk");
//            mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
            mWebView.loadUrl(loadurl);
        }
    }

    private void initWebView() {
        initWebViewSetting();
        initWebViewListener();
    }

    private void initWebViewListener() {
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                titleTv.setText(title);
            }

            @Override
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mUploadCallback = filePathCallback;
                showFileChooser();
                return true;
            }

        });
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                final Activity context = WebViewActivity.this;

                // ------  对alipays:相关的scheme处理 -------
                if (isAliPay(url)) {
                    try {
                        context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                    } catch (Exception e) {
                        new AlertDialog.Builder(context)
                                .setMessage("未检测到客户端，请安装后重试。")
                                .setPositiveButton("立即安装", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Uri alipayUrl = Uri.parse("https://d.alipay.com");
                                        context.startActivity(new Intent("android.intent.action.VIEW", alipayUrl));
                                    }
                                }).setNegativeButton("取消", null).show();
                    }
                    return true;
                }
                // ------- 处理结束 -------

                if (url.contains("https://wx.tenpay.com")) {
                    Map extraHeaders = new HashMap<String, String>();
                    extraHeaders.put("Referer", "https://com.ljz.study");
                    mWebView.loadUrl(url, extraHeaders);
                    return true;
                }

                try {
                    if (isWXH5Pay(url)) {
                        try {
                            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                        } catch (Exception e) {
                            new AlertDialog.Builder(context)
                                    .setMessage("未检测到微信客户端，请安装后重试。")
                                    .setPositiveButton("确定", null).show();
                        }
                        return true;
                    }
                } catch (Exception e) {
                    return false;
                }

                if (!(url.startsWith("http") || url.startsWith("https"))) {
                    return true;
                }

                view.loadUrl(url);
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view,
                                                              String url) {
                return null;
            }
        });
    }

    private void initWebViewSetting() {
        WebSettings settings = mWebView.getSettings();
        if (settings != null) {
            settings.setJavaScriptEnabled(true);
            settings.setLoadWithOverviewMode(true);
            settings.setUseWideViewPort(true);  //enable viewport tag
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            settings.setTextZoom(100);
            settings.setAppCacheEnabled(true);
            settings.setDatabaseEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setAllowContentAccess(true);
            settings.setAllowFileAccess(true);
            settings.setUserAgentString(settings.getUserAgentString() + " AppVersion/" + BuildConfig.VERSION_CODE);
            settings.setUserAgentString(settings.getUserAgentString() + " app/hanami");
            //webview chrome调试
            if (BuildConfig.DEBUG) {
                mWebView.setWebContentsDebuggingEnabled(true);
            } else {
                mWebView.setWebContentsDebuggingEnabled(false);
            }

//            if (isBigEvent(loadurl)){
//                settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//            }else{
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
            //}
        }

    }

    private void initCookie() {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setAcceptThirdPartyCookies(mWebView, true);
    }

    private void syncCookie(Context context, String url, String cookie) {
        Log.d(TAG, "set cookie url = " + url + "\n cookie = " + cookie);
        if (TextUtils.isEmpty(cookie)) {
            return;
        }
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);

        String hToken = "H_token=" + cookie;
        String hDid = "H_device_id=1001001010111";
        String sid = "sid=" + UUID.randomUUID().toString();
        int h = DisplayUtil.px2dip(this, SystemUIUtils.getStatusBarHeight(this));
        if (h <= 10) {
            h = 25;
        }

        String statusHeight = "H_safe_area_inset_top=" + String.valueOf(h);

        String domainUrl = "https://com.ljz.study/";

        cookieManager.setCookie(domainUrl, hToken);
        cookieManager.setCookie(domainUrl, hDid);
        cookieManager.setCookie(domainUrl, sid);
        cookieManager.setCookie(domainUrl, statusHeight);

        cookieManager.flush();
    }

    private void showFileChooser() {

    }

    public static boolean isWXH5Pay(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        return url.toLowerCase().startsWith("weixins:") || url.toLowerCase().startsWith("weixin:");
    }

    public static boolean isAliPay(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        return url.startsWith("alipays:") || url.startsWith("alipay");
    }

    public static boolean isBigEvent(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }

        if (url.contains("/stable/giant_event")) {
            return true;
        }
        return false;
    }

    @OnClick(R.id.call_js_with_params)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.call_js_no_params:
                // 无参数调用
                mWebView.loadUrl("javascript:javacalljs()");
                break;
            case R.id.call_js_with_params:
                // 传递参数调用
//                mWebView.loadUrl("javascript:javacalljswithargs(" + "'hello world'" + ")");
                mWebView.evaluateJavascript("javascript:javacalljswithargs(" + "'hello world'" + ")", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Log.d(TAG, "onReceiveValue: -----value = " + value);
                    }
                });
                break;
        }
    }
}
