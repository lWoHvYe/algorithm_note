package com.lwohvye.table.array;

import java.util.ArrayList;

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
    }

}
