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
  public Class<?> findClass(String name) throws ClassNotFoundException {
    byte[] bt = loadClassData(name);
    if(bt == null){
      throw new ClassNotFoundException();
    }else{
      return defineClass(name, bt, 0, bt.length);
    }
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

  /**
   * JVM在加载类之前会检查请求类是否已经被加载过，也就是要调用findLoadClass方法查看是否能够返回实例。如果类
   * 已经加载过了，在调用loadClass将会导致类冲突。但是JVM标识一个类是否和另外的一个类相同的时候，有两个条件，
   * 一个是看这个类的完成的类名是否一样，这个类名包括类所在的包名。二是看加载这个类的 classLoader 是否是同一个，
   * 这里所说的同一个是指classloader的实例是否是同一个实例。即使是同一个classLoader类的两个实例，加载同一个类
   * 也会不一样。所以根据这个原理，可以新建不同的classloader 加载实例对象，实现热部署。
   * */
  public static void main(String[] args) throws InstantiationException, IllegalAccessException,
      NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException,
      ClassNotFoundException {

    CustomerClassLoader loader = new CustomerClassLoader();
    findClass1(loader,"com.customerclassloader.example.String");
    /**
     *第二次调用，例如在代码中添加： 
     * findClass1(loader,"com.customerclassloader.example.String");
     * 此方法执行的时候，就会爆出：
     * Exception in thread "main" java.lang.LinkageError: loader 
     * (instance of  com/customerclassloader/example/CustomerClassLoader): 
     * attempted  duplicate class definition for name: "com/customerclassloader/example/String"
     *  at java.lang.ClassLoader.defineClass1(Native Method)
     *  at java.lang.ClassLoader.defineClass(ClassLoader.java:760)
     *  at java.lang.ClassLoader.defineClass(ClassLoader.java:642)
     *  at com.customerclassloader.example.CustomerClassLoader.findClass(CustomerClassLoader.java:25)
     *  at com.customerclassloader.example.CustomerClassLoader.findClass1(CustomerClassLoader.java:59)
     *  at com.customerclassloader.example.CustomerClassLoader.main(CustomerClassLoader.java:53)
     * 
     * */
    CustomerClassLoader loader1 = new CustomerClassLoader();
    findClass1(loader1,"com.customerclassloader.example.String");
    
    
  }

  private static void findClass1(CustomerClassLoader loader, String classfullpath)
      throws InstantiationException, IllegalAccessException, NoSuchMethodException,
      SecurityException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
    Class<?> c = loader.findClass(classfullpath);
    System.out.println("Loaded by :" + c.getClassLoader());
    Object ob = c.newInstance();
    Method md = c.getMethod("run");
    String returnstring = (java.lang.String) md.invoke(ob);
    System.out.println(returnstring);
  }

}
