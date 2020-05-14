package com.watson.demo.multithread.waitnotify;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author : fengHangWen
 * @CreateDate : 2020/5/14 10:58
 * @Description :
 * 等待/通知机制, 是指一个线程 A 调用了对象 O 的 wait() 方法进入等待状态,
 * 而另一个线程 B 调用了对象 O 的 notify() 或者 notifyAll() 方法,
 * 线程 A 收到通知后从对象 O 的 wait() 方法返回, 进而执行后续操作.
 * 上述两个线程通过对象 O 来完成交互,
 * 而对象上的 wait() 和 notify/notifyAll() 的关系就如同开关信号一样, 用来完成等待方和通知方之间的交互工作.
 *
 * 注意事项:
 *
 * 1. 调用 wait(), notify(), notifyAll() 需要先对调用对象加锁.
 * 2. 调用 wait() 方法后, 线程状态从 RUNNING 变成 WAITING, 并将当前线程放到对象的等待队列.
 * 3. 调用 notify()/notifyAll() 后, 等待线程不会马上从 wait() 返回, 需要等调用 notify()/notifyAll() 的线程释放锁.
 * 4. 从 wait() 方法返回的前提是获得了调用对象的锁.
 * 5. notifyAll() 方法是将等待队列中的全部线程移到同步队列, 被移动的线程状态由 WAITING 变成 BLOCKED.
 */
public class WaitNotify {

    private static boolean flag = true;
    private static final Object lock = new Object();

    /**
     * @author : fengHangWen
     * @CreateDate : 2020/5/14 11:21
     * @Description : 程序实现意图:
     * waitThread 首先获取了对象的锁, 然后调用对象的 wait() 方法, 从而放弃了锁并进入了对象的等待队列 WaitQueue 中, 进入等待状态.
     * 由于 waitThread 释放了对象的锁, notifyThread 随后获取了对象的锁, 并调用对象的 notify() 方法,
     * 将 waitThread 从 WaitQueue 移到 SynchronizedQueue 中,
     * 此时 waitThread 的状态变为阻塞状态, notifyThread 释放了锁之后, waitThread 再次获取到锁并从 wait() 方法返回继续执行.
     */
    public static void main(String[] args) throws Exception {

        Thread waitThread = new Thread(new Wait(), "waitThread");
        waitThread.start();

        TimeUnit.SECONDS.sleep(1);

        Thread notifyThread = new Thread(new Notify(), "notifyThread");
        notifyThread.start();

    }

    /**
     * @author : fengHangWen
     * @CreateDate : 2020/5/14 10:50
     * @Description : 消费者线程
     */
    static class Wait implements Runnable {

        @Override
        public void run() {

            synchronized (lock) {
                while (flag) {
                    try {
                        System.out.println(Thread.currentThread() +
                                "flag is true waitnotify @ " +
                                new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        // 释放锁对象
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println(Thread.currentThread() +
                    "flag is false running @ " +
                    new SimpleDateFormat("HH:mm:ss").format(new Date()));
        }
    }

    /**
     * @author : fengHangWen
     * @CreateDate : 2020/5/14 10:48
     * @Description : 生产者线程
     */
    static class Notify implements Runnable {

        @Override
        public void run() {

            synchronized (lock) {
                try {
                    System.out.println(Thread.currentThread() +
                            "hold lock. notify @ " +
                            new SimpleDateFormat("HH:mm:ss").format(new Date()));
                    // 通知"等待"状态的线程
                    lock.notify();
                    flag = false;
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            synchronized (lock) {
                try {
                    System.out.println(Thread.currentThread() +
                            "hold lock. again. sleep @ " +
                            new SimpleDateFormat("HH:mm:ss").format(new Date()));
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
