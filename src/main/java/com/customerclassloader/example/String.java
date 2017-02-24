package com.customerclassloader.example;

/**
 * @author zhailz
 *
 * 时间：2017年2月22日 ### 上午10:53:21
 */
public class String {

  public java.lang.String custom(){
    return "custom";
  }
  
  public java.lang.String run(){
    return "custom";
  }
  
  public static void main(java.lang.String[] args) {
    try {
      Class<?> string1 = String.class.getClassLoader().loadClass("java.lang.String");
      java.lang.String va = (java.lang.String) string1.newInstance();
      System.out.println(va.toString());
      Class<?> string2 = String.class.getClassLoader().loadClass("java.lang.String");
      java.lang.String vb = (java.lang.String) string2.newInstance();
      System.out.println(vb.toString());
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InstantiationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
