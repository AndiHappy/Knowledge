package com.stringcode.example;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @author zhailz
 *
 * 时间：2017年2月15日 ### 下午12:36:22
 * 
 * 示例：string的编码格式的变化，以及转化
 */
public class StringEnDeCode {

  /**
   * @param args
   * @throws UnsupportedEncodingException 
   */
  public static void main(String[] args) throws Exception {
    StringEnDeCode code = new StringEnDeCode();
    code.testStringConding();
  }

  private void testStringConding() throws Exception {
    String str = "I am 君山";
    // 这个默认的编码是UTF-8，因为是OXS的系统
    char[] chars = str.toCharArray();
    // [1,  , a, m,  , 君, 山]
    for (char c : chars) {
      System.out.print((int) c + " ");
    }
    // UTF-8 的字符集中，字符的编码是变长的，每一个字符采用的是1~6个字节进行编码,从对应的数值上面也能看出
    // 49 32 97 109 32 21531 23665
    
    for (char c : chars) {
      String value = Integer.toHexString((int) c);
      System.out.print(value + " ");
    }
    // 16进制的情况下，数组的展示为：
    // 49 20 61 6d 20 541b 5c71
    
    byte[] bytes = str.getBytes("UTF8");
    System.out.println("UTF-8 格式情况的bytes："+Arrays.toString(bytes));

    // [73, 32, 97, 109, 32, -27, -112, -101, -27, -79, -79]
    // 转化为字节数组的时候，使用一个字节能标识出来的字符，数字是准确的，
    // 但是超过一个自己的数组就不准确了
   
    
    byte[] bytesISO8859 = str.getBytes("ISO-8859-1");
    // 相当于把UTF-8格式的字符串，转化为 ISO-8859-1 肯定是会丢失信息的，为什么呢？
    // 因为 ISO-8859-1 是单字节编码，总共只有256个字符
    // [49, 32, 97, 109, 32, 63, 63]
    
    String isostring = new String(bytesISO8859, Charset.forName("ISO-8859-1"));
    // 单字节编码，其中一位标识符号位，还有7位，最大值2的八次方减一，即是63，所以我们可以看到
    // 21531 23665 都转化为了63，而63在ISO-8859-1 对应的就是一个 ? 字符
    System.out.println(isostring); 
    // I am ??
    
    byte[] bytesgbk = str.getBytes("GBK");
    // [73, 32, 97, 109, 32, -66, -3, -55, -67]
    // GBK 采用是双字节编码，所以在utf-8 转化为 gbk的时候，同样的会有其他意义的符号出现
    String stringgbk = new String(bytesgbk, Charset.forName("ISO-8859-1"));
    System.out.println(stringgbk);
    //I am ¾ýÉ½
    
    // gbk.txt 文件的属性是 gbk编码，所以读取的时候，采用gbk格式
    FileInputStream reader =  new FileInputStream("gbk.txt");
    byte[] cbuf = new byte[9];
    reader.read(cbuf);
    reader.close();
    System.out.println(Arrays.toString(cbuf));
    //[73, 32, 97, 109, 32, -66, -3, -55, -67]
    // 和UTF-8 的byte是不同的，但是英文的字符确是一致的

    String gbkstring = new String(cbuf, Charset.forName("GBK"));
    System.out.println(gbkstring);//I am 君山
    
    byte[] gbk2utf8 = gbkstring.getBytes(Charset.forName("UTF-8"));
//    [73, 32, 97, 109, 32, -27, -112, -101, -27, -79, -79]
    System.out.println(new String(gbk2utf8,Charset.forName("UTF-8")));
//    I am 君山

  }

}
