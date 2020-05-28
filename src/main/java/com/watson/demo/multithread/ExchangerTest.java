package com.watson.demo.multithread;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : fengHangWen
 * @CreateDate : 2020/5/28 10:19
 * @Description :
 * Exchanger 用于 "两个" 线程之间数据的交换, 如果两个线程中的一个没有执行exchange() 方法, 则会一直等待
 * public V exchange(V x, long timeout, TimeUnit unit) 方法还可以设置超时等待时间
 */
public class ExchangerTest {

    private static final Exchanger<String> exgr = new Exchanger<>();

    private static ExecutorService threadPool = Executors.newFixedThreadPool(4);

    public static void main(String[] args) {

        threadPool.execute(() -> {
            try {
                String a = "A";
                System.out.println("线程1拿到的数据 : " + exgr.exchange(a));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.execute(() -> {
            try {
                String b = "B";
                System.out.println("线程2拿到的数据 : " + exgr.exchange(b));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.execute(() -> {
            try {
                String c = "C";
                System.out.println("线程3拿到的数据 : " + exgr.exchange(c));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.execute(() -> {
            try {
                String d = "D";
                System.out.println("线程4拿到的数据 : " + exgr.exchange(d));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.shutdown();

    }
}
