package com.customerclassloader.example;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.String;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author zhailz
 *
 *         时间：2017年2月22日 ### 上午10:49:09
 * 
 *         示例：自定义的classLoader
 * 
 */
public class CustomerClassLoader extends ClassLoader {
  @Override
  public Class<?> findClass(String name) {
    byte[] bt = loadClassData(name);
    return defineClass(name, bt, 0, bt.length);
  }

  private byte[] loadClassData(String className) {
    // read class
    InputStream is = getClass().getClassLoader().getResourceAsStream(className.replace(".", "/")
        + ".class");
    ByteArrayOutputStream byteSt = new ByteArrayOutputStream();
    // write into byte
    int len = 0;
    try {
      while ((len = is.read()) != -1) {
        byteSt.write(len);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    // convert into byte array
    return byteSt.toByteArray();
  }

  public static void main(String[] args) throws InstantiationException, IllegalAccessException,
      NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException,
      ClassNotFoundException {

    CustomerClassLoader loader = new CustomerClassLoader();
    findClass1(loader,"com.customerclassloader.example.String");
  }

  private static void findClass1(CustomerClassLoader loader, String classfullpath)
      throws InstantiationException, IllegalAccessException, NoSuchMethodException,
      SecurityException, IllegalArgumentException, InvocationTargetException {
    Class<?> c = loader.findClass(classfullpath);
    System.out.println("Loaded by :" + c.getClassLoader());
    Object ob = c.newInstance();
    Method md = c.getMethod("run");
    String returnstring = (java.lang.String) md.invoke(ob);
    System.out.println(returnstring);
  }

}
