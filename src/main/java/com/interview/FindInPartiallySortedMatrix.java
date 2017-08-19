package com.interview;

/**
 * @author zhailz
 * @Date 2017年8月19日 - 下午4:52:39
 * @Doc:在一个二维数组中，每一行都按照从左到右递增的顺序排列，每一列都按照从上到下递增的顺序排列，
 * 清完一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 */
public class FindInPartiallySortedMatrix {

  public static boolean find(int[][] array, int key) {
    if (array.length > 0) {
      int height = array.length;
      int length = array[0].length;

      int i = 0;
      int j = length - 1;
      while (j > -1 && i < height) {
        if (array[i][j] == key) {
          return true;
        } else if (array[i][j] > key) {
          j--;
        } else if (array[i][j] < key) {
          i++;
        }
      }

    }
    return false;
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    int[][] array = new int[][] { { 1, 2, 8, 9, 10 }, { 2, 4, 9, 12, 13 }, { 4, 7, 10, 13, 15 }, {
        6, 8, 11, 15, 17 } };

    System.out.println(find(array, 7));

    array = new int[][] { { 1, 2, 8, 9, 10 }, { 2, 4, 9, 12, 13 }, { 4, 5, 10, 13, 15 }, { 6, 8, 11,
        15, 17 } };

    System.out.println(find(array, 7));

  }

}
