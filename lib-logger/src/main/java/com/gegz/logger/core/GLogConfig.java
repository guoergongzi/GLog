package com.gegz.logger.core;

/**
 * 日志整体配置类
 */
public class GLogConfig {
    /**
     * 找到日志代码需要追溯的层数
     */
    public static final int STACK_TRACE_INDEX = 5;

    /**
     * 每块日志行数（用于切割日志规避开发环境的日志上限）
     */
    public final static int MAX_LENGTH = 4000;

    /**
     * 是否显示日志
     */
    public static boolean IS_SHOW_LOG = false;
    /**
     * 是否以最高级别显示所有日志
     */
    public static boolean IS_ALL_ERROR = false;
    /**
     * 默认日志前綴，一般用应用简称即可
     */
    public static String TAG_DEFAULT = "logger";
    /**
     * 默认日志文件前綴，一般用应用简称加“_”即可
     */
    public static String FILE_PREFIX = "logger_";
    /**
     * 是否显示日志所在模块（如Live、Home、Blog等）
     */
    public static boolean IS_SHOW_MODULE = true;
    /**
     * 是否显示日志类型（如Network、Data、View等）
     */
    public static boolean IS_SHOW_TYPE = false;

}
