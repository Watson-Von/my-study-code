package com.watson.demo.multithread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author : fengHangWen
 * @CreateDate : 2020/5/26 9:33
 * @Description : ForkJoin 框架测试
 */
public class ForkJoinTest {

    public static void main(String[] args) {

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask countTask = new CountTask(1, 4);
        ForkJoinTask<Integer> submit = forkJoinPool.submit(countTask);
        try {
            Integer result = submit.get();
            System.out.println("1+2+3+4 = " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            forkJoinPool.shutdown();
        }

    }

    /**
     * @author : fengHangWen
     * @CreateDate : 2020/5/26 9:52
     * @Description : 执行 start 到 end 相加的值
     * 例如: start = 1, end = 4, 则返回 1+2+3+4 的值
     */
    static class CountTask extends RecursiveTask<Integer> {

        // 阈值
        private static final int THRESHOLD = 2;
        private int start;
        private int end;

        public CountTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {

            int sum = 0;

            // 如果任务足够小, 则可以直接执行, 不进行拆分
            boolean canCompute = (end - start) <= THRESHOLD;

            if (canCompute) {
                for (int i = start; i <= end; i++) {
                    sum += i;
                }
            } else {
                // 任务进行拆分
                int middle = (start + end) / 2;
                CountTask leftTask = new CountTask(start, middle);
                CountTask rightTask = new CountTask(middle + 1, end);
                // 执行子任务, fork将去执行compute() 方法
                leftTask.fork();
                rightTask.fork();
                // 等待子任务执行完, 并得到结果
                Integer leftResult = leftTask.join();
                Integer rightResult = rightTask.join();

                // 合并子任务
                sum = leftResult + rightResult;
            }

            return sum;
        }
    }

}
