package com.gegz.logger.core.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gegz.logger.core.GLogConfig;
import com.gegz.logger.core.constants.GLogTypes;
import com.gegz.logger.core.entity.GLogEntity;
import com.gegz.logger.core.util.GLogPrinter;
import com.gegz.logger.core.util.GLogUtil;
import com.gegz.logger.core.util.GLogWrapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

/**
 * 打印日志到文件的实现类
 */
public class FileLogImpl {

    /**
     * 打印文件到日志的方法
     *
     * @param entity          日志配置信息
     * @param tagStr          日志标识
     * @param targetDirectory 日志目标文件夹
     * @param fileName        日志文件名
     * @param objectMsg       日志内容
     */
    public static void printFile(GLogEntity entity, String tagStr, File targetDirectory, String fileName, Object objectMsg) {
        // 判断当前是否需要打印日志
        if (entity.isForceShow() || GLogConfig.IS_SHOW_LOG) {
            // 加工日志内容
            String[] contents = GLogWrapper.wrapperContent(entity, tagStr, objectMsg);
            // 获取加工后的日志标识
            String tag = contents[0];
            // 获取加工后的日志内容
            String msg = contents[1];
            // 获取加工后的日志头
            String headString = contents[2];
            // 打印文件到日志
            printFile(tag, targetDirectory, fileName, headString, msg);
        }
    }

    /**
     * 打印日志到文件的方法
     *
     * @param tagStr          日志标识
     * @param targetDirectory 日志目标文件夹
     * @param fileName        日志文件名
     * @param headString      日志头
     * @param msg             日志内容
     */
    private static void printFile(String tagStr, File targetDirectory, @Nullable String fileName, String headString, String msg) {
        // 判断文件名是否为空，为空则生成一个文件名
        fileName = fileName == null ? GLogUtil.getFileName() : fileName;
        // 保存日志到文件并判断结果
        if (save(targetDirectory, fileName, msg)) {
            GLogPrinter.printSub(GLogTypes.D, tagStr, headString + " save log success ! location is >>>" + targetDirectory.getAbsolutePath() + "/" + fileName);
        } else {
            GLogPrinter.printSub(GLogTypes.E, tagStr, headString + "save log fails !");
        }
    }

    /**
     * 保存日志到文件的方法
     *
     * @param dic      文件位置
     * @param fileName 文件名
     * @param msg      日志内容
     * @return 是否保存成功
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static boolean save(File dic, @NonNull String fileName, String msg) {
        // 如果没有文件夹就创建文件夹
        if (!dic.exists()) {
            dic.mkdirs();
        }
        // 新建文件对象
        File file = new File(dic, fileName);
        // 如果没有文件就创建文件
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 尝试进行保存
        try (OutputStream outputStream = new FileOutputStream(file); OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            // 写入日志内容
            outputStreamWriter.write(msg);
            // 清理写入流缓冲区
            outputStreamWriter.flush();
            return true;
        } catch (Exception e) {
            // 打印错误信息
            e.printStackTrace();
            return false;
        }
    }

}
