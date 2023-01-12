package com.gegz.glog.logger;

import com.gegz.logger.core.GLogExcutor;
import com.gegz.logger.core.constants.GLogTypes;
import com.gegz.logger.core.entity.GLogEntity;

/**
 * 公用日志类
 */
public class GTestLogger extends GLogExcutor {

    // 日志工具单例
    private static volatile GTestLogger gTestLogger;

    /**
     * 构造方法
     */
    private GTestLogger(GLogEntity entity) {
        super(entity);
    }

    /**
     * 获取单例的方法
     */
    public static GTestLogger getInstance() {
        if (gTestLogger == null) {
            synchronized (GTestLogger.class) {
                if (gTestLogger == null) {
                    initLogger();
                }
            }
        }
        return gTestLogger;
    }

    /**
     * 初始化日志工具类的方法
     */
    private static void initLogger() {
        GLogEntity entity = new GLogEntity();
        entity.initClassName("GTestLogger");
        entity.initModuleName("G");
        entity.initTypeName("TEST");
        entity.initDefaultLevel(GLogTypes.V);
        entity.initJsonLevel(GLogTypes.V);
        entity.initFormatJson(true);
        entity.initTraceCount(3);
        entity.initForceShow(false);
        entity.initShowDivide(false);
        entity.initShowAll(true);
        entity.initMaxLengthCount(3);
        gTestLogger = new GTestLogger(entity);
    }
}
