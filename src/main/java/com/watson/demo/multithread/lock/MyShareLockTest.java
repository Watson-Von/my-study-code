package com.watson.demo.multithread.lock;

import org.junit.Test;

public class MyShareLockTest {

    @Test
    public void test() {

        final MyShareLock myShareLock = new MyShareLock();

        class Worker extends Thread {

            @Override
            public void run() {

                while (true) {

                    // 获取到锁
                    myShareLock.lock();

                    try {
                        // 睡 1s
                        Thread.sleep(1000L);
                        System.out.println(Thread.currentThread().getName());
                        // 再睡 1s
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        // 释放锁
                        myShareLock.unLock();
                    }

                }
            }
        }

        // 启动十个线程
        for (int i = 0; i < 10; i++) {
            Worker worker = new Worker();
            // 设置为守护线程, 当 JVM 中没有非守护线程在运行的时候, JVM 会关闭
            worker.setDaemon(true);
            worker.start();
        }

        for (int i = 0; i < 10; i++) {

            try {
                // 睡 1s 换行
                Thread.sleep(1000L);
                System.out.println();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
