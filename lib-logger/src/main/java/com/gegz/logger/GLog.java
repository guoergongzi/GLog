package com.gegz.logger;

import android.content.Context;

import com.gegz.logger.core.GLogConfig;
import com.gegz.logger.core.GLogInterface;
import com.gegz.logger.settings.GLogSettingsActivity;

/**
 * 日志模块外观类，提供一些静态方法
 */
public class GLog {

    private static GLogInterface gLogInterface;

    public static GLogInterface getgLogInterface() {
        return gLogInterface;
    }

    /**
     * 初始化日志模块的方法
     *
     * @param showLog      是否需要显示日志（对强制显示的部分无效）
     * @param isAllError   是否以最高级别显示所有日志（为了应对部分机型不显示Error以下信息）
     * @param defaultTag   默认日志前綴，一般用应用简称即可
     * @param isShowModule 是否显示日志所在模块
     * @param isShowType   是否显示日志类型
     */
    public static void initLogger(boolean showLog, boolean isAllError, String defaultTag, boolean isShowModule, boolean isShowType, GLogInterface gLogInterface) {
        GLogConfig.IS_SHOW_LOG = showLog;
        GLogConfig.IS_ALL_ERROR = isAllError;
        GLogConfig.TAG_DEFAULT = defaultTag;
        GLogConfig.FILE_PREFIX = defaultTag + "_";
        GLogConfig.IS_SHOW_MODULE = isShowModule;
        GLogConfig.IS_SHOW_TYPE = isShowType;
        GLog.gLogInterface = gLogInterface;
    }

    /**
     * 启动网络请求模块设置界面的方法
     */
    public static void startSetttingActivity(Context context) {
        GLogSettingsActivity.startActivity(context);
    }
}
