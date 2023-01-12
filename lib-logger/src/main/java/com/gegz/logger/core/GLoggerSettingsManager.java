package com.gegz.logger.core;

import com.gegz.logger.core.entity.GLogEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 日志SDK配置管理类
 */
public class GLoggerSettingsManager {

    /**
     * 所有日志配置实体类，用来在运行中调整实体类配置
     */
    public static List<GLogEntity> entities = new ArrayList<>();

    public static List<GLogEntity> getEntities() {
        return entities;
    }
}
