package com.gegz.glog.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.gegz.glog.R;
import com.gegz.glog.activity.base.BaseActivity;

/**
 * 测试界面，测试配置入口按钮是否能在所有继承BaseActivity的界面中正常显示
 */
public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
}
