package com.gegz.logger.core.util;

import android.text.TextUtils;

import com.gegz.logger.core.GLogConfig;
import com.gegz.logger.core.entity.GLogEntity;


/**
 * 获取日志加工后内容的工具类
 */
public class GLogWrapper {

    /**
     * 加工日志的方法
     *
     * @param entity  日志配置信息
     * @param tagStr  日志标识
     * @param objects 日志内容
     * @return 加工后的日志信息（0：日志标识tag；1：日志内容msg;2：日志头headString；）
     */
    public static String[] wrapperContent(GLogEntity entity, String tagStr, Object... objects) {
        // 声明日志标识字符串
        StringBuilder tag = new StringBuilder(GLogConfig.TAG_DEFAULT);
        // 判断是否需要显示模块标识
        if (GLogConfig.IS_SHOW_MODULE && !TextUtils.isEmpty(entity.getModuleName())) {
            tag.append("_").append(entity.getModuleName());
        }
        // 判断是否需要显示类型标识
        if (GLogConfig.IS_SHOW_TYPE && !TextUtils.isEmpty(entity.getTypeName())) {
            tag.append("_").append(entity.getTypeName());
        }
        // 判断需要显示标识
        if (!TextUtils.isEmpty(tagStr)) {
            tag.append("_").append(tagStr);
        }
        // 获取日志内容字符串
        String msg = getObjectsString(objects);
        // 声明日志头字符串
        StringBuilder headString = new StringBuilder();
        // 拼接第一个换行符
        if (entity.getTraceCount() > 1) {
            headString.append(" \n");
        }
        // 获取方法调用栈
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        // 遍历方法调用栈，显示调用栈信息
        for (int i = GLogConfig.STACK_TRACE_INDEX; i < GLogConfig.STACK_TRACE_INDEX + entity.getTraceCount(); i++) {
            if (i < stackTrace.length) {
                // 拼接调用栈信息
                headString.append("at ").append(stackTrace[i]).append("\n");
            }
        }
        // 返回加工后的日志字符串
        return new String[]{tag.toString(), msg, headString.toString()};
    }

    /**
     * 获取日志内容字符串的方法
     *
     * @param objects 日志参数（用于单条日志打印多个信息）
     * @return 日志参数转换成的用于打印的字符串
     */
    @SuppressWarnings("UnnecessaryToStringCall")
    private static String getObjectsString(Object... objects) {
        // 声明结果字符串
        StringBuilder result = new StringBuilder("\t");
        // 判断日志参数是否为空
        if (objects == null) {
            result.append("Log with null object");
            return result.toString();
        }
        // 判断是否有多个日志参数
        if (objects.length > 1) {
            // 换行与其它参数分离
            result.append("\n");
            // 遍历日志参数
            for (int i = 0; i < objects.length; ++i) {
                Object object = objects[i];
                // 判断日志参数是否为空并打印参数信息
                if (object == null) {
                    result.append("\t").append("Param").append("[").append(i).append("]").append(" = ").append("null").append("\n");
                } else {
                    result.append("\t").append("Param").append("[").append(i).append("]").append(" = ").append(object.toString()).append("\n");
                }
            }
        } else if (objects.length == 1) {
            // 只有一个日志内容时直接打印其内容
            Object object = objects[0];
            if (object == null) {
                result.append("Log with null object");
            } else {
                result.append(object.toString());
            }
        } else {
            // 日志参数为0时显示提示信息
            result.append("Log with null object");
        }
        // 返回拼接结果
        return result.toString();
    }
}
