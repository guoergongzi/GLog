package com.gegz.logger.core.constants;

/**
 * 日志类型枚举（除A、JSON外，级别从上到下由低到高）
 */
public interface GLogTypes {

    /**
     * 全部日志
     */
    int V = 1;
    /**
     * 调试日志
     */
    int D = 2;
    /**
     * 信息日志
     */
    int I = 3;
    /**
     * 警告日志
     */
    int W = 4;
    /**
     * 错误日志
     */
    int E = 5;
    /**
     * 不可能发生的错误（级别与错误日志相等）
     */
    int WTF = 6;
    /**
     * JSON日志
     */
    int JSON = 7;
    /**
     * XML日志
     */
    int XML = 8;
}
