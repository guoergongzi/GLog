package com.gegz.logger.core.impl;

import com.gegz.logger.core.GLogConfig;
import com.gegz.logger.core.entity.GLogEntity;
import com.gegz.logger.core.util.GLogPrinter;
import com.gegz.logger.core.util.GLogUtil;

/**
 * 打印普通日志的实现类
 */
public class DefaultLogImpl {

    /**
     * 打印日志的方法
     *
     * @param entity 日志配置信息
     * @param type   日志级别
     * @param tag    日志标识
     * @param msg    日志内容
     */
    public static void printDefault(GLogEntity entity, int type, String tag, String msg) {
        // 声明行号变量
        int lineNumber = 0;
        // 获取显示完整日志需要的行数
        int countOfSub = msg.length() / GLogConfig.MAX_LENGTH;
        // 根据配置信息决定是否显示分割线
        if (entity.isShowDivide()) {
            GLogUtil.printLine(type, tag, true);
        }
        // 判断是否需要分割显示
        if (countOfSub > 0 && entity.isShowAll()) {
            // 根据日志分割的块数进行分块显示
            for (int i = 0; i < countOfSub && i < entity.getMaxLengthCount(); ++i) {
                String sub = msg.substring(lineNumber, lineNumber + GLogConfig.MAX_LENGTH);
                GLogPrinter.printSub(type, tag, sub);
                lineNumber += GLogConfig.MAX_LENGTH;
            }
            // 打印最后一块日志
            GLogPrinter.printSub(type, tag, msg.substring(lineNumber));
        } else {
            // 直接打印日志
            GLogPrinter.printSub(type, tag, msg);
        }
        // 根据配置信息决定是否显示分割线
        if (entity.isShowDivide()) {
            GLogUtil.printLine(type, tag, false);
        }
    }
}
