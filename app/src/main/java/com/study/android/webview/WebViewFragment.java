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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import com.study.android.BuildConfig;
import com.study.android.R;
import com.study.android.utils.SystemUIUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WebViewFragment extends Fragment {
    private static String URL = "url";
    private static String IS_TRANSPARENT = "isTransparent";
    private static final String TAG = "WebViewFragment";

    private boolean isTransparent = false;
    private String mUrl;
    private JsBridgeContainer mJsBridgeObj = null;

    @BindView(R.id.title_left)
    AppCompatImageView titleBarLeftImage;

    @BindView(R.id.tv_webview_title)
    TextView titleTv;

    @BindView(R.id.title_right)
    AppCompatImageView titleBarRightImage;

    @BindView(R.id.ll_web_view_title)
    LinearLayout titleBar;

    @BindView(R.id.web_view_container)
    RelativeLayout webViewContainer;

    @BindView(R.id.no_network_layout)
    RelativeLayout noNetworkLayout;

    //    @BindView(R.id.web_view)
    public static WebView mWebView;

    @BindView(R.id.root)
    LinearLayout root;

    public static WebViewFragment newInstance(String url) {
        WebViewFragment fragment = new WebViewFragment();

        Bundle args = new Bundle();
        args.putString(URL, url);
        fragment.setArguments(args);

        return fragment;
    }

    public static WebViewFragment newInstance(String url, boolean isTransparent) {
        WebViewFragment fragment = new WebViewFragment();

        Bundle args = new Bundle();
        args.putString(URL, url);
        args.putBoolean(IS_TRANSPARENT, isTransparent);
        fragment.setArguments(args);

        return fragment;
    }

    public void updateloadUrl(String url) {
        titleBar.setVisibility(View.VISIBLE);
        mUrl = url;
        loadUrl();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUIUtils.setupTranslucentSystemBar(getActivity());
        SystemUIUtils.setSystemBarTitle(getActivity());
        if (getArguments() != null) {
            mUrl = getArguments().getString(URL);
            isTransparent = getArguments().getBoolean(IS_TRANSPARENT);
        }

        String authcookie = "test_authcookie";
        syncCookie(getActivity(), mUrl, authcookie);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        ButterKnife.bind(this, view);
        if (mWebView == null) {
            mWebView = new WebView(getActivity().getApplicationContext());
        }

        if (BuildConfig.DEBUG) {
            mWebView.setWebContentsDebuggingEnabled(true);
        } else {
            mWebView.setWebContentsDebuggingEnabled(false);
        }

        if (webViewContainer != null) {
            webViewContainer.removeView(mWebView);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mWebView.setLayoutParams(params);
            ViewGroup parentViewGroup = (ViewGroup) mWebView.getParent();
            if (parentViewGroup != null) {
                parentViewGroup.removeView(mWebView);
            }
            webViewContainer.addView(mWebView);
        }

        loadUrl();
        createJsBridgeObj(mWebView);
        mWebView.addJavascriptInterface(mJsBridgeObj, "JSAction");

        webViewContainer.setVisibility(View.VISIBLE);
        noNetworkLayout.setVisibility(View.GONE);

        if (isTransparent) {
            mWebView.setBackgroundColor(getResources().getColor(R.color.transparent));
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWebView();
    }

    private void initWebView() {
        initWebViewSetting();
        initWebViewListener();
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

    public void initWebViewListener() {
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                titleTv.setText(title);
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                final Activity context = getActivity();

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
        });
    }

    private void initWebViewSetting() {
        WebSettings settings = mWebView.getSettings();
        if (settings != null) {
            settings.setJavaScriptEnabled(true);
            settings.setLoadWithOverviewMode(true);
            settings.setUseWideViewPort(true);  //enable viewport tag
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
            settings.setTextZoom(100);
            settings.setAppCacheEnabled(true);
            settings.setDatabaseEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }
    }

    private void loadUrl() {
        Log.d(TAG, "loadUrl: loadurl = " + mUrl);
        //initCookie();
        if (!TextUtils.isEmpty(mUrl)) {
            mWebView.loadUrl(mUrl);
        }
    }

    private void createJsBridgeObj(WebView webView) {
        if (mJsBridgeObj == null) {
            mJsBridgeObj = new JsBridgeContainer(getContext(), this, mWebView);
            // for (JsBridgeBase bridge : mBridges) {
            //     mJsBridgeObj.registerJsBridge(this, bridge);
            // }
        }
    }

    @Override
    public void onDestroy() {
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

    public static void syncCookie(Context context, String url, String cookie) {
        Log.d(TAG, "set cookie url = " + url + "\n cookie = " + cookie);
        if (TextUtils.isEmpty(cookie)) {
            return;
        }
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);

        String hToken = "H_token=" + cookie;
        String hDid = "H_device_id=1001001010111";
        String sid = "sid=" + UUID.randomUUID().toString();

        String domainUrl = "https://com.ljz.study/";

        cookieManager.setCookie(domainUrl, hToken);
        cookieManager.setCookie(domainUrl, hDid);
        cookieManager.setCookie(domainUrl, sid);

        cookieManager.flush();
    }
}
