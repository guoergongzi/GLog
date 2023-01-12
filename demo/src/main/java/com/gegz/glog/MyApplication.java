package com.gegz.glog;

import android.app.Application;

import com.gegz.logger.GLog;
import com.gegz.logger.core.GLogInterface;
import com.google.gson.Gson;

/**
 * App类，推荐在这里初始化sdk
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化SDK
        GLog.initLogger(BuildConfig.DEBUG, false, "GDEMO", true, true, new GLogInterface() {
            @Override
            public String objectToString(Object object) {
                return new Gson().toJson(object);
            }
        });
    }
}
