package com.gegz.logger.core.impl;

import com.gegz.logger.core.constants.GLogStrs;
import com.gegz.logger.core.entity.GLogEntity;
import com.gegz.logger.core.util.GLogPrinter;
import com.gegz.logger.core.util.GLogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 打印Json日志的实现类
 */
public class JsonLogImpl {

    /**
     * 打印Json文件的方法
     *
     * @param entity     日志配置信息
     * @param tag        日志标识
     * @param msg        日志内容
     * @param headString 日志头
     */
    public static void printJson(GLogEntity entity, String tag, String msg, String headString) {
        // 默认打印未格式化的Json数据
        DefaultLogImpl.printDefault(entity, entity.getJsonLevel(), tag, headString + msg);
        // 判断是否需要显示格式化后的Json数据
        if (!entity.isFormatJson()) {
            return;
        }
        // 声明打印内容
        String message;
        msg = msg.trim();
        try {
            // 判断是否是对象Json
            if (msg.startsWith("{")) {
                // 是对象Json则进行对象Json解析（每层缩进4）
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(4);
            } else if (msg.startsWith("[")) {
                // 是数组Json则进行数组Json解析（每层缩进4）
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(4);
            } else {
                // 不是Json则不进行解析
                message = msg;
            }
        } catch (JSONException e) {
            // 出错时不进行解析
            message = msg;
        }
        // 拼接日志头信息和换行符
        message = headString + GLogStrs.LINE_SEPARATOR + message;
        // 打印分割线
        GLogUtil.printLine(entity.getJsonLevel(), tag, true);
        // 系统换行符判空
        if (GLogStrs.LINE_SEPARATOR != null) {
            // 使用换行符分割Json日志内容
            String[] lines = message.split(GLogStrs.LINE_SEPARATOR);

            // 遍历Json日志内容条目并打印
            for (String line : lines) {
                GLogPrinter.printSub(entity.getJsonLevel(), tag, "║ " + line);
            }
        }
        // 打印分割线
        GLogUtil.printLine(entity.getJsonLevel(), tag, false);
    }
}
