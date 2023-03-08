package com.gjk.javabasis.io.readfile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.core.io.ClassPathResource;

/**
 * Demo1
 *
 * @author: Thunisoft
 * @date: 2021/9/24 14:36
 * @description:
 */
public class Demo1 {


//    public static void main(String[] args) throws IOException {
//        Demo1 demo1 = new Demo1();
//        InputStream inputStream = demo1.getClass().getResourceAsStream("/文号.txt");
//        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
//        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//        String wh = null;
//        List<String> list = new ArrayList<>();
//        while((wh = bufferedReader.readLine())!= null){
//            list.add(wh);
//        }
//        System.out.println(list.toString());
//
//    }


    public static void main(String[] args) throws IOException {
        String absolutePath = new ClassPathResource("文号.txt").getFile().getAbsolutePath();
        System.out.println(absolutePath);
        List<String> list = Files.readAllLines(Paths.get(absolutePath));
        System.out.println(list);
    }
}
