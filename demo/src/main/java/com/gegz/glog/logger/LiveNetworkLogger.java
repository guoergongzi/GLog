package com.gegz.glog.logger;

import com.gegz.logger.core.GLogExcutor;
import com.gegz.logger.core.constants.GLogTypes;
import com.gegz.logger.core.entity.GLogEntity;

/**
 * 直播网络日志类
 */
public class LiveNetworkLogger extends GLogExcutor {

    // 日志工具单例
    private static volatile LiveNetworkLogger liveNetworkLogger;

    /**
     * 构造方法
     */
    private LiveNetworkLogger(GLogEntity entity) {
        super(entity);
    }

    /**
     * 获取单例的方法
     */
    public static LiveNetworkLogger getInstance() {
        if (liveNetworkLogger == null) {
            synchronized (LiveNetworkLogger.class) {
                if (liveNetworkLogger == null) {
                    initLogger();
                }
            }
        }
        return liveNetworkLogger;
    }

    /**
     * 初始化日志工具类的方法
     */
    private static void initLogger() {
        GLogEntity entity = new GLogEntity();
        entity.initClassName("LiveNetworkLogger");
        entity.initModuleName("LIVE");
        entity.initTypeName("NET");
        entity.initDefaultLevel(GLogTypes.V);
        entity.initJsonLevel(GLogTypes.V);
        entity.initFormatJson(true);
        entity.initTraceCount(3);
        entity.initForceShow(false);
        entity.initShowDivide(false);
        entity.initShowAll(true);
        entity.initMaxLengthCount(3);
        liveNetworkLogger = new LiveNetworkLogger(entity);
    }
}
