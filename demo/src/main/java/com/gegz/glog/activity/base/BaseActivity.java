package com.gegz.glog.activity.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gegz.glog.BuildConfig;
import com.gegz.glog.view.GFloatButton;
import com.gegz.logger.GLog;

/**
 * activity基类，用来让配置入口按钮显示在所有界面上
 */
public abstract class BaseActivity extends AppCompatActivity {

    // 悬浮按钮
    GFloatButton floatButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (BuildConfig.DEBUG) {
            floatButton = new GFloatButton(this);
            floatButton.show();
            // 绑定悬浮按钮点击事件
            floatButton.setOnClickListener(v -> {
                GLog.startSetttingActivity(BaseActivity.this);
            });
        }
    }
}
