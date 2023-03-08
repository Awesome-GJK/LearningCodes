package com.gjk.iceblue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.documents.TextSelection;

/**
 * spiredoc
 *
 * spiredoc-可直接指定doc文件，通过api去修改doc文件内容等，如替换书签，添加水印等
 * 官网帮助文档链接：https://www.e-iceblue.cn/spiredocforjavaoperating/create-word-document-in-java.html
 *
 *
 * @author: gaojiankang
 * @date: 2021/07/28/15:25
 * @description:
 */
public class spiredoc {


    private static final String PATTERN_MATCH = "【[\\s\\S]*】";

    public static void main(String[] args) throws FileNotFoundException {
        File file;
        try (FileInputStream fileInputStream = new FileInputStream("D:\\CCFile\\姚元培\\山西统计局人事考核需求.docx")) {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document(fileInputStream);
           //根据正则匹配关键词，获取文本内容
            Pattern c = Pattern.compile(PATTERN_MATCH);
            TextSelection[] allPattern = document.findAllPattern(c);
            List<String> texts = Arrays.stream(allPattern).map(text -> text.get(1)).collect(Collectors.toList());
            System.out.println(JSON.toJSONString(texts));
            //接收所有修订
            document.acceptChanges();
            //保存为docx格式文件
            document.saveToStream(outputStream, FileFormat.Docx);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
