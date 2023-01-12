package com.gegz.glog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.gegz.glog.R;
import com.gegz.glog.activity.base.BaseActivity;
import com.gegz.glog.logger.GTestLogger;
import com.gegz.glog.logger.LiveNetworkLogger;
import com.gegz.logger.core.GLoggerSettingsManager;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 主界面，用来测试打印功能
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initJumpButton();
        initLogButton();
    }

    /**
     * 初始化跳转按钮
     */
    private void initJumpButton() {
        Button jumpButton = findViewById(R.id.btn_jump_test);
        jumpButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, TestActivity.class);
            startActivity(intent);
        });
    }

    /**
     * 初始化打印按钮
     */
    private void initLogButton() {
        Button printButton = findViewById(R.id.btn_print_log);
        printButton.setOnClickListener(view -> {
            testLogger();
        });

    }

    /**
     * 测试日志控件
     */
    private void testLogger() {

        String tag = "RESPONSE";
        String message = "hello";
        String params1 = "test1";
        String params2 = "test2";
        String params3 = "test3";

        // 测试Verbose级别日志
        LiveNetworkLogger.getInstance().v();
        LiveNetworkLogger.getInstance().v(message);
        LiveNetworkLogger.getInstance().v(tag, message);
        LiveNetworkLogger.getInstance().v(tag, params1, params2, params3);

        // 测试其它级别日志
        LiveNetworkLogger.getInstance().d(tag, message);
        LiveNetworkLogger.getInstance().i(tag, message);
        LiveNetworkLogger.getInstance().w(tag, message);
        LiveNetworkLogger.getInstance().e(tag, message);
        LiveNetworkLogger.getInstance().a(tag, message);
        LiveNetworkLogger.getInstance().print(tag, message);

        // 测试其它类型的日志
        String jsonString = new Gson().toJson(GLoggerSettingsManager.getEntities());
        Object object = GLoggerSettingsManager.getEntities();
        String xmlString = "";
        try {
            StringBuilder sb = new StringBuilder();
            InputStream is = null;
            is = getAssets().open("person1.xml");
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
            xmlString = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LiveNetworkLogger.getInstance().json(jsonString);
        LiveNetworkLogger.getInstance().json(tag, jsonString);
        LiveNetworkLogger.getInstance().json(object);
        LiveNetworkLogger.getInstance().json(tag, object);
        LiveNetworkLogger.getInstance().xml(xmlString);
        LiveNetworkLogger.getInstance().xml(tag, xmlString);

        // 测试文件保存日志
        String directory = getExternalCacheDir() + "/Log";
        File targetDirectory = new File(directory);
        String fileName = "testFile";

        LiveNetworkLogger.getInstance().file(targetDirectory, message);
        LiveNetworkLogger.getInstance().file(tag, targetDirectory, message);
        LiveNetworkLogger.getInstance().file(tag, targetDirectory, fileName, message);

        // 测试调用栈日志
        LiveNetworkLogger.getInstance().trace();

        // 测试其它日志类
        GTestLogger.getInstance().print(tag, message);
    }
}