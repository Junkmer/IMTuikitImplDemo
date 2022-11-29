package com.tencent.im.tuikit.impl.demo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMValueCallback;
import com.tencent.qcloud.tim.tuiofflinepush.utils.BrandUtil;
import com.tencent.qcloud.tuicore.TUIConstants;
import com.tencent.qcloud.tuicore.TUICore;
import com.tencent.qcloud.tuicore.interfaces.ITUINotification;
import com.tencent.qcloud.tuicore.util.ErrorMessageConverter;
import com.tencent.qcloud.tuicore.util.TUIUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class DemoApplication extends Application {

    private static final String TAG = DemoApplication.class.getSimpleName();
    private static DemoApplication instance;

    public static DemoApplication instance() {
        return instance;
    }

    @Override
    public void onCreate() {
        Log.e("Push-TAG", "组件push-方式.....111");
        Log.i(TAG, "onCreate");
        super.onCreate();

        if (isMainProcess()) {
            instance = this;
        }

        registerNotify();
    }

    private void initBuildInformation() {
        try {
            JSONObject buildInfoJson = new JSONObject();
            buildInfoJson.put("buildBrand", BrandUtil.getBuildBrand());
            buildInfoJson.put("buildManufacturer", BrandUtil.getBuildManufacturer());
            buildInfoJson.put("buildModel", BrandUtil.getBuildModel());
            buildInfoJson.put("buildVersionRelease", BrandUtil.getBuildVersionRelease());
            buildInfoJson.put("buildVersionSDKInt", BrandUtil.getBuildVersionSDKInt());
            // 工信部要求 app 在运行期间只能获取一次设备信息。因此 app 获取设备信息设置给 SDK 后，SDK 使用该值并且不再调用系统接口。
            // The Ministry of Industry and Information Technology requires the app to obtain device information only once 
            // during its operation. Therefore, after the app obtains the device information and sets it to the SDK, the SDK 
            // uses this value and no longer calls the system interface.
            V2TIMManager.getInstance().callExperimentalAPI("setBuildInfo", buildInfoJson.toString(), new V2TIMValueCallback<Object>() {
                @Override
                public void onSuccess(Object o) {
                    Log.i(TAG, "setBuildInfo success");
                }

                @Override
                public void onError(int code, String desc) {
                    Log.i(TAG, "setBuildInfo code:" + code + " desc:" + ErrorMessageConverter.convertIMError(code, desc));
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean isMainProcess() {
        ActivityManager am = ((ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE));
        String mainProcessName = this.getPackageName();
        String currentProcessName = TUIUtil.getProcessName();
        if (mainProcessName.equals(currentProcessName)) {
            return true;
        } else {
            return false;
        }
    }

    // call in Application onCreate
    private void registerNotify() {
        TUICore.registerEvent(TUIConstants.TUIOfflinePush.EVENT_NOTIFY, TUIConstants.TUIOfflinePush.EVENT_NOTIFY_NOTIFICATION, new ITUINotification() {
                    @Override
                    public void onNotifyEvent(String key, String subKey, Map<String, Object> param) {
                        Log.d(TAG, "onNotifyEvent key = " + key + "subKey = " + subKey);
                        if (TUIConstants.TUIOfflinePush.EVENT_NOTIFY.equals(key)) {
                            if (TUIConstants.TUIOfflinePush.EVENT_NOTIFY_NOTIFICATION.equals(subKey)) {
                                if (param != null) {
                                    //做跳转逻辑
//                                    Log.d(TAG, "做跳转逻辑");
                                    String extString = (String) param.get(TUIConstants.TUIOfflinePush.NOTIFICATION_EXT_KEY);
                                }
                            }
                        }
                    }
                });
    }
}
