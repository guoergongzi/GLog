package com.gegz.logger.core.entity;

import com.gegz.logger.core.constants.GLogTypes;

import java.io.Serializable;

/**
 * 日志配置信息，根据该实体类决定日志显示的标识、格式等
 */
@SuppressWarnings("UnusedReturnValue")
public class GLogEntity implements Serializable {

    /**
     * 日志类类名
     */
    private String className = "";
    /**
     * 日志类型标识
     */
    private String typeName = "";
    /**
     * 日志模块标识
     */
    private String moduleName = "";
    /**
     * 是否强制显示该类型日志
     */
    private boolean forceShow = false;
    /**
     * 默认日志级别
     */
    private int defaultLevel = GLogTypes.V;
    /**
     * Json日志和Xml日志级别
     */
    private int jsonLevel = GLogTypes.V;
    /**
     * 是否显示格式化的Json数据
     */
    private boolean formatJson = false;
    /**
     * 显示的任务栈数量
     */
    private int traceCount = 0;
    /**
     * 是否显示分割线
     */
    private boolean showDivide = false;
    /**
     * 是否显示完整日志
     */
    private boolean showAll = true;
    /**
     * 最大日志块数（可以显示多少个MAX_LENGTH的长度）
     */
    private int maxLengthCount = 1;

    /**
     * 日志类类名
     */
    public String getClassName() {
        return className;
    }

    /**
     * 日志类类名
     */
    public GLogEntity initClassName(String className) {
        this.className = className;
        return this;
    }

    /**
     * 日志类型标识
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * 日志类型标识
     */
    public GLogEntity initTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    /**
     * 日志模块标识
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * 日志模块标识
     */
    public GLogEntity initModuleName(String moduleName) {
        this.moduleName = moduleName;
        return this;
    }

    /**
     * 是否强制显示该类型日志
     */
    public boolean isForceShow() {
        return forceShow;
    }

    /**
     * 是否强制显示该类型日志
     */
    public GLogEntity initForceShow(boolean forceShow) {
        this.forceShow = forceShow;
        return this;
    }

    /**
     * Json日志和Xml日志级别
     */
    public int getDefaultLevel() {
        return defaultLevel;
    }

    /**
     * Json日志和Xml日志级别
     */
    public GLogEntity initDefaultLevel(int defaultLevel) {
        this.defaultLevel = defaultLevel;
        return this;
    }

    /**
     * Json日志和Xml日志级别
     */
    public int getJsonLevel() {
        return jsonLevel;
    }

    /**
     * Json日志和Xml日志级别
     */
    public GLogEntity initJsonLevel(int jsonLevel) {
        this.jsonLevel = jsonLevel;
        return this;
    }

    /**
     * 是否显示格式化的Json数据
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isFormatJson() {
        return formatJson;
    }

    /**
     * 是否显示格式化的Json数据
     */
    public GLogEntity initFormatJson(boolean formatJson) {
        this.formatJson = formatJson;
        return this;
    }

    /**
     * 显示的任务栈数量
     */
    public int getTraceCount() {
        return traceCount;
    }

    /**
     * 显示的任务栈数量
     */
    public GLogEntity initTraceCount(int traceCount) {
        this.traceCount = traceCount;
        return this;
    }

    /**
     * 是否显示分割线
     */
    public boolean isShowDivide() {
        return showDivide;
    }

    /**
     * 是否显示分割线
     */
    public GLogEntity initShowDivide(boolean showDivide) {
        this.showDivide = showDivide;
        return this;
    }

    /**
     * 是否显示完整日志
     */
    public boolean isShowAll() {
        return showAll;
    }

    /**
     * 是否显示完整日志
     */
    public GLogEntity initShowAll(boolean showAll) {
        this.showAll = showAll;
        return this;
    }

    /**
     * 最大日志块数（可以显示多少个MAX_LENGTH的长度）
     */
    public int getMaxLengthCount() {
        return maxLengthCount;
    }

    /**
     * 最大日志块数（可以显示多少个MAX_LENGTH的长度）
     */
    public GLogEntity initMaxLengthCount(int maxLengthCount) {
        this.maxLengthCount = maxLengthCount;
        return this;
    }
}
