package com.customerclassloader.example;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author zhailz
 *
 * 时间：2017年2月22日 ### 下午1:54:48
 */
public class CustomerURLClassLoader extends URLClassLoader {

  public CustomerURLClassLoader(URL[] urls, ClassLoader parent) {
    super(urls, parent);
  }
  
 

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
