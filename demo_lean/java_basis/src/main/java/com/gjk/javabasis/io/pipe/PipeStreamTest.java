package com.gjk.javabasis.io.pipe;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;

/**
 * PipeStreamTest
 *
 * @author: GJK
 * @date: 2022/6/6 14:44
 * @description:
 */
public class PipeStreamTest {

    public static void main(String[] args) throws IOException {

        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream();
        pipedOutputStream.connect(pipedInputStream);
        //
        //E:\GJK\项目材料\znws\外部对接文档\密标系统对接\产品廉政档案密标对接设计文档.doc
        byte[] bytes = Files.readAllBytes(Paths.get("E:\\GJK\\项目材料\\OA\\20220402第三次测评问题.docx"));
        pipedOutputStream.write(bytes);
        System.out.println("-=====" + pipedInputStream.toString());



    }


}
