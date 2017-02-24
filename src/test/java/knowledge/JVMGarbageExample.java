package knowledge;

/**
 * @author zhailz
 *
 * 时间：2017年2月24日 ### 上午9:44:22
 * 
 * example:不同垃圾回收算法的设计
 */
public class JVMGarbageExample {
  
  

  /**
   * 使用的设置
   * -Xms20M 初始堆大小
   * -Xmx20M 堆的最大值的大小
   * -Xmn10M 年轻代大小(1.4or lator)
   * -XX:+UseSerialGC 使用Serial垃圾回收器
   * -XX:+PrintGCDetails 打印GC日志
   * GC日志分析：
   * 总共20M，年轻代(young代）10M,也就是 eden区为8M，from为1M，to为1M
   * @throws InterruptedException 
   */
  @SuppressWarnings("unused")
  public static void main(String[] args) throws InterruptedException {
//    Thread.sleep(10000);
    // 找到java进程的pid
    int m = 1024*1024;// 如果在此行注释掉下面所有的代码，Eden的使用占比是18%usered，
    byte[] b = new byte[2*m];//运行此行代码的时候，Eden的使用占比是43%，43%-18% = 25%，8m的四份之一，正好对应是b的2M内存
    Thread.sleep(1000);
    byte[] b1 = new byte[2*m];//运行此行代码的时候，Eden的使用占比是68%，68%-43% = 25%，8m的四份之一，正好对应是b1的2M内存
    Thread.sleep(1000);
    byte[] b2 = new byte[2*m];//运行此行代码的时候，Eden的使用占比是93%，93%-68% = 25%，8m的四份之一，正好对应是b1的2M内存
    Thread.sleep(1000);
    byte[] b3 = new byte[(2*m)];//运行此行代码的时候，Eden就会满，触发一次GC
//    byte[] b31 = new byte[(int)(0.5*m)];//运行此行代码的时候，Eden就会满，触发一次GC
//    byte[] b32 = new byte[(int)(0.5*m)];//运行此行代码的时候，Eden就会满，触发一次GC

//    Thread.sleep(1000);
//    byte[] b4 = new byte[2*m];//2M的内存空间
//    Thread.sleep(1000);
//    byte[] b5 = new byte[2*m];//2M的内存空间
//    Thread.sleep(1000);
//    byte[] b6 = new byte[2*m];//2M的内存空间
//    Thread.sleep(1000);
//    byte[] b7 = new byte[2*m];//2M的内存空间
  }
/*
  [GC (Allocation Failure) [DefNew: 7503K->470K(9216K), 0.0073545 secs] 7503K->6614K(19456K), 0.0074044 secs] 
  [Times: user=0.00 sys=0.00, real=0.01 secs] 
      [GC (Allocation Failure) [DefNew: 6771K->6771K(9216K), 0.0000122 secs]
      [Tenured: 6144K->8192K(10240K), 0.0043661 secs] 12915K->12755K(19456K), 
      [Metaspace: 2785K->2785K(1056768K)], 0.0044101 secs] 
      [Times: user=0.01 sys=0.00, real=0.01 secs] 
      
      Heap
       def new generation   total 9216K, used 6775K [0x00000007bec00000, 0x00000007bf600000, 0x00000007bf600000)
        eden space 8192K,  82% used [0x00000007bec00000, 0x00000007bf29dfe0, 0x00000007bf400000)
        from space 1024K,   0% used [0x00000007bf500000, 0x00000007bf500000, 0x00000007bf600000)
        to   space 1024K,   0% used [0x00000007bf400000, 0x00000007bf400000, 0x00000007bf500000)
       tenured generation   total 10240K, used 8192K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
         the space 10240K,  80% used [0x00000007bf600000, 0x00000007bfe00040, 0x00000007bfe00200, 0x00000007c0000000)
       Metaspace       used 2791K, capacity 4486K, committed 4864K, reserved 1056768K
        class space    used 299K, capacity 386K, committed 512K, reserved 1048576K
*/
  
  
 /*
  *  jmap -heap 21954 命令：
  Attaching to process ID 21954, please wait...
  Debugger attached successfully.
  Server compiler detected.
  JVM version is 25.40-b25

  using thread-local object allocation.
  Mark Sweep Compact GC

  Heap Configuration:
     MinHeapFreeRatio         = 40
     MaxHeapFreeRatio         = 70
     MaxHeapSize              = 20971520 (20.0MB)
     NewSize                  = 10485760 (10.0MB)
     MaxNewSize               = 10485760 (10.0MB)
     OldSize                  = 10485760 (10.0MB)
     NewRatio                 = 2
     SurvivorRatio            = 8
     MetaspaceSize            = 21807104 (20.796875MB)
     CompressedClassSpaceSize = 1073741824 (1024.0MB)
     MaxMetaspaceSize         = 17592186044415 MB
     G1HeapRegionSize         = 0 (0.0MB)

  Heap Usage:
  New Generation (Eden + 1 Survivor Space):
     capacity = 9437184 (9.0MB)
     used     = 1392144 (1.3276519775390625MB)
     free     = 8045040 (7.6723480224609375MB)
     14.751688639322916% used
  Eden Space:
     capacity = 8388608 (8.0MB)
     used     = 1392144 (1.3276519775390625MB)
     free     = 6996464 (6.6723480224609375MB)
     16.59564971923828% used
  From Space:
     capacity = 1048576 (1.0MB)
     used     = 0 (0.0MB)
     free     = 1048576 (1.0MB)
     0.0% used
  To Space:
     capacity = 1048576 (1.0MB)
     used     = 0 (0.0MB)
     free     = 1048576 (1.0MB)
     0.0% used
  tenured generation:
     capacity = 10485760 (10.0MB)
     used     = 0 (0.0MB)
     free     = 10485760 (10.0MB)
     0.0% used

  807 interned Strings occupying 52728 bytes.
*/
  
  /*
   * jstat -gc 22036 1000 命令的，展示的GC的形式
   * 
  S0C    S1C    S0U    S1U      EC       EU        OC         OU       MC     MU    CCSC   CCSU   YGC     YGCT    FGC    FGCT     GCT   
 1024.0 1024.0  0.0    0.0    8192.0   1359.7   10240.0      0.0     4480.0 780.8  384.0   75.8       0    0.000   0      0.000    0.000
 1024.0 1024.0  0.0    0.0    8192.0   1359.7   10240.0      0.0     4480.0 780.8  384.0   75.8       0    0.000   0      0.000    0.000
 1024.0 1024.0  0.0    0.0    8192.0   1359.7   10240.0      0.0     4480.0 780.8  384.0   75.8       0    0.000   0      0.000    0.000
 1024.0 1024.0  0.0    0.0    8192.0   1359.7   10240.0      0.0     4480.0 780.8  384.0   75.8       0    0.000   0      0.000    0.000
 1024.0 1024.0  0.0    0.0    8192.0   3407.7   10240.0      0.0     4480.0 780.8  384.0   75.8       0    0.000   0      0.000    0.000
 1024.0 1024.0  0.0    0.0    8192.0   5455.8   10240.0      0.0     4480.0 780.8  384.0   75.8       0    0.000   0      0.000    0.000
 1024.0 1024.0  0.0    0.0    8192.0   7503.8   10240.0      0.0     4480.0 780.8  384.0   75.8       0    0.000   0      0.000    0.000
 1024.0 1024.0  0.0   471.0   8192.0   2048.0   10240.0     6144.0   4864.0 2781.8 512.0  298.9       1    0.006   0      0.000    0.006
 1024.0 1024.0  0.0   471.0   8192.0   4252.7   10240.0     6144.0   4864.0 2781.8 512.0  298.9       1    0.006   0      0.000    0.006
 1024.0 1024.0  0.0   471.0   8192.0   6300.7   10240.0     6144.0   4864.0 2781.8 512.0  298.9       1    0.006   0      0.000    0.006
 1024.0 1024.0  0.0    0.0    8192.0   6694.2   10240.0     8192.1   4864.0 2782.3 512.0  298.9       2    0.006   1      0.005    0.011
  */
  
  
}