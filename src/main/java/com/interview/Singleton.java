package com.interview;

/**
 * @author zhailz
 * @Date 2017年8月19日 - 下午12:37:35
 * @Doc: 单例模式
 */
public class Singleton {

  // 第一种设计方式
  private Singleton() {}

  private static Singleton instance1 = null;

  public static Singleton getInstance1() {
    if (instance1 == null) {
      instance1 = new Singleton();
    }
    return instance1;
  }

  /**
   * 第一种的应用的场景是单线程
   * 如果是多线程的情况，在同时判断：instance1 == null 的时候，就会建立起两个实例来
   */

  // 第一种变形
  public static synchronized Singleton getInstance() {
    if (instance1 == null) {
      instance1 = new Singleton();
    }
    return instance1;
  }

  /**
   * 虽然保证了线程的安全性，但是效率不高
   */

  // 第二种：加锁双校验
  private static Singleton instance2 = null;

  public static Singleton getInstance2() {
    if (instance2 == null) {
      synchronized (Singleton.class) {
        if (instance2 == null) {
          instance2 = new Singleton();
        }
      }
    }
    return instance2;
  }

  /**
   * 这段代码看起来很完美，很可惜，它是有问题。主要在于instance2 = new Singleton()这句，
   * 这并非是一个原子操作，事实上在 JVM 中这句话大概做了下面 3 件事情。
   * 
   * 1. 给 instance 分配内存
   * 2. 调用 Singleton 的构造函数来初始化成员变量
   * 3. 将instance对象指向分配的内存空间（执行完这步 instance 就为非 null 了）
   * 
   * 但是在 JVM 的即时编译器中存在指令重排序的优化。也就是说上面的第二步和第三步的顺序是不能保证的，
   * 最终的执行顺序可能是 1-2-3 也可能是 1-3-2。如果是后者，则在 3 执行完毕、2 未执行之前，
   * 被线程二抢占了，这时 instance 已经是非 null 了（但却没有初始化），所以线程二会直接返回 instance，
   * 然后使用，然后顺理成章地报错。
   */

  // 第二种的变形
  private volatile static Singleton instance3; // 声明成 volatile

  public static Singleton getSingleton() {
    if (instance3 == null) {
      synchronized (Singleton.class) {
        if (instance3 == null) {
          instance3 = new Singleton();
        }
      }
    }
    return instance3;
  }

  /**
   * 第二种的变形，直接在声明变量的时候添加volatile标识符。
   * 
   * 使用volatile标识符，不是因为线程之间的可见性。而是使用 volatile 的主要原因是其另一个特性：
   * 禁止指令重排序优化。也就是说，在 volatile 变量的赋值操作后面会有一个内存屏障（生成的汇编代码上），
   * 读操作不会被重排序到内存屏障之前。从「先行发生原则」的角度理解的话，
   * 就是对于一个 volatile 变量的写操作都先行发生于后面对这个变量的读操作（这里的“后面”是时间上的先后顺序）。
   * 
   * 但是特别注意在 Java 5 以前的版本使用了 volatile 的双检锁还是有问题的。
   * 其原因是 Java 5 以前的 JMM （Java 内存模型）是存在缺陷的，
   * 即时将变量声明成 volatile 也不能完全避免重排序，
   * 主要是 volatile 变量前后的代码仍然存在重排序问题。
   * 这个 volatile 屏蔽重排序的问题在 Java 5 中才得以修复，所以在这之后才可以放心使用 volatile。
   */

  // 第三种
  // 类加载时就初始化
  private static final Singleton instance4 = new Singleton();

  public static Singleton getInstance4() {
    return instance4;
  }

  /**
   * 这个在是类加载的时候，就初始化，保证线程安全。借助JVM的加载类的特性
   */

  
  
  // 第四种
  private static class SingletonHolder {
    private static final Singleton instance = new Singleton();
  }
  public static final Singleton getInstance5() {
    return SingletonHolder.instance;
  }
  
  /**
   * 这种写法仍然使用JVM本身机制保证了线程安全问题；由于 SingletonHolder 是私有的，
   * 除了 getInstance5() 之外没有办法访问它，因此它是懒汉式的；
   * 同时读取实例的时候不会进行同步，没有性能缺陷；也不依赖 JDK 版本。
   * */
  
  // 第五种
  public enum EasySingleton{
      INSTANCE;
  }
  /**
   * 这个是依赖语法的规范。
   * 
   * 我们可以通过EasySingleton.INSTANCE来访问实例，这比调用getInstance()方法简单多了。
   * 创建枚举默认就是线程安全的，所以不需要担心double checked locking，
   * 而且还能防止反序列化导致重新创建新的对象。
   * */

}
