package com.lwohvye.table.array;

import java.util.Arrays;

// 查找
public class SearchArray {

    // 有序数组（非递减）
    public int[] nums = new int[]{1, 2, 3, 4, 5, 5, 6, 6, 6, 6, 6, 6, 6, 7, 8, 9, 9, 9, 11, 25, 26, 43, 44};

    // 二分查找
    class BinarySearch {

        public int binarySearch(int[] nums, int target) {
            int left = 0, right = nums.length - 1;
            while (left <= right) {
                var mid = (right - left >> 1) + left; // 避免溢出
                var mNum = nums[mid];
                if (mNum == target)
                    return mid;
                else if (mNum < target)
                    left = mid + 1;
                else right = mid - 1;
            }
            // 未找到
            return -1;
        }

        public int binarySearch2(int[] nums, int target) {
            // 使用库函数。如果找到就会返回值的下标，如果没找到就会返回一个负数，这个负数取反之后就是查找的值应该在数组中的位置（从1开始计数）
            // - 当找到时，返回对应的下标
            // - 当未找到时，返回该元素在数组中应在的位置，将其取反 -1 后，即为首个大于目标值的元素的下标。同理取反 -1 -1 后，即为最后一个小于目标元素的下标
            // 并且需注意，在未找到时，取反后的值可能为数组长度 + 1，表示数组中所有的元素都比目标值小
            return Arrays.binarySearch(nums, target);
        }

        /*
            二分查找很简单，二分查找的变形需要注意一些细节。
            1、当找大于等于key的第一个元素，或者查找小于等于key的最后一个元素时，
            循环条件是 low < high，这和基本的二分查找不同，
            但需要在循环退出的时候，判断是否满足条件；

            2、如果是找最后一个满足条件的情况，循环条件是 low < high
            下限移动时不能用 low=mid+1；而应该用 low=mid；
            此时，mid计算时应该用 mid=（low+high+1）/2，
            保证 最后low、high相差1时不会陷入死循环，
            循环退出后，下限可能是结果；

            3、如果是找第一个满足条件的情况，循环条件是 low < high
            移动时不能用 high=mid-1；而应该用 high=mid；
            此时，mid计算时还是用 mid=（low+high）/2
            循环退出后，上限可能是结果；
            也可以考虑看Arrays.binarySearch()的源码。按那个通用的方式
         */
        // 使用库函数。如果找到就会返回值的下标，如果没找到就会返回一个负数，这个负数取反之后就是查找的值应该在数组中的位置（从1开始计数）
        // - 当找到时，返回对应的下标
        // - 当未找到时，返回该元素在数组中应在的位置，将其取反 -1 后，即为首个大于目标值的元素的下标。同理取反 -2 后，即为最后一个小于目标元素的下标
        // 并且需注意，在未找到时，取反后的值可能为数组长度 + 1（最大下标+2），表示数组中所有的元素都比目标值小。也可能为0，表示所有元素都比目标值大
        public int binarySearch0(int[] a, int low, int high, int key) {
            while (low <= high) {
                int mid = (low + high) >>> 1;
                int midVal = a[mid];

                if (midVal < key)
                    low = mid + 1;
                else if (midVal > key)
                    high = mid - 1;
                else
                    return mid; // key found
            }
            return -(low + 1);  // key not found.
        }
    }

}
