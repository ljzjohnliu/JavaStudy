package com.study.android.webview;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONObject;

public class SysInfoBridge extends JsBridgeBase {

    SysInfoBridge() {
        super();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean handlerJs(int type, String callback, JSONObject jparam) {
        boolean result = true;
        switch (type) {
            case JSActionType.SHOW_TOAST:
                String msg = jparam.optString(JSBridgeConstants.MSG);
                showToast(msg);
                break;

            case JSActionType.GET_DEVICE_INFO:
                getDeviceInfo(callback);
                break;
            default:
                result = false;
                break;
        }
        return result;
    }

    private void getDeviceInfo(String cb) {
        String resultStr;
        JSONObject data = new JSONObject();
        try {
            data.put("model", Build.MODEL);
            data.put("os_name", "android");
            data.put("os_version", Build.VERSION.RELEASE);
            data.put("sdk_level", Build.VERSION.SDK_INT);
            resultStr = data.toString();
        } catch (Exception e) {
            Log.d("jsbridge", "getDeviceInfo: e: " + e);
            resultStr = e.getMessage();
        }
        postCallback(cb, resultStr, true);
    }
}
