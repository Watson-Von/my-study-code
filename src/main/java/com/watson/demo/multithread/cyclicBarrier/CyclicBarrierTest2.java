package com.watson.demo.multithread.cyclicBarrier;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author : fengHangWen
 * @CreateDate : 2020/5/27 17:40
 * @Description : 先执行四个线程, 最后在汇总
 */
public class CyclicBarrierTest2 implements Runnable {

    private CyclicBarrier cyclicBarrier = new CyclicBarrier(4, this);

    private ExecutorService executor = Executors.newFixedThreadPool(4);

    private Map<String, Integer> map = new ConcurrentHashMap<>();

    public void count() {
        for (int i = 0; i < 4; i++) {
            executor.execute(() -> {
                try {
                    map.put(Thread.currentThread().getName(), 2);
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public void run() {
        if (map.isEmpty()) {
            System.out.println("空");
        } else {
            int result = 0;
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                result += entry.getValue();
            }
            System.out.println(result);
        }
        executor.shutdown();
    }

    public static void main(String[] args) {
        CyclicBarrierTest2 cyclicBarrierTest2 = new CyclicBarrierTest2();
        cyclicBarrierTest2.count();
    }

}
