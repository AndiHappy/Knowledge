package knowledge;
class Exception1 extends Exception {}

class Exception2 extends Exception1 {}

public class Human {
  public static void main(String[] args) throws Exception {
    try {
      try {
        throw new Exception2();
      } catch (Exception1 a) {
        System.out.println("Caught Exception1");
        throw a;
      }
    } catch (Exception2 s) {
      System.out.println("Caught Exception2");
      return;
    } finally {
      System.out.println("Hello World!");
    }
  }
}
