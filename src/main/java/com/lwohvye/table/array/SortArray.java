package com.lwohvye.table.array;

// 排序
public class SortArray {

    public int[] quickSort(int[] nums, int start, int end) {
        if (start < 0 || end >= nums.length || start > end)
            return new int[0];
        var smallIndex = partition(nums, start, end);
        quickSort(nums, start, smallIndex - 1);
        quickSort(nums, smallIndex + 1, end);
        return nums;
    }

    /**
     * 这个理解起来还可以
     *
     * @param nums
     * @param start
     * @param end
     * @return int
     * @date 2022/2/26 6:18 PM
     */
    private int partition(int[] nums, int start, int end) {
        int pivot = (int) (Math.random() * (end - start + 1)) + start; // 随机取一个标识，打乱一下，避免极端情况下（倒序）n^2的问题
        swap(nums, start, pivot);
        var index = start;
        for (int i = start + 1; i <= end; i++) {
            if (nums[i] < nums[start])  // 升序，这里带不带等于都行，把等于分到哪一侧好像区别不大
                swap(nums, ++index, i); // 把index后移一位，再与其交换，这样index及其前的都不比基准大
        }
        swap(nums, start, index); // 最后把基准放到index处
        return index;
    }

    /**
     * 这个最初没有理解，尤其是各边界
     *
     * @param nums
     * @param start
     * @param end
     * @return int
     * @date 2022/2/26 6:18 PM
     */
    private int partition1(int[] nums, int start, int end) {
        //基准在范围内随机选择
        int pivot = (int) (Math.random() * (end - start + 1)) + start; // 随机取一个标识，打乱一下，避免极端情况下（倒序）n^2的问题
        //因为下面比较前先进行了自增，所以这里先减一
        int index = start - 1;
        //把基准放到最后，最后会将基准挪到smallIndex对应的位置
        swap(nums, pivot, end);
        for (int i = start; i <= end; i++) {
            // 22-2-26：index为分界，其左侧都比基准小，而其所在的位置，可能比基准大（i > index），此时其为与i位置之间，首个比基准大的值
            if (nums[i] <= nums[end]) { // 这里没有等于，会栈溢出
                index++;
                //i比smallIndex大，证明出现了end不是最大的情况，
                if (i > index) swap(nums, i, index);
            }
        }
        return index;
    }

    /**
     * 这个也比较简单，是另一种方式
     *
     * @param nums
     * @param start
     * @param end
     * @return int
     * @date 2022/2/26 6:22 PM
     */
    private int partition2(int[] nums, int start, int end) {
        int i = start; // 因为从start右侧开始找，不包括start
        int j = end + 1; // 因为先--，所以这里先加了1
        while (true) {
            while (nums[++i] < nums[start] && i < end) ; // 向右找一个比l大的
            while (nums[--j] > nums[start] && j > start) ; // 向左找一个比l小的
            if (i >= j) break;
            swap(nums, i, j); // 交换两者，前提是i要小于j才行，所以**不能**把上面的break移除，并把while的条件改为i<j
        }
        swap(nums, start, j); // 把基准放到j的位置。
        return j; // 此时，j的位置即为第j个元素
    }

    private void swap(int[] nums, int start, int pivot) {
        // 当用下面这种交换形式时，需注意前校验两者的值不相等
        if (nums[start] == nums[pivot]) return;
        nums[start] ^= nums[pivot];
        nums[pivot] ^= nums[start];
        nums[start] ^= nums[pivot];
    }


}
