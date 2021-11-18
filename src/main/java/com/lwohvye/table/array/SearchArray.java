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
    }

}
