package com.interview;

import java.util.regex.Pattern;

/**
 * @author zhailz
 * @Date 2017年8月19日 - 下午6:17:23
 * @Doc: 替换空格
 */
public class ReplaceBlank {
  
  public static String replaceBlankInJDK(String va) {
    return Pattern.compile(" ").matcher(va).replaceAll("%20");
  }
  

  /**
   * @param args
   */
  public static void main(String[] args) {
    System.out.println(replaceBlank("we are happy."));
  }



  /**
   * 从两端开始
   * */
  private static char[] replaceBlank(String string) {
    char[] value = string.toCharArray();
    int length = 0;
    for (int i = 0; i < value.length; i++) {
      if(value[i] == ' ') {
        length++;
      }
    }
    
    char[] tmp = new char[value.length+length*2];
    for (int i = 0,ii = 0,j=value.length -1,jj= tmp.length-1; ii<=jj; i++,j--) {
      if(value[i] != ' ') {
        tmp[ii] = value[i];
        ii=ii+1;
      }else {
        tmp[ii] = '%';
        tmp[ii+1]='2';
        tmp[ii+2]='0';
        ii=ii+3;
      }
      
      if(value[j] != ' ') {
        tmp[jj] = value[j];
        jj=jj-1;
      }else {
        tmp[jj] = '0';
        tmp[jj-1]='2';
        tmp[jj-2]='%';
        jj=jj-3;
      }
    }
    
    return tmp;
  }

}
