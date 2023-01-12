package com.gegz.logger.core.util;

import android.util.Log;

import com.gegz.logger.core.GLogConfig;
import com.gegz.logger.core.constants.GLogTypes;

/**
 * 最终对日志进行显示的工具类
 */
public class GLogPrinter {

    /**
     * 最终打印日志的方法
     *
     * @param type   日志级别
     * @param tag    日志标识
     * @param sub    日志内容
     */
    public static void printSub(int type, String tag, String sub) {
        // 判断是否总是以最高级别显示日志（为了应对部分机型不显示Error以下信息）
        if (GLogConfig.IS_ALL_ERROR && type != 6) {
            Log.e(tag, sub);
        }
        switch (type) {
            case GLogTypes.V:
                Log.v(tag, sub);
                break;
            case GLogTypes.D:
                Log.d(tag, sub);
                break;
            case GLogTypes.I:
                Log.i(tag, sub);
                break;
            case GLogTypes.W:
                Log.w(tag, sub);
                break;
            case GLogTypes.E:
                Log.e(tag, sub);
                break;
            case GLogTypes.WTF:
                Log.wtf(tag, sub);
                break;
            default:
        }

    }
}
