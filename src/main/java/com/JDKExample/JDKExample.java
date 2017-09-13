package com.JDKExample;

/**
 * @author zhailz
 * @date 17/9/13 - 下午7:35.
 */

/**
 * -XX:+PrintFlagsInitial 。这个参数显示在处理参数之前所有可设置的参数及它们的值，
 * 然后直接退出程序。“参数处理”包括许多步骤，例如说检查参数之间是否有冲突，通过ergonomics调整某些参数的值
 *
 *  -XX:+PrintCommandLineFlags 。这个参数的作用是显示出VM初始化完毕后所有跟最初的默认值不同的参数及它们的值。
 *
 *  -XX:+PrintFlagsFinal 。前一个参数只显示跟默认值不同的，而这个参数则可以显示所有可设置的参数及它们的值。
 * */
public class JDKExample {


  public static void main(String[] args){
    System.out.println ("JVM Options");
  }
}
