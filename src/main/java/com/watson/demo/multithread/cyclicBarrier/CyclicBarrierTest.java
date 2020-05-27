package com.watson.demo.multithread.cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author : fengHangWen
 * @CreateDate : 2020/5/27 16:45
 * @Description :
 * 拦截一批线程, 直到达到屏障的时候才会让这一批线程并发执行
 * parties 定义拦截线程数
 * 当拦截的线程都 await() 完了以后, 优先执行构造函数第二个入参 barrierAction (也是一个线程)
 */
public class CyclicBarrierTest {

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("3");
    });

    public static void main(String[] args) {

        new Thread(() -> {
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("1");
        }).start();

        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("2");

    }

}
