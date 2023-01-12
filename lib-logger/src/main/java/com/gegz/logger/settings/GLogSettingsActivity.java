package com.gegz.logger.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gegz.logger.R;
import com.gegz.logger.core.GLoggerSettingsManager;
import com.gegz.logger.core.entity.GLogEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 日志框架灵活配置界面
 */
public class GLogSettingsActivity extends AppCompatActivity {

    /**
     * 保存配置按钮
     */
    private Button saveButton;
    /**
     * 各个日志打印类的配置布局的容器布局
     */
    private LinearLayout settingsLayout;
    /**
     * 放弃修改并返回的按钮
     */
    private ImageView backButton;
    /**
     * 各个日志打印类的配置布局
     */
    private final List<GLogSettingLayout> settingLayouts = new ArrayList<>();


    /**
     * 启动界面的方法
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, GLogSettingsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glog_settings);
        backButton = findViewById(R.id.iv_glog_settings_back);
        saveButton = findViewById(R.id.btn_glog_settings_save);
        settingsLayout = findViewById(R.id.layout_glog_settings);
        initSettingLayouts();
        initSaveButton();
        initBackButton();
    }

    /**
     * 初始化各个日志打印类的配置布局
     */
    private void initSettingLayouts() {
        for (GLogEntity entity : GLoggerSettingsManager.getEntities()) {
            GLogSettingLayout settingLayout = new GLogSettingLayout(this);
            settingLayout.initEntity(entity);
            settingLayouts.add(settingLayout);
            settingsLayout.addView(settingLayout);
        }
    }

    /**
     * 初始化保存按钮
     */
    private void initSaveButton() {
        saveButton.setOnClickListener(view -> {
            for (GLogSettingLayout settingLayout : settingLayouts) {
                if (!settingLayout.checkSetting()) {
                    return;
                }
            }
            for (GLogSettingLayout settingLayout : settingLayouts) {
                settingLayout.saveEntity();
            }
            finish();
        });
    }

    /**
     * 初始化返回按钮
     */
    private void initBackButton() {
        backButton.setOnClickListener(view -> finish());
    }
}
