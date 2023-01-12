package com.gegz.logger.core.impl;

import com.gegz.logger.core.GLogConfig;
import com.gegz.logger.core.constants.GLogTypes;
import com.gegz.logger.core.entity.GLogEntity;
import com.gegz.logger.core.util.GLogWrapper;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 打印调用栈日志的实现类（优化中，暂不可用）
 */
public class TraceLogImpl {

    /**
     * 打印调用栈日志
     *
     * @param entity 日志配置信息
     */
    public static void printStackTrace(GLogEntity entity) {
        // 判断当前是否需要打印日志
        if (entity.isForceShow() || GLogConfig.IS_SHOW_LOG) {
            // 声明可抛出对象
            Throwable tr = new Throwable();
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            tr.printStackTrace(pw);
            pw.flush();
            String message = sw.toString();
            String[] traceString = message.split("\\n\\t");
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            String[] contents = traceString;
            int var7 = traceString.length;

            String trace;
            for (int var8 = 0; var8 < var7; ++var8) {
                trace = contents[var8];
                if (!trace.contains("at anda.travel.driver.common.tools.logger")) {
                    sb.append(trace).append("\n");
                }
            }

            contents = GLogWrapper.wrapperContent(entity, null, sb.toString());
            String tag = contents[0];
            String msg = contents[1];
            trace = contents[2];
            DefaultLogImpl.printDefault(entity, entity.getDefaultLevel(), tag, trace + msg);
        }
    }
}
