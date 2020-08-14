package com.watson.demo.algorithm;

/**
 * @author : fengHangWen
 * @version : 1.0
 * @description : 二分查找算法
 * @company : 深圳一点盐光科技有限公司
 * @date : 2020/8/14 14:17
 */
public class Dichotomy {

    public static Integer dichotomy(int[] array, int i) {

        if (array == null || array.length == 0 || i == 0) {
            return 0;
        }

        int low = 0;
        int high = array.length - 1;
        int middle;

        while (low <= high) {

            middle = (low + high) / 2;
            System.out.println(middle);

            if (array[middle] > i) {
                high = middle - 1;

                System.out.println("high : " + high);
            } else if (array[middle] < i) {
                low = middle + 1;

                System.out.println("low : " + low);
            } else {
                return middle;
            }

        }


        return 0;

    }

    public static void main(String[] args) {

        int[] array = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
        int findNum = 5;

        System.out.println(dichotomy(array, findNum));

    }

}
