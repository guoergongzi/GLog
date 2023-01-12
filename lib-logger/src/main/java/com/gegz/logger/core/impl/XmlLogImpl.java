package com.gegz.logger.core.impl;

import com.gegz.logger.core.constants.GLogStrs;
import com.gegz.logger.core.entity.GLogEntity;
import com.gegz.logger.core.util.GLogPrinter;
import com.gegz.logger.core.util.GLogUtil;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * 打印Xml日志的实现类
 */
public class XmlLogImpl {

    /**
     * 打印Xml日志的方法
     *
     * @param entity     日志配置信息
     * @param tag        日志标识
     * @param xml        日志内容
     * @param headString 日志头
     */
    public static void printXml(GLogEntity entity, String tag, String xml, String headString) {
        // 默认打印未格式化的Json数据
        DefaultLogImpl.printDefault(entity, entity.getJsonLevel(), tag, headString + xml);
        // 判断是否需要显示格式化后的Json数据
        if (!entity.isFormatJson()) {
            return;
        }
        // 判断xml内容是否为空
        if (xml != null) {
            // 解析xml内容
            xml = formatXML(xml);
            // 拼接日志头和xml内容
            xml = headString + "\n" + xml;
        } else {
            // 拼接日志头和提示信息
            xml = headString + "Log with null object";
        }
        // 打印分割线
        GLogUtil.printLine(entity.getJsonLevel(), tag, true);
        // 系统换行符判空
        if (GLogStrs.LINE_SEPARATOR != null) {
            // 使用换行符分割xml日志内容
            String[] lines = xml.split(GLogStrs.LINE_SEPARATOR);

            // 遍历Json日志内容条目并打印
            for (String line : lines) {
                // 判断当前行内容不是空行才打印
                if (!GLogUtil.isEmpty(line)) {
                    GLogPrinter.printSub(entity.getJsonLevel(), tag, "║ " + line);
                }
            }
        }
        // 打印分割线
        GLogUtil.printLine(entity.getJsonLevel(), tag, false);
    }

    private static String formatXML(String inputXML) {
        try {
            Source xmlInput = new StreamSource(new StringReader(inputXML));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString().replaceFirst(">", ">\n");
        } catch (Exception var4) {
            var4.printStackTrace();
            return inputXML;
        }
    }
}
