package com.gegz.logger.core;

import com.gegz.logger.GLog;
import com.gegz.logger.core.constants.GLogTypes;
import com.gegz.logger.core.entity.GLogEntity;
import com.gegz.logger.core.impl.DefaultLogImpl;
import com.gegz.logger.core.impl.FileLogImpl;
import com.gegz.logger.core.impl.JsonLogImpl;
import com.gegz.logger.core.impl.TraceLogImpl;
import com.gegz.logger.core.impl.XmlLogImpl;
import com.gegz.logger.core.util.GLogWrapper;

import java.io.File;

/**
 * 日志打印执行器，用于对外部提供打印功能
 */
public class GLogExcutor {

    /**
     * 日志配置信息对象
     */
    private final GLogEntity entity;

    /**
     * 构造方法，用于接收日志配置信息对象
     */
    public GLogExcutor(GLogEntity entity) {
        GLoggerSettingsManager.getEntities().add(entity);
        this.entity = entity;
    }

    public void print() {
        if (entity == null) {
            v();
            return;
        }
        printLog(entity.getDefaultLevel(), null, "execute");
    }

    public void print(Object msg) {
        if (entity == null) {
            v(msg);
            return;
        }
        printLog(entity.getDefaultLevel(), null, msg);
    }

    public void print(String tag, Object... objects) {
        if (entity == null) {
            v(tag, objects);
            return;
        }
        printLog(entity.getDefaultLevel(), tag, objects);
    }

    public void v() {
        printLog(GLogTypes.V, null, "execute");
    }

    public void v(Object msg) {
        printLog(GLogTypes.V, null, msg);
    }

    public void v(String tag, Object... objects) {
        printLog(GLogTypes.V, tag, objects);
    }

    public void d() {
        printLog(GLogTypes.D, null, "execute");
    }

    public void d(Object msg) {
        printLog(GLogTypes.D, null, msg);
    }

    public void d(String tag, Object... objects) {
        printLog(GLogTypes.D, tag, objects);
    }

    public void i() {
        printLog(GLogTypes.I, null, "execute");
    }

    public void i(Object msg) {
        printLog(GLogTypes.I, null, msg);
    }

    public void i(String tag, Object... objects) {
        printLog(GLogTypes.I, tag, objects);
    }

    public void w() {
        printLog(GLogTypes.W, null, "execute");
    }

    public void w(Object msg) {
        printLog(GLogTypes.W, null, msg);
    }

    public void w(String tag, Object... objects) {
        printLog(GLogTypes.W, tag, objects);
    }

    public void e() {
        printLog(GLogTypes.E, null, "execute");
    }

    public void e(Object msg) {
        printLog(GLogTypes.E, null, msg);
    }

    public void e(String tag, Object... objects) {
        printLog(GLogTypes.E, tag, objects);
    }

    public void a() {
        printLog(GLogTypes.WTF, null, "execute");
    }

    public void a(Object msg) {
        printLog(GLogTypes.WTF, null, msg);
    }

    public void a(String tag, Object... objects) {
        printLog(GLogTypes.WTF, tag, objects);
    }

    public void json(String jsonFormat) {
        printLog(GLogTypes.JSON, null, jsonFormat);
    }

    public void json(String tag, String jsonFormat) {
        printLog(GLogTypes.JSON, tag, jsonFormat);
    }

    public void json(Object object) {
        try {
            printLog(GLogTypes.JSON, null, GLog.getgLogInterface().objectToString(object));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void json(String tag, Object object) {
        try {
            printLog(GLogTypes.JSON, tag, GLog.getgLogInterface().objectToString(object));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void xml(String xml) {
        printLog(GLogTypes.XML, null, xml);
    }

    public void xml(String tag, String xml) {
        printLog(GLogTypes.XML, tag, xml);
    }

    public void file(File targetDirectory, Object msg) {
        FileLogImpl.printFile(entity, null, targetDirectory, null, msg);
    }

    public void file(String tag, File targetDirectory, Object msg) {
        FileLogImpl.printFile(entity, tag, targetDirectory, null, msg);
    }

    public void file(String tag, File targetDirectory, String fileName, Object msg) {
        FileLogImpl.printFile(entity, tag, targetDirectory, fileName, msg);
    }

    public void trace() {
        TraceLogImpl.printStackTrace(entity);
    }

    /**
     * 根据日志类型调用实现类进行打印的方法
     *
     * @param type    日志级别
     * @param tagStr  日志标识
     * @param objects 日志内容
     */
    private void printLog(int type, String tagStr, Object... objects) {
        // 判断当前是否需要打印日志
        if (entity.isForceShow() || GLogConfig.IS_SHOW_LOG) {
            // 加工日志内容
            String[] contents = GLogWrapper.wrapperContent(entity, tagStr, objects);
            // 获取日志标识
            String tag = contents[0];
            // 获取日志内容
            String msg = contents[1];
            // 获取日志头
            String headString = contents[2];
            // 根据日志类型调用相应实现类打印
            switch (type) {
                case GLogTypes.V:
                case GLogTypes.D:
                case GLogTypes.I:
                case GLogTypes.W:
                case GLogTypes.E:
                case GLogTypes.WTF:
                    // 打印普通日志
                    DefaultLogImpl.printDefault(entity, type, tag, headString + msg);
                    break;
                case GLogTypes.JSON:
                    // 打印Json日志
                    JsonLogImpl.printJson(entity, tag, msg, headString);
                    break;
                case GLogTypes.XML:
                    // 打印Xml日志
                    XmlLogImpl.printXml(entity, tag, msg, headString);
                    break;
                default:
            }

        }
    }
}
