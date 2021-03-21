package com.example.geek_java;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;


public class Second_question extends ClassLoader{
    public static void main(String[] args) throws Exception {
        Second_question second_question = new Second_question();
        Class<?> aClass = second_question.findClass("Hello");
        //通过反射对象创建实例
        Object obj =aClass.newInstance();
        //通过反射对象获得方法
        Method show = aClass.getDeclaredMethod("hello");
        //设置方法的访问权限
        show.setAccessible(true);
        //调用方法
        show.invoke(obj);

    }
    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        FileInputStream inputStream = null;
        FileOutputStream outputStream=null;
        try {
            inputStream =new FileInputStream("src/com/example/geek_java/week1/Hello.xlass");
            byte[] bytes =new byte[inputStream.available()];
            inputStream.read(bytes);
            for (int i =0;i<bytes.length;i++){
                bytes[i]=(byte)(255 -bytes[i]);
            }
            outputStream=new FileOutputStream("src/com/example/geek_java/week1/Hello.class");
            outputStream.write(bytes);
            return defineClass(name,bytes,0,bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
