package com.gegz.logger.core.util;

import android.text.TextUtils;

import com.gegz.logger.core.GLogConfig;
import com.gegz.logger.core.constants.GLogTypes;

import java.util.Random;

/**
 * 日志库需要的部分工具方法
 */
public class GLogUtil {

    /**
     * 打印分割线的方法
     *
     * @param tag   日志标签，用于对齐多行日志
     * @param isTop 是否是上分割线
     */
    public static void printLine(int type, String tag, boolean isTop) {
        // 判断是上分割线还是下分割线并打印
        if (isTop) {
            GLogPrinter.printSub(type, tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            GLogPrinter.printSub(type, tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }

    }

    /**
     * 判断当前行是否为空行的方法
     *
     * @param line 行内容
     * @return 当前行是否为空行
     */
    public static boolean isEmpty(String line) {
        // 根据字符串是否为空、换行符、制表符或空格判断是否为空行
        if (TextUtils.isEmpty(line)) {
            return true;
        } else if ("\n".equals(line)) {
            return true;
        } else {
            return "\t".equals(line) || TextUtils.isEmpty(line.trim());
        }
    }

    /**
     * 生成文件名的方法
     *
     * @return 生成的文件名
     */
    public static String getFileName() {
        // 声明随机数
        Random random = new Random();
        // 返回拼接的文件名（该方法实现可能产生文件名重叠）
        return GLogConfig.FILE_PREFIX + Long.toString(System.currentTimeMillis() + (long) random.nextInt(10000)).substring(4) + ".log";
    }
}
