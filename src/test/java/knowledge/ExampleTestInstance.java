package knowledge;

/**
 * @author zhailz
 *
 * 时间：2017年2月22日 ### 下午3:08:57
 */
public class ExampleTestInstance {

  public void change(int a,example ax){
    a =a+10;
    ax.a = ax.a+10;
  }
  
  public void change(Long a){
    a =a+10l;
  }
  
  public static void main(String[] args) {
    ExampleTestInstance instance = new ExampleTestInstance();
    example ax = new example();
    Integer a = 0;
    Long al = new Long(1);
    instance.change(a, ax);
    instance.change(al);
    System.out.println(a);
    System.out.println(ax.a);
    System.out.println(al.longValue());
  }

}

class example{
  int a;
}