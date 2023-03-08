package com.gjk.javabasis.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

/**
 * SerializableTest
 *
 * @author: GJK
 * @date: 2022/8/14 18:21
 * @description:
 */
public class SerializableTest {

    public static final  String basePath  = "C:\\Users\\Administrator\\Desktop\\";


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Student student = new Student();
        student.setName("gjk");
        student.setNo(99);
        SerializableTest serializableTest = new SerializableTest();
        //JDK序列化
        //serializableTest.JDKSerializable(student);
        //Hessian序列化
        serializableTest.HessianSerializable(student);

    }


    public void JDKSerializable(Student student) throws IOException, ClassNotFoundException {
        //序列化
        FileOutputStream fileOutputStream = new FileOutputStream((basePath + "student.dat"));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(student);
        objectOutputStream.flush();
        objectOutputStream.close();

        //反序列化
        FileInputStream fileInputStream = new FileInputStream((basePath + "student.dat"));
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Student newStudent = (Student) objectInputStream.readObject();
        objectInputStream.close();
        System.out.println(newStudent.toString());
    }


    public void HessianSerializable(Student student) throws IOException, ClassNotFoundException {
        //序列化
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Hessian2Output hessian2Output = new Hessian2Output(byteArrayOutputStream);
        hessian2Output.writeObject(student);
        hessian2Output.flushBuffer();
        byte[] bytes = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();

        //反序列化
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Hessian2Input hessian2Input = new Hessian2Input(byteArrayInputStream);
        Student newStudent = (Student) hessian2Input.readObject();
        hessian2Input.close();
        System.out.println(newStudent.toString());
    }
}
