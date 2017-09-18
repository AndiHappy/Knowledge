package knowledge;

import static java.lang.Class.forName;

/**
 * @author zhailz
 * @date 17/9/17 - 上午9:40.
 */
public class ClassLoaderAndClassForName {

  public static void main(String[] args){

    try {

      Object value =  forName ("com.interview.FileCopyUtil");
      System.out.println (value.toString ());

      value =  ClassLoader.getSystemClassLoader ();
      System.out.println (value.toString ());

    } catch (ClassNotFoundException e) {
      e.printStackTrace ();
    }

  }
}
