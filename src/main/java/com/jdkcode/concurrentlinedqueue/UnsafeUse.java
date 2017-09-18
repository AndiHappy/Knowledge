package com.jdkcode.concurrentlinedqueue;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author AndiHappy CollectionTest.java
 *
 * @date 2017年1月9日 下午8:14:11
 */
public class UnsafeUse {
  public  static Unsafe getUnsafe() {
    try {
        Field singleoneInstanceField = Unsafe.class.getDeclaredField("theUnsafe");
        singleoneInstanceField.setAccessible(true);
        return (Unsafe) singleoneInstanceField.get(null);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

}
